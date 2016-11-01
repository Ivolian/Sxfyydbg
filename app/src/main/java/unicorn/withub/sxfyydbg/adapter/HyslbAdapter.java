package unicorn.withub.sxfyydbg.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import unicorn.withub.sxfyydbg.R;


public class HyslbAdapter extends BaseAdapter{
    Context context;
    List<String> list=new ArrayList<String>();
	public HyslbAdapter(Context context) {
		super();
		this.context = context;
		addList();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
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
			convertView=LayoutInflater.from(context).inflate(R.layout.ysqhysxx_item, null);
			holder=new ViewHolder();
			holder.tv_chrs=(TextView) convertView.findViewById(R.id.tv_chrs);
			holder.tv_hysxx=(TextView) convertView.findViewById(R.id.tv_hysxx);
			holder.sp_sfldcj=(Spinner) convertView.findViewById(R.id.sp_sfldcj);
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
		holder.tv_hysxx.setText(list.get(position));
		return convertView;
	}
	void addList(){
		list.add("第一会议室");
		list.add("第二会议室");
		list.add("第三会议室");
		
	}
	class ViewHolder{
		TextView tv_hysxx;
		TextView tv_chrs;
		Spinner  sp_sfldcj;
		TextView tv_kssj;
		TextView tv_jssj;
		TextView tv_sqr;
		TextView tv_sqrq;
		TextView tv_sqbm;
		TextView tv_sqrdh;
	}

}
