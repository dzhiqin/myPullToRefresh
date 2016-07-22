package com.example.mypulltorefresh.pullableview;

import com.example.mypulltorefresh.interfaces.Pullable;

import android.content.Context;
import android.widget.ScrollView;

public class PullableScrollView extends ScrollView implements Pullable{

	public PullableScrollView(Context context) {
		super(context);
		// TODO �Զ����ɵĹ��캯�����
	}

	@Override
	public boolean canPullDown() {
		//���scroll��������ˣ�����������
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
