package unicorn.withub.sxfyydbg.activity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import unicorn.withub.sxfyydbg.R;
import unicorn.withub.sxfyydbg.broadcastreceiver.BroadcastData;
import unicorn.withub.sxfyydbg.data.BaseConstant;
import unicorn.withub.sxfyydbg.data.LoginHelper;
import unicorn.withub.sxfyydbg.data.NormalRequestBuild;
import unicorn.withub.sxfyydbg.data.UserData;
import unicorn.withub.sxfyydbg.util.DataHelper;
import unicorn.withub.sxfyydbg.util.StringUtil;

public class HdxzActivity extends BaseActivity{
	private String string="下一流程选择";
	private ListView lv_hdxz_list;
	private TextView tv_zwkyhd;
	private JSONArray result;
	private HdxzAdapter adapter;
	private JSONArray yzry;
	private Bundle bundle;
	private List<JSONObject> list=new ArrayList<JSONObject>();
	private int req_id;
	private BroadcastReceiver receiver=new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String action=intent.getAction();
			if(BroadcastData.finish.equals(action)){
				finish();
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hdxz_activity);
		initView(string);
		IntentFilter filter=new IntentFilter();
		filter.addAction(BroadcastData.finish);
		registerReceiver(receiver, filter);
	}
	@Override
	protected void initView(String title) {
		// TODO Auto-generated method stub
		super.initView(title);
		tv_zwkyhd=(TextView) findViewById(R.id.tv_zwkyhd);
		lv_hdxz_list=(ListView) findViewById(R.id.lv_hdxz_list);
		adapter=new HdxzAdapter(this);
		lv_hdxz_list.setAdapter(adapter);
		bundle=getIntent().getExtras();
		tv_zwkyhd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				processSubmit();
			}
		});
		lv_hdxz_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				try {
					UserData.tjlc.setTargetNodeId(list.get(position).getString("id"));
					nextProcessuser(bundle.getString("unid"), bundle.getString("ProcessUNID"), list.get(position).getString("id"));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					alert(null, "数据转换异常");
				}
			}
		});
		if(LoginHelper.getLoginInfo()==null){
			StringUtil.tagetToLogin(HdxzActivity.this);
			return;
		}
		checkProcess(bundle.getString("unid"), bundle.getString("ProcessUNID"));
	}

	protected void checkProcess(String unid, String processUNID) {
		dataHelper = DataHelper.getInstance();
		Bundle bundle = new Bundle();
		bundle.putString(BaseConstant.REQUEST_BUSI_CODE, "processnode");
		bundle.putString("RequestlBuild", "NormalRequestBuild");
		bundle.putString("parameters_unid", unid);
		bundle.putString("parameters_processid", processUNID);
		Log.i("unid", "unid=" + unid);
		Log.i("processUNID", "processUNID=" + processUNID);
		Log.i("loginData", "loginData=" + LoginHelper.getLoginInfo());
		bundle.putString("loginInfo", LoginHelper.getLoginInfo() + "");
		sendRequest(bundle, new NormalRequestBuild(), "正在查询");
	}

	protected void nextProcessuser(String unid, String processUNID,
			String nodeid) {
		req_id = 1;
		dataHelper = DataHelper.getInstance();
		Bundle bundle = new Bundle();
		bundle.putString(BaseConstant.REQUEST_BUSI_CODE, "nextprocessuser");
		bundle.putString("RequestlBuild", "NormalRequestBuild");
		bundle.putString("parameters_unid", unid);
		bundle.putString("parameters_processid", processUNID);
		bundle.putString("parameters_nodeid", nodeid);
		Log.i("loginData", "loginData=" + LoginHelper.getLoginInfo());
		bundle.putString("loginInfo", LoginHelper.getLoginInfo() + "");
		sendRequest(bundle, new NormalRequestBuild(), "正在查询");
	}

	protected void processSubmit() {
		req_id = 2;
		dataHelper = DataHelper.getInstance();
		Bundle bundle = new Bundle();
		bundle.putString(BaseConstant.REQUEST_BUSI_CODE, "processsubmit");
		bundle.putString("RequestlBuild", "NormalRequestBuild");
		bundle.putString("parameters_unid", UserData.tjlc.getUnid());
		bundle.putString("parameters_processid", UserData.tjlc.getProcessid());
		bundle.putString("parameters_subject", UserData.tjlc.getSubject());
		bundle.putString("parameters_currentNodeId",
				UserData.tjlc.getCurrentNodeId());
		bundle.putString("parameters_targetNodeId", "");
		bundle.putString("parameters_selUsers", "");
		bundle.putString("parameters_fldtrace", UserData.tjlc.getFldtrace());
		bundle.putString("parameters_fldbname", UserData.tjlc.getFldbname());
		if (UserData.db_type == 1) {
			bundle.putString("parameters_dbpath", "linkey_fw.nsf");
		} else if (UserData.db_type == 2) {
			bundle.putString("parameters_dbpath", "linkey_sw.nsf");
		} else if (UserData.db_type == 3) {
			bundle.putString("parameters_dbpath", "leavenew.nsf");
		} else if (UserData.db_type == 4) {
			bundle.putString("parameters_dbpath", "ygout.nsf");
		} else if (UserData.db_type == 5) {
			bundle.putString("parameters_dbpath", "car.nsf");
		}
		Log.i("UserData.tjlc", "UserData.tjlc=" + UserData.tjlc.toString());
		Log.i("loginData", "loginData=" + LoginHelper.getLoginInfo());
		bundle.putString("loginInfo", LoginHelper.getLoginInfo() + "");
		sendRequest(bundle, new NormalRequestBuild(), "正在查询");
	}
	public void busiFinish(Bundle bundle) {
		if(bundle.get("code").equals("000000")){
		
			String rs = bundle.getString("parameter-result");
			Log.i("rs", "rs=="+rs);
			if(!rs.equals("") && !rs.equals("null")){
				if(req_id==0){
					try {
						result=new JSONArray(rs);
						Log.i("result", "result="+result.toString());
						setDataList();
						if(list.size()==0){
							tv_zwkyhd.setVisibility(View.VISIBLE);
							lv_hdxz_list.setVisibility(View.GONE);
							return;
						}
						adapter.notifyDataSetInvalidated();
					} catch (JSONException e) {
						alert(null, "数据转换异常");
					}
				}else if(req_id==1){
					try {
						yzry=new JSONArray(rs);
						Log.i("yzry", "yzry="+yzry.toString());
						Intent intent=new Intent(HdxzActivity.this, TjlcActivity.class);
						Bundle bundle2=new Bundle();
						bundle2.putString("yzry", rs);
						intent.putExtras(bundle2);
						startActivity(intent);
					} catch (JSONException e) {
						alert(null, "数据转换异常");
					}
				}
				else if(req_id==2){
					UserData.tjlc.setSelUsers("");
					UserData.tjlc.setFldbname("");
					UserData.tjlc.setFldtrace("");
					if("true".equals(rs)){
						getAlertDialog();
					}else{
						alert(null, "提交失败");
					}
				}
			}
		}else{
			alert("操作提示", getCodeMsg(bundle.getString("code"),bundle.getString("msg")));
		}
    }
	private void setDataList(){
		try {
			for(int i=0;i<result.length();i++){
				if("false".equals(result.getJSONObject(i).getString("current"))){
					list.add(result.getJSONObject(i));
				}else{
					UserData.tjlc.setCurrentNodeId(result.getJSONObject(i).getString("id"));
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			alert(null, "数据转换异常");
		}
	}
	private void getAlertDialog(){
		AlertDialog.Builder builder=new AlertDialog.Builder(this);
		builder.setTitle("提示");
		builder.setMessage("提交成功");
		builder.setNeutralButton("确定",diaListener);
		builder.create().show();		
	}
	DialogInterface.OnClickListener diaListener=new DialogInterface.OnClickListener() {
		
		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			Intent intent=new Intent(BroadcastData.finish);
			sendBroadcast(intent);
		}
	};
	class HdxzAdapter extends BaseAdapter{
        Context context;
		public HdxzAdapter(Context context) {
			super();
			this.context = context;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			
            return list.size(); 
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder=null;
			if(convertView==null){
				convertView=LayoutInflater.from(context).inflate(R.layout.dbsx_list_item, null);
				holder=new ViewHolder();
				holder.tv_dbsx_item=(TextView) convertView.findViewById(R.id.tv_dbsx);
				
				convertView.setTag(holder);
			}else{
				holder=(ViewHolder) convertView.getTag();
			}
			holder.tv_dbsx_item.setText(Html.fromHtml(getTxt(position,"name")));
			return convertView;
		}
		
		
	}
	
	private String getTxt(int position,String str){
    	try {
			return list.get(position).getString(str);
		} catch (JSONException e) {
			return "";
		}
    }
	class ViewHolder{
		TextView tv_dbsx_item;
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
			StringUtil.tagetToLogin(HdxzActivity.this);
			break;
		case R.id.titleBack:
            finish();
			break;
		}
	}

}
