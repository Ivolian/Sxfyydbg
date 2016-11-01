package unicorn.withub.sxfyydbg.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.FrameLayout;

import unicorn.withub.sxfyydbg.R;
import unicorn.withub.sxfyydbg.data.UserData;
import unicorn.withub.sxfyydbg.util.StringUtil;

/**主页面*/
public class MainActivity extends BaseActivity{
	private String string="办公事项";
	private FrameLayout fra_ggxx;
	private FrameLayout fra_csx;
	private FrameLayout fra_qjdb;
	private FrameLayout fra_gcdb;
	private FrameLayout fra_nbsw;
	private FrameLayout fra_ycdb;
	private FrameLayout fra_hyssq;
	private FrameLayout fra_nbfw;
	private FrameLayout fra_xwzx;
    //JSONObject result;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView(string);
	}
	/**控件初始化*/
	@Override
	protected void initView(String title) {
		// TODO Auto-generated method stub
		super.initView(title);
		fra_ggxx=(FrameLayout) findViewById(R.id.fra_ggxx);
		fra_csx=(FrameLayout) findViewById(R.id.fra_csx);
		fra_qjdb=(FrameLayout) findViewById(R.id.fra_qjdb);
		fra_gcdb=(FrameLayout) findViewById(R.id.fra_gcdb);
		fra_nbsw=(FrameLayout) findViewById(R.id.fra_nbsw);
		fra_ycdb=(FrameLayout) findViewById(R.id.fra_ycdb);
		fra_hyssq=(FrameLayout) findViewById(R.id.fra_hyssq);
		fra_nbfw=(FrameLayout) findViewById(R.id.fra_nbfw);
		fra_xwzx=(FrameLayout) findViewById(R.id.fra_xwzx);
		fra_ggxx.setOnClickListener(this);
		fra_csx.setOnClickListener(this);
		fra_qjdb.setOnClickListener(this);
		fra_gcdb.setOnClickListener(this);
		fra_nbsw.setOnClickListener(this);
		fra_ycdb.setOnClickListener(this);
		fra_hyssq.setOnClickListener(this);
		fra_nbfw.setOnClickListener(this);
		fra_xwzx.setOnClickListener(this);
//		Intent intent=new Intent(MainActivity.this, TimeService.class);
//		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		startService(intent);
	}
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent=null;
		switch (v.getId()) {
		case R.id.fra_ggxx:
			intent=new Intent(MainActivity.this, NotificationListActivity.class);
            startActivity(intent);
			break;
		case R.id.fra_csx:
			getThisIntent(1);
			break;
		case R.id.fra_qjdb:
			UserData.db_type=3;
			getThisIntent(2);
			break;
		case R.id.fra_gcdb:
			UserData.db_type=4;
			getThisIntent(3);
			break;
		case R.id.fra_nbsw:
			UserData.db_type=2;
			getThisIntent(4);
			break;
		case R.id.fra_ycdb:
			UserData.db_type=5;
			getThisIntent(5);
			break;
		case R.id.fra_hyssq:
			intent=new Intent(MainActivity.this, HyslbActivity.class);
			startActivity(intent);
			break;
		case R.id.fra_nbfw:
			UserData.db_type=1;
			getThisIntent(6);
			break;
		case R.id.fra_xwzx:
			intent=new Intent(MainActivity.this, XwzxListActivity.class);
			startActivity(intent);
			break;
		case R.id.titleSub:
			StringUtil.tagetToLogin(MainActivity.this);
			break;
		case R.id.titleBack:
            finish();
			break;
		}
	}
	/**待办页面跳转*/
	public void getThisIntent(int res){
		Intent intent=new Intent(MainActivity.this, DbsxListActivity.class);
		Bundle bundle=new Bundle();
		if(res==1){
			bundle.putString("title", "超审限提醒");
			bundle.putString("type", ""+1);
		}else if(res==2){
			bundle.putString("title", "请假待办事项");
			bundle.putString("type", ""+2);
		}else if(res==3){
			bundle.putString("title", "公出待办事项");
			bundle.putString("type", ""+3);
		}else if(res==4){
			bundle.putString("title", "内部收文待办事项");
			bundle.putString("type", ""+4);
		}else if(res==5){
			bundle.putString("title", "用车待办事项");
			bundle.putString("type", ""+5);
		}else if(res==6){
			bundle.putString("title", "内部发文待办事项");
			bundle.putString("type", ""+6);
		}
		intent.putExtras(bundle);
		startActivity(intent);
	}
}
