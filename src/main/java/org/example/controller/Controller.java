package org.example.controller;

import org.example.entities.Booking;
import org.example.entities.Court;
import org.example.entities.User;
import org.example.service.BookingService;
import org.example.service.CourtService;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v5")
public class Controller {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private CourtService courtService;

    @Autowired
    private UserService userService;

    @GetMapping("/availability")
    public List<Court> listAvailableTimeSlots(){return courtService.listAvailableTimeSlots();}

    @GetMapping("/getcurrentrates")
    private List<Object> getCurrentRates(){
        String uri ="https://openexchangerates.org/api/latest.json?app_id=cb66c6ac2c0b44cfabe2db7b73fbdc09&symbols=,EUR,SEK";
        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> exchangeRates = restTemplate.getForObject(uri, Map.class);
        Map<String, Object> rates = (Map<String, Object>) exchangeRates.get("rates");

        List<Object> currencies = new ArrayList<>();
        currencies.add(rates.get("EUR"));
        currencies.add(rates.get("SEK"));

        return currencies;
    }

    @PostMapping("/bookings")
    public ResponseEntity<String> addBooking(@RequestBody Booking booking){
        return bookingService.makeBookingLogic(booking);
    }

    @GetMapping("/mybookings/{id}")
    public List<Booking> getAllBookings(@PathVariable("id") int id){
        return bookingService.listAllBookings(id);
    }


    //Admin endpoints

    @GetMapping("/customers")
    public List<User> getAllUsers(){
        return userService.listUsers();
    }

    @PostMapping("/register")
    public ResponseEntity<User> addUser(@RequestBody User user){
        return new ResponseEntity<User>(userService.addUser(user), HttpStatus.CREATED);
    }

    @DeleteMapping("/deletebooking/{id}")
    public ResponseEntity<String> deleteBooking(@PathVariable("id") int id){
        bookingService.deleteBooking(id);
        return new ResponseEntity<String>("Booking deleted", HttpStatus.OK);
    }

    @PutMapping("/updateinfo")
    public ResponseEntity<String> updateUserInfo(@RequestBody User user){
        return userService.updateInfoLogic(user);
    }

    @PutMapping("/bookings/{id}")
    public ResponseEntity<String> updateBooking(@PathVariable("id") int id, @RequestBody Booking booking){
        return bookingService.updateBookingLogic(booking, id);
    }











}
