package unicorn.withub.sxfyydbg.data;

public class XorY_Position {
	/**根据得到的屏幕坐标，确定要在图片上写字的横坐标位置*/
	public static int getPhoneX(int n){
		if(n<40){
			n=n-10;
		}else if(n<60){
			n=n-20;
		}else if(n<80){
			n=n-30;
		}else if(n<100){
			n=n-38;
		}else if(n<120){
			n=n-48;
		}else if(n<140){
			n=n-58;
		}else if(n<160){
			n=n-68;
		}else if(n<180){
			n=n-78;
		}else if(n<200){
			n=n-88;
		}else if(n<220){
			n=n-98;
		}else if(n<240){
			n=n-108;
		}else if(n<260){
			n=n-118;
		}else if(n<280){
			n=n-128;
		}else if(n<=300){
			n=n-138;
		}else if(n<=320){
			n=n-148;
		}else if(n<=340){
			n=n-158;
		}
		return n;
	}
	/**根据得到的屏幕坐标，确定要在图片上写字的横坐标位置*/
	public static int getPhoneY(int n){
		if(n<40){
			n=n-10;
		}else if(n<60){
			n=n-22;
		}else if(n<80){
			n=n-34;
		}else if(n<100){
			n=n-46;
		}else if(n<120){
			n=n-58;
		}else if(n<140){
			n=n-70;
		}else if(n<160){
			n=n-82;
		}else if(n<180){
			n=n-94;
		}else if(n<200){
			n=n-106;
		}else if(n<220){
			n=n-118;
		}else if(n<240){
			n=n-130;
		}else if(n<260){
			n=n-142;
		}else if(n<280){
			n=n-154;
		}else if(n<=300){
			n=n-166;
		}else if(n<=320){
			n=n-178;
		}else if(n<=340){
			n=n-190;
		}
		return n;
	}
	/**根据得到的屏幕坐标，确定要在图片上写字的横坐标位置*/
	public static int getPadX(int n){
		if(n<40){
			n=n-15;
		}else if(n<60){
			n=n-30;
		}else if(n<80){
			n=n-46;
		}else if(n<100){
			n=n-62;
		}else if(n<120){
			n=n-78;
		}else if(n<140){
			n=n-94;
		}else if(n<160){
			n=n-110;
		}else if(n<180){
			n=n-126;
		}else if(n<200){
			n=n-142;
		}else if(n<220){
			n=n-158;
		}else if(n<240){
			n=n-174;
		}else if(n<260){
			n=n-190;
		}else if(n<280){
			n=n-206;
		}else if(n<=300){
			n=n-216;
		}else if(n<=320){
			n=n-226;
		}else if(n<=340){
			n=n-236;
		}else if(n<=360){
			n=n-246;
		}else if(n<=380){
			n=n-256;
		}else if(n<=400){
			n=n-266;
		}else if(n<=420){
			n=n-276;
		}
		return n;
	}
	/**根据得到的屏幕坐标，确定要在图片上写字的横坐标位置*/
	public static int getPadY(int n){
		if(n<40){
			n=n-10;
		}else if(n<60){
			n=n-23;
		}else if(n<80){
			n=n-36;
		}else if(n<100){
			n=n-49;
		}else if(n<120){
			n=n-63;
		}else if(n<140){
			n=n-78;
		}else if(n<160){
			n=n-100;
		}else if(n<180){
			n=n-115;
		}else if(n<200){
			n=n-130;
		}else if(n<220){
			n=n-145;
		}else if(n<240){
			n=n-160;
		}else if(n<260){
			n=n-175;
		}else if(n<280){
			n=n-190;
		}else if(n<=300){
			n=n-205;
		}else if(n<=320){
			n=n-220;
		}else if(n<=340){
			n=n-235;
		}
		return n;
	}

}
