package com.example.shortURL.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.shortURL.entities.Url;
import com.example.shortURL.service.UrlShorteningService;

@RestController
public class MyController {

	@Autowired
	UrlShorteningService urlService;

	@GetMapping("/health")
	public String health() {
		return "OKAY";
	}

	@PostMapping(value="/create-shorturl")
	public String convertToShortURL(@RequestBody Url url)

	{
		String shorturl = urlService.checkifPresent(url.getLongUrl());
		if (shorturl != null) {
			//System.out.println("already present");
			return shorturl;
		} else
			return urlService.creatShortUrl(url.getLongUrl());
	}

	/*@PostMapping("/get-longurl")
	public String convertToLongURL(@RequestBody String shortUrl) {
		return urlService.getOriginalUrl(shortUrl);
	}*/

	@GetMapping("/{shortUrl}")
	public ResponseEntity<Void> redirectLink(@PathVariable String shortUrl) {
		String url = urlService.getOriginalUrl(shortUrl);
		System.out.println("hello from method");
		return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(url)).build();

	}

}
