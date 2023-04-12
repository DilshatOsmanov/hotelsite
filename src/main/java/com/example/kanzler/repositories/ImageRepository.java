package com.example.kanzler.repositories;

import com.example.kanzler.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ImageRepository extends JpaRepository<Image, Long> {}
