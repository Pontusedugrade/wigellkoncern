package com.wigell.wigellpadel.service;

import com.wigell.wigellpadel.entities.Booking;
import com.wigell.wigellpadel.entities.Court;

import java.util.List;

public interface CourtServiceInterface {

    List<Court> listAvailableTimeSlots();
    List<Court> listAllCourts();
    Court findByID (int ID);
}
