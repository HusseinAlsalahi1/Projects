import java.util.Iterator;
import java.util.NoSuchElementException;

public class ReverseGLLIterator<E> implements Iterator<E>{
	private GenericList<E>.Node<E> curr;
	
	public ReverseGLLIterator(GenericList<E>.Node<E> first) {
        curr = first;
        if(curr != null)
        	while(curr.next != null)
        		curr = curr.next;
    }
	@Override
	public boolean hasNext() {
		return curr != null;
	}
	
	@Override
	public E next() {
		if(!hasNext())
			throw new NoSuchElementException();
		E val = curr.data;
		curr = curr.prev;
		return val;
	}

}
