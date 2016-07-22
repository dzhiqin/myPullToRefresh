package com.example.mypulltorefresh.activity;

import java.util.ArrayList;
import java.util.List;

import com.example.mypulltorefresh.PullableViewLayout;


import com.example.mypulltorefresh.PullableViewLayout.PullToRefreshListener;
import com.example.mypulltorefresh.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class PullableListViewActivity extends Activity {
	private ListView listView;
	private PullableViewLayout pullLayout;
	private boolean isFirsttIn=true;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listview);
		pullLayout=(PullableViewLayout)findViewById(R.id.refresh_view);
		pullLayout.setOnRefreshListener(new PullToRefreshListener(){

			@Override
			public void onRefresh() {
				try{
					Thread.sleep(3000);
				}catch(InterruptedException e){
					e.printStackTrace();
				}
				pullLayout.refreshingFinish();
			}
			
		}, 0);
		listView=(ListView)findViewById(R.id.content_view);
		List<String> items=new ArrayList<String>();
		for(int i=0;i<30;i++){
			items.add("item"+i);
		}
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items);
		listView.setAdapter(adapter);
	}
	@Override
	public void onWindowFocusChanged(boolean hasFocus){
		super.onWindowFocusChanged(hasFocus);;
		//isFirstIn 第一次进入 自动刷新
		//...
	}
	
}
