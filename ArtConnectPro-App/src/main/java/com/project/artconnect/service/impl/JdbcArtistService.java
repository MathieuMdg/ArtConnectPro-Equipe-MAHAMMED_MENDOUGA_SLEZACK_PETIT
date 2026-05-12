package com.project.artconnect.service.impl;

import com.project.artconnect.dao.ArtistDao;
import com.project.artconnect.model.Artist;
import com.project.artconnect.model.Discipline;
import com.project.artconnect.service.ArtistService;
import com.project.artconnect.util.ConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class JdbcArtistService implements ArtistService {
    private final ArtistDao artistDao;

    public JdbcArtistService(ArtistDao artistDao) {
        this.artistDao = artistDao;
    }

    @Override
    public List<Artist> getAllArtists() {
        return artistDao.findAll();
    }

    @Override
    public Optional<Artist> getArtistByName(String name) {
        return artistDao.findAll().stream()
                .filter(a -> a.getName() != null && a.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    @Override
    public void createArtist(Artist artist) {
        artistDao.save(artist);
    }

    @Override
    public void updateArtist(Artist artist) {
        artistDao.update(artist);
    }

    @Override
    public void deleteArtist(String name) {
        artistDao.delete(name);
    }

    @Override
    public List<Discipline> getAllDisciplines() {
        List<Discipline> disciplines = new ArrayList<>();
        String sql = "SELECT name FROM discipline ORDER BY name";
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                disciplines.add(new Discipline(rs.getString("name")));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load disciplines", e);
        }
        return disciplines;
    }

    @Override
    public List<Artist> searchArtists(String query, String disciplineName, String city) {
        String normalizedQuery = query != null ? query.trim().toLowerCase() : null;
        return artistDao.findAll().stream()
                .filter(a -> normalizedQuery == null || normalizedQuery.isEmpty() ||
                        (a.getName() != null && a.getName().toLowerCase().contains(normalizedQuery)))
                .filter(a -> city == null || city.isBlank() ||
                        (a.getCity() != null && a.getCity().equalsIgnoreCase(city)))
                .filter(a -> disciplineName == null || disciplineName.isBlank() ||
                        a.getDisciplines().stream().anyMatch(d -> d.getName().equalsIgnoreCase(disciplineName)))
                .collect(Collectors.toList());
    }
}
