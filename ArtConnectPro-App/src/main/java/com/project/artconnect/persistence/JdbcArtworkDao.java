package com.project.artconnect.persistence;

import com.project.artconnect.dao.ArtworkDao;
import com.project.artconnect.model.Artist;
import com.project.artconnect.model.Artwork;
import com.project.artconnect.model.ArtworkTag;
import com.project.artconnect.util.ConnectionManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** JDBC implementation for ArtworkDao. */
public class JdbcArtworkDao implements ArtworkDao {

    @Override
    public List<Artwork> findAll() {
        String sql = baseSelect() + " ORDER BY aw.title";
        List<Artwork> artworks = new ArrayList<>();
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Artwork artwork = mapArtwork(rs);
                artwork.setTags(loadTags(conn, artwork.getTitle()));
                artworks.add(artwork);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load artworks", e);
        }
        return artworks;
    }

    @Override
    public void save(Artwork artwork) {
        String sql = """
                INSERT INTO artwork(title, creation_year, type, medium, dimensions, description, price, status, artist_id)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, (SELECT artist_id FROM artist WHERE name = ?))
                """;
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            fillArtworkStatement(ps, artwork);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save artwork", e);
        }
    }

    @Override
    public void update(Artwork artwork) {
        String sql = """
                UPDATE artwork
                SET creation_year = ?, type = ?, medium = ?, dimensions = ?, description = ?, price = ?, status = ?,
                    artist_id = (SELECT artist_id FROM artist WHERE name = ?)
                WHERE title = ?
                """;
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            if (artwork.getCreationYear() == null) {
                ps.setNull(1, Types.INTEGER);
            } else {
                ps.setInt(1, artwork.getCreationYear());
            }
            ps.setString(2, artwork.getType());
            ps.setString(3, artwork.getMedium());
            ps.setString(4, artwork.getDimensions());
            ps.setString(5, artwork.getDescription());
            ps.setDouble(6, artwork.getPrice());
            ps.setString(7, artwork.getStatus() != null ? artwork.getStatus().name() : Artwork.Status.FOR_SALE.name());
            ps.setString(8, artwork.getArtist() != null ? artwork.getArtist().getName() : null);
            ps.setString(9, artwork.getTitle());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update artwork", e);
        }
    }

    @Override
    public void delete(String title) {
        String sql = "DELETE FROM artwork WHERE title = ?";
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, title);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete artwork", e);
        }
    }

    @Override
    public List<Artwork> findByArtistName(String artistName) {
        String sql = baseSelect() + " WHERE ar.name = ? ORDER BY aw.title";
        List<Artwork> artworks = new ArrayList<>();
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, artistName);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Artwork artwork = mapArtwork(rs);
                    artwork.setTags(loadTags(conn, artwork.getTitle()));
                    artworks.add(artwork);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load artworks by artist", e);
        }
        return artworks;
    }

    private String baseSelect() {
        return """
                SELECT aw.title, aw.creation_year, aw.type, aw.medium, aw.dimensions, aw.description, aw.price, aw.status,
                       ar.name AS artist_name, ar.bio AS artist_bio, ar.birth_year AS artist_birth_year,
                       ar.contact_email, ar.phone, ar.city, ar.website, ar.social_media, ar.is_active
                FROM artwork aw
                JOIN artist ar ON ar.artist_id = aw.artist_id
                """;
    }

    private Artwork mapArtwork(ResultSet rs) throws SQLException {
        Artist artist = new Artist();
        artist.setName(rs.getString("artist_name"));
        artist.setBio(rs.getString("artist_bio"));
        int birthYear = rs.getInt("artist_birth_year");
        artist.setBirthYear(rs.wasNull() ? null : birthYear);
        artist.setContactEmail(rs.getString("contact_email"));
        artist.setPhone(rs.getString("phone"));
        artist.setCity(rs.getString("city"));
        artist.setWebsite(rs.getString("website"));
        artist.setSocialMedia(rs.getString("social_media"));
        artist.setActive(rs.getBoolean("is_active"));

        Artwork artwork = new Artwork();
        artwork.setTitle(rs.getString("title"));
        int creationYear = rs.getInt("creation_year");
        artwork.setCreationYear(rs.wasNull() ? null : creationYear);
        artwork.setType(rs.getString("type"));
        artwork.setMedium(rs.getString("medium"));
        artwork.setDimensions(rs.getString("dimensions"));
        artwork.setDescription(rs.getString("description"));
        artwork.setPrice(rs.getDouble("price"));
        String status = rs.getString("status");
        artwork.setStatus(status != null ? Artwork.Status.valueOf(status) : Artwork.Status.FOR_SALE);
        artwork.setArtist(artist);
        return artwork;
    }

    private List<ArtworkTag> loadTags(Connection conn, String artworkTitle) throws SQLException {
        String sql = """
                SELECT t.name
                FROM artwork_tag t
                JOIN artwork_tag_map atm ON atm.tag_id = t.tag_id
                JOIN artwork aw ON aw.artwork_id = atm.artwork_id
                WHERE aw.title = ?
                ORDER BY t.name
                """;
        List<ArtworkTag> tags = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, artworkTitle);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    tags.add(new ArtworkTag(rs.getString("name")));
                }
            }
        }
        return tags;
    }

    private void fillArtworkStatement(PreparedStatement ps, Artwork artwork) throws SQLException {
        ps.setString(1, artwork.getTitle());
        if (artwork.getCreationYear() == null) {
            ps.setNull(2, Types.INTEGER);
        } else {
            ps.setInt(2, artwork.getCreationYear());
        }
        ps.setString(3, artwork.getType());
        ps.setString(4, artwork.getMedium());
        ps.setString(5, artwork.getDimensions());
        ps.setString(6, artwork.getDescription());
        ps.setDouble(7, artwork.getPrice());
        ps.setString(8, artwork.getStatus() != null ? artwork.getStatus().name() : Artwork.Status.FOR_SALE.name());
        ps.setString(9, artwork.getArtist() != null ? artwork.getArtist().getName() : null);
    }
}
