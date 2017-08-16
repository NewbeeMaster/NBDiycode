package newbeemaster.com.nbdiycode.fragment;

import android.app.Application;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zone.zbanner.ViewPagerCircle;
import com.zone.zbanner.indicator.IndicatorView;
import com.zone.zbanner.indicator.type.CircleIndicator;
import com.zone.zbanner.indicator.type.abstarct.ShapeIndicator;
import com.zone.zbanner.simpleadapter.PagerAdapterCircle_Image;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import newbeemaster.com.nbdiycode.R;
import newbeemaster.com.nbdiycode.activity.MainActivity;

/**
 * [2017] by Zone
 */

public class GuideFragment extends Fragment {

    View rootView;

    @Bind(R.id.pager)
    ViewPagerCircle pager;
    @Bind(R.id.indicatorView)
    IndicatorView indicatorView;
    @Bind(R.id.ivLaunch)
    ImageView ivLaunch;

    final List<Integer> resourceList = new ArrayList<Integer>();
    private PagerAdapterCircle_Image mviewPager;
    private ShapeIndicator circleIndicator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_guide, null);
        this.rootView = view;
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        resourceList.add(R.drawable.lanuch_01);
        resourceList.add(R.drawable.lanuch_02);
        resourceList.add(R.drawable.lanuch_03);

        ivLaunch.setOnClickListener(a ->{
            startActivity(new Intent(view.getContext(), MainActivity.class));
//            finish
        });

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

    @Override
    public void onDestroy() {
        ButterKnife.bind(rootView);
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
