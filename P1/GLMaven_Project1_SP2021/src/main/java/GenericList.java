import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

public abstract class GenericList<T> implements Iterable<T> {
	
	private Node<T> head;
	private int length;
	
	public class Node<D>{
		D data;
		Node<D> next;
		Node<D> prev;
		public Node(D val) {
			data = val;
		}
	}
	public void print() {
		if(this.head == null) 
			System.out.println("Empty List");
		else {
			Node<T> temp = this.head;
			while(temp != null) {
				System.out.println(temp.data);
				temp = temp.next;
			}
		}
	}
	public abstract void add(T data);
	
	public T delete() {
		T hVal;
		if(this.head == null)
			return null;
		this.length--;
		hVal = this.head.data;
		if(this.length == 0)
			this.head = null;
		else {
			this.head = this.head.next;
			this.head.prev = null;
		}
		return hVal;
	}
	public ArrayList<T> dumpList(){
		ArrayList<T> al = new ArrayList<>();
		while(this.length > 0)
			al.add(this.delete());
		return al;
	}
	public T get(int index) {
		Node<T> temp = this.head;
		if(index >= this.length || index < 0)
			return null;
		for(int i = 0; i < index; i++)
			temp = temp.next;
		return temp.data;
	}
	
	public T set(int index, T element) {
		Node<T> temp = this.head;
		T oldData;
		if(index >= length || index < 0)
			return null;
		for(int i = 0; i < index; i++)
			temp = temp.next;
		oldData = temp.data;
		temp.data = element;
		return oldData;
	}
	public int getLength() {
		return this.length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public Node<T> getHead() {
		return this.head;
	}
	public void setHead(Node<T> h) {
		this.head = h;
	}
	
	@Override
	public Iterator<T> iterator() {
		return new GLLIterator<T>(this.head);
	}
	public ListIterator<T> listIterator( int index){
		return new GLListIterator<T>(index, this.head);
	}
	public Iterator<T> descendingIterator(){
		return new ReverseGLLIterator<T>(this.head);
	}
}
