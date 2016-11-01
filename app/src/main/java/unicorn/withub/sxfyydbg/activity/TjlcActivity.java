package unicorn.withub.sxfyydbg.activity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

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

public class TjlcActivity extends BaseActivity{
	private String string="提交流程";
	private Spinner sp_bmxz;
	private TextView tv_submit;
	private ListView lv_ryxz;
	private ListView lv_xzzxr;
	private JSONArray bm_arr;
	private JSONArray ry_arr;
	private JSONArray yzry;
	private List<String> xzry_list=new ArrayList<String>();
	private List<String> yzry_list=new ArrayList<String>();
	private List<String> bm_list=new ArrayList<String>();
	private SpAdapter bm_adapter;
	private DqbmAdapter ryxz_adapter;
	private XzryAdapter xzry_adapter;
	private int selectPosition=-1;
	private int req_id;
	private BroadcastReceiver receiver=new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
//			String action=intent.getAction();
//			if(BroadcastData.finish.equals(action)){
//				finish();
//			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nbfwtjlc_activity);
		initView(string);
		IntentFilter filter=new IntentFilter();
		filter.addAction(BroadcastData.finish);
		registerReceiver(receiver, filter);
	}
	@Override
	protected void initView(String title) {
		// TODO Auto-generated method stub
		super.initView(title);
		sp_bmxz=(Spinner) findViewById(R.id.sp_bmxz);
		tv_submit=(TextView) findViewById(R.id.tv_submit);
		lv_ryxz=(ListView) findViewById(R.id.lv_ryxz_list);
		lv_xzzxr=(ListView) findViewById(R.id.lv_xzzxr_list);
		ryxz_adapter=new DqbmAdapter(this);
		xzry_adapter=new XzryAdapter(this);
		lv_ryxz.setAdapter(ryxz_adapter);
		lv_xzzxr.setAdapter(xzry_adapter);
		Bundle bundle=getIntent().getExtras();
		setYzryList(bundle);
		tv_submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(UserData.tjlc.getSelUsers()==null||"".equals(UserData.tjlc.getSelUsers())){
					Toast.makeText(TjlcActivity.this, "请选择处理用户", Toast.LENGTH_SHORT).show();
					return;
				}
				processSubmit();
			}
		});
		sp_bmxz.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Log.i("queryPerson", "queryPerson");
				if(bm_arr!=null){
					try {
						Log.i("queryPerson==", "queryPerson==");
						queryPerson(bm_arr.getJSONObject(position).getString("DEPART_ID"));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						alert(null, "数据转换异常");
					}
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		lv_ryxz.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				try {
					for(int i=0;i<xzry_list.size();i++){
						if(ry_arr.getJSONObject(position).getString("USER_NAME").equals(xzry_list.get(i))){
							return;
						}
					}
					selectPosition=-1;
					xzry_list.add(ry_arr.getJSONObject(position).getString("USER_NAME"));
					xzry_adapter.notifyDataSetInvalidated();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					alert(null, "数据转换异常");
				}
			}
		});
		lv_xzzxr.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				selectPosition=position;
				UserData.tjlc.setSelUsers(xzry_list.get(position));
				xzry_adapter.notifyDataSetInvalidated();
			}
		});
		lv_xzzxr.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				xzry_list.remove(position);
				selectPosition=-1;
				xzry_adapter.notifyDataSetInvalidated();
				return true;
			}
		});
		//Bundle bundle=getIntent().getExtras();
		//checkProcess(bundle.getString("unid"), bundle.getString("ProcessUNID"));
		if(LoginHelper.getLoginInfo()==null){
			StringUtil.tagetToLogin(TjlcActivity.this);
			return;
		}
		queryDept();
	}
	private void setYzryList(Bundle bundle){
		try {
			yzry=new JSONArray(bundle.getString("yzry"));
			for(int i=0;i<yzry.length();i++){
				yzry_list.add(yzry.getString(i));
			}
			for(int i=0;i<yzry_list.size();i++){
				if(yzry_list.get(i)!=null&&!"".equals(yzry_list.get(i))){
					xzry_list.add(yzry_list.get(i));
				}
			}
			xzry_adapter.notifyDataSetInvalidated();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			alert(null, "数据转换异常");
		}
	}
	protected void queryDept() {
		 dataHelper = DataHelper.getInstance();
		 Bundle bundle = new Bundle();
		 bundle.putString(BaseConstant.REQUEST_BUSI_CODE,
					"alldepts");
		 bundle.putString("RequestlBuild",
					"NormalRequestBuild");
		 
		 Log.i("loginData", "loginData="+LoginHelper.getLoginInfo());
		 bundle.putString("loginInfo", LoginHelper.getLoginInfo()+"");
		 sendRequest(bundle,new NormalRequestBuild(),"正在查询" );
	}
	protected void processSubmit() {
		req_id=2;
		 dataHelper = DataHelper.getInstance();
		 Bundle bundle = new Bundle();
		 bundle.putString(BaseConstant.REQUEST_BUSI_CODE,
					"processsubmit");
		 bundle.putString("RequestlBuild",
					"NormalRequestBuild");
		 bundle.putString("parameters_unid", UserData.tjlc.getUnid());
		 bundle.putString("parameters_processid", UserData.tjlc.getProcessid());
		 bundle.putString("parameters_subject", UserData.tjlc.getSubject());
		 bundle.putString("parameters_currentNodeId", UserData.tjlc.getCurrentNodeId());
		 bundle.putString("parameters_targetNodeId", UserData.tjlc.getTargetNodeId());
		 bundle.putString("parameters_selUsers", UserData.tjlc.getSelUsers());
		 bundle.putString("parameters_fldtrace", UserData.tjlc.getFldtrace());
		 bundle.putString("parameters_fldbname", UserData.tjlc.getFldbname());
		 Log.i("db_type", "db_type="+UserData.db_type);
		 if(UserData.db_type==1){
			 bundle.putString("parameters_dbpath", "linkey_fw.nsf");
		 }else if(UserData.db_type==2){
			 bundle.putString("parameters_dbpath", "linkey_sw.nsf");
		 }else if(UserData.db_type==3){
			 bundle.putString("parameters_dbpath", "leavenew.nsf");
		 }else if(UserData.db_type==4){
			 bundle.putString("parameters_dbpath", "ygout.nsf");
		 }else if(UserData.db_type==5){
			 bundle.putString("parameters_dbpath", "car.nsf");
		 }
//		 Log.i("UserData.tjlc", "UserData.tjlc="+UserData.tjlc.toString());
//		 Log.i("loginData", "loginData="+LoginHelper.getLoginInfo());
		 bundle.putString("loginInfo", LoginHelper.getLoginInfo()+"");
		 sendRequest(bundle,new NormalRequestBuild(),"正在提交..." );
	}
	protected void queryPerson(String deptId) {
		req_id=1;
		 dataHelper = DataHelper.getInstance();
		 Bundle bundle = new Bundle();
		 bundle.putString(BaseConstant.REQUEST_BUSI_CODE,
					"getusersBydeptid");
		 bundle.putString("RequestlBuild",
					"NormalRequestBuild");
		 bundle.putString("parameters_departId", deptId);
		 Log.i("loginData", "loginData="+LoginHelper.getLoginInfo());
		 bundle.putString("loginInfo", LoginHelper.getLoginInfo()+"");
		 sendRequest(bundle,new NormalRequestBuild(),"正在查询" );
	}
	public void busiFinish(Bundle bundle) {
		if(bundle.get("code").equals("000000")){
		
			String rs = bundle.getString("parameter-result");
			Log.i("rs", "rs=="+rs);
			if(!rs.equals("") && !rs.equals("null")){
				if(req_id==0){
					try {
						bm_arr=new JSONArray(rs);
						Log.i("bm_arr", "bm_arr="+bm_arr.toString());
						setList(bm_arr);
						bm_adapter=new SpAdapter(this,bm_list);
						sp_bmxz.setAdapter(bm_adapter);
						//bm_adapter.notifyDataSetInvalidated();
						//setDataAdapter(bm_arr, bm_adapter, "DEPART_NAME");
						//setDataBmAdapter(bm_arr);
					} catch (JSONException e) {
						alert(null, "数据转换异常");
					}
				}else if(req_id==1){
					try {
						ry_arr=new JSONArray(rs);
						Log.i("ry_arr", "ry_arr="+ry_arr.toString());
						//setDataAdapter(ry_arr, ry_adapter, "USER_NAME");
						ryxz_adapter.notifyDataSetInvalidated();
					} catch (JSONException e) {
						alert(null, "数据转换异常");
					}
				}else if(req_id==2){
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
			finish();
		}
	};
	private void setList(JSONArray arr){
		for(int i=0;i<arr.length();i++){
			try {
				bm_list.add(bm_arr.getJSONObject(i).getString("DEPART_NAME"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				alert(null, "数据转换异常");
			}
		}
	}
//	private void setDataAdapter(JSONArray arr,ArrayAdapter<String> adapter,String str){
//		try {
//			if(arr!=null){
//				for(int i=0;i<arr.length();i++){
//					adapter.add(StringUtil.nullOrEmptyToStr(arr.getJSONObject(i).getString(str)));
//				}
//				adapter.notifyDataSetInvalidated();
//			}
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			alert(null, "数据转换异常");
//		}
//	}
//	private void setDataBmAdapter(JSONArray arr){
//		try {
//			if(arr!=null){
//				for(int i=0;i<arr.length();i++){
//					list.add(arr.getJSONObject(i).getString("DEPART_NAME"));
//				}
//				bm_adapter.notifyDataSetInvalidated();
//			}
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			alert(null, "数据转换异常");
//		}
//	}
	class DqbmAdapter extends BaseAdapter{
        Context context;
		public DqbmAdapter(Context context) {
			super();
			this.context = context;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			if(ry_arr== null){
        		return 0;
        	}
            return ry_arr.length(); 
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
				convertView=LayoutInflater.from(context).inflate(R.layout.ryxz_list_item, null);
				holder=new ViewHolder();
				holder.tv_dbsx_item=(TextView) convertView.findViewById(R.id.tv_bmry);
				
				convertView.setTag(holder);
			}else{
				holder=(ViewHolder) convertView.getTag();
			}
			holder.tv_dbsx_item.setBackgroundResource(R.drawable.dxzry_item);
			holder.tv_dbsx_item.setText(getTxt(ry_arr,position,"USER_NAME"));
			return convertView;
		}
		
		
	}
	private String getTxt(JSONArray arr,int position,String str){
    	try {
			return arr.getJSONObject(position).getString(str);
		} catch (JSONException e) {
			return "";
		}
    }
	class XzryAdapter extends BaseAdapter{
        Context context;
		public XzryAdapter(Context context) {
			super();
			this.context = context;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			if(xzry_list== null){
        		return 0;
        	}
            return xzry_list.size(); 
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
				convertView=LayoutInflater.from(context).inflate(R.layout.ryxz_list_item, null);
				holder=new ViewHolder();
				holder.tv_dbsx_item=(TextView) convertView.findViewById(R.id.tv_bmry);
				
				convertView.setTag(holder);
			}else{
				holder=(ViewHolder) convertView.getTag();
			}
			if(position==selectPosition){
				holder.tv_dbsx_item.setBackgroundResource(R.color.tjlc_xzry);
			}else{
				holder.tv_dbsx_item.setBackgroundResource(R.drawable.dxzry_item);
			}
			holder.tv_dbsx_item.setText(xzry_list.get(position));
			return convertView;
		}
	}
	class ViewHolder{
		TextView tv_dbsx_item;
	}
//	private String getTxt(int position,String str){
//		try {
//			return xzry_list.get(position).getString(str);
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			alert(null, "数据转换异常");
//			return "";
//		}
//	}
	class SpAdapter extends BaseAdapter{
        Context context;
        List<String> list;
		public SpAdapter(Context context,List<String> list) {
			super();
			this.context = context;
			this.list=list;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
//			if(bm_arr== null){
//        		return 0;
//        	}
//            return bm_arr.length(); 
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
			SpViewHolder holder=null;
			if(convertView==null){
				convertView=LayoutInflater.from(context).inflate(R.layout.spinner_item, null);
				holder=new SpViewHolder();
				holder.tv_ldcj_item=(TextView) convertView.findViewById(R.id.tv_sp_item);
				convertView.setTag(holder);
			}else{
				holder=(SpViewHolder) convertView.getTag();
			}
			//holder.tv_ldcj_item.setText(getTxt(bm_arr, position, "DEPART_NAME"));
			holder.tv_ldcj_item.setText(list.get(position));
			return convertView;
		}
		
	}
	class SpViewHolder{
		TextView tv_ldcj_item;
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
			StringUtil.tagetToLogin(TjlcActivity.this);
			break;
		case R.id.titleBack:
            finish();
			break;
		}
	}

}
