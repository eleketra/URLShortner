package com.example.shortURL.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.shortURL.entities.Url;

@Repository
public interface UrlDao extends JpaRepository<Url,String> {

	Url findByLongUrl(String longurl);
}
