package com.project.artconnect.model;

import java.time.LocalDateTime;

public class Workshop {

    private int workshopId;

    private String title;

    private LocalDateTime date;

    private int durationMinutes;

    private int maxParticipants;

    private double price;

    private Artist instructor;

    private String location;

    private String description;

    private String level;

    public Workshop() {
    }

    public Workshop(
            int workshopId,
            String title,
            LocalDateTime date,
            int durationMinutes,
            int maxParticipants,
            double price,
            Artist instructor,
            String location,
            String description,
            String level
    ) {

        this.workshopId = workshopId;
        this.title = title;
        this.date = date;
        this.durationMinutes = durationMinutes;
        this.maxParticipants = maxParticipants;
        this.price = price;
        this.instructor = instructor;
        this.location = location;
        this.description = description;
        this.level = level;
    }

    public int getWorkshopId() {
        return workshopId;
    }

    public void setWorkshopId(int workshopId) {
        this.workshopId = workshopId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(
            int durationMinutes
    ) {

        this.durationMinutes = durationMinutes;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(
            int maxParticipants
    ) {

        this.maxParticipants = maxParticipants;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Artist getInstructor() {
        return instructor;
    }

    public void setInstructor(Artist instructor) {
        this.instructor = instructor;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(
            String description
    ) {

        this.description = description;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public String toString() {

        return title + " | " + date.toLocalDate();
    }
    
}