package com.ess.meetingplanner.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "MEETING")
public class Meeting {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Long id;

	@Column(name = "TYPE")
	@NotEmpty(message="Veuillez donner le type de reunion")
	private String type;

	@Column(name = "START_TIME", columnDefinition="TIMESTAMP")	
	@NotNull(message="Veuillez donner l'horaire de début")
	@DateTimeFormat(pattern="dd-MM-yyyy '-' HH:mm")
	private LocalDateTime startTime;
	
	@Column(name = "END_TIME", columnDefinition="TIMESTAMP")	
	@NotNull(message="Veuillez donner l'horaire de fin")
	@DateTimeFormat(pattern="dd-MM-yyyy '-' HH:mm")
	private LocalDateTime endTime;

	@Column(name = "ATTENDANTS_NUMBER")
	@Digits(integer = 2, fraction = 0, message="Veuillez donner le nombre de participants")
	@Min(2)
	private int attendantsNumber;
	
	@Column(name = "ACTIVATED")
	private boolean activated = false;
	
	@Column(name = "ROOM_SELECTED")
	private String roomSelected;

	public Meeting(MeetingType type, LocalDateTime  startTime, LocalDateTime  endTime, int attendantsNumber) {
		this.type = type.name();
		this.startTime = startTime;
		this.endTime = endTime;
		this.setAttendantsNumber(attendantsNumber);
	}
	
	public String getActivated() {
		return this.activated? "Planifiée": "Non planifiée";
	}

}

