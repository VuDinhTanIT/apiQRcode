package com.vku.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuestDTO {

	private int id;
	private String cccd;
	private String email;
	private String phoneNumber;
	private String organization;
	private String purpose;
	private String licensePlate;
	private String QRCodeImage;
	private String arrivlDate;
//	@Column(name = "create_time", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false, nullable = false)
//	private Timestamp createTime;
//	
//	@Column(name = "update_time", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP", updatable = true, nullable = false)
//	private Timestamp updateTime;

	
	
}
