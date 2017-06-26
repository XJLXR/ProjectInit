package com.demo.android.newlife.ui.present;

import android.content.Context;

import com.demo.android.newlife.ui.base.BasePresent;
import com.demo.android.newlife.ui.view.IVideoFgView;

/**
 * Created by XiongRun on 2017/5/12.
 */

public class VideoPresent extends BasePresent<IVideoFgView> {

	private Context context;
	private IVideoFgView iVideoFgView;

	public VideoPresent(Context context){
		this.context =context;
	}

	public  void initData(){
		iVideoFgView = getRefVew();
		if (iVideoFgView != null){
			iVideoFgView.getTabLayout();
		}
	}
}
