package com.example.bookshop.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.bookshop.exception.ErrorMessage;

import lombok.AllArgsConstructor;

@Service
public class EmailService {
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	Logger logger = Logger.getLogger(EmailService.class);

	public String sentOtpMessage(String to, int otp) throws MessagingException {
		MimeMessage msg = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(msg, "utf-8");
		helper.setFrom("Trng2@evolvinsols.com");
		helper.setTo(to);
		helper.setText("OTP is:" + otp, true);
		helper.setSubject("Otp for login");

		javaMailSender.send(msg);
		return "" + otp + "";
	}
		
	public void sendEmail(String subject,String to,String emailMessage) {
		try {
			MimeMessage msg = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(msg, "utf-8");
			helper.setText(emailMessage,true);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setFrom("Trng2@evolvinsols.com");
			logger.info(emailMessage);
			logger.info(subject);
			logger.info(to);
			javaMailSender.send(msg);
		} catch (MessagingException e) {
			logger.error(ErrorMessage.EMAIL_FAILED,e);
			throw new IllegalStateException(ErrorMessage.EMAIL_FAILED);
		}
	}
	

}
