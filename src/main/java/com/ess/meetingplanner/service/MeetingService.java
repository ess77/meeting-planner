package com.ess.meetingplanner.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ess.meetingplanner.model.Meeting;
import com.ess.meetingplanner.repository.MeetingRepository;

@Service
public class MeetingService {
	
	@Autowired
	private MeetingRepository meetingRepository;

	
	public List<Meeting> findAll() {
		// TODO Auto-generated method stub
		return meetingRepository.findAll();
	}

}
