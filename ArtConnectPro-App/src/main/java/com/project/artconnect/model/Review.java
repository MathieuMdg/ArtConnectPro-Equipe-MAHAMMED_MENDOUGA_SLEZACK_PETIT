package com.project.artconnect.model;

import java.time.LocalDate;

public class Review {

    private int reviewId;

    private CommunityMember reviewer;

    private Artwork artwork;

    private int rating;

    private String comment;

    private LocalDate reviewDate;

    public Review() {
    }

    public Review(
            int reviewId,
            CommunityMember reviewer,
            Artwork artwork,
            int rating,
            String comment,
            LocalDate reviewDate
    ) {

        this.reviewId = reviewId;
        this.reviewer = reviewer;
        this.artwork = artwork;
        this.rating = rating;
        this.comment = comment;
        this.reviewDate = reviewDate;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public CommunityMember getReviewer() {
        return reviewer;
    }

    public void setReviewer(
            CommunityMember reviewer
    ) {

        this.reviewer = reviewer;
    }

    public Artwork getArtwork() {
        return artwork;
    }

    public void setArtwork(Artwork artwork) {
        this.artwork = artwork;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDate getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(
            LocalDate reviewDate
    ) {

        this.reviewDate = reviewDate;
    }

    @Override
    public String toString() {

        return artwork.getTitle()
                + " | "
                + rating
                + "/5 | "
                + reviewer.getName();
    }
}