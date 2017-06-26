package com.demo.android.mvpmodel.model;

import com.demo.android.mvpmodel.bean.WXNews;
import com.demo.android.mvpmodel.http.HttpMethod;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by XiongRun on 2017/5/17.
 */

public class WxModel  {

	/**
	 * 获取数据
	 * @param map
	 * @return
	 */
	public Observable<WXNews> getNewsList(Map<String,String> map){
		return HttpMethod.getInstance().getWxApiServer().getNewsList(map)
				.subscribeOn(Schedulers.io())
				.unsubscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread());
	}

}
