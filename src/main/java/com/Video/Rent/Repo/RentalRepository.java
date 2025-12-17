package com.Video.Rent.Repo;

import com.Video.Rent.Entity.Rental;
import com.Video.Rent.Entity.User;
import com.Video.Rent.Entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RentalRepository extends JpaRepository<Rental, Long> {
    long countByUserAndActiveTrue(User user);

    Optional<Rental> findByUserAndVideoAndActiveTrue(User user, Video video);


}
