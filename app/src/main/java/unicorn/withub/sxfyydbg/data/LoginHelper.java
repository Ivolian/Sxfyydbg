package unicorn.withub.sxfyydbg.data;

import java.util.Calendar;
import java.util.Date;


public class LoginHelper {

    private static final int timeout = 30;

    private static LoginInfo loginInfo;
    private static Date lastDate;
    private static String key;

    public static LoginInfo getLoginInfo() {
        if (lastDate != null && loginInfo != null) {

            Date current = new Date();

            Calendar cr = Calendar.getInstance();
            cr.setTime(lastDate);
            cr.add(Calendar.MINUTE, timeout);
            Date td = cr.getTime();

            if (lastDate.after(current)) {
                loginInfo = null;
            } else if (td.before(current)) {
                loginInfo = null;
                lastDate = new Date();
            }
            return loginInfo;
        } else {
            return null;
        }

    }

    public static void setLoginInfo(LoginInfo loginInfo) {
        LoginHelper.loginInfo = loginInfo;
        lastDate = new Date();
    }

    public static void setKey(String key) {
        LoginHelper.key = key;
    }

    public static String getKey() {
        return key;
    }
}
