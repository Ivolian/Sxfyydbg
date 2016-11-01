package unicorn.withub.sxfyydbg.data;

import android.content.Context;
import android.os.Bundle;

import org.xml.sax.SAXException;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

public interface ResponseHandler {
	public Bundle parseXml(String xml, Context context) throws SAXException,
	ParserConfigurationException, IOException;

}
