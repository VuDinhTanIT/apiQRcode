package com.vku.dtos;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vku.models.Curriculum;
import com.vku.models.Khoa;
import com.vku.models.Majors;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class StudentDTO {

	private String studentCode;
	
	private String cccd;
	private String name;
	private Majors majors;

	private Khoa khoa;

	private Curriculum curriculum;
	private String method;
	private String email;
	private String phoneNumber;
	private String address;
	private String className;
	private String groupCodes;
	private String QRCodeImage;
//	@Column(name = "create_time", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false, nullable = false)
//	private Timestamp createTime;
//	
//	@Column(name = "update_time", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP", updatable = true, nullable = false)
//	private Timestamp updateTime;

}
