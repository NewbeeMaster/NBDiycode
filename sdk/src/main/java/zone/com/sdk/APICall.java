package zone.com.sdk;

import zone.com.sdk.API.login.api.LoginAPI;
import zone.com.sdk.API.news.api.NewsAPI;
import zone.com.sdk.API.sites.api.SitesAPI;
import zone.com.sdk.API.token.api.TokenAPI;
import zone.com.sdk.API.topic.api.TopicAPI;

/**
 * [2017] by Zone
 */

public interface APICall extends TokenAPI,LoginAPI,TopicAPI,NewsAPI,SitesAPI {
}
