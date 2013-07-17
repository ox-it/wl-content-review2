package org.sakaiproject.contentreview.turnitin;

import com.turnitin.api.TurnitinAPI;
import com.turnitin.api.data.TiiAssignment;
import com.turnitin.api.data.TiiSubmission;
import com.turnitin.exception.TurnitinSDKException;
import org.sakaiproject.content.api.ContentHostingService;
import org.sakaiproject.content.api.ContentResource;
import org.sakaiproject.contentreview.review.ReviewSystem;
import org.sakaiproject.contentreview.turnitin.mapping.IdMapping;
import org.sakaiproject.contentreview.turnitin.mapping.IdMappingDao;
import org.sakaiproject.contentreview.turnitin.report.Report;
import org.sakaiproject.contentreview.turnitin.report.ReportDao;
import org.sakaiproject.contentreview.turnitin.sync.TurnitinSynchronisation;
import org.sakaiproject.contentreview2.Review;
import org.sakaiproject.entity.api.ResourceProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import static org.sakaiproject.contentreview.turnitin.mapping.IdMapping.Type.ASSIGNMENT;
import static org.sakaiproject.contentreview.turnitin.mapping.IdMapping.Type.USER;

public class TurnitinReviewSystem implements ReviewSystem {
    @Deprecated
    public static final String PARAM_TASK_TYPE;
    @Deprecated
    public static final String PARAM_TASK_CREATE_TYPE;
    @Deprecated
    public static final String PARAM_TASK_CREATE_ASSIGNMENT_ID;
    @Deprecated
    public static final String PARAM_TASK_CREATE_SITE_ID;
    @Deprecated
    public static final String PARAM_TII_TITLE;
    @Deprecated
    public static final String PARAM_TII_START_DATE;
    @Deprecated
    public static final String PARAM_TII_DUE_DATE;
    @Deprecated
    public static final String PARAM_TII_FEEDBACK_RELEASE_DATE;
    @Deprecated
    public static final String PARAM_TII_INSTRUCTIONS;
    @Deprecated
    public static final String PARAM_TII_AUTHOR_ORIGINALITY_ACCESS;
    @Deprecated
    public static final String PARAM_TII_SUBMITTED_DOCUMENTS_CHECK;
    @Deprecated
    public static final String PARAM_TII_INTERNET_CHECK;
    @Deprecated
    public static final String PARAM_TII_PUBLICATIONS_CHECK;
    @Deprecated
    public static final String PARAM_TII_INSTITUTION_CHECK;
    @Deprecated
    public static final String PARAM_TII_MAX_GRADE;
    @Deprecated
    public static final String PARAM_TII_LATE_SUBMISSIONS_ALLOWED;
    @Deprecated
    public static final String PARAM_TII_SUBMIT_PAPERS_TO;
    @Deprecated
    public static final String PARAM_TII_RESUBMISSION_RULE;
    @Deprecated
    public static final String PARAM_TII_BIBLIOGRAPHY_EXCLUDED;
    @Deprecated
    public static final String PARAM_TII_QUOTED_EXCLUDED;
    @Deprecated
    public static final String PARAM_TII_SMALL_MATCH_EXCLUSION_TYPE;
    @Deprecated
    public static final String PARAM_TII_SMALL_MATCH_EXCLUSION_THRESHOLD;
    @Deprecated
    public static final String PARAM_TII_ANONYMOUS_MARKING;
    @Deprecated
    public static final String PARAM_TII_ERATER;
    @Deprecated
    public static final String PARAM_TII_ERATER_SPELLING;
    @Deprecated
    public static final String PARAM_TII_ERATER_GRAMMAR;
    @Deprecated
    public static final String PARAM_TII_ERATER_USAGE;
    @Deprecated
    public static final String PARAM_TII_ERATER_MECHANICS;
    @Deprecated
    public static final String PARAM_TII_ERATER_STYLE;
    @Deprecated
    public static final String PARAM_TII_ERATER_SPELLING_DICTIONARY;
    @Deprecated
    public static final String PARAM_TII_ERATER_HANDBOOK;
    @Deprecated
    public static final String PARAM_TII_TRANSLATED_MATCHING;
    @Deprecated
    public static final String PARAM_TII_INSTRUCTOR_DEFAULTS_SAVE;
    @Deprecated
    public static final String PARAM_TII_INSTRUCTOR_DEFAULTS;

    static {
        String prefix = TurnitinReviewSystem.class.getName();
        PARAM_TASK_TYPE = prefix + ".PARAM_TASK_TYPE";
        PARAM_TASK_CREATE_TYPE = prefix + ".PARAM_TASK_CREATE_TYPE";
        PARAM_TASK_CREATE_ASSIGNMENT_ID = prefix + ".PARAM_TASK_CREATE_ASSIGNMENT_ID";
        PARAM_TASK_CREATE_SITE_ID = prefix + ".PARAM_TASK_CREATE_SITE_ID";
        PARAM_TII_TITLE = prefix + ".PARAM_TII_TITLE";
        PARAM_TII_START_DATE = prefix + ".PARAM_TII_START_DATE";
        PARAM_TII_DUE_DATE = prefix + ".PARAM_TII_DUE_DATE";
        PARAM_TII_FEEDBACK_RELEASE_DATE = prefix + ".PARAM_TII_FEEDBACK_RELEASE_DATE";
        PARAM_TII_INSTRUCTIONS = prefix + ".PARAM_TII_INSTRUCTIONS";
        PARAM_TII_AUTHOR_ORIGINALITY_ACCESS = prefix + ".PARAM_TII_AUTHOR_ORIGINALITY_ACCESS";
        PARAM_TII_SUBMITTED_DOCUMENTS_CHECK = prefix + ".PARAM_TII_SUBMITTED_DOCUMENTS_CHECK";
        PARAM_TII_INTERNET_CHECK = prefix + ".PARAM_TII_INTERNET_CHECK";
        PARAM_TII_PUBLICATIONS_CHECK = prefix + ".PARAM_TII_PUBLICATIONS_CHECK";
        PARAM_TII_INSTITUTION_CHECK = prefix + ".PARAM_TII_INSTITUTION_CHECK";
        PARAM_TII_MAX_GRADE = prefix + ".PARAM_TII_MAX_GRADE";
        PARAM_TII_LATE_SUBMISSIONS_ALLOWED = prefix + ".PARAM_TII_LATE_SUBMISSIONS_ALLOWED";
        PARAM_TII_SUBMIT_PAPERS_TO = prefix + ".PARAM_TII_SUBMIT_PAPERS_TO";
        PARAM_TII_RESUBMISSION_RULE = prefix + ".PARAM_TII_RESUBMISSION_RULE";
        PARAM_TII_BIBLIOGRAPHY_EXCLUDED = prefix + ".PARAM_TII_BIBLIOGRAPHY_EXCLUDED";
        PARAM_TII_QUOTED_EXCLUDED = prefix + ".PARAM_TII_QUOTED_EXCLUDED";
        PARAM_TII_SMALL_MATCH_EXCLUSION_TYPE = prefix + ".PARAM_TII_SMALL_MATCH_EXCLUSION_TYPE";
        PARAM_TII_SMALL_MATCH_EXCLUSION_THRESHOLD = prefix + ".PARAM_TII_SMALL_MATCH_EXCLUSION_THRESHOLD";
        PARAM_TII_ANONYMOUS_MARKING = prefix + ".PARAM_TII_ANONYMOUS_MARKING";
        PARAM_TII_ERATER = prefix + ".PARAM_TII_ERATER";
        PARAM_TII_ERATER_SPELLING = prefix + ".PARAM_TII_ERATER_SPELLING";
        PARAM_TII_ERATER_GRAMMAR = prefix + ".PARAM_TII_ERATER_GRAMMAR";
        PARAM_TII_ERATER_USAGE = prefix + ".PARAM_TII_ERATER_USAGE";
        PARAM_TII_ERATER_MECHANICS = prefix + ".PARAM_TII_ERATER_MECHANICS";
        PARAM_TII_ERATER_STYLE = prefix + ".PARAM_TII_ERATER_STYLE";
        PARAM_TII_ERATER_SPELLING_DICTIONARY = prefix + ".PARAM_TII_ERATER_SPELLING_DICTIONARY";
        PARAM_TII_ERATER_HANDBOOK = prefix + ".PARAM_TII_ERATER_HANDBOOK";
        PARAM_TII_TRANSLATED_MATCHING = prefix + ".PARAM_TII_TRANSLATED_MATCHING";
        PARAM_TII_INSTRUCTOR_DEFAULTS_SAVE = prefix + ".PARAM_TII_INSTRUCTOR_DEFAULTS_SAVE";
        PARAM_TII_INSTRUCTOR_DEFAULTS = prefix + ".PARAM_TII_INSTRUCTOR_DEFAULTS";
    }

    private static final Logger logger = LoggerFactory.getLogger(TurnitinReviewSystem.class);
    /**
     * Maximum size by default is 20MB.
     */
    private static final int MAX_FILE_SIZE = 20971520;
    private ContentHostingService contentHostingService;
    private ReportDao turnitinReportDao;
    private TurnitinAPI turnitinApi;
    private IdMappingDao idMappingDao;
    private TurnitinSynchronisation turnitinSynchronisation;

    @Override
    public void submitReview(Review review) {
        turnitinSynchronisation.synchronizeMembership(review.getSiteId(), review.getUserId());

        int tiiUserId = idMappingDao.getIdMappingBySakaiId(USER, review.getUserId()).getTurnitinId();
        int assignmentId = idMappingDao.getIdMappingBySakaiId(ASSIGNMENT, review.getAssignmentId()).getTurnitinId();

        for (Review.ReviewItem content : review.getFiles()) {
            String contentId = content.getFileId();
            Report report = new Report(review.getSubmissionId(), contentId);
            report.setDateSubmission(new Date());
            turnitinReportDao.createReport(report);
            File temporaryContentFile = null;
            try {
                ContentResource resource = contentHostingService.getResource(contentId);

                TiiSubmission tiiSubmission = new TiiSubmission();
                tiiSubmission.setAssignmentId(assignmentId);
                tiiSubmission.setSubmitterUserId(tiiUserId);
                tiiSubmission.setTitle(resource.getProperties().getProperty(ResourceProperties.PROP_DISPLAY_NAME));

                temporaryContentFile = copyContentToFile(resource);
                tiiSubmission.setSubmissionDataPath(temporaryContentFile.getPath());

                turnitinApi.createSubmission(tiiSubmission);
                review.setStatus(Review.Status.SUBMITTED);
            } catch (TurnitinSDKException e) {
                logger.error("Impossible to create a submission for '{}'", contentId, e);
                //TODO: Add details on the report submission failure:
                review.setStatus(Review.Status.FAILED);
                report.setErrorCode(e.getFaultCode());
                report.setErrorMessage(e.getMessage());
                turnitinReportDao.saveReport(report);
            } catch (Exception e) {
                logger.error("Impossible to create a submission for '{}'", contentId, e);
                //TODO: Add details on the report submission failure:
                review.setStatus(Review.Status.FAILED);
            } finally {

                if (temporaryContentFile != null && !temporaryContentFile.delete())
                    logger.warn("Couldn't remove the temporary file '{}'", temporaryContentFile.getPath());
            }
        }
    }

    private File copyContentToFile(ContentResource resource) {
        try {
            String fileName = resource.getProperties().getProperty(ResourceProperties.PROP_ORIGINAL_FILENAME);
            File temporaryFile = File.createTempFile("tii_", fileName);

            InputStream inputStream = resource.streamContent();
            ReadableByteChannel inputChannel = Channels.newChannel(inputStream);
            FileChannel outputChannel = new FileOutputStream(temporaryFile).getChannel();
            outputChannel.transferFrom(inputChannel, 0, resource.getContentLength());

            if (inputStream.read() != -1) {
                logger.error("The content '{}' hasn't been read entirely. "
                        + "ContentHostingService gave a size of {} bytes, but there are more.",
                        resource.getId(), resource.getContentLength());
            }
            return temporaryFile;
        } catch (Exception e) {
            throw new IllegalStateException("Couldn't copy the content of '" + resource.getId() + "' "
                    + "to a temporary file", e);
        }
    }

    @Override
    public void retrieveReview(Review review) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isFileSupported(String contentId) {
        try {
            ContentResource resource = contentHostingService.getResource(contentId);
            return resource.getContentLength() <= MAX_FILE_SIZE;
        } catch (Exception e) {
            logger.warn("Couldn't check if '{}' is supported.", contentId, e);
            return false;
        }
    }

    @Override
    public boolean isResubmissionSupported() {
        return true;
    }

    @Deprecated
    @Override
    public Object execute(Map<String, Object> parameters) {
        String task = (String) parameters.get(PARAM_TASK_TYPE);
        //TODO: Create getAssignment()
        if (PARAM_TASK_CREATE_TYPE.equals(task)) {
            createAssignment(parameters);
            return null;
        } else {
            throw new IllegalArgumentException("Couldn't figure out what action to do with '" + task + "'");
        }
    }

    @Deprecated
    private void createAssignment(Map<String, Object> parameters) {
        String sakaiAssignmentId = (String) parameters.get(PARAM_TASK_CREATE_ASSIGNMENT_ID);
        String sakaiSiteId = (String) parameters.get(PARAM_TASK_CREATE_SITE_ID);
        IdMapping siteId = turnitinSynchronisation.synchronizeClass(sakaiSiteId);

        TiiAssignment assignment = new TiiAssignment();
        assignment.setClassId(siteId.getTurnitinId());
        assignment.setTitle((String) parameters.get(PARAM_TII_TITLE));
        assignment.setStartDate((Calendar) parameters.get(PARAM_TII_START_DATE));
        assignment.setDueDate((Calendar) parameters.get(PARAM_TII_DUE_DATE));
        assignment.setFeedbackReleaseDate((Calendar) parameters.get(PARAM_TII_FEEDBACK_RELEASE_DATE));
        assignment.setInstructions((String) parameters.get(PARAM_TII_INSTRUCTIONS));
        assignment.setAuthorOriginalityAccess((Boolean) parameters.get(PARAM_TII_AUTHOR_ORIGINALITY_ACCESS));
        assignment.setSubmittedDocumentsCheck((Boolean) parameters.get(PARAM_TII_SUBMITTED_DOCUMENTS_CHECK));
        assignment.setInternetCheck((Boolean) parameters.get(PARAM_TII_INTERNET_CHECK));
        assignment.setPublicationsCheck((Boolean) parameters.get(PARAM_TII_PUBLICATIONS_CHECK));
        assignment.setInstitutionCheck((Boolean) parameters.get(PARAM_TII_INSTITUTION_CHECK));
        assignment.setMaxGrade((Integer) parameters.get(PARAM_TII_MAX_GRADE));
        assignment.setLateSubmissionsAllowed((Boolean) parameters.get(PARAM_TII_LATE_SUBMISSIONS_ALLOWED));
        assignment.setSubmitPapersTo((Integer) parameters.get(PARAM_TII_SUBMIT_PAPERS_TO));
        assignment.setResubmissionRule((Integer) parameters.get(PARAM_TII_RESUBMISSION_RULE));
        assignment.setBibliographyExcluded((Boolean) parameters.get(PARAM_TII_BIBLIOGRAPHY_EXCLUDED));
        assignment.setQuotedExcluded((Boolean) parameters.get(PARAM_TII_QUOTED_EXCLUDED));
        assignment.setSmallMatchExclusionType((Integer) parameters.get(PARAM_TII_SMALL_MATCH_EXCLUSION_TYPE));
        assignment.setSmallMatchExclusionThreshold((Integer) parameters.get(PARAM_TII_SMALL_MATCH_EXCLUSION_THRESHOLD));
        assignment.setAnonymousMarking((Boolean) parameters.get(PARAM_TII_ANONYMOUS_MARKING));
        assignment.setErater((Boolean) parameters.get(PARAM_TII_ERATER));
        assignment.setEraterSpelling((Boolean) parameters.get(PARAM_TII_ERATER_SPELLING));
        assignment.setEraterGrammar((Boolean) parameters.get(PARAM_TII_ERATER_GRAMMAR));
        assignment.setEraterUsage((Boolean) parameters.get(PARAM_TII_ERATER_USAGE));
        assignment.setEraterMechanics((Boolean) parameters.get(PARAM_TII_ERATER_MECHANICS));
        assignment.setEraterStyle((Boolean) parameters.get(PARAM_TII_ERATER_STYLE));
        assignment.setEraterSpellingDictionary((String) parameters.get(PARAM_TII_ERATER_SPELLING_DICTIONARY));
        assignment.setEraterHandbook((Integer) parameters.get(PARAM_TII_ERATER_HANDBOOK));
        assignment.setTranslatedMatching((Boolean) parameters.get(PARAM_TII_TRANSLATED_MATCHING));
        assignment.setInstructorDefaultsSave((Integer) parameters.get(PARAM_TII_INSTRUCTOR_DEFAULTS_SAVE));
        assignment.setInstructorDefaults((Integer) parameters.get(PARAM_TII_INSTRUCTOR_DEFAULTS));

        int tiiAssignmentId = turnitinApi.createAssignment(assignment).getTiiAssignment().getAssignmentId();
        idMappingDao.createMappingId(new IdMapping(ASSIGNMENT, sakaiAssignmentId, tiiAssignmentId));
    }

    public void setContentHostingService(ContentHostingService contentHostingService) {
        this.contentHostingService = contentHostingService;
    }

    public void setTurnitinApi(TurnitinAPI turnitinApi) {
        this.turnitinApi = turnitinApi;
    }
}
