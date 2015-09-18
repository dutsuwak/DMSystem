package dataAccess.XML;

import java.io.File;
import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class pesoXML extends CrearXML {
	
	public pesoXML(String pTextRaiz){
		super(pTextRaiz);
		
		try {
						
			anadirElemento("source", "12312312");
			anadirElemento("target", "dd");
			anadirElemento("peso", "32");
		
			
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
	
	public static void main (String[] argsv)
	{
		@SuppressWarnings("unused")
		pesoXML d = new pesoXML("peso");
	}
	
}
