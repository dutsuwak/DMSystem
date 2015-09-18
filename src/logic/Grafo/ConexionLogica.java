package logic.Grafo;

public class ConexionLogica {
	
	String _fromID;
	String _toID;
	int _precio;
	
	public ConexionLogica(String pFromID, String pToID,int pPrecio)
	{
		_fromID = pFromID;
		_toID = pToID;
		_precio = pPrecio;
	}
	
	
	/**
	 * @return _fromID: El Id del dispositvo de donde llega la conexion
	 */
	public String get_fromID() {
		return _fromID;
	}

	/**
	 * @param pFromID: El Id del dispostivo hacia el cual va la conexion
	 */
	public void set_fromID(String pFromID) {
		this._fromID = pFromID;
	}

	/**
	 * @return _toID: el ID del dispositivo de donde viene la conexion
	 */
	public String get_toID() {
		return _toID;
	}

	/**
	 * @param pToID: El Id del nodo de donde viene la conexion
	 */
	public void set_toID(String pToID) {
		this._toID = pToID;
	}

	/**
	 * @return _precio: el precio que cuesta ir del nodo de inicio al de llegada
	 */
	public int get_precio() {
		return _precio;
	}

	/**
	 * @param pPrecio: el precio que se desea fijar al precio de la conexion 
	 */
	public void set_precio(int pPrecio) {
		this._precio = pPrecio;
	}

	

}
