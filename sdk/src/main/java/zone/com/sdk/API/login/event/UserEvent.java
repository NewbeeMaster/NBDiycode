package zone.com.sdk.API.login.event;

import zone.com.sdk.API.login.bean.UserDetail;

/**
 * [2017] by Zone
 */

public class UserEvent {
    public UserDetail userDetail;
    public UserEvent(UserDetail userDetail) {
        this.userDetail=userDetail;
    }
}
