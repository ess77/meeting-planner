package com.ess.meetingplanner.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ess.meetingplanner.model.Room;
import com.ess.meetingplanner.repository.RoomRepository;

@Service
public class RoomRepositoryService {
	
	@Autowired
	private RoomRepository roomRepository;

	
	public List<Room> findAll() {
		// TODO Auto-generated method stub
		return roomRepository.findAll();
	}
	
	public List<Room> getByName(String name) {
		// TODO Auto-generated method stub
		return roomRepository.findByName(name);
	}

}
