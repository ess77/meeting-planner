package com.ess.meetingplanner.controller;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import com.ess.meetingplanner.model.Meeting;
import com.ess.meetingplanner.model.MeetingType;
import com.ess.meetingplanner.model.Room;
import com.ess.meetingplanner.repository.MeetingRepository;
import com.ess.meetingplanner.repository.RoomRepository;
import com.ess.meetingplanner.service.PlanningService;
import com.ess.meetingplanner.service.RoomRepositoryService;
import com.ess.meetingplanner.service.RoomService;


/**
 * Test de {@link BlogArticleService} avec les annotations Mockito
 *
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ComponentScan(basePackages = {"com.ess.meetingplanner"})
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PlanningServiceIntegrationByReunionTests {
	
	/** Logger **/
	private static final Logger LOGGER = LoggerFactory.getLogger(PlanningServiceIntegrationByReunionTests.class);
	
	@Autowired
	private RoomRepositoryService roomRepositoryService;
	
//	@Autowired
	@Mock
	private PlanningService plannningService;
	
	@Autowired
	private RoomService roomService;
	
	@Autowired
	private MeetingRepository meetingRepository;
	
	
	private PlanningService planningService = new PlanningService();
	
	@Autowired
	private RoomRepository roomRepository;
	
	private LocalDateTime startTime = LocalDateTime.of(2021, 7, 15, 8, 0);
	
	private LocalDateTime endTime = startTime.plusHours(1l);
	
	//Reunion1
	Meeting reunion1 = null;
	
	//Reunion4
	Meeting reunion4 = null;
	
	
	//Bloc d'initialusation
	{
		
		//Reunion1
		reunion1 = new Meeting(MeetingType.VC, LocalDateTime.of(2021, 7, 15, 9, 0), LocalDateTime.of(2021, 7, 15, 10, 0), 8);
		
		//Reunion4
		reunion4 = new Meeting(MeetingType.RS23, LocalDateTime.of(2021, 7, 15, 11, 0), LocalDateTime.of(2021, 7, 15, 12, 0), 2);
		
	}
	
	@BeforeEach
	public  void initForEach() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void getPlanningForReunion1Test() {
		
		
    	LOGGER.info("getPlanningForm : {} ",  reunion1);
    	List<Room> roomList = null;
    	
    	//Cas Reunion1
        roomList = roomRepository.findByName("E3001");
        Room roomReunion1 = roomList.get(0);
        Room.calculateSanitary_capacity(roomReunion1);
        when(plannningService.getPlanning(reunion1)).thenReturn(roomReunion1);
        assertEquals(roomReunion1, plannningService.getPlanning(reunion1));
        
//        assertNotNull(plannningService.getPlanning(reunion1));
	}
	
	@Test
	public void getPlanningForReunion4Test() {
		
		LOGGER.info("getPlanningForReunion4Test : {} ",  reunion4);
		
		//cas reunion4
    	Room roomReunion4 = roomService.getNoRoomNeeded();
		when(plannningService.getPlanning(reunion4)).thenReturn(roomReunion4);
		assertNotNull(plannningService.getPlanning(reunion4).getNoRoomNeeded());
		assertEquals(roomReunion4, plannningService.getPlanning(reunion4));
	}
	
	
	
}
