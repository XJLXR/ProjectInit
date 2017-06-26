package com.demo.android.newlife.ui.view;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by XiongRun on 2017/5/12.
 */

public interface IGankFgView {

	void setDataRefresh(Boolean refresh);
	RecyclerView getRecyclerView();
	GridLayoutManager getLayoutManager();
}
