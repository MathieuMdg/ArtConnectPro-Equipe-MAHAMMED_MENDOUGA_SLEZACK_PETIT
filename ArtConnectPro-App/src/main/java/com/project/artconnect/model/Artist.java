package com.project.artconnect.model;

import java.util.ArrayList;
import java.util.List;

public class Artist {

    private int artistId;

    private String name;

    private String bio;

    private Integer birthYear;

    private List<Discipline> disciplines =
            new ArrayList<>();

    private String contactEmail;

    private String phone;

    private String city;

    private String website;

    private String socialMedia;

    private boolean isActive;

    private List<Artwork> artworks =
            new ArrayList<>();

    public Artist() {
    }

    public Artist(
            int artistId,
            String name,
            String bio,
            Integer birthYear,
            String contactEmail,
            String phone,
            String city,
            String website,
            String socialMedia,
            boolean isActive
    ) {

        this.artistId = artistId;
        this.name = name;
        this.bio = bio;
        this.birthYear = birthYear;
        this.contactEmail = contactEmail;
        this.phone = phone;
        this.city = city;
        this.website = website;
        this.socialMedia = socialMedia;
        this.isActive = isActive;
    }

    // =====================================================
    // GETTERS / SETTERS
    // =====================================================

    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public List<Discipline> getDisciplines() {
        return disciplines;
    }

    public void setDisciplines(
            List<Discipline> disciplines
    ) {

        this.disciplines = disciplines;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(
            String contactEmail
    ) {

        this.contactEmail = contactEmail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getSocialMedia() {
        return socialMedia;
    }

    public void setSocialMedia(
            String socialMedia
    ) {

        this.socialMedia = socialMedia;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<Artwork> getArtworks() {
        return artworks;
    }

    public void setArtworks(
            List<Artwork> artworks
    ) {

        this.artworks = artworks;
    }

    @Override
    public String toString() {

        return name;
    }
}