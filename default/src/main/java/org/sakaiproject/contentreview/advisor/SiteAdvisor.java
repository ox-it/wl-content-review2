package org.sakaiproject.contentreview.advisor;

/**
 * Ensures that a site can use the content review system.
 */
public interface SiteAdvisor {
    boolean isSiteSupported(String siteId);
}
