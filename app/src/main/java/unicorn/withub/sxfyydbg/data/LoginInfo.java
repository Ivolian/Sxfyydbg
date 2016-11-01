package unicorn.withub.sxfyydbg.data;

public class LoginInfo {
	//默认登录类型
	
			private String loginName;
			private String loginBusiType;
			private String lastDate;
			private String userId;
			private String fydm;
			private String userName;
			private String ticket;
			private String deptId;
			private String deptName;
			private String userTel;
			public String getLoginName() {
				return loginName;
			}
			public void setLoginName(String loginName) {
				this.loginName = loginName;
			}
			public String getLoginBusiType() {
				return loginBusiType;
			}
			public void setLoginBusiType(String loginBusiType) {
				this.loginBusiType = loginBusiType;
			}
			
			public String getUserId() {
				return userId;
			}
			public void setUserId(String userId) {
				this.userId = userId;
			}
			public String getFydm() {
				return fydm;
			}
			public void setFydm(String fydm) {
				this.fydm = fydm;
			}
			public String getLastDate() {
				return lastDate;
			}
			public void setLastDate(String lastDate) {
				this.lastDate = lastDate;
			}
			public String getUserName() {
				return userName;
			}
			public void setUserName(String userName) {
				this.userName = userName;
			}
			public String getTicket() {
				return ticket;
			}
			public void setTicket(String ticket) {
				this.ticket = ticket;
			}
			public String getDeptId() {
				return deptId;
			}
			public void setDeptId(String deptId) {
				this.deptId = deptId;
			}
			public String getDeptName() {
				return deptName;
			}
			public void setDeptName(String deptName) {
				this.deptName = deptName;
			}
			public String getUserTel() {
				return userTel;
			}
			public void setUserTel(String userTel) {
				this.userTel = userTel;
			}
			@Override
			public String toString() {
				return "LoginInfo [loginName=" + loginName + ", loginBusiType="
						+ loginBusiType + ", lastDate=" + lastDate + ", userId="
						+ userId + ", fydm=" + fydm + ", userName=" + userName
						+ ", ticket=" + ticket + ", deptId=" + deptId
						+ ", deptName=" + deptName + ", userTel=" + userTel + "]";
			}
			
			
			
			

}
