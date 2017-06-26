package com.demo.android.projectinit.base;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.demo.android.projectinit.R;
import com.demo.android.projectinit.utils.CommonUtils;
import com.demo.android.projectinit.utils.MyLogger;
import com.demo.android.projectinit.widget.VaryViewHelperController;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

	protected String TAG ;
	protected VaryViewHelperController mVaryViewHelperController;
	protected MyLogger logger;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getContentView());
		logger = MyLogger.kLog();
		ButterKnife.bind(this);
		TAG = this.getClass().getSimpleName();


		if (null != getLoadingTargetView()) {
			mVaryViewHelperController = new VaryViewHelperController(getLoadingTargetView());
		}

		initData();
		initView();
		initToolBar();

	}

	protected abstract void initToolBar();

	/**
	 * 初始化view
	 */
	protected abstract void initView();

	/**
	 * 初始化数据
	 */
	protected abstract void initData();

	/**
	 * 根据布局id
	 * @return
	 */
	protected abstract int getContentView() ;

	protected abstract View getLoadingTargetView();


	/**
	 * startActivity
	 *
	 * @param clazz
	 */
	protected void readyGo(Class<?> clazz) {
		Intent intent = new Intent(this, clazz);
		startActivity(intent);
		overridePendingTransition(R.anim.in_from_right,R.anim.out_to_left);
	}

	/**
	 * startActivity with bundle
	 *
	 * @param clazz
	 * @param bundle
	 */
	protected void readyGo(Class<?> clazz, Bundle bundle) {
		Intent intent = new Intent(this, clazz);
		if (null != bundle) {
			intent.putExtras(bundle);
		}
		startActivity(intent);
		overridePendingTransition(R.anim.in_from_right,R.anim.out_to_left);
	}

	/**
	 * startActivity then finish
	 *
	 * @param clazz
	 */
	protected void readyGoThenKill(Class<?> clazz) {
		Intent intent = new Intent(this, clazz);
		startActivity(intent);
		overridePendingTransition(R.anim.in_from_right,R.anim.out_to_left);
		finish();
	}

	/**
	 * startActivity with bundle then finish
	 *
	 * @param clazz
	 * @param bundle
	 */
	protected void readyGoThenKill(Class<?> clazz, Bundle bundle) {
		Intent intent = new Intent(this, clazz);
		if (null != bundle) {
			intent.putExtras(bundle);
		}
		startActivity(intent);
		overridePendingTransition(R.anim.in_from_right,R.anim.out_to_left);
		finish();
	}

	/**
	 * startActivityForResult
	 *
	 * @param clazz
	 * @param requestCode
	 */
	protected void readyGoForResult(Class<?> clazz, int requestCode) {
		Intent intent = new Intent(this, clazz);
		startActivityForResult(intent, requestCode);
		overridePendingTransition(R.anim.in_from_right,R.anim.out_to_left);
	}

	/**
	 * startActivityForResult with bundle
	 *
	 * @param clazz
	 * @param requestCode
	 * @param bundle
	 */
	protected void readyGoForResult(Class<?> clazz, int requestCode, Bundle bundle) {
		Intent intent = new Intent(this, clazz);
		if (null != bundle) {
			intent.putExtras(bundle);
		}
		startActivityForResult(intent, requestCode);
		overridePendingTransition(R.anim.in_from_right,R.anim.out_to_left);
	}


	/**
	 * show toast
	 *
	 * @param msg
	 */
	protected void showToast(String msg) {
		if (null != msg && !CommonUtils.isEmpty(msg)) {
			Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
		}
	}
	protected void showNetWorkError() {
		showToast(getResources().getString(R.string.network_error_tips));
	}


	/**
	 * toggle show loading
	 *正在加载中布局
	 * @param toggle
	 */
	protected void toggleShowLoading(boolean toggle, String msg) {
		if (null == mVaryViewHelperController) {
			throw new IllegalArgumentException("You must return a right target view for loading");
		}

		if (toggle) {
			mVaryViewHelperController.showLoading(msg);
		} else {
			mVaryViewHelperController.restore();
		}
	}

	/**
	 * toggle show empty
	 *空数据布局
	 * @param toggle
	 */
	protected void toggleShowEmpty(boolean toggle, String msg, View.OnClickListener onClickListener) {
		if (null == mVaryViewHelperController) {
			throw new IllegalArgumentException("You must return a right target view for loading");
		}

		if (toggle) {
			mVaryViewHelperController.showEmpty(msg, onClickListener);
		} else {
			mVaryViewHelperController.restore();
		}
	}

	/**
	 * toggle show error
	 *数据加载错误布局
	 * @param toggle
	 */
	protected void toggleShowError(boolean toggle, String msg, View.OnClickListener onClickListener) {
		if (null == mVaryViewHelperController) {
			throw new IllegalArgumentException("You must return a right target view for loading");
		}

		if (toggle) {
			mVaryViewHelperController.showError(msg, onClickListener);
		} else {
			mVaryViewHelperController.restore();
		}
	}

	/**
	 * toggle show network error
	 *网络错误布局
	 * @param toggle
	 */
	protected void toggleNetworkError(boolean toggle, View.OnClickListener onClickListener) {
		if (null == mVaryViewHelperController) {
			throw new IllegalArgumentException("You must return a right target view for loading");
		}

		if (toggle) {
			mVaryViewHelperController.showNetworkError(onClickListener);
		} else {
			mVaryViewHelperController.restore();
		}
	}

	/**
	 * 重新加载布局
	 */
	protected void toggleRestore(){
		mVaryViewHelperController.restore();
	}


	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
	}
}
