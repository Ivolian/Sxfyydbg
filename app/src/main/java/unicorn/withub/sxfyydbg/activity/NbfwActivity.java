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

/**我的待办详情页面*/
public class NbfwActivity extends BaseActivity{
	private String string="内部发文";
	private TextView tv_department;
	private TextView tv_editor;
	private TextView tv_tilte;
	private TextView tv_keyword;
	private TextView tv_fwsj;
	private TextView tv_ckzw;
	private TextView tv_yldps;
	private TextView tv_bmldyj;
	private TextView tv_bmblyj;
	private TextView tv_qtbmyj;
	private TextView tv_submit;
	private TextView tv_cklzjl;
	private JSONObject result;
	private JSONArray listYldps;
	private JSONArray listBmldyj;
	private JSONArray listBmblyj;
	private JSONArray listQtbmyj;
	private String unid;
	private String uid;
	private String yldps;
	private String bmldyj;
	private String bmblyj;
	private String qtbmyj;
	private BroadcastReceiver receiver=new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String action=intent.getAction();
			if(BroadcastData.nbfwyldps.equals(action)){
				tv_yldps.setText(yldps+ StringUtil.editcontent+"\n");
				UserData.tjlc.setFldtrace(tv_yldps.getText().toString());
			}else if(BroadcastData.nbfwbmldyj.equals(action)){
				tv_bmldyj.setText(bmldyj+StringUtil.editcontent+"\n");
				UserData.tjlc.setFldtrace(tv_bmldyj.getText().toString());
			}else if(BroadcastData.nbfwbmblyj.equals(action)){
				tv_bmblyj.setText(bmblyj+StringUtil.editcontent+"\n");
				UserData.tjlc.setFldtrace(tv_bmblyj.getText().toString());
			}else if(BroadcastData.nbfwqtbmyj.equals(action)){
				tv_qtbmyj.setText(qtbmyj+StringUtil.editcontent+"\n");
				UserData.tjlc.setFldtrace(tv_qtbmyj.getText().toString());
			}else if(BroadcastData.finish.equals(action)){
				finish();
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.plandetail_activity);
		initView(string);
		IntentFilter filter=new IntentFilter();
		filter.addAction(BroadcastData.nbfwyldps);
		filter.addAction(BroadcastData.nbfwbmldyj);
		filter.addAction(BroadcastData.nbfwbmblyj);
		filter.addAction(BroadcastData.nbfwqtbmyj);
		filter.addAction(BroadcastData.finish);
		registerReceiver(receiver, filter);
	}
	/**控件初始化*/
	@Override
	protected void initView(String title) {
		// TODO Auto-generated method stub
		super.initView(title);
		UserData.tjlc.setFldbname("");
		UserData.tjlc.setFldtrace("");
		tv_submit=(TextView) findViewById(R.id.tv_nbfw_submit);
		tv_cklzjl=(TextView) findViewById(R.id.tv_nbfw_cklzjl);
		tv_submit.setOnClickListener(listener);
		tv_cklzjl.setOnClickListener(listener);
		tv_department=(TextView) findViewById(R.id.tv_department);
		tv_editor=(TextView) findViewById(R.id.tv_editer);
		tv_tilte=(TextView) findViewById(R.id.tv_tilte);
		tv_keyword=(TextView) findViewById(R.id.tv_keyword);
		tv_fwsj=(TextView) findViewById(R.id.tv_fwsj);
		tv_ckzw=(TextView) findViewById(R.id.tv_ckzw);
		tv_ckzw.setOnClickListener(listener);
		tv_yldps=(TextView) findViewById(R.id.tv_yldps);
		tv_bmldyj=(TextView) findViewById(R.id.tv_bmldyj);
		tv_bmblyj=(TextView) findViewById(R.id.tv_bmblyj);
		tv_qtbmyj=(TextView) findViewById(R.id.tv_qtbmyj);
		tv_yldps.setOnClickListener(listener2);
		tv_bmldyj.setOnClickListener(listener2);
		tv_bmblyj.setOnClickListener(listener2);
		tv_qtbmyj.setOnClickListener(listener2);
		tv_yldps.setMovementMethod(ScrollingMovementMethod.getInstance());
		tv_bmldyj.setMovementMethod(ScrollingMovementMethod.getInstance());
		tv_bmblyj.setMovementMethod(ScrollingMovementMethod.getInstance());
		tv_qtbmyj.setMovementMethod(ScrollingMovementMethod.getInstance());
		Bundle bundle=getIntent().getExtras();
		uid=bundle.getString("sid");
		if(LoginHelper.getLoginInfo()==null){
			StringUtil.tagetToLogin(NbfwActivity.this);
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
			case R.id.tv_ckzw:
				intent=new Intent(NbfwActivity.this,ZwDetailActivity.class);
				bundle=new Bundle();
				bundle.putString("unid", unid);
				intent.putExtras(bundle);
				break;
			case R.id.tv_nbfw_submit:
				try {
					intent=new Intent(NbfwActivity.this,HdxzActivity.class);
					bundle=new Bundle();
					bundle.putString("unid", unid);
					bundle.putString("ProcessUNID", StringUtil.nullOrEmptyToStr(result.getString("ProcessUNID")));
					intent.putExtras(bundle);
					break;
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					alert(null, "数据转换异常");
				}
				break;
			case R.id.tv_nbfw_cklzjl:
				intent=new Intent(NbfwActivity.this,CklzjlActivity.class);
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
				case R.id.tv_yldps:
					tv_yldps.setText(yldps);
					if("YuanZhang".equals(StringUtil.nullOrEmptyToStr(result.getString("canEditId")))){
						UserData.tjlc.setFldbname("YuanZhang");
						StringUtil.edittype=BroadcastData.nbfwyldps;
						dialog.show();
					}
					break;
				case R.id.tv_bmldyj:
					tv_bmldyj.setText(bmldyj);
					if("BuMen".equals(StringUtil.nullOrEmptyToStr(result.getString("canEditId")))){
						UserData.tjlc.setFldbname("BuMen");
						StringUtil.edittype=BroadcastData.nbfwbmldyj;
						dialog.show();
					}
					break;
				case R.id.tv_bmblyj:
					tv_bmblyj.setText(bmblyj);
					if("BuMenBanLi".equals(StringUtil.nullOrEmptyToStr(result.getString("canEditId")))){
						UserData.tjlc.setFldbname("BuMenBanLi");
						StringUtil.edittype=BroadcastData.nbfwbmblyj;
						dialog.show();
					}
					break;
				case R.id.tv_qtbmyj:
					tv_qtbmyj.setText(qtbmyj);
					if("QiTaBuMen".equals(StringUtil.nullOrEmptyToStr(result.getString("canEditId")))){
						UserData.tjlc.setFldbname("QiTaBuMen");
						StringUtil.edittype=BroadcastData.nbfwqtbmyj;
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
					"fw");
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
					tv_department.setText(StringUtil.nullOrEmptyToStr(result.getString("ApproveName")));
					tv_editor.setText(StringUtil.nullOrEmptyToStr(result.getString("PostName")));
					tv_tilte.setText(StringUtil.nullOrEmptyToStr(result.getString("Subject")));
					tv_keyword.setText(StringUtil.nullOrEmptyToStr(result.getString("ztc")));
					tv_fwsj.setText(StringUtil.nullOrEmptyToStr(result.getString("yinfasj")));
					listYldps=new JSONArray(result.getString("listYuanZhang"));
					unid=StringUtil.nullOrEmptyToStr(result.getString("unid"));
					UserData.tjlc.setUnid(unid);
					UserData.tjlc.setProcessid(StringUtil.nullOrEmptyToStr(result.getString("ProcessUNID")));
					UserData.tjlc.setSubject(StringUtil.nullOrEmptyToStr(result.getString("Subject")));
					//Log.i("Subject", "Subject=="+StringUtil.nullOrEmptyToStr(result.getString("Subject")));
					if(listYldps!=null&&listYldps.length()!=0){
						setContent(listYldps,tv_yldps);
					}
					listBmldyj=new JSONArray(result.getString("listBuMen"));
					if(listBmldyj!=null&&listBmldyj.length()!=0){
						setContent(listBmldyj,tv_bmldyj);
					}
					listBmblyj=new JSONArray(result.getString("listBuMenBanLi"));
					if(listBmblyj!=null&&listBmblyj.length()!=0){
						setContent(listBmblyj,tv_bmblyj);
					}
					listQtbmyj=new JSONArray(result.getString("QiTaBuMen"));
					if(listQtbmyj!=null&&listQtbmyj.length()!=0){
						setContent(listQtbmyj,tv_qtbmyj);
					}
					if(!"YuanZhang".equals(StringUtil.nullOrEmptyToStr(result.getString("canEditId")))){
						tv_yldps.setBackgroundColor(getResources().getColor(R.color.editable));
					}
					if(!"BuMen".equals(StringUtil.nullOrEmptyToStr(result.getString("canEditId")))){
						tv_bmldyj.setBackgroundColor(getResources().getColor(R.color.editable));
					}
					if(!"BuMenBanLi".equals(StringUtil.nullOrEmptyToStr(result.getString("canEditId")))){
						tv_bmblyj.setBackgroundColor(getResources().getColor(R.color.editable));
					}
					if(!"QiTaBuMen".equals(StringUtil.nullOrEmptyToStr(result.getString("canEditId")))){
						tv_qtbmyj.setBackgroundColor(getResources().getColor(R.color.editable));
					}
					yldps=tv_yldps.getText().toString();
					bmldyj=tv_bmldyj.getText().toString();
					bmblyj=tv_bmblyj.getText().toString();
					qtbmyj=tv_qtbmyj.getText().toString();
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
			StringUtil.tagetToLogin(NbfwActivity.this);
			break;
		case R.id.titleBack:
            finish();
			break;
		}
	}
	
}
