package com.ess.meetingplanner.controller;

import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.annotations.Synchronize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ess.meetingplanner.model.ExtraFeature;
import com.ess.meetingplanner.model.Meeting;
import com.ess.meetingplanner.model.MeetingType;
import com.ess.meetingplanner.model.Room;
import com.ess.meetingplanner.repository.FeatureRepository;
import com.ess.meetingplanner.repository.MeetingRepository;
import com.ess.meetingplanner.repository.RoomRepository;
import com.ess.meetingplanner.service.PlanningService;

import lombok.Synchronized;

@RestController
public class PlanningController {
	
	/** Logger **/
	private static final Logger LOGGER = LoggerFactory.getLogger(PlanningController.class);
	
	@Autowired
	private MeetingRepository meetingRepository;
	
	@Autowired
	private RoomRepository roomRepository;
	
	@Autowired
	private FeatureRepository featureRepository;
	
	@Autowired
	private PlanningService planningService;
	
	private LocalDateTime startTime = LocalDateTime.now().plusDays(1).withHour(8).withMinute(0);
	
	private LocalDateTime endTime = startTime.plusHours(1l);
	
	@GetMapping("/")
	public ModelAndView newPlanningForm(ModelAndView mav) {
		InetAddress ip = InetAddress.getLoopbackAddress();
		Meeting meeting = new Meeting(MeetingType.RC, startTime, endTime, 3);
    	mav.addObject("meeting", meeting);
    	mav.addObject("meetingTypes", MeetingType.values());
    	mav.setViewName("planning");
    	LOGGER.info("getPlanningForm : {} : {}", mav, meeting);
    	return mav;
	}
	
	@GetMapping("/listAllMeetings")
	public ModelAndView showPlanningList(ModelAndView mav) {
		List<Meeting> planningList = meetingRepository.findAll();
		mav.addObject("planningList", planningList);
		mav.setViewName("planningList");
		LOGGER.info("showPlanningList");
		return mav;
	}
	
	@GetMapping("/listAllRooms")
	public ModelAndView showRoomList(ModelAndView mav) {
		List<Room> roomList = roomRepository.findAll();
		roomList = roomList.stream().map(Room::calculateSanitary_capacity).collect(Collectors.toList());
		mav.addObject("roomList", roomList);
		mav.setViewName("roomList");
		LOGGER.info("showRoomList");
		return mav;
	}
	
	@GetMapping("/listExtraFeaturesAvailable")
	public ModelAndView showExtraFeaturesAvailableList(ModelAndView mav) {
		List<ExtraFeature> extraFeaturesAvailableList = featureRepository.findAll();
		mav.addObject("extraFeaturesAvailableList", extraFeaturesAvailableList);
		mav.setViewName("extraFeaturesAvailableList");
		LOGGER.info("showExtraFeaturesAvailableList");
		return mav;
	}
	
	@GetMapping("/newMeeting")
	public ModelAndView showPlanningForm(ModelAndView mav) {
		Meeting meeting = new Meeting(MeetingType.RC, startTime, endTime, 3);
		mav.addObject("meeting", meeting);
		mav.addObject("meetingTypes", MeetingType.values());
		mav.setViewName("planning");
		LOGGER.info("showPlanningForm");
		return mav;
	}
	
	@PostMapping("/addMeeting")
	@Synchronized
	public ModelAndView postPlanningForm(ModelAndView mav, @ModelAttribute Meeting meetingPosted) {
		Room roomProposal = planningService.getPlanning(meetingPosted);
		Room roomSelected = null;
		
		if(roomProposal != null && (roomProposal.getNoRoomNeeded() == null)) {
			
				roomProposal.setSelected(true);
				roomProposal.setStartTime(meetingPosted.getStartTime());
				
				//let one hour for sanitary cleansing  crew
				LocalDateTime newEndTime =  meetingPosted.getEndTime().minusHours(1l);
				roomProposal.setEndTime(newEndTime);
				
				roomSelected = roomRepository.save(roomProposal);
		}
			
		if((roomProposal != null) && (roomProposal.getNoRoomNeeded() != null)) {
			mav.setViewName("notificationMessage");
			mav.addObject("errorMessage", roomProposal.getNoRoomNeeded());
			LOGGER.info("postPlanningForm : {} : no room for RS23 message {}", mav, roomProposal.getNoRoomNeeded());
			
		} else if((roomSelected != null) && (roomSelected.getNoRoomNeeded() == null)) {
			Meeting meetingSetted = meetingPosted;
			meetingSetted.setActivated(true);
			meetingSetted.setRoomSelected(roomSelected.getName());
			meetingRepository.save(meetingSetted);
			mav.addObject("meeting", meetingSetted);
			mav.setViewName("showPlanning");
			LOGGER.info("postPlanningForm : {} : room proposed : {}", mav, roomSelected);
			
		} else if(roomSelected == null) {
			mav.setViewName("errorMessage");
			mav.addObject("errorMessage", "Pas de Salle Disponible.");
		}
		
		LOGGER.info("postPlanningForm : {}", mav);
		return mav;
	}
	
	// Return a generic 500 response in case of any runtime exception
	@ExceptionHandler(RuntimeException.class)
	public final ResponseEntity<Exception> handleAllExceptions(RuntimeException ex) {
	    return new ResponseEntity<Exception>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
