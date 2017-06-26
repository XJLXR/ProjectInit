package com.demo.android.projectinit.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.demo.android.projectinit.R;
import com.demo.android.projectinit.bean.Video;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MyViewHolder> {


	int[] videoIndexs = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
	private Context context;

	public VideoAdapter(Context context){
		this.context = context;
	}
	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
				context).inflate(R.layout.item_video, parent,
				false));
		return holder;
	}

	@Override
	public void onBindViewHolder(MyViewHolder holder, int position) {
		 holder.jcVideoPlayer.setUp(
				Video.videoUrls[0][position], JCVideoPlayer.SCREEN_LAYOUT_LIST,
				Video.videoTitles[0][position]);
		Glide.with(context)
				.load(Video.videoThumbs[0][position])
				.into(holder.jcVideoPlayer.thumbImageView);

	}

	@Override
	public int getItemCount() {
		return videoIndexs.length;
	}

	public class MyViewHolder extends RecyclerView.ViewHolder {

		JCVideoPlayerStandard jcVideoPlayer;

		public MyViewHolder(View itemView) {
			super(itemView);
			jcVideoPlayer = (JCVideoPlayerStandard) itemView.findViewById(R.id.videoplayer);

		}
	}
}
