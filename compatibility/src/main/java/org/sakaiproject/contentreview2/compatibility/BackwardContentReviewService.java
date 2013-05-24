package org.sakaiproject.contentreview2.compatibility;

import org.sakaiproject.contentreview.exception.QueueException;
import org.sakaiproject.contentreview.exception.ReportException;
import org.sakaiproject.contentreview.exception.SubmissionException;
import org.sakaiproject.contentreview.exception.TransientSubmissionException;
import org.sakaiproject.contentreview.model.ContentReviewItem;
import org.sakaiproject.contentreview.service.ContentReviewService;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Implementation of the previous ContentReview API which delegates calls to the new API.
 */
public class BackwardContentReviewService implements ContentReviewService {

    @Override
    public void queueContent(String userId, String siteId, String taskId, String contentId) throws QueueException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getReviewScore(String s) throws QueueException, ReportException, Exception {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
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
    public Long getReviewStatus(String s) throws QueueException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
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
