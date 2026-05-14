package com.project.artconnect.model;

import java.time.LocalDateTime;

public class Booking {

    private int bookingId;

    private Workshop workshop;

    private CommunityMember member;

    private LocalDateTime bookingDate;

    private String paymentStatus;

    public Booking() {
    }

    public Booking(
            int bookingId,
            Workshop workshop,
            CommunityMember member,
            LocalDateTime bookingDate,
            String paymentStatus
    ) {

        this.bookingId = bookingId;
        this.workshop = workshop;
        this.member = member;
        this.bookingDate = bookingDate;
        this.paymentStatus = paymentStatus;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public Workshop getWorkshop() {
        return workshop;
    }

    public void setWorkshop(Workshop workshop) {
        this.workshop = workshop;
    }

    public CommunityMember getMember() {
        return member;
    }

    public void setMember(
            CommunityMember member
    ) {

        this.member = member;
    }

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(
            LocalDateTime bookingDate
    ) {

        this.bookingDate = bookingDate;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(
            String paymentStatus
    ) {

        this.paymentStatus = paymentStatus;
    }

    @Override
    public String toString() {

        return workshop.getTitle()
                + " | "
                + member.getName()
                + " | "
                + paymentStatus;
    }
}