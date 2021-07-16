package com.ess.meetingplanner.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ess.meetingplanner.model.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

	@Query(value = "SELECT * FROM ROOM r  WHERE r.name = :name", nativeQuery = true)
	public List<Room> findByName(String name);
}
