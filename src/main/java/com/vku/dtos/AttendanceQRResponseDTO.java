package com.vku.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceQRResponseDTO {
	private Long attendanceId;
    private String attendanceQRImageBase64;
}
