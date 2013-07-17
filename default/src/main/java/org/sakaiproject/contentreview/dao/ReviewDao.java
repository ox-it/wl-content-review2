package org.sakaiproject.contentreview.dao;

import org.sakaiproject.contentreview2.Review;

import java.util.Collection;

public interface ReviewDao {
    Review getReview(String submissionId);

    Collection<Review> getReviewsById(Collection<String> submissionIds);

    Collection<Review> getReviewsForAssignment(String assignmentId);

    void saveReview(Review review);
}
