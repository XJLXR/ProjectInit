package com.demo.android.newlife.api;

import com.demo.android.newlife.bean.Meizhi;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by XiongRun on 2017/5/12.
 */

public interface GankApiServer {

	String BASE_GANK_URL = "http://gank.io/api/";

	@GET("data/福利/10/{page}")
	Observable<Meizhi> getMeizhiData(@Path("page") int page);
}
