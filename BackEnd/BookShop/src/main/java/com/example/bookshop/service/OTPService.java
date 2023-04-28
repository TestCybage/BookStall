package com.example.bookshop.service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;

@Service
public class OTPService {

	private static final Integer EXPIRE_MINS = 5;
	
	private Cache<String, Integer> otpCache;

	public OTPService() {
		super();
		otpCache = CacheBuilder.newBuilder().expireAfterWrite(EXPIRE_MINS, TimeUnit.MINUTES)
				.build(new CacheLoader<String, Integer>() {
					public Integer load(String key) {
						return 0;
					}
				});
	}
	
	public Cache<String, Integer> getOtpCache() {
        return otpCache;
    }

	public int generateOTP(String key) throws NoSuchAlgorithmException {
		Random random = SecureRandom.getInstanceStrong();
		int otp = 100000 + random.nextInt(900000);
		otpCache.asMap().put(key, otp);
		return otp;
	}

	public int getOTP(String key) {
		try {
			return otpCache.getIfPresent(key);
		} catch (Exception e) {
			return 0;
		}
	}
	
	public void clearOTP(String key) {
		otpCache.invalidate(key);
	}

}
