package unicorn.withub.sxfyydbg.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import unicorn.withub.sxfyydbg.R;
import unicorn.withub.sxfyydbg.broadcastreceiver.BroadcastData;
import unicorn.withub.sxfyydbg.data.BaseConstant;
import unicorn.withub.sxfyydbg.data.LoginHelper;
import unicorn.withub.sxfyydbg.data.NormalRequestBuild;
import unicorn.withub.sxfyydbg.data.UserData;
import unicorn.withub.sxfyydbg.util.DataHelper;
import unicorn.withub.sxfyydbg.util.StringUtil;

public class XzhysActivity extends BaseActivity{
	private String string="会议室信息";
	private GridView gv_hys;
	private HysAdapter adapter;
	private JSONArray result;
	private BroadcastReceiver receiver=new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String action=intent.getAction();
			if(action.equals(BroadcastData.hysxx)){
				//tv_hysxx.setText(UserData.str_hysxx);
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hysxx_activity);
		initView(string);
		IntentFilter filter=new IntentFilter();
		filter.addAction(BroadcastData.action);
		filter.addAction(BroadcastData.fwdbcount);
		filter.addAction(BroadcastData.ggcount);
		filter.addAction(BroadcastData.qjdbcount);
		filter.addAction(BroadcastData.swdbcount);
		filter.addAction(BroadcastData.ycdbcount);
		filter.addAction(BroadcastData.hysxx);
		registerReceiver(receiver, filter);
		Bundle bundle=getIntent().getExtras();
		Log.i("Starttime", bundle.getString("start"));
		Log.i("endtime", bundle.getString("end"));
		if(LoginHelper.getLoginInfo()==null){
			StringUtil.tagetToLogin(XzhysActivity.this);
			return;
		}
		query(bundle.getString("start"),bundle.getString("end"));
	}
	@Override
	protected void initView(String title) {
		// TODO Auto-generated method stub
		super.initView(title);
		gv_hys=(GridView) findViewById(R.id.gv_hysxx);
		adapter=new HysAdapter(this);
		gv_hys.setAdapter(adapter);
		gv_hys.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				//Intent intent=new Intent(XzhysActivity.this, InputHyssqActivity.class);
				//startActivity(intent);
				if("0".equals(getTxt(position, "staus"))){
					UserData.str_hysxx=getTxt(position, "room_name")+"，"+"房号："+getTxt(position, "room_no")+""+
							"，"+"容纳人数"+getTxt(position, "capacity")+"人";
					UserData.room_name=getTxt(position, "room_name");
					UserData.room_no=getTxt(position, "room_no");
					UserData.ronm_floor=getTxt(position, "floor");
					UserData.capacity=getTxt(position, "capacity");
					UserData.door_no=getTxt(position, "door_no");
					Intent intent=new Intent(BroadcastData.hysxx);
					sendBroadcast(intent);
					finish();
				}else if("1".equals(getTxt(position, "staus"))){
					getToast1("该会议室已占用");
				}else if("2".equals(getTxt(position, "staus"))){
					getToast1("该会议室已停用");
				}
			}
		});
	}
	protected void query(String start,String end) {
		 dataHelper = DataHelper.getInstance();
		 Bundle bundle = new Bundle();
		 bundle.putString(BaseConstant.REQUEST_BUSI_CODE,
					"meetinfor");
		 bundle.putString("RequestlBuild",
					"NormalRequestBuild");

		 bundle.putString("parameters_start", start);
		 bundle.putString("parameters_end", end);
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
					result = new JSONArray(rs);
					Log.i("result", "result=="+result.toString());
					adapter.notifyDataSetInvalidated();
				} catch (JSONException e) {
					alert(null, "数据转换异常");
				}
			}else{
			}
		}else{
			alert("操作提示", getCodeMsg(bundle.getString("code"),bundle.getString("msg")));
		}
    }
	class HysAdapter extends BaseAdapter{
	    Context context;
		public HysAdapter(Context context) {
			super();
			this.context = context;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			if(result == null){
        		return 0;
        	}
            return result.length(); 
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
				convertView=LayoutInflater.from(context).inflate(R.layout.hysxx_item, null);
				holder=new ViewHolder();
				holder.re_hysxx_item=(RelativeLayout) convertView.findViewById(R.id.re_hysxx_item);
				holder.tv_hys_index=(TextView) convertView.findViewById(R.id.tv_hys_index);
				holder.tv_hysfh=(TextView) convertView.findViewById(R.id.tv_hysfh);
				holder.tv_hysrnrs=(TextView) convertView.findViewById(R.id.tv_hysrnrs);
				convertView.setTag(holder);
			}else{
				holder=(ViewHolder) convertView.getTag();
			}
			if("0".equals(getTxt(position, "staus"))){
				holder.re_hysxx_item.setBackgroundResource(R.color.hyskx);
			}else if("1".equals(getTxt(position, "staus"))){
				holder.re_hysxx_item.setBackgroundResource(R.color.hyssyz);
			}else if("2".equals(getTxt(position, "staus"))){
				holder.re_hysxx_item.setBackgroundResource(R.color.hysty);
			}
			holder.tv_hys_index.setText(getTxt(position, "room_name"));
			holder.tv_hysfh.setText("("+getTxt(position, "room_no")+")");
			holder.tv_hysrnrs.setText("容纳人数"+getTxt(position, "capacity")+"人");
			return convertView;
		}
		class ViewHolder{
			RelativeLayout re_hysxx_item;
			TextView tv_hys_index;
			TextView tv_hysfh;
			TextView tv_hysrnrs;
		}

	}
	private String getTxt(int position,String str){
    	try {
			return result.getJSONObject(position).getString(str);
		} catch (JSONException e) {
			return "";
		}
    }
	private void getToast1(String string){
		Toast.makeText(XzhysActivity.this, string, Toast.LENGTH_SHORT).show();
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
			StringUtil.tagetToLogin(XzhysActivity.this);
			break;
		case R.id.titleBack:
            finish();
			break;
		}
	}
	

}
