package com.ess.meetingplanner.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ess.meetingplanner.model.ExtraFeature;
import com.ess.meetingplanner.model.Feature;
import com.ess.meetingplanner.model.Meeting;
import com.ess.meetingplanner.model.MeetingType;
import com.ess.meetingplanner.model.Room;
import com.ess.meetingplanner.repository.FeatureRepository;
import com.ess.meetingplanner.repository.MeetingRepository;
import com.ess.meetingplanner.repository.RoomRepository;
import com.ess.meetingplanner.utils.DateRangeComparator;

import lombok.Synchronized;

@Service
public class PlanningService {

	/** Logger **/
	private static final Logger LOGGER = LoggerFactory.getLogger(PlanningService.class);

	@Autowired
	private MeetingRepository meetingRepository;
	
	@Autowired
	private RoomService roomgService;

	@Autowired
	private RoomRepository roomRepository;

	@Autowired
	private FeatureRepository featureRepository;
	
	@Autowired
	private RoomService roomService;
	
	@Value("${message.rs23.no-room-needed}")
	private String messageRS23;

	@Synchronized
	public Room getPlanning(Meeting meetingProposal) {
		
		//if meeting type is RS23, no need for room planning
		if(MeetingType.RS23.name().equalsIgnoreCase(meetingProposal.getType())) {
			Room roomUnecessary = this.roomgService.getNoRoomNeeded();
			return roomUnecessary;
		}
		
		//For sanitary cleaning convenience, add one hour to endTime
		LocalDateTime newEndTime =  meetingProposal.getEndTime().plusHours(1l);
		meetingProposal.setEndTime(newEndTime);

		//get the set of room with minimum capacity required
		List<Room> roomList = getRoomListWithRequiredCapacity(meetingProposal);

		//get the set of room with range not overlaying the required one !!!
		getRoomListWithAvailableRange(meetingProposal, roomList);

		//Get the list of required features for this meeting
		List<Feature> requiredFeatureList = getFeatureList(meetingProposal);

		List<Room> finalRoomList = new ArrayList<>();
		
		/** Get the set of room owning all the features or that can be provided with the required features.
		 *  First we seek for rooms with all required features, thereafter we process rooms with requiring 
		 *  features to be added.
		 **/
		
		// seeking rooms with all required features or populate the list of missing features for each room
		getRoomListWithAllFeatures(roomList, requiredFeatureList, finalRoomList);
		
		// process for rooms with missing features
		getRoomListWithFeaturesAdded(roomList, finalRoomList);
		
		Room roomProposal = roomService.getOptimizedPropsal(finalRoomList, meetingProposal.getAttendantsNumber());
		
		//remove the chosen room from the finalRoomList for release of resources
		if(roomProposal != null) finalRoomList.stream().filter(roomNotChosen -> roomNotChosen.equals(roomProposal));
		
		// Release unecesssary ressources
		List<Feature> addedFeatures;
		for(Room roomToRelease:finalRoomList) {
			addedFeatures = roomToRelease.getFeaturesAddedList();
			releaseExtraFeatures(addedFeatures);
		}
		
		return roomProposal;
	}
	
	/**
	 * With the extra features list,
	 * release the resources not for processing
	 */
	private void releaseExtraFeatures(List<Feature> addedFeatures) {
		List<ExtraFeature> extraFeaturesList = featureRepository.findAll();
		populateExtraFeatureList(extraFeaturesList);
		for(Feature featureToRelease: addedFeatures) {//Que pasa?

			//Get the index in extraFeatures list
			@SuppressWarnings("unlikely-arg-type")
			Optional<Integer> indexOfExtraFeatureOpt = extraFeaturesList
					.stream()
					//Extrafeature equals method has been overidden accordingly
					.filter(extraFeat -> extraFeat.equals(featureToRelease))
					.map(elt -> extraFeaturesList.indexOf(elt)).findFirst();
			
			if(indexOfExtraFeatureOpt.isEmpty()) continue;
			int indexOfExtraFeature = indexOfExtraFeatureOpt.get();
			if(indexOfExtraFeature < 0) continue;

			ExtraFeature extraFeature = extraFeaturesList.get(indexOfExtraFeature);
			int availableQuantity = extraFeature.getAvailableQuantity();
			availableQuantity++;
			extraFeature.setAvailableQuantity(availableQuantity);
		}
	}

	
	/**
	 * @param meetingProposal
	 * @return
	 */
	public List<Room> getRoomListWithRequiredCapacity(Meeting meetingProposal) {
		
		int requiredCapacity = meetingProposal.getAttendantsNumber();
		List<Room> roomList = roomRepository.findAll();

		//set room sanitary_capacity
		roomList = roomList.stream().map(Room::calculateSanitaryCapacity).collect(Collectors.toList());
		
		List<Room> roomList1 = roomList.stream().filter(room -> room.getSanitary_capacity() >= requiredCapacity).collect(Collectors.toList());
		roomList1.stream().map(room -> room.getSanitary_capacity()).forEach(System.out::println);
		return roomList1;
		
	}
	
	/**
	 * @param meetingProposal
	 * @param roomList1
	 */
	public void getRoomListWithAvailableRange(Meeting meetingProposal, List<Room> roomList) {
		List<Room> roomToRemoveFromProcess = new ArrayList<>();
		DateRangeComparator localDateTimeRangeComparator = new DateRangeComparator(meetingProposal.getStartTime(), meetingProposal.getEndTime());
		for(Room room : roomList) {
			if(localDateTimeRangeComparator.isWithinRangeLocalDateTime(room.getStartTime()) || localDateTimeRangeComparator.isWithinRangeLocalDateTime(room.getEndTime())) {
				roomToRemoveFromProcess.add(room);
			}
		}
		roomList.removeAll(roomToRemoveFromProcess);
	}
	
	/**
	 * @param meetingProposal
	 * @return
	 */
	public List<Feature> getFeatureList(Meeting meetingProposal) {
		MeetingType meetingType = MeetingType.valueOf(meetingProposal.getType());
		List<Feature> requiredFeatureList = Arrays.asList(
				(meetingType.getNbrBOARD() == 1? Feature.BOARD: null),
				(meetingType.getNbrSCREEN() == 1? Feature.SCREEN: null),
				(meetingType.getNbrSPIDER_PHONE() == 1? Feature.SPIDER_PHONE: null),
				(meetingType.getNbrWEBCAM() == 1? Feature.WEBCAM: null));
		return requiredFeatureList;
	}
	
	
	/**
	 * Rooms with initial all required features will be put in the finalRoomList,
	 * and remove from the roomList.
	 * The other ones will stay in the roomList for further processings.
	 *
	 * @param roomList1
	 * @param requiredFeatureList
	 * @param finalRoomList
	 */
	public void getRoomListWithAllFeatures(List<Room> roomList, List<Feature> requiredFeatureList, List<Room> finalRoomList) {
		List<Room> roomToRemoveFromProcess = new ArrayList<>();
		for(Room room: roomList) {
			room.setAllFeaturesList();
			for(Feature feature: requiredFeatureList) {
				if(feature != null && !room.getFeaturesList().contains(feature)) {//Warning!!
					room.getFeaturesToAddList().add(feature);
				}
			}

			if(room.getFeaturesToAddList().size() == 0) {//We've got one
				roomToRemoveFromProcess.add(room);
				finalRoomList.add(room);

			}
		}
		roomList.removeAll(roomToRemoveFromProcess);
		
	}

	/**
	 * Proccessing of the rooms which require to add features to meet the requirements.
	 * Extra features are consifered as limited resources and and as such, require to manage availability.
	 * 
	 * @param roomList
	 * @param finalRoomList
	 */
	public void getRoomListWithFeaturesAdded(List<Room> roomList, List<Room> finalRoomList) {
		if(roomList.size() != 0) {
			List<ExtraFeature> extraFeaturesList = featureRepository.findAll();
			
			// Set association with corresponding Feature
			populateExtraFeatureList(extraFeaturesList);

			List<Room> roomToRemoveFromProcess = new ArrayList<>();
			for(Room room: roomList) {
				List<Feature> missingFeatures = room.getFeaturesToAddList();
				List<Feature> missingFeaturesToRemove = new ArrayList<>();
				
				/**
				 * For every feature in the missingFeatures list, we try get its corresponding index in 
				 * the extraFeaturesList list, and decrement its available quantity.
				 */
				for(Feature feature: missingFeatures) {

					//equals method has been overidden accordingly
					Optional<Integer> indexOfExtraFeatureOpt = extraFeaturesList.stream().filter(extraFeat -> extraFeat.equals(feature)).map(elt -> extraFeaturesList.indexOf(elt)).findFirst();
					if(indexOfExtraFeatureOpt.isEmpty()) continue;
					int indexOfExtraFeature = indexOfExtraFeatureOpt.get();
					if(indexOfExtraFeature < 0) continue;

					ExtraFeature extraFeature = extraFeaturesList.get(indexOfExtraFeature);
					int availableQuantity = extraFeature.getAvailableQuantity();

					if(availableQuantity > 0) {

						availableQuantity--;
						extraFeature.setAvailableQuantity(availableQuantity);
						room.getFeaturesAddedList().add(feature);
						missingFeaturesToRemove.add(feature);

					} else { 
						
						/** We need to revert the added resources, because the containing room 
						 * cannot meet the requirements or is not chosen.
						 */
						List<Feature> addedFeatures = room.getFeaturesAddedList();
						
						//release resources
						releaseExtraFeatures(addedFeatures);
						
						addedFeatures.clear();
						room.setFeaturesAddedList(addedFeatures);
					}
				}
				if(missingFeaturesToRemove.size() == missingFeatures.size()) {
					missingFeatures.clear();
					roomToRemoveFromProcess.add(room);
					finalRoomList.add(room);
					
				}
			}
			roomList.removeAll(roomToRemoveFromProcess);
		}
	}
	
	

	/**
	 * @param extraFeaturesList
	 */
	public void populateExtraFeatureList(List<ExtraFeature> extraFeaturesList) {
		extraFeaturesList.get(0).setFeature(Feature.SCREEN);
		extraFeaturesList.get(1).setFeature(Feature.BOARD);
		extraFeaturesList.get(2).setFeature(Feature.SPIDER_PHONE);
		extraFeaturesList.get(3).setFeature(Feature.WEBCAM);
	}

}
