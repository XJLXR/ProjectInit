package com.demo.android.newlife.ui.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.demo.android.newlife.R;
import com.demo.android.newlife.bean.Livings;
import com.demo.android.newlife.http.HttpMethod;
import com.demo.android.newlife.ui.adapter.JokAdapter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by XiongRun on 2017/5/11.
 */

public class FragmentJok extends Fragment {

	private View view;
	private XRecyclerView recyclerView;
	private GridLayoutManager manager;
	private ProgressDialog dialog;
	private List<Livings.LivesBean> lives;
	private List<Livings.LivesBean> mList;
	private JokAdapter adapter;
	private Intent intent;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.fragment_jok,container,false);

		initViews();
		initData();
		return view;
	}

	private void initViews() {
		mList = new ArrayList<>();
		dialog = new ProgressDialog(getContext());
		recyclerView = (XRecyclerView) view.findViewById(R.id.recycler);
		manager = new GridLayoutManager(getContext(),2);
		recyclerView.setLayoutManager(manager);
		adapter = new JokAdapter(getContext(), R.layout.item_jok,mList);
		recyclerView.setAdapter(adapter);

		/*adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {



			@Override
			public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
				intent = new Intent(getContext(),JokViewActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("addr",lives.get(position-1).getStream_addr());
				intent.putExtras(bundle);
				getContext().startActivity(intent);
			}

			@Override
			public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
				return false;
			}
		});*/
	}

	private void initData() {
		loadData();
	}

	private void loadData() {

		HttpMethod.getInstance().getLivingsServer().getData()
				.subscribeOn(Schedulers.io())
				.doOnSubscribe(new Consumer<Disposable>() {
					@Override
					public void accept(Disposable disposable) throws Exception {
						dialog.show();
					}
				})
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Observer<Livings>() {
					@Override
					public void onSubscribe(Disposable d) {

					}

					@Override
					public void onNext(Livings value) {
						lives = value.getLives();
						if (lives != null){
							mList.addAll(lives);
							adapter.notifyDataSetChanged();
						}else {
							return;
						}

					}

					@Override
					public void onError(Throwable e) {
						dialog.dismiss();
					}

					@Override
					public void onComplete() {
						dialog.dismiss();
					}
				});

	}

}
