package com.demo.android.newlife.ui.base;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.demo.android.newlife.MyApplication;
import com.demo.android.newlife.utils.MyLogger;
import com.demo.android.newlife.utils.UmengUtils;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import butterknife.ButterKnife;

/**
 * Created by XiongRun on 2017/5/5.
 */

public abstract class BaseActivity<V ,T extends BasePresent<V>> extends AppCompatActivity {


	public Context mContext;
	protected String TAG ;
	public T mPresenter;

	protected MyApplication application;
	protected SystemBarTintManager tintManager;
	protected MyLogger logger;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getLayoutId());
		mContext = this;
		logger = MyLogger.kLog();
		ButterKnife.bind(this);
		TAG = this.getClass().getSimpleName();

		//获取present引用允许为空，不是所有都要实现MVP模式
		if (getPresenter() != null){
			mPresenter = getPresenter();
			mPresenter.attachView((V)this);
		}

		/*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
			setTranslucentStatus(getResources().getColor(R.color.colorPrimary));
		}
*/

		initView();
		initToolbar();
		initData();
		application= MyApplication.getInstance();
		application.addActivity(this);

	}


	/**
	 * 设置状态栏背景状态
	 * <p/>
	 * 4.4以上
	 */
	public void setTranslucentStatus(int color) {

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			Window win = getWindow();
			WindowManager.LayoutParams winParams = win.getAttributes();
			final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
			winParams.flags |= bits;
			win.setAttributes(winParams);
		}
		tintManager = new SystemBarTintManager(this);
		tintManager.setStatusBarTintEnabled(true);
		tintManager.setTintColor(color);
	}

	protected abstract void initView();

	protected abstract void initToolbar();

	protected abstract void initData();

	@Override
	protected void onDestroy() {
		super.onDestroy();
		application = null;
		if(mPresenter!=null){
			mPresenter.dettachView();
		}
	}

	/**
	 * 获取Presenter
	 * */
	public T getPresenter() {
		return null;
	}

	/**
	 * 判断当前 Activity 是否允许返回
	 * 主界面不允许返回，次级界面允许返回
	 *
	 * @return false
	 */
	public boolean canBack() {
		return false;
	}

	/**
	 * 添加布局文件
	 * @return
	 */
	public abstract int getLayoutId() ;


	@Override
	protected void onResume() {
		super.onResume();
		UmengUtils.onResumeToActivity(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		UmengUtils.onPauseToActivity(this);
	}
}
