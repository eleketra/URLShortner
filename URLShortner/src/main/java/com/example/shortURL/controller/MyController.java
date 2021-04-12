package com.example.shortURL.controller;

import java.net.URI;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;




import com.example.shortURL.service.UrlShorteningService;


@RestController

public class MyController {

	
	@Autowired
	UrlShorteningService urlService;
	
//	
	@PostMapping("/create-shorturl")
	public String convertToShortURL(@RequestBody String longUrl)
	
	{
		
		return urlService.creatShortUrl(longUrl);
	}
	
	
	@PostMapping("/get-longurl")
	public String convertToLongURL(@RequestBody String shortUrl)
	{
		return urlService.getOriginalUrl(shortUrl);
	}
	
	
	
	@GetMapping("/{shortUrl}")

   public ResponseEntity<Void> redirectLink(@PathVariable String shortUrl)
	{
	   
	 //  System.out.println("your ip="+limitingComponent.getUserIP());
		String url= urlService.getOriginalUrl(shortUrl);
		 return ResponseEntity.status(HttpStatus.FOUND)
	                .location(URI.create(url))
	                .build();
	
 }
	
	/*public ResponseEntity greetingFallBack(String name, io.github.resilience4j.ratelimiter.RequestNotPermitted ex) {
        System.out.println("Rate limit applied no further calls are accepted");

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Retry-After", "2"); //retry after one second

        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                .headers(responseHeaders) //send retry header
                .body("Too many request - No further calls are accepted");
    }*/
}
