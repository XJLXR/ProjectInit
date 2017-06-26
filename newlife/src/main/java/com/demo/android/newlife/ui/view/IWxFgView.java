package com.demo.android.newlife.ui.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by XiongRun on 2017/5/9.
 */

public interface IWxFgView {

	void setDataRefresh(Boolean refresh);
	RecyclerView getRecyclerView();
	LinearLayoutManager getLayoutManager();
}
