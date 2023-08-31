package org.example.service;

import org.example.entities.Court;
import org.example.repository.CourtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CourtService implements CourtServiceInterface {

    private CourtRepository courtRepository;
    private ExchangeRateService exchangeRateService;

    @Autowired
    public CourtService(CourtRepository courtRepository, ExchangeRateService exchangeRateService) {
        this.courtRepository = courtRepository;
        this.exchangeRateService = exchangeRateService;
    }

    @Override
    public List<Court> listAvailableTimeSlots() {
        List<Court> allCourts = listAllCourts();
        List<Court> availableCourts = new ArrayList<>();
        for (Court court : allCourts) {
            if (!court.isBooked())
                availableCourts.add(court);
        }
        return availableCourts;
    }

    @Override
    public List<Court> listAllCourts() {

        Map<String, Object> exchangeRates = exchangeRateService.getExchangeRates();
        Map<String, Object> rates = (Map<String, Object>) exchangeRates.get("rates");

        List<Court> courts = courtRepository.findAll();
        List<Court> updatedCourts = new ArrayList<>();

        for (Court court : courts) {
            double priceInSEK = court.getPriceSEK();
            double exchangeRate = (double) rates.get("SEK");
            double priceInTargetCurrency = priceInSEK / exchangeRate;
            court.setPriceEUR(priceInTargetCurrency);
            updatedCourts.add(court);
        }
        courtRepository.saveAll(updatedCourts);
        return updatedCourts;
    }

    @Override
    public Court findByID(int ID) {
        Court court = null;
        Optional<Court> possibleCourt = courtRepository.findById(ID);
        if (possibleCourt.isPresent())
            court = possibleCourt.get();
        return court;
    }
}

