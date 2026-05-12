package com.project.artconnect.service.impl;

import com.project.artconnect.dao.CommunityMemberDao;
import com.project.artconnect.model.Artwork;
import com.project.artconnect.model.CommunityMember;
import com.project.artconnect.model.Review;
import com.project.artconnect.service.CommunityService;
import com.project.artconnect.util.ConnectionManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class JdbcCommunityService implements CommunityService {
    private final CommunityMemberDao communityMemberDao;

    public JdbcCommunityService(CommunityMemberDao communityMemberDao) {
        this.communityMemberDao = communityMemberDao;
    }

    @Override
    public List<CommunityMember> getAllMembers() {
        return communityMemberDao.findAll();
    }

    @Override
    public Optional<CommunityMember> getMemberByName(String name) {
        return communityMemberDao.findAll().stream()
                .filter(m -> m.getName() != null && m.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    @Override
    public List<Review> getReviewsByMember(CommunityMember member) {
        if (member == null || member.getEmail() == null) {
            return Collections.emptyList();
        }

        String sql = """
                SELECT r.rating, r.comment, r.review_date, a.title
                FROM review r
                JOIN community_member m ON m.member_id = r.reviewer_member_id
                JOIN artwork a ON a.artwork_id = r.artwork_id
                WHERE m.email = ?
                ORDER BY r.review_date DESC
                """;
        List<Review> reviews = new ArrayList<>();
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, member.getEmail());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Artwork artwork = new Artwork();
                    artwork.setTitle(rs.getString("title"));

                    Review review = new Review();
                    review.setReviewer(member);
                    review.setArtwork(artwork);
                    review.setRating(rs.getInt("rating"));
                    review.setComment(rs.getString("comment"));
                    Date reviewDate = rs.getDate("review_date");
                    if (reviewDate != null) {
                        review.setReviewDate(reviewDate.toLocalDate());
                    }
                    reviews.add(review);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load reviews", e);
        }
        return reviews;
    }
}
