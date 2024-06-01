package com.vku.models;

import java.sql.Timestamp;


import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "course_code")
	private String courseCode;
	private String name;
	private String week;
	private String room;
//	Thứ trong tuần
	private String dayOfWeek;
//	@Nullable
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "school_year_semester")
	private SchoolYear_Semester schoolYearSemester;
	private int semester;
//	Tiết 1-10
	private String period;
	private boolean status;	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lecturer_id")
	private Officer officer;

	@Column(name = "create_time", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false, nullable = false)
	private Timestamp createTime;
	
	@Column(name = "update_time", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP", updatable = true, nullable = false)
	private Timestamp updateTime;
	
}
