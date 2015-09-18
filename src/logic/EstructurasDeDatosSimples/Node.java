package logic.EstructurasDeDatosSimples;


public class Node<k> {
	
	private k _data;
	private Node<k> _next;
	private Node<k> _previous;
	

	public Node(k pData){
		_data = pData;
	}

	
	

	public Node<k> getNext() {return _next;}
	
	public void setNext(Node<k> next) { this._next = next;}
	
	public void setPrevious(Node<k> previous){this._previous = previous;}
	
	public Node<k> getPrevious() {return _previous;}
	
	public k getData() {return _data;}
	
	public void setData(k _data) { this._data = _data;}

}
