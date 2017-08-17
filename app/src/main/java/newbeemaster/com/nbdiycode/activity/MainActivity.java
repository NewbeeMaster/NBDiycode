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
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.OnClick;
import newbeemaster.com.nbdiycode.R;
import newbeemaster.com.nbdiycode.activity.common.BaseNBActivity;
import newbeemaster.com.nbdiycode.fragment.GuideFragment;
import newbeemaster.com.nbdiycode.util.Config;
import newbeemaster.com.nbdiycode.util.DataCache;

public class MainActivity extends BaseNBActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private DataCache mCache;
    private Config mConfig;
    private int mCurrentPosition = 0;
//    private TopicListFragment mFragment1;
//    private NewsListFragment mFragment2;
//    private SitesListFragment mFragment3;
    private GuideFragment mFragment1;
    private GuideFragment mFragment2;
    private GuideFragment mFragment3;

    private boolean isToolbarFirstClick = true;


    // view
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.drawer_layout) DrawerLayout drawer;
    @Bind(R.id.nav_view) NavigationView navigationView;
    @Bind(R.id.view_pager) ViewPager mViewPager;
    @Bind(R.id.tab_layout) TabLayout mTabLayout;


    // 事件
    @OnClick({ R.id.fab, R.id.toolbar})



    @Override
    public void setContentView() {
        setContentView(R.layout.a_main);
    }

    @Override
    public void initData() {
        //        EventBus.getDefault().register(this);
        mCache = new DataCache(this);
        mConfig = Config.getSingleInstance();
        initMenu();
        initViewPager();

    }

    @Override
    public void setListener() {

    }


    //--- viewpager adapter ------------------------------------------------------------------------

    private void initViewPager() {
        mViewPager.setOffscreenPageLimit(3); // 防止滑动到第三个页面时，第一个页面被销毁

        mFragment1 = new GuideFragment();
        mFragment2 = new GuideFragment();
        mFragment3 = new GuideFragment();
//        mFragment1 = TopicListFragment.newInstance();
//        mFragment2 = NewsListFragment.newInstance();
//        mFragment3 = SitesListFragment.newInstance();

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

        mCurrentPosition = mConfig.getMainViewPagerPosition();
        mViewPager.setCurrentItem(mCurrentPosition);

        mTabLayout.setupWithViewPager(mViewPager);
    }

    // 快速返回顶部
    private void quickToTop() {
//        switch (mCurrentPosition) {
//            case 0:
//                mFragment1.quickToTop();
//                break;
//            case 1:
//                mFragment2.quickToTop();
//                break;
//            case 2:
//                mFragment3.quickToTop();
//                break;
//        }
    }

//    // 如果收到此状态说明用户已经登录成功了
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onLogin(GetMeEvent event) {
//        if (event.isOk()) {
//            UserDetail me = event.getBean();
//            mCache.saveMe(me);
//            loadMenuData(); // 加载菜单数据
//        }
//    }
//
//    // 如果收到此状态说明用户登出了
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onLogout(LogoutEvent event) {
//        loadMenuData(); // 加载菜单数据
//    }

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
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // 双击 666
        final GestureDetector detector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
//                quickToTop();   // 快速返回头部
                return super.onDoubleTap(e);
            }
        });

        toolbar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                detector.onTouchEvent(event);
                return false;
            }
        });

        loadMenuData();
    }

    // 加载侧边栏菜单数据(与用户相关的)
    private void loadMenuData() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        ImageView avatar = (ImageView) headerView.findViewById(R.id.nav_header_image);
        TextView username = (TextView) headerView.findViewById(R.id.nav_header_name);
        TextView tagline = (TextView) headerView.findViewById(R.id.nav_header_tagline);

//        if (mDiycode.isLogin()) {
//            UserDetail me = mCache.getMe();
//            if (me == null) {
//                Logger.e("获取自己缓存失效");
//                mDiycode.getMe();   // 重新加载
//                return;
//            }
//
//            username.setText(me.getLogin());
//            tagline.setText(me.getTagline());
//            Glide.with(this).load(me.getAvatar_url()).into(avatar);
//            avatar.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    UserDetail me = mCache.getMe();
//                    if (me == null) {
//                        try {
//                            me = mDiycode.getMeNow();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    if (me != null) {
//                        User user = new User();
//                        user.setId(me.getId());
//                        user.setName(me.getName());
//                        user.setLogin(me.getLogin());
//                        user.setAvatar_url(me.getAvatar_url());
//                        UserActivity.newInstance(newbeemaster.com.nbdiycode.activity.MainActivity.this, user);
//                    }
//                }
//            });
//        } else {
            mCache.removeMe();
            username.setText("(未登录)");
            tagline.setText("点击头像登录");
            avatar.setImageResource(R.mipmap.ic_launcher);
            avatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    openActivity(LoginActivity.class);
                }
            });
//        }
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
//            openActivity(SettingActivity.class);
            return true;
        } else if (id == R.id.action_notification) {
//            if (!mDiycode.isLogin()) {
//                openActivity(LoginActivity.class);
//            } else {
//                openActivity(NotificationActivity.class);
//            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

//        if (id == R.id.nav_post) {
//            if (!mDiycode.isLogin()) {
//                openActivity(LoginActivity.class);
//                return true;
//            }
//            MyTopicActivity.newInstance(this, MyTopicActivity.InfoType.MY_TOPIC);
//        } else if (id == R.id.nav_collect) {
//            if (!mDiycode.isLogin()) {
//                openActivity(LoginActivity.class);
//                return true;
//            }
//            MyTopicActivity.newInstance(this, MyTopicActivity.InfoType.MY_COLLECT);
//        } else if (id == R.id.nav_about) {
//            openActivity(AboutActivity.class);
//        } else if (id == R.id.nav_setting) {
//            openActivity(SettingActivity.class);
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        mConfig.saveMainViewPagerPosition(mCurrentPosition);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar:
                if (isToolbarFirstClick) {
//                    toastShort("双击标题栏快速返回顶部");
                    isToolbarFirstClick = false;
                }
                break;
            case R.id.fab:
                quickToTop();
                break;
        }
    }


}
