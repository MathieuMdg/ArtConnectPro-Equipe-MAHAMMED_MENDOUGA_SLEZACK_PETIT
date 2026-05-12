package com.project.artconnect.persistence;

import com.project.artconnect.dao.GalleryDao;
import com.project.artconnect.model.Exhibition;
import com.project.artconnect.model.Gallery;
import com.project.artconnect.util.ConnectionManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcGalleryDao implements GalleryDao {

    @Override
    public Optional<Gallery> findById(Long id) {
        String sql = "SELECT gallery_id, name, address, owner_name, opening_hours, contact_phone, rating, website FROM gallery WHERE gallery_id = ?";
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Gallery gallery = mapGallery(rs);
                    gallery.setExhibitions(loadExhibitions(conn, id, gallery));
                    return Optional.of(gallery);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load gallery by id", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Gallery> findAll() {
        String sql = "SELECT gallery_id, name, address, owner_name, opening_hours, contact_phone, rating, website FROM gallery ORDER BY name";
        List<Gallery> galleries = new ArrayList<>();
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                long galleryId = rs.getLong("gallery_id");
                Gallery gallery = mapGallery(rs);
                gallery.setExhibitions(loadExhibitions(conn, galleryId, gallery));
                galleries.add(gallery);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load galleries", e);
        }
        return galleries;
    }

    private Gallery mapGallery(ResultSet rs) throws SQLException {
        Gallery gallery = new Gallery();
        gallery.setName(rs.getString("name"));
        gallery.setAddress(rs.getString("address"));
        gallery.setOwnerName(rs.getString("owner_name"));
        gallery.setOpeningHours(rs.getString("opening_hours"));
        gallery.setContactPhone(rs.getString("contact_phone"));
        gallery.setRating(rs.getDouble("rating"));
        gallery.setWebsite(rs.getString("website"));
        return gallery;
    }

    private List<Exhibition> loadExhibitions(Connection conn, long galleryId, Gallery gallery) throws SQLException {
        String sql = "SELECT title, start_date, end_date, description, curator_name, theme FROM exhibition WHERE gallery_id = ? ORDER BY start_date";
        List<Exhibition> exhibitions = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, galleryId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Exhibition exhibition = new Exhibition();
                    exhibition.setTitle(rs.getString("title"));
                    Date start = rs.getDate("start_date");
                    if (start != null) exhibition.setStartDate(start.toLocalDate());
                    Date end = rs.getDate("end_date");
                    if (end != null) exhibition.setEndDate(end.toLocalDate());
                    exhibition.setDescription(rs.getString("description"));
                    exhibition.setCuratorName(rs.getString("curator_name"));
                    exhibition.setTheme(rs.getString("theme"));
                    exhibition.setGallery(gallery);
                    exhibitions.add(exhibition);
                }
            }
        }
        return exhibitions;
    }
}
