package com.example.mypulltorefresh.pullableview;

import com.example.mypulltorefresh.interfaces.Pullable;

import android.content.Context;
import android.widget.ScrollView;

public class PullableScrollView extends ScrollView implements Pullable{

	public PullableScrollView(Context context) {
		super(context);
		// TODO 自动生成的构造函数存根
	}

	@Override
	public boolean canPullDown() {
		//如果scroll划到最顶部了，就允许下拉
		if(getScrollY()==0)
			return true;
		else 
			return false;
		
	}

	@Override
	public boolean canPullUp() {
		
		if(getChildAt(0).getMeasuredHeight()<=getScrollY()+getHeight()){
			return true;
		}
		else
		return false;
	}

	
}
