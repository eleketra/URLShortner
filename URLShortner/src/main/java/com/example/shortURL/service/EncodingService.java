package com.example.shortURL.service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.type.StringType;
import org.springframework.stereotype.Service;

@Service
public class EncodingService {

	final String allowedString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	// function to generate md5 hash
	protected String generate_hash(String message) {
		String digest = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] hash = md.digest(message.getBytes("UTF-8"));
			StringBuilder sb = new StringBuilder(2 * hash.length);
			for (byte b : hash) {
				sb.append(String.format("%02x", b & 0xff));
			}
			digest = sb.toString();
		} catch (UnsupportedEncodingException ex) {
			Logger.getLogger(StringType.class.getName()).log(Level.SEVERE, null, ex);
		} catch (NoSuchAlgorithmException ex) {
			Logger.getLogger(StringType.class.getName()).log(Level.SEVERE, null, ex);
		}
		return digest;
	}

	// function to encode in base62
	protected String base_convert(String num) {
		long value = Long.valueOf(Integer.parseInt(num, 16));
		StringBuilder sb = new StringBuilder();
		while (value != 0) {
			sb.append(allowedString.charAt((int) (value % 62)));
			value /= 62;
		}
		while (sb.length() < 6) {
			sb.append(0);
		}
		return sb.reverse().toString();

	}

}
