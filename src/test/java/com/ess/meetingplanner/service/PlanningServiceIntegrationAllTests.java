package com.ess.meetingplanner.service;


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
public class PlanningServiceIntegrationAllTests {
	
	/** Logger **/
	private static final Logger LOGGER = LoggerFactory.getLogger(PlanningServiceIntegrationAllTests.class);
	
	@Autowired
	private RoomRepositoryService roomRepositoryService;
	
	@Autowired
//	@Mock
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
	
	//Reunion2
	Meeting reunion2 = null;
	
	//Reunion3
	Meeting reunion3 = null;
	
	//Reunion4
	Meeting reunion4 = null;
	
	//Reunion5
	Meeting reunion5 = null;
	
	//Reunion6
	Meeting reunion7 = null;
	
	//Reunion7
	Meeting reunion6 = null;
	
	//Reunion8
	Meeting reunion8 = null;
	
	//Reunion9
	Meeting reunion9 = null;
	
	//Reunion10
	Meeting reunion10 = null;
	
	//Reunion11
	Meeting reunion11 = null;
	
	//Reunion12
	Meeting reunion12 = null;
	
	//Reunion13
	Meeting reunion13 = null;
	
	//Reunion14
	Meeting reunion14 = null;
	
	//Reunion15
	Meeting reunion16 = null;
	
	//Reunion16
	Meeting reunion15 = null;
	
	//Reunion17
	Meeting reunion17 = null;
	
	//Reunion18
	Meeting reunion18 = null;
	
	//Reunion19
	Meeting reunion19 = null;
	
	//Reunion20
	Meeting reunion20 = null;
	
	//Bloc d'initialusation
	{
		
		//Reunion1
		reunion1 = new Meeting(MeetingType.VC, LocalDateTime.of(2021, 7, 15, 9, 0), LocalDateTime.of(2021, 7, 15, 10, 0), 8);
		
		//Reunion2
		reunion2 = new Meeting(MeetingType.VC, LocalDateTime.of(2021, 7, 15, 9, 0), LocalDateTime.of(2021, 7, 15, 10, 0), 6);
		
		//Reunion3
		reunion3 = new Meeting(MeetingType.RC, LocalDateTime.of(2021, 7, 15, 11, 0), LocalDateTime.of(2021, 7, 15, 12, 0), 4);
		
		//Reunion4
		reunion4 = new Meeting(MeetingType.RS23, LocalDateTime.of(2021, 7, 15, 11, 0), LocalDateTime.of(2021, 7, 15, 12, 0), 2);
		
		//Reunion5
		reunion5 = new Meeting(MeetingType.SPEC, LocalDateTime.of(2021, 7, 15, 11, 0), LocalDateTime.of(2021, 7, 15, 12, 0), 9);
		
		//Reunion6
		reunion7 = new Meeting(MeetingType.RC, LocalDateTime.of(2021, 7, 15, 11, 0), LocalDateTime.of(2021, 7, 15, 12, 0), 7);
		
		//Reunion7
		reunion6 = new Meeting(MeetingType.VC, LocalDateTime.of(2021, 7, 15, 11, 0), LocalDateTime.of(2021, 7, 15, 12, 0), 9);
		
		//Reunion8
		reunion8 = new Meeting(MeetingType.SPEC, LocalDateTime.of(2021, 7, 15, 8, 0), LocalDateTime.of(2021, 7, 15, 9, 0), 10);
		
		//Reunion9
		reunion9 = new Meeting(MeetingType.SPEC, LocalDateTime.of(2021, 7, 15, 9, 0), LocalDateTime.of(2021, 7, 15, 10, 0), 5);
		
		//Reunion10
		reunion10 = new Meeting(MeetingType.RS4n, LocalDateTime.of(2021, 7, 15, 9, 0), LocalDateTime.of(2021, 7, 15, 10, 0), 4);
		
		//Reunion11
		reunion11 = new Meeting(MeetingType.RC, LocalDateTime.of(2021, 7, 15, 9, 0), LocalDateTime.of(2021, 7, 15, 10, 0), 8);
		
		//Reunion12
		reunion12 = new Meeting(MeetingType.VC, LocalDateTime.of(2021, 7, 15, 11, 0), LocalDateTime.of(2021, 7, 15, 12, 0), 12);
		
		//Reunion13
		reunion13 = new Meeting(MeetingType.SPEC, LocalDateTime.of(2021, 7, 15, 11, 0), LocalDateTime.of(2021, 7, 15, 12, 0), 5);
		
		//Reunion14
		reunion14 = new Meeting(MeetingType.VC, LocalDateTime.of(2021, 7, 15, 8, 0), LocalDateTime.of(2021, 7, 15, 9, 0), 3);
		
		//Reunion15
		reunion16 = new Meeting(MeetingType.SPEC,  LocalDateTime.of(2021, 7, 15, 8, 0), LocalDateTime.of(2021, 7, 15, 9, 0), 2);
		
		//Reunion16
		reunion15 = new Meeting(MeetingType.VC, LocalDateTime.of(2021, 7, 15, 8, 0), LocalDateTime.of(2021, 7, 15, 9, 0), 12);
		
		//Reunion17
		reunion17 = new Meeting(MeetingType.VC, LocalDateTime.of(2021, 7, 15, 10, 0), LocalDateTime.of(2021, 7, 15, 11, 0), 10);
		
		//Reunion18
		reunion18 = new Meeting(MeetingType.RS23, LocalDateTime.of(2021, 7, 15, 11, 0), LocalDateTime.of(2021, 7, 15, 12, 0), 2);
		
		//Reunion19
		reunion19 = new Meeting(MeetingType.RS4n, LocalDateTime.of(2021, 7, 15, 9, 0), LocalDateTime.of(2021, 7, 15, 12, 0), 4);
		
		//Reunion20
		reunion20 = new Meeting(MeetingType.RC, LocalDateTime.of(2021, 7, 15, 9, 0), LocalDateTime.of(2021, 7, 15, 10, 0), 7);
	}
	
	@BeforeEach
	public  void initForEach() {
		MockitoAnnotations.openMocks(this);
	}
	
	
	@Test
	public void getPlanningForAllReunionTest() {
		
		
		LOGGER.info("getPlanningForAllReunionTest");
		
		List<Meeting> reunionList = List.of(reunion1, reunion2, reunion3, reunion4, reunion5, 
											reunion6, reunion7, reunion8, reunion9, reunion10, 
											reunion11, reunion12, reunion13, reunion14, reunion15, 
											reunion16, reunion17, reunion18, reunion19, reunion20);

		for(Meeting reunion: reunionList) {
    		
    		assertNotNull(plannningService.getPlanning(reunion));
    		
    	}
		
//		List<Room> roomList = roomRepository.findByName("E3001");
//		Room roomReunion1 = roomList.get(0);
//		Room.calculateSanitary_capacity(roomReunion1);
//		when(plannningService.getPlanning(reunion1)).thenReturn(roomReunion1);
//		assertEquals(roomReunion1, plannningService.getPlanning(reunion1));
	}
	
	
}
