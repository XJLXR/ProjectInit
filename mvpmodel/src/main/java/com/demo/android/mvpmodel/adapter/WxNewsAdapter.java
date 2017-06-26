package com.demo.android.mvpmodel.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.demo.android.mvpmodel.R;
import com.demo.android.mvpmodel.bean.WXNews;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by XiongRun on 2017/5/9.
 */

public class WxNewsAdapter extends CommonAdapter<WXNews.NewslistBean> {


	public WxNewsAdapter(Context context, int layoutId, List<WXNews.NewslistBean> datas) {
		super(context, layoutId, datas);
	}

	@Override
	protected void convert(ViewHolder holder, WXNews.NewslistBean newslistBean, int position) {
		holder.setText(R.id.tv_title,newslistBean.getTitle());
		holder.setText(R.id.tv_time,newslistBean.getCtime());
		holder.setText(R.id.tv_from,newslistBean.getDescription());

		Glide.with(mContext)
				.load(newslistBean.getPicUrl())
				.asBitmap()
				.into((ImageView) holder.getView(R.id.iv));


	}
}
