package org.sakaiproject.contentreview2;

import java.util.Collection;
import java.util.Map;

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

    /**
     * Retrieves the reviews for the given submissions.
     * <p>
     * If a review can't be found it will not be returned in the map.
     * </p>
     *
     * @param submissionIds identifiers of the submissions for which reviews have been created.
     * @return reviews mapped by submission id.
     */
    Map<String, Review> getReviewsById(Collection<String> submissionIds);

    /**
     * Retrieves the reviews for a given assignment.
     *
     * @param assignmentId identifier of the assignment to which the review is attached.
     * @return reviews for that assignment.
     */
    Collection<Review> getReviewsForAssignment(String assignmentId);

    /**
     * Starts the review process (creates the review).
     *
     * @param review to start.
     * @throws IllegalArgumentException if a review with the same id already exists.
     */
    //TODO: Change the exception?
    void startReview(Review review) throws IllegalArgumentException;

    /**
     * Returns {@code true} if the site can use the content review service.
     *
     * @param siteId identifier of the site which will use content review.
     * @return true if the site can use the content review service, false otherwise.
     */
    boolean isSiteSupported(String siteId);

    /**
     * Returns {@code true} if the file is supported by the content review system.
     *
     * @param contentId identifier of the file which will be sent to the content review system.
     * @return true if the file can be analysed through the content review service, false otherwise.
     */
    boolean isFileSupported(String contentId);

    /**
     * Returns {@code true} if it is possible to resubmit documents for a review.
     *
     * @return true if resubmissions are supported, false otherwise.
     */
    boolean isResubmissionSupported();
}
