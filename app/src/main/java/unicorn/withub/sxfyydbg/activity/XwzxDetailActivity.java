package unicorn.withub.sxfyydbg.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import unicorn.withub.sxfyydbg.R;
import unicorn.withub.sxfyydbg.data.LoginHelper;
import unicorn.withub.sxfyydbg.util.StringUtil;

public class XwzxDetailActivity extends BaseActivity{
	protected static final String TAG = "HttpErrorRecieveDemo";
	private String title="新闻详情";
	private WebView  wv_content;
	private WebSettings mWebSettings;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xwzxdetailactivity);
		initView(title);
		
	}
	@SuppressLint({ "CutPasteId", "SetJavaScriptEnabled", "JavascriptInterface" })
	@Override
	protected void initView(String title) {
		// TODO Auto-generated method stub
		super.initView(title);
		wv_content=(WebView) findViewById(R.id.wv_xwzx_content);
		if(LoginHelper.getLoginInfo()==null){
			StringUtil.tagetToLogin(XwzxDetailActivity.this);
			return;
		}
		Bundle bundle=getIntent().getExtras();
		mWebSettings = wv_content.getSettings(); 
        mWebSettings.setJavaScriptEnabled(true); 
//        mWebSettings.setBuiltInZoomControls(true); 
//        mWebSettings.setLightTouchEnabled(true); 
//        mWebSettings.setSupportZoom(true); 
//        wv_content.setHapticFeedbackEnabled(false);
     // 然后,为WebView添加对应的接口
        wv_content.addJavascriptInterface(new Console(), "HTMLOUT");
        Log.i("=========", ""+bundle.getString("contentId"));
        wv_content.loadUrl("http://1.85.16.50:10000/requestChange/XwViewServlet.do?contentid="+bundle.getString("contentId"));
        wv_content.setWebChromeClient(new MyWebChromeClient());
	}
	// 首先,定一个类,叫什么名称都可以,但是里面的方法名必须与
	// Javascript的console中的方法名对应
	class Console{
		@JavascriptInterface
	    @SuppressWarnings("unused")
	    public void processHTML(String html)
	    {
	        // process the html as needed by the app
			Log.i("html", "html="+html);
	    }
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.titleSub:
			StringUtil.tagetToLogin(XwzxDetailActivity.this);
			break;
		case R.id.titleBack:
            finish();
			break;
		}
	}
	class MyWebChromeClient extends WebChromeClient{

		public MyWebChromeClient() {
			super();
		}
		@Override
		public void onConsoleMessage(String message, int lineNumber,
				String sourceID) {
			// TODO Auto-generated method stub
			super.onConsoleMessage(message, lineNumber, sourceID);
		}
		@Override
		public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
			// TODO Auto-generated method stub
			Log.i(TAG, "consoleMessage="+consoleMessage.message());
			return true;
			//return super.onConsoleMessage(consoleMessage);
		}
		
	}

}
