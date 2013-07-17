package org.sakaiproject.contentreview.turnitin.report;

import java.util.Date;

public class Report {
    private final String reviewId;
    private final String documentId;
    private int submissionId;
    private String errorCode;
    private boolean feedback;
    private boolean voiceFeedback;
    private Date dateSubmission;
    private String errorMessage;
    private String instructorReport;
    private String studentReport;

    public Report(String reviewId, String documentId) {
        this.reviewId = reviewId;
        this.documentId = documentId;
    }

    public String getReviewId() {
        return reviewId;
    }

    public String getDocumentId() {
        return documentId;
    }

    public int getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(int submissionId) {
        this.submissionId = submissionId;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public boolean isFeedback() {
        return feedback;
    }

    public void setFeedback(boolean feedback) {
        this.feedback = feedback;
    }

    public boolean isVoiceFeedback() {
        return voiceFeedback;
    }

    public void setVoiceFeedback(boolean voiceFeedback) {
        this.voiceFeedback = voiceFeedback;
    }

    public Date getDateSubmission() {
        return dateSubmission;
    }

    public void setDateSubmission(Date dateSubmission) {
        this.dateSubmission = dateSubmission;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getInstructorReport() {
        return instructorReport;
    }

    public void setInstructorReport(String instructorReport) {
        this.instructorReport = instructorReport;
    }

    public String getStudentReport() {
        return studentReport;
    }

    public void setStudentReport(String studentReport) {
        this.studentReport = studentReport;
    }
}
