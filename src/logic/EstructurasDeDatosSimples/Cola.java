package logic.EstructurasDeDatosSimples;


public class Cola<k> extends List<k> {

    public Cola() {
        super();
    }

    public void enQueue(k obj) {
        this.insertPointer(obj);
    }

    public k deQueue() {
        if (_pointer == null)
            return null;

        Node<k> n = _pointer;
        _pointer = _pointer.getPrevious();
        if (_pointer != null)
        	_pointer.setNext(null);
        else
            _head = _pointer;

        return n.getData();
    }
}