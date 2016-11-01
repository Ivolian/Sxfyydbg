package unicorn.withub.sxfyydbg.activity;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Date;

import unicorn.withub.sxfyydbg.R;
import unicorn.withub.sxfyydbg.broadcastreceiver.BroadcastData;
import unicorn.withub.sxfyydbg.data.BaseConstant;
import unicorn.withub.sxfyydbg.data.LoginHelper;
import unicorn.withub.sxfyydbg.data.NormalRequestBuild;
import unicorn.withub.sxfyydbg.data.UserData;
import unicorn.withub.sxfyydbg.util.DataHelper;
import unicorn.withub.sxfyydbg.util.DateUtil;
import unicorn.withub.sxfyydbg.util.StringUtil;

public class InputHyssqActivity extends BaseActivity{
	private String string="会议室申请";
	private TextView tv_save;
	private TextView tv_sqr;
	private TextView tv_sqrq;
	private EditText et_sqrbm;
	private EditText et_sqrdh;
	private EditText et_chrs;
	private TextView tv_ksrq;
	private TextView tv_jsrq;
	private TextView tv_kssj;
	private TextView tv_jssj;
	private TextView tv_hysxx;
	private EditText et_sqsy;
	private EditText et_bz;
	private Spinner sp_sfldcj;
	private SpAdapter adapter;
	private String arr[]=new String[]{"是","否"};
	private String date_start;
	private String date_end;
	private String time_start;
	private String time_end;
	private String sfldcj;
	private BroadcastReceiver receiver=new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String action=intent.getAction();
			if(action.equals(BroadcastData.hysxx)){
				tv_hysxx.setText(UserData.str_hysxx);
			}
		}
	};
	private DatePickerDialog.OnDateSetListener onDateStartListener = new DatePickerDialog.OnDateSetListener() {  
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,  
                int dayOfMonth) {  
            Log.d("test", ""+year+"年"+(monthOfYear+1)+"月"+dayOfMonth+"日");
            String moth="";
            String day="";
            if((monthOfYear+1)<=9){
            	moth="0"+(monthOfYear+1);
            }else{
            	moth=""+(monthOfYear+1);
            }
            if(dayOfMonth<=9){
            	day="0"+dayOfMonth;
            }else{
            	day=""+dayOfMonth;
            }
            date_start=""+year+"-"+moth+"-"+day;
            tv_ksrq.setText(date_start);
            if(!DateUtil.isTodayDate(new Date(),date_start+" 00:00:00")){
				Toast.makeText(InputHyssqActivity.this, "不能申请今天之前的日期", Toast.LENGTH_SHORT).show();
				tv_ksrq.setText("");
				return;
			}
        }
    };
    private DatePickerDialog.OnDateSetListener onDateEndListener = new DatePickerDialog.OnDateSetListener() {  
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,  
                int dayOfMonth) {  
            Log.d("test", ""+year+"年"+(monthOfYear+1)+"月"+dayOfMonth+"日");
            String moth="";
            String day="";
            if((monthOfYear+1)<=9){
            	moth="0"+(monthOfYear+1);
            }else{
            	moth=""+(monthOfYear+1);
            }
            if(dayOfMonth<=9){
            	day="0"+dayOfMonth;
            }else{
            	day=""+dayOfMonth;
            }
            date_end=""+year+"-"+moth+"-"+day;
            tv_jsrq.setText(date_end);
            if(tv_ksrq.getText().toString() == null || tv_ksrq.getText().toString().trim().equals("")){
    			alert(null, "开始日期不能为空");
    			tv_ksrq.setText("");
    			return;
    		}
    		if(tv_kssj.getText().toString() == null || tv_kssj.getText().toString().trim().equals("")){
    			alert(null, "开始时间不能为空");
    			tv_ksrq.setText("");
    			return;
    		}
            if(!DateUtil.isDate(date_start+" 00:00:00",date_end+" 00:00:00")){
				Toast.makeText(InputHyssqActivity.this, "结束日期不能再开始日期之前", Toast.LENGTH_SHORT).show();
				tv_jsrq.setText("");
				return;
			}
            if(!DateUtil.isThreeDayDate(date_start+" 00:00:00",date_end+" 00:00:00")){
				Toast.makeText(InputHyssqActivity.this, "申请日期不能超过三天", Toast.LENGTH_SHORT).show();
				tv_jsrq.setText("");
				return;
			}
        }
    };
    private TimePickerDialog.OnTimeSetListener onTimeStartListener=new TimePickerDialog.OnTimeSetListener() {
		
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			// TODO Auto-generated method stub
			String hour="";
			String min="";
			if(hourOfDay<=9){
				hour="0"+hourOfDay;
			}else{
				hour=""+hourOfDay;
			}
			if(minute<=9){
				min="0"+minute;
			}else{
				min=""+minute;
			}
			time_start=""+hour+":"+min;
			tv_kssj.setText(time_start);
		}
	};
	private TimePickerDialog.OnTimeSetListener onTimeEndListener=new TimePickerDialog.OnTimeSetListener() {
		
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			// TODO Auto-generated method stub
			String hour="";
			String min="";
			if(hourOfDay<=9){
				hour="0"+hourOfDay;
			}else{
				hour=""+hourOfDay;
			}
			if(minute<=9){
				min="0"+minute;
			}else{
				min=""+minute;
			}
			time_end=""+hour+":"+min;
			tv_jssj.setText(time_end);
			if(tv_ksrq.getText().toString() == null || tv_ksrq.getText().toString().trim().equals("")){
    			alert(null, "开始日期不能为空");
    			tv_jssj.setText("");
    			return;
    		}
    		if(tv_kssj.getText().toString() == null || tv_kssj.getText().toString().trim().equals("")){
    			alert(null, "开始时间不能为空");
    			tv_jssj.setText("");
    			return;
    		}
			Log.i("start", "start="+date_start+" "+time_start+":00");
			Log.i("end", "end="+date_end+" "+time_end+":00");
			String start=date_start+" "+time_start+":00";
			String end=date_end+" "+time_end+":00";
			if(!DateUtil.isDate1(start,end)){
				Toast.makeText(InputHyssqActivity.this, "结束时间不能再开始时间之前", Toast.LENGTH_SHORT).show();
				tv_jssj.setText("");
				return;
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.sqhys_activity);
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
	}
	@Override
	protected void initView(String title) {
		// TODO Auto-generated method stub
		super.initView(title);
		tv_save=(TextView) findViewById(R.id.tv_save);
		tv_save.setOnClickListener(listener);
		tv_sqr=(TextView) findViewById(R.id.tv_sqr);
		tv_sqr.setText(LoginHelper.getLoginInfo().getUserName());
		tv_sqrq=(TextView) findViewById(R.id.tv_sqrq);
		tv_sqrq.setText(LoginHelper.getLoginInfo().getLastDate());
		et_sqrbm=(EditText) findViewById(R.id.et_sqrbm);
		et_sqrbm.setText(LoginHelper.getLoginInfo().getDeptName());
		et_sqrdh=(EditText) findViewById(R.id.et_sqrdh);
		et_sqrdh.setText(LoginHelper.getLoginInfo().getUserTel());
		et_chrs=(EditText) findViewById(R.id.et_chrs);
		tv_ksrq=(TextView) findViewById(R.id.tv_ksrq);
		tv_jsrq=(TextView) findViewById(R.id.tv_jsrq);
		tv_kssj=(TextView) findViewById(R.id.tv_kssj);
		tv_jssj=(TextView) findViewById(R.id.tv_jssj);
		tv_hysxx=(TextView) findViewById(R.id.tv_hysxx);
		tv_hysxx.setOnClickListener(listener);
		tv_ksrq.setOnClickListener(listener);
		tv_jsrq.setOnClickListener(listener);
		tv_kssj.setOnClickListener(listener);
		tv_jssj.setOnClickListener(listener);
		et_sqsy=(EditText) findViewById(R.id.et_sqsy);
		et_bz=(EditText) findViewById(R.id.et_bz);
		et_sqsy.setMovementMethod(ScrollingMovementMethod.getInstance());
		et_bz.setMovementMethod(ScrollingMovementMethod.getInstance());
		sp_sfldcj=(Spinner) findViewById(R.id.sp_sfldcj);
		adapter=new SpAdapter(this);
		sp_sfldcj.setAdapter(adapter);
		sp_sfldcj.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				sfldcj=arr[position];
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		if(LoginHelper.getLoginInfo()==null){
			StringUtil.tagetToLogin(InputHyssqActivity.this);
			return;
		}
	}
	public void save(){
		if(et_chrs.getText().toString() == null || et_chrs.getText().toString().trim().equals("")){
			alert(null, "参会人数不能为空");
			return;
		}
		
		if(!dateIsNull()){
			return;
		}
		if(tv_hysxx.getText().toString() == null || tv_hysxx.getText().toString().trim().equals("")){
			alert(null, "会议室信息不能为空");
			return;
		}
		 dataHelper = DataHelper.getInstance();
		 Log.i("baseActivity.dataHelper", "baseActivity.dataHelper="+dataHelper);
		 Bundle bundle = new Bundle();
		 
		 bundle.putString(BaseConstant.REQUEST_BUSI_CODE,
					"phoneAppMeet");
		 bundle.putString("RequestlBuild",
					"NormalRequestBuild");
		 bundle.putString("parameters_begin_time", tv_ksrq.getText().toString()+" "+tv_kssj.getText().toString());
		 bundle.putString("parameters_end_time", tv_jsrq.getText().toString()+" "+tv_jssj.getText().toString());
		 bundle.putString("parameters_apply_usertel", et_sqrdh.getText().toString());
		 bundle.putString("parameters_chrs", et_chrs.getText().toString());
		 bundle.putString("parameters_hsld", sfldcj);
		 bundle.putString("parameters_remarks", et_bz.getText().toString());
		 bundle.putString("parameters_apply_reason", et_sqsy.getText().toString());
		 bundle.putString("parameters_room_no", UserData.room_no);
		 bundle.putString("parameters_room_name", UserData.room_name);
		 bundle.putString("parameters_floor", UserData.ronm_floor);
		 bundle.putString("parameters_door_no", UserData.door_no);
		 bundle.putString("parameters_capacity", UserData.capacity);
		 Log.i("loginData", "loginData="+LoginHelper.getLoginInfo());
		 bundle.putString("loginInfo", LoginHelper.getLoginInfo()+"");
		 sendRequest(bundle,new NormalRequestBuild(),"正在保存,请稍后...");
	}
	public void busiFinish(Bundle bundle) {
		if(bundle.get("code").equals("000000")){
		
			String message = bundle.getString("parameter-message");
			Log.i("message", "message=="+message);
			if("1".equals(message)){
				showDialog("保存成功");
			}else{
				alert(null, "该会议室已经被占用");
			}
		}else{
			alert("操作提示", getCodeMsg(bundle.getString("code"),bundle.getString("msg")));
		}
    }
	OnClickListener listener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int id=v.getId();
			switch (id) {
			case R.id.tv_hysxx:
				if(!dateIsNull()){
					return;
				}
				Intent intent=new Intent(InputHyssqActivity.this, XzhysActivity.class);
				Bundle bundle=new Bundle();
				bundle.putString("start",date_start+" "+time_start);
				bundle.putString("end", date_end+" "+time_end);
				intent.putExtras(bundle);
				startActivity(intent);
				break;
			case R.id.tv_ksrq:
				DateUtil.getDialog(InputHyssqActivity.this,onDateStartListener).show();
				break;
			case R.id.tv_jsrq:
				DateUtil.getDialog(InputHyssqActivity.this,onDateEndListener).show();
				break;
			case R.id.tv_kssj:
				DateUtil.getTimeDialog(InputHyssqActivity.this, onTimeStartListener).show();
				break;
			case R.id.tv_jssj:
				DateUtil.getTimeDialog(InputHyssqActivity.this, onTimeEndListener).show();
				break;
			case R.id.tv_save:
				if(LoginHelper.getLoginInfo()==null){
					StringUtil.tagetToLogin(InputHyssqActivity.this);
					return;
				}
				save();
				break;
			}
		}
	};
	private boolean dateIsNull(){
		if(tv_ksrq.getText().toString() == null || tv_ksrq.getText().toString().trim().equals("")){
			alert(null, "开始日期不能为空");
			return false;
		}
		if(tv_kssj.getText().toString() == null || tv_kssj.getText().toString().trim().equals("")){
			alert(null, "开始时间不能为空");
			return false;
		}
		if(tv_jsrq.getText().toString() == null || tv_jsrq.getText().toString().trim().equals("")){
			alert(null, "结束日期不能为空");
			return false;
		}
		if(tv_jssj.getText().toString() == null || tv_jssj.getText().toString().trim().equals("")){
			alert(null, "结束时间不能为空");
			return false;
		}
		return true;
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(receiver);
	}
	class SpAdapter extends BaseAdapter{
        Context context;
        
		public SpAdapter(Context context) {
			super();
			this.context = context;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return arr.length;
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
				convertView=LayoutInflater.from(context).inflate(R.layout.spinner_item, null);
				holder=new ViewHolder();
				holder.tv_ldcj_item=(TextView) convertView.findViewById(R.id.tv_sp_item);
				convertView.setTag(holder);
			}else{
				holder=(ViewHolder) convertView.getTag();
			}
			
			holder.tv_ldcj_item.setText(arr[position]);
			return convertView;
		}
		
	}
	class ViewHolder{
		TextView tv_ldcj_item;
	}
    protected void showDialog(String str) {
		
		Builder builer = new Builder(this);
		builer.setTitle("提示");
		builer.setMessage(str);
		builer.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				setResult(0);
				finish();
			}
		});
		AlertDialog dialog = builer.create();
		dialog.show();
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.titleSub:
			StringUtil.tagetToLogin(InputHyssqActivity.this);
			break;
		case R.id.titleBack:
            finish();
			break;
		}
	}

}
