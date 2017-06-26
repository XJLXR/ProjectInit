package com.demo.android.newlife.ui.model;

import com.demo.android.newlife.bean.WXNews;
import com.demo.android.newlife.http.HttpMethod;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by XiongRun on 2017/5/9.
 */

public class WxFgModel {

	public Observable<WXNews> getNewsList(Map<String,String> map){
		return HttpMethod.getInstance().getWxApiServer().getNewsList(map)
				.subscribeOn(Schedulers.io())
				.unsubscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread());
	}
}
