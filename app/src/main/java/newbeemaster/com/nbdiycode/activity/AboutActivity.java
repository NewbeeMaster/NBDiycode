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
 * Last modified 2017-03-25 02:42:10
 *
 * GitHub:  https://github.com/GcsSloop
 * Website: http://www.gcssloop.com
 * Weibo:   http://weibo.com/GcsSloop
 */

package newbeemaster.com.nbdiycode.activity;

import android.view.View;

import butterknife.OnClick;
import newbeemaster.com.nbdiycode.R;
import newbeemaster.com.nbdiycode.activity.common.BaseNBActivity;

public class AboutActivity extends BaseNBActivity implements View.OnClickListener {

    @OnClick({ R.id.feed_back, R.id.github, R.id.contribute })

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.feed_back:
//                IntentUtil.openUrl(this,"https://github.com/GcsSloop/diycode/issues/1");
                break;
            case R.id.github:
//                IntentUtil.openUrl(this,"https://github.com/GcsSloop");
                break;
            case R.id.contribute:
//                IntentUtil.openAlipay(this);
                break;
        }
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_about);
    }

    @Override
    public void initData() {
//        setTitle("关于");

    }

    @Override
    public void setListener() {

    }
}
