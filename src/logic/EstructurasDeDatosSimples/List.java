package logic.EstructurasDeDatosSimples;

public class List <k> {

	protected Node<k> _head;
	protected Node<k> _pointer;
	protected int counter=0;
	
	//constructor
	public List() {
		this._head = null;
		this._pointer = null;
		
	}
	public void insertHead(k pData)
	{
		counter++;
		if (_head == null){
			_head = _pointer = new Node<k>(pData);
		}
		else
		{
			Node<k> tmp = new Node<k>(pData);
			_head.setPrevious(tmp);
			tmp.setNext(_head);
			_head = tmp;
			//_head.setPrevious(_pointer);
			//_pointer.setNext(_head);
			
			
		}
		
	}

	
	public void insertPointer(k pData) {
		counter++;
		if (_head == null){
			_head = _pointer = new Node<k>(pData);
		}
		else{
			Node<k> tmp = new Node<k>(pData);
			_pointer.setNext(tmp);
			tmp.setPrevious(_pointer);
			_pointer = tmp;
			//_pointer.setNext(_head);
			//_head.setPrevious(_pointer);
		}
	}
	
	
	
	public boolean findData(k pData){
		Node<k> tmp = _head;
		while (tmp != _pointer){
			if (tmp.getData().equals(pData)){
				//System.out.println(true);
				return true;
			}
			else{
				tmp = tmp.getNext();
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @param pIndice: el indice del dato que se desea buscar
	 * @return Devuelve el dato que se encuentra en ese indice
	 * @throws IndexOutOfBoundsException
	 */
	public k obtener(int pIndice) throws IndexOutOfBoundsException {
        if (pIndice < 0) 
            throw new IndexOutOfBoundsException("Indice no puede ser negativo: " + pIndice);
        if (pIndice >= this.counter)
            throw new IndexOutOfBoundsException("Tamano Cadena: " + this.counter + " <= " + pIndice);
        
        int idxActual = 0;
        Node<k> actual = this._head;
        while(idxActual < pIndice) {
            actual = actual.getNext();
            idxActual++;
        }
        
        return actual.getData();
    }
	
	public void showData(){
		Node<k> tmp = _head;
		while (tmp.getNext() != null){
			System.out.println(tmp.getData());
			tmp = tmp.getNext();
		}
		System.out.println(tmp.getData());
	}
	
	public void delete(k pData){
		if (_head == null) {
			System.out.println("la lista es vacia");
			return;
		}
		
		if (_head.getData().equals(pData) ){
			_head = _head.getNext();
			_pointer.setNext(_head);
			counter--;
			return;
		}
		Node<k> tmp = _head;
		while (tmp.getNext() != _head & tmp.getNext().getData() != pData){
			tmp = tmp.getNext();
		}
		if (tmp.getNext() == _head) {
			//System.out.println("Delete  " + tmp.getData()); // CAUSA ERROR DESCONOCIDO 404 
			return;
		}
		else{
			if (tmp.getNext() == _pointer){
				_pointer = tmp;
				_pointer.setNext(_head);
				counter--;
				return;
			}
			else {
				tmp.setNext(tmp.getNext().getNext());
				counter--;
			}
		}
	}
	
	public boolean exist(k obj) {
    	Node<k> actual = _head;
        while (actual != null) {
            if (actual.getData().equals(obj))
                return true;
            actual = actual.getNext();
        }
        return false;
    }
	
	public Node<k> getHead(){
		return _head;
	}
	
	public Node<k> getPointer(){
		return _pointer;
	}
	public int length(){
		return counter;
	}
	
	public boolean isEmpty() {
        return _head == null;
    }
	
	public void Swap(Node<k> firstElement, Node<k> secondElement){
		Node<k> tmp = firstElement;
		k tmp3 = firstElement.getData();
		while(tmp.getNext() != _head){
			if (tmp == firstElement){
				firstElement.setData(secondElement.getData());
				secondElement.setData(tmp3);
			}
			tmp = tmp.getNext();
		}
	}	
}
