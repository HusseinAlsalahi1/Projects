import java.util.ListIterator;
import java.util.NoSuchElementException;

public class GLListIterator<E> implements ListIterator<E>{
	private GenericList<E>.Node<E> curr;
	private int index;
	// used to make this class behave more like a ListIterator.
	private boolean endWasReached;
	
	GLListIterator(int i, GenericList<E>.Node<E> head){
		this.curr = head;
		if(this.curr != null)
			for(this.index = 0; (this.index < i) && curr.next != null; this.index++) {
				curr = curr.next;
			}
		if(this.index < i - 1 || i < 0 || (head == null && i != 0))
			throw new NoSuchElementException();
		else if(head == null || this.index < i) {
			endWasReached = true;
		}
	}
	@Override
	public boolean hasNext() {
		if(curr == null)
			return false;
		else if(endWasReached && curr.next == null)
			return false;
		else
			return true;
	}

	@Override
	public E next() {
		E val;
		if(curr == null || endWasReached && curr.next == null)
			throw new NoSuchElementException();
		else if(endWasReached) {
			curr = curr.next;
			val = curr.data;
			index++;
		}
		else {
			val = curr.data;
			if(curr.next == null)
				endWasReached = true;
			else {
				curr = curr.next;
				index++;
			}
		}
		return val;
	}

	@Override
	public boolean hasPrevious() {
		if(curr == null)
			return false;
		else if(!endWasReached && curr.prev == null)
			return false;
		else
			return true;
	}

	@Override
	public E previous() {
		E val;
		if(curr == null || !endWasReached && curr.prev == null)
			throw new NoSuchElementException();
		else if(!endWasReached) {
			curr = curr.prev;
			val = curr.data;
			index--;
		}
		else {
			val = curr.data;
			if(curr.prev == null)
				endWasReached = false;
			else {
				curr = curr.prev;
				index--;
			}
		}
		return val;
	}

	@Override
	public int nextIndex() {
		if(curr == null)
			return 0;
		return this.index + 1;
	}

	@Override
	public int previousIndex() {
		if(curr == null)
			return -1;
		if(endWasReached)
			return this.index;
		return this.index - 1;
	}

	@Override
	public void remove() {}
	@Override
	public void set(E e) {}
	@Override
	public void add(E e) {}

}
