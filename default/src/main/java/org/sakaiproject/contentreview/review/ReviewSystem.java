package org.sakaiproject.contentreview.review;

import org.sakaiproject.contentreview2.Review;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: oucs0164
 * Date: 31/05/2013
 * Time: 10:32
 * To change this template use File | Settings | File Templates.
 */
public interface ReviewSystem {
    /**
     * <p>
     * Modifies the status of the review parameter once the submission is done (but do not save it).
     * </p>
     *
     * @param review
     */
    void submitReview(Review review);

    boolean isFileSupported(String contentId);

    boolean isResubmissionSupported();

    @Deprecated
    Object execute(Map<String, Object> parameters);

    void retrieveReview(Review review);
}
