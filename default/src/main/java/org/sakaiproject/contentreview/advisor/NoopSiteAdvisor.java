package org.sakaiproject.contentreview.advisor;

/**
 * Simple SiteAdvisor accepting every site.
 */
public class NoopSiteAdvisor implements SiteAdvisor {
    @Override
    public boolean isSiteSupported(String siteId) {
        return true;
    }
}
