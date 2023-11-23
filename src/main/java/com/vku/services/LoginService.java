package com.vku.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vku.models.Officer;
import com.vku.models.Student;

@Service
public class LoginService {
	 private static final String OFFICER = "officer";
	 private static final String STUDENT = "student";
	@Autowired
	private StudentService studentService;
	@Autowired
	private OfficerService officerService;
	
	public Object login(String username, String password) {
		// Mã hóa password để so sánh với password đã lưu trong cơ sở dữ liệu
//		String password = PasswordEncryptor.encryptPassword(pass);
//		System.out.println("password: " + password);
//		System.out.println("getTypeUser: " + getUserType(username));
		if(getUserType(username).equalsIgnoreCase(OFFICER)) {
			return loginOfficer(username, password);
		}else {
			
			return loginStudent(username, password);
		}
	}
	
	public Officer loginOfficer(String username, String password) {
	    // Kiểm tra xem username có tồn tại trong lớp Officer hay không	
	    Officer officer = officerService.findByOfficerCode(username);
	    String decodedPass = PasswordEncryptor.decryptPassword(officer.getPassword());

	    return decodedPass.equals(password)  ? officer : null;
	}
	public Officer loginAdmin(String username, String password) {
	    // Kiểm tra xem username có tồn tại trong lớp Officer hay không	
	    Officer officer = officerService.findByOfficerCode(username);
	    if(officer == null ) {
	    	return null;
	    }
	    String decodedPass = PasswordEncryptor.decryptPassword(officer.getPassword());

	    return decodedPass.equals(password)  ? officer : null;
	}
	public Student loginStudent(String username, String password) {
	    // Kiểm tra xem username có tồn tại trong lớp Student hay không	
	    Student student = studentService.findByStudentCode(username);
	    if(student == null ) {
	    	return null;
	    }
	    String decodedPass = PasswordEncryptor.decryptPassword(student.getPassword());
//	    System.out.println("decodedpass: " + decodedPass + "- " +decodedPass.equals(password));
	    return decodedPass.equals(password) ? student : null;
	}

	public String getUserType(String username) {
	    if (username.contains("gv") || username.contains("cb")) {
	        return OFFICER;
	    } else {
	        return STUDENT;
	    }
	}

	
}
