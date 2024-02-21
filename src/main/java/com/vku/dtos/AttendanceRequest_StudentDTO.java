package com.vku.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceRequest_StudentDTO {
	private String studentCode;
    private String qrCodeInfo;
}
