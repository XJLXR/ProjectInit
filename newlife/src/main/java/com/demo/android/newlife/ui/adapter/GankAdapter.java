package com.demo.android.newlife.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.demo.android.newlife.R;
import com.demo.android.newlife.bean.Meizhi;
import com.demo.android.newlife.ui.activity.MeiZhiActivity;
import com.demo.android.newlife.utils.ToastShow;

import java.util.List;

/**
 * Created by XiongRun on 2017/5/12.
 */

public class GankAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	private Context context;
	private List<Meizhi.ResultsBean> mList;

	public GankAdapter(Context mContext, List<Meizhi.ResultsBean> mList) {
		this.context = mContext;
		this.mList = mList;
	}
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

		View view = LayoutInflater.from(context).inflate(R.layout.item_fragment_gank,parent,false);
		return new ItemViewHolder(view);
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
		if (holder instanceof ItemViewHolder){
			((ItemViewHolder) holder).tv_meizhi_title.setText(mList.get(position).getPublishedAt());
			Glide.with(context)
					.load(mList.get(position).getUrl())
					.diskCacheStrategy(DiskCacheStrategy.ALL)
					.into(((ItemViewHolder) holder).iv_meizi);


			((ItemViewHolder) holder).iv_meizi.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					ToastShow.getInstance(context).toastShow("postion"+position);
					Intent intent = new Intent(context,MeiZhiActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("url",mList.get(position).getUrl());
					intent.putExtras(bundle);
					context.startActivity(intent);
				}
			});


		}
	}

	@Override
	public int getItemCount() {
		return mList.size();
	}

	public class ItemViewHolder extends RecyclerView.ViewHolder {

		public TextView tv_meizhi_title;
		public ImageView iv_meizi;

		public ItemViewHolder(View itemView) {
			super(itemView);
			tv_meizhi_title = (TextView) itemView.findViewById(R.id.tv_meizhi_title);
			iv_meizi = (ImageView) itemView.findViewById(R.id.iv_meizhi);
		}
	}
}
