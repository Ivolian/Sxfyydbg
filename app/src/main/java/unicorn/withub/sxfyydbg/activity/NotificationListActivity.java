package unicorn.withub.sxfyydbg.activity;


import android.content.BroadcastReceiver;
import android.content.Context;
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
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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

/**公告列表页面*/
public class NotificationListActivity extends BaseActivity{
	private String string="通知公告列表";
	private ListView lv_notice_list;
	private NotificationListAdapter adapter;
	private JSONArray result;
	private int req_id;
	private int selectPosition;
	private TextView tv_yd;
	private TextView tv_wd;
	private FrameLayout buttommenu;
	private RelativeLayout rela_zwsj;
	private RelativeLayout rela_gglb;
	
	private List<JSONObject> list=new ArrayList<JSONObject>();
	private BroadcastReceiver receiver=new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notificationlist_activity);
		initView(string);
		addListener();
		IntentFilter filter=new IntentFilter();
		filter.addAction(BroadcastData.action);
		filter.addAction(BroadcastData.fwdbcount);
		filter.addAction(BroadcastData.ggcount);
		filter.addAction(BroadcastData.qjdbcount);
		filter.addAction(BroadcastData.swdbcount);
		filter.addAction(BroadcastData.ycdbcount);
		registerReceiver(receiver, filter);
		if(LoginHelper.getLoginInfo()==null){
			StringUtil.tagetToLogin(NotificationListActivity.this);
			return;
		}
		wdxx();
	}
	/**控件初始化*/
	@Override
	protected void initView(String title) {
		// TODO Auto-generated method stub
		super.initView(title);
		rela_zwsj=(RelativeLayout) findViewById(R.id.rela_zwsj);
		rela_gglb=(RelativeLayout) findViewById(R.id.rela_gglb);
		lv_notice_list=(ListView) findViewById(R.id.lv_notice_list);
		adapter=new NotificationListAdapter(NotificationListActivity.this);
		lv_notice_list.setAdapter(adapter);
		tv_wd=(TextView) findViewById(R.id.tv_wd);
		tv_yd=(TextView) findViewById(R.id.tv_yd);
		buttommenu=(FrameLayout) findViewById(R.id.notification_buttom);
		buttommenu.setVisibility(View.GONE);
	}
	
	/**控件监听*/
	void addListener(){
		lv_notice_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
                if(req_id==0){
                	req_id=1;
    			    deleteItem(getTxt(position, "ID"));
    			    selectPosition=position;
                }
				Intent intent=new Intent(NotificationListActivity.this, NotificationDetailActivity.class);
				Bundle bundle=new Bundle();
				bundle.putString("title", getTxt(position, "TITLE"));
				bundle.putString("content", getTxt(position, "content"));
				bundle.putString("username", getTxt(position, "USER_NAME"));
				bundle.putString("starttime", getTxt(position, "starttime"));
				bundle.putString("fjurl", getTxt(position, "fjurl"));
				bundle.putString("fjname", getTxt(position, "fjname"));
				intent.putExtras(bundle);
				startActivity(intent);
				Log.i("list=====", "list======"+list);
			}
		});
		tv_wd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				buttommenu.setVisibility(View.GONE);
				tv_wd.setBackgroundResource(R.color.dj);
				tv_yd.setBackgroundResource(R.color.wdj);
				req_id=0;
				wdxx();
			}
		});
		tv_yd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				buttommenu.setVisibility(View.VISIBLE);
				tv_wd.setBackgroundResource(R.color.wdj);
				tv_yd.setBackgroundResource(R.color.dj);
				req_id=2;
				ydxx();
			}
		});
	}
	private String getTxt(int position,String str){
    	try {
    		return list.get(position).getString(str);
		} catch (JSONException e) {
			return "";
		}
    }
	protected void wdxx() {
		 dataHelper = DataHelper.getInstance();
		 Bundle bundle = new Bundle();
		 bundle.putString(BaseConstant.REQUEST_BUSI_CODE,
					"cmsmsg");
		 bundle.putString("RequestlBuild",
					"NormalRequestBuild");

		 Log.i("loginData", "loginData="+LoginHelper.getLoginInfo());
		 bundle.putString("loginInfo", LoginHelper.getLoginInfo()+"");
		 sendRequest(bundle,new NormalRequestBuild(),"正在查询" );
	}
	protected void ydxx() {
		 dataHelper = DataHelper.getInstance();
		 Bundle bundle = new Bundle();
		 bundle.putString(BaseConstant.REQUEST_BUSI_CODE,
					"cmsmsged");
		 bundle.putString("RequestlBuild",
					"NormalRequestBuild");
		 bundle.putString("parameters_currentPage", currentPage+"");
		 bundle.putString("parameters_pageSize", pageSize+"");
		 Log.i("loginData", "loginData="+LoginHelper.getLoginInfo());
		 bundle.putString("loginInfo", LoginHelper.getLoginInfo()+"");
		 sendRequest(bundle,new NormalRequestBuild(),"正在查询" );
	}
	protected void deleteItem(String id) {
		 dataHelper = DataHelper.getInstance();
		 Bundle bundle = new Bundle();
		 bundle.putString(BaseConstant.REQUEST_BUSI_CODE,
					"readcmsmsg");
		 bundle.putString("RequestlBuild",
					"NormalRequestBuild");

		 Log.i("loginData", "loginData="+LoginHelper.getLoginInfo());
		 bundle.putString("loginInfo", LoginHelper.getLoginInfo()+"");
		 bundle.putString("parameters_msgId", id);
		 sendRequest(bundle,new NormalRequestBuild(),"正在查询" );
	}
	public void busiFinish(Bundle bundle) {
		if(bundle.get("code").equals("000000")){
		
			String rs = bundle.getString("parameter-result");
			Log.i("rs", "rs=="+rs);
			if(!rs.equals("") && !rs.equals("null")){
				if(req_id==0){
					try {
						result = new JSONArray(rs);
						if(result.length()==0){
							rela_gglb.setVisibility(View.GONE);
							rela_zwsj.setVisibility(View.VISIBLE);
							return;
						}else{
							rela_gglb.setVisibility(View.VISIBLE);
							rela_zwsj.setVisibility(View.GONE);
						}
						Log.i("result", "result=="+result.toString());
						getList(result);
						adapter.notifyDataSetInvalidated();
					} catch (JSONException e) {
						alert(null, "数据转换异常");
					}
				}else if(req_id==1){
					req_id=0;
					if("true".equals(rs)){
						Log.i("re====", "result++++==");
						list.remove(selectPosition);
						if(list.size()==0){
							rela_gglb.setVisibility(View.GONE);
							rela_zwsj.setVisibility(View.VISIBLE);
							return;
						}else{
							rela_gglb.setVisibility(View.VISIBLE);
							rela_zwsj.setVisibility(View.GONE);
						}
						UserData.gg_count=list.size();
						adapter.notifyDataSetInvalidated();
						Intent intent=new Intent(BroadcastData.ggcount);
						sendBroadcast(intent);
					}
				}else if(req_id==2){
					try {
						result = new JSONArray(rs);
						Log.i("result", "result=="+result.toString());
						if(result.length()==0){
							rela_gglb.setVisibility(View.GONE);
							rela_zwsj.setVisibility(View.VISIBLE);
							return;
						}else{
							rela_gglb.setVisibility(View.VISIBLE);
							rela_zwsj.setVisibility(View.GONE);
						}
						getList(result);
						adapter.notifyDataSetInvalidated();
					} catch (JSONException e) {
						alert(null, "数据转换异常");
					}
					String count = bundle.getString("parameter-count");
					Log.i("count", "count=="+count);
					rowCount=Long.parseLong(count);
					mCountPage = (rowCount+ (pageSize-1)  ) / pageSize;
				}
			}else{
				rela_gglb.setVisibility(View.GONE);
				rela_zwsj.setVisibility(View.VISIBLE);
			}
		}else{
			alert("操作提示", getCodeMsg(bundle.getString("code"),bundle.getString("msg")));
		}
    }
	private void getList(JSONArray result){
		list.clear();
		try {
			if(result!=null){
				for(int i=0;i<result.length();i++){
					list.add(result.getJSONObject(i));
				}
			}else{
			}
		} catch (JSONException e) {
			return ;
		}
	}
	class NotificationListAdapter extends BaseAdapter{
		Context context;
		LayoutInflater inflater;

		public NotificationListAdapter(Context context) {
			super();
			this.context = context;
			this.inflater=LayoutInflater.from(context);
		}
		

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			if(list == null){
        		return 0;
        	}
            return list.size();
		}

		@Override
		public String getItem(int position) {
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
				convertView=inflater.inflate(R.layout.notificationlist_item, null);
				holder=new ViewHolder();
				holder.tv_content=(TextView) convertView.findViewById(R.id.tv_notice_item);
				convertView.setTag(holder);
			}else{
				holder=(ViewHolder) convertView.getTag();
			}
			holder.tv_content.setText(getTxt(position));
			return convertView;
		}
		private String getTxt(int position){
        	try {
        		if("PRV_MSG".equals(result.getJSONObject(position).getString("TYPE"))){
        			return "(消息)"+getTitle(list.get(position).getString("TITLE"))+" "+list.get(position).getString("starttime");
        		}else{
        			return "(公告)"+getTitle(list.get(position).getString("TITLE"))+" "+list.get(position).getString("starttime");
        		}
			} catch (JSONException e) {
				return "";
			}
        }
		private String getTitle(String str){
			if(str!=null){
				if(str.length()<=11){
					return str;
				}else{
					return str.substring(0, 10)+"....";
				}
			}else{
				return "";
			}
		}
		/**内部类，用来进行ListView的优化*/
		class ViewHolder{
			TextView tv_content;
		}
		

	}
	public void prePage(View view){
		is_next=0;
		if(currentPage==1){
			Toast.makeText(NotificationListActivity.this, "已经是第一页", Toast.LENGTH_SHORT).show();
			return;
		}
		parsePage();
    	ydxx();
    	Log.i("currentPage", "currentPage="+currentPage);
    }
    
    public void nextPage(View view){
    	is_next=1;
    	if(currentPage==mCountPage){
    		Toast.makeText(NotificationListActivity.this, "已经是最后一页", Toast.LENGTH_SHORT).show();
    		return;
    	}
    	parsePage();
    	ydxx();	
    	Log.i("currentPage", "currentPage="+currentPage);
	}
    public void fresh(View view){
    	is_next=2;
    	ydxx();
    	Log.i("currentPage", "currentPage="+currentPage);
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
			StringUtil.tagetToLogin(NotificationListActivity.this);
			break;
		case R.id.titleBack:
            finish();
			break;
		}
	}

}
