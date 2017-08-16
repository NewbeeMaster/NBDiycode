package newbeemaster.com.nbdiycode.animate.plugins;

import com.nineoldandroids.animation.AnimatorSet;

import zone.com.zanimate.object.ObjectAnimatorHelper;

/**
 * Created by sxl on 2016/6/27.
 */
public class Begin_Iv_Animator implements ObjectAnimatorHelper.BaseViewAnimator {
    @Override
    public void prepare(AnimatorSet mAnimatorSet) {
        mAnimatorSet.playTogether(
                ObjectAnimatorHelper.ofFloat("scaleX", 1.5F, 1),
                ObjectAnimatorHelper.ofFloat("scaleY", 1.5F, 1)
        );
        mAnimatorSet.setDuration(1900);
    }
}
