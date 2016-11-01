package unicorn.withub.sxfyydbg.widget;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import unicorn.withub.sxfyydbg.R;


/**自定义控件，画一个双箭头*/
public class LineImage extends View{

	public LineImage(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		//设置画布背景，这里设置为透明背景
		canvas.drawColor(getContext().getResources().getColor(R.color.light));
		//定义画笔
		Paint paint=new Paint();
		//设置画笔颜色为白色
		paint.setColor(Color.WHITE);
		//设置画笔只描边
		paint.setStyle(Paint.Style.STROKE);
		//设置画笔画的宽度
		paint.setStrokeWidth(2);
		//定义一条线
		Path path1=new Path();
		//设置基点
		path1.moveTo(0, 0);
		//设置终点
		path1.lineTo(15, 15);
		path1.close();
		//画线
		canvas.drawPath(path1, paint);
		Path path2=new Path();
		path2.moveTo(15, 15);
		path2.lineTo(0, 30);
		path2.close();
		canvas.drawPath(path2, paint);
		Path path3=new Path();
		path3.moveTo(15, 0);
		path3.lineTo(30, 15);
		path3.close();
		canvas.drawPath(path3, paint);
		Path path4=new Path();
		path4.moveTo(30, 15);
		path4.lineTo(15, 30);
		path4.close();
		canvas.drawPath(path4, paint);
		
	}
	

}
