package unicorn.withub.sxfyydbg.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import unicorn.withub.sxfyydbg.R;
import unicorn.withub.sxfyydbg.data.BaseConstant;
import unicorn.withub.sxfyydbg.data.LoginHelper;
import unicorn.withub.sxfyydbg.data.LoginInfo;
import unicorn.withub.sxfyydbg.data.NormalRequestBuild;
import unicorn.withub.sxfyydbg.util.DataHelper;

/**登录页面*/
public class LoginActivity extends BaseActivity{
	public static final String PREFS_NAME = "prefsname";
	public static final String USERNAME = "username";
	private static final String DEFAULT_USERNAME = ""; //默认用户名
	private EditText et_name;
	private EditText et_pwd;
	private Button btn_submit;
	private JSONObject result;
	//偏好设置，用于保存用户名
	SharedPreferences setting;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);
		init();
	}
	/**控件初始化*/
	void init(){
		et_name=(EditText) findViewById(R.id.et_name);
		et_pwd=(EditText) findViewById(R.id.et_pwd);
		btn_submit=(Button) findViewById(R.id.login_btn_submit);
		setting=getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		et_name.setText(getUserName());
		if("".equals(et_name.getText().toString())){
			et_name.requestFocus();
		}else{
			et_pwd.requestFocus();
		}
		btn_submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				query(et_name.getText().toString(), et_pwd.getText().toString());
				
			}
		});
	}
	
	
	public void query(String userId,String pwd){
		if(userId == null || userId.trim().equals("")){
			alert(null, "用户名不能为空");
			return;
		}
		
		if(pwd == null || pwd.trim().equals("")){
			alert(null, "密码不能为空");
			return;
		}
		saveUserName(et_name.getText().toString());
		 dataHelper = DataHelper.getInstance();
		 Log.i("baseActivity.dataHelper", "baseActivity.dataHelper="+dataHelper);
		 Bundle bundle = new Bundle();
		 
		 bundle.putString(BaseConstant.REQUEST_BUSI_CODE,
					"oalogin");
		 bundle.putString("RequestlBuild",
					"NormalRequestBuild");
		 bundle.putString("parameters_userName", userId);
		 bundle.putString("parameters_passWord", pwd);
		 
		 sendRequest(bundle,new NormalRequestBuild(),"正在登录,请稍后...");
	}
	public void busiFinish(Bundle bundle) {
		if(bundle.get("code").equals("000000")){
		    if("1".equals(bundle.getString("parameter-message"))){
		    	LoginHelper.setKey(bundle.getString("parameter-key"));
		    	Log.i("parameter-key", "parameter-key="+bundle.getString("parameter-key"));
		    	LoginInfo loginInfo=new LoginInfo();
		    	loginInfo.setTicket(bundle.getString("parameter-key"));
		    	String rs = bundle.getString("parameter-result");
		    	Log.i("rs", "rs=="+rs);
		    	try {
					result = new JSONObject(rs);
					Log.i("result", "result=="+result.toString());
					loginInfo.setLoginName(result.getString("loginName"));
					loginInfo.setUserId(result.getString("userId"));
					loginInfo.setFydm(result.getString("fydm"));
					loginInfo.setLastDate(result.getString("lastDate"));
					loginInfo.setUserName(result.getString("userName"));
					loginInfo.setLoginBusiType(result.getString("loginBusiType"));
					loginInfo.setDeptId(result.getString("deptId"));
					loginInfo.setDeptName(result.getString("deptName"));
					loginInfo.setUserTel(result.getString("userTel"));
					Log.i("loginInfo", "loginInfo=="+loginInfo.toString());
					LoginHelper.setLoginInfo(loginInfo);
					et_pwd.getText().clear();
					Intent intent = new Intent(LoginActivity.this,
							MainActivity.class);
					startActivity(intent);
				} catch (JSONException e) {
					alert(null, "数据转换异常");
				}
		    }else{
		    	alert(null, "登录失败！");
		    }
		}else{
			alert("操作提示", getCodeMsg(bundle.getString("code"),bundle.getString("msg")));
		}
    }
	/**保存用户名*/
	@SuppressLint("CommitPrefEdits")
	private void saveUserName(String userName){
		Editor editor=setting.edit();
		editor.putString(USERNAME, userName);
		editor.commit();
	}
	/**获取用户名*/
	private String getUserName(){
		return setting.getString(USERNAME, DEFAULT_USERNAME);
	}
//	private void getAlertDialog(){
//		AlertDialog.Builder builder=new AlertDialog.Builder(this);
//		builder.setTitle("提示");
//		builder.setMessage("登录成功！");
//		builder.setNeutralButton("确定",diaListener);
//		builder.create().show();		
//	}
//	DialogInterface.OnClickListener diaListener=new DialogInterface.OnClickListener() {
//		
//		@Override
//		public void onClick(DialogInterface dialog, int which) {
//			// TODO Auto-generated method stub
//			finish();
//		}
//	};
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.i("loginActivity", "loginActivity");
	};

}
