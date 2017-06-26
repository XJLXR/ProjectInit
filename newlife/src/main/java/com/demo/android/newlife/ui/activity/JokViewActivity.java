package com.demo.android.newlife.ui.activity;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.demo.android.newlife.R;
import com.demo.android.newlife.utils.MediaUtils;
import com.dou361.ijkplayer.listener.OnShowThumbnailListener;
import com.dou361.ijkplayer.widget.PlayStateParams;
import com.dou361.ijkplayer.widget.PlayerView;

import static com.demo.android.newlife.MyApplication.mContext;

/**
 * Created by XiongRun on 2017/5/8.
 */

public class JokViewActivity extends AppCompatActivity {

	private PowerManager.WakeLock wakeLock;
	private PowerManager pm;
	private PlayerView player;
	private String addr;
	private Context context;
	private View rootView;



	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addr = getIntent().getStringExtra("addr");
		this.context = this;
		rootView = getLayoutInflater().from(this).inflate(R.layout.activity_jok_view, null);

		setContentView(rootView);

		pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		wakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "liveTAG");
		wakeLock.acquire();

		player = new PlayerView(this, rootView)
				.setScaleType(PlayStateParams.fitparent)
				.hideMenu(true)
				.hideSteam(true)
				.setForbidDoulbeUp(true)
				.hideCenterPlayer(true)
				.hideControlPanl(true)

				.showThumbnail(new OnShowThumbnailListener() {
					@Override
					public void onShowThumbnail(ImageView ivThumbnail) {
						Glide.with(context)
								.load("http://pic2.nipic.com/20090413/406638_125424003_2.jpg")
								.placeholder(R.color.cl_default)
								.error(R.color.cl_error)
								.into(ivThumbnail);
					}
				})
				.setPlaySource(addr)
				.startPlay();

	}

	@Override
	protected void onPause() {
		super.onPause();
		if (player != null) {
			player.onPause();
		}
		MediaUtils.muteAudioFocus(mContext, true);
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (player != null) {
			player.onResume();
		}
		MediaUtils.muteAudioFocus(mContext, false);
		if (wakeLock != null) {
			wakeLock.acquire();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (player != null) {
			player.onDestroy();
		}
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if (player != null) {
			player.onConfigurationChanged(newConfig);
		}
	}

	@Override
	public void onBackPressed() {
		if (player != null && player.onBackPressed()) {
			return;
		}
		super.onBackPressed();
		if (wakeLock != null) {
			wakeLock.release();
		}
	}
}
