package com.vku.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

import com.vku.dtos.QRData;
import com.vku.services.QrCodeService;

import ch.qos.logback.core.model.Model;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class QRcode {

//	private final String LOGO = "https://vku.udn.vn/images/logo_icon.png";

	@Autowired
	private QrCodeService qrCodeService;

	@GetMapping("/home")
	public String home(Model model) {
		return "home";
	}

	@PostMapping("/createQR")
	public String testQR(HttpServletRequest request, @RequestParam("content") String content) throws Exception {
		generateQRcodeWithLogo(content, request, "event");
		return null;
	}
	

//	private String convertMapToJson(Map<String, String> requestData) throws JsonProcessingException {
//		ObjectMapper objectMapper = new ObjectMapper();
//		return objectMapper.writeValueAsString(requestData);
//	}
	@PostMapping("/api/readQR")
	public ResponseEntity<?> readQRCode(@RequestParam("file") MultipartFile file) {
		try {
			String qrData = qrCodeService.readQRCode(file);
			// Thực hiện các hành động tương ứng với tham số và dữ liệu từ mã QR
//            if (qrData.equals("param1")) {
//                // Thực hiện hành động cho param1
//            } else if (qrData.equals("param2")) {
//                // Thực hiện hành động cho param2
//            } else {
//                // Xử lý trường hợp không xác định
//            }
			return ResponseEntity.ok(qrData);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to read QR code.");
		}
	}

	public String generateQRcodeWithLogo(String content, HttpServletRequest request, String nameQRcode)
			throws Exception {
		String applicationPath = request.getServletContext().getRealPath("");
		String upload_dir = "uploads";
		String uploadFilePath = applicationPath + upload_dir + File.separator;
		nameQRcode = nameQRcode + "-QRCode.png";
		String qrcodePath = uploadFilePath + nameQRcode;
		System.out.println("path image: " + qrcodePath);

		String txt = qrCodeService.generateQRcodeWithLogo(content, qrcodePath, applicationPath);

		byte[] qrCodeImageBytes = Files.readAllBytes(Path.of(qrcodePath));
		String qrCodeImageBase64 = Base64.getEncoder().encodeToString(qrCodeImageBytes);
//		System.out.println("QR Code with Logo Generated Successfully");

		return qrCodeImageBase64;

	}

	public QRData generateQRcodeWithLogoO(String content, HttpServletRequest request, String nameQRcode)
			throws Exception {
		String applicationPath = request.getServletContext().getRealPath("");
		String upload_dir = "uploads";
		String uploadFilePath = applicationPath + upload_dir + File.separator;
		nameQRcode = nameQRcode + "-QRCode.png";
		String qrcodePath = uploadFilePath + nameQRcode;
		System.out.println("path image: " + Path.of(qrcodePath).getFileName().toString());

		String txt = qrCodeService.generateQRcodeWithLogo(content, qrcodePath, applicationPath);

		byte[] qrCodeImageBytes = Files.readAllBytes(Path.of(qrcodePath));
		String qrCodeImageBase64 = Base64.getEncoder().encodeToString(qrCodeImageBytes);

		QRData qrData = new QRData(nameQRcode, qrCodeImageBase64);
		return qrData;
	}

	public String generateQRcodeWithExpirationDays(String content, HttpServletRequest request, String nameQRcode,
			int expirations) throws Exception {
		// Tính toán thời gian hết hạn bằng cách thêm số ngày vào thời gian hiện tại
		LocalDateTime expirationTime = null;
		if (content.contains("|")) {
			expirationTime = LocalDateTime.now().plusHours(expirations);

		} else {
			expirationTime = LocalDateTime.now().plusDays(expirations);
		}

		// Convert expiration time to a string representation
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String expirationTimeString = expirationTime.format(formatter);

		// Append expiration time to the content
		String contentWithExpirationTime = content + "|" + expirationTimeString;
		System.out.println("contentWithExpirationTime: " + contentWithExpirationTime);
//		System.out.println("isQRCodeValid: "+  isQRCodeValid(contentWithExpirationTime));
		return generateQRcodeWithLogo(contentWithExpirationTime, request, nameQRcode);
		// Generate QR code with the modified content

	}

	public boolean isQRCodeValid(String qrCodeContent) {
		// Split the QR code content into the original content and the expiration time
		System.out.println("qr content: " + qrCodeContent);
		String[] parts = qrCodeContent.contains("|") ? qrCodeContent.trim().split("\\|") : null;
//		String[] parts = qrCodeContent.trim().split(Pattern.quote(" | "),-1);
		if (parts.length < 2) {
			// QR code does not contain the required information
			return false;
		}
//		String originalContent = parts[0];
		System.out.println("parts :" + parts[0] + " - " + parts[1] + " - " + parts[2] + "length : " + parts.length);
		String expirationTimeString = parts[parts.length - 1];

		// Parse the expiration time string into LocalDateTime
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime expirationTime = LocalDateTime.parse(expirationTimeString, formatter);

		// Check if the current time is before the expiration time
		LocalDateTime currentTime = LocalDateTime.now();
		return currentTime.isBefore(expirationTime);
	}

}