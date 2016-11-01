package unicorn.withub.sxfyydbg.activity;


import android.content.BroadcastReceiver;
import android.content.Context;import android.content.Intent;
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

/**内部收文详情页面*/
public class NbswActivity extends BaseActivity{
	private String string="内部收文";
	private TextView tv_lwdw;
	private TextView tv_wh;
	private TextView tv_swbh;
	private TextView tv_lwbt;
	private TextView tv_lwrq;
	private TextView tv_ckzw;
	private TextView tv_nbyj;
	private TextView tv_ldps;
	private TextView tv_wjqm;
	private TextView tv_cbqk;
	private TextView tv_submit;
	private TextView tv_cklzjl;
	private JSONObject result;
	private String unid;
	private String uid;
	private String nbyj;
	private String ldps;
	private String wjqm;
	private String cbqk;
	private BroadcastReceiver receiver=new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String action=intent.getAction();
			if(BroadcastData.nbswnbyj.equals(action)){
				tv_nbyj.setText(nbyj+ StringUtil.editcontent+"\n");
				UserData.tjlc.setFldtrace(tv_nbyj.getText().toString());
			}else if(BroadcastData.nbswldps.equals(action)){
				tv_ldps.setText(ldps+StringUtil.editcontent+"\n");
				UserData.tjlc.setFldtrace(tv_ldps.getText().toString());
			}else if(BroadcastData.nbswwjqm.equals(action)){
				tv_wjqm.setText(wjqm+StringUtil.editcontent+"\n");
				UserData.tjlc.setFldtrace(tv_wjqm.getText().toString());
			}else if(BroadcastData.nbswcbqk.equals(action)){
				tv_cbqk.setText(cbqk+StringUtil.editcontent+"\n");
				UserData.tjlc.setFldtrace(tv_cbqk.getText().toString());
			}else if(BroadcastData.finish.equals(action)){
				finish();
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		try {
			setContentView(R.layout.nbsw_activity);
			initView(string);
			IntentFilter filter=new IntentFilter();
			filter.addAction(BroadcastData.nbswcbqk);
			filter.addAction(BroadcastData.nbswldps);
			filter.addAction(BroadcastData.nbswnbyj);
			filter.addAction(BroadcastData.nbswwjqm);
			filter.addAction(BroadcastData.finish);
			registerReceiver(receiver, filter);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**控件初始化*/
	@Override
	protected void initView(String title) {
		// TODO Auto-generated method stub
		super.initView(title);
		UserData.tjlc.setFldbname("");
		UserData.tjlc.setFldtrace("");
		//scrollView.setFocusableInTouchMode(true);
		tv_submit=(TextView) findViewById(R.id.tv_nbsw_submit);
		//tv_back=(TextView) findViewById(R.id.tv_nbsw_back);
		tv_cklzjl=(TextView) findViewById(R.id.tv_nbsw_cklzjl);
		//tv_back.setOnClickListener(listener2);
		tv_submit.setOnClickListener(listener);
		tv_cklzjl.setOnClickListener(listener);
		tv_lwdw=(TextView) findViewById(R.id.tv_lwdw);
		tv_wh=(TextView) findViewById(R.id.tv_wh);
		tv_swbh=(TextView) findViewById(R.id.tv_swbh);
		tv_lwbt=(TextView) findViewById(R.id.tv_lwbt);
		tv_lwbt.setMovementMethod(ScrollingMovementMethod.getInstance());
		tv_lwrq=(TextView) findViewById(R.id.tv_lwrq);
		tv_ckzw=(TextView) findViewById(R.id.tv_ckzw);
		tv_ckzw.setOnClickListener(listener);
		tv_nbyj=(TextView) findViewById(R.id.tv_nbyj);
		tv_ldps=(TextView) findViewById(R.id.tv_ldps);
		tv_wjqm=(TextView) findViewById(R.id.tv_wjqm);
		tv_cbqk=(TextView) findViewById(R.id.tv_cbqk);
		tv_nbyj.setOnClickListener(listener2);
		tv_ldps.setOnClickListener(listener2);
		tv_wjqm.setOnClickListener(listener2);
		tv_cbqk.setOnClickListener(listener2);
		tv_nbyj.setMovementMethod(ScrollingMovementMethod.getInstance());
		tv_ldps.setMovementMethod(ScrollingMovementMethod.getInstance());
		tv_wjqm.setMovementMethod(ScrollingMovementMethod.getInstance());
		tv_cbqk.setMovementMethod(ScrollingMovementMethod.getInstance());
		Bundle bundle=getIntent().getExtras();
		uid=bundle.getString("sid");
		if(LoginHelper.getLoginInfo()==null){
			StringUtil.tagetToLogin(NbswActivity.this);
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
				intent=new Intent(NbswActivity.this,ZwDetailActivity.class);
				bundle=new Bundle();
				bundle.putString("unid", unid);
				intent.putExtras(bundle);
				break;
			case R.id.tv_nbsw_submit:
				try {
					intent=new Intent(NbswActivity.this,HdxzActivity.class);
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
			case R.id.tv_nbsw_cklzjl:
				intent=new Intent(NbswActivity.this,CklzjlActivity.class);
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
				case R.id.tv_nbyj:
					tv_nbyj.setText(nbyj);
					if("NewSend".equals(StringUtil.nullOrEmptyToStr(result.getString("canEditId")))){
						UserData.tjlc.setFldbname("NewSend");
						StringUtil.edittype=BroadcastData.nbswnbyj;
						dialog.show();
					}
					break;
				case R.id.tv_ldps:
					tv_ldps.setText(ldps);
					if("Leader".equals(StringUtil.nullOrEmptyToStr(result.getString("canEditId")))){
						UserData.tjlc.setFldbname("Leader");
						StringUtil.edittype=BroadcastData.nbswldps;
						dialog.show();
					}
					break;
				case R.id.tv_wjqm:
					tv_wjqm.setText(wjqm);
					if("Reader".equals(StringUtil.nullOrEmptyToStr(result.getString("canEditId")))){
						UserData.tjlc.setFldbname("Reader");
						StringUtil.edittype=BroadcastData.nbswwjqm;
						dialog.show();
					}
					break;
				case R.id.tv_cbqk:
					tv_cbqk.setText(cbqk);
					if("Done".equals(StringUtil.nullOrEmptyToStr(result.getString("canEditId")))){
						UserData.tjlc.setFldbname("Done");
						StringUtil.edittype=BroadcastData.nbswcbqk;
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
					"sw");
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
					tv_lwdw.setText(StringUtil.nullOrEmptyToStr(result.getString("lwdw")));
					tv_wh.setText(StringUtil.nullOrEmptyToStr(result.getString("No1")));
					tv_lwbt.setText(StringUtil.nullOrEmptyToStr(result.getString("Subject")));
					tv_swbh.setText(StringUtil.nullOrEmptyToStr(result.getString("FileClassNo")));
					tv_lwrq.setText(StringUtil.nullOrEmptyToStr(result.getString("Timeldate")));
					unid=StringUtil.nullOrEmptyToStr(result.getString("unid"));
					UserData.tjlc.setUnid(unid);
					UserData.tjlc.setProcessid(StringUtil.nullOrEmptyToStr(result.getString("ProcessUNID")));
					UserData.tjlc.setSubject(StringUtil.nullOrEmptyToStr(result.getString("Subject")));
					JSONArray newSendOut=new JSONArray(result.getString("listNewSendOut"));
					if(newSendOut!=null&&newSendOut.length()!=0){
						setContent(newSendOut,tv_nbyj);
					}
					JSONArray leaderOut=new JSONArray(result.getString("listLeaderOut"));
					if(leaderOut!=null&&leaderOut.length()!=0){
						setContent(leaderOut,tv_ldps);
					}
					JSONArray readerOut=new JSONArray(result.getString("listReaderOut"));
					if(readerOut!=null&&readerOut.length()!=0){
						setContent(readerOut,tv_wjqm);
					}
					JSONArray doneOut=new JSONArray(result.getString("listDoneOut"));
					if(doneOut!=null&&doneOut.length()!=0){
						setContent(doneOut,tv_cbqk);
					}
					if(!"NewSend".equals(StringUtil.nullOrEmptyToStr(result.getString("canEditId")))){
						tv_nbyj.setBackgroundColor(getResources().getColor(R.color.editable));
					}
					if(!"Leader".equals(StringUtil.nullOrEmptyToStr(result.getString("canEditId")))){
						tv_ldps.setBackgroundColor(getResources().getColor(R.color.editable));
					}
					if(!"Reader".equals(StringUtil.nullOrEmptyToStr(result.getString("canEditId")))){
						tv_wjqm.setBackgroundColor(getResources().getColor(R.color.editable));
					}
					if(!"Done".equals(StringUtil.nullOrEmptyToStr(result.getString("canEditId")))){
						tv_cbqk.setBackgroundColor(getResources().getColor(R.color.editable));
					}
					nbyj=tv_nbyj.getText().toString();
					ldps=tv_ldps.getText().toString();
					wjqm=tv_wjqm.getText().toString();
					cbqk=tv_cbqk.getText().toString();
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
			StringUtil.tagetToLogin(NbswActivity.this);
			break;
		case R.id.titleBack:
            finish();
			break;
		}
	}
	
	
}
