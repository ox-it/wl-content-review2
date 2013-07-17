package org.sakaiproject.contentreview;

import org.sakaiproject.contentreview.advisor.SiteAdvisor;
import org.sakaiproject.contentreview.dao.ReviewDao;
import org.sakaiproject.contentreview.queue.ReviewQueue;
import org.sakaiproject.contentreview.review.ReviewSystem;
import org.sakaiproject.contentreview2.Review;
import org.sakaiproject.contentreview2.ReviewService;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DefaultReviewService implements ReviewService {
    private ReviewDao reviewDao;
    private ReviewQueue reviewQueue;
    private ReviewSystem reviewSystem;
    private SiteAdvisor siteAdvisor;

    @Override
    public Review getReview(String submissionId) {
        return reviewDao.getReview(submissionId);
    }

    @Override
    public Map<String, Review> getReviewsById(Collection<String> submissionIds) {
        Map<String, Review> reviews = new HashMap<String, Review>();
        for (Review review : reviewDao.getReviewsById(submissionIds)) {
            reviews.put(review.getSubmissionId(), review);
        }
        return Collections.unmodifiableMap(reviews);
    }

    @Override
    public Collection<Review> getReviewsForAssignment(String assignmentId) {
        return Collections.unmodifiableCollection(reviewDao.getReviewsForAssignment(assignmentId));
    }

    @Override
    public void startReview(Review review) throws IllegalArgumentException {
        if (!isSiteSupported(review.getSiteId())) {
            throw new IllegalArgumentException("This site can't use the content review system");
        }
        if (getReview(review.getSubmissionId()) != null) {
            throw new IllegalArgumentException("This review already exists");
        }

        reviewDao.saveReview(review);
        reviewQueue.queue(review);
    }

    @Deprecated
    @Override
    public Object executeOnReviewSystem(Map<String, Object> parameters) {
        if (!isSiteSupported(review.getSiteId())) {
            throw new IllegalArgumentException("This site can't use the content review system");
        }
        return reviewSystem.execute(parameters);
    }

    @Override
    public boolean isSiteSupported(String siteId) {
        return siteAdvisor.isSiteSupported(siteId);
    }

    @Override
    public boolean isFileSupported(String contentId) {
        return reviewSystem.isFileSupported(contentId);
    }

    @Override
    public boolean isResubmissionSupported() {
        return reviewSystem.isResubmissionSupported();
    }

    public void setReviewDao(ReviewDao reviewDao) {
        this.reviewDao = reviewDao;
    }

    public void setReviewSystem(ReviewSystem reviewSystem) {
        this.reviewSystem = reviewSystem;
    }

    public void setReviewQueue(ReviewQueue reviewQueue) {
        this.reviewQueue = reviewQueue;
    }

    public void setSiteAdvisor(SiteAdvisor siteAdvisor) {
        this.siteAdvisor = siteAdvisor;
    }
}
