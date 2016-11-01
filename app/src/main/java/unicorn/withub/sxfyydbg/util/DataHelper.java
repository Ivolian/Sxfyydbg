package unicorn.withub.sxfyydbg.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;

import unicorn.withub.sxfyydbg.activity.BaseActivity;
import unicorn.withub.sxfyydbg.data.RequestBuild;
import unicorn.withub.sxfyydbg.data.ResponseHandler;
import unicorn.withub.sxfyydbg.http.HttpClients;


public class DataHelper {
	private static DataHelper myself = null;
	private DialogInterface.OnClickListener cancelBtnListener;
	private DialogInterface.OnCancelListener cancelListener;
	private DialogInterface.OnKeyListener keyListener;
	public boolean isCancel = false;
	private BaseActivity context;
	private Context con;

	public DataHelper() {
		cancelListener = new DialogInterface.OnCancelListener() {

			public void onCancel(DialogInterface arg0) {
				// TODO Auto-generated method stub
				isCancel = true;
			}

		};
		cancelBtnListener = new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				isCancel = true;
				context.canelRequest();
			}

		};
		keyListener = new DialogInterface.OnKeyListener() {

			public boolean onKey(DialogInterface arg0, int keyCode,
					KeyEvent arg2) {
				// TODO Auto-generated method stub
				if ((keyCode == KeyEvent.KEYCODE_BACK)) {
					context.canelRequestForBack();
				}
				return true;
			}

		};
	}

	public static DataHelper getInstance() {
		if (myself == null)
			myself = new DataHelper();
		return myself;
	}

	public ProgressDialog showProgressDialogWithCancelButton(
			Context paramContext, CharSequence paramCharSequence1) {
		return showProgressDialogWithCancelButton(paramContext, null,
				paramCharSequence1, false, cancelListener, cancelBtnListener);
	}

	public ProgressDialog showProgressDialogWithCancelButton(
			Context paramContext, CharSequence paramCharSequence1,
			CharSequence paramCharSequence2, boolean paramBoolean1,
			DialogInterface.OnCancelListener paramOnCancelListener,
			DialogInterface.OnClickListener paramOnClickListener) {
		ProgressDialog localProgressDialog = new ProgressDialog(paramContext);
		localProgressDialog.setTitle(paramCharSequence1);
		localProgressDialog.setMessage(paramCharSequence2);
		localProgressDialog.setIndeterminate(paramBoolean1);
		localProgressDialog.setCancelable(true);
		localProgressDialog.setOnCancelListener(paramOnCancelListener);
		localProgressDialog.setOnKeyListener(keyListener);
		localProgressDialog.setButton(-1, "取  消", paramOnClickListener);
		localProgressDialog.show();
		isCancel = false;
		return localProgressDialog;
	}

	public ProgressDialog showProgressDialogWithoutCancelButton(
			Context paramContext, CharSequence paramCharSequence1,
			CharSequence paramCharSequence2, boolean paramBoolean1

	) {
		ProgressDialog localProgressDialog = new ProgressDialog(paramContext);
		localProgressDialog.setTitle(paramCharSequence1);
		localProgressDialog.setMessage(paramCharSequence2);
		localProgressDialog.setIndeterminate(paramBoolean1);
		localProgressDialog.setCancelable(false);
		localProgressDialog.show();
		isCancel = false;
		return localProgressDialog;
	}

	public ProgressDialog showProgressDialogWithoutCancelButton(
			Context paramContext, CharSequence paramCharSequence2) {
		return showProgressDialogWithoutCancelButton(paramContext, null,
				paramCharSequence2, false);
	}

	/**
	 * <p>
	 * 	（requestlBuild1）解析请求参数（param)，并发送远程请求，并解析请求结果，封装为{@link Message}。
	 * 并由hander1({@link Handler}处理。
	 * </p>
	 * @param param  请求参数
	 * @param handler1  业务处理
	 * @param requestlBuild1  构建请求参数
	 * @param responseHandler1  业务应答处理
	 * @param activity  //上下文
	 */
	public void sendRequest(Bundle param, Handler handler1,
							RequestBuild requestlBuild1, ResponseHandler responseHandler1,
							BaseActivity activity) {
		SystemValidUtil.validConnect(activity.getBaseApplication());
		isCancel = false;
		this.context = activity;
		new BusiRequest(param, handler1, requestlBuild1, responseHandler1)
				.start();
	}
	
	

	class BusiRequest extends Thread {
		private Bundle param;

		private Handler paramHandler;
		private RequestBuild requestlBuild;
		private ResponseHandler responseHandler;

		public BusiRequest(Bundle param, Handler paramHandler,
				RequestBuild requestlBuild, ResponseHandler responseHandler) {
			this.param = param;
			this.paramHandler = paramHandler;
			this.requestlBuild = requestlBuild;
			this.responseHandler = responseHandler;
		}

		public void run() {
			Message msg = new Message();
			try {
				HttpClients httpClients = new HttpClients(context);
				Log.i("requestlBuild", "requestlBuild==="+requestlBuild);
				Log.i("param", "param===="+param.toString());
				String xml = requestlBuild.buildXml(param, context);
				if (BaseActivity.IS_DEBUG) {
					Log.d("BusiRequestUtil.request", "--->request xml\n" + xml);
				}
				String ret =httpClients.postXml(requestlBuild.getServerUrl(),
						xml); 
				if (BaseActivity.IS_DEBUG) {
					Log.d("BusiRequestUtil.query", "-->response xml\n" + ret);
				}
				Bundle queryResult = responseHandler.parseXml(ret, context);
				Log.i("===", "queryResult=="+queryResult);
				msg.arg1 = 0;
				msg.arg2 = param.getInt("requestTime");
				msg.setData(queryResult);
				paramHandler.sendMessage(msg);
			} catch (Exception e) {
				// TODO: handle exception
				Log.i("===", "++++");
				msg.arg1 = 1;
				msg.obj = e;
				msg.arg2 = param.getInt("requestTime");
				paramHandler.sendMessage(msg);
			}
		}
	}


	public DialogInterface.OnClickListener getCancelBtnListener() {
		return cancelBtnListener;
	}

	public void setCancelBtnListener(
			DialogInterface.OnClickListener cancelBtnListener) {
		this.cancelBtnListener = cancelBtnListener;
	}

	public DialogInterface.OnCancelListener getCancelListener() {
		return cancelListener;
	}

	public void setCancelListener(
			DialogInterface.OnCancelListener cancelListener) {
		this.cancelListener = cancelListener;
	}

	public boolean isCancel() {
		return isCancel;
	}

}
