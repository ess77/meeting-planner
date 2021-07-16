package com.ess.meetingplanner.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ess.meetingplanner.model.ExtraFeature;

@Repository
public interface FeatureRepository extends JpaRepository<ExtraFeature, Long> {

	public List<ExtraFeature> findAll();
}
