package unicorn.withub.sxfyydbg.dialog;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import unicorn.withub.sxfyydbg.R;
import unicorn.withub.sxfyydbg.broadcastreceiver.BroadcastData;
import unicorn.withub.sxfyydbg.util.StringUtil;

/**编辑图片的自定义对话框*/
public class EditDialog extends Dialog{
    Context context;
    EditText et_input;
    Button btn_add;
    public BroadcastReceiver receiver=new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			
		}
	};
	public EditDialog(Context context, int theme) {
		super(context, R.style.PlanDialog);
		this.context=context;
		// TODO Auto-generated constructor stub
		IntentFilter filter=new IntentFilter();
		filter.addAction(BroadcastData.nbfwyldps);
		filter.addAction(BroadcastData.nbfwbmldyj);
		filter.addAction(BroadcastData.nbfwbmblyj);
		filter.addAction(BroadcastData.nbfwqtbmyj);
		filter.addAction(BroadcastData.nbswcbqk);
		filter.addAction(BroadcastData.nbswldps);
		filter.addAction(BroadcastData.nbswnbyj);
		filter.addAction(BroadcastData.nbswwjqm);
		filter.addAction(BroadcastData.qjsqbmldyj);
		filter.addAction(BroadcastData.qjsqyldyj);
		filter.addAction(BroadcastData.qjsqrscyj);
		filter.addAction(BroadcastData.gcsqzgbmyj);
		filter.addAction(BroadcastData.gcsqzgyldyj);
		filter.addAction(BroadcastData.ycsqbmsp);
		filter.addAction(BroadcastData.ycsqcgsp);
		filter.addAction(BroadcastData.ycsqcdldsp);
		this.context.registerReceiver(receiver, filter);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.plan_dialog);
		initView();
		addListener();
	}
	/**控件初始化*/
	void initView(){
		et_input=(EditText) findViewById(R.id.et_input);
		btn_add=(Button) findViewById(R.id.btn_dia_add);
	}
	/**控件监听*/
	void addListener(){
        btn_add.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				StringUtil.editcontent=et_input.getText().toString();
				Intent intent=new Intent(StringUtil.edittype);
				context.sendBroadcast(intent);
				EditDialog.this.dismiss();
			}
		}); 
	}
	
	
	

}
