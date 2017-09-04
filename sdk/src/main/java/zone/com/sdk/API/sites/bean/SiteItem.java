package zone.com.sdk.API.sites.bean;

/**
 * [2017] by Zone
 */

public class SiteItem {
    private String name;
    private String url;
    private String avatar_url;
    private boolean isFullspan;

    public SiteItem(Sites.Site site) {
        this.name = site.getName();
        this.url = site.getUrl();
        this.avatar_url = site.getAvatar_url();
    }

    public SiteItem(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public boolean isFullspan() {
        return isFullspan;
    }

    public SiteItem setFullspan(boolean fullspan) {
        isFullspan = fullspan;
        return this;
    }
}
