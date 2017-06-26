package com.demo.android.newlife.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.demo.android.newlife.R;
import com.demo.android.newlife.ui.base.BaseActivity;
import com.demo.android.newlife.utils.ToastShow;
import com.demo.android.newlife.widget.FragmentFactory;
import com.stephentuso.welcome.WelcomeHelper;

import butterknife.BindView;

import static com.demo.android.newlife.R.id.drawer_wechat;

public class MainActivity extends BaseActivity {

	@BindView(R.id.tool_bar)
	Toolbar toolBar;
	@BindView(R.id.my_fragment)
	FrameLayout myFragment;
	@BindView(R.id.navigation)
	NavigationView navigation;
	@BindView(R.id.drawer)
	DrawerLayout drawer;
	@BindView(R.id.toolbar_title)
	TextView toolbarTitle;
	private WelcomeHelper welcomeHelper;
	private ActionBarDrawerToggle mDrawerToggle;
	private FragmentManager mFragmentManager;
	private FragmentFactory mFragmentFactory;
	private Fragment defualtFragment;
	private MenuItem menuItem;
	private Fragment currentFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		welcomeHelper = new WelcomeHelper(this, SplashActivity.class);
		welcomeHelper.show(savedInstanceState);
	}

	@Override
	protected void initView() {
		mFragmentManager = getSupportFragmentManager();
		mFragmentFactory = new FragmentFactory(mFragmentManager,mContext);

		//默认显示的fragment
		switchFragment(mFragmentFactory.getItem(R.id.drawer_wechat));
		//菜单
		menuItem = navigation.getMenu().findItem(drawer_wechat);

		//点击navigation条目切换fragment
		navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
			@Override
			public boolean onNavigationItemSelected(@NonNull MenuItem item) {
				drawer.closeDrawer(GravityCompat.START);

				switch (item.getItemId()){
					case R.id.drawer_gank:

						switchFragment(mFragmentFactory.getItem(R.id.drawer_gank));
						break;
					case R.id.drawer_wechat:
						switchFragment(mFragmentFactory.getItem(R.id.drawer_wechat));
						break;

					case R.id.drawer_news:

						switchFragment(mFragmentFactory.getItem(R.id.drawer_news));
						break;

					case R.id.drawer_video:

						switchFragment(mFragmentFactory.getItem(R.id.drawer_video));
						break;

					case R.id.drawer_setting:

						break;

					case R.id.drawer_like:
						Intent intent = new Intent(MainActivity.this,VideoActivity.class);
						startActivity(intent);
						break;

					case R.id.drawer_about:

						break;
				}
				if(menuItem!=null){
					menuItem.setChecked(false);
				}
				menuItem = item;
				item.setChecked(true);
				return true;
			}
		});
	}


	public void switchFragment(Fragment fragment){
		if (fragment != null){
			if (currentFragment == null || !currentFragment
					.getClass().getName().equals(fragment.getClass().getName()))
				getSupportFragmentManager().beginTransaction().replace(R.id.my_fragment, fragment)
						.commit();
			currentFragment = fragment;
		}
	}

	@Override
	protected void initToolbar() {
		toolbarTitle.setText("女神");
		mDrawerToggle = new ActionBarDrawerToggle(this, drawer,
				toolBar, R.string.drawer_open, R.string.drawer_close);
		mDrawerToggle.syncState();
		drawer.addDrawerListener(mDrawerToggle);

		/**
		 * 去掉NavigationView滑动
		 */
		disableNavigationViewScrollbars(navigation);
	}

	@Override
	protected void initData() {

	}

	@Override
	public int getLayoutId() {
		return R.layout.activity_main;
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		welcomeHelper.onSaveInstanceState(outState);
	}

	private void disableNavigationViewScrollbars(NavigationView navigationView) {
		if (navigationView != null) {
			NavigationMenuView navigationMenuView = (NavigationMenuView) navigationView.getChildAt(0);
			if (navigationMenuView != null) {
				navigationMenuView.setVerticalScrollBarEnabled(false);
			}
		}
	}

	@Override
	public void onBackPressed() {
		if(drawer.isDrawerOpen(GravityCompat.START)){
			drawer.closeDrawer(GravityCompat.START);
		}else{
			_exit();
		}

	}

	long mExitTime=0;

	/**
	 * 退出
	 */
	private void _exit() {

		if (System.currentTimeMillis() - mExitTime > 2000) {
			ToastShow.getInstance(mContext).toastShow("再按一次退出程序");
			mExitTime = System.currentTimeMillis();
		} else {
			finish();
		}
	}
}
