package dataAccess.XPATH;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import logic.EstructurasDeDatosSimples.List;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;



public class ReadXml {
	
	private String archivoXML;
	
	private List<String> listDispositivo;
	private List<String> listConexiones;

	XPath xpath;
	XPathFactory xpathfactory;
   
   
    
	public ReadXml(String pTipo){
		this.archivoXML = pTipo;
		
		xpathfactory = XPathFactory.newInstance();
		xpath= xpathfactory.newXPath();
		listConexiones = new List<>();
		listDispositivo = new List<>();	
		
	}
        
	
  
	public List<String> getDispositivo(){
		try{
			
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(archivoXML);
		
        XPathExpression _puerto = xpath.compile("grafo/dispositivos/dispositivo/@puerto");
        XPathExpression _tipo = xpath.compile("grafo/dispositivos/dispositivo/@tipo");
        XPathExpression _ID = xpath.compile("grafo/dispositivos/dispositivo/@id");
        
        Object puertos = _puerto.evaluate(doc, XPathConstants.NODESET);
        Object tipos = _tipo.evaluate(doc, XPathConstants.NODESET);
        Object ids = _ID.evaluate(doc, XPathConstants.NODESET);
        
        NodeList puertosList = (NodeList) puertos;
        NodeList tiposList = (NodeList) tipos;
        NodeList idsList = (NodeList) ids;
        
        for(int i = 0; i < puertosList.getLength(); i++){
        	
        	String puerto = puertosList.item(i).getNodeValue();
        	String tipo = tiposList.item(i).getNodeValue();
        	String id = idsList.item(i).getNodeValue();
        	
        	String dispositivo = puerto +"#"+tipo+"#"+id;
        	//System.out.println(dispositivo);
        	listDispositivo.insertPointer(dispositivo);
        }
        
		}
		catch(Exception e)
		{
			e.printStackTrace();
		
		}
		
		return listDispositivo;
		
		
        
	}
    
	public List<String> getConexiones(){
		
		try{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true); 
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(archivoXML);
		
        XPathExpression source = xpath.compile("grafo/conexiones/conexion/@source");
        XPathExpression target = xpath.compile("grafo/conexiones/conexion/@target");
        XPathExpression precio = xpath.compile("grafo/conexiones/conexion/@precio");
        
        Object sourceNodos = source.evaluate(doc, XPathConstants.NODESET);
        Object targetNodos = target.evaluate(doc, XPathConstants.NODESET);
        Object precioNodos = precio.evaluate(doc, XPathConstants.NODESET);
        
        NodeList nodosSources = (NodeList) sourceNodos;
        NodeList nodosTarget = (NodeList) targetNodos;
        NodeList nodosprecio = (NodeList) precioNodos;
        
        for(int i = 0; i < nodosprecio.getLength(); i++){
        	
        	String _source = nodosSources.item(i).getNodeValue();
        	String _target = nodosTarget.item(i).getNodeValue();
        	String _precio = nodosprecio.item(i).getNodeValue();
        	
        	String conexion = _source+"#"+_target+"#"+_precio;
        	
        	//System.out.println(conexion);
       
        	listConexiones.insertPointer(conexion);    	
        	}
        
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
			
		}
		return listConexiones;
		
		
        
	}
	
	
	
}
