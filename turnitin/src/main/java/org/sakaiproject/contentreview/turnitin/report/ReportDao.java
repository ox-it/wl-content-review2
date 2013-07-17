package org.sakaiproject.contentreview.turnitin.report;

public interface ReportDao {
    void createReport(Report report);
    void saveReport(Report report);
    Report getReportForReview(String reviewId);
}
