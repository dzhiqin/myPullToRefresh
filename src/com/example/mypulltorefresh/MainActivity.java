package com.example.mypulltorefresh;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

	private TextView textView;
	private ListView mListView;
	private ArrayAdapter<String > adapter;
	private String[] items={"��������ˢ�µ�ListView","��������ˢ�µ�ScrollView","��������ˢ�µ�WebView","��������ˢ�µ�GridView","��������ˢ�µ�ImageView"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		textView=(TextView)findViewById(R.id.get_source);
		mListView=(ListView)findViewById(R.id.myListView);
		adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items);
		mListView.setAdapter(adapter);
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
