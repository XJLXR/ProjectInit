package com.demo.android.newlife.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.demo.android.newlife.R;
import com.demo.android.newlife.ui.base.BaseActivity;
import com.demo.android.newlife.utils.ToastShow;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

import static com.demo.android.newlife.R.id.iv_meizhi_pic;

/**
 * Created by XiongRun on 2017/5/12.
 */

public class MeiZhiActivity extends BaseActivity {
	@BindView(iv_meizhi_pic)
	PhotoView ivMeizhiPic;
	@BindView(R.id.save_img)
	FloatingActionButton saveImg;
	@BindView(R.id.layout_pic)
	RelativeLayout layoutPic;
	private PhotoViewAttacher photoViewAttacher;

	@Override
	protected void initView() {
		String url = getIntent().getStringExtra("url");
		logger.d(url);
		photoViewAttacher = new PhotoViewAttacher(ivMeizhiPic);
		Glide.with(mContext)
				.load(url)
				.asBitmap()
				.into(ivMeizhiPic);

		photoViewAttacher.update();
	}

	@Override
	protected void initToolbar() {

	}

	@Override
	protected void initData() {
		saveImg.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				saveImg();
			}
		});
	}

	@Override
	public int getLayoutId() {
		return R.layout.activity_meizhi;
	}

	@Override
	public boolean canBack() {
		return true;
	}

	public void saveImg(){
		ivMeizhiPic.setDrawingCacheEnabled(true);
		ivMeizhiPic.buildDrawingCache();
		Bitmap bitmap = ivMeizhiPic.getDrawingCache();

		//将Bitmap 转换成二进制，写入本地
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG , 100 , stream);
		byte[] byteArray = stream.toByteArray();

		File dir=new File(Environment.getExternalStorageDirectory ().getAbsolutePath()+"/meinv" );
		if (!dir.exists()){
			dir.mkdir();
		}
		String filename = System.currentTimeMillis() + ".png";
		File file = new File(dir,filename);
		try {
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(byteArray, 0, byteArray.length);
			fos.flush();
			//用广播通知相册进行更新相册
			Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
			Uri uri = Uri.fromFile(file);
			intent.setData(uri);
			MeiZhiActivity.this.sendBroadcast(intent);
			ToastShow.mToastShow.toastShow("保存图片成功");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			ToastShow.mToastShow.toastShow(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			ToastShow.mToastShow.toastShow(e.getMessage());
		}
	}
}
