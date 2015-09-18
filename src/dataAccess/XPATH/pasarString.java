package dataAccess.XPATH;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.sun.xml.internal.txw2.Document;



public class pasarString {
	
	public pasarString() throws TransformerException{
		
	try{	
		String xml = "<resp><status>good</status><msg>hi</msg></resp>";
		
		InputSource source = new InputSource(new StringReader(xml));
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		org.w3c.dom.Document document = db.parse(source);
		
		XPathFactory xpathFactory = XPathFactory.newInstance();
		XPath xpath = xpathFactory.newXPath();
		
		String msg = xpath.evaluate("/resp/msg", document);
		String status = xpath.evaluate("/resp/status", document);
	}
	
	catch (ParserConfigurationException | XPathExpressionException | SAXException | IOException pce) {
		pce.printStackTrace();
	  }

	//System.out.println("msg=" + msg + ";" + "status=" + status);
	
	}
	

	
	
	
	//---------------------------------------------------------------------
/*	
	String xml = "<resp><status>good</status><msg>hi</msg></resp>";

	XPathFactory xpathFactory = XPathFactory.newInstance();
	XPath xpath = xpathFactory.newXPath();

	InputSource source = new InputSource(new StringReader(xml));
	Document doc = (Document) xpath.evaluate("/", source, XPathConstants.NODE);
	String status = xpath.evaluate("/resp/status", doc);
	String msg = xpath.evaluate("/resp/msg", doc);

	System.out.println("status=" + status);
	System.out.println("Message=" + msg);
	
	*/
	
}
