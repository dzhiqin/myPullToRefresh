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
			//û��item��ʱ��Ҳ����ˢ��
			return true;
		}else if(getFirstVisiblePosition()==0&&getChildAt(0).getTop()==0){
			//��һ���ɼ���item��position��0��������parent��top��0����˵������������
			return true;
		}else 
			return false;
		
	}
	 public boolean canPullUp(){
		 if(getCount()==0){
			 // û��item��ʱ��Ҳ����ˢ��
			 return true;
		 }else if(getLastVisiblePosition()==getCount()-1){
			 //�������һ����,���ж��Ƿ񻮵�����
			 if(getChildAt(getLastVisiblePosition()-getFirstVisiblePosition())!=null&&
					 getChildAt(getLastVisiblePosition()-getFirstVisiblePosition()).getBottom()<=getMeasuredHeight())
				 return true;
		 }
		 return false;
	 }
}
