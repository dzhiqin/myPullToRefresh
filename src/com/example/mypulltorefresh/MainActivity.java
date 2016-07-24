package com.example.mypulltorefresh;

import com.example.mypulltorefresh.activity.PullableGridViewActivity;
import com.example.mypulltorefresh.activity.PullableImageViewActivity;
import com.example.mypulltorefresh.activity.PullableListViewActivity;
import com.example.mypulltorefresh.activity.PullableScrollViewActivity;
import com.example.mypulltorefresh.activity.PullableWebViewActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

	private TextView resTextView;
	private TextView noteTextView;
	private ListView mListView;
	private ArrayAdapter<String > adapter;
	private String[] items={"可以下拉刷新的ListView","可以下拉刷新的ScrollView","可以下拉刷新的WebView","可以下拉刷新的GridView","可以下拉刷新的ImageView"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		resTextView=(TextView)findViewById(R.id.get_source);
		resTextView.setText("获取源码：https://github.com/dzhiqin/myPullToRefresh.git");
		noteTextView=(TextView)findViewById(R.id.get_note);
		noteTextView.setText("阅读笔记：http://note.youdao.com/yws/public/redirect/share?id=6ece5d238ed2e51811521c702f5b1a96&type=false");
		mListView=(ListView)findViewById(R.id.myListView);
		adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items);
		mListView.setAdapter(adapter);
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i=new Intent();
				switch(position){
				case 0:
					i.setClass(MainActivity.this, PullableListViewActivity.class);
					break;
				case 1:
					i.setClass(MainActivity.this, PullableScrollViewActivity.class);
					break;
				case 2:
					i.setClass(MainActivity.this, PullableWebViewActivity.class);
					break;
				case 3:
					i.setClass(MainActivity.this, PullableGridViewActivity.class);
					break;
				case 4:
					i.setClass(MainActivity.this, PullableImageViewActivity.class);
					break;
				default:
					break;
					
				}
				startActivity(i);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
