package com.demo.android.projectinit;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.blankj.utilcode.util.Utils;

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
