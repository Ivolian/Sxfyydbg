package unicorn.withub.sxfyydbg.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import javax.security.auth.login.LoginException;

import unicorn.withub.sxfyydbg.R;
import unicorn.withub.sxfyydbg.application.BaseApplication;
import unicorn.withub.sxfyydbg.application.BaseSetting;
import unicorn.withub.sxfyydbg.data.BaseConstant;
import unicorn.withub.sxfyydbg.data.NormalResponseHandler;
import unicorn.withub.sxfyydbg.data.RequestBuild;
import unicorn.withub.sxfyydbg.dialog.EditDialog;
import unicorn.withub.sxfyydbg.exception.ErrorHandler;
import unicorn.withub.sxfyydbg.exception.NotFoundNetConnectionException;
import unicorn.withub.sxfyydbg.exception.ValidException;
import unicorn.withub.sxfyydbg.util.DataHelper;

public class BaseActivity extends Activity implements OnClickListener{
	public static final boolean IS_DEBUG = true;
	public DataHelper dataHelper;
	public static ProgressDialog mProgress;
	private TextView tv_title;
	protected long currentPage=1;
    protected long pageSize=10;
    protected long mCountPage = 1;
    protected int is_next=0;
    protected long rowCount;
    protected EditDialog dialog;
    protected TextView tv_back;
	protected TextView tv_sub;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		dialog=new EditDialog(this, 0);
	}
	protected void initView(String title){
		tv_title=(TextView) findViewById(R.id.titleName);
		tv_sub=(TextView) findViewById(R.id.titleSub);
		tv_back=(TextView) findViewById(R.id.titleBack);
		tv_back.setOnClickListener(this);
		tv_sub.setOnClickListener(this);
		tv_title.setText(title);
	}
	public BaseApplication getBaseApplication() {
		return (BaseApplication) getApplication();
	}
	
	public void canelRequest(){
		
	}
	
	public void canelRequestForBack(){
		
	}
	
	// 业务处理
	public class BusiHandler extends Handler {
			private BaseActivity activity;

			public BusiHandler() {
				super(getMainLooper());
				activity = BaseActivity.this;
			}

			public BusiHandler(BaseActivity c) {
				super(c.getMainLooper());
				activity = c;
			}

			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);

				if (!dataHelper.isCancel()) {
					activity.handleResponse(msg);
				}
			}
	}
	
	public void sendRequest(Bundle param, RequestBuild requestBuild, String progessMsg) {
		try {
			Handler handler = new BusiHandler();
			if (mProgress != null && mProgress.isShowing())
				return;
			param.putInt(BaseConstant.key_requestTime, BaseSetting.getInstance(this)
					.createRequestTime());
			dataHelper.sendRequest(param, handler, requestBuild,
					new NormalResponseHandler(), this);
			 mProgress = dataHelper.showProgressDialogWithoutCancelButton(this,
					 	progessMsg);
		} catch(NotFoundNetConnectionException e) {
			alert("操作提示", "连接网络失败，请检查网络设置");
		}catch (Exception e) {
				if (mProgress != null
						&& (mProgress.isShowing() || !mProgress.isIndeterminate())) {
					mProgress.cancel();
					// mProgress.dismiss();
				}
				error(e, this);
				
				Bundle bun = new Bundle();
				bun.putString("code", "-10000");
				bun.putString("description", "查询失败，请检查网络设置！");
				
				busiFinish(bun);
		}
	}
	
	public void handleResponse(Message msg) {
		try {
			if (msg != null) {
				if (msg.arg2 == 0
						|| msg.arg2 == BaseSetting.getInstance(this)
								.getRequestTime()) {
					Object obj = msg.obj;
					if (obj != null) {
						if (obj instanceof Exception) {
							Exception e = (Exception) msg.obj;
							error(e, this, "handleResponse");
						} else if (obj instanceof Bundle == false) {
							busiFinish(obj);
						} else {
							handleMessage((Bundle) obj);
						}
					} else {
						Log.i("res_msg", "res_msg=="+msg);
						Bundle bundle = msg.getData();
						Log.i("res_bundle", "res_bundle=="+bundle);
						handleMessage(bundle);
					}
				}
			}
		} catch (Exception e) {
			error(e, this, "handleResponse-handleMessage");
		} finally {
			if (mProgress != null
					&& (mProgress.isShowing() || !mProgress.isIndeterminate())) {
				mProgress.cancel();
				// mProgress.dismiss();
			}
		}
	}

	public void handleMessage(Bundle bundle) {
		if (bundle != null && bundle.isEmpty() == false) {
			String code = bundle.getString("code");
			if (code != null && code.equals("0")) {
				// 增加签名验证
				if (bundle.getBoolean("flag") == false) {
					alert("", "服务器签名错误。");
					return;
				}
			}
		}
		busiFinish(bundle);
	}

	
	/************** 异常处理,异常无按钮事件 ******************/
	public void error(Exception e, Object o, String location) {

		if (e instanceof ValidException) {
			alert(null, e.getMessage());
		} else if (e instanceof LoginException) {
		} else if (e instanceof NotFoundNetConnectionException) {
			error(e.getMessage());
		} else {
			if (IS_DEBUG) {
				error(ErrorHandler.getMsg(e) + ":" + e.getMessage() + "\n" + o + "\n"
						+ location);
			} else {
				error(ErrorHandler.getMsg(e));
			}

		}
	}
	
	public void error(Exception e, Object o) {
		error(e, o, "");
	}

	public void error(final String message) {
		//
		DialogInterface.OnClickListener clickListener = new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dlg, int sumthin) {
				error();
			}
		};
		alert("错误", message, clickListener);

	}
	/**
	 * 错误提示点击确定后的事件
	 */
	public void error() {
	}
	
	public void alert(String title, final String message) {
		// 显示alert对话框
		alert(title, message,this);
	}
	
	public void alert(String title, final String message,Activity context){
		// 显示alert对话框
		if (title == null)
			title = "提示";
		final AlertDialog alertDialog = new AlertDialog.Builder(context)
						.setTitle(title).setMessage(message)
						.setNeutralButton("确定", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dlg, int sumthin) {

							}
						}).create();
		alertDialog.show();
	}

	
	public void alert(String title, final String message,
			DialogInterface.OnClickListener clickListener) {
		// 显示alert对话框
		if (title == null || title.length() == 0)
			title = "";
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setNeutralButton("确定 ", clickListener);
		builder.create().show();
	}

	public void alertWithCancelButton(String title, final String message,
			DialogInterface.OnClickListener clickListener) {
		// 显示alert对话框
		if (title == null || title.length() == 0)
			title = "";
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setNeutralButton("确定 ", clickListener);
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				// 取消按钮事件

			}
		});
		builder.create().show();
	}
	
	public void busiFinish(Object o) {
		// alert("", "请处理服务器响应");
	}

	public void busiFinish(Bundle o) {
		// alert("", "请处理服务器响应");
	}
	
	public String getCodeMsg(String code,String msg){
		return msg;
	}
	/**分页解析*/
    protected void parsePage(){
		
		if(rowCount > 0){
			
			Log.i("mCountPage="+mCountPage, "rowCount="+rowCount);
			if(is_next==0){
				if(currentPage>0){
					currentPage--;
				}else{
					currentPage=1;
				}
			}else if(is_next==1){
				if(currentPage < mCountPage){
					currentPage++;
				}else{
					currentPage=mCountPage; 
					
				}
			}
			if(mCountPage == 0){
				currentPage = 1;
			}
		}
		
	}
    @Override
    protected void onDestroy() {
    	// TODO Auto-generated method stub
    	super.onDestroy();
    	unregisterReceiver(dialog.receiver);
    }
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}
