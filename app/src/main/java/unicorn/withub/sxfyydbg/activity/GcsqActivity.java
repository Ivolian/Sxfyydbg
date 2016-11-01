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

public class GcsqActivity extends BaseActivity{
	private String string="公出审批";
	private EditText et_gcsqrxm;
	private EditText et_gcszbm;
	private EditText et_gcwcrxm;
	private EditText et_gcwcdd;
	private TextView tv_gcks;
	private TextView tv_gcjs;
	private TextView tv_gcwcsy;
	private TextView tv_submit;
	private TextView tv_cklzjl;
	private TextView tv_gcbmldyj;
	private TextView tv_gczgyldyj;
	private String gcbmldyj;
	private String gczgyldyj;
	private JSONObject result;
	private String uid;
	private String unid;
	private BroadcastReceiver receiver=new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String action=intent.getAction();
			if(BroadcastData.gcsqzgbmyj.equals(action)){
				tv_gcbmldyj.setText(gcbmldyj+ StringUtil.editcontent+"\n");
				UserData.tjlc.setFldtrace(tv_gcbmldyj.getText().toString());
			}else if(BroadcastData.gcsqzgyldyj.equals(action)){
				tv_gczgyldyj.setText(gczgyldyj+StringUtil.editcontent+"\n");
				UserData.tjlc.setFldtrace(tv_gczgyldyj.getText().toString());
			}else if(BroadcastData.finish.equals(action)){
				finish();
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gcsq_activity);
		initView(string);
		IntentFilter filter=new IntentFilter();
		filter.addAction(BroadcastData.gcsqzgbmyj);
		filter.addAction(BroadcastData.gcsqzgyldyj);
		filter.addAction(BroadcastData.finish);
		registerReceiver(receiver, filter);
	}
	@Override
	protected void initView(String title) {
		// TODO Auto-generated method stub
		super.initView(title);
		UserData.tjlc.setFldbname("");
		UserData.tjlc.setFldtrace("");
		tv_submit=(TextView) findViewById(R.id.tv_gcsq_save);
		tv_cklzjl=(TextView) findViewById(R.id.tv_gcsq_cklzjl);
		tv_submit.setOnClickListener(listener);
		tv_cklzjl.setOnClickListener(listener);
		et_gcsqrxm=(EditText) findViewById(R.id.et_gcsqrxm);
		et_gcszbm=(EditText) findViewById(R.id.et_gcszbm);
		et_gcwcrxm=(EditText) findViewById(R.id.et_gcwcrxm);
		et_gcwcdd=(EditText) findViewById(R.id.et_gcwcdd);
		tv_gcbmldyj=(TextView) findViewById(R.id.tv_gcbmldyj);
		tv_gczgyldyj=(TextView) findViewById(R.id.tv_gczgyldyj);
		tv_gcbmldyj.setOnClickListener(listener2);
		tv_gczgyldyj.setOnClickListener(listener2);
		tv_gcbmldyj.setMovementMethod(ScrollingMovementMethod.getInstance());
		tv_gczgyldyj.setMovementMethod(ScrollingMovementMethod.getInstance());
		tv_gcks=(TextView) findViewById(R.id.tv_gcks);
		tv_gcjs=(TextView) findViewById(R.id.tv_gcjs);
		tv_gcwcsy=(TextView) findViewById(R.id.tv_gcwcsy);
		//tv_back=(TextView) findViewById(R.id.tv_gcsq_back);
		Bundle bundle=getIntent().getExtras();
		uid=bundle.getString("sid");
		if(LoginHelper.getLoginInfo()==null){
			StringUtil.tagetToLogin(GcsqActivity.this);
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
            case R.id.tv_gcsq_save:
            	try {
					intent=new Intent(GcsqActivity.this,HdxzActivity.class);
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
            case R.id.tv_gcsq_cklzjl:
            	intent=new Intent(GcsqActivity.this,CklzjlActivity.class);
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
				case R.id.tv_gcbmldyj:
					tv_gcbmldyj.setText(gcbmldyj);
					if("YuanZhang".equals(StringUtil.nullOrEmptyToStr(result.getString("canEditId")))){
						UserData.tjlc.setFldbname("YuanZhang");
						StringUtil.edittype=BroadcastData.gcsqzgbmyj;
						dialog.show();
					}
					break;
				case R.id.tv_gczgyldyj:
					tv_gczgyldyj.setText(gczgyldyj);
					if("FenYuanZhang".equals(StringUtil.nullOrEmptyToStr(result.getString("canEditId")))){
						UserData.tjlc.setFldbname("FenYuanZhang");
						StringUtil.edittype=BroadcastData.gcsqzgyldyj;
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
					"gongchuapply");
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
					et_gcsqrxm.setText(StringUtil.nullOrEmptyToStr(result.getString("reader")));
					et_gcszbm.setText(StringUtil.nullOrEmptyToStr(result.getString("approveName")));
					et_gcwcrxm.setText(StringUtil.nullOrEmptyToStr(result.getString("readUser")));
					et_gcwcdd.setText(StringUtil.nullOrEmptyToStr(result.getString("ysunit")));
					tv_gcwcsy.setText(StringUtil.nullOrEmptyToStr(result.getString("preason")));
					tv_gcks.setText(StringUtil.nullOrEmptyToStr(result.getString("startdate")));
					tv_gcjs.setText(StringUtil.nullOrEmptyToStr(result.getString("enddate")));
					unid=StringUtil.nullOrEmptyToStr(result.getString("unid"));
					UserData.tjlc.setUnid(unid);
					UserData.tjlc.setProcessid(StringUtil.nullOrEmptyToStr(result.getString("processUNID")));
					UserData.tjlc.setSubject(StringUtil.nullOrEmptyToStr(result.getString("subject")));
					JSONObject traces=new JSONObject(result.getString("traces"));
					if(traces!=null){
						Log.i("traces.getString", "==="+traces.getString("YuanZhang"));
						JSONArray bmlist=new JSONArray(traces.getString("YuanZhang"));
						if(bmlist!=null&&bmlist.length()!=0){
							setContent(bmlist,tv_gcbmldyj);
						}
						JSONArray yldlist=new JSONArray(traces.getString("FenYuanZhang"));
						if(yldlist!=null&&yldlist.length()!=0){
							setContent(yldlist,tv_gczgyldyj);
						}
					}
					if(!"YuanZhang".equals(StringUtil.nullOrEmptyToStr(result.getString("canEditId")))){
						tv_gcbmldyj.setBackgroundColor(getResources().getColor(R.color.editable));
					}
					if(!"FenYuanZhang".equals(StringUtil.nullOrEmptyToStr(result.getString("canEditId")))){
						tv_gczgyldyj.setBackgroundColor(getResources().getColor(R.color.editable));
					}
					gcbmldyj=tv_gcbmldyj.getText().toString();
					gczgyldyj=tv_gczgyldyj.getText().toString();
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
			StringUtil.tagetToLogin(GcsqActivity.this);
			break;
		case R.id.titleBack:
            finish();
			break;
		}
	}
	

}
