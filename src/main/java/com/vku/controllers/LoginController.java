package com.vku.controllers;

import com.vku.dtos.LoginRequest;
import com.vku.models.Guest;
import com.vku.models.Log;
import com.vku.models.Officer;
import com.vku.models.Student;
import com.vku.services.GuestService;
import com.vku.services.LogService;
import com.vku.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LoginController {
	@Autowired
	private LoginService loginService;
	@Autowired
	private LogService logService;
	@Autowired
	private GuestService guestService;

//	@PostMapping("/login")
//	public ResponseEntity<Object> login(@RequestBody LoginRequest loginRequest) {
//		String username = loginRequest.getUsername();
//		String password = loginRequest.getPassword();
//
//		Object user = loginService.login(username, password);
//		Log log = new Log();
//		if (user != null) {
//			log.setActor(username);
//			log.setLog("Đăng nhập thành công: " + user.getClass() + " - " + username);
//			logService.wirteLog(log);
//		} else {
//			log.setLog("Đăng nhập thất bại với username: " + username + " - pass: " + password);
//			logService.wirteLog(log);
//		}
//		if (user instanceof Officer) {
//			// Đăng nhập thành công với vai trò Officer
//			return ResponseEntity.ok().body(user);
//		} else if (user instanceof Student) {
//			// Đăng nhập thành công với vai trò Student
//			return ResponseEntity.ok().body(user);
//		} else {
//			// Đăng nhập không thành công
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed");
//		}
//	}
	@PostMapping("/guests/login")
	public ResponseEntity<Object> loginGuest(@RequestBody LoginRequest loginRequest) {
		String userName = loginRequest.getUsername();
		String phoneNumber = loginRequest.getPassword();

		Guest user = guestService.loginByPhoneNumberAndUserName(phoneNumber, userName);
		Log log = new Log();
		if (user != null) {
			log.setActor(user.getName());
			log.setLog("Khách đăng nhập thành công: " + userName + " - " + phoneNumber);
			logService.wirteLog(log);
			return ResponseEntity.ok().body(user);
		} else {
//			log.setLog("Đăng nhập thất bại với username: " + username + " - pass: " + password);
//			logService.wirteLog(log);
			return ResponseEntity.status(HttpStatus.OK).body(null);
		}
	}

	@PostMapping("/officers/login")
	public ResponseEntity<Object> loginOfficer(@RequestBody LoginRequest loginRequest) {
		String username = loginRequest.getUsername();
		String password = loginRequest.getPassword();

		Officer user = loginService.loginOfficer(username, password);
		Log log = new Log();
		if (user != null) {
			log.setActor(username);
			log.setLog("Đăng nhập thành công: " + user.getName() + " - " + user.getOfficerCode());
			logService.wirteLog(log);
			return ResponseEntity.ok().body(user);
		} else {
			log.setLog("Đăng nhập thất bại với username: " + username + " - pass: " + password);
			logService.wirteLog(log);
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
	}

	@PostMapping("/students/login")
	public ResponseEntity<Object> loginStudent(@RequestBody LoginRequest loginRequest) {
		String username = loginRequest.getUsername();
		String password = loginRequest.getPassword();

		Student user = loginService.loginStudent(username, password);
		Log log = new Log();
		if (user != null) {
			log.setActor(username);
			log.setLog("Đăng nhập thành công: " + user.getName() + " - " + user.getStudentCode());
			logService.wirteLog(log);
			return ResponseEntity.ok().body(user);
		} else {
			log.setLog("Đăng nhập thất bại với username: " + username + " - pass: " + password);
			logService.wirteLog(log);
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
	}

	@PostMapping("admin/login-web")
	public ResponseEntity<Object> loginAdmin(@RequestBody LoginRequest loginRequest) {
		String username = loginRequest.getUsername();
		String password = loginRequest.getPassword();

		Object user = loginService.login(username, password);
		Log log = new Log();
		if (user != null) {
			log.setActor(username);
			log.setLog("Đăng nhập thành công: " + user.getClass() + " - " + ((Officer) user).getOfficerCode());
			logService.wirteLog(log);
		} else {
			log.setLog("Đăng nhập thất bại với username: " + username + " - pass: " + password);
			logService.wirteLog(log);
		}
		return ResponseEntity.ok().body(user);

	}
}
