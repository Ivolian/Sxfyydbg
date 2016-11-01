package unicorn.withub.sxfyydbg.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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

public class XwzxListActivity extends BaseActivity{
	private String string="内网新闻";
	private ListView lv_xwzx;
	private XwzxListAdapter adapter;
	private JSONArray result;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xwzxlist_activity);
		initView(string);
		if(LoginHelper.getLoginInfo()==null){
			StringUtil.tagetToLogin(XwzxListActivity.this);
			return;
		}
		query();
	}
	@Override
	protected void initView(String title) {
		// TODO Auto-generated method stub
		super.initView(title);
		lv_xwzx=(ListView) findViewById(R.id.lv_xwzx_list);
		adapter=new XwzxListAdapter(this);
		lv_xwzx.setAdapter(adapter);
		lv_xwzx.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(XwzxListActivity.this, XwzxDetailActivity.class);
				Bundle bundle=new Bundle();
//				bundle.putString("title", getTxt(position, "title"));
//				bundle.putString("content", getTxt(position, "txt"));
//				bundle.putString("author", getTxt(position, "author"));
//				bundle.putString("release_date", getTxt(position, "release_date"));
				bundle.putString("contentId", getTxt(position, "content_id"));
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
	}
	protected void query() {
		 dataHelper = DataHelper.getInstance();
		 Bundle bundle = new Bundle();
		 bundle.putString(BaseConstant.REQUEST_BUSI_CODE,
					"xinwen");
		 bundle.putString("RequestlBuild",
					"NormalRequestBuild");

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
	class XwzxListAdapter extends BaseAdapter{
		Context context;
		public XwzxListAdapter(Context context) {
			super();
			this.context = context;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			if(result==null){
				return 0;
			}else{
				return result.length();
			}
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
				convertView=LayoutInflater.from(context).inflate(R.layout.notificationlist_item, null);
				holder=new ViewHolder();
				holder.tv_xwzx_item=(TextView) convertView.findViewById(R.id.tv_notice_item);
				convertView.setTag(holder);
			}else{
				holder=(ViewHolder) convertView.getTag();
			}
			
			holder.tv_xwzx_item.setText(getTitle(getTxt(position,"title")));
			return convertView;
		}
	}
	private String getTxt(int position,String str){
    	try {
			return result.getJSONObject(position).getString(str);
		} catch (JSONException e) {
			return "";
		}
    }
	private String getTitle(String str){
		if(str!=null){
			if(str.length()<=17){
				return str;
			}else{
				return str.substring(0, 16)+"....";
			}
		}else{
			return "";
		}
	}
	class ViewHolder{
		TextView tv_xwzx_item;
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.titleSub:
			StringUtil.tagetToLogin(XwzxListActivity.this);
			break;
		case R.id.titleBack:
            finish();
			break;
		}
	}

}
