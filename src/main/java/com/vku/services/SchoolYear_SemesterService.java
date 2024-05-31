package com.vku.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.vku.models.SchoolYear_Semester;
import com.vku.repositories.SchoolYear_SemesterRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.List;
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
    public  int getWeekOfCurrentSchoolYear() {
    	
    	SchoolYear_Semester schoolYear_Semester = getSchoolYear_SemesterById(1);
    	String startDateStr = "";
    	LocalDateTime currentDateNotFormat = LocalDateTime.now();
		if(schoolYear_Semester != null )
    		startDateStr = schoolYear_Semester.getSchoolYearStartDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LocalDate startDate = LocalDate.parse(startDateStr, formatter);
//		LocalDate currentDate = LocalDate.parse(currentDateNotFormat.format(formatter), formatter);
        LocalDate currentDate = LocalDate.parse("25/05/2024", formatter);
//        LocalDate currentDate = LocalDate.now().format(formatter);

        if (startDate.getDayOfWeek().getValue() != 1) { // Ngày bắt đầu không phải thứ 2
            return -1;
        }

        long daysSinceStart = startDate.until(currentDate, ChronoField.DAY_OF_YEAR.getBaseUnit());
        if (daysSinceStart < 0) {
            return 0; // Ngày hiện tại trước ngày bắt đầu năm học
        }	

        int weekNumber = (int) (daysSinceStart / 7) + 1;
        System.out.println(weekNumber);
//        if (currentDate.getDayOfWeek().getValue() >= startDate.getDayOfWeek().getValue()) {
//            weekNumber++; // Tuần đầu tiên chỉ từ ngày bắt đầu đến CN
//        }
//        System.out.println("Sau if: week" + weekNumber);

        return weekNumber;
    }
}
