package unicorn.withub.sxfyydbg.xml;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

public class BaseContentHandler implements ContentHandler{
	private String value = "";

	/*
	 * public void characters(char[] ch, int start, int length) throws
	 * SAXException { // TODO Auto-generated method stub StringBuffer buffer =
	 * new StringBuffer(); for (int i = start; i < start + length; i++) {
	 * 
	 * buffer.append(ch[i]); } value = buffer.toString();
	 * 
	 * }
	 */
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
		StringBuffer buffer = new StringBuffer();
		for (int i = start; i < start + length; i++) {
			switch (ch[i]) {
			// case '\\':
			// buffer.append("\\\\");
			// break;
			case '\r':
				buffer.append("\\r");
				break;
			// case '\n':
			// buffer.append("");
			// break;
			case '\t':
				buffer.append("\\t");
				break;
			// case '\"':
			// buffer.append("\\\"");
			// break;
			default:
				buffer.append(ch[i]);
			}
		}
		value += buffer.toString();
	}

	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub

	}

	public void endElement(String uri, String localName, String name)
			throws SAXException {
		// TODO Auto-generated method stub
		value = "";
	}

	public void endPrefixMapping(String prefix) throws SAXException {
		// TODO Auto-generated method stub

	}

	public void ignorableWhitespace(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub

	}

	public void processingInstruction(String target, String data)
			throws SAXException {
		// TODO Auto-generated method stub

	}

	public void setDocumentLocator(Locator locator) {
		// TODO Auto-generated method stub

	}

	public void skippedEntity(String name) throws SAXException {
		// TODO Auto-generated method stub

	}

	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub

	}

	public void startElement(String uri, String localName, String name,
			Attributes atts) throws SAXException {
		// TODO Auto-generated method stub
	}

	public void startPrefixMapping(String prefix, String uri)
			throws SAXException {
		// TODO Auto-generated method stub

	}

	protected String getValue() {
		if(value==null){
			value = "";
		}
		value = value.replace("\\n", "\n");
		value = value.trim();
		if (value.startsWith("\n")) {
			value = value.substring(2);
		}
		return value;
	}

	public Object getResult() {
		return null;
	}
}
