package com.example.mypulltorefresh.pullableview;



import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;



import com.example.mypulltorefresh.interfaces.Pullable;

public class PullableWebView extends WebView implements Pullable{

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
		if(getScrollY()==0)
			return true;
		else
			return false;
	}

	/**
	 * getScrollY().顶部划出去的距离
	 * getHeight().当前WebView容器的高度，与屏幕有关
	 * getContentHeight().返回的是整个html的高度，但并不等同于整个页面的高度，因为WebView具有缩放功能，
	 * 所以当前的页面的高度应该是原始html的高度再乘上缩放比例
	 */
	@SuppressWarnings("deprecation")
	@Override
	public boolean canPullUp() {
		if(getHeight()+getScrollY()>=getContentHeight()*getScale())
			return true;
		else
			return false;
	}

}
