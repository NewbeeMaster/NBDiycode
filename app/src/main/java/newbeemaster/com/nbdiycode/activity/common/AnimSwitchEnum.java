package newbeemaster.com.nbdiycode.activity.common;

import android.app.Activity;

import newbeemaster.com.nbdiycode.R;

public enum AnimSwitchEnum {

    None(0), Fade(1),
    //常用 RightToLift
    RightToLift(2), LiftToRight(3);

    int value = 0;

    AnimSwitchEnum(int value) {
        this.value = value;
    }

    public void overridePendingTransition(Activity activity) {
        switch (value) {
            case 0:
                break;
            case 1:
                activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case 2:
                activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case 3:
                activity.overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                break;
        }

    }
}