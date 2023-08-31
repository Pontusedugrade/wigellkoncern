package com.wigell.wigellpadel.service;

import com.wigell.wigellpadel.entities.Booking;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BookingServiceInterface {

    ResponseEntity<String> makeBookingLogic(Booking booking);
    List<Booking> listAllBookings(int id);
    List<Booking> listAllBookingsLogic();
    void deleteBooking(int id);
    ResponseEntity<String> updateBookingLogic(Booking booking, int id);
    Booking updateBooking(Booking booking, int id);
    Booking findByID (int ID);
}
