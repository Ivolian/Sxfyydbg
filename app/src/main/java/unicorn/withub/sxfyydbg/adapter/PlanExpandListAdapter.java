package unicorn.withub.sxfyydbg.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import unicorn.withub.sxfyydbg.R;
import unicorn.withub.sxfyydbg.activity.NbfwActivity;


public class PlanExpandListAdapter extends BaseExpandableListAdapter{
	List<String> list;
    LayoutInflater inflater;
    Context context;
    NbfwActivity activity;
	public PlanExpandListAdapter(List<String> list, Context context) {
		super();
		if(list!=null){
			this.list = list;
		}else{
			this.list=new ArrayList<String>();
		}
		Log.i("list", "list"+list);
		this.context = context;
		this.inflater=LayoutInflater.from(context);
		activity=(NbfwActivity) context;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolderChild viewHolderChild;
		if(convertView==null){
			convertView=inflater.inflate(R.layout.plan_expand_child, null);
			viewHolderChild=new ViewHolderChild();
			viewHolderChild.et_child=(EditText) convertView.findViewById(R.id.et_planchild);
			//viewHolderChild.tv_submit=(TextView) convertView.findViewById(R.id.tv_ldps_tj);
			convertView.setTag(viewHolderChild);
		}else{
			viewHolderChild=(ViewHolderChild) convertView.getTag();
		}
		/*viewHolderChild.tv_submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i("===", viewHolderChild.tv_submit.getText().toString());
			}
		});*/
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return list.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolderParent viewHolder=null;
		if(convertView==null){
			convertView=inflater.inflate(R.layout.plan_expand_parent, null);
			viewHolder=new ViewHolderParent();
			viewHolder.tv_parent_content=(TextView) convertView.findViewById(R.id.tv_parent_content);
			convertView.setTag(viewHolder);
		}else{
			viewHolder=(ViewHolderParent) convertView.getTag();
		}
		String str=list.get(groupPosition);
		viewHolder.tv_parent_content.setText(str);
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}
	class ViewHolderParent{
		TextView tv_parent_content;
	}
	class ViewHolderChild{
		EditText et_child;
		TextView tv_submit;
	}

}
