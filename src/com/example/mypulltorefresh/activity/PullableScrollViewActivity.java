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
		//����һ����headerͷ��canPullDown(),canPullUp()����layout
		pullLayout=(PullableViewLayout)findViewById(R.id.refresh_view);
		//ΪpullLayoutע��ˢ�¼����¼�
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
