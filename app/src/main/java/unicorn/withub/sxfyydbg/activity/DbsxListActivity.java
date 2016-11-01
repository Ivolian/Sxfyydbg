package unicorn.withub.sxfyydbg.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import unicorn.withub.sxfyydbg.util.DataHelper;
import unicorn.withub.sxfyydbg.util.StringUtil;

public class DbsxListActivity extends BaseActivity{
	private String type="";
	private ListView lv_dbsx;
	private DbsxAdapter adapter;
	private JSONArray result;
	private List<JSONObject> list=new ArrayList<JSONObject>();
	private RelativeLayout rela_zwsj;
	private RelativeLayout rela_dblb;
	private BroadcastReceiver receiver=new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String action=intent.getAction();
			if(BroadcastData.finish.equals(action)){
				query();
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		try {
			setContentView(R.layout.dbsxlist);
			Bundle bundle=getIntent().getExtras();
			type=bundle.getString("type");
			initView(bundle.getString("title"));
			IntentFilter filter=new IntentFilter();
			filter.addAction(BroadcastData.action);
			filter.addAction(BroadcastData.fwdbcount);
			filter.addAction(BroadcastData.ggcount);
			filter.addAction(BroadcastData.qjdbcount);
			filter.addAction(BroadcastData.swdbcount);
			filter.addAction(BroadcastData.ycdbcount);
			filter.addAction(BroadcastData.finish);
			filter.addAction(BroadcastData.hysxx);
			registerReceiver(receiver, filter);
			if(LoginHelper.getLoginInfo()==null){
				StringUtil.tagetToLogin(DbsxListActivity.this);
				return;
			}
			query();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	@Override
	protected void initView(String title) {
		// TODO Auto-generated method stub
		super.initView(title);
		rela_zwsj=(RelativeLayout) findViewById(R.id.rela_zwsj);
		rela_dblb=(RelativeLayout) findViewById(R.id.rela_dblb);
		lv_dbsx=(ListView) findViewById(R.id.lv_dbsx_list);
		adapter=new DbsxAdapter(this);
		lv_dbsx.setAdapter(adapter);
		lv_dbsx.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent=null;
				if("2".equals(type)){
					intent=new Intent(DbsxListActivity.this,QjsqActivity.class);
					Bundle bundle=new Bundle();
					bundle.putString("uid", getTxt(position,"uid"));
					bundle.putString("sid", getTxt(position,"sid"));
					intent.putExtras(bundle);
					startActivity(intent);
				}else if("3".equals(type)){
					intent=new Intent(DbsxListActivity.this,GcsqActivity.class);
					Bundle bundle=new Bundle();
					bundle.putString("uid", getTxt(position,"uid"));
					bundle.putString("sid", getTxt(position,"sid"));
					intent.putExtras(bundle);
					startActivity(intent);
				}else if("4".equals(type)){
					intent=new Intent(DbsxListActivity.this,NbswActivity.class);
					Bundle bundle=new Bundle();
					bundle.putString("uid", getTxt(position,"uid"));
					bundle.putString("sid", getTxt(position,"sid"));
					intent.putExtras(bundle);
					startActivity(intent);
				}else if("5".equals(type)){
					intent=new Intent(DbsxListActivity.this,YcsqActivity.class);
					Bundle bundle=new Bundle();
					bundle.putString("uid", getTxt(position,"uid"));
					bundle.putString("sid", getTxt(position,"sid"));
					intent.putExtras(bundle);
					startActivity(intent);
				}else if("6".equals(type)){
					intent=new Intent(DbsxListActivity.this,NbfwActivity.class);
					Bundle bundle=new Bundle();
					bundle.putString("uid", getTxt(position,"uid"));
					bundle.putString("sid", getTxt(position,"sid"));
					intent.putExtras(bundle);
					startActivity(intent);
				}
			}
		});
	}
	private String getTxt(int position,String str){
    	try {
    		return result.getJSONObject(position).getString(str);
		} catch (JSONException e) {
			return "";
		}
    }
	protected void query() {
		 dataHelper = DataHelper.getInstance();
		 Bundle bundle = new Bundle();
		 if("1".equals(type)){
			 bundle.putString(BaseConstant.REQUEST_BUSI_CODE,
						"csxtip");
		 }else if("2".equals(type)){
			 bundle.putString(BaseConstant.REQUEST_BUSI_CODE,
						"qingjia");
		 }else if("3".equals(type)){
			 bundle.putString(BaseConstant.REQUEST_BUSI_CODE,
						"gongchu");
		 }else if("4".equals(type)){
			 bundle.putString(BaseConstant.REQUEST_BUSI_CODE,
						"shouwen");
		 }else if("5".equals(type)){
			 bundle.putString(BaseConstant.REQUEST_BUSI_CODE,
						"yongche");
		 }else if("6".equals(type)){
			 bundle.putString(BaseConstant.REQUEST_BUSI_CODE,
						"fawen");
		 }
		 bundle.putString("RequestlBuild",
					"NormalRequestBuild");

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
					list.clear();
					if(result.length()==0){
						rela_dblb.setVisibility(View.GONE);
						rela_zwsj.setVisibility(View.VISIBLE);
						return;
					}else{
						rela_dblb.setVisibility(View.VISIBLE);
						rela_zwsj.setVisibility(View.GONE);
					}
					Log.i("result", "result=="+result.toString());
					getList(result);
					adapter.notifyDataSetInvalidated();
				} catch (JSONException e) {
					alert(null, "数据转换异常");
				}
			}else{
				rela_dblb.setVisibility(View.GONE);
				rela_zwsj.setVisibility(View.VISIBLE);
			}
		}else{
			alert("操作提示", getCodeMsg(bundle.getString("code"),bundle.getString("msg")));
		}
    }
	private void getList(JSONArray result){
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
	class DbsxAdapter extends BaseAdapter{
        Context context;
		public DbsxAdapter(Context context) {
			super();
			this.context = context;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			if(list== null){
        		return 0;
        	}
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
			
			holder.tv_dbsx_item.setText(Html.fromHtml(getTxt(position)));
			return convertView;
		}
		private String getTxt(int position){
        	try {
				return list.get(position).getString("subject");
			} catch (JSONException e) {
				return "";
			}
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
			StringUtil.tagetToLogin(DbsxListActivity.this);
			break;
		case R.id.titleBack:
            finish();
			break;
		}
	}

}
