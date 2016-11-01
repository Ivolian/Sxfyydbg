package unicorn.withub.sxfyydbg.xml;

import android.os.Bundle;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ResponseContentHandler extends BaseContentHandler{
	private Bundle bundle = new Bundle();
	private String key = "";


	public void endElement(String uri, String localName, String name)
			throws SAXException {
		// TODO Auto-generated method stub
		if (key.equals(localName)) {
			bundle.putString(key, getValue());
		}
		
		if(key.startsWith("parameter-") && localName.equals("parameter")){
			
			if(bundle.getString(key) == null){
				bundle.putString(key, getValue());
			}
		}
		if (key.equals("description") && getValue().length() == 0) {
			bundle.putString(key, "操作失败");
		}
		super.endElement(uri, localName, name);
	}

	public void startElement(String uri, String localName, String name,
			Attributes atts) throws SAXException {
		// TODO Auto-generated method stub

		if (localName.equals("response")) {

		} else if (localName.equals("parameter")) {
			key = "parameter-" + atts.getValue("name");
			//bundle.putString("parameter-" + atts.getValue("name"), getValue());
		} else {
			key = localName;
		}
	}

	public Bundle getBundle() {
		return bundle;
	}

}
