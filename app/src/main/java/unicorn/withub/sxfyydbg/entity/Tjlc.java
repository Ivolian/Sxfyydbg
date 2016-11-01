package unicorn.withub.sxfyydbg.entity;

import java.io.Serializable;

public class Tjlc implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String unid;
	String processid;
	String subject;
	String currentNodeId;
	String targetNodeId;
	String selUsers;
	String fldtrace;
	String fldbname;
	public String getUnid() {
		return unid;
	}
	public void setUnid(String unid) {
		this.unid = unid;
	}
	public String getProcessid() {
		return processid;
	}
	public void setProcessid(String processid) {
		this.processid = processid;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getCurrentNodeId() {
		return currentNodeId;
	}
	public void setCurrentNodeId(String currentNodeId) {
		this.currentNodeId = currentNodeId;
	}
	public String getTargetNodeId() {
		return targetNodeId;
	}
	public void setTargetNodeId(String targetNodeId) {
		this.targetNodeId = targetNodeId;
	}
	public String getSelUsers() {
		return selUsers;
	}
	public void setSelUsers(String selUsers) {
		this.selUsers = selUsers;
	}
	public String getFldtrace() {
		return fldtrace;
	}
	public void setFldtrace(String fldtrace) {
		this.fldtrace = fldtrace;
	}
	public String getFldbname() {
		return fldbname;
	}
	public void setFldbname(String fldbname) {
		this.fldbname = fldbname;
	}
	@Override
	public String toString() {
		return "Tjlc [unid=" + unid + ", processid=" + processid + ", subject="
				+ subject + ", currentNodeId=" + currentNodeId
				+ ", targetNodeId=" + targetNodeId + ", selUsers=" + selUsers
				+ ", fldtrace=" + fldtrace + ", fldbname=" + fldbname + "]";
	}
	

}
