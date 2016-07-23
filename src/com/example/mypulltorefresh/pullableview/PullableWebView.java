package com.example.mypulltorefresh.pullableview;



import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import com.example.mypulltorefresh.interfaces.Pullable;

public class PullableWebView extends ListView implements Pullable{

	public PullableWebView(Context context) {
		super(context);
		// TODO 自动生成的构造函数存根
	}
	public PullableWebView(Context context,AttributeSet attrs){
		super(context,attrs);
	}
	public PullableWebView(Context context,AttributeSet attrs,int defStyle){
		super(context,attrs,defStyle);
	}
	@Override
	public boolean canPullDown() {
		// TODO 自动生成的方法存根
		return true;
	}

	@Override
	public boolean canPullUp() {
		// TODO 自动生成的方法存根
		return true;
	}

}
