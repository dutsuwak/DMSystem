package logic.EstructurasDeDatosSimples;


import java.util.EmptyStackException;


public class Pila<k> extends List<k> {

    public Pila() {
        super();
    }

    public void push(k pDato) {
        this.insertPointer(pDato);
    }

    public k pop() throws EmptyStackException {
        if (_head == null)
            throw new EmptyStackException();
        
        Node<k> n = _head;
        _head = _head.getNext();
        if (_head != null)
        	_head.setPrevious(null);
        else
            _pointer = _head;

        return n.getData();
    }
}
