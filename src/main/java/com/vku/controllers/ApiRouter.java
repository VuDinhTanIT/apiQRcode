package com.vku.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiRouter {
	@Autowired
	GuestController guestController;
	
	@RequestMapping("/Guest")
	public GuestController guestController() {
		return guestController;
	}
}
