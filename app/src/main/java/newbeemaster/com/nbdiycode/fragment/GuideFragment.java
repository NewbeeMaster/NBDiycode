package newbeemaster.com.nbdiycode.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.trello.rxlifecycle2.components.support.RxFragment;
import com.zone.zbanner.ViewPagerCircle;
import com.zone.zbanner.indicator.IndicatorView;
import com.zone.zbanner.indicator.type.CircleIndicator;
import com.zone.zbanner.indicator.type.abstarct.ShapeIndicator;
import com.zone.zbanner.simpleadapter.PagerAdapterCircle_Image;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import newbeemaster.com.nbdiycode.R;
import newbeemaster.com.nbdiycode.event.GuideFinishEvent;

/**
 * [2017] by Zone
 */

public class GuideFragment extends RxFragment {

    @BindView(R.id.pager)
    ViewPagerCircle pager;
    @BindView(R.id.indicatorView)
    IndicatorView indicatorView;
    @BindView(R.id.ivLaunch)
    ImageView ivLaunch;

    final List<Integer> resourceList = new ArrayList<Integer>();
    private PagerAdapterCircle_Image mviewPager;
    private ShapeIndicator circleIndicator;
    private Unbinder binder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_guide, null);
        binder=ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        resourceList.add(R.drawable.lanuch_01);
        resourceList.add(R.drawable.lanuch_02);
        resourceList.add(R.drawable.lanuch_03);

        mviewPager = new PagerAdapterCircle_Image(view.getContext(), resourceList, false) {
            @Override
            public void setImage(ImageView iv, final int position) {
                Glide.with(GuideFragment.this)
                        .load(resourceList.get(position))
                        .centerCrop()
                        .dontAnimate()
                        .into(iv);

            }
        };

        ivLanuch();

        pager.setAdapter(mviewPager);
        pager.closeTimeCircle();
        pager.setPageTransformer(true, null);
        indicatorView.setViewPager(pager);

        circleIndicator = new CircleIndicator(20)
                .setShapeEntity(new ShapeIndicator
                                .ShapeEntity()
                                .setStrokeWidthHalf(5)
                                .setStrokeColor(Color.WHITE)
                                .setHaveFillColor(false),
                        new ShapeIndicator.ShapeEntity()
                                .setStrokeWidthHalf(5)
                                .setFillColor(Color.RED)
                                .setHaveStrokeColor(false));
        indicatorView.setIndicator(circleIndicator);

    }

    private void ivLanuch() {
        ivLaunch.setVisibility(View.GONE);
        ivLaunch.setOnClickListener(a -> EventBus.getDefault().post(new GuideFinishEvent()));
        pager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                if (position == 2)
                    ivLaunch.setVisibility(View.VISIBLE);
                else
                    ivLaunch.setVisibility(View.GONE);
            }

        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binder.unbind();
    }
}
