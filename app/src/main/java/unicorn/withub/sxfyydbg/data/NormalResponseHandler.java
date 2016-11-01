package unicorn.withub.sxfyydbg.data;

import android.content.Context;
import android.os.Bundle;

import org.xml.sax.SAXException;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import unicorn.withub.sxfyydbg.xml.XmlParser;


public class NormalResponseHandler implements ResponseHandler{
	public Bundle parseXml(String xml, Context context) throws SAXException,
			ParserConfigurationException, IOException {
		// TODO Auto-generated method stub
		XmlParser parser = new XmlParser();
		Bundle bundle = parser.parseResponse(xml);
		return bundle;
	}

}
