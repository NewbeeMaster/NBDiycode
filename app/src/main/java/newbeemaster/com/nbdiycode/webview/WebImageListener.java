/*
 * Copyright 2017 GcsSloop
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Last modified 2017-03-18 02:02:46
 *
 * GitHub:  https://github.com/GcsSloop
 * Website: http://www.gcssloop.com
 * Weibo:   http://weibo.com/GcsSloop
 */

package newbeemaster.com.nbdiycode.webview;

import android.content.Context;
import android.content.Intent;
import android.webkit.JavascriptInterface;


import java.util.ArrayList;

import newbeemaster.com.nbdiycode.activity.ImageActivity;
import newbeemaster.com.nbdiycode.util.UrlUtil;

public class WebImageListener {
    private Context mContext;
    private Class<? extends ImageActivity> mImageActivity;
    private ArrayList<String> mImages = new ArrayList<>();

    public WebImageListener(Context context, Class<? extends ImageActivity> imageActivity) {
        mContext = context;
        mImageActivity = imageActivity;
    }

    public ArrayList<String> getImages() {
        return mImages;
    }

    /**
     * 收集图片(当发现图片时会调用该方法)
     *
     * @param url 图片链接
     */
    @JavascriptInterface
    public void collectImage(final String url) {
//        Logger.e("collect:" + url);
        if (UrlUtil.isGifSuffix(url)){
            return;
        }
        if (!mImages.contains(url))
            mImages.add(url);
    }

    /**
     * 图片被点击(图片被点击时调用该方法)
     *
     * @param url 图片链接
     */
    @JavascriptInterface
    public void onImageClicked(String url) {
//        Logger.e("clicked:" + url);
        if (mImageActivity != null) {
            Intent intent = new Intent(mContext, mImageActivity);
            intent.putExtra(ImageActivity.CURRENT_IMAGE_URL, url);
            if (!UrlUtil.isGifSuffix(url)){
                intent.putExtra(ImageActivity.ALL_IMAGE_URLS, mImages);
            }
            mContext.startActivity(intent);
        }
    }

}
