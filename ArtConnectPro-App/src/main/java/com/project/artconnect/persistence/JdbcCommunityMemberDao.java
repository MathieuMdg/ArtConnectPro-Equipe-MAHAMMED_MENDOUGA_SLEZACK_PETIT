package com.project.artconnect.persistence;

import com.project.artconnect.dao.CommunityMemberDao;
import com.project.artconnect.model.CommunityMember;
import com.project.artconnect.model.Discipline;
import com.project.artconnect.util.ConnectionManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcCommunityMemberDao implements CommunityMemberDao {
    @Override
    public Optional<CommunityMember> findById(Long id) {
        String sql = "SELECT member_id, name, email, birth_year, phone, city, membership_type FROM community_member WHERE member_id = ?";
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    CommunityMember member = mapMember(rs);
                    member.setFavoriteDisciplines(loadFavoriteDisciplines(conn, id));
                    return Optional.of(member);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load member by id", e);
        }
        return Optional.empty();
    }

    @Override
    public List<CommunityMember> findAll() {
        String sql = "SELECT member_id, name, email, birth_year, phone, city, membership_type FROM community_member ORDER BY name";
        List<CommunityMember> members = new ArrayList<>();
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                long memberId = rs.getLong("member_id");
                CommunityMember member = mapMember(rs);
                member.setFavoriteDisciplines(loadFavoriteDisciplines(conn, memberId));
                members.add(member);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load members", e);
        }
        return members;
    }

    private CommunityMember mapMember(ResultSet rs) throws SQLException {
        CommunityMember member = new CommunityMember();
        member.setName(rs.getString("name"));
        member.setEmail(rs.getString("email"));
        int birthYear = rs.getInt("birth_year");
        member.setBirthYear(rs.wasNull() ? null : birthYear);
        member.setPhone(rs.getString("phone"));
        member.setCity(rs.getString("city"));
        member.setMembershipType(rs.getString("membership_type"));
        return member;
    }

    private List<Discipline> loadFavoriteDisciplines(Connection conn, long memberId) throws SQLException {
        String sql = """
                SELECT d.name
                FROM discipline d
                JOIN member_favorite_discipline mfd ON mfd.discipline_id = d.discipline_id
                WHERE mfd.member_id = ?
                ORDER BY d.name
                """;
        List<Discipline> disciplines = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, memberId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    disciplines.add(new Discipline(rs.getString("name")));
                }
            }
        }
        return disciplines;
    }
}
