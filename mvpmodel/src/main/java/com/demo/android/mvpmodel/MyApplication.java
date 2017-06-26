package com.demo.android.mvpmodel;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.blankj.utilcode.util.Utils;
import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;

import java.util.LinkedList;
import java.util.List;


/**
 * Created by XiongRun on 2016/9/4.
 */

public class MyApplication extends Application {

	public static Context mContext;
	public  static Handler handler;
	private List<Activity> activityList = new LinkedList<Activity>();

	private static MyApplication instance;

	public MyApplication(){

		getAppContext();
	}
	// 单例模式中获取唯一的MyApplication实例
	public static MyApplication getInstance(){

		synchronized (MyApplication.class){
			if (instance == null){
				synchronized (MyApplication.class){
					instance = new MyApplication();
				}
			}
		}
		return instance;
	}

	public static Context getAppContext() {
		return mContext;
	}
	@Override
	public void onCreate() {
		super.onCreate();
		mContext = this;
		handler = new Handler();
		Utils.init(getAppContext());

		if (LeakCanary.isInAnalyzerProcess(this)) {
			return;
		}
		LeakCanary.install(this);

		//初始化Stetho调试工具
		Stetho.initialize(
				Stetho.newInitializerBuilder(this)
						.enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
						.enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
						.build());
	}

	// 添加Activity到容器中
	public void addActivity(Activity activity) {
		activityList.add(activity);
		Log.v("TAG","添加数据了");
	}

	// 添加Activity到容器中
	public void clearActivity() {
		activityList.clear();
	}
}
