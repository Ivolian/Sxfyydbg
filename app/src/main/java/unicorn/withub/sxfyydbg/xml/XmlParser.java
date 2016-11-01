package unicorn.withub.sxfyydbg.xml;

import android.os.Bundle;
import android.util.Log;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


public class XmlParser {
	public Bundle parseResponse(String infoXML) throws SAXException,
	IOException, ParserConfigurationException {
		// 创建处理文档内容相关事件的处理器
		ResponseContentHandler contentHandler = new ResponseContentHandler();
		// 创建一个XML解析器（通过SAX方式读取解析XML）
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader reader = sp.getXMLReader();
		// 设置XML解析器的处理文档内容相关事件的处理器
		reader.setContentHandler(contentHandler);
		// 解析books.xml文档
		// 创建一个新的字符串
		StringReader read = new StringReader(infoXML);
		try {
			reader.parse(new InputSource(read));
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("XmlParser.parseResponse", infoXML, e);
			throw new RuntimeException("响应报文解析失败");
		}
		
		return contentHandler.getBundle();
	}

}
