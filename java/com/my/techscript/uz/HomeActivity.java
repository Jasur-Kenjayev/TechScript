package com.my.techscript.uz;

import android.animation.*;
import android.app.*;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.*;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.net.Uri;
import android.os.*;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.*;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.*;
import org.json.*;

public class HomeActivity extends Activity {
	
	private Timer _timer = new Timer();
	
	private double n = 0;
	
	private LinearLayout linear2;
	private WebView webview4;
	private ProgressBar progressbar2;
	
	private TimerTask t;
	private RequestNetwork net;
	private RequestNetwork.RequestListener _net_request_listener;
	private Intent i = new Intent();
	private SharedPreferences sp;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.home);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		linear2 = findViewById(R.id.linear2);
		webview4 = findViewById(R.id.webview4);
		webview4.getSettings().setJavaScriptEnabled(true);
		webview4.getSettings().setSupportZoom(true);
		progressbar2 = findViewById(R.id.progressbar2);
		net = new RequestNetwork(this);
		sp = getSharedPreferences("sp", Activity.MODE_PRIVATE);
		
		webview4.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageStarted(WebView _param1, String _param2, Bitmap _param3) {
				final String _url = _param2;
				
				super.onPageStarted(_param1, _param2, _param3);
			}
			
			@Override
			public void onPageFinished(WebView _param1, String _param2) {
				final String _url = _param2;
				linear2.setVisibility(View.GONE);
				webview4.requestFocus();
				super.onPageFinished(_param1, _param2);
			}
		});
		
		_net_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				final String _tag = _param1;
				final String _response = _param2;
				final HashMap<String, Object> _responseHeaders = _param3;
				_internet();
			}
			
			@Override
			public void onErrorResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _message = _param2;
				i.setClass(getApplicationContext(), NoNetActivity.class);
				startActivity(i);
				sp.edit().putString("url", webview4.getUrl()).commit();
			}
		};
	}
	
	private void initializeLogic() {
		_internet();
		webview4.loadUrl("https://techscript.uz/posts/");
	}
	
	@Override
	public void onBackPressed() {
		if (webview4.canGoBack()) {
			webview4.goBack();
		}
		else {
			n++;
			if (n == 1) {
				SketchwareUtil.showMessage(getApplicationContext(), "To exit app click back again!");
				t = new TimerTask() {
					@Override
					public void run() {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								n = 0;
							}
						});
					}
				};
				_timer.schedule(t, (int)(2000));
			}
			else {
				finishAffinity();
			}
		}
	}
	public void _internet() {
		net.startRequestNetwork(RequestNetworkController.GET, "https://techscript.uz/posts/", "A", _net_request_listener);
	}
	
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	@Deprecated
	public int getLocationX(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}
	
	@Deprecated
	public int getLocationY(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[1];
	}
	
	@Deprecated
	public int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}
	
	@Deprecated
	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
			_result.add((double)_arr.keyAt(_iIdx));
		}
		return _result;
	}
	
	@Deprecated
	public float getDip(int _input) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels() {
		return getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels() {
		return getResources().getDisplayMetrics().heightPixels;
	}
}