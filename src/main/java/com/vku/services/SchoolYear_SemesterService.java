package com.vku.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.vku.models.SchoolYear_Semester;
import com.vku.repositories.SchoolYear_SemesterRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;

@Service
public class SchoolYear_SemesterService {

	@Autowired
	private SchoolYear_SemesterRepository schoolYear_SemesterRepository;

	public List<SchoolYear_Semester> getAllSchoolYear_Semesters() {
		return schoolYear_SemesterRepository.findAll();
	}
//    public List<SchoolYear_Semester> getAllSchoolYear_SemestersByOrderByUpdateTimeDesc() {
//        return schoolYear_SemesterRepository.findAllByOrderByUpdateTime();
//    }

	public SchoolYear_Semester getSchoolYear_SemesterById(Integer id) {
		return schoolYear_SemesterRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("SchoolYear_Semester not found: " + id));
	}
//    public SchoolYear_Semester getEvnetByIdStr(String id) {
//    	return getSchoolYear_SemesterById(Integer.parseInt(id));
//    }

	public SchoolYear_Semester createSchoolYear_Semester(SchoolYear_Semester schoolYear_Semester) {
		return schoolYear_SemesterRepository.save(schoolYear_Semester);
	}

	public SchoolYear_Semester updateSchoolYear_Semester(SchoolYear_Semester schoolYear_Semester) {
		return schoolYear_SemesterRepository.save(schoolYear_Semester);
	}

	public void deleteSchoolYear_Semester(Integer id) {
		schoolYear_SemesterRepository.deleteById(id);
	}

	public int getWeekOfCurrentSchoolYear() {
		final int MONDAY = 1;
		// Lấy thời gian bắt đầu năm học hiện hành
		SchoolYear_Semester schoolYear_Semester = schoolYear_SemesterRepository.findByCurrent(true).get(0);
		String startDateStr = "";

		if (schoolYear_Semester != null)
			startDateStr = schoolYear_Semester.getSchoolYearStartDate();
		else
			return -1;
		System.out.println("SchoolSemesterService: startTime: " + startDateStr + " - " + schoolYear_Semester);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

//		LocalDateTime currentDateNotFormat = LocalDateTime.now();
//		LocalDate currentDate = LocalDate.parse(currentDateNotFormat.format(formatter), formatter);

		LocalDate startDate = LocalDate.parse(startDateStr, formatter);
		LocalDate currentDate = LocalDate.parse("29/05/2024", formatter);
		LocalDate today = LocalDate.now();
		if (startDate.getDayOfWeek().getValue() != MONDAY) {
			return -1; // Ngày bắt đầu không phải thứ 2
		}

		long daysSinceStart = startDate.until(currentDate, ChronoField.DAY_OF_YEAR.getBaseUnit());
		if (daysSinceStart < 0) {
			return 0; // Ngày hiện tại trước ngày bắt đầu năm học
		}

		return (int) (daysSinceStart / 7) + 1;
	}

	public int getDayOfWeekCurrent() {

		LocalDateTime currentDateNotFormat = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate currentDate = LocalDate.parse(currentDateNotFormat.format(formatter), formatter);
//		LocalDate currentDate = LocalDate.parse("25/05/2024", formatter);

		return currentDate.getDayOfWeek().getValue();
	}
	

}
