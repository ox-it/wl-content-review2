package org.sakaiproject.contentreview2;

/**
 * Service allowing to review content submitted by users.
 * <p>
 * A content review implementation will be in charge of handling user submissions and provide a score
 * for each submission.<br />
 * Usually the score is based on plagiarism detection, but other detections can be applied.
 * </p>
 */
public interface ContentReviewService {
    /**
     * Retrieves a review based on a submission id.
     *
     * @param submissionId unique identifier of the submission.
     * @return the review associated with the submission.
     */
    Review getReview(String submissionId);
}
