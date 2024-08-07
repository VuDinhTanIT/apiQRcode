package com.vku.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vku.models.Course;
import com.vku.repositories.CourseRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CourseService {
	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	SchoolYear_SemesterService schoolYear_SemesterService;

	public List<Course> getAllCourses() {
		return courseRepository.findAll();
	}

	public Course getCourseById(Long id) {
		return courseRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
	}

	public Course createCourse(Course course) {
		course.setId(null);
		return courseRepository.save(course);
	}

	public Course updateCourse(Course course) {
		return courseRepository.save(course);
	}

	public void deleteCourse(Long id) {
		courseRepository.deleteById(id);
	}

	public List<Course> getCoursesByOfficerId(int officerId) {
		// TODO Auto-generated method stub
		return courseRepository.findByOfficerId(officerId);
	}

	// Lấy ra những học phần dạy ngày hôm nay
	public List<Course> getTodayTeachingScheduleByLecture(int officerId) {
		// TODO Auto-generated method stub
		int dayOfWeek = schoolYear_SemesterService.getDayOfWeekCurrent();
		String dayOfWeekStr = String.valueOf(dayOfWeek);
//		dayOfWeekStr = "3";
		List<Course> courses = courseRepository.findByOfficerIdAndDayOfWeek(officerId, dayOfWeekStr);
//		System.out.println("courseSer - course: " + courses);
		List<Course> todayTeachingCourses = getTodayTeachingSchedule(courses);

//		

//	    System.out.println("todayTeachingC + " + todayTeachingCourses);
		return todayTeachingCourses;

	}
	public List<Course> getTodayTeachingScheduleByStudent(String studentCode) {
		// TODO Auto-generated method stub
		int dayOfWeek = schoolYear_SemesterService.getDayOfWeekCurrent();
		String dayOfWeekStr = String.valueOf(dayOfWeek);
		dayOfWeekStr = "3";
		List<Course> courses = getCoursesByStudentCodeAndDayOfweek(studentCode, dayOfWeekStr);
//		System.out.println("courseSer - course: " + courses);
		
		List<Course> todayTeachingCourses = getTodayTeachingSchedule(courses);

//		

//	    System.out.println("todayTeachingC + " + todayTeachingCourses);
		return todayTeachingCourses;

	}

	public List<Course> getCourses(String studentCode) {
		return courseRepository.getCoursesByStudentCode(studentCode);
	}
	public List<Course> getCoursesByStudentCodeAndDayOfweek(String studentCode, String dayOfWeek) {
		return courseRepository.getCoursesByStudentCodeAndDayOfWeek(studentCode, dayOfWeek);
	}

	public List<Course> getTodayTeachingSchedule(List<Course> courses) {
		// TODO Auto-generated method stub
		int currentWeek = schoolYear_SemesterService.getWeekOfCurrentSchoolYear();
//		System.out.println("courseSer - course: " + courses);
		List<Course> todayTeachingCourses = new ArrayList<>();
//		System.out.println(" current week: " + currentWeek) ;
		for (Course course : courses) {
			String[] weeks = course.getWeek().split(",");
			for (String week : weeks) {
				if (week.contains("->")) {
					String[] range = week.split("->");
					System.out.println("split week contains: " + range);
					int startWeek = Integer.parseInt(range[0].trim());
					int endWeek = Integer.parseInt(range[1].trim());
					if (currentWeek >= startWeek && currentWeek <= endWeek) {
						todayTeachingCourses.add(course);
						break;
					}
				} else {
					int singleWeek = Integer.parseInt(week.trim());
//	                System.out.println("Single week: " + singleWeek);
					if (currentWeek == singleWeek) {
						todayTeachingCourses.add(course);
						break;
					}
				}
			}
		}
//	    System.out.println("todayTeachingC + " + todayTeachingCourses);
		return todayTeachingCourses;

	}

}