package com.ess.meetingplanner.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "EXTRA_FEATURE")
public class ExtraFeature {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Long id;

	@Column(name = "FEATURE_NAME")
	private String featureName;
	
	@Column(name = "FEATURE")
	private Feature feature;

	@Column(name = "QUANTITY")
	private int quantity;
	
	@Column(name = "AVAILABLE_QUANTITY")
	private int availableQuantity;
	
	@Transient
	private LocalDateTime startTime;
	
	@Transient
	private LocalDateTime endTime;


	public ExtraFeature(String featureName, int quantity) {
		this.featureName = featureName;
		this.feature = Feature.valueOf(featureName);
		this.quantity = quantity;
		this.availableQuantity = quantity;
	}
	
	public Feature getFeature() {
		return this.feature;
	}
	
	public boolean equals(Object obj) {
		if(obj == null) return false;
		
		if (this == obj ) {
			return true;
		}
		if((obj instanceof ExtraFeature) && (((ExtraFeature)obj).getFeature().equals(this.feature))) return true;
		
		if((obj instanceof Feature) && (obj.equals(this.feature))) return true;
		
		if((obj instanceof Feature) && (((Feature)obj).name()
				.equalsIgnoreCase(this.
						feature.
						name()))) return true;
		
		return false;
	}
	

}

