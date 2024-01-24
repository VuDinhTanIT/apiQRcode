package com.vku.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentAttendanceForCourse {

	private String studentCode;
	private String nameClass;
	private String nameStudent;
	private boolean present;
	private float absenceCount;

}
