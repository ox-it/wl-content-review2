package org.sakaiproject.contentreview.turnitin.sync;

import com.turnitin.api.TurnitinAPI;
import com.turnitin.api.data.TiiClass;
import com.turnitin.api.data.TiiMembership;
import com.turnitin.api.data.TiiUser;
import com.turnitin.api.response.APIResponse;
import org.sakaiproject.authz.api.SecurityService;
import org.sakaiproject.contentreview.dao.ReviewDao;
import org.sakaiproject.contentreview.turnitin.mapping.IdMapping;
import org.sakaiproject.contentreview.turnitin.mapping.IdMappingDao;
import org.sakaiproject.contentreview.turnitin.report.Report;
import org.sakaiproject.contentreview.turnitin.report.ReportDao;
import org.sakaiproject.exception.IdUnusedException;
import org.sakaiproject.site.api.Site;
import org.sakaiproject.site.api.SiteService;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserDirectoryService;
import org.sakaiproject.user.api.UserNotDefinedException;

import static org.sakaiproject.contentreview.turnitin.mapping.IdMapping.Type;

public class TurnitinSynchronisation {
    private static final String INSTRUCTOR_PERMISSION = "section.role.instructor";
    private static final String LEARNER_PERMISSION = "section.role.learner";
    private TurnitinAPI turnitinApi;
    private IdMappingDao idMappingDao;
    private ReportDao reportDao;
    private SiteService siteService;
    private UserDirectoryService userDirectoryService;
    private SecurityService securityService;
    private ReviewDao reviewDao;

    public void synchronizeSite(String sakaiSiteId) {
        try {
            IdMapping classId = synchronizeClass(sakaiSiteId);
            Site site = siteService.getSite(classId.getSakaiId());

            for (String sakaiUserId : site.getUsers()) {
                IdMapping userId = synchronizeUser(sakaiUserId);
                synchronizeMembership(classId, userId);
            }
        } catch (IdUnusedException e) {
            throw new IllegalStateException("Couldn't synchronise site '" + sakaiSiteId + "'", e);
        }
    }

    private void synchronizeMembership(IdMapping classId, IdMapping userId) {
        TiiMembership tiiMembership = new TiiMembership();
        tiiMembership.setClassId(classId.getTurnitinId());
        tiiMembership.setUserId(userId.getTurnitinId());

        String siteReference = siteService.siteReference(classId.getSakaiId());
        //TODO: Add additional custom checks on users.
        if (securityService.unlock(userId.getSakaiId(), INSTRUCTOR_PERMISSION, siteReference)) {
            turnitinApi.createInstructorMembership(tiiMembership);
        } else if (securityService.unlock(userId.getSakaiId(), LEARNER_PERMISSION, siteReference)) {
            turnitinApi.createStudentMembership(tiiMembership);
        }
    }

    public IdMapping synchronizeClass(String sakaiSiteId) {
        try {
            IdMapping classId = idMappingDao.getIdMappingBySakaiId(Type.CLASS, sakaiSiteId);

            if (classId == null) {
                Site site = siteService.getSite(sakaiSiteId);
                TiiClass tiiClass = new TiiClass();
                tiiClass.setTitle(site.getTitle());

                APIResponse apiResponse = turnitinApi.createClass(tiiClass);
                classId = new IdMapping(Type.CLASS, sakaiSiteId, apiResponse.getTiiClass().getClassId());
                idMappingDao.createMappingId(classId);
            }

            return classId;
        } catch (IdUnusedException e) {
            throw new IllegalStateException("Couldn't create site '" + sakaiSiteId + "'", e);
        }
    }

    public IdMapping synchronizeUser(String sakaiUserId) {
        try {
            IdMapping userId = idMappingDao.getIdMappingBySakaiId(Type.USER, sakaiUserId);

            User user = userDirectoryService.getUser(sakaiUserId);
            TiiUser tiiUser = new TiiUser();
            tiiUser.setFirstName(user.getFirstName());
            tiiUser.setLastName(user.getLastName());

            if (userId == null) {
                tiiUser.setEmail(user.getEmail());

                APIResponse apiResponse = turnitinApi.createStudentUser(tiiUser);
                userId = new IdMapping(Type.USER, sakaiUserId, apiResponse.getTiiUser().getUserId());
                idMappingDao.createMappingId(userId);
            } else {
                tiiUser.setUserId(userId.getTurnitinId());

                turnitinApi.updateUser(tiiUser);
            }

            return userId;
        } catch (UserNotDefinedException e) {
            throw new IllegalStateException("Couldn't create user '" + sakaiUserId + "'", e);
        }
    }

    public void synchronizeMembership(String siteId, String userId) {
        synchronizeMembership(synchronizeClass(siteId), synchronizeUser(userId));
    }

    public Report synchronizeReport(String sakaiReviewId) {
        Report report = reportDao.getReportForReview(sakaiReviewId);

        if (report == null) {
            report = new Report();
            report.setDocumentId(sa);
            //Submit review
        } else {
            //update report
        }

        return report;

        try {
            IdMapping userId = idMappingDao.getIdMappingBySakaiId(Type.USER, sakaiUserId);

            User user = userDirectoryService.getUser(sakaiUserId);
            TiiUser tiiUser = new TiiUser();
            tiiUser.setFirstName(user.getFirstName());
            tiiUser.setLastName(user.getLastName());

            if (userId == null) {
                tiiUser.setEmail(user.getEmail());

                APIResponse apiResponse = turnitinApi.createStudentUser(tiiUser);
                userId = new IdMapping(Type.USER, sakaiUserId, apiResponse.getTiiUser().getUserId());
                idMappingDao.createMappingId(userId);
            } else {
                tiiUser.setUserId(userId.getTurnitinId());

                turnitinApi.updateUser(tiiUser);
            }

            return userId;
        } catch (UserNotDefinedException e) {
            throw new IllegalStateException("Couldn't create user '" + sakaiUserId + "'", e);
        }
    }

    public void setTurnitinApi(TurnitinAPI turnitinApi) {
        this.turnitinApi = turnitinApi;
    }
}
