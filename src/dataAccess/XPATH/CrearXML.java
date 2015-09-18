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
import org.w3c.dom.Node;
	
	public class CrearXML {
		
	Document documentoXML;
	Element _raiz;
	private Element dispositivos;
	private Element conexiones;
	
	CrearXML(String pTextRaiz)
	{	
		try {
			
			DocumentBuilderFactory instanciaDocumento = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = instanciaDocumento.newDocumentBuilder();
			
			documentoXML = docBuilder.newDocument();
			
			// root elements
			 
			_raiz = documentoXML.createElement(pTextRaiz);
			documentoXML.appendChild(_raiz);
		}
		
		catch (ParserConfigurationException pce) {
		
			
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
		 * elemento a A�adir
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
	
	public void anadirElemento(String tituloID,String pNuevoNodoID)
	{
		Element nuevoElemento = documentoXML.createElement(tituloID);
		nuevoElemento.appendChild(documentoXML.createTextNode(pNuevoNodoID));
        _raiz.appendChild(nuevoElemento);
	}
	
	public void anadirElementoConAtributos(String tituloID, String pID,String pTituloTipo, 
			String pTipo,String pTituloPuerto, String pPuerto, String pTituloElemento)
	{
		/**
		 * Dispositivo 1
		 */
		Element dispositivo = documentoXML.createElement(pTituloElemento);
		_raiz.appendChild(dispositivo);

		/**
		 * Atributo "Id" Dispositivo 1
		 */
		Attr id = documentoXML.createAttribute(tituloID);
		id.setValue(pID);
		_raiz.setAttributeNode(id);
		
		/**
		 * Atributo "Tipo" Dispositivo 1
		 */
		Attr btipo = documentoXML.createAttribute(pTituloTipo);
		btipo.setValue(pTipo);
		_raiz.setAttributeNode(btipo);
		
		/**
		 * Atributo "Puerto"" Dispositivo 1
		 */
		Attr puerto = documentoXML.createAttribute("puerto");
		puerto.setValue(pPuerto);
		_raiz.setAttributeNode(puerto);
		
	}
		 
}

			
			
