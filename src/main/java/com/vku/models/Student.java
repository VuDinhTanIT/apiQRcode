package com.vku.models;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "student")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class Student {

	@Id
	@Column(name="id")
	private String id;
	@Column(name="student_code")
	private String studentCode;
	
	@Column(columnDefinition = "VARCHAR(255)")
	private String cccd;
	@Column(columnDefinition = "VARCHAR(60)")
	private String name;
	@Column(columnDefinition = "VARCHAR(60)")
	private String email;
	@Column(columnDefinition = "VARCHAR(20)")
	private String phoneNumber;
	@Column(columnDefinition = "VARCHAR(255)")
	private String address;
	@Column(columnDefinition = "VARCHAR(10)")
	private String className;
	@Column(columnDefinition = "VARCHAR(255)")
	private String groupCodes;
	private String QRCodeImage;
	private boolean status;

	@Column(name = "create_time", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false, nullable = false)
	private Timestamp createTime;
	
	@Column(name = "update_time", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP", updatable = true, nullable = false)
	private Timestamp updateTime;

}
