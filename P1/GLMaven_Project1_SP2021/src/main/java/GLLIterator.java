import java.util.Iterator;
import java.util.NoSuchElementException;

public class GLLIterator<E> implements Iterator<E>{
	private GenericList<E>.Node<E> curr;
	
	public GLLIterator(GenericList<E>.Node<E> start) {
        curr = start;
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
		curr = curr.next;
		return val;
	}
}
