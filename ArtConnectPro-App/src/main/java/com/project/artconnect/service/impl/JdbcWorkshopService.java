package com.project.artconnect.service.impl;

import com.project.artconnect.dao.WorkshopDao;
import com.project.artconnect.model.Booking;
import com.project.artconnect.model.CommunityMember;
import com.project.artconnect.model.Workshop;
import com.project.artconnect.service.WorkshopService;
import com.project.artconnect.util.ConnectionManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class JdbcWorkshopService implements WorkshopService {
    private final WorkshopDao workshopDao;

    public JdbcWorkshopService(WorkshopDao workshopDao) {
        this.workshopDao = workshopDao;
    }

    @Override
    public List<Workshop> getAllWorkshops() {
        return workshopDao.findAll();
    }

    @Override
    public Optional<Workshop> getWorkshopByTitle(String title) {
        return workshopDao.findAll().stream()
                .filter(w -> w.getTitle() != null && w.getTitle().equalsIgnoreCase(title))
                .findFirst();
    }

    @Override
    public void bookWorkshop(Workshop workshop, CommunityMember member) {
        if (workshop == null || member == null) {
            return;
        }

        String sql = """
                INSERT INTO booking(workshop_id, member_id, booking_date, payment_status)
                VALUES (
                    (SELECT workshop_id FROM workshop WHERE title = ?),
                    (SELECT member_id FROM community_member WHERE email = ?),
                    NOW(),
                    'PENDING'
                )
                """;

        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, workshop.getTitle());
            ps.setString(2, member.getEmail());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create booking", e);
        }
    }

    @Override
    public List<Booking> getBookingsByMember(CommunityMember member) {
        if (member == null || member.getEmail() == null) {
            return Collections.emptyList();
        }

        String sql = """
                SELECT b.booking_date, b.payment_status,
                       w.title, w.date, w.price, w.level
                FROM booking b
                JOIN community_member m ON m.member_id = b.member_id
                JOIN workshop w ON w.workshop_id = b.workshop_id
                WHERE m.email = ?
                ORDER BY b.booking_date DESC
                """;
        List<Booking> bookings = new ArrayList<>();
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, member.getEmail());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Workshop workshop = new Workshop();
                    workshop.setTitle(rs.getString("title"));
                    Timestamp workshopDate = rs.getTimestamp("date");
                    if (workshopDate != null) {
                        workshop.setDate(workshopDate.toLocalDateTime());
                    }
                    workshop.setPrice(rs.getDouble("price"));
                    workshop.setLevel(rs.getString("level"));

                    Booking booking = new Booking();
                    booking.setMember(member);
                    booking.setWorkshop(workshop);
                    Timestamp bookingDate = rs.getTimestamp("booking_date");
                    if (bookingDate != null) {
                        booking.setBookingDate(bookingDate.toLocalDateTime());
                    }
                    booking.setPaymentStatus(rs.getString("payment_status"));
                    bookings.add(booking);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load bookings", e);
        }
        return bookings;
    }
}
