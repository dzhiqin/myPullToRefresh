package com.example.mypulltorefresh.activity;

import com.example.mypulltorefresh.PullableViewLayout;
import com.example.mypulltorefresh.PullableViewLayout.PullToRefreshListener;
import com.example.mypulltorefresh.R;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class PullableWebViewActivity extends Activity {

	private PullableViewLayout pullLayout;
	private WebView webView;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_webview);
		webView=(WebView)findViewById(R.id.content_view);
		webView.getSettings().setJavaScriptEnabled(true);//支持JavaScript脚本
		webView.setWebViewClient(new WebViewClient());//当需要跳转网页时，仍在当前webview中显示
		webView.loadUrl("http://www.baidu.com");
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
	}
}
