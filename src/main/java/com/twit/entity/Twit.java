package com.twit.entity;

import java.util.Date;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Twit {
	
	@GeneratedValue
	@Id
	private int id;
	
	@Builder.Default
	private int likes = 0;
	
	@Builder.Default
	private int disLikes = 0;
	
	@ManyToOne
	private User user;
	
	
	private String twit;
	
	@Builder.Default
	private Date createAt = new Date();
	
	@Builder.Default
	@JsonIgnore
	private Date deletedAt = null;
	
	@Builder.Default
	@JsonIgnore
	private Date editedAt = null;
	
	@Builder.Default
	@Enumerated(EnumType.STRING)
	@JsonIgnore
	private TwitStatus status = TwitStatus.ENABLED;
	
	@Enumerated(EnumType.STRING)
	private TwitCategory category;
	
	public enum TwitCategory {
		TECHNOLOGY,
		SCIENCE,
		DATING,
		SCHOOL,
		HOUSEHOLD,
		FOOD,
		WELLBEING
	}
	
	public enum TwitStatus {
		ENABLED,DISABLED
	}

}
