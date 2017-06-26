package com.demo.android.newlife.ui.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.demo.android.newlife.R;
import com.demo.android.newlife.bean.WXNews;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by XiongRun on 2017/5/9.
 */

public class WxAdapter extends CommonAdapter<WXNews.NewslistBean> {
	public List<WXNews.NewslistBean> mList = null;

	public WxAdapter(Context context, int layoutId, List<WXNews.NewslistBean> datas) {
		super(context, layoutId, datas);
		this.mList =datas;
	}


	public void setData(List<WXNews.NewslistBean> list){
		this.mList = list;
		notifyDataSetChanged();
	}

	@Override
	protected void convert(ViewHolder holder, WXNews.NewslistBean newslistBean, int position) {
		holder.setText(R.id.tv_title,newslistBean.getTitle());
		holder.setText(R.id.tv_time,newslistBean.getCtime());
		holder.setText(R.id.tv_from,newslistBean.getDescription());

		Glide.with(mContext)
				.load(newslistBean.getPicUrl())
				.skipMemoryCache(false)
				.diskCacheStrategy(DiskCacheStrategy.ALL)
				.into((ImageView) holder.getView(R.id.iv));
	}
}

