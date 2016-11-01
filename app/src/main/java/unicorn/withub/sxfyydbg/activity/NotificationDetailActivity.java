package unicorn.withub.sxfyydbg.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import unicorn.withub.sxfyydbg.R;
import unicorn.withub.sxfyydbg.data.LoginHelper;
import unicorn.withub.sxfyydbg.util.StringUtil;

public class NotificationDetailActivity extends BaseActivity{
	private String str="公告详细信息";
	private TextView tv_noti_title;
	private TextView tv_noti_content;
	private ListView lv_xiazai;
	private LinearLayout lin_bottom;
	private String fjname;
	private String [] fjnameArr;
	private String [] fjurlArr;
	private List<String> fjurlList=new ArrayList<String>();
	private String fjurl;
	private Fjadapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notificationdetail_activity);
		initView(str);
	}
	@Override
	protected void initView(String title) {
		// TODO Auto-generated method stub
		super.initView(title);
		tv_noti_title=(TextView) findViewById(R.id.tv_noti_title);
		TextPaint tp=tv_noti_title.getPaint();
		tp.setFakeBoldText(true);
		tv_noti_content=(TextView) findViewById(R.id.tv_noti_content);
		tv_noti_content.setMovementMethod(ScrollingMovementMethod.getInstance());
		lv_xiazai=(ListView) findViewById(R.id.lv_xiazai);
		lv_xiazai.setItemsCanFocus(true);
		lin_bottom=(LinearLayout) findViewById(R.id.lin_notice_buttom);
		Bundle bundle=getIntent().getExtras();
		Log.i("bundle==", "bundle=="+bundle.toString());
		Log.i("content==", "content=="+bundle.getString("content"));
		tv_noti_content.setText(Html.fromHtml(bundle.getString("content"))+"\n"+bundle.getString("username")+" "+bundle.getString("starttime"));
		tv_noti_title.setText(bundle.getString("title"));
		fjname=bundle.getString("fjname");
		fjurl=bundle.getString("fjurl");
		if(LoginHelper.getLoginInfo()==null){
			StringUtil.tagetToLogin(NotificationDetailActivity.this);
			return;
		}
		getFjname(fjname);
		getFjurl(fjurl);
		if("".equals(fjname)){
			lin_bottom.setVisibility(View.GONE);
		}else{
			lin_bottom.setVisibility(View.VISIBLE);
		}
		adapter=new Fjadapter(this);
		lv_xiazai.setAdapter(adapter);
	}
	/**得到拆分后要下载附件的url*/
	private void getFjurl(String url){
		fjurlArr=url.split(";");
		try {
			for(int i=0;i<fjurlArr.length;i++){
				Log.i("fjurlArr", "fjurlArr="+fjurlArr[i]);
				fjurlList.add("http://1.85.16.50:10000/requestChange/attachRedirect.do?url="+URLEncoder.encode(fjurlArr[i], "UTF-8"));
			}
			Log.i("fjurlList", "fjurlList="+fjurlList);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**得到拆分后要下载附件的文件名*/
	private void getFjname(String name){
		fjnameArr=name.split(";");
		for(int i=0;i<fjnameArr.length;i++){
			Log.i("fjnameArr", "fjnameArr="+fjnameArr[i]);
		}
	}
	class Fjadapter extends BaseAdapter{
		Context context;
		

		public Fjadapter(Context context) {
			super();
			this.context = context;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return fjnameArr.length;
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
				convertView=LayoutInflater.from(context).inflate(R.layout.fj_item, null);
				holder=new ViewHolder();
				holder.tv_name=(TextView) convertView.findViewById(R.id.fj_name);
				holder.tv_name.setMovementMethod(LinkMovementMethod.getInstance());
				convertView.setTag(holder);
			}else{
				holder=(ViewHolder) convertView.getTag();
			}
			//拼接一个超链接
			String html="<a href="+fjurlList.get(position)+">"+fjnameArr[position]+"</a>";
			holder.tv_name.setText(Html.fromHtml(html));
			return convertView;
		}
		
	}
	class ViewHolder{
		TextView tv_name;
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.titleSub:
			StringUtil.tagetToLogin(NotificationDetailActivity.this);
			break;
		case R.id.titleBack:
            finish();
			break;
		}
	}


}
