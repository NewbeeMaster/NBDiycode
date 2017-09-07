package newbeemaster.com.nbdiycode.activity;

import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zone.lib.utils.data.file2io2data.SharedUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import newbeemaster.com.nbdiycode.R;
import newbeemaster.com.nbdiycode.activity.common.BaseNBActivity;
import newbeemaster.com.nbdiycode.constant.SPConstant;
import zone.com.sdk.API.login.event.UserEvent;
import newbeemaster.com.nbdiycode.fragment.NewListFragment;
import newbeemaster.com.nbdiycode.fragment.SiteListFragment;
import newbeemaster.com.nbdiycode.fragment.TopicListFragment;
import zone.com.sdk.API.login.bean.UserDetail;
import zone.com.sdk.Diycode;

public class MainActivity extends BaseNBActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private int mCurrentPosition = 0;

    private TopicListFragment mFragment1;
    private NewListFragment mFragment2;
    private SiteListFragment mFragment3;

    // view
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawer;
    @Bind(R.id.nav_view)
    NavigationView navigationView;
    @Bind(R.id.view_pager)
    ViewPager mViewPager;
    @Bind(R.id.tab_layout)
    TabLayout mTabLayout;
    private TextView username, tagline;
    private ImageView avatar;


    @Override
    public void setContentView() {
        setContentView(R.layout.a_main);
        registerEventBus();
    }

    @Override
    public void findIDs() {
        super.findIDs();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        avatar = (ImageView) headerView.findViewById(R.id.nav_header_image);
        username = (TextView) headerView.findViewById(R.id.nav_header_name);
        tagline = (TextView) headerView.findViewById(R.id.nav_header_tagline);
    }

    @Override
    public void initData() {
        initMenu();
        loadMenuData(new UserEvent(Diycode.getInstance()
                .isLogin() ? SharedUtils.get(SPConstant.USER_DETAIL, UserDetail.class) : null));
        initViewPager();
    }

    @Override
    public void setListener() {

    }

    //--- viewpager adapter ------------------------------------------------------------------------

    private void initViewPager() {
        mViewPager.setOffscreenPageLimit(3); // 防止滑动到第三个页面时，第一个页面被销毁

        mFragment1 = new TopicListFragment();
        mFragment2 = new NewListFragment();
        mFragment3 = new SiteListFragment();

        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            String[] types = {"Topics", "News", "Sites", "Test"};

            @Override
            public Fragment getItem(int position) {
                if (position == 0)
                    return mFragment1;
                if (position == 1)
                    return mFragment2;
                if (position == 2)
                    return mFragment3;
                return mFragment1;
//                return TextFragment.newInstance(types[position]);
            }

            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return types[position];
            }
        });


        mCurrentPosition = 0;
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mCurrentPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        mViewPager.setCurrentItem(mCurrentPosition);
        mTabLayout.setupWithViewPager(mViewPager);
    }


    //--- menu -------------------------------------------------------------------------------------

    // 初始化菜单(包括侧边栏菜单和顶部菜单选项)
    private void initMenu() {
        toolbar.setLogo(R.mipmap.logo_actionbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    // 加载侧边栏菜单数据(与用户相关的)
    private void loadMenuData(UserEvent mUserEvent) {
        if (mUserEvent != null && mUserEvent.userDetail != null) {
            username.setText(mUserEvent.userDetail.getName());
            tagline.setText(mUserEvent.userDetail.getTagline());
            Glide.with(this).load(mUserEvent.userDetail.getAvatar_url()).into(avatar);
        } else {
            username.setText("(未登录)");
            tagline.setText("点击头像登录");
            avatar.setImageResource(R.mipmap.ic_launcher);
            avatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openActivity(LoginActvity.class);
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            openActivity(SettingActivity.class);
            return true;
        } else if (id == R.id.action_notification) {
            if (!isLogin())
                openActivity(NotificationActivity.class);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_post) {
            if (!isLogin())
                openActivity(MyTopicActivity.class, "type", MyTopicActivity.InfoType.MY_TOPIC);
        } else if (id == R.id.nav_collect) {
            if (!isLogin())
                openActivity(MyTopicActivity.class, "type", MyTopicActivity.InfoType.MY_COLLECT);
        } else if (id == R.id.nav_about) {
            openActivity(AboutActivity.class);
        } else if (id == R.id.nav_setting) {
            openActivity(SettingActivity.class);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private boolean isLogin() {
        if (!Diycode.getInstance().isLogin()) {
            openActivity(LoginActvity.class);
            return true;
        }
        return false;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public synchronized void onLoginEvent(UserEvent event) {
        loadMenuData(event);
    }


}
