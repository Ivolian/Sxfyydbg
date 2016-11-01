package unicorn.withub.sxfyydbg.activity;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import unicorn.withub.sxfyydbg.R;
import unicorn.withub.sxfyydbg.broadcastreceiver.BroadcastData;
import unicorn.withub.sxfyydbg.data.BaseConstant;
import unicorn.withub.sxfyydbg.data.LoginHelper;
import unicorn.withub.sxfyydbg.data.NormalRequestBuild;
import unicorn.withub.sxfyydbg.data.UserData;
import unicorn.withub.sxfyydbg.util.DataHelper;
import unicorn.withub.sxfyydbg.util.StringUtil;

public class YcsqActivity extends BaseActivity{
	private String string="用车审批";
	private EditText et_ycly;
	private EditText et_ycsqr;
	private EditText et_sqrdh;
	private EditText et_ccrxm;
	private EditText et_qwmdd;
	private EditText et_ycrs;
	private TextView tv_bmsp;
	private TextView tv_cgsp;
	private TextView tv_cdldsp;
	private String bmsp;
	private String cgsp;
	private String cdldsp;
	private EditText et_bz;
	private TextView tv_ycbm;
	private TextView tv_ycks;
	private TextView tv_ycjs;
	private TextView tv_save;
	private TextView tv_cklzjl;
	private JSONObject result;
	private String uid;
	private String unid;
	private BroadcastReceiver receiver=new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String action=intent.getAction();
			if(BroadcastData.ycsqbmsp.equals(action)){
				tv_bmsp.setText(bmsp+ StringUtil.editcontent+"\n");
				UserData.tjlc.setFldtrace(tv_bmsp.getText().toString());
			}else if(BroadcastData.ycsqcgsp.equals(action)){
				tv_cgsp.setText(cgsp+StringUtil.editcontent+"\n");
				UserData.tjlc.setFldtrace(tv_cgsp.getText().toString());
			}else if(BroadcastData.ycsqcdldsp.equals(action)){
				tv_cdldsp.setText(cdldsp+StringUtil.editcontent+"\n");
				UserData.tjlc.setFldtrace(tv_cdldsp.getText().toString());
			}else if(BroadcastData.finish.equals(action)){
				finish();
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ycsq_activity);
		initView(string);
		IntentFilter filter=new IntentFilter();
		filter.addAction(BroadcastData.ycsqbmsp);
		filter.addAction(BroadcastData.ycsqcgsp);
		filter.addAction(BroadcastData.ycsqcdldsp);
		filter.addAction(BroadcastData.finish);
		registerReceiver(receiver, filter);
	}
	@Override
	protected void initView(String title) {
		// TODO Auto-generated method stub
		super.initView(title);
		UserData.tjlc.setFldbname("");
		UserData.tjlc.setFldtrace("");
		et_ycly=(EditText) findViewById(R.id.et_ycly);
		et_ycsqr=(EditText) findViewById(R.id.et_ycsqr);
		et_sqrdh=(EditText) findViewById(R.id.et_sqrdh);
		et_ccrxm=(EditText) findViewById(R.id.et_ccrxm);
		et_qwmdd=(EditText) findViewById(R.id.et_qwmdd);
		et_ycrs=(EditText) findViewById(R.id.et_ycrs);
		tv_bmsp=(TextView) findViewById(R.id.tv_bmsp);
		tv_cgsp=(TextView) findViewById(R.id.tv_cgsp);
		tv_cdldsp=(TextView) findViewById(R.id.tv_cdldsp);
		tv_bmsp.setOnClickListener(listener2);
		tv_cgsp.setOnClickListener(listener2);
		tv_cdldsp.setOnClickListener(listener2);
		et_bz=(EditText) findViewById(R.id.et_bz);
		tv_bmsp.setMovementMethod(ScrollingMovementMethod.getInstance());
		tv_cgsp.setMovementMethod(ScrollingMovementMethod.getInstance());
		tv_cdldsp.setMovementMethod(ScrollingMovementMethod.getInstance());
		et_bz.setMovementMethod(ScrollingMovementMethod.getInstance());
		tv_ycbm=(TextView) findViewById(R.id.tv_ycbm);
		tv_ycks=(TextView) findViewById(R.id.tv_ycks);
		//tv_ycks.setOnClickListener(listener);
		tv_ycjs=(TextView) findViewById(R.id.tv_ycjs);
		//tv_ycjs.setOnClickListener(listener);
		tv_save=(TextView) findViewById(R.id.tv_ycsq_save);
		tv_cklzjl=(TextView) findViewById(R.id.tv_ycsq_cklzjl);
		//tv_back=(TextView) findViewById(R.id.tv_ycsq_back);
		tv_save.setOnClickListener(listener);
		tv_cklzjl.setOnClickListener(listener);
		Bundle bundle=getIntent().getExtras();
		Log.i("uid="+bundle.getString("uid"), "sid="+bundle.getString("sid"));
		uid=bundle.getString("sid");
		if(LoginHelper.getLoginInfo()==null){
			StringUtil.tagetToLogin(YcsqActivity.this);
			return;
		}
		query(bundle.getString("uid"), bundle.getString("sid"));
	}
	OnClickListener listener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int id=v.getId();
			Intent intent=null;
			Bundle bundle=null;
			switch (id) {
            case R.id.tv_ycsq_save:
            	try {
					intent=new Intent(YcsqActivity.this,HdxzActivity.class);
					bundle=new Bundle();
					bundle.putString("unid", unid);
					bundle.putString("ProcessUNID", StringUtil.nullOrEmptyToStr(result.getString("processUNID")));
					intent.putExtras(bundle);
					break;
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					alert(null, "数据转换异常");
				}
				break;
            case R.id.tv_ycsq_cklzjl:
            	intent=new Intent(YcsqActivity.this,CklzjlActivity.class);
				bundle=new Bundle();
				bundle.putString("uid", uid);
				intent.putExtras(bundle);
				break;
			}
			startActivity(intent);
		}
	};
	 OnClickListener listener2=new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int id=v.getId();
				try {
					switch (id) {
					case R.id.tv_bmsp:
						tv_bmsp.setText(bmsp);
						if("Node3".equals(StringUtil.nullOrEmptyToStr(result.getString("nodeId")))){
							UserData.tjlc.setFldbname("Node3");
							StringUtil.edittype=BroadcastData.ycsqbmsp;
							dialog.show();
						}
						break;
					case R.id.tv_cgsp:
						tv_cgsp.setText(cgsp);
						if("Node8".equals(StringUtil.nullOrEmptyToStr(result.getString("nodeId")))){
							UserData.tjlc.setFldbname("Node8");
							StringUtil.edittype=BroadcastData.ycsqcgsp;
							dialog.show();
						}
						break;
					case R.id.tv_cdldsp:
						tv_cdldsp.setText(cdldsp);
						if("Node15".equals(StringUtil.nullOrEmptyToStr(result.getString("nodeId")))){
							UserData.tjlc.setFldbname("Node15");
							StringUtil.edittype=BroadcastData.ycsqcdldsp;
							dialog.show();
						}
						break;
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					alert(null, "数据转换异常");
				}
			}
		};
	protected void query(String uid,String sid) {
		 dataHelper = DataHelper.getInstance();
		 Bundle bundle = new Bundle();
		 bundle.putString(BaseConstant.REQUEST_BUSI_CODE,
					"ycapply");
		 bundle.putString("RequestlBuild",
					"NormalRequestBuild");
		 bundle.putString("parameters_uid", uid);
		 bundle.putString("parameters_sid", sid);
		 Log.i("loginData", "loginData="+LoginHelper.getLoginInfo());
		 bundle.putString("loginInfo", LoginHelper.getLoginInfo()+"");
		 sendRequest(bundle,new NormalRequestBuild(),"正在查询" );
	}
	public void busiFinish(Bundle bundle) {
		if(bundle.get("code").equals("000000")){
		
			String rs = bundle.getString("parameter-result");
			Log.i("rs", "rs=="+rs);
			if(!rs.equals("") && !rs.equals("null")){
				try {
					result = new JSONObject(rs);
					Log.i("result", "result=="+result.toString());
					et_ycly.setText(StringUtil.nullOrEmptyToStr(result.getString("subject")));
					et_ycsqr.setText(StringUtil.nullOrEmptyToStr(result.getString("frmUser")));
					et_sqrdh.setText(StringUtil.nullOrEmptyToStr(result.getString("frmUserTel")));
					tv_ycbm.setText(StringUtil.nullOrEmptyToStr(result.getString("fdDept")));
					tv_ycks.setText(StringUtil.nullOrEmptyToStr(result.getString("startTime")));
					tv_ycjs.setText(StringUtil.nullOrEmptyToStr(result.getString("endTime")));
					et_ccrxm.setText(StringUtil.nullOrEmptyToStr(result.getString("andName")));
					et_qwmdd.setText(StringUtil.nullOrEmptyToStr(result.getString("dest")));
					et_ycrs.setText(StringUtil.nullOrEmptyToStr(result.getString("andNamenum")+"人"));
					et_bz.setText(StringUtil.nullOrEmptyToStr(result.getString("comment")));
					unid=StringUtil.nullOrEmptyToStr(result.getString("unid"));
					UserData.tjlc.setUnid(unid);
					UserData.tjlc.setProcessid(StringUtil.nullOrEmptyToStr(result.getString("processUNID")));
					UserData.tjlc.setSubject(StringUtil.nullOrEmptyToStr(result.getString("subject")));
					JSONObject traces=new JSONObject(result.getString("traces"));
					if(traces!=null){
						Log.i("traces.getString", "==="+traces.getString("bmsp"));
						JSONArray bmlist=new JSONArray(traces.getString("bmsp"));
						if(bmlist!=null&&bmlist.length()!=0){
							setContent(bmlist,tv_bmsp);
						}
						JSONArray cglist=new JSONArray(traces.getString("cgsp"));
						if(cglist!=null&&cglist.length()!=0){
							setContent(cglist,tv_cgsp);
						}
						JSONArray cdlist=new JSONArray(traces.getString("cdldp"));
						if(cdlist!=null&&cdlist.length()!=0){
							setContent(cdlist,tv_cdldsp);
						}
					}
					if(!"Node3".equals(StringUtil.nullOrEmptyToStr(result.getString("nodeId")))){
						tv_bmsp.setBackgroundColor(getResources().getColor(R.color.editable));
					}
					if(!"Node8".equals(StringUtil.nullOrEmptyToStr(result.getString("nodeId")))){
						tv_cgsp.setBackgroundColor(getResources().getColor(R.color.editable));
					}
					if(!"Node15".equals(StringUtil.nullOrEmptyToStr(result.getString("nodeId")))){
						tv_cdldsp.setBackgroundColor(getResources().getColor(R.color.editable));
					}
					bmsp=tv_bmsp.getText().toString();
					cgsp=tv_cgsp.getText().toString();
					cdldsp=tv_cdldsp.getText().toString();
				} catch (JSONException e) {
					alert(null, "数据转换异常");
				}
			}
		}else{
			alert("操作提示", getCodeMsg(bundle.getString("code"),bundle.getString("msg")));
		}
   }
	private void setContent(JSONArray array, TextView et) {
		String str = "";
		try {
			for (int i = 0; i < array.length(); i++) {
				str += StringUtil.nullOrEmptyToStr(array.getJSONObject(i).getString("text")) + "\n";
			}
			et.setText(str);
			str = "";
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(receiver);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.titleSub:
			StringUtil.tagetToLogin(YcsqActivity.this);
			break;
		case R.id.titleBack:
            finish();
			break;
		}
	}
	

}
