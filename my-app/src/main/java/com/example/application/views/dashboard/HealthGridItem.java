package com.example.application.views.dashboard;

import java.time.LocalDate;

/**
 * Simple DTO class for the inbox list to demonstrate complex object data
 */
public class HealthGridItem {

    private LocalDate date;
    private String name;
    private String country;
    private String status;
    private String theme;

    public HealthGridItem() {

    }

    public HealthGridItem(LocalDate date, String name, String country, String status, String theme) {
        this.date = date;
        this.name = name;
        this.country = country;
        this.status = status;
        this.theme = theme;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }
}
