package com.vku.controllers.officers;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vku.controllers.QRcode;
import com.vku.dtos.AttendanceQRResponseDTO;
import com.vku.dtos.ErrorResponse;
import com.vku.dtos.StudentAttendanceForCourse;
import com.vku.models.AttendanceSheet;
import com.vku.models.Course;
import com.vku.models.DetailAttendance;
import com.vku.models.Guest;
import com.vku.models.Log;
import com.vku.models.Officer;
import com.vku.models.Student;
import com.vku.models.Student_Course;
import com.vku.services.AttendanceSheetService;
import com.vku.services.CourseService;
import com.vku.services.DetailAttendanceService;
import com.vku.services.GuestService;
import com.vku.services.OfficerService;
import com.vku.services.StudentService;
import com.vku.services.Student_CourseService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("api/officer")
public class OfficerController {
	@Autowired
	private Student_CourseService student_CourseService;
	@Autowired
	private QRcode qrCode;
	@Autowired
	private AttendanceSheetService attendanceSheetService;
	@Autowired
	private DetailAttendanceService detailAttendanceService;
	@Autowired
	private OfficerService officerService;
	@Autowired
	private GuestService guestService;
	@Autowired
	private CourseService courseService;
	@Autowired
	private StudentService studentService;

	@GetMapping("/{id}")
	public ResponseEntity<Officer> getOfficerById(@PathVariable("id") int officerId) {
		return ResponseEntity.ok(officerService.getOfficerById(officerId));
	}

	@GetMapping("/getStudentAttendanceForCourseId/{id}")
	public ResponseEntity<List<StudentAttendanceForCourse>> getStudentAttendanceForCourseId(
			@PathVariable("id") int courseId) {
//		List<Student_Course> listStudentCourses = student_CourseService.getStudentCourseWithCourseId((long) courseId);
		List<StudentAttendanceForCourse> studentAttendanceForCourseList = new ArrayList<>();
		studentAttendanceForCourseList = student_CourseService.getInfoStudentAttByCourseId((long) courseId);
//		for (Student_Course student_Course : listStudentCourses) {
//			StudentAttendanceForCourse st = new StudentAttendanceForCourse();
//			Student student = studentService.getStudentByStudentCode(student_Course.getStudentCode());
//			st.setStudentCode(student_Course.getStudentCode());
//			st.setNameClass(student.getClassName());
//			st.setNameStudent(student.getName());
//			st.setPresent(student_Course.isExtraSheet());
//			st.setAbsenceCount(0);
//			studentAttendanceForCourseList.add(st);
//		}
		System.out.println(" list attSt" + studentAttendanceForCourseList);
		return ResponseEntity.ok(studentAttendanceForCourseList);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateOfficer(@PathVariable("id") int officerId, @RequestBody Officer updatedOfficer) {
//		Log log = new Log();
		try {
			Officer officer = officerService.getOfficerById(officerId);
//			if (officer != null) {
//	              log.setActor();
//				log.setLog("Sửa officer với id: " + officerId + " - mã cán bộ:" + officer.getOfficerCode() + " - "
//						+ officer.getName());
//				logService.wirteLog(log);
//			}
			return new ResponseEntity<>(officerService.updateOfficer(officerId, updatedOfficer), HttpStatus.OK);

		} catch (NoSuchElementException e) {
			ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), 404);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		} catch (Exception e) {
			ErrorResponse errorResponse = new ErrorResponse("Đã xảy ra lỗi", 500);
//			log.setLog("Sửa thất bại với id: " + officerId + " - " + e.getMessage());
//			logService.wirteLog(log);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
		}
	}

	// Cập nhập cho điểm danh phụ với courseId thành true
	@PutMapping("/extraSheet/{courseId}")
	public ResponseEntity<?> setExtraSheetFalseWithCourseId(@PathVariable("courseId") Long courseId) {
		try {
			student_CourseService.setExtraSheetWithCourseId(courseId, false);
			return ResponseEntity.ok(true);
		} catch (NoSuchElementException e) {
			return ResponseEntity.ok(false);
		} catch (Exception e) {
			return ResponseEntity.ok(false);
		}
	}

// Quét mã QR khách đên
	@PostMapping("/scanQRForGuest")
	public ResponseEntity<?> scanQRcode(@RequestParam("QRcode") String QRcode) {
		try {
			if (!qrCode.isQRCodeValid(QRcode)) {
				throw new Exception("Time out");
			}
			String guestId = QRcode.trim().split("\\|")[0];
			Guest guest = guestService.getGuestById(Integer.parseInt(guestId));
			return ResponseEntity.ok(true);
		} catch (NoSuchElementException e) {
			e.getMessage();
			return ResponseEntity.ok(false);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(false);
		}
	}

	// Lấy danh sách lớp học phần theo id giảng viên
	@GetMapping(value = { "/getCoursesByOfficerId/{officerId}", "course/{officerId}" })
	public ResponseEntity<List<Course>> getCourseById(@PathVariable("officerId") int officerId) {
		List<Course> listCourses = courseService.getCoursesByOfficerId(officerId);
		System.out.println("Officer C - listCourses: " + listCourses);
		if (listCourses == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(listCourses);
	}

	// Lấy thông tin SV từ mã SV
	@GetMapping("/getStudentByStudentCode/{studentCode}")
	public ResponseEntity<Student> getStudentByStudentCode(@PathVariable("studentCode") String studentCode) {

		return ResponseEntity.ok(null);
	}

	@PostMapping("/attendanceQR")
	public ResponseEntity<?> createSheetQRcode(@RequestParam("courseId") int courseIdI,
			@RequestParam("lessonContent") String lessonContent, HttpServletRequest request) throws Exception {
		Long courseId = (long) courseIdI;
		System.out.println("Officer Controler: CourseId =  " + courseId);
		LocalDateTime attendanceDate = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		AttendanceSheet attendanceSheet = new AttendanceSheet();
		attendanceSheet.setId(null);
		attendanceSheet.setCourseId(courseId);
		attendanceSheet.setLessonContent(lessonContent);
		attendanceSheet.setTeachDate(attendanceDate.format(formatter));
		attendanceSheet.setStatus(true);
		try {
//			setExtraSheetFalseWithCourseId(courseId);
			student_CourseService.setExtraSheetWithCourseId(courseId, false);
			AttendanceSheet attendanceSheetCreated = attendanceSheetService.createAttendanceSheet(attendanceSheet);
			String QRcodeInfo = courseId + "|" + attendanceDate.format(formatter);
			int expirationHours = 2;
			String attendanceQRImageBase64 = qrCode.generateQRcodeWithExpirationDays(QRcodeInfo, request,
					"attendanceQR", expirationHours);
			AttendanceQRResponseDTO responseDTO = new AttendanceQRResponseDTO();
			responseDTO.setAttendanceId(attendanceSheetCreated.getId());
			responseDTO.setAttendanceQRImageBase64(attendanceQRImageBase64);
//        Student_Course createdStudent_Course = student_CourseService.createStudent_Course(Student_Course);
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("AttendanceQR: " + e.getMessage());
			return ResponseEntity.ok(false);

		}
	}

	// Điểm danh bình thường
	@PostMapping("/attendanceNormal")
	public ResponseEntity<?> createNormalAttendance(@RequestParam("courseId") Long courseId,
			@RequestParam("lessonContent") String lessonContent, HttpServletRequest request) throws Exception {

		try {
			LocalDateTime attendanceDate = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			Boolean checkAttendanced = attendanceSheetService.checkAttendancedToday(Timestamp.valueOf(attendanceDate),
					courseId);
			System.out.println("checkAttendance: " + checkAttendanced);
			if (checkAttendanced) {
				new Exception("Đã tạo điểm danh rồi");
			} else {

				AttendanceSheet attendanceSheet = new AttendanceSheet();
				attendanceSheet.setId(null);
				attendanceSheet.setCourseId(courseId);
				attendanceSheet.setLessonContent(lessonContent);
				attendanceSheet.setTeachDate(attendanceDate.format(formatter));
				attendanceSheet.setStatus(true);

				setExtraSheetFalseWithCourseId(courseId);
				student_CourseService.setExtraSheetWithCourseId(courseId, true);
				AttendanceSheet attendanceSheetCreated = attendanceSheetService.createAttendanceSheet(attendanceSheet);
//			Student_Course createdStudent_Course = student_CourseService.createStudent_Course(Student_Course);

				return new ResponseEntity<>(attendanceSheetCreated.getId(), HttpStatus.OK);
//			return ResponseEntity.ok(true);
			}
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());

		}
		return null;
	}

//Note: Khi điểm danh thủ công từng SV đã vào rồi, khi lưu điểm danh sẽ chèn vào nữa => Lưu trước khi điểm danh thủ công
	@PostMapping("/saveAttendance")
	public ResponseEntity<?> saveAttendanceWithAttendanceId(@RequestParam("courseId") Long courseId) {
		try {
//			AttendanceSheet attendanceSheet = attendanceSheetService.getAttendanceSheetById(attendanceId);
//			Lẫy những studentCourse có ExtraSheet = false( những thằng vắng thì lấy)
			List<Student_Course> listStudent_CoursesWithCoureId = student_CourseService
					.getByExtraSheetAndCourseId(false, courseId);
			System.out.println("studentList: " + listStudent_CoursesWithCoureId);

			for (Student_Course student_Course : listStudent_CoursesWithCoureId) {
				if (!student_Course.isExtraSheet()) {
					DetailAttendance detailAttendance = new DetailAttendance();
					detailAttendance.setStudentCode(student_Course.getStudentCode());
					detailAttendance.setCourseId(courseId);
					detailAttendanceService.createDetailAttendance(detailAttendance);
//					student_CourseService.
					System.out.println("studentController:  att - " + detailAttendance);
				}
			}
			return ResponseEntity.ok(true);
		} catch (NoSuchElementException e) {
			e.getMessage();
			return ResponseEntity.ok(false);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(false);
		}
	}

//  Cần xxem xét lại vì save đã bao quét rồi, không cần nhiều ... 
	@PostMapping("/updateAttendance")
	public ResponseEntity<?> updateAttendanceWithAttendanceId(@RequestParam("courseId") Long courseId) {
		try {
//			Lẫy những studentCourse với courseId và có updateTime lớn hơn thèn cập nhâp theo attendanceId mới nhất của bảng DetailAttendance
//			Lấy những đứa mà quét mã QR sau khi đã lưu
			List<Student_Course> listStudent_CoursesWithCoureId = student_CourseService
					.getByCourseIdWithUpdateTimeThanT(courseId,
							detailAttendanceService.getFirstByCourseIdWithLatestUpdateTime(courseId).getUpdateTime());
			System.out.println("studentList: " + listStudent_CoursesWithCoureId);

			//
			for (Student_Course student_Course : listStudent_CoursesWithCoureId) {
				// Nếu mà sv đó vắng thì chèn vào bảng điểm danh chi tiết
				if (!student_Course.isExtraSheet()) {
					DetailAttendance detailAttendance = new DetailAttendance();
//					detailAttendance.setId(null);
					detailAttendance.setStudentCode(student_Course.getStudentCode());
					detailAttendance.setCourseId(courseId);
//					detailAttendanceService.createDetailAttendance(detailAttendance);
//					student_CourseService.
					System.out.println("studentController:  att - " + detailAttendance);
				} else {
					// Điểm danh lại: Nếu mà tìm có trong bảng detailAtt thì xóa đi
					detailAttendanceService.deleteByAttendanceIdAndStudentCodeAndDateOff(courseId,
							student_Course.getStudentCode(), student_Course.getUpdateTime());
					System.out.println("xóa: " + student_Course);

				}
			}
//			setExtraSheetFalseWithCourseId(attendanceSheet.getCourseId());
			return ResponseEntity.ok(true);
		} catch (NoSuchElementException e) {
			e.getMessage();
			return ResponseEntity.ok(false);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(false);
		}
	}

//Gọi hàm xóa SV với courseId và ngày dạy bảng điểm danh chi tiết, nếu check đúng( có sv trong bảng-> SV vắng)
//	thì SV đó được xóa khỏi bảng và cập nhập lại trạng thái có mặt cho SV.
//	nếu check false, thì thực hiện thêm SV vào bảng và cập nhập lại trạng thái thành vắng cho SV.
	@PutMapping("/attendancePerStudent")
	public ResponseEntity<?> attendancePerStudent(@RequestParam("courseId") Long courseId,
			@RequestParam("studentCode") String studentCode) {
		try {
//			AttendanceSheet attendanceSheet = attendanceSheetService.getAttendanceSheetById(attendanceId);
			// Lấy thời gian thực hiện bây giờ
			Timestamp dayOff = Timestamp.valueOf(LocalDateTime.now()); //
//			System.out.println("OfficerCon - DayOff: " + dayOff);
//			Xóa SV trong bảng Điểm danh chi tiết, trả về true nếu có SV đc xóa, false nếu ko có SV nào bị xóa
			boolean bool = detailAttendanceService.deleteByAttendanceIdAndStudentCodeAndDateOff(courseId, studentCode,
					dayOff);
			if (!bool) {
//				THực hiện thêm SV vào bảng điểm danh chi tiết và cập nhập
				DetailAttendance detailAttendance = new DetailAttendance();
//				detailAttendance.setId(null);
				detailAttendance.setStudentCode(studentCode);
				detailAttendance.setCourseId(courseId);
				detailAttendanceService.createDetailAttendance(detailAttendance);
//				CẬp nhập thành trạng thái vắng cho SV
				student_CourseService.setExtraSheetWithCourseIdAndStudentCode(courseId, studentCode, false);

//				student_CourseService.
//				System.out.println("studentController:  att - " + detailAttendance);
			} else {
//				CẬp nhập trạng thái có mặt cho SV
				student_CourseService.setExtraSheetWithCourseIdAndStudentCode(courseId, studentCode, true);
			}

			return ResponseEntity.ok(true);
		} catch (NoSuchElementException e) {
			e.getMessage();
			return ResponseEntity.ok(false);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(false);
		}
	}

}
