package com.Video.Rent.Service;

import com.Video.Rent.Entity.Rental;
import com.Video.Rent.Entity.User;
import com.Video.Rent.Entity.Video;
import com.Video.Rent.Repo.RentalRepository;
import com.Video.Rent.Repo.VideoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;
    private final VideoRepository videoRepository;

    public RentalService(RentalRepository rentalRepository,
                         VideoRepository videoRepository) {
        this.rentalRepository = rentalRepository;
        this.videoRepository = videoRepository;
    }

    public void rentVideo(User user, Long videoId) {

        if (rentalRepository.countByUserAndActiveTrue(user) >= 2) {
            throw new IllegalStateException("Maximum 2 active rentals allowed");
        }

        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new EntityNotFoundException("Video not found"));

        if (!video.isAvailable()) {
            throw new IllegalStateException("Video is not available");
        }

        Rental rental = new Rental();
        rental.setUser(user);
        rental.setVideo(video);
        rental.setRentedAt(LocalDateTime.now());

        video.setAvailable(false);

        rentalRepository.save(rental);
        videoRepository.save(video);
    }

    public void returnVideo(User user, Long videoId) {

        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new EntityNotFoundException("Video not found"));

        Rental rental = rentalRepository
                .findByUserAndVideoAndActiveTrue(user, video)
                .orElseThrow(() -> new IllegalStateException("No active rental found"));

        rental.setActive(false);
        rental.setReturnedAt(LocalDateTime.now());

        video.setAvailable(true);

        rentalRepository.save(rental);
        videoRepository.save(video);
    }
}

