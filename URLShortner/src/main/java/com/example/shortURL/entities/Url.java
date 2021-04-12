package com.example.shortURL.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Url {
	
	@SequenceGenerator(name = "port_gen", sequenceName = "port_gen",  initialValue = 1234567890)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "port_gen")
	private long id;
	private String oldUrl;
	
	public Url()
	{
		super();
	}
	public Url(long id, String oldUrl) {
		super();
		this.id = id;
		this.oldUrl = oldUrl;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getOldUrl() {
		return oldUrl;
	}
	public void setOldUrl(String oldUrl) {
		this.oldUrl = oldUrl;
	}
			
}
