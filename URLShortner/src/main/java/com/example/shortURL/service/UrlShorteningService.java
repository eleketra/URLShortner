package com.example.shortURL.service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.shortURL.dao.UrlDao;
import com.example.shortURL.entities.Url;

@Service
@CacheConfig(cacheNames = "url")
public class UrlShorteningService {

	final String baseUrl = "http://localhost:8080/";
	@Autowired
	private UrlDao urlrepo;

	@Autowired
	private EncodingService encodeService;

	// service to create new short url
	public String creatShortUrl(String longUrl) {

		String hash = encodeService.generate_hash(longUrl);
		Url url = new Url();
		url.setLongUrl(longUrl);
		String shorturl = "";
		for (int i = 0; i < hash.length() - 6; i++) {
			shorturl = encodeService.base_convert(hash.substring(i, i + 6));
			url.setShortUrl(shorturl);
			if (urlrepo.save(url) != null)
				break;
		}
		return baseUrl + shorturl;

	}

	// service to check for already present shorten url
	@Cacheable(value = "shortUrl", key = "#longUrl")
	public String checkifPresent(String longUrl) {
		Url url = urlrepo.findByLongUrl(longUrl);
		if (url != null)
			return baseUrl + url.getShortUrl();
		else
			return null;
	}

	// service to get the original url for redirection
	@Cacheable(value = "longUrl", key = "#shortUrl")
	public String getOriginalUrl(String shortUrl) {

		Url entity = urlrepo.findById(shortUrl).orElseThrow();
		return entity.getLongUrl();
	}

}
