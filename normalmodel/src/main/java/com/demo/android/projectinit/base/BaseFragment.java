package com.demo.android.projectinit.base;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.demo.android.projectinit.R;
import com.demo.android.projectinit.utils.CommonUtils;
import com.demo.android.projectinit.widget.VaryViewHelperController;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment {
	protected View mRootView;
	protected String TAG;
	protected VaryViewHelperController mVaryViewHelperController;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		if (mRootView == null){
			mRootView = inflater.inflate(getContentView(), container, false);
			TAG=this.getClass().getSimpleName();
			ButterKnife.bind(this, mRootView);
			if (null != getLoadingTargetView()) {
				mVaryViewHelperController = new VaryViewHelperController(getLoadingTargetView());
			}

			initViews(mRootView);
			initToolBar();
			initData();

		}else {
			ViewGroup parent = (ViewGroup) mRootView.getParent();
			if (null != parent) {
				parent.removeView(mRootView);
			}
		}


		return mRootView;
	}

	protected abstract void initToolBar();

	protected abstract void initData();

	protected abstract void initViews(View mRootView);

	protected abstract View getLoadingTargetView();

	public abstract int getContentView();


	/**
	 * startActivity with bundle
	 *
	 * @param clazz
	 * @param bundle
	 */
	protected void readyGo(Class<?> clazz, Bundle bundle) {
		Intent intent = new Intent(getActivity(), clazz);
		if (null != bundle) {
			intent.putExtras(bundle);
		}
		startActivity(intent);
		getActivity().overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
	}
	/**
	 * startActivity
	 *
	 * @param clazz
	 */
	protected void readyGo(Class<?> clazz) {
		Intent intent = new Intent(getActivity(), clazz);
		startActivity(intent);
		getActivity().overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
	}

	/**
	 * startActivityForResult
	 *
	 * @param clazz
	 * @param requestCode
	 */
	protected void readyGoForResult(Class<?> clazz, int requestCode) {
		Intent intent = new Intent(getActivity(), clazz);
		startActivityForResult(intent, requestCode);
		getActivity().overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
	}

	/**
	 * startActivityForResult with bundle
	 *
	 * @param clazz
	 * @param requestCode
	 * @param bundle
	 */
	protected void readyGoForResult(Class<?> clazz, int requestCode, Bundle bundle) {
		Intent intent = new Intent(getActivity(), clazz);
		if (null != bundle) {
			intent.putExtras(bundle);
		}
		startActivityForResult(intent, requestCode);
		getActivity().overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
	}

	protected void showToast(String msg) {
		if (null != msg && !CommonUtils.isEmpty(msg)) {
			Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
//            Snackbar.make(((Activity) getActivity()).getWindow().getDecorView(), msg, Snackbar.LENGTH_SHORT).show();
		}
	}

	protected void showNetWorkError() {
		showToast(getResources().getString(R.string.network_error_tips));
	}

	/**
	 * toggle show loading
	 *
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
	 *
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
	 *
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
	 *
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

	protected void toggleRestore(){
		mVaryViewHelperController.restore();
	}
}

