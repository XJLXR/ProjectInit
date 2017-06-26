package com.demo.android.newlife.ui.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.demo.android.newlife.R;
import com.demo.android.newlife.bean.Livings;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by XiongRun on 2017/5/12.
 */

public class JokAdapter extends CommonAdapter<Livings.LivesBean> {

	private Context mContext;
	public JokAdapter(Context context, int layoutId, List<Livings.LivesBean> datas) {
		super(context, layoutId, datas);
		this.mContext=context;
	}

	@Override
	protected void convert(ViewHolder holder, Livings.LivesBean livesBean, int position) {
		holder.setText(R.id.tv_title,livesBean.getName()+"距离您"+livesBean.getDistance());
		Glide.with(mContext)
				.load(livesBean.getCreator().getPortrait())
				.centerCrop()
				.into((ImageView) holder.getView(R.id.iv));
	}
}
