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
import com.demo.android.newlife.bean.WXNews;
import com.demo.android.newlife.ui.activity.WxDetailActivity;

import java.util.List;


/**
 * Created by XiongRun on 2017/5/11.
 */

public class WxFgAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	private static final int TYPE_ITEM =0;  //普通Item View
	private static final int TYPE_FOOTER = -1;  //底部FootView
	private static final int TYPE_TOP =-2; //顶部
	//上拉加载更多
	public static final int  PULLUP_LOAD_MORE=0;
	//正在加载中
	public static final int  LOADING_MORE=1;
	//上拉加载更多状态-默认为0
	private int load_more_status=0;
	//没有数据
	public static final int NO_MORE_DATA=2;

	public List<WXNews.NewslistBean> mData;
	private Context mContext;

	public WxFgAdapter(Context context,List<WXNews.NewslistBean> mData){

		this.mData = mData;
		this.mContext = context;
	}

	public void setData(List<WXNews.NewslistBean> mData){
		this.mData = mData;
		notifyDataSetChanged();
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

		if (viewType == TYPE_ITEM ){
			View view = LayoutInflater.from(mContext).inflate(R.layout.item_fragment_wx,parent,false);
			ItemViewHolder itemViewHolder = new ItemViewHolder(view);
			return itemViewHolder;
		}/*else if (viewType == TYPE_FOOTER){
			View view = LayoutInflater.from(mContext).inflate(R.layout.item_fragment_wx_footer,parent,false);
			FootViewHolder footViewHolder = new FootViewHolder(view);
			return footViewHolder;
		}*/

		return null;
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder,  int position) {

		if (holder instanceof ItemViewHolder){
			((ItemViewHolder) holder).tvfrom.setText(mData.get(position).getDescription());
			((ItemViewHolder) holder).tvtime.setText(mData.get(position).getCtime());
			((ItemViewHolder) holder).tvtitle.setText(mData.get(position).getTitle());

			Glide.with(mContext)
					.load(mData.get(position).getPicUrl())
					.diskCacheStrategy(DiskCacheStrategy.ALL)
					.into(((ItemViewHolder) holder).iv);

			holder.itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(mContext,WxDetailActivity.class);

					Bundle bundle = new Bundle();
					bundle.putSerializable("WxNews",mData.get(position));
					intent.putExtras(bundle);
					mContext.startActivity(intent);
				}
			});
		}/*else if (holder instanceof FootViewHolder){
			switch (load_more_status){
				case PULLUP_LOAD_MORE:
					((FootViewHolder) holder).loadMore.setText("上拉加载更多...");
					break;
				case LOADING_MORE:
					((FootViewHolder) holder).loadMore.setText("正在加载更多数据...");
					break;

				case NO_MORE_DATA:
					((FootViewHolder) holder).loadMore.setText("没有更多数据...");
					break;
				default:

					break;
			}
		}*/

	}

	@Override
	public int getItemCount() {
		return mData == null?0:mData.size();
	}


	@Override
	public int getItemViewType(int position) {

		return TYPE_ITEM;
	/*	if (position == 0){
			return TYPE_TOP;
		}else*//* if (position == getItemCount()+1){
			return TYPE_FOOTER;
		}else {
			return TYPE_ITEM;
		}*/
	}

	//自定义的ViewHolder，持有每个Item的的所有界面元素
	public static class ItemViewHolder extends RecyclerView.ViewHolder {
		public TextView tvtime,tvtitle,tvfrom;
		public ImageView iv;
		public ItemViewHolder(View view){
			super(view);
			tvtitle = (TextView) view.findViewById(R.id.tv_title);
			tvtime = (TextView) view.findViewById(R.id.tv_time);
			tvfrom = (TextView) view.findViewById(R.id.tv_from);
			iv = (ImageView) view.findViewById(R.id.iv);
		}
	}


	/*public static class FootViewHolder extends RecyclerView.ViewHolder{

		public ProgressBar progress;
		public  TextView loadMore;

		public FootViewHolder(View itemView) {
			super(itemView);
			progress = (ProgressBar) itemView.findViewById(R.id.progress);
			loadMore = (TextView) itemView.findViewById(R.id.tv_load_prompt);
		}
	}*/

	/**
	 * //上拉加载更多
	 * PULLUP_LOAD_MORE=0;
	 * //正在加载中
	 * LOADING_MORE=1;
	 * //加载完成已经没有更多数据了
	 * NO_MORE_DATA=2;
	 * @param status
	 */
	/*public void changeMoreStatus(int status){
		load_more_status=status;
		notifyDataSetChanged();
	}*/

	/*public static class TopViewHolder extends RecyclerView.ViewHolder{

		public TopViewHolder(View itemView) {
			super(itemView);
		}
	}*/

}
