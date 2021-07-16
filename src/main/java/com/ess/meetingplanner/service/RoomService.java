package com.ess.meetingplanner.service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ess.meetingplanner.model.Room;
import com.ess.meetingplanner.repository.RoomRepository;

@Service
public class RoomService {
	
	@Autowired
	private RoomRepository roomRepository;
	
	@Value("${message.rs23.no-room-needed}")
	private String messageRS23;
	
	public Room getOptimizedPropsal(List<Room> roomProposals, int attendants) {
		if(roomProposals == null) return null;
		
		Long min = roomProposals.stream().map(room -> {return Math.abs(room.getSanitary_capacity() - attendants);}).reduce(0l, Long::min);
		Optional<Room> minCapacityRoom = roomProposals.stream().sorted(Comparator.comparing(Room::getSanitary_capacity)).findFirst();
		//Sure, there must be at least one
		return minCapacityRoom.isPresent()? minCapacityRoom.get(): null;
		
	}
	
	public Room getNoRoomNeeded() {
		List<Room> roomList = roomRepository.findByName("E9999");
		Room roomUnecessary = roomList.get(0);
		roomUnecessary.setNoRoomNeeded(messageRS23);
		return roomUnecessary;
	}
}
