package org.sakaiproject.contentreview.queue;

import org.sakaiproject.contentreview2.Review;

/**
 * Queue reviews
 */
public interface ReviewQueue {
    void queueForSubmission(Review review);
    void queueForRetrieval(Review review);
}
