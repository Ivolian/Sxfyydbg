package unicorn.withub.sxfyydbg.broadcastreceiver;


import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlSerializer;

import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Iterator;

import unicorn.withub.sxfyydbg.activity.BaseActivity;
import unicorn.withub.sxfyydbg.application.BaseApplication;
import unicorn.withub.sxfyydbg.data.BaseConstant;
import unicorn.withub.sxfyydbg.data.LoginHelper;
import unicorn.withub.sxfyydbg.data.LoginInfo;
import unicorn.withub.sxfyydbg.data.NormalResponseHandler;
import unicorn.withub.sxfyydbg.data.RequestBuild;
import unicorn.withub.sxfyydbg.http.HttpClients;
import unicorn.withub.sxfyydbg.util.DateUtil;
import unicorn.withub.sxfyydbg.util.RandomGeneter;

public class TimeReceiver extends BroadcastReceiver{
	public static final String PREFS_NAME = "prefsname";
	public static final String username = "username";
	public static final String loginName = "loginName";
	public static final String loginBusiType = "loginBusiType";
	public static final String lastDate = "lastDate";
	public static final String userId = "userId";
	public static final String fydm = "fydm";
	public static final String ticket = "ticket";
	public static final String deptId = "deptId";
	public static final String deptName = "deptName";
	public static final String userTel = "userTel";
	private static final String DEFAULT_USERNAME = ""; //默认用户名
	//偏好设置，用于保存用户名
    private SharedPreferences setting;
    public static LoginInfo loginInfo;
    public static int count=1;
    private NormalRequestBuild requestBuild;
    private NormalResponseHandler responseHandler;
    private Bundle param;
    private Context context;
    private Bundle resBun;
    @SuppressLint("HandlerLeak")
	private Handler handler=new Handler(){
    	public void handleMessage(Message msg) {
    		if(resBun!=null){
    			Log.i("resBun", "resBun==="+resBun);
    		}
    	};
    };
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		this.context=context;
		setting=context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		setLoginInfo();
		if("time".equals(intent.getAction())){
			Log.i("time", "time++");
		    //BadgeUtil.setBadgeCount(context, count++);
			request();
		}
	}
	
	private void request() {
		requestBuild = new NormalRequestBuild();
		responseHandler = new NormalResponseHandler();
		param = new Bundle();
		param.putString(BaseConstant.REQUEST_BUSI_CODE, "countNumDao");
		param.putString("RequestlBuild", "NormalRequestBuild");
		Log.i("loginData", "loginData="+loginInfo);
		param.putString("loginInfo", loginInfo+"");
		new CountThread().start();
	}
	
	class CountThread extends Thread{
		
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			Message msg = new Message();
			try {
				HttpClients httpClients = new HttpClients(context);
				String xml = requestBuild.buildXml(param, context);
				if (BaseActivity.IS_DEBUG) {
					Log.d("BusiRequestUtil.request", "--->request xml\n" + xml);
				}
				String ret =httpClients.postXml(requestBuild.getServerUrl(),
						xml); 
				if (BaseActivity.IS_DEBUG) {
					Log.d("BusiRequestUtil.query", "-->response xml\n" + ret);
				}
				resBun = responseHandler.parseXml(ret, context);
				msg.what = 0;
				msg.arg2 = param.getInt("requestTime");
				msg.setData(resBun);
				handler.sendMessage(msg);
			} catch (Exception e) {
				// TODO: handle exception
				resBun=null;
				msg.what = 1;
				msg.obj = e;
				msg.arg2 = param.getInt("requestTime");
				handler.sendMessage(msg);
			}
		}
	}
	
	private void setLoginInfo(){
		if(LoginHelper.getLoginInfo()!=null){
			saveLoginName(LoginHelper.getLoginInfo().getLoginName());
			saveLoginBusiType(LoginHelper.getLoginInfo().getLoginBusiType());
			saveUserName(LoginHelper.getLoginInfo().getUserName());
			saveLastDate(LoginHelper.getLoginInfo().getLastDate());
			saveUserId(LoginHelper.getLoginInfo().getUserId());
			saveFydm(LoginHelper.getLoginInfo().getFydm());
			saveTicket(LoginHelper.getLoginInfo().getTicket());
			saveDeptId(LoginHelper.getLoginInfo().getDeptId());
			saveDeptName(LoginHelper.getLoginInfo().getDeptName());
			saveUserTel(LoginHelper.getLoginInfo().getUserTel());
			loginInfo=new LoginInfo();
			loginInfo.setLoginName(getLoginName());
			loginInfo.setLoginBusiType(getLoginBusiType());
			loginInfo.setUserName(getUserName());
			loginInfo.setLastDate(getLastDate());
			loginInfo.setTicket(getTicket());
			loginInfo.setUserId(getUserId());
			loginInfo.setFydm(getFydm());
			loginInfo.setDeptId(getDeptId());
			loginInfo.setDeptName(getDeptName());
			loginInfo.setUserTel(getUserTel());
		}
	}
	
	/**保存*/
	@SuppressLint("CommitPrefEdits")
	private void saveUserName(String userName){
		Editor editor=setting.edit();
		editor.putString(username, userName);
		editor.commit();
	}
	/**保存*/
	@SuppressLint("CommitPrefEdits")
	private void saveLoginName(String loginName_){
		Editor editor=setting.edit();
		editor.putString(loginName, loginName_);
		editor.commit();
	}
	/**保存*/
	@SuppressLint("CommitPrefEdits")
	private void saveLoginBusiType(String loginBusiType_){
		Editor editor=setting.edit();
		editor.putString(loginBusiType, loginBusiType_);
		editor.commit();
	}
	/**保存*/
	@SuppressLint("CommitPrefEdits")
	private void saveLastDate(String lastDate_){
		Editor editor=setting.edit();
		editor.putString(lastDate, lastDate_);
		editor.commit();
	}
	/**保存*/
	@SuppressLint("CommitPrefEdits")
	private void saveUserId(String userId_){
		Editor editor=setting.edit();
		editor.putString(userId, userId_);
		editor.commit();
	}
	/**保存*/
	@SuppressLint("CommitPrefEdits")
	private void saveFydm(String fydm_){
		Editor editor=setting.edit();
		editor.putString(fydm, fydm_);
		editor.commit();
	}
	/**保存*/
	@SuppressLint("CommitPrefEdits")
	private void saveTicket(String ticket_){
		Editor editor=setting.edit();
		editor.putString(ticket, ticket_);
		editor.commit();
	}
	/**保存*/
	@SuppressLint("CommitPrefEdits")
	private void saveDeptId(String deptId_){
		Editor editor=setting.edit();
		editor.putString(deptId, deptId_);
		editor.commit();
	}
	/**保存*/
	@SuppressLint("CommitPrefEdits")
	private void saveDeptName(String deptName_){
		Editor editor=setting.edit();
		editor.putString(deptName, deptName_);
		editor.commit();
	}
	/**保存*/
	@SuppressLint("CommitPrefEdits")
	private void saveUserTel(String userTel_){
		Editor editor=setting.edit();
		editor.putString(userTel, userTel_);
		editor.commit();
	}
	
	/**获取*/
	private String getUserName(){
		return setting.getString(username, DEFAULT_USERNAME);
	}
	/**获取*/
	private String getLoginName(){
		return setting.getString(loginName, DEFAULT_USERNAME);
	}
	/**获取*/
	private String getLoginBusiType(){
		return setting.getString(loginBusiType, DEFAULT_USERNAME);
	}
	/**获取*/
	private String getLastDate(){
		return setting.getString(lastDate, DEFAULT_USERNAME);
	}
	/**获取*/
	private String getUserId(){
		return setting.getString(userId, DEFAULT_USERNAME);
	}
	/**获取*/
	private String getFydm(){
		return setting.getString(fydm, DEFAULT_USERNAME);
	}
	/**获取*/
	private String getTicket(){
		return setting.getString(ticket, DEFAULT_USERNAME);
	}
	/**获取*/
	private String getDeptId(){
		return setting.getString(deptId, DEFAULT_USERNAME);
	}
	/**获取*/
	private String getDeptName(){
		return setting.getString(deptName, DEFAULT_USERNAME);
	}
	/**获取*/
	private String getUserTel(){
		return setting.getString(userTel, DEFAULT_USERNAME);
	}
	
	class NormalRequestBuild extends RequestBuild {
		@Override
		public String buildXml(Bundle bundle, Context context)
				throws UnsupportedEncodingException, NoSuchAlgorithmException {
			
			String time = DateUtil
					.formatDateTime(new Date(), "yyyyMMddHHmmss");
			
			String request_id = bundle.getString(BaseConstant.REQUEST_BUSI_CODE);
			
			StringWriter writer = null;
			try {
				XmlSerializer xmlSerializer = Xml.newSerializer();
				writer = new StringWriter();
				
				xmlSerializer.setOutput(writer);
				xmlSerializer.startDocument("UTF-8", true);
				xmlSerializer.startTag("", "request");
				
				
				xmlSerializer.startTag("", "requestFlow");
				xmlSerializer.text(getRequestFlow());
				xmlSerializer.endTag("", "requestFlow");
				
				
				xmlSerializer.startTag("", "version");
				xmlSerializer.text(BaseApplication.getVersion());
				xmlSerializer.endTag("", "version");
				
				xmlSerializer.startTag("", "UUID");
				xmlSerializer.text(BaseApplication.getUUID());
				xmlSerializer.endTag("", "UUID");
				
				
				xmlSerializer.startTag("", "busiCode");
				xmlSerializer.text(request_id);
				xmlSerializer.endTag("", "busiCode");
				
				xmlSerializer.startTag("", "loginName");
				xmlSerializer.text(getLoginName());
				xmlSerializer.endTag("", "loginName");
				
				xmlSerializer.startTag("", "loginBusiType");
				xmlSerializer.text(getLoginBusiType());
				xmlSerializer.endTag("", "loginBusiType");
				
				xmlSerializer.startTag("", "ticket");
				xmlSerializer.text(getTicket());
				xmlSerializer.endTag("", "ticket");
				
				String randCode = getRandCode();
				xmlSerializer.startTag("", "randCode");
				xmlSerializer.text(randCode);
				xmlSerializer.endTag("", "randCode");
				
				xmlSerializer.startTag("", "randCodeSec");
				xmlSerializer.text(getRandCodeSecOfRSA(randCode));
				xmlSerializer.endTag("", "randCodeSec");
				
				xmlSerializer.startTag("", "time");
				xmlSerializer.text(time);
				xmlSerializer.endTag("", "time");
				
				xmlSerializer.startTag("", "phoneType");
				xmlSerializer.text(BaseApplication.getPhoneType());
				xmlSerializer.endTag("", "phoneType");
				
				Iterator<String> it = bundle.keySet().iterator();
				
				xmlSerializer.startTag("", "parameters");
				
				String parameter = "";
				String name = "";
				while (it.hasNext()) {
					parameter = it.next();
					if (parameter.startsWith("parameters_")) {
						name = parameter.substring(11);
						xmlSerializer.startTag("", "parameter");
						xmlSerializer.attribute("", "name", name);
						xmlSerializer.cdsect(bundle.getString(parameter));
						xmlSerializer.endTag("", "parameter");
					}
				}
				
				
				xmlSerializer.endTag("", "parameters");
				xmlSerializer.endTag("", "request");
				
				xmlSerializer.endDocument();
			} catch (Exception e) {
				e.printStackTrace();
			} 
			return writer.toString();
		}

		private String getRandCode() {
			return RandomGeneter.generateString(12);
		}
		
		private String getRandCodeSecOfRSA(String randCode){
			return "";
		}

		@Override
		public String getServerUrl() {
			//return "http://61.186.177.9:8070/bs/request.shtml";
			//return "http://192.168.1.100:8080/sxgfBusiGate/request.shtml";
			//return "http://192.168.1.104:8080/sxgfBusiGate/request.shtml";
			//return "http://10.0.2.2:8088/bs/request.shtml";
			//return "http://149.0.0.26:8027/bs/request.shtml";
			//return "http://172.16.0.10/sxgfBusiGate/request.shtml";
	// todo zhiqiande
//			return "http://192.168.1.119:8080/sxgfBusiGate/request.shtml";
			//return "http://192.168.1.108:9000/sxgfBusiGate/request.shtml";
			//return "http://1.85.16.50:10000/requestChange/redirect.do";
			//return "http://154.0.66.211:8080/sxgfBusiGate/request.shtml";
			//return "http://172.25.242.1:8080/sxgfBusiGate/request.shtml";

			return "http://192.168.130.61:8090/sxgfBusiGate/request.shtml";

			}

		
		
		public  String getRequestFlow(){
			return DateUtil.formatDateTime(new Date(),"yyyyMMddHHmmss") + RandomGeneter.generateString(6);
		}
	}

}
