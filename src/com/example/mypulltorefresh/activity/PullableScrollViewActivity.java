package com.example.mypulltorefresh.activity;

import com.example.mypulltorefresh.PullableViewLayout;


import com.example.mypulltorefresh.PullableViewLayout.PullToRefreshListener;
import com.example.mypulltorefresh.R;

import android.app.Activity;
import android.os.Bundle;

public class PullableScrollViewActivity extends Activity {
	
	private PullableViewLayout pullLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scrollview);
		//这是一个带header头，canPullDown(),canPullUp()检测的layout
		pullLayout=(PullableViewLayout)findViewById(R.id.refresh_view);
		//为pullLayout注册刷新监听事件
		pullLayout.setOnRefreshListener(new PullToRefreshListener(){

			@Override
			public void onRefresh() {
				try{
					Thread.sleep(3000);
				}catch(InterruptedException e){
					e.printStackTrace();
				}
				pullLayout.refreshingFinish();
			}},0);
		
	}

}
