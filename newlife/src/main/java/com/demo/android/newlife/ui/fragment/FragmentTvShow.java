package com.demo.android.newlife.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.demo.android.newlife.R;

/**
 * Created by XiongRun on 2017/5/11.
 */

public class FragmentTvShow extends Fragment {

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_tvshow,container,false);
		return view;
	}
	/*@Override
	protected void initData() {

	}

	@Override
	protected void initToolbar() {

	}

	@Override
	protected void initView(View rootView) {

	}

	@Override
	public int createViewLayoutId() {
		return 0;
	}*/
}
