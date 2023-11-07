package com.vku.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfficerDTO {

	private int id;
	private String officerCode;
	private String name;
	private String email;
	private String cccd;

	private PositionDTO positionCode;
	private String degree;
	private String salary;
	private String allowance;
	private String QRCodeImage;
//	@Column(name = "create_time", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false, nullable = false)
//	private Timestamp createTime;
//	
//	@Column(name = "update_time", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP", updatable = true, nullable = false)
//	private Timestamp updateTime;
	

}
