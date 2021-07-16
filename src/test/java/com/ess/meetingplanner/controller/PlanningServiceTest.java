package com.ess.meetingplanner.controller;


import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import com.ess.meetingplanner.model.Feature;
import com.ess.meetingplanner.model.Meeting;
import com.ess.meetingplanner.model.MeetingType;
import com.ess.meetingplanner.model.Room;
import com.ess.meetingplanner.repository.MeetingRepository;
import com.ess.meetingplanner.service.PlanningService;
import com.ess.meetingplanner.service.RoomRepositoryService;
import com.ess.meetingplanner.service.RoomService;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ComponentScan(basePackages = {"com.ess.meetingplanner"})
public class PlanningServiceTest {
	
	/** Logger **/
	private static final Logger LOGGER = LoggerFactory.getLogger(PlanningServiceTest.class);
	
	@Autowired
	private RoomRepositoryService roomRepositoryService;
	
	@Autowired
	private PlanningService plannningService;
	
	@Autowired
	private RoomService roomService;
	
	@Autowired
	private MeetingRepository meetingRepository;
	
	
	private PlanningService planningService = new PlanningService();
	
	private LocalDateTime startTime = LocalDateTime.of(2021, 7, 15, 8, 0);
	
	private LocalDateTime endTime = startTime.plusHours(1l);
	
	//Reunion1
		Meeting reunion1 = null;
		
	{
		//Reunion1
		reunion1 = new Meeting(MeetingType.VC, LocalDateTime.of(2021, 7, 15, 9, 0), LocalDateTime.of(2021, 7, 15, 10, 0), 8);
	}
	
	
	@Test
	public void  getFeatureListTest() {
		
		List<Feature> featureRequiredList = null;
		Meeting meeting = new Meeting();
		
		//VC
		meeting.setType(MeetingType.VC.name());
		featureRequiredList = planningService.getFeatureList(meeting);
		
		assertTrue(featureRequiredList.contains(Feature.SCREEN));
		assertTrue(featureRequiredList.contains(Feature.SPIDER_PHONE));
		assertTrue(featureRequiredList.contains(Feature.WEBCAM));
		
		//RC
		meeting.setType(MeetingType.RC.name());
		featureRequiredList = planningService.getFeatureList(meeting);
		
		assertTrue(featureRequiredList.contains(Feature.SCREEN));
		assertTrue(featureRequiredList.contains(Feature.BOARD));
		assertTrue(featureRequiredList.contains(Feature.SPIDER_PHONE));
	}
	
	@Test
	public void getRoomListWithAllFeatures_VC_Test() {
		
		List<Room> roomList = roomRepositoryService.findAll();
		int initialRoomListSize = roomList.size();
		int actualRoomListSize = roomList.size();
		
		List<Room> finalRoomList = new ArrayList<>();
		
		List<Feature> featureRequiredList = null;
		Meeting meeting = new Meeting();
		
		//Cas MeetingType.VC
		meeting.setType(MeetingType.VC.name());
		featureRequiredList = planningService.getFeatureList(meeting);
		
		planningService.getRoomListWithAllFeatures(roomList, featureRequiredList, finalRoomList);
		
		actualRoomListSize = roomList.size();
		
		assertTrue(actualRoomListSize == initialRoomListSize - 1);
		for(Room room: finalRoomList) {
			featureRequiredList = room.getFeaturesList();
			assertTrue(featureRequiredList.contains(Feature.SCREEN));
			assertTrue(featureRequiredList.contains(Feature.SPIDER_PHONE));
			assertTrue(featureRequiredList.contains(Feature.WEBCAM));
		}
	}
	
	@Test
	public void getRoomListWithAllFeatures_RC_Test() {
		
		List<Room> roomList = roomRepositoryService.findAll();
		int initialRoomListSize = roomList.size();
		int actualRoomListSize = roomList.size();
		
		List<Room> finalRoomList = new ArrayList<>();
		
		List<Feature> featureRequiredList = null;
		Meeting meeting = new Meeting();
		
		//Cas MeetingType.RC
		meeting.setType(MeetingType.RC.name());
		featureRequiredList = planningService.getFeatureList(meeting);
		
		planningService.getRoomListWithAllFeatures(roomList, featureRequiredList, finalRoomList);
		
		assertTrue(actualRoomListSize == initialRoomListSize);
		assertTrue(finalRoomList.size() == 0);
	}
	
}
