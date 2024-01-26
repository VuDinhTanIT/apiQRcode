package com.vku.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentCourseInfoDTO {

	private String studentCode;
	private String nameClass;
	private String nameStudent;
	private String courseName;
	private Long courseId;


}
