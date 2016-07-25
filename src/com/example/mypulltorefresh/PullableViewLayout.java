package com.example.mypulltorefresh;

import com.example.mypulltorefresh.utils.LogUtil;
import com.example.mypulltorefresh.interfaces.Pullable;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.content.Context;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.RotateAnimation;

public class PullableViewLayout extends LinearLayout implements View.OnTouchListener{
	/**
	 * ��������ˢ��
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
	 * ���˶�㴥��
	 */
	private int mEvent=-1;
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
	 * ������ͷת��180�㶯��
	 */
	//��Ϊ��java�����ʾ
	//private RotateAnimation rotateAnimation;
	/**
	 * ��Ȧ��ת����
	 */
	//��Ϊ��progressBar������Ҫ�ö�������ʾ
	//private RotateAnimation refreshingAnimation;
	/**
	 * ����ͷ��view
	 */
	private View header;
	/**
	 * ʵ����pullable�ӿڵ�View
	 */
	private View pullableView;
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
	private TextView description;
	/**
	 * ����ͷ�Ĳ��ֲ���
	 */
	private MarginLayoutParams headerLayoutParams;
	/**
	 * ����ˢ�µĻص��ӿ�
	 */
	private PullToRefreshListener mListener;
	/**
	 * ����ˢ�¿ؼ��Ĺ��캯��
	 */

	public PullableViewLayout(Context context){
		super(context);
		touchSlop=ViewConfiguration.get(context).getScaledTouchSlop();
	}
	public PullableViewLayout(Context context,AttributeSet attrs){
		super(context,attrs);
		/**
		 * ��ͷ��ת��ˢ��ԲȦ�Ķ���
		 */
		//rotateAnimation=(RotateAnimation)AnimationUtils.loadAnimation(context, R.anim.reverse_anim);
		//refreshingAnimation=(RotateAnimation)AnimationUtils.loadAnimation(context, R.anim.refreshing_anim);
		/**
		 * ���ö��������˶�
		 */
		//LinearInterpolator lir=new LinearInterpolator();
		//rotateAnimation.setInterpolator(lir);
		//refreshingAnimation.setInterpolator(lir);
		touchSlop=ViewConfiguration.get(context).getScaledTouchSlop();		
	}
	public PullableViewLayout(Context context ,AttributeSet attrs,int defStyle){
		super(context,attrs,defStyle);
		touchSlop=ViewConfiguration.get(context).getScaledTouchSlop();	
	}
	/**
	 * ����һ�γ�ʼ��������������ͷ����ƫ�ƣ���pullableViewע��touch�¼�
	 */
	@Override
	protected void onLayout(boolean changed,int l,int t,int r,int b){
		super.onLayout(changed, l, t, r, b);
		//��һ�μ���onLayout����
		if(!loadOnce){
			header=getChildAt(0);
			LogUtil.v("test", "header="+header);
			pullableView=getChildAt(1);
			LogUtil.v("test", "pullableView="+pullableView);
			//����Ϊ��������Ϊ����������ͷ
			hideHeaderHeight=-header.getHeight();
			headerLayoutParams=(MarginLayoutParams)header.getLayoutParams();
			//���Կ���topMarginҲ�Ǹ�����Ҳ����˵���ؼ��������ľ��롰���ֵ������ȡ����ֵ����Ҳ������⣬��Ϊ�����ſ��Դ���λ
			headerLayoutParams.topMargin=hideHeaderHeight;
			progressBar=(ProgressBar)header.findViewById(R.id.progress_bar);
			arrow=(ImageView)header.findViewById(R.id.arrow_icon);
			description=(TextView)header.findViewById(R.id.description_tv);
			//ΪpullableView���ô��������¼�
			pullableView.setOnTouchListener(this);
			loadOnce=true;
		}
	}
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch(event.getActionMasked()){
		case MotionEvent.ACTION_DOWN://�������
			LogUtil.v("test", "ACTION_DOWN");
			yDown=event.getRawY();
			mEvent=0;
			break;
		case MotionEvent.ACTION_POINTER_DOWN:
		case MotionEvent.ACTION_POINTER_UP:
			//LogUtil.v("test", "ACTION_POINTER_DOWN OR UP");
			//���˶�㴥��
			mEvent=-1;
			break;
		case MotionEvent.ACTION_MOVE://�ƶ����
			LogUtil.v("test", "ACTION_MOVE");
			
			if(mEvent==0&&((Pullable)pullableView).canPullDown())
			{
				float yMove=event.getRawY();
				int distance=(int)(yMove-yDown);
				//ȥ��΢С�Ķ������
				if(distance<touchSlop){					
					return false;
				}
				if(currentStatus!=STATUS_REFRESHING){
					//�Ѿ�������״̬
					if(headerLayoutParams.topMargin>0){
						//header�ڸ����ֵ�top����0��˵���Ѿ���ȫ����
						currentStatus=STATUS_RELEASE_TO_REFRESH;
					}else{
						currentStatus=STATUS_PULL_TO_REFRESH;
					}
					//ʵʱ�ƶ�header���ƶ�������distance/2��ʵ���ƶ��ľ�������ָ���������һ�룬��ʵ������������Ч��
					headerLayoutParams.topMargin=distance/2+hideHeaderHeight;
					header.setLayoutParams(headerLayoutParams);
				}
			}
			
			break;
		case MotionEvent.ACTION_UP://���ּ��
			default:
			if((currentStatus==STATUS_RELEASE_TO_REFRESH)){
				//����ʱ������ͷ�����ˢ��״̬����ȥ��������ˢ������
				new RefreshingTask().execute();
			}else if(currentStatus==STATUS_PULL_TO_REFRESH){
				//����ʱ���������ˢ��״̬���͵�����������ͷ������
				new HideHeaderTask().execute();
			}
			break;
		}
		//����ʱʱ�̼ǵø�������ͷ�е���Ϣ
		
		if(currentStatus==STATUS_RELEASE_TO_REFRESH||currentStatus==STATUS_PULL_TO_REFRESH){
			updateHeaderView();
			//��¼�ϴε�״̬
			lastStatus=currentStatus;
			// ��ǰ�������������ͷ�״̬��ͨ������true���ε�ListView�Ĺ����¼�
		    //����true��listView�Ͳ������Ϲ�������
		    return true;
		}
		return false;
	}
	/**
	 * �����е�ˢ���߼���ɺ�ǵõ���һ�£������һֱ��������ˢ��״̬
	 */
	public void refreshingFinish(){
		currentStatus=STATUS_REFRESH_FINISHED;
		new HideHeaderTask().execute();
	}
	/**
	 * ��������ͷ�е���Ϣ
	 */
	private void updateHeaderView(){
		//���״̬û�иı� lastStatus=currentStatus���Ͳ�����view�������ͷһֱ����ת
		if(lastStatus!=currentStatus){
			if(currentStatus==STATUS_PULL_TO_REFRESH){
				description.setText(getResources().getString(R.string.pull_to_refresh));//��������ˢ��
				arrow.setVisibility(View.VISIBLE);
				progressBar.setVisibility(View.GONE);
				rotateArrow();
			}else if(currentStatus==STATUS_RELEASE_TO_REFRESH){
				description.setText(getResources().getString(R.string.release_to_refresh));//�ͷ�ˢ��
				arrow.setVisibility(View.VISIBLE);
				progressBar.setVisibility(View.GONE);
				rotateArrow();
			}else if(currentStatus==STATUS_REFRESHING){
				description.setText(getResources().getString(R.string.refreshing));//����ˢ��
				arrow.setVisibility(View.GONE);
				progressBar.setVisibility(View.VISIBLE);
				arrow.clearAnimation();
			}
			//
		
		}
		
	}
	/**
	 * ���ݵ�ǰ״̬��ת��ͷ
	 */
	private void rotateArrow(){
		LogUtil.v("test", "currentStatus="+currentStatus);
		float pivotX=arrow.getWidth()/2f;
		float pivotY=arrow.getHeight()/2;
		float fromDegrees=0f;
		float toDegrees=0f;
		if(currentStatus==STATUS_PULL_TO_REFRESH){
			fromDegrees=0f;
			toDegrees=180f;
		}else if(currentStatus==STATUS_RELEASE_TO_REFRESH){
			fromDegrees=180f;
			toDegrees=360f;
		}
		RotateAnimation animation=new RotateAnimation(fromDegrees,toDegrees,pivotX,pivotY);
		animation.setDuration(100);
		animation.setFillAfter(true);
		animation.setRepeatCount(0);
		arrow.startAnimation(animation);
	}
	/**
	 * ����ˢ�µ������ڴ������л�ȥ�ص�ע�����������ˢ�¼�����
	 */
	class RefreshingTask extends AsyncTask<Void,Integer,Void>{

		@Override
		protected Void doInBackground(Void... params) {
			int topMargin=headerLayoutParams.topMargin;
			/**
			 * topMargin������SCROLL_SPEED,ÿ�μ�ȥ���ӳ�10ms��ֱ��<=0,���м����õ���ʱ���������ͷ�ع���ʱ��
			 * ��ν����ͷ�ع����ǣ�����ͷ�ӱ�������λ���ϻ���topMargin=0������ͷ������ʾ�ڶ�������λ���������
			 */
			while(true){
				topMargin=topMargin+SCROLL_SPEED;
				if(topMargin<=0){
					topMargin=0;
					break;
				}
			
				/**
			     * publishProgress���ɾ���˾Ͳ����лع���Ч������������ͷ������ʲôλ�ã����ֺ�ֱ�ӳ����ڶ���topMargin=0 ��λ��
			     * ÿ�ε�����publishProgress()֮�󣬾ͻ����onProgressUpDate()�������˺�����UI�߳������У��𵽸��½����Ч��
			     * @author Dawn 20160716
			     */
				publishProgress(topMargin);
				sleep(10);
			}
			currentStatus=STATUS_REFRESHING;
			publishProgress(0);
			/**
			 * ִ����header�ع��ö�֮����ִ�У�
			 * �ⲿΪPullableListViewActivity��ע����setOnRefreshListener��������mListtener=listener
			 * ����mListener���������ص�onRefresh��
			 */
			if(mListener!=null){
				mListener.onRefresh();
			}
			return null;
		}
		@Override
		protected void onProgressUpdate(Integer...topMargin){
			/**
			    * onProgressUpdate�Ĳ�����Integer...topMargin��
			    * ������⣺����һ����ȷ��������Integer[]��ÿ�ν���һ������ռ�õ���topMargin[0]���λ��
			    * @author-Dawn 20160716
			    */
			updateHeaderView();
			headerLayoutParams.topMargin=topMargin[0];
			header.setLayoutParams(headerLayoutParams);
		}
	}
	/**
	  * ��������ͷ�����񣬵�δ��������ˢ�»�����ˢ����ɺ󣬴����񽫻�ʹ����ͷ�������ء�
	  *���Refreshing Task����onPostExecute���˺�����doInBackground��return��ȡ����
	  *onPostExecute��Ҫ���ڸı䵱ǰ��״̬currentStatus=STATUS_REFRESH_FINISHED
	  * @author Dawn
	  */
	class HideHeaderTask extends AsyncTask<Void,Integer,Integer>{
	
		@Override
		protected Integer doInBackground(Void... params) {
			int topMargin=headerLayoutParams.topMargin;
			while(true){
				topMargin=topMargin+SCROLL_SPEED;//���������topMargin��ֵԽ��ԽС��headerͷ��λ�������ƶ�
				if(topMargin<=hideHeaderHeight){
					topMargin=hideHeaderHeight;//��topMargin�ﵽhideHeaderHeight�ĸ߶�ʱ��ֹͣ�ƶ�
					break;
				}
				publishProgress(topMargin);
				sleep(10);
			}
			return topMargin;
		}
		@Override
		//�˺����ڵ���publishProgress������ִ��
		protected void onProgressUpdate(Integer...topMargin){
			updateHeaderView();
			headerLayoutParams.topMargin=topMargin[0];
			header.setLayoutParams(headerLayoutParams);
		}
		@Override
		//�˺�����ִ����doInBackground������ִ�У�topMargin�Ǵ�doInBackground����
		protected void onPostExecute(Integer topMargin){
			headerLayoutParams.topMargin=topMargin;
			header.setLayoutParams(headerLayoutParams);
			currentStatus=STATUS_REFRESH_FINISHED;
		}
		
	}
	/**
	  * ʹ��ǰ�߳�˯��ָ���ĺ�������
	  *
	  * @param time
	  *            ָ����ǰ�߳�˯�߶�ã��Ժ���Ϊ��λ
	  */
	 private void sleep(int time) {
	  try {
	   Thread.sleep(time);
	  } catch (InterruptedException e) {
	   e.printStackTrace();
	  }
	 }
	/**
	  * ������ˢ�¿ؼ�ע��һ����������
	  *
	  * @param listener
	  *            ��������ʵ�֡�
	  * @param id
	  *            Ϊ�˷�ֹ��ͬ���������ˢ�����ϴθ���ʱ���ϻ����г�ͻ�� �벻ͬ������ע������ˢ�¼�����ʱһ��Ҫ���벻ͬ��id��
	  */
	 public void setOnRefreshListener(PullToRefreshListener listener, int id) {
	  mListener = listener;
	  mId = id;
	 }
	 public interface PullToRefreshListener {
		  /**
		   * ˢ��ʱ��ȥ�ص��˷������ڷ����ڱ�д�����ˢ���߼���ע��˷����������߳��е��õģ� ����Բ������߳������к�ʱ������
		   */
		  void onRefresh();
		 }
}
