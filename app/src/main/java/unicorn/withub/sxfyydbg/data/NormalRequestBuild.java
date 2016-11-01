package unicorn.withub.sxfyydbg.data;

import android.content.Context;
import android.os.Bundle;
import android.util.Xml;

import org.xmlpull.v1.XmlSerializer;

import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Iterator;

import unicorn.withub.sxfyydbg.application.BaseApplication;
import unicorn.withub.sxfyydbg.util.DateUtil;
import unicorn.withub.sxfyydbg.util.RandomGeneter;


public class NormalRequestBuild extends RequestBuild{
	@Override
	public String buildXml(Bundle bundle, Context context)
			throws UnsupportedEncodingException, NoSuchAlgorithmException {
		
		String time = DateUtil
				.formatDateTime(new Date(), "yyyyMMddHHmmss");
		
		String request_id = bundle.getString(BaseConstant.REQUEST_BUSI_CODE);
		
		StringWriter writer = null;
		try {
			XmlSerializer xmlSerializer = Xml.newSerializer();
			writer = new StringWriter();
			
			xmlSerializer.setOutput(writer);
			xmlSerializer.startDocument("UTF-8", true);
			xmlSerializer.startTag("", "request");
			
			
			xmlSerializer.startTag("", "requestFlow");
			xmlSerializer.text(getRequestFlow());
			xmlSerializer.endTag("", "requestFlow");
			
			
			xmlSerializer.startTag("", "version");
			xmlSerializer.text(BaseApplication.getVersion());
			xmlSerializer.endTag("", "version");
			
			xmlSerializer.startTag("", "UUID");
			xmlSerializer.text(BaseApplication.getUUID());
			xmlSerializer.endTag("", "UUID");
			
			
			xmlSerializer.startTag("", "busiCode");
			xmlSerializer.text(request_id);
			xmlSerializer.endTag("", "busiCode");
			
			xmlSerializer.startTag("", "loginName");
			xmlSerializer.text(getLoginName());
			xmlSerializer.endTag("", "loginName");
			
			xmlSerializer.startTag("", "loginBusiType");
			xmlSerializer.text(getLoginBusiType());
			xmlSerializer.endTag("", "loginBusiType");
			
			xmlSerializer.startTag("", "ticket");
			xmlSerializer.text(getTicket());
			xmlSerializer.endTag("", "ticket");
			
			String randCode = getRandCode();
			xmlSerializer.startTag("", "randCode");
			xmlSerializer.text(randCode);
			xmlSerializer.endTag("", "randCode");
			
			xmlSerializer.startTag("", "randCodeSec");
			xmlSerializer.text(getRandCodeSecOfRSA(randCode));
			xmlSerializer.endTag("", "randCodeSec");
			
			xmlSerializer.startTag("", "time");
			xmlSerializer.text(time);
			xmlSerializer.endTag("", "time");
			
			xmlSerializer.startTag("", "phoneType");
			xmlSerializer.text(BaseApplication.getPhoneType());
			xmlSerializer.endTag("", "phoneType");
			
			Iterator<String> it = bundle.keySet().iterator();
			
			xmlSerializer.startTag("", "parameters");
			
			String parameter = "";
			String name = "";
			while (it.hasNext()) {
				parameter = it.next();
				if (parameter.startsWith("parameters_")) {
					name = parameter.substring(11);
					xmlSerializer.startTag("", "parameter");
					xmlSerializer.attribute("", "name", name);
					xmlSerializer.cdsect(bundle.getString(parameter));
					xmlSerializer.endTag("", "parameter");
				}
			}
			
			
			xmlSerializer.endTag("", "parameters");
			xmlSerializer.endTag("", "request");
			
			xmlSerializer.endDocument();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return writer.toString();
	}

	private String getRandCode() {
		return RandomGeneter.generateString(12);
	}
	
	private String getRandCodeSecOfRSA(String randCode){
		return "";
	}

	@Override
	public String getServerUrl() {

		//return "http://61.186.177.9:8070/bs/request.shtml";
		//return "http://192.168.1.100:8080/sxgfBusiGate/request.shtml";
		//return "http://192.168.1.104:8080/sxgfBusiGate/request.shtml";
		//return "http://10.0.2.2:8088/bs/request.shtml";
		//return "http://149.0.0.26:8027/bs/request.shtml";
		//return "http://172.16.0.10/sxgfBusiGate/request.shtml";
		//return "http://192.168.1.119:8080/sxgfBusiGate/request.shtml";
		//return "http://192.168.1.108:9000/sxgfBusiGate/request.shtml";
		return "http://1.85.16.50:10000/requestChange/redirect.do";
		//return "http://1.85.16.50:10000/requestChange/redirect.do";
		//return "http://154.0.66.211:8080/sxgfBusiGate/request.shtml";
		//return "http://172.25.242.1:8080/sxgfBusiGate/request.shtml";
	}

	
	private String getLoginName(){
		if(LoginHelper.getLoginInfo() == null){
			return "";
		}else{
			return LoginHelper.getLoginInfo().getLoginName();
		}
	}
	
	private String getLoginBusiType(){
		if(LoginHelper.getLoginInfo() == null){
			return "";
		}else{
			return String.valueOf(LoginHelper.getLoginInfo().getLoginBusiType());
		}
	}
	
	private String getTicket(){
		if(LoginHelper.getKey() == null){
			return "";
		}else{
			return LoginHelper.getKey();
		}
	}
	
	
	public  String getRequestFlow(){
		return DateUtil.formatDateTime(new Date(),"yyyyMMddHHmmss") + RandomGeneter.generateString(6);
	}

}
