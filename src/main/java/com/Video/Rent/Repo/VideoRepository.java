package com.Video.Rent.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Video.Rent.Entity.Video;

public interface VideoRepository extends JpaRepository<Video, Long> {
    
}
