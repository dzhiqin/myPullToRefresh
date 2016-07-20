package com.example.mypulltorefresh;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

public class PullableViewLayout extends LinearLayout implements View.OnTouchListener{
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
	 * 实现了pullable接口的View
	 */
	private View pullableView;
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
	private TextView description;
	/**
	 * 下拉头的布局参数
	 */
	private MarginLayoutParams headerLayoutParams;
	/**
	 * 下拉刷新的回调接口
	 */
	private PullToRefreshListener mListener;
	/**
	 * 下拉刷新控件的构造函数
	 */

	public PullableViewLayout(Context context,AttributeSet attrs){
		super(context,attrs);
		/**
		 * 箭头翻转和刷新圆圈的动画
		 */
		rotateAnimation=(RotateAnimation)AnimationUtils.loadAnimation(context, R.anim.reverse_anim);
		refreshingAnimation=(RotateAnimation)AnimationUtils.loadAnimation(context, R.anim.refreshing_anim);
		/**
		 * 设置动画匀速运动
		 */
		LinearInterpolator lir=new LinearInterpolator();
		rotateAnimation.setInterpolator(lir);
		refreshingAnimation.setInterpolator(lir);
		touchSlop=ViewConfiguration.get(context).getScaledTouchSlop();
	}
	/**
	 * 进行一次初始化操作，将下拉头向上偏移，给pullableView注册touch事件
	 */
	@Override
	protected void onLayout(boolean changed,int l,int t,int r,int b){
		super.onLayout(changed, l, t, r, b);
		//第一次加载onLayout进入
		if(!loadOnce){
			header=getChildAt(0);
			pullableView=getChildAt(1);
			//设置为负数，是为了隐藏下拉头
			hideHeaderHeight=-header.getHeight();
			headerLayoutParams=(MarginLayoutParams)header.getLayoutParams();
			//可以看到topMargin也是负数，也就是说”控件到顶部的距离“这个值并不是取绝对值，这也可以理解，因为正负号可以代表方位
			headerLayoutParams.topMargin=hideHeaderHeight;
			progressBar=(ProgressBar)header.findViewById(R.id.progress_bar);
			arrow=(ImageView)header.findViewById(R.id.arrow_icon);
			description=(TextView)header.findViewById(R.id.description_tv);
			//为pullableView设置触摸监听事件
			pullableView.setOnTouchListener(this);
			loadOnce=true;
		}
	}
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch(event.getActionMasked()){
		case MotionEvent.ACTION_DOWN://触摸检测
			yDown=event.getY();
			break;
		case MotionEvent.ACTION_MOVE://移动检测
			float yMove=event.getY();
			int distance=(int)(yMove-yDown);
			//去除微小的抖动误差
			if(distance<touchSlop){
				return false;
			}
			if(currentStatus!=STATUS_REFRESHING){
				//已经在下拉状态
				if(headerLayoutParams.topMargin>0){
					//header在父布局的top大于0，说明已经完全拉下
					currentStatus=STATUS_RELEASE_TO_REFRESH;
				}else{
					currentStatus=STATUS_PULL_TO_REFRESH;
				}
				//实时移动header，移动距离是distance/2，实际移动的距离是手指下拉距离的一半，以实现用力下拉的效果
				headerLayoutParams.topMargin=distance/2+hideHeaderHeight;
				header.setLayoutParams(headerLayoutParams);
			}
			break;
		case MotionEvent.ACTION_UP://松手检测
			default://同时过滤了多点触控的事件
			if((currentStatus==STATUS_RELEASE_TO_REFRESH)){
				//松手时如果是释放立即刷新状态，就去调用正在刷新任务
				new RefreshingTask().excute();
			}else if(currentStatus==STATUS_PULL_TO_REFRESH){
				//松手时如果是下拉刷新状态，就调用隐藏下拉头的任务
				new HideHeaderTask().excute();
			}
			break;
		}
		//触摸时时刻记得更新下拉头中的信息
		if(currentStatus==STATUS_RELEASE_TO_REFRESH||currentStatus==STATUS_PULL_TO_REFRESH){
			updateHeaderView();
			// 当前正处于下拉或释放状态，通过返回true屏蔽掉ListView的滚动事件
		    //返回true，listView就不会网上滚动内容
		    return true;
		}
		return false;
	}
	/**
	 * 当所有的刷新逻辑完成后记得调用一下，否则会一直处于正在刷新状态
	 */
	public void refreshingFinish(){
		currentStatus=STATUS_REFRESH_FINISHED;
		new HideHeaderTask().excute();
	}
	/**
	 * 更新下拉头中的信息
	 */
	private void updateHeaderView(){
		if(lastStatus!=currentStatus){
			if(currentStatus==STATUS_PULL_TO_REFRESH){
				description.setText(getResources().getString(R.string.pull_to_refresh));//下拉可以刷新
				arrow.setVisibility(View.VISIBLE);
				progressBar.setVisibility(View.GONE);
			}else if(currentStatus==STATUS_RELEASE_TO_REFRESH){
				description.setText(getResources().getString(R.string.release_to_refresh));//释放刷新
				arrow.setVisibility(View.VISIBLE);
				progressBar.setVisibility(View.GONE);
				arrow.startAnimation(rotateAnimation);
			}else if(currentStatus==STATUS_REFRESHING){
				description.setText(getResources().getString(R.string.refreshing));//正在刷新
				arrow.setVisibility(View.GONE);
				progressBar.setVisibility(View.VISIBLE);
				arrow.clearAnimation();
			}
			//
		
		}
		
	}
	/**
	  * 给下拉刷新控件注册一个监听器。
	  *
	  * @param listener
	  *            监听器的实现。
	  * @param id
	  *            为了防止不同界面的下拉刷新在上次更新时间上互相有冲突， 请不同界面在注册下拉刷新监听器时一定要传入不同的id。
	  */
	 public void setOnRefreshListener(PullToRefreshListener listener, int id) {
	  mListener = listener;
	  mId = id;
	 }
	 public interface PullToRefreshListener {
		  /**
		   * 刷新时会去回调此方法，在方法内编写具体的刷新逻辑。注意此方法是在子线程中调用的， 你可以不必另开线程来进行耗时操作。
		   */
		  void onRefresh();
		 }
}
