package com.ess.meetingplanner.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ess.meetingplanner.model.Meeting;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {

	public List<Meeting> findAll();
}
