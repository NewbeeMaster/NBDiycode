package newbeemaster.com.nbdiycode.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nineoldandroids.animation.Animator;
import com.trello.rxlifecycle2.components.support.RxFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import newbeemaster.com.nbdiycode.R;
import newbeemaster.com.nbdiycode.animate.plugins.Begin_Iv_Animator;
import newbeemaster.com.nbdiycode.event.GuideFinishEvent;
import newbeemaster.com.nbdiycode.utils.RxComposes;
import zone.com.zanimate.object.ObjectAnimatorHelper;
import zone.com.zrefreshlayout.utils.SimpleAnimatorListener;

/**
 * [2017] by Zone
 */

public class BeginFragment extends RxFragment {

    @Bind(R.id.iv)
    ImageView iv;
    @Bind(R.id.loadingPass)
    TextView loadingPass;
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.frag_begin, null);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Observable.interval(0,1000, TimeUnit.MILLISECONDS)
                .take(3)
                .compose(bindToLifecycle())
                .compose(RxComposes.applyObservableAsync())
                .subscribe(aLong -> {
                    loadingPass.setText("跳过 " + (3 - aLong) + "s");
                });
        ObjectAnimatorHelper.playPreset(Begin_Iv_Animator.class)
                .setTarget(iv)
                .addListener(new SimpleAnimatorListener() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        jump();
                    }
                })
                .start();

        loadingPass.setOnClickListener(v -> jump());

    }

    private void jump() {
        EventBus.getDefault().post(new GuideFinishEvent());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
