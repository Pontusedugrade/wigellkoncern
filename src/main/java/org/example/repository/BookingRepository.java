package com.wigell.wigellpadel.repository;

import com.wigell.wigellpadel.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
}
