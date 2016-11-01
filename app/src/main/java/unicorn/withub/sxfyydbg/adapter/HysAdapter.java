package unicorn.withub.sxfyydbg.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import unicorn.withub.sxfyydbg.R;

public class HysAdapter extends BaseAdapter{
    Context context;
    List<String> list=new ArrayList<String>();
    int arr[]=new int[]{R.color.hyskx,R.color.hyssyz,R.color.hysty,R.color.hyssyz,R.color.hyskx,
    		R.color.hyssyz,R.color.hyskx,R.color.hyssyz,R.color.hysty};
	public HysAdapter(Context context) {
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
			convertView=LayoutInflater.from(context).inflate(R.layout.hysxx_item, null);
			holder=new ViewHolder();
			holder.re_hysxx_item=(RelativeLayout) convertView.findViewById(R.id.re_hysxx_item);
			holder.tv_hys_index=(TextView) convertView.findViewById(R.id.tv_hys_index);
			holder.tv_hysfh=(TextView) convertView.findViewById(R.id.tv_hysfh);
			holder.tv_hysrnrs=(TextView) convertView.findViewById(R.id.tv_hysrnrs);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		holder.re_hysxx_item.setBackgroundResource(arr[position]);
		holder.tv_hys_index.setText(list.get(position));
		return convertView;
	}
	void addList(){
		list.add("第一会议室");
		list.add("第二会议室");
		list.add("第三会议室");
		list.add("第四会议室");
		list.add("第五会议室");
		list.add("第六会议室");
		list.add("第七会议室");
		list.add("第八会议室");
		list.add("第九会议室");
		
	}
	class ViewHolder{
		RelativeLayout re_hysxx_item;
		TextView tv_hys_index;
		TextView tv_hysfh;
		TextView tv_hysrnrs;
	}

}
