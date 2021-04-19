package com.example.shortURL.service;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

@Configuration
@ComponentScan(basePackages = "com.example.shortURL.service")
public class RateLimitingService implements Filter {

	private int MAX_REQUESTS_PER_TWO_SECOND = 5; // or whatever you want it to be

	private LoadingCache<String, Integer> requestCountsPerIpAddress;

	public RateLimitingService() {
		super();
		requestCountsPerIpAddress = CacheBuilder.newBuilder().expireAfterWrite(5, TimeUnit.SECONDS)
				.build(new CacheLoader<String, Integer>() {
					public Integer load(String key) {
						return 0;
					}
				});
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	private boolean isMaximumRequestsPerSecondExceeded(String clientIpAddress) {
		int requests = 0;
		try {
			requests = requestCountsPerIpAddress.get(clientIpAddress);
			if (requests > MAX_REQUESTS_PER_TWO_SECOND) {
				return true;
			}
		} catch (ExecutionException e) {
			requests = 0;
		}
		requests++;
		requestCountsPerIpAddress.put(clientIpAddress, requests);
		return false;
	}

	public String getClientIP(HttpServletRequest request) {
		String xfHeader = request.getHeader("X-Forwarded-For");
		if (xfHeader == null) {
			return request.getRemoteAddr();
		}
		return xfHeader.split(",")[0];
	}

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {

		HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
		String clientIpAddress = getClientIP((HttpServletRequest) servletRequest);

		if (isMaximumRequestsPerSecondExceeded(clientIpAddress)) {

			httpServletResponse.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
			httpServletResponse.getWriter().write("Too many requests");
			return;
		}

		filterChain.doFilter(servletRequest, servletResponse);

	}
}
