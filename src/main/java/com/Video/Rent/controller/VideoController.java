package com.Video.Rent.controller;

import org.springframework.http.HttpStatus;
import com.Video.Rent.Entity.Video;
import com.Video.Rent.Repo.VideoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/videos")
public class VideoController {

    private final VideoRepository videoRepository;

    public VideoController(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    /**
     * Accessible by CUSTOMER and ADMIN
     */
    @GetMapping
    public List<Video> getAllVideos() {
        return videoRepository.findAll();
    }

    /**
     * ADMIN only
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Video> createVideo(@RequestBody Video video) {
        Video savedVideo = videoRepository.save(video);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedVideo);
    }

    /**
     * ADMIN only
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Video> updateVideo(
            @PathVariable Long id,
            @RequestBody Video updatedVideo) {

        return videoRepository.findById(id)
                .map(video -> {
                    video.setTitle(updatedVideo.getTitle());
                    video.setDirector(updatedVideo.getDirector());
                    video.setGenre(updatedVideo.getGenre());
                    video.setAvailable(updatedVideo.isAvailable());
                    return ResponseEntity.ok(videoRepository.save(video));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * ADMIN only
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVideo(@PathVariable Long id) {

        if (!videoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        videoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
