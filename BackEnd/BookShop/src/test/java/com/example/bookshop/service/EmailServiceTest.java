package com.example.bookshop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.example.bookshop.exception.ErrorMessage;

@SpringBootTest
class EmailServiceTest {

	@MockBean
	private JavaMailSender javaMailSender;

	@MockBean
	private MimeMessage mimeMessage;

	@MockBean
	private MimeMessageHelper mimeMessageHelper;

	@Autowired
	private EmailService emailService;

	@Test
	void testSentOtpMessage() throws MessagingException {
		String to = "test@example.com";
		int otp = 1234;
		when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);

		String actualOtpString = emailService.sentOtpMessage(to, otp);

		assertEquals(String.valueOf(otp), actualOtpString);
		verify(javaMailSender, times(1)).send(mimeMessage);
	}

	@Test
	void testSendEmail() {
		String to = "test@example.com";
		String subject = "Test Subject";
		String message = "Test Message";

		when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);

		emailService.sendEmail(subject, to, message);

		verify(javaMailSender, times(1)).send(mimeMessage);
	}

	@Test
	void testSendEmailWithException() throws MessagingException {
		String to = "@INVALID_EMAIL";
		String subject = "Test Subject";
		String message = "Test Message";
		when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
		
		MessagingException messagingException = new MessagingException();
		doThrow(messagingException).when(mimeMessageHelper).setTo(to);
		
		
		IllegalStateException illegalStateException = assertThrows(IllegalStateException.class, () -> {
			emailService.sendEmail(subject, to, message);
		});
		assertEquals(ErrorMessage.EMAIL_FAILED, illegalStateException.getMessage());
	}

}
