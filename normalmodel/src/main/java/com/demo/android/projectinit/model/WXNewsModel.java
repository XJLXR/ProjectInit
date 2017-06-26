package com.demo.android.projectinit.model;

import com.demo.android.projectinit.bean.WXNews;
import com.demo.android.projectinit.http.HttpMethod;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by XiongRun on 2017/5/9.
 */

public class WXNewsModel {

	public Observable<WXNews> getNewsList(Map<String,String> map){
		return HttpMethod.getInstance().getWxApiServer().getNewsList(map)
				.subscribeOn(Schedulers.io())
				.unsubscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread());
	}
}
