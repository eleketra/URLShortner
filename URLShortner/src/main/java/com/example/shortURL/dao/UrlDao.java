package com.example.shortURL.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.shortURL.entities.Url;

public interface UrlDao extends JpaRepository<Url,Long> {

}
