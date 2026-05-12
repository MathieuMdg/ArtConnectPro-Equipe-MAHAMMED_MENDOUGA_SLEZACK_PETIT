package com.project.artconnect.persistence;

import com.project.artconnect.dao.WorkshopDao;
import com.project.artconnect.model.Artist;
import com.project.artconnect.model.Workshop;
import com.project.artconnect.util.ConnectionManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcWorkshopDao implements WorkshopDao {
    @Override
    public Optional<Workshop> findById(Long id) {
        String sql = baseSelect() + " WHERE w.workshop_id = ?";
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapWorkshop(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load workshop by id", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Workshop> findAll() {
        String sql = baseSelect() + " ORDER BY w.date";
        List<Workshop> workshops = new ArrayList<>();
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                workshops.add(mapWorkshop(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load workshops", e);
        }
        return workshops;
    }

    private String baseSelect() {
        return """
                SELECT w.title, w.date, w.duration_minutes, w.max_participants, w.price, w.location, w.description, w.level,
                       a.name AS artist_name, a.contact_email, a.city, a.bio
                FROM workshop w
                JOIN artist a ON a.artist_id = w.instructor_artist_id
                """;
    }

    private Workshop mapWorkshop(ResultSet rs) throws SQLException {
        Artist artist = new Artist();
        artist.setName(rs.getString("artist_name"));
        artist.setContactEmail(rs.getString("contact_email"));
        artist.setCity(rs.getString("city"));
        artist.setBio(rs.getString("bio"));

        Workshop workshop = new Workshop();
        workshop.setTitle(rs.getString("title"));
        Timestamp ts = rs.getTimestamp("date");
        if (ts != null) {
            workshop.setDate(ts.toLocalDateTime());
        }
        workshop.setDurationMinutes(rs.getInt("duration_minutes"));
        workshop.setMaxParticipants(rs.getInt("max_participants"));
        workshop.setPrice(rs.getDouble("price"));
        workshop.setLocation(rs.getString("location"));
        workshop.setDescription(rs.getString("description"));
        workshop.setLevel(rs.getString("level"));
        workshop.setInstructor(artist);
        return workshop;
    }
}
