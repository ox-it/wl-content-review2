package org.sakaiproject.contentreview.queue;

import org.sakaiproject.contentreview.dao.ReviewDao;
import org.sakaiproject.contentreview.review.ReviewSystem;
import org.sakaiproject.contentreview2.Review;

/**
 * Created with IntelliJ IDEA.
 * User: oucs0164
 * Date: 12/06/2013
 * Time: 11:37
 * To change this template use File | Settings | File Templates.
 */
public class SimpleReviewQueue implements ReviewQueue {
    private ReviewSystem reviewSystem;
    private ReviewDao reviewDao;

    @Override
    public void queueForSubmission(Review review) {
        reviewSystem.submitReview(review);
        reviewDao.saveReview(review);
        if (review.getStatus() != Review.Status.FAILED)
            queueForRetrieval(review);
    }

    @Override
    public void queueForRetrieval(Review review) {
        reviewSystem.retrieveReview(review);
        reviewDao.saveReview(review);
    }
}
