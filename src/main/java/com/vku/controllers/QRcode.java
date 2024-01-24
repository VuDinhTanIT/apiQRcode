package com.vku.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.awt.*;
import java.awt.image.BufferedImage;

import java.io.File;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.EnumMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import ch.qos.logback.core.model.Model;
import jakarta.servlet.http.HttpServletRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class QRcode {

//	private final String LOGO = "https://vku.udn.vn/images/logo_icon.png";

	@GetMapping("/home")
	public String home(Model model) {
		return "home";
	}

	@PostMapping("/createQR")
	public String testQR(HttpServletRequest request , @RequestParam("content") String content ) throws Exception {
		return generateQRcodeWithLogo(content, request, "event");
	}

	private String convertMapToJson(Map<String, String> requestData) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(requestData);
	}

	public String generateQRcodeWithLogo(String content, HttpServletRequest request, String nameQRcode)
			throws Exception {
		String applicationPath = request.getServletContext().getRealPath("");
		String upload_dir = "uploads";
		String uploadFilePath = applicationPath + upload_dir + File.separator;
		String qcodePath = uploadFilePath + nameQRcode + "-QRCode.png";
//		System.out.println("acodePath: " + qcodePath);
		Map<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, 400, 400, hints);
		MatrixToImageConfig imageConfig = new MatrixToImageConfig(MatrixToImageConfig.BLACK, MatrixToImageConfig.WHITE);

		BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix, imageConfig);
		// Getting logo image
		String pathLogo = applicationPath + "logos\\iconlogo.png";
//		System.out.println("path Logo: " + pathLogo);
		BufferedImage logoImage = resizeImage(ImageIO.read(new File(pathLogo)), 30, 30);
//		BufferedImage logoImage = resizeImage(ImageIO.read(new File("D:\\2_INTERN\\gpt.png")), 30, 30);

//           BufferedImage logoImage = getOverlay(LOGO);

		int finalImageHeight = qrImage.getHeight() - logoImage.getHeight();
		int finalImageWidth = qrImage.getWidth() - logoImage.getWidth();
		Graphics2D graphics = qrImage.createGraphics();
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
		graphics.drawImage(logoImage, (int) Math.round(finalImageWidth / 2), (int) Math.round(finalImageHeight / 2),
				null);

		ImageIO.write(qrImage, "png", new File(qcodePath));

		byte[] qrCodeImageBytes = Files.readAllBytes(Path.of(qcodePath));
		String qrCodeImageBase64 = Base64.getEncoder().encodeToString(qrCodeImageBytes);
//		System.out.println("QR Code with Logo Generated Successfully");

		return qrCodeImageBase64;

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
		String[] parts = qrCodeContent.trim().split("\\|");
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

	private static BufferedImage resizeImage(BufferedImage image, int width, int height) {
		BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = resizedImage.createGraphics();
		graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		graphics.drawImage(image, 0, 0, width, height, null);
		graphics.dispose();
		return resizedImage;
	}

}