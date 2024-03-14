package com.vku.services;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.EnumMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

@Service
public class QrCodeService {

//	------------------------

	public String readQRCode(String base64Image) {
		String encodedContent = null;
		try {
			byte[] imageBytes = Base64.getDecoder().decode(base64Image);
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageBytes);
			BufferedImage bufferedImage = ImageIO.read(byteArrayInputStream);
			encodedContent = readQRCode(bufferedImage);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return encodedContent;
	}

	public String readQRCode(File qrCodeFile) {
		String encodedContent = null;
		try {
			BufferedImage bufferedImage = ImageIO.read(qrCodeFile);

			encodedContent = readQRCode(bufferedImage);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return encodedContent;
	}

	public String readQRCode(MultipartFile file) {
		String encodedContent = null;
		try {
			File qrCodeFile = convertMultipartFileToFile(file);
			BufferedImage bufferedImage = ImageIO.read(qrCodeFile);
			encodedContent = readQRCode(bufferedImage);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return encodedContent;
	}

	private File convertMultipartFileToFile(MultipartFile file) throws IOException {
		File convertedFile = new File(file.getOriginalFilename());
		FileCopyUtils.copy(file.getBytes(), convertedFile);
		return convertedFile;
	}

	public String readQRCode(BufferedImage bufferedImage) {
		String encodedContent = null;
		try {
			BufferedImageLuminanceSource bufferedImageLuminanceSource = new BufferedImageLuminanceSource(bufferedImage);
			HybridBinarizer hybridBinarizer = new HybridBinarizer(bufferedImageLuminanceSource);
			BinaryBitmap binaryBitmap = new BinaryBitmap(hybridBinarizer);
			MultiFormatReader multiFormatReader = new MultiFormatReader();

			Result result = multiFormatReader.decode(binaryBitmap);
			encodedContent = result.getText();
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
		return encodedContent;
	}

	public String generateQRcodeWithLogo(String content, String qrcodePath, String applicationPath) throws Exception {
		// TODO Auto-generated method stub

		Map<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, 400, 400, hints);
		MatrixToImageConfig imageConfig = new MatrixToImageConfig(MatrixToImageConfig.BLACK, MatrixToImageConfig.WHITE);

		BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix, imageConfig);
		// Getting logo image
		String pathLogo = applicationPath + "logos\\iconlogo.png";
//		System.out.println("path Logo: " + pathLogo);
		BufferedImage logoImage = resizeImage(ImageIO.read(new File(pathLogo)), 30, 30);

		int finalImageHeight = qrImage.getHeight() - logoImage.getHeight();
		int finalImageWidth = qrImage.getWidth() - logoImage.getWidth();
		Graphics2D graphics = qrImage.createGraphics();
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
		graphics.drawImage(logoImage, (int) Math.round(finalImageWidth / 2), (int) Math.round(finalImageHeight / 2),
				null);

		ImageIO.write(qrImage, "png", new File(qrcodePath));
		return null;
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
