package unicorn.withub.sxfyydbg.activity;

import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
import unicorn.withub.sxfyydbg.util.DateUtil;
import unicorn.withub.sxfyydbg.util.StringUtil;

public class QjsqActivity extends BaseActivity{
	private String string="请假审批";
	private TextView tv_submit;
	private TextView tv_cklzjl;
	private TextView tv_qjrq;
	private TextView tv_qjbm;
	private TextView tv_qjrxm;
	private TextView tv_qjrzwxx;
	private TextView  tv_qjzl;
	private EditText et_qjsy;
	private TextView tv_bmldyj;
	private TextView tv_yldyj;
	private TextView tv_rscyj;
	private String bmldyj;
	private String yldyj;
	private String rscyj;
	private EditText et_bz;
	private TextView tv_qjks;
	private TextView tv_qjjs;
	private String date_start;
	private String date_end;
	private JSONObject result;
	private String unid;
	private String uid;
	private DatePickerDialog.OnDateSetListener onDateStartListener = new DatePickerDialog.OnDateSetListener() {  
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,  
                int dayOfMonth) {  
            Log.d("test", ""+year+"年"+(monthOfYear+1)+"月"+dayOfMonth+"日");
            date_start=""+year+"-"+(monthOfYear+1)+"-"+dayOfMonth;
            tv_qjks.setText(date_start);
        }
    };
    private DatePickerDialog.OnDateSetListener onDateEndListener = new DatePickerDialog.OnDateSetListener() {  
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,  
                int dayOfMonth) {  
            Log.d("test", ""+year+"年"+(monthOfYear+1)+"月"+dayOfMonth+"日");
            date_end=""+year+"-"+(monthOfYear+1)+"-"+dayOfMonth;
            tv_qjjs.setText(date_end);
            if(!DateUtil.isDate(date_start,date_end)){
				Toast.makeText(QjsqActivity.this, "结束日期不能再开始日期之前", Toast.LENGTH_SHORT).show();
				tv_qjjs.setText("");
				return;
			}
        }
    }; 
    private BroadcastReceiver receiver=new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String action=intent.getAction();
			if(BroadcastData.qjsqbmldyj.equals(action)){
				tv_bmldyj.setText(bmldyj+ StringUtil.editcontent+"\n");
				UserData.tjlc.setFldtrace(tv_bmldyj.getText().toString());
			}else if(BroadcastData.qjsqyldyj.equals(action)){
				tv_yldyj.setText(yldyj+StringUtil.editcontent+"\n");
				UserData.tjlc.setFldtrace(tv_yldyj.getText().toString());
			}else if(BroadcastData.qjsqrscyj.equals(action)){
				tv_rscyj.setText(rscyj+StringUtil.editcontent+"\n");
				UserData.tjlc.setFldtrace(tv_rscyj.getText().toString());
			}else if(BroadcastData.finish.equals(action)){
				finish();
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.qjsq_activity);
		initView(string);
		IntentFilter filter=new IntentFilter();
		filter.addAction(BroadcastData.qjsqbmldyj);
		filter.addAction(BroadcastData.qjsqyldyj);
		filter.addAction(BroadcastData.qjsqrscyj);
		filter.addAction(BroadcastData.finish);
		registerReceiver(receiver, filter);
	}
	@Override
	protected void initView(String title) {
		// TODO Auto-generated method stub
		super.initView(title);
		UserData.tjlc.setFldbname("");
		UserData.tjlc.setFldtrace("");
		tv_submit=(TextView) findViewById(R.id.tv_qjsq_submit);
		//tv_back=(TextView) findViewById(R.id.tv_qjsq_back);
		tv_cklzjl=(TextView) findViewById(R.id.tv_qjsq_cklzjl);
		tv_submit.setOnClickListener(listener2);
		tv_cklzjl.setOnClickListener(listener2);
		//tv_back.setOnClickListener(listener);
		tv_qjrq=(TextView) findViewById(R.id.tv_qjrq);
		tv_qjbm=(TextView) findViewById(R.id.tv_qjbm);
		tv_qjrxm=(TextView) findViewById(R.id.tv_qjrxm);
		tv_qjrzwxx=(TextView) findViewById(R.id.tv_qjrzwxx);
		tv_qjzl=(TextView) findViewById(R.id.tv_qjzl);
		et_qjsy=(EditText) findViewById(R.id.et_qjsy);
		tv_bmldyj=(TextView) findViewById(R.id.tv_bmldyj);
		
		tv_yldyj=(TextView) findViewById(R.id.tv_yldyj);
		tv_rscyj=(TextView) findViewById(R.id.tv_rscyj);
		tv_bmldyj.setOnClickListener(listener);
		tv_yldyj.setOnClickListener(listener);
		tv_rscyj.setOnClickListener(listener);
		et_bz=(EditText) findViewById(R.id.et_bz);
		tv_bmldyj.setMovementMethod(ScrollingMovementMethod.getInstance());
		tv_yldyj.setMovementMethod(ScrollingMovementMethod.getInstance());
		tv_rscyj.setMovementMethod(ScrollingMovementMethod.getInstance());
		et_bz.setMovementMethod(ScrollingMovementMethod.getInstance());
		tv_qjks=(TextView) findViewById(R.id.tv_qjks);
		//tv_qjks.setOnClickListener(listener);
		tv_qjjs=(TextView) findViewById(R.id.tv_qjjs);
		//tv_qjjs.setOnClickListener(listener);
		Bundle bundle=getIntent().getExtras();
		Log.i("uid="+bundle.getString("uid"), "sid="+bundle.getString("sid"));
		uid=bundle.getString("sid");
		if(LoginHelper.getLoginInfo()==null){
			StringUtil.tagetToLogin(QjsqActivity.this);
			return;
		}
		query(bundle.getString("uid"), bundle.getString("sid"));
	}

	OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int id = v.getId();
			try {
				switch (id) {
				case R.id.tv_bmldyj:
					tv_bmldyj.setText(bmldyj);
					if("P_Dept".equals(StringUtil.nullOrEmptyToStr(result.getString("canEditId")))){
						UserData.tjlc.setFldbname("Dept");
						StringUtil.edittype=BroadcastData.qjsqbmldyj;
						dialog.show();
					}
					break;
				case R.id.tv_yldyj:
					tv_yldyj.setText(yldyj);
					if("P_Office".equals(StringUtil.nullOrEmptyToStr(result.getString("canEditId")))){
						UserData.tjlc.setFldbname("Office");
						StringUtil.edittype=BroadcastData.qjsqyldyj;
						dialog.show();
					}
					break;
				case R.id.tv_rscyj:
					tv_rscyj.setText(rscyj);
					if("P_Services".equals(StringUtil.nullOrEmptyToStr(result.getString("canEditId")))){
						UserData.tjlc.setFldbname("Services");
						StringUtil.edittype=BroadcastData.qjsqrscyj;
						dialog.show();
					}
					break;
				case R.id.tv_qjks:
					DateUtil.getDialog(QjsqActivity.this, onDateStartListener)
							.show();
					break;
				case R.id.tv_qjjs:
					DateUtil.getDialog(QjsqActivity.this, onDateEndListener).show();
					break;
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				alert(null, "数据转换异常");
			}
		}
	};
	OnClickListener listener2 = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int id=v.getId();
			Intent intent=null;
			Bundle bundle=null;
			switch (id) {
			case R.id.tv_qjsq_submit:
				try {
					intent=new Intent(QjsqActivity.this,HdxzActivity.class);
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
			case R.id.tv_qjsq_cklzjl:
				intent=new Intent(QjsqActivity.this,CklzjlActivity.class);
				bundle=new Bundle();
				bundle.putString("uid", uid);
				intent.putExtras(bundle);
				break;
			}
			startActivity(intent);
		}
	};
	protected void query(String uid,String sid) {
		 dataHelper = DataHelper.getInstance();
		 Bundle bundle = new Bundle();
		 bundle.putString(BaseConstant.REQUEST_BUSI_CODE,
					"qjapply");
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
					tv_qjrq.setText(StringUtil.nullOrEmptyToStr(result.getString("stDate")));
				    tv_qjbm.setText(StringUtil.nullOrEmptyToStr(result.getString("deptName")));
					tv_qjrxm.setText(StringUtil.nullOrEmptyToStr(result.getString("userName")));
					tv_qjrzwxx.setText(StringUtil.nullOrEmptyToStr(result.getString("job")));
					tv_qjks.setText(StringUtil.nullOrEmptyToStr(result.getString("startdate")));
					tv_qjjs.setText(StringUtil.nullOrEmptyToStr(result.getString("enddate")));
					tv_qjzl.setText(StringUtil.nullOrEmptyToStr(result.getString("typeName")));
					et_qjsy.setText(StringUtil.nullOrEmptyToStr(result.getString("shiyou")));
					et_bz.setText(StringUtil.nullOrEmptyToStr(result.getString("rtmarket")));
					unid=StringUtil.nullOrEmptyToStr(result.getString("unid"));
					UserData.tjlc.setUnid(unid);
					UserData.tjlc.setProcessid(StringUtil.nullOrEmptyToStr(result.getString("processUNID")));
					UserData.tjlc.setSubject(StringUtil.nullOrEmptyToStr(result.getString("subject")));
					JSONObject traces=new JSONObject(result.getString("traces"));
					if(traces!=null){
						JSONArray bmlist=new JSONArray(traces.getString("Dept"));
						if(bmlist!=null&&bmlist.length()!=0){
							setContent(bmlist,tv_bmldyj);
						}
						JSONArray yldlist=new JSONArray(traces.getString("Office"));
						if(yldlist!=null&&yldlist.length()!=0){
							setContent(yldlist,tv_yldyj);
						}
						JSONArray rsclist=new JSONArray(traces.getString("Services"));
						if(rsclist!=null&&rsclist.length()!=0){
							setContent(rsclist,tv_rscyj);
						}
					}
					if(!"P_Dept".equals(StringUtil.nullOrEmptyToStr(result.getString("canEditId")))){
						tv_bmldyj.setBackgroundColor(getResources().getColor(R.color.editable));
					}
					if(!"P_Office".equals(StringUtil.nullOrEmptyToStr(result.getString("canEditId")))){
						tv_yldyj.setBackgroundColor(getResources().getColor(R.color.editable));
					}
					if(!"P_Services".equals(StringUtil.nullOrEmptyToStr(result.getString("canEditId")))){
						tv_rscyj.setBackgroundColor(getResources().getColor(R.color.editable));
					}
					bmldyj=tv_bmldyj.getText().toString();
					yldyj=tv_yldyj.getText().toString();
					rscyj=tv_rscyj.getText().toString();
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
			StringUtil.tagetToLogin(QjsqActivity.this);
			break;
		case R.id.titleBack:
            finish();
			break;
		}
	}

	

}
