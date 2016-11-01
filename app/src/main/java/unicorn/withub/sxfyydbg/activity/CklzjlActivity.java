package unicorn.withub.sxfyydbg.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import unicorn.withub.sxfyydbg.R;
import unicorn.withub.sxfyydbg.data.BaseConstant;
import unicorn.withub.sxfyydbg.data.LoginHelper;
import unicorn.withub.sxfyydbg.data.NormalRequestBuild;
import unicorn.withub.sxfyydbg.util.DataHelper;
import unicorn.withub.sxfyydbg.util.StringUtil;

public class CklzjlActivity extends BaseActivity{
	private String string="流转记录";
	private ListView lv_cklzjl;
	private TextView tv_dclhj;
	private TextView tv_dclyh;
	private TextView tv_sdsj;
	private JSONArray ycl_arr;
	private JSONObject dcl;
	private YclAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cklzjl_activity);
		initView(string);
	}
	@Override
	protected void initView(String title) {
		// TODO Auto-generated method stub
		super.initView(title);
		lv_cklzjl=(ListView) findViewById(R.id.lv_ycl_list);
		adapter=new YclAdapter(this);
		lv_cklzjl.setAdapter(adapter);
		tv_dclhj=(TextView) findViewById(R.id.tv_dclhj);
		tv_dclyh=(TextView) findViewById(R.id.tv_dclyh);
		tv_sdsj=(TextView) findViewById(R.id.tv_sdsj);
		Bundle bundle=getIntent().getExtras();
		if(LoginHelper.getLoginInfo()==null){
			StringUtil.tagetToLogin(CklzjlActivity.this);
			return;
		}
		query(bundle.getString("uid"));
	}
	protected void query(String uid) {
		 dataHelper = DataHelper.getInstance();
		 Bundle bundle = new Bundle();
		 bundle.putString(BaseConstant.REQUEST_BUSI_CODE,
					"lzjl");
		 bundle.putString("RequestlBuild",
					"NormalRequestBuild");
		 bundle.putString("parameters_uid", uid);
		 Log.i("loginData", "loginData="+LoginHelper.getLoginInfo());
		 bundle.putString("loginInfo", LoginHelper.getLoginInfo()+"");
		 sendRequest(bundle,new NormalRequestBuild(),"正在查询" );
	}
	public void busiFinish(Bundle bundle) {
		if(bundle.get("code").equals("000000")){
		
			String rs1 = bundle.getString("parameter-result1");
			String rs2 = bundle.getString("parameter-result2");
			Log.i("rs1", "rs1=="+rs1);
			Log.i("rs2", "rs2=="+rs2);
			if(!rs1.equals("") && !rs1.equals("null")){
				try {
					ycl_arr = new JSONArray(rs1);
					Log.i("ycl_arr", "ycl_arr=="+ycl_arr.toString());
					adapter.notifyDataSetInvalidated();
				} catch (JSONException e) {
					alert(null, "数据转换异常");
				}
			}
			if(!rs2.equals("") && !rs2.equals("null")){
				try {
					dcl=new JSONObject(rs2);
					Log.i("dcl", "dcl=="+dcl.toString());
					tv_dclhj.setText(dcl.getString("subject"));
					tv_dclyh.setText(dcl.getString("userName"));
					tv_sdsj.setText(dcl.getString("startDate"));
				} catch (JSONException e) {
					alert(null, "数据转换异常");
				}
			}
		}else{
			alert("操作提示", getCodeMsg(bundle.getString("code"),bundle.getString("msg")));
		}
    }
	class YclAdapter extends BaseAdapter{
	    Context context;
		public YclAdapter(Context context) {
			super();
			this.context = context;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			if(ycl_arr == null){
	    		return 0;
	    	}
	        return ycl_arr.length(); 
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
				convertView=LayoutInflater.from(context).inflate(R.layout.cklzjl_item, null);
				holder=new ViewHolder();
				holder.tv_czr=(TextView) convertView.findViewById(R.id.tv_czr);
				holder.tv_czhj=(TextView) convertView.findViewById(R.id.tv_czhj);
				holder.tv_sdsj=(TextView) convertView.findViewById(R.id.tv_sdsj);
				holder.tv_scsj=(TextView) convertView.findViewById(R.id.tv_scsj);
				holder.tv_syhj=(TextView) convertView.findViewById(R.id.tv_syhj);
				convertView.setTag(holder);
			}else{
				holder=(ViewHolder) convertView.getTag();
			}
			holder.tv_czr.setText(getTxt(position, "userName"));
			holder.tv_czhj.setText(getTxt(position, "subject"));
			holder.tv_sdsj.setText(getTxt(position, "startDate"));
			holder.tv_scsj.setText(getTxt(position, "endDate"));
			holder.tv_syhj.setText(getTxt(position, "nextActionName"));
			return convertView;
		}
		private String getTxt(int position,String str){
        	try {
        		
        		return ycl_arr.getJSONObject(position).getString(str);
			} catch (JSONException e) {
				return "";
			}
        }
		class ViewHolder{
			TextView tv_czr;
			TextView tv_czhj;
			TextView tv_sdsj;
			TextView tv_scsj;
			TextView tv_syhj;
		}
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.titleSub:
			StringUtil.tagetToLogin(CklzjlActivity.this);
			break;
		case R.id.titleBack:
            finish();
			break;
		}
	}

}
