package org.sakaiproject.contentreview2.compatibility;

import org.sakaiproject.contentreview.exception.QueueException;
import org.sakaiproject.contentreview.exception.ReportException;
import org.sakaiproject.contentreview.exception.SubmissionException;
import org.sakaiproject.contentreview.exception.TransientSubmissionException;
import org.sakaiproject.contentreview.model.ContentReviewItem;
import org.sakaiproject.contentreview.service.ContentReviewService;
import org.sakaiproject.contentreview2.Review;
import org.sakaiproject.contentreview2.ReviewService;

import java.util.*;

/**
 * Implementation of the previous ContentReview API which delegates calls to the new API.
 */
public class BackwardContentReviewService implements ContentReviewService {
    public static final long STATUS_QUEUED = 1L;
    public static final long STATUS_SUBMITTED = 2L;
    public static final long STATUS_RETRIEVED = 3L;
    public static final long STATUS_FAILED = 8L;
    private ReviewService reviewService;

    @Override
    public void queueContent(String userId, String siteId, String taskId, String contentId) throws QueueException {
        Review review = new Review(contentId, taskId, userId, siteId, Collections.singleton(contentId));
        reviewService.startReview(review);
    }

    @Override
    public int getReviewScore(String contentId) throws Exception {
        return reviewService.getReview(contentId).getScore();
    }

    @Override
    public String getReviewReport(String s) throws QueueException, ReportException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getReviewReportStudent(String s) throws QueueException, ReportException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getReviewReportInstructor(String s) throws QueueException, ReportException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Long getReviewStatus(String contentId) throws QueueException {
        switch (reviewService.getReview(contentId).getStatus()) {
            case QUEUED:
                return STATUS_QUEUED;
            case SUBMITTED:
                return STATUS_SUBMITTED;
            case RETRIEVED:
                return STATUS_RETRIEVED;
            case FAILED:
            default:
                return STATUS_FAILED;
        }
    }

    @Override
    public Date getDateQueued(String s) throws QueueException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Date getDateSubmitted(String s) throws QueueException, SubmissionException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void processQueue() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void checkForReports() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<ContentReviewItem> getReportList(String s, String s2) throws QueueException, SubmissionException, ReportException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<ContentReviewItem> getReportList(String s) throws QueueException, SubmissionException, ReportException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<ContentReviewItem> getAllContentReviewItems(String s, String s2) throws QueueException, SubmissionException, ReportException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getServiceName() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void resetUserDetailsLockedItems(String s) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isAcceptableContent(org.sakaiproject.content.api.ContentResource contentResource) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isSiteAcceptable(org.sakaiproject.site.api.Site site) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getIconUrlforScore(Long aLong) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean allowResubmission() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void removeFromQueue(String s) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getLocalizedStatusMessage(String s, String s2) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getLocalizedStatusMessage(String s) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getReviewError(String s) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getLocalizedStatusMessage(String s, Locale locale) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Map getAssignment(String s, String s2) throws SubmissionException, TransientSubmissionException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void createAssignment(String s, String s2, Map map) throws SubmissionException, TransientSubmissionException {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
