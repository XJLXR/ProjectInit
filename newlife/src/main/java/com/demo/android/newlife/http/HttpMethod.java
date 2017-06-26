package com.demo.android.newlife.http;

import android.util.Log;

import com.demo.android.newlife.MyApplication;
import com.demo.android.newlife.api.GankApiServer;
import com.demo.android.newlife.api.LivingsServer;
import com.demo.android.newlife.api.WXApiServer;
import com.demo.android.newlife.utils.CommonUtil;
import com.demo.android.newlife.utils.MyLogger;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by XiongRun on 2017/5/9.
 */

public class HttpMethod {

	private static final int DEFAULT_TIMEOUT = 5;

	private static HttpMethod INSTANCE;
	private  OkHttpClient sOkHttpClient;

	/**
	 * 无网络时设缓存有效期为两天
	 */
	public static final long CACHE_STALE_SEC = 60 * 60 * 24 * 2;

	/**
	 * 有网时设置缓存为1天
	 */

	public static final long CACHE_STALE_AGE = 60 * 60;

	/**
	 * 查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
	 * max-stale 指示客户机可以接收超出超时期间的响应消息。如果指定max-stale消息的值，那么客户机可接收超出超时期指定值之内的响应消息。
	 */
	public static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;

	/**
	 * 查询网络的Cache-Control设置，头部Cache-Control设为max-age=0
	 * (假如请求了服务器并在a时刻返回响应结果，则在max-age规定的秒数内，浏览器将不会发送对应的请求到服务器，数据由缓存直接返回)时则不会使用缓存而请求服务器
	 */
	public static final String CACHE_CONTROL_AGE = "max-age=0";

	private final Retrofit wxRetrofit;
	private final WXApiServer wxApiServer;
	private final Retrofit gankRetrofit;
	private final GankApiServer gankApiServer;
	private final Retrofit lsRetrofit;
	private final LivingsServer livingsServer;

	//微信接口
	public WXApiServer getWxApiServer(){
		return wxApiServer;
	}

	public GankApiServer getGankApiServer(){
		return gankApiServer;
	}

	public LivingsServer getLivingsServer(){
		return livingsServer;
	}


	private HttpMethod(){

		//此处添加RxJavaCallAdapterFactory，把请求结果直接映射
		wxRetrofit = new Retrofit.Builder()
			   .client(getsOkHttpClient())
			   .addConverterFactory(GsonConverterFactory.create())
			   .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
			   .baseUrl(WXApiServer.BASE_WX_URL)
			   .build();

		wxApiServer = wxRetrofit.create(WXApiServer.class);

		gankRetrofit = new Retrofit.Builder()
				.client(getsOkHttpClient())
				.addConverterFactory(GsonConverterFactory.create())
				.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
				.baseUrl(GankApiServer.BASE_GANK_URL)
				.build();

		gankApiServer = gankRetrofit.create(GankApiServer.class);

		lsRetrofit = new Retrofit.Builder()
				.client(getsOkHttpClient())
				.addConverterFactory(GsonConverterFactory.create())
				.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
				.baseUrl(LivingsServer.BASE_URL)
				.build();

		livingsServer = lsRetrofit.create(LivingsServer.class);
	}

	private OkHttpClient getsOkHttpClient(){

		if (sOkHttpClient == null){
			synchronized (HttpMethod.class){
				Cache mCache = new Cache(new File(MyApplication.getAppContext().getCacheDir(),"HttpCache"),1024*1024*100);//100m
				if (sOkHttpClient == null){
					sOkHttpClient = new OkHttpClient.Builder()
							.cache(mCache)
							.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
							.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
							.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
							.retryOnConnectionFailure(true)
							.addNetworkInterceptor(loggingInterceptor)
							.addNetworkInterceptor(new StethoInterceptor())
							.addInterceptor(mRewriteCacheControlInterceptor)
							.addInterceptor(mLoggingInterceptor)
							.build();
				}
			}
		}

		return sOkHttpClient;
	}

	public static HttpMethod getInstance(){
		if (INSTANCE == null){
			synchronized (HttpMethod.class){
				INSTANCE = new HttpMethod();
			}
		}
		return INSTANCE;
	}

	/**
	 * //拦截请求和响应日志并输出，其实有很多封
	 * 装好的日志拦截插件，大家也可以根据个人喜好选择。
	 */

	private HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
		@Override
		public void log(String message) {
			Log.i("HttpManager",message);
		}
	}).setLevel(HttpLoggingInterceptor.Level.BODY);

	/**
	 * 云端响应头拦截器，用来配置缓存策略
	 * Dangerous interceptor that rewrites the server's cache-control header.
	 */
	private final Interceptor mRewriteCacheControlInterceptor = new Interceptor() {
		@Override
		public Response intercept(Chain chain) throws IOException {
			Request request = chain.request();

			//有网络时只从网络获取
			if (CommonUtil.isNetworkAvailable(MyApplication.getAppContext())) {
				request = request.newBuilder()
						.cacheControl(CacheControl.FORCE_NETWORK)
						.build();
				MyLogger.kLog().d("no nets");
			}else {
				//无网络时只从缓存中读取
				request = request.newBuilder()
						.cacheControl(CacheControl.FORCE_CACHE)
						.build();
			}

			Response originalResponse = chain.proceed(request);
			if (CommonUtil.isNetworkAvailable(MyApplication.getAppContext())) {
				//有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
				//String cacheControl = request.cacheControl().toString();
				return originalResponse.newBuilder()
						.header("Cache-Control", "public, max-age=" + CACHE_STALE_AGE)
						.removeHeader("Pragma")
						.build();
			} else {
				return originalResponse.newBuilder()
						.header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_STALE_SEC)
						.removeHeader("Pragma")
						.build();
			}
		}
	};


	private final Interceptor mLoggingInterceptor = new Interceptor() {
		@Override
		public Response intercept(Chain chain) throws IOException {
			//这个chain里面包含了request和response，所以你要什么都可以从这里拿
			Request request = chain.request();
			long t1 = System.nanoTime();//请求发起的时间
			MyLogger.kLog().i(String.format("Sending request %s on %s%n%s", request.url(), chain.connection(), request.headers()));
			Response response = chain.proceed(request);
			long t2 = System.nanoTime();//收到响应的时间
			MyLogger.kLog().i(String.format(
					Locale.getDefault(),
					"Received response for %s in %.1fms%n%s",
					response.request().url(),
					(t2 - t1) / 1e6d,
					response.headers()));
			return response;
		}
	};
}
