package com.example.mypulltorefresh.pullableview;



import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;



import com.example.mypulltorefresh.interfaces.Pullable;

public class PullableWebView extends WebView implements Pullable{

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
		if(getScrollY()==0)
			return true;
		else
			return false;
	}

	/**
	 * getScrollY().��������ȥ�ľ���
	 * getHeight().��ǰWebView�����ĸ߶ȣ�����Ļ�й�
	 * getContentHeight().���ص�������html�ĸ߶ȣ���������ͬ������ҳ��ĸ߶ȣ���ΪWebView�������Ź��ܣ�
	 * ���Ե�ǰ��ҳ��ĸ߶�Ӧ����ԭʼhtml�ĸ߶��ٳ������ű���
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
