package com.example.mypulltorefresh.pullableview;



import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import com.example.mypulltorefresh.interfaces.Pullable;

public class PullableWebView extends ListView implements Pullable{

	public PullableWebView(Context context) {
		super(context);
		// TODO �Զ����ɵĹ��캯�����
	}
	public PullableWebView(Context context,AttributeSet attrs){
		super(context,attrs);
	}
	public PullableWebView(Context context,AttributeSet attrs,int defStyle){
		super(context,attrs,defStyle);
	}
	@Override
	public boolean canPullDown() {
		// TODO �Զ����ɵķ������
		return true;
	}

	@Override
	public boolean canPullUp() {
		// TODO �Զ����ɵķ������
		return true;
	}

}
