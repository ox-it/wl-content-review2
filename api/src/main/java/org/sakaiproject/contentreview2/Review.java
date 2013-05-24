package org.sakaiproject.contentreview2;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Review generated by the content review system.
 */
public class Review {
    /**
     * Unique identifier of the submission, can also be used as the unique identifier of the review.
     */
    private final String submissionId;
    /**
     * Unique identifier of the assignment to which the submission is linked.
     * <p>
     * The assignment Id can be used by the review system to group reviews and making them accessible.<br />
     * It is possible to have a null value for the assignment Id.
     * </p>
     * <p>
     * As the assignment id is provided by the code calling the content review service, and multiple tools might call
     * the review service, it is recommended to ensure the uniqueness of the assignment id by either adding the name
     * of the calling tool, or using a {@link UUID}.
     * </p>
     */
    private final String assignmentId;
    /**
     * Unique identifier of the user who sent the submission.
     * <p>
     * The userId isn't mandatory but could be used by the review system to identify the work of a specific user
     * when the review is being made.
     * </p>
     */
    private final String userId;
    /**
     * Unique identifier of the site in which the submission was made.
     * <p>
     * The siteId isn't mandatory but could be used by the review system to identify the class in which the submission
     * has been made.
     * </p>
     */
    private final String siteId;
    /**
     * Identifier of the files sent in the submission.
     */
    private final Set<String> filesId;
    /**
     * Score of the review.
     * <p>
     *     The score of a review has to be between 0 and 100.
     * </p>
     */
    private int score;
    /**
     * Status of the review.
     */
    private Status status;

    /**
     * Creates a review for the given submission.
     *
     * @param submissionId identifier of the submission.
     * @param assignmentId identifier of the assignment to which the submission is linked (can be null).
     * @param userId       identifier of the user who sent the submission (can be null).
     * @param siteId       identifier of the site in which the assignment is (can be null).
     * @param filesId      identifiers of the files sent in the submission.
     */
    public Review(String submissionId, String assignmentId, String userId, String siteId, Set<String> filesId) {
        this.submissionId = submissionId;
        this.assignmentId = assignmentId;
        this.userId = userId;
        this.siteId = siteId;
        this.filesId = Collections.unmodifiableSet(new HashSet<String>(filesId));
    }

    /**
     * Creates a review and generates its submission id.
     *
     * @param assignmentId identifier of the assignment to which the submission is linked (can be null).
     * @param userId       identifier of the user who sent the submission (can be null).
     * @param siteId       identifier of the site in which the assignment is (can be null).
     * @param filesId      identifiers of the files sent in the submission.
     * @see #Review(String, String, String, String, java.util.Set
     */
    public Review(String assignmentId, String userId, String siteId, Set<String> filesId) {
        this(UUID.randomUUID().toString(), assignmentId, userId, siteId, filesId);
    }

    public String getSubmissionId() {
        return submissionId;
    }

    public String getAssignmentId() {
        return assignmentId;
    }

    public String getUserId() {
        return userId;
    }

    public String getSiteId() {
        return siteId;
    }

    public Set<String> getFilesId() {
        return filesId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Status of a review.
     */
    public static enum Status {
        /**
         * The review has been created but hasn't been sent to the review system yet.
         */
        QUEUED,
        /**
         * The review has been sent to the review system.
         */
        SUBMITTED,
        /**
         * The review report has come back.
         */
        RETRIEVED,
        /**
         * Something went wrong within the review system. More details can be provided by the implementation.
         */
        FAILED
    }
}
