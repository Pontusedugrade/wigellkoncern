package com.wigell.wigellpadel.repository;

import com.wigell.wigellpadel.entities.Court;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourtRepository extends JpaRepository<Court, Integer> {
}