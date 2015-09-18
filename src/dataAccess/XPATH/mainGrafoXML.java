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

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class mainGrafoXML {
	
	private static Document documentoXML;
	private Element grafo;
	private Element dispositivos;
	private Element conexiones;
	
	mainGrafoXML()
	{	
		try {
			
			DocumentBuilderFactory instanciaDocumento = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = instanciaDocumento.newDocumentBuilder();
			
			documentoXML = docBuilder.newDocument();
			
			// root elements
			 
			grafo = documentoXML.createElement("grafo");
			documentoXML.appendChild(grafo);
	 
			/**
			 * dispositivos: elemento Padre
			 */
			dispositivos = documentoXML.createElement("dispositivos");
			grafo.appendChild(dispositivos);
			
			/**
			 * Dispositivos
			 */
			anadirDispositivo("1234567890123456","cliente","7789");
			anadirDispositivo("1234567890123457","cliente","7788");
			anadirDispositivo("1234567890123458","hub","65432");
			anadirDispositivo("1234567890123459","base","2001");
			anadirDispositivo("1234567890123450","hub","9987");
			anadirDispositivo("1234567890123451","base","666");
			
			
			/**
			 *  Conexiones: element Padre
			 */
			conexiones = documentoXML.createElement("conexiones");
			grafo.appendChild(conexiones);
			
			/**
			 * Conexiones
			 */
			
			anadirConexion("1234567890123456", "1234567890123457", "10");
			anadirConexion("1234567890123457", "1234567890123456", "4");
			anadirConexion("1234567890123458", "1234567890123451", "6");
			anadirConexion("1234567890123458", "1234567890123450", "11");
			anadirConexion("1234567890123458", "1234567890123459", "10");
			anadirConexion("1234567890123451", "1234567890123456", "3");
			anadirConexion("1234567890123451", "1234567890123458", "9");
			anadirConexion("1234567890123450", "1234567890123457", "8");
			anadirConexion("1234567890123456", "1234567890123450", "7");
				
			
			/**
			 *  Crea el XML
			 */
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
		    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			
			StreamResult result = new StreamResult(new StringWriter());
			StreamResult result2 = new StreamResult(new File("grafo.xml"));
			
		    DOMSource source2 = new DOMSource(documentoXML);
		    transformer.transform(source2, result);
		    transformer.transform(source2, result2);

		    String xmlOutput = result.getWriter().toString();
		    System.out.println(xmlOutput);
			
		/**
		 * Excepciones Que se pueden presentar
		 */
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		  } catch (TransformerException tfe) {
			tfe.printStackTrace();
		  }
		
		
	}
	
	/**
	 * Metodo para agregar una nueva conexion al grafo
	 * @param pSource: Dispositivo de salida de la conexion
	 * @param pTarget: Dispositivo de llegada de la conexion
	 * @param pCosto: Precio de la conexion
	 */
	public void anadirConexion(String pSource, String pTarget, String pCosto)
	{
		/**
		 * elemento Conexion
		 */
		Element conexion = documentoXML.createElement("conexion");
		conexiones.appendChild(conexion);
		
		/**
		 * atributo source
		 */
		Attr source = documentoXML.createAttribute("source");
		source.setValue(pSource);
		conexion.setAttributeNode(source);
		
		/**
		 * atributo target
		 */
		Attr target = documentoXML.createAttribute("target");
		target.setValue(pTarget);
		conexion.setAttributeNode(target);
		
		/**
		 * atributo precio
		 */
		Attr precio = documentoXML.createAttribute("precio");
		precio.setValue(pCosto);
		conexion.setAttributeNode(precio);
		
		
		
	}
	
	public void anadirDispositivo(String pID, String pTipo, String pPuerto)
	{
		/**
		 * Dispositivo 1
		 */
		Element dispositivo = documentoXML.createElement("dispositivo");
		dispositivos.appendChild(dispositivo);

		/**
		 * Atributo "Id" Dispositivo 1
		 */
		Attr aid = documentoXML.createAttribute("id");
		aid.setValue(pID);
		dispositivo.setAttributeNode(aid);
		
		/**
		 * Atributo "Tipo" Dispositivo 1
		 */
		Attr btipo = documentoXML.createAttribute("tipo");
		btipo.setValue(pTipo);
		dispositivo.setAttributeNode(btipo);
		
		/**
		 * Atributo "Puerto"" Dispositivo 1
		 */
		Attr puerto = documentoXML.createAttribute("puerto");
		puerto.setValue(pPuerto);
		dispositivo.setAttributeNode(puerto);
		
	}
		
	 public static void main(String[] argsv){
	 
		 mainGrafoXML d = new mainGrafoXML(); 
			
	 }
	 
	 
	 
	}


