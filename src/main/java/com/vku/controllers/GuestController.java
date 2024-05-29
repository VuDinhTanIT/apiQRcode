package com.vku.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vku.dtos.AttendanceRequest_StudentDTO;
import com.vku.dtos.QRData;
import com.vku.enums.QR_Parameters;
import com.vku.models.Guest;
import com.vku.services.GuestService;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

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

		String content = QR_Parameters.GUEST +"||"+  String.valueOf(guest.getId());

//		String qrCodeImageBase64 = qRcode.generateQRcodeWithExpirationDays(content, request, guest.getCccd(), 1);
		QRData qrData = qRcode.generateQRcodeWithLogoO(content, request, guest.getPhoneNumber());
		guest.setQRCodeImage(qrData.getNameQRcode());
//		guestService.updateGuest(guest);
//        return new ResponseEntity<>(createdGuest, HttpStatus.CREATED);
		return ResponseEntity.ok(guestService.updateGuest(guestService.updateGuest(guest)));
	}
	@PostMapping("/scanQR")
	public ResponseEntity<?> ScanAttendanceQRcode(@RequestParam String guestQR,
			HttpServletRequest request) throws Exception {
		try {
			Guest guest = guestService.getGuestById(Integer.parseInt(guestQR));
			if (guest == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			
//			System.out.println("Request Att: " + attendanceRequest.toString());
//			String QRcodeInfo = attendanceRequest.getQrCodeInfo();
//			String studentCode = attendanceRequest.getStudentCode(); 
//			boolean isValid = qRcode.isQRCodeValid(guestQR);
//			if(!isValid) {
//				throw new Exception("Time out");
//			}
//			String courseId = QRcodeInfo.trim().split("\\|")[0];
			System.out.println("G-Controller: guest: " + guest);
			return new ResponseEntity<>(guest, HttpStatus.OK);		
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
//			System.out.println(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			
		} 
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