package unicorn.withub.sxfyydbg.service;


import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;

import unicorn.withub.sxfyydbg.broadcastreceiver.TimeReceiver;
import unicorn.withub.sxfyydbg.util.BadgeUtil;

public class TimeService extends Service{
	private int count=1;
    @SuppressLint("HandlerLeak")
	private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==1){
                BadgeUtil.setBadgeCount(getApplicationContext(), count++);
            }
        }
    };
    @Override
    public void onCreate() {
        super.onCreate();
        //new MyThread().start();
        Intent intent=new Intent(TimeService.this, TimeReceiver.class);
        intent.setAction("time");
        PendingIntent pi=PendingIntent.getBroadcast(TimeService.this, 0, intent, 0);
        AlarmManager am=(AlarmManager) getSystemService(ALARM_SERVICE);
        am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, 
        		SystemClock.elapsedRealtime(), 30*1000, pi);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    class MyThread extends Thread{
        @Override
        public void run() {
            super.run();
            try {
                while (true){
                    Message msg=new Message();
                    msg.what=1;
                    handler.sendEmptyMessage(msg.what);
                    Thread.sleep(60000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
