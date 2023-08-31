package com.wigell.wigellpadel.service;

import com.wigell.wigellpadel.Exceptions.ResourceNotFoundException;
import com.wigell.wigellpadel.entities.Booking;
import com.wigell.wigellpadel.entities.Court;
import com.wigell.wigellpadel.entities.User;
import com.wigell.wigellpadel.repository.BookingRepository;
import com.wigell.wigellpadel.repository.CourtRepository;
import com.wigell.wigellpadel.repository.UserRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService implements BookingServiceInterface {

    private static final Logger logger = Logger.getLogger(BookingService.class);
    private BookingRepository bookingRepository;
    private ExchangeRateService exchangeRateService;
    private CourtRepository courtRepository;
    private UserRepository userRepository;



    @Autowired
    public BookingService(BookingRepository bookingRepository, ExchangeRateService exchangeRateService, CourtRepository courtRepository, UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.exchangeRateService = exchangeRateService;
        this.courtRepository = courtRepository;
        this.userRepository = userRepository;

    }


    @Transactional
    @Override
    public ResponseEntity<String> makeBookingLogic(Booking booking) {
        Court court = courtRepository.findById(booking.getCourt().getCourtId()).orElse(null);

        if(court == null){
            return new ResponseEntity<>("Court not found", HttpStatus.CONFLICT);
        }
        if (court.isBooked()) {
            return new ResponseEntity<>("Process failed! There is already an active booking on this court", HttpStatus.CONFLICT);
        }
        else {
            court.setBooked(true);
            courtRepository.save(court);
            bookingRepository.save(booking);
            logger.info("Court " + court.getCourtId() + " booked successfully.");
            return new ResponseEntity<>("Court "  + court.getCourtId() + " is now booked", HttpStatus.CREATED);
        }
    }

    @Override
    public List<Booking> listAllBookings(int id) {

        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "Id", id));


        List<Booking> allBookings = listAllBookingsLogic();

        List<Booking> myBookings = new ArrayList<>();
        for(Booking booking : allBookings){
            if (booking.getUser().getUserId() == user.getUserId()) {
                myBookings.add(booking);
            }
        }
        return myBookings;
    }

    @Override
    public List<Booking> listAllBookingsLogic(){
        return bookingRepository.findAll();
    }


    @Override
    public void deleteBooking(int id) {
        logger.info("Booking with id " + id + " successfully deleted.");
        bookingRepository.deleteById(id);
    }

    @Override
    public Booking updateBooking(Booking booking, int id) {
        Booking b = bookingRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Booking", "Id", id));

        Court newCourt = booking.getCourt();
        System.out.println(b.getCourt());
        System.out.println(booking.getCourt());
        b.setDateTime(booking.getDateTime());
        b.setTotalPlayers(booking.getTotalPlayers());
        b.setUser(booking.getUser());


        if(!newCourt.equals(b.getCourt())){
            newCourt.setBooked(true);
            b.getCourt().setBooked(false);
        }

        System.out.println(b.getCourt().isBooked());
        System.out.println(newCourt.isBooked());
        b.setCourt(booking.getCourt());
        bookingRepository.save(b);
        return b;
    }


    @Override
    public Booking findByID(int ID) {
        Booking booking = null;
        Optional<Booking> possibleBooking = bookingRepository.findById(ID);
        if (possibleBooking.isPresent())
            booking = possibleBooking.get();
        return booking;
    }


    @Override
    public ResponseEntity<String> updateBookingLogic(Booking booking, int id) {
        Court court = courtRepository.findById(booking.getCourt().getCourtId()).orElse(null);
        booking.setCourt(court);
        updateBooking(booking, id);
        logger.info("Booking with id " + id + " updated successfully.");
        return new ResponseEntity<>("Booking updated successfully", HttpStatus.OK);
    }




}
