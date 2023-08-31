package org.example.service;

import org.example.entities.Court;

import java.util.List;

public interface CourtServiceInterface {

    List<Court> listAvailableTimeSlots();
    List<Court> listAllCourts();
    Court findByID (int ID);
}
