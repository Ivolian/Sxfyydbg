package unicorn.withub.sxfyydbg.activity;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import unicorn.withub.sxfyydbg.R;
import unicorn.withub.sxfyydbg.data.LoginHelper;
import unicorn.withub.sxfyydbg.util.StringUtil;


public class ZwDetailActivity extends BaseActivity{
	private String str="正文内容";
	private WebView zw;
	private WebSettings mWebSettings;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zw_detail_activity);
		initView(str);
	}
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void initView(String title) {
		// TODO Auto-generated method stub
		super.initView(title);
		zw=(WebView) findViewById(R.id.wb_zw_content);
		//zw.setMovementMethod(ScrollingMovementMethod.getInstance());
		if(LoginHelper.getLoginInfo()==null){
			StringUtil.tagetToLogin(ZwDetailActivity.this);
			return;
		}
		mWebSettings = zw.getSettings(); 
        mWebSettings.setJavaScriptEnabled(true); 
        mWebSettings.setBuiltInZoomControls(true); 
        mWebSettings.setLightTouchEnabled(true); 
        mWebSettings.setSupportZoom(true); 
        zw.setHapticFeedbackEnabled(false); 
		Bundle bundle=getIntent().getExtras();
		//query(bundle.getString("unid"));
		zw.loadUrl("http://1.85.16.50:10000/requestChange/ZwViewServlet.do?unid="+bundle.getString("unid"));
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.titleSub:
			StringUtil.tagetToLogin(ZwDetailActivity.this);
			break;
		case R.id.titleBack:
            finish();
			break;
		}
	}
	

}
