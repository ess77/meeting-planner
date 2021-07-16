package com.ess.meetingplanner.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import com.ess.meetingplanner.model.Room;
import com.ess.meetingplanner.repository.MeetingRepository;
import com.ess.meetingplanner.service.PlanningService;
import com.ess.meetingplanner.service.RoomRepositoryService;
import com.ess.meetingplanner.service.RoomService;


/**
 * Test de {@link BlogArticleService} avec les annotations Mockito
 *
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ComponentScan(basePackages = {"com.ess.meetingplanner"})
public class RoomServiceTest {
	
	/** Logger **/
	private static final Logger LOGGER = LoggerFactory.getLogger(RoomServiceTest.class);
	
	@Autowired
	private RoomRepositoryService roomRepositoryService;
	
	@Autowired
	private RoomService roomService;
	
	@Test
	public void getOptimizedPropsalTest() {
		List<Room> roomList = roomRepositoryService.findAll();
		List<Room> roomListFiltered = null;
		int participants = 5;
		
		roomList = roomList.stream().map(Room::calculateSanitary_capacity).filter(room -> room.getSanitary_capacity() > participants).collect(Collectors.toList());
		
		Room roomMinCapacity = roomService.getOptimizedPropsal(roomList, participants);
		
		roomListFiltered = roomList.stream().filter(room -> !room.equals(roomMinCapacity)).collect(Collectors.toList());
		Room roomAboveCapacity = roomService.getOptimizedPropsal(roomListFiltered, participants);
		
		
		assertTrue( roomMinCapacity.getSanitary_capacity() >= participants);
		assertTrue( roomMinCapacity.getSanitary_capacity() <= roomAboveCapacity.getSanitary_capacity());
		
    	LOGGER.info("getOptimizedPropsalTest : {} ",  roomMinCapacity);
	}
	
}
