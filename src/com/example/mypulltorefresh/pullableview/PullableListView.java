package com.example.mypulltorefresh.pullableview;

import com.example.mypulltorefresh.interfaces.Pullable;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class PullableListView extends ListView implements Pullable{
	public PullableListView(Context context)
	{
		super(context);
	}

	public PullableListView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public PullableListView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}
	public boolean canPullDown(){
		if(getCount()==0){
			//没有item的时候也可以刷新
			return true;
		}else if(getFirstVisiblePosition()==0&&getChildAt(0).getTop()==0){
			//第一个可见的item的position是0，而且与parent的top是0，就说明划倒顶部了
			return true;
		}else 
			return false;
		
	}
	 public boolean canPullUp(){
		 if(getCount()==0){
			 // 没有item的时候也可以刷新
			 return true;
		 }else if(getLastVisiblePosition()==getCount()-1){
			 //滑到最后一项了,再判断是否划到底了
			 if(getChildAt(getLastVisiblePosition()-getFirstVisiblePosition())!=null&&
					 getChildAt(getLastVisiblePosition()-getFirstVisiblePosition()).getBottom()<=getMeasuredHeight())
				 return true;
		 }
		 return false;
	 }
}
