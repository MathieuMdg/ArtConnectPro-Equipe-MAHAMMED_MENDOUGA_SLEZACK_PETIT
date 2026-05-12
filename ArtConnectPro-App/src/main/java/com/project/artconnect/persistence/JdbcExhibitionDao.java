package com.project.artconnect.persistence;

import com.project.artconnect.dao.ExhibitionDao;
import com.project.artconnect.model.Exhibition;
import com.project.artconnect.model.Gallery;
import com.project.artconnect.util.ConnectionManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcExhibitionDao implements ExhibitionDao {
    @Override
    public List<Exhibition> findAll() {
        String sql = """
                SELECT e.title, e.start_date, e.end_date, e.description, e.curator_name, e.theme,
                       g.name AS gallery_name, g.address, g.rating
                FROM exhibition e
                JOIN gallery g ON g.gallery_id = e.gallery_id
                ORDER BY e.start_date
                """;
        List<Exhibition> exhibitions = new ArrayList<>();
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                exhibitions.add(mapExhibition(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load exhibitions", e);
        }
        return exhibitions;
    }

    @Override
    public void save(Exhibition exhibition) {
        String sql = """
                INSERT INTO exhibition(title, start_date, end_date, description, curator_name, theme, gallery_id)
                VALUES (?, ?, ?, ?, ?, ?, (SELECT gallery_id FROM gallery WHERE name = ?))
                """;
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, exhibition.getTitle());
            ps.setDate(2, exhibition.getStartDate() != null ? Date.valueOf(exhibition.getStartDate()) : null);
            ps.setDate(3, exhibition.getEndDate() != null ? Date.valueOf(exhibition.getEndDate()) : null);
            ps.setString(4, exhibition.getDescription());
            ps.setString(5, exhibition.getCuratorName());
            ps.setString(6, exhibition.getTheme());
            ps.setString(7, exhibition.getGallery() != null ? exhibition.getGallery().getName() : null);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save exhibition", e);
        }
    }

    @Override
    public void update(Exhibition exhibition) {
        String sql = """
                UPDATE exhibition
                SET start_date = ?, end_date = ?, description = ?, curator_name = ?, theme = ?,
                    gallery_id = (SELECT gallery_id FROM gallery WHERE name = ?)
                WHERE title = ?
                """;
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, exhibition.getStartDate() != null ? Date.valueOf(exhibition.getStartDate()) : null);
            ps.setDate(2, exhibition.getEndDate() != null ? Date.valueOf(exhibition.getEndDate()) : null);
            ps.setString(3, exhibition.getDescription());
            ps.setString(4, exhibition.getCuratorName());
            ps.setString(5, exhibition.getTheme());
            ps.setString(6, exhibition.getGallery() != null ? exhibition.getGallery().getName() : null);
            ps.setString(7, exhibition.getTitle());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update exhibition", e);
        }
    }

    @Override
    public void delete(String title) {
        String sql = "DELETE FROM exhibition WHERE title = ?";
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, title);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete exhibition", e);
        }
    }

    private Exhibition mapExhibition(ResultSet rs) throws SQLException {
        Gallery gallery = new Gallery();
        gallery.setName(rs.getString("gallery_name"));
        gallery.setAddress(rs.getString("address"));
        gallery.setRating(rs.getDouble("rating"));

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
        return exhibition;
    }
}
