package com.example.offresvoyage.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class StatisticsDto {
    private long totalOffers;
    private double totalRevenue;
    private double avgPrice;
    private double avgCapacity;
    private int totalCapacity;
    private Map<Integer, Double> yearlyRevenue;

    // Getters and setters for yearlyRevenue
    public Map<Integer, Double> getYearlyRevenue() {
        return yearlyRevenue;
    }

    public void setYearlyRevenue(Map<Integer, Double> yearlyRevenue) {
        this.yearlyRevenue = yearlyRevenue;
    }

}
