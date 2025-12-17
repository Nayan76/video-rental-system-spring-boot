package com.Video.Rent.Entity;

import jakarta.persistence.*;
import jakarta.persistence.Id; // RIGHT

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "rentals")
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "video_id")
    private Video video;

    private LocalDateTime rentedAt;
    private LocalDateTime returnedAt;

    private boolean active = true;

    // ---------- Getters ----------
    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Video getVideo() {
        return video;
    }

    public LocalDateTime getRentedAt() {
        return rentedAt;
    }

    public LocalDateTime getReturnedAt() {
        return returnedAt;
    }

    public boolean isActive() {
        return active;
    }

    // ---------- Setters ----------
    public void setUser(User user) {
        this.user = user;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public void setRentedAt(LocalDateTime rentedAt) {
        this.rentedAt = rentedAt;
    }

    public void setReturnedAt(LocalDateTime returnedAt) {
        this.returnedAt = returnedAt;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
