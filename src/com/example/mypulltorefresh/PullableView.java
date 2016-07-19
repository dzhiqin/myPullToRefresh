package com.example.mypulltorefresh;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.RotateAnimation;

public class PullableView extends LinearLayout implements View.OnTouchListener{
	/**
	 * ����״̬
	 */
	public static final int STATUS_PULL_TO_REFRESH=0;
	/**
	 * �ͷ�����ˢ��
	 */
	public static final int STATUS_RELEASE_TO_REFRESH=1;
	/**
	 * ����ˢ��
	 */
	public static final int STATUS_REFRESHING=2;
	/**
	 * ˢ����ɻ�δˢ��
	 */
	public static final int STATUS_REFRESH_FINISHED=3;
	/**
	 * ����ͷ�ع����ٶ�
	 */
	public static final int SCROLL_SPEED=-20;
	/**
	 * Ϊ�˷�ֹ��ͬ���������ˢ���ڸ���ʱ�����໥��ͻ��ʹ��id��������
	 */
	private int mId=-1;
	/**
	 * ����ͷ�ĸ߶�
	 */
	private int hideHeaderHeight;
	/**
	 * ��ǰ����ʲô״̬����ѡֵ��STATUS_PULL_TO_REFRESH,STATUS_RELEASE_TO_REFRESH,
	 * STATUS_REFRESHING,STATUS_REFRESH_FINISHED
	 */
	private int currentStatus=STATUS_REFRESH_FINISHED;
	/**
	 * ��¼��һ�ε�״̬��ʲô����������ظ�����
	 */
	private int lastStatus=currentStatus;
	/**
	 * ��ָ����ʱ����Ļ������
	 */
	private float yDown;
	/**
	 * ���ж�Ϊ����֮ǰ�û���ָ�����ƶ������ֵ������
	 */
	private int touchSlop;
	/**
	 * �Ƿ��Ѿ����ع�һ�Σ�������onLayout�н��г�ʼ��
	 */
	private boolean loadOnce;
	/**
	 * ��ǰ�Ƿ�����������ֻ��ListView������������ʱ�����������
	 */
	private boolean ableToPull;
	/**
	 * ������ͷ��ѡ��180�㶯��
	 */
	private RotateAnimation rotateAnimation;
	/**
	 * ��Ȧ��ת����
	 */
	private RotateAnimation refreshingAnimation;
	/**
	 * ����ͷ��view
	 */
	private View header;
	/**
	 * ˢ��ʱ��ʾ�Ľ�����
	 */
	private ProgressBar progressBar;
	/**
	 * ָʾ�������ͷŵļ�ͷͼ��
	 */
	private ImageView arrow;
	/**
	 * ָʾ�������ͷŵ���������
	 */
	private TextView updateAt;
	/**
	 * ����ͷ�Ĳ��ֲ���
	 */
	private MarginLayoutParams headerLayoutParems;
	/**
	 * ����ˢ�¿ؼ��Ĺ��캯��
	 */

	public PullableView(Context context,AttributeSet attrs){
		super(context,attrs);
		
	}
}
