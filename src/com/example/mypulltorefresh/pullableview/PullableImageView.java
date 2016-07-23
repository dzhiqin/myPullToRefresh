package com.example.mypulltorefresh.pullableview;

import com.example.mypulltorefresh.interfaces.Pullable;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class PullableImageView extends ImageView implements Pullable{

	public PullableImageView(Context context){
		super(context);
	}
	public PullableImageView(Context context, AttributeSet attrs){
		super(context,attrs);
	}
	public PullableImageView(Context context,AttributeSet attrs,int defStyle){
		super(context, attrs, defStyle);
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
