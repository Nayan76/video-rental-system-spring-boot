package com.Video.Rent.controller;

import com.Video.Rent.Entity.User;
import com.Video.Rent.Service.RentalService;
import com.Video.Rent.Repo.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    private final RentalService rentalService;
    private final UserRepository userRepository;

    public RentalController(RentalService rentalService,
                            UserRepository userRepository) {
        this.rentalService = rentalService;
        this.userRepository = userRepository;
    }

    /**
     * CUSTOMER & ADMIN
     */
    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
    @PostMapping("/videos/{videoId}/rent")
    public ResponseEntity<?> rentVideo(
            @PathVariable Long videoId,
            Authentication authentication) {

        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        rentalService.rentVideo(user, videoId);
        return ResponseEntity.ok("Video rented successfully");
    }

    /**
     * CUSTOMER & ADMIN
     */
    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
    @PostMapping("/videos/{videoId}/return")
    public ResponseEntity<?> returnVideo(
            @PathVariable Long videoId,
            Authentication authentication) {

        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        rentalService.returnVideo(user, videoId);
        return ResponseEntity.ok("Video returned successfully");
    }
}
