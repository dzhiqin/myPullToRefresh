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
	 * 下拉状态
	 */
	public static final int STATUS_PULL_TO_REFRESH=0;
	/**
	 * 释放立即刷新
	 */
	public static final int STATUS_RELEASE_TO_REFRESH=1;
	/**
	 * 正在刷新
	 */
	public static final int STATUS_REFRESHING=2;
	/**
	 * 刷新完成或未刷新
	 */
	public static final int STATUS_REFRESH_FINISHED=3;
	/**
	 * 下拉头回滚的速度
	 */
	public static final int SCROLL_SPEED=-20;
	/**
	 * 为了防止不同界面的下拉刷新在更新时间上相互冲突，使用id来做区分
	 */
	private int mId=-1;
	/**
	 * 下拉头的高度
	 */
	private int hideHeaderHeight;
	/**
	 * 当前处于什么状态，可选值有STATUS_PULL_TO_REFRESH,STATUS_RELEASE_TO_REFRESH,
	 * STATUS_REFRESHING,STATUS_REFRESH_FINISHED
	 */
	private int currentStatus=STATUS_REFRESH_FINISHED;
	/**
	 * 记录上一次的状态是什么，避免进行重复操作
	 */
	private int lastStatus=currentStatus;
	/**
	 * 手指按下时的屏幕纵坐标
	 */
	private float yDown;
	/**
	 * 被判定为滚动之前用户手指可以移动的最大值，消抖
	 */
	private int touchSlop;
	/**
	 * 是否已经加载过一次，用于在onLayout中进行初始化
	 */
	private boolean loadOnce;
	/**
	 * 当前是否允许下拉，只有ListView滚动到顶部的时候才允许下拉
	 */
	private boolean ableToPull;
	/**
	 * 下拉箭头的选择180°动画
	 */
	private RotateAnimation rotateAnimation;
	/**
	 * 绕圈旋转动画
	 */
	private RotateAnimation refreshingAnimation;
	/**
	 * 下拉头的view
	 */
	private View header;
	/**
	 * 刷新时显示的进度条
	 */
	private ProgressBar progressBar;
	/**
	 * 指示下拉和释放的箭头图标
	 */
	private ImageView arrow;
	/**
	 * 指示下拉和释放的文字描述
	 */
	private TextView updateAt;
	/**
	 * 下拉头的布局参数
	 */
	private MarginLayoutParams headerLayoutParems;
	/**
	 * 下拉刷新控件的构造函数
	 */

	public PullableView(Context context,AttributeSet attrs){
		super(context,attrs);
		
	}
}
