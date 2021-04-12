package com.example.shortURL.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.shortURL.dao.UrlDao;
import com.example.shortURL.entities.Url;

@Service
//@CacheConfig(cacheNames = "url")
public class UrlShorteningService {
	
	final String allowedString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
   
	
	@Autowired
	private UrlDao urlrepo;

	@CacheEvict(value = "allUrlcache", allEntries = true)
	public String creatShortUrl(String longUrl) {
		
		
		Url url=new Url();
		url.setOldUrl(longUrl);
		Url entity=urlrepo.save(url);
		
		//return "https://wheelseye.com/"+encode(entity.getId());
		
		return "http://localhost:8080/"+encode(entity.getId());
		
	}

	private String encode(long value) {
		 StringBuilder sb = new StringBuilder();
		    while (value != 0) {
		        sb.append(allowedString.charAt((int)(value % 62)));
		        value /= 62;
		    }
		   /* while (sb.length() < 6) {
		        sb.append(0);
		    }*/
		    return sb.reverse().toString();
	
	}
	
	private long decode(String shortUrl)
	{
		String todecode=shortUrl;//.substring(shortUrl.lastIndexOf("/")+1);
		int counter=todecode.length()-1;
		long result=0;
		for(int i=0;i<todecode.length();i++)
		{
			result+=allowedString.indexOf(todecode.charAt(i))*Math.pow(62, counter);
			counter--;
			
		}
		
		return result;
	}
	
	@Cacheable("url")
	public String getOriginalUrl(String shortUrl)
	{
		
		long id=decode(shortUrl);
		Url entity=urlrepo.findById(id).orElseThrow();
		return entity.getOldUrl();
	}

}
