package com.project.artconnect.persistence;

import com.project.artconnect.dao.ArtistDao;
import com.project.artconnect.model.Artist;
import com.project.artconnect.model.Discipline;
import com.project.artconnect.util.ConnectionManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** JDBC implementation for ArtistDao. */
public class JdbcArtistDao implements ArtistDao {

    @Override
    public List<Artist> findAll() {
        String sql = "SELECT name, bio, birth_year, contact_email, phone, city, website, social_media, is_active FROM artist ORDER BY name";
        List<Artist> artists = new ArrayList<>();

        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Artist artist = mapArtist(rs);
                artist.setDisciplines(loadDisciplines(conn, artist.getName()));
                artists.add(artist);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load artists", e);
        }
        return artists;
    }

    @Override
    public void save(Artist artist) {
        String sql = """
                INSERT INTO artist(name, bio, birth_year, contact_email, phone, city, website, social_media, is_active)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            fillArtistStatement(ps, artist);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save artist", e);
        }
    }

    @Override
    public void update(Artist artist) {
        String sql = """
                UPDATE artist
                SET bio = ?, birth_year = ?, contact_email = ?, phone = ?, city = ?, website = ?, social_media = ?, is_active = ?
                WHERE name = ?
                """;

        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, artist.getBio());
            if (artist.getBirthYear() == null) {
                ps.setNull(2, Types.INTEGER);
            } else {
                ps.setInt(2, artist.getBirthYear());
            }
            ps.setString(3, artist.getContactEmail());
            ps.setString(4, artist.getPhone());
            ps.setString(5, artist.getCity());
            ps.setString(6, artist.getWebsite());
            ps.setString(7, artist.getSocialMedia());
            ps.setBoolean(8, artist.isActive());
            ps.setString(9, artist.getName());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update artist", e);
        }
    }

    @Override
    public void delete(String artistName) {
        String sql = "DELETE FROM artist WHERE name = ?";
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, artistName);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete artist", e);
        }
    }

    @Override
    public List<Artist> findByCity(String city) {
        String sql = "SELECT name, bio, birth_year, contact_email, phone, city, website, social_media, is_active FROM artist WHERE city = ? ORDER BY name";
        List<Artist> artists = new ArrayList<>();

        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, city);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Artist artist = mapArtist(rs);
                    artist.setDisciplines(loadDisciplines(conn, artist.getName()));
                    artists.add(artist);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load artists by city", e);
        }
        return artists;
    }

    private Artist mapArtist(ResultSet rs) throws SQLException {
        Artist artist = new Artist();
        artist.setName(rs.getString("name"));
        int birthYear = rs.getInt("birth_year");
        artist.setBirthYear(rs.wasNull() ? null : birthYear);
        artist.setBio(rs.getString("bio"));
        artist.setContactEmail(rs.getString("contact_email"));
        artist.setPhone(rs.getString("phone"));
        artist.setCity(rs.getString("city"));
        artist.setWebsite(rs.getString("website"));
        artist.setSocialMedia(rs.getString("social_media"));
        artist.setActive(rs.getBoolean("is_active"));
        return artist;
    }

    private List<Discipline> loadDisciplines(Connection conn, String artistName) throws SQLException {
        String sql = """
                SELECT d.name
                FROM discipline d
                JOIN artist_discipline ad ON ad.discipline_id = d.discipline_id
                JOIN artist a ON a.artist_id = ad.artist_id
                WHERE a.name = ?
                ORDER BY d.name
                """;
        List<Discipline> disciplines = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, artistName);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    disciplines.add(new Discipline(rs.getString("name")));
                }
            }
        }
        return disciplines;
    }

    private void fillArtistStatement(PreparedStatement ps, Artist artist) throws SQLException {
        ps.setString(1, artist.getName());
        ps.setString(2, artist.getBio());
        if (artist.getBirthYear() == null) {
            ps.setNull(3, Types.INTEGER);
        } else {
            ps.setInt(3, artist.getBirthYear());
        }
        ps.setString(4, artist.getContactEmail());
        ps.setString(5, artist.getPhone());
        ps.setString(6, artist.getCity());
        ps.setString(7, artist.getWebsite());
        ps.setString(8, artist.getSocialMedia());
        ps.setBoolean(9, artist.isActive());
    }
}
