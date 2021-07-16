package com.ess.meetingplanner.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "ROOM")
public class Room {
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Long id;

	@Column(name = "NAME")
	@NotNull(message="Veuillez donner le nom de la salle")
	private String name;
	
	@Column(name = "CAPACITY")
	@NotNull(message="Veuillez donner la capacité initiale")
	private long capacity;

	@Column(name = "FEATURES")	
	private String features;
	
	@Transient
	private List<Feature> featuresList = new ArrayList<Feature>();
	
	@Transient
	private List<String> featuresListStr = new ArrayList<String>();
	
	@Transient
	private List<Feature> featuresToAddList = new ArrayList<Feature>();
	
	@Transient
	private List<Feature> featuresAddedList = new ArrayList<Feature>();
	
	@Transient
	private long sanitary_capacity;

	@Column(name = "START_TIME")
	private LocalDateTime startTime;
	
	@Column(name = "END_TIME")
	private LocalDateTime endTime;
	
	@Transient
	private boolean selected;
	
	@Transient
	private String noRoomNeeded;

	public Room(String name, long initialCapacity, String features) {
		this.name = name;
		
		this.capacity = initialCapacity;
		
		this.sanitary_capacity = Math.round(Math.floor(initialCapacity * 0.7));
		
		this.features = features;
		
		String[] featureTab = features.split("|");
		
		List<String> featuresListStr = Arrays.asList(featureTab);
		
		for(String featureName: featuresListStr) {
			this.featuresList.add(Feature.valueOf(featureName));
		}
		
	}
	
	private void setFeaturesFromList(List<Feature> featuresList ) {
		this.features = featuresList.stream().map(elt -> elt.name()).collect(Collectors.joining("|"));
	}
	
	public void setAllFeaturesList() {
		if(this.features != null) {
			this.featuresListStr = Arrays.stream(this.features.split("\\|")).collect(Collectors.toList());
			this.featuresList = this.featuresListStr.stream().map(elt -> Feature.valueOf(elt)).collect(Collectors.toList());
		}
	}
	
	public static Room calculateSanitary_capacity(Room room ) {
		room.setSanitary_capacity(Math.round(Math.floor(room.getCapacity() * 0.7)));
		return room;
	}
	
	public String toString() {
		return "Name : " + this.name + ", Real Capacity : " + this.sanitary_capacity + ", Features : " + this.features;
	}
	
	public boolean equals(Object obj) {
		if((obj == null) || (!(obj instanceof Room))) return false;
		
		
		if (this == obj ) {
			return true;
		}
		
//		if(((Room)obj).getName().equalsIgnoreCase(this.name)) return true;
		
		return false;
	}
}
