package dataAccess.XPATH;

import java.io.File;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class pesoXML extends CrearXML {
	
	public pesoXML(String pTextRaiz, String pSource, String pTarget, String pCosto){
		super(pTextRaiz);
		
		try {
						
			anadirElemento("source", pSource);
			anadirElemento("target", pTarget);
			anadirElemento("peso", pCosto);
		
			
			/**
			 *  Crea el XML
			 */
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
		    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			
			StreamResult result = new StreamResult(new StringWriter());
			StreamResult result2 = new StreamResult(new File("peso.xml"));
		    DOMSource source2 = new DOMSource(documentoXML);
		    transformer.transform(source2, result);
		    transformer.transform(source2, result2);

		    String xmlOutput = result.getWriter().toString();
		    System.out.println(xmlOutput);
		    
		    
		    /**
			 * Excepciones Que se pueden presentar
			 */
		
			  } catch (TransformerException tfe) {
				tfe.printStackTrace();
			  }
		
			
	}
	
}
