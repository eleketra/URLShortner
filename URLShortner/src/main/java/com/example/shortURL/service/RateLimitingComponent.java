package com.example.shortURL.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RateLimitingComponent {
	
	
	
	private int limit=5;
	private static Map<String, Integer> limiters = new ConcurrentHashMap<>();
	
	public String getUserIP()
	{
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
		        .getRequest();

		
		String ip = request.getHeader("x-forwarded-for");      
		   if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {      
		       ip = request.getHeader("Proxy-Client-IP");      
		   }      
		   if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {      
		       ip = request.getHeader("WL-Proxy-Client-IP");      
		   }      
		   if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {      
		       ip = request.getRemoteAddr();      
		   }      
		   return ip;   

	}
	
	/*public boolean checkLimitation()
	{
		String clientId=getUserIP();
		if(clientId==null)
			return true;
		 if(limiters.containsKey(clientId))
		 {
			 if(limiters.get(clientId)<limit)
			 {
				 limiters.put(clientId,limiters.get(clientId)+1);
				 return true;
			 }
			 else if(limiters.get(clientId)==limit)
			 {
				 
			 }
		 }*/
	}


