package unicorn.withub.sxfyydbg.broadcastreceiver;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.TextView;

import unicorn.withub.sxfyydbg.activity.MainActivity;

public class BroadcastData {
	public static String action="loginInfo";
	public static String ggcount="ggcount";
	public static String ycdbcount="ycdbcount";
	public static String qjdbcount="qjdbcount";
	public static String swdbcount="swdbcount";
	public static String fwdbcount="fwdbcount";
	public static String hysxx="hysxx";
	public static String nbfwyldps="nbfwyldps";
	public static String nbfwbmldyj="nbfwbmldyj";
	public static String nbfwbmblyj="nbfwbmblyj";
	public static String nbfwqtbmyj="nbfwqtbmyj";
	public static String nbswnbyj="nbswnbyj";
	public static String nbswldps="nbswldps";
	public static String nbswwjqm="nbswwjqm";
	public static String nbswcbqk="nbswcbqk";
	public static String qjsqbmldyj="qjsqbmldyj";
	public static String qjsqyldyj="qjsqyldyj";
	public static String qjsqrscyj="qjsqrscyj";
	public static String gcsqzgbmyj="gcsqzgbmyj";
	public static String gcsqzgyldyj="gcsqzgyldyj";
	public static String ycsqbmsp="ycsqbmsp";
	public static String ycsqcgsp="ycsqcgsp";
	public static String ycsqcdldsp="ycsqcdldsp";
	public static String finish="finish";
	public Activity activity;
	public MainActivity mainActivity;
	private TextView tv_count;
	private String count;
	public BroadcastData(Activity activity) {
		super();
		this.activity = activity;
		IntentFilter filter=new IntentFilter();
		filter.addAction(action);
		filter.addAction(fwdbcount);
		filter.addAction(ggcount);
		filter.addAction(qjdbcount);
		filter.addAction(swdbcount);
		filter.addAction(ycdbcount);
		activity.registerReceiver(receiver, filter);
		
	}
	public BroadcastReceiver receiver=new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String cat=intent.getAction();
			if(action.equals(cat)){
				activity.finish();
			}else if(ggcount.equals(cat)){
				tv_count.setText(count);
			}else if(fwdbcount.equals(cat)){
				tv_count.setText(count);
			}else if(swdbcount.equals(cat)){
				tv_count.setText(count);
			}else if(qjdbcount.equals(cat)){
				tv_count.setText(count);
			}else if(ycdbcount.equals(cat)){
				tv_count.setText(count);
			}
		}
	};
	public BroadcastReceiver getReceiver() {
		return receiver;
	}
	public void unregistReceiver(){
		activity.unregisterReceiver(receiver);
	}
	public void setCount(TextView tv,String count){
		tv_count=tv;
		this.count=count;
	}

}
