package unicorn.withub.sxfyydbg.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import unicorn.withub.sxfyydbg.R;
import unicorn.withub.sxfyydbg.data.BaseConstant;
import unicorn.withub.sxfyydbg.data.LoginHelper;
import unicorn.withub.sxfyydbg.data.NormalRequestBuild;
import unicorn.withub.sxfyydbg.util.DataHelper;
import unicorn.withub.sxfyydbg.util.StringUtil;

public class HyslbActivity extends BaseActivity{
	private String string="会议室申请信息";
	private TextView tv_xzsq;
	private TextView tv_zwlsjl;
	private TextView tv_sx;
	private ListView lv_sqxx_list;
	private HyslbAdapter adapter;
	//View view;
	private JSONArray result;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ysqhysxx);
		initView(string);
		addListener();
	}
	@Override
	protected void initView(String title) {
		// TODO Auto-generated method stub
		super.initView(title);
		tv_zwlsjl=(TextView) findViewById(R.id.tv_zwlsjl);
		tv_xzsq=(TextView) findViewById(R.id.tv_xzhyssq);
		tv_sx=(TextView) findViewById(R.id.tv_sx);
		//view=getLayoutInflater().inflate(R.layout.hyslb_head, null);
		lv_sqxx_list=(ListView) findViewById(R.id.lv_ysqhysxx);
		//lv_sqxx_list.addHeaderView(view);
		adapter=new HyslbAdapter(this);
		lv_sqxx_list.setAdapter(adapter);
		if(LoginHelper.getLoginInfo()==null){
			StringUtil.tagetToLogin(HyslbActivity.this);
			return;
		}
		query();
	}
	void addListener(){
		
		tv_xzsq.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(HyslbActivity.this,InputHyssqActivity.class);
				startActivityForResult(intent, 0);
			}
		});
		tv_sx.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				query();
			}
		});
		
	}
	protected void query() {
		 dataHelper = DataHelper.getInstance();
		 Bundle bundle = new Bundle();
		 bundle.putString(BaseConstant.REQUEST_BUSI_CODE,
					"appmeet");
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
					Log.i("result", "result=="+result.toString());
					if(result.length()==0){
						tv_zwlsjl.setVisibility(View.VISIBLE);
						lv_sqxx_list.setVisibility(View.GONE);
						return;
					}
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
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode==0){
			query();
		}
	}
	class HyslbAdapter extends BaseAdapter{
	    Context context;
		public HyslbAdapter(Context context) {
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
				convertView=LayoutInflater.from(context).inflate(R.layout.ysqhysxx_item, null);
				holder=new ViewHolder();
				holder.tv_chrs=(TextView) convertView.findViewById(R.id.tv_chrs);
				holder.tv_hysxx=(TextView) convertView.findViewById(R.id.tv_hysxx);
				holder.tv_sfldcj=(TextView) convertView.findViewById(R.id.tv_sfldcj);
				holder.tv_kssj=(TextView) convertView.findViewById(R.id.tv_kssj);
				holder.tv_jssj=(TextView) convertView.findViewById(R.id.tv_jssj);
				holder.tv_sqr=(TextView) convertView.findViewById(R.id.tv_sqr);
				holder.tv_sqrq=(TextView) convertView.findViewById(R.id.tv_sqrq);
				holder.tv_sqbm=(TextView) convertView.findViewById(R.id.tv_sqbm);
				holder.tv_sqrdh=(TextView) convertView.findViewById(R.id.tv_sqrdh);
				convertView.setTag(holder);
			}else{
				holder=(ViewHolder) convertView.getTag();
			}
			holder.tv_hysxx.setText(getTxt(position, "room_name")+","+getTxt(position, "room_no"));
			holder.tv_chrs.setText(getTxt(position, "chrs")+"人");
			holder.tv_sfldcj.setText(getTxt(position, "hsld"));
			holder.tv_kssj.setText(getTxt(position, "begin_time"));
			holder.tv_jssj.setText(getTxt(position, "end_time"));
			holder.tv_sqrq.setText(getTxt(position, "apply_time"));
			holder.tv_sqbm.setText(getTxt(position, "apply_depname"));
			holder.tv_sqrdh.setText(getTxt(position, "apply_usertel"));
			holder.tv_sqr.setText(getTxt(position, "apply_username"));
			return convertView;
		}
		private String getTxt(int position,String str){
        	try {
        		
        		return result.getJSONObject(position).getString(str);
			} catch (JSONException e) {
				return "";
			}
        }
		class ViewHolder{
			TextView tv_hysxx;
			TextView tv_chrs;
			TextView tv_sfldcj;
			TextView tv_kssj;
			TextView tv_jssj;
			TextView tv_sqr;
			TextView tv_sqrq;
			TextView tv_sqbm;
			TextView tv_sqrdh;
		}
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.titleSub:
			StringUtil.tagetToLogin(HyslbActivity.this);
			break;
		case R.id.titleBack:
            finish();
			break;
		}
	}

}
