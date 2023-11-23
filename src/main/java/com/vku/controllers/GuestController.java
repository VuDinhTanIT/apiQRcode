package com.vku.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vku.models.Guest;
import com.vku.services.GuestService;

import jakarta.servlet.http.HttpServletRequest;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/guests")
public class GuestController {
	@Autowired
	private GuestService guestService;
	@Autowired
	private QRcode qRcode;
	
	@GetMapping
	public ResponseEntity<List<Guest>> getAllGuests() {
		List<Guest> guests = guestService.getAllGuests();
		return new ResponseEntity<>(guests, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Guest> getGuestById(@PathVariable("id") int id) {
		Guest guest = guestService.getGuestById(id);
		if (guest == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(guest, HttpStatus.OK);
	}

	@GetMapping("/test")
	public String test(HttpServletRequest request) {
//        Guest guest = guestService.getGuestById(id);
//        if (guest == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        return new ResponseEntity<>(guest, HttpStatus.OK);
		System.out.println("-----------");
		System.out.println(request.getContextPath());
		System.out.println(request.getRequestURI());
		System.out.println(request.getServletPath().toString());
		System.out.println(request.getServletContext().toString());

		return null;
	}

//    @PostMapping
//    public ResponseEntity<Guest> createGuest(@RequestBody Guest guest) {
//        Guest createdGuest = guestService.createGuest(guest);
//        
//        return new ResponseEntity<>(createdGuest, HttpStatus.CREATED);
//    }
	// tạo mã QR cho khách mời
	@PutMapping("/createQR")
	public ResponseEntity<Guest> generateQRcodeForGuest(@RequestBody Guest guest, HttpServletRequest request) throws Exception {
//    	HttpServletRequest request = null;
//		Guest createdGuest = guestService.createGuest(guest);
		// Trả về link call api gọi đến guest vừa tạo

		String content = String.valueOf(guest.getId());

		String qrCodeImageBase64 = qRcode.generateQRcodeWithExpirationDays(content, request, guest.getCccd(), 1);
		guest.setQRCodeImage(qrCodeImageBase64);
//		guestService.updateGuest(guest);
//        return new ResponseEntity<>(createdGuest, HttpStatus.CREATED);
		return ResponseEntity.ok(guestService.updateGuest(guestService.updateGuest(guest)));
	}
	@PostMapping
	public ResponseEntity<Guest> createGuest(@RequestBody Guest guest, HttpServletRequest request) throws Exception {
//    	HttpServletRequest request = null;
		Guest createdGuest = guestService.createGuest(guest);
        return new ResponseEntity<>(createdGuest, HttpStatus.CREATED);
//		return qrCodeImageBase64;
	}
	@PutMapping("/{id}")
	public ResponseEntity<Guest> updateGuest(@PathVariable("id") int id, @RequestBody Guest guest) {
		Guest existingGuest = guestService.getGuestById(id);
		if (existingGuest == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		guest.setId(id);
		Guest updatedGuest = guestService.updateGuest(guest);
		return new ResponseEntity<>(updatedGuest, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteGuest(@PathVariable("id") int id) {
		Guest existingGuest = guestService.getGuestById(id);
		if (existingGuest == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		guestService.deleteGuest(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}