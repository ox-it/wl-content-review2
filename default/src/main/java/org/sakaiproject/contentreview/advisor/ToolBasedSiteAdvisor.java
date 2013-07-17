package org.sakaiproject.contentreview.advisor;

import org.sakaiproject.exception.IdUnusedException;
import org.sakaiproject.site.api.Site;
import org.sakaiproject.site.api.SiteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Site advisor that ensure that one of the tools requiring content review is present.
 */
public class ToolBasedSiteAdvisor implements SiteAdvisor {
    private static final Logger logger = LoggerFactory.getLogger(ToolBasedSiteAdvisor.class);
    private final Collection<String> toolIds = new LinkedList<String>();
    private SiteService siteService;

    public void init() {
        //TODO: Load additional tools from sakai.properties
    }

    @Override
    public boolean isSiteSupported(String siteId) {
        try {
            Site site = siteService.getSite(siteId);
            for (String toolId : toolIds)
                if (site.getToolForCommonId(toolId) != null)
                    return true;
        } catch (IdUnusedException e) {
            logger.warn("Couldn't check if the site '{}' is supported.", siteId, e);
        }
        return false;
    }

    public void registerTool(String toolId) {
        this.toolIds.add(toolId);
    }

    public void setSiteService(SiteService siteService) {
        this.siteService = siteService;
    }
}
