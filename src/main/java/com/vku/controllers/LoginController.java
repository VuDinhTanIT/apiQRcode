package com.vku.controllers;

import com.vku.dtos.LoginRequest;
import com.vku.models.Log;
import com.vku.models.Officer;
import com.vku.models.Student;
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

	@PostMapping("/login")
	public ResponseEntity<Object> login(@RequestBody LoginRequest loginRequest) {
		String username = loginRequest.getUsername();
		String password = loginRequest.getPassword();

		Object user = loginService.login(username, password);
		Log log = new Log();
		if (user != null) {
			log.setActor(username);
			log.setLog("Đăng nhập thành công: " + user.getClass() + " - " + username);
			logService.wirteLog(log);
		} else {
			log.setLog("Đăng nhập thất bại với username: " + username + " - pass: " + password);
			logService.wirteLog(log);
		}
		if (user instanceof Officer) {
			// Đăng nhập thành công với vai trò Officer
			return ResponseEntity.ok().body(user);
		} else if (user instanceof Student) {
			// Đăng nhập thành công với vai trò Student
			return ResponseEntity.ok().body(user);
		} else {
			// Đăng nhập không thành công
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed");
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
