package dataAccess.XPATH;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.Node;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

public class seConectaUPDATExml {
	Document documentoXML;
	
	public seConectaUPDATExml(String pSourceID,String pTargetID)
	{
		try {
		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance(); 
	    domFactory.setIgnoringComments(true);
	    DocumentBuilder builder = domFactory.newDocumentBuilder();
	    documentoXML = builder.parse(new File("grafo.xml")); 
		
	    int NumeroRandom = (int) Math.floor(Math.random()* (0-1000) + (10000));
		insertBefore("dispositivos", pSourceID+"-"+NumeroRandom, "updateid");
		insertBefore("updateid", pTargetID, "target");
		insertBefore("target", pSourceID, "source");
		
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
	    transformer.setOutputProperty(OutputKeys.INDENT, "yes");

	    StreamResult result = new StreamResult(new StringWriter());
	    StreamResult result2 = new StreamResult(new File("dispositivoBaseGrafo.xml"));
	    DOMSource source = new DOMSource(documentoXML);
	    
	    transformer.transform(source, result);
	    transformer.transform(source, result2);

	    String xmlOutput = result.getWriter().toString();
	    System.out.println(xmlOutput);

	} catch (ParserConfigurationException pce) {
		pce.printStackTrace();
	  } catch (TransformerException tfe) {
		tfe.printStackTrace();
	  } catch (SAXException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
	}
	

	public void insertBefore(String antesDE,String pTextElement, String pElementTitulo)
	{
		 NodeList nodes = documentoXML.getElementsByTagName(antesDE);
		 Text textElement = documentoXML.createTextNode(pTextElement); 
		 Element elementoInsertar = documentoXML.createElement(pElementTitulo); 
		 elementoInsertar.appendChild(textElement); 
		 
		 nodes.item(0).getParentNode().insertBefore(elementoInsertar, nodes.item(0));
		
	}
	
	
}
