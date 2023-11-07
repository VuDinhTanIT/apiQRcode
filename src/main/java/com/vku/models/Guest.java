package com.vku.models;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
//@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class Guest {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private int id;
	private String cccd;
	private String email;
	private String phoneNumber;
	private String organization;
	private String purpose;
	private String licensePlate;
	private String QRCodeImage;
	private boolean status;

	private String arrivlDate;
	@Column(name = "create_time", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false, nullable = false)
	private Timestamp createTime;
	
	@Column(name = "update_time", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP", updatable = true, nullable = false)
	private Timestamp updateTime;

	
	
}
