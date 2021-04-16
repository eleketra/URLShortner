package com.example.shortURL.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

//@AllArgsConstructor
//@NoArgsConstructor
@Entity

public class Url implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	private String shortUrl;
	private String longUrl;

	public Url() {
		super();
	}

	public Url(String longUrl) {
		super();
		this.longUrl = longUrl;
		
	}

	public String getShortUrl() {
		return shortUrl;
	}

	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}

	public String getLongUrl() {
		return longUrl;
	}

	public void setLongUrl(String longUrl) {
		this.longUrl = longUrl;
	}

}
