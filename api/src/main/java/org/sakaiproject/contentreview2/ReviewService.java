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
public interface ReviewService {
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
     * Sends a message to the review system.
     * <p>
     * Some review systems (such as Turnitin) need additional operations before being usable
     * (an assignment may have to be created manually for example), this method allows to directly send an order from
     * the tool using the content review service to the review system.
     * </p>
     * <p>
     * It is a design flaw (such a method shouldn't exist) but is helpful with tools that are currently
     * (Sakai 2.10) available and rely on a content review system.<br />
     * A more conventional behaviour would be having the tool using the content review service to do the calls to the
     * review system directly (through another service specific to the review system).
     * </p>
     * <p>
     * For all intents and purposes this method should <b>NEVER</b> be used.
     * </p>
     *
     * @param parameters parameters to send to the review system.
     * @return an Object that can be null.
     * @deprecated
     */
    @Deprecated
    Object executeOnReviewSystem(Map<String, Object> parameters);

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
