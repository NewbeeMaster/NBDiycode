package newbeemaster.com.nbdiycode.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.rd.PageIndicatorView;
import com.zone.lib.utils.activity_fragment_ui.ToastUtils;
import com.zone.zbanner.PagerAdapterCycle;
import com.zone.zbanner.ViewPagerCircle;
import com.zone.zbanner.indicator.IndicatorView;
import com.zone.zbanner.indicator.type.CircleIndicator;
import com.zone.zbanner.indicator.type.abstarct.ShapeIndicator;
import com.zone.zbanner.simpleadapter.PagerAdapterCircle_Image;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import newbeemaster.com.nbdiycode.R;
import newbeemaster.com.nbdiycode.activity.common.BaseNBActivity;
import newbeemaster.com.nbdiycode.fragment.GuideFragment;

/**
 * [2017] by Zone
 */

public class ImageActivity extends BaseNBActivity {

    public static final String ALL_IMAGE_URLS = "all_images";
    public static final String CURRENT_IMAGE_URL = "current_image";

    protected List<String> images = new ArrayList<>();     // 所有图片
    protected String current_image_url = null;                  // 当前图片
    protected int current_image_position = 0;                  // 当前图片位置

    private PagerAdapterCycle mviewPager;
    private ShapeIndicator circleIndicator;
    @BindView(R.id.pager)
    ViewPagerCircle pager;
    @BindView(R.id.indicatorView)
    IndicatorView indicatorView;

    @Override
    public void setContentView() {
        setContentView(R.layout.a_image);

    }

    @Override
    public void initData() {
        setTitle("查看图片");

        // 初始化图片 url 和 图片集合，保证两个数据都存在
        Intent intent = getIntent();

        // 没有传递当前图片，设置模式为错误
        String imageUrl = intent.getStringExtra(CURRENT_IMAGE_URL);
        if (imageUrl == null || imageUrl.isEmpty()) {
            ToastUtils.showShort(this, "没有传递图片链接");
            return;
        }

        ArrayList<String> temp = intent.getStringArrayListExtra(ALL_IMAGE_URLS);
        if (temp == null || temp.size() <= 0) {
            // 记录当前图片，计算位置
            images.clear();
            images.add(imageUrl);
        } else if (temp.size() > 0) {
            // 如果图片集合大于0
            images = new ArrayList<>(temp);
        }

        if (!images.contains(imageUrl)) {
            images.add(imageUrl);
        }

        current_image_url = imageUrl;
        current_image_position = images.indexOf(current_image_url);


        mviewPager = new PagerAdapterCycle(this, images, false) {
            @Override
            public View getView(Context context, int position) {
                PhotoView photoView = (PhotoView) getLayoutInflater().inflate(R.layout.item_image, pager, false);
                photoView.enable();
                String url = images.get(position);
                Glide.with(ImageActivity.this).load(images.get(position)).into(photoView);
                return photoView;
            }
        };


        pager.setAdapter(mviewPager,current_image_position);
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
        indicatorView.setSnap(true);
    }


}
