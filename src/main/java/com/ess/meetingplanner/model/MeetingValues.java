package com.ess.meetingplanner.model;

public class MeetingValues {

	
	private Long id;

	
	private String type;

	
	private String startTime;
	
	
	private String endTime;

	
	private int attendantsNumber;

	public MeetingValues(MeetingType type, String startTime, String endTime, int attendantsNumber) {
		this.type = type.name();
		this.startTime = startTime;
		this.endTime = endTime;
		this.attendantsNumber = attendantsNumber;
	}

}

