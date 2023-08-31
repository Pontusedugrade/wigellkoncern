package com.wigell.wigellpadel.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "court")
public class Court {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "court_id")
    private int courtId;
    @Column(name = "price_SEK")
    private double priceSEK;
    @Column(name = "price_EURO")
    private double priceEUR;
    @Column(name = "court_name")
    private String courtName;
    @Column(name = "booked")
    private boolean booked;

    public Court() {
    }

    public Court(double priceSEK, double priceEUR, String courtName, boolean booked) {
        this.priceSEK = priceSEK;
        this.priceEUR = priceEUR;
        this.courtName = courtName;
        this.booked = booked;
    }

    public int getCourtId() {
        return courtId;
    }

    public void setCourtId(int courtId) {
        this.courtId = courtId;
    }

    public double getPriceSEK() {
        return priceSEK;
    }

    public void setPriceSEK(double priceSEK) {
        this.priceSEK = priceSEK;
    }

    public double getPriceEUR() {
        return priceEUR;
    }

    public void setPriceEUR(double priceEUR) {
        this.priceEUR = priceEUR;
    }

    public String getCourtName() {
        return courtName;
    }

    public void setCourtName(String courtName) {
        this.courtName = courtName;
    }

    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }

    @Override
    public String toString() {
        return "Court{" +
                "courtId=" + courtId +
                ", priceSEK=" + priceSEK +
                ", priceEUR=" + priceEUR +
                ", courtName='" + courtName + '\'' +
                ", booked=" + booked +
                '}';
    }
}
