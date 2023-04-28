package com.example.bookshop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.google.common.cache.Cache;

@SpringBootTest
class OTPServiceTest {
	
	@Autowired
	private OTPService otpService;
	
	private Cache<String, Integer> otpCache;
	
	@BeforeEach
    public void setUp() {
        otpService = new OTPService();
        otpCache = otpService.getOtpCache();
    }
	
//	@Test
//    void testOtpCacheConstructorReturnsZero() {
//        String key = "testKey";
//        Integer value = otpCache.getIfPresent(key);
//        assertEquals(0, value);
//    }

	@Test
	void testGenerateOTP() throws NoSuchAlgorithmException {
		String key = "test_key";
        int otp = otpService.generateOTP(key);
        assertTrue(otp >= 100000 && otp <= 999999, "OTP should be a 6-digit number");
        assertEquals(otp, otpService.getOTP(key), "Generated OTP should be stored in the cache");
    
	}

	@Test
	void testGetOTP() throws NoSuchAlgorithmException {
		String key = "test_key";
        otpService.generateOTP(key);
        int otp = otpService.getOTP(key);
        assertTrue(otp >= 100000 && otp <= 999999, "OTP should be a 6-digit number");
   
	}

	@Test
	void testClearOTP() throws NoSuchAlgorithmException {
		 String key = "test_key";
	        otpService.generateOTP(key);
	        otpService.clearOTP(key);
	        assertEquals(0, otpService.getOTP(key), "OTP should be cleared from the cache");
	   
	}

}
