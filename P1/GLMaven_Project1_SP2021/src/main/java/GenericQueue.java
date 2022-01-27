
public class GenericQueue<T> extends GenericList<T> {
	private Node<T> tail;
	
	GenericQueue(T data){
		this.tail = new Node<T>(data);
		this.setHead(this.tail);
		this.setLength(1);
	}
	public T removeTail() {
		if(this.tail == null)
			return null;
		else if(this.getLength() == 1)
			return dequeue();
		else {
			this.setLength(this.getLength() - 1);
			T tVal = this.tail.data;
			this.tail = this.tail.prev;
			this.tail.next = null;
			return tVal;
		}
	}
	public void enqueue(T data) {
		this.add(data);
	}
	public T dequeue() {
		return this.delete();
	}
	
	@Override
	public void add(T data) {
		this.setLength(this.getLength() + 1);
		if(this.getHead() == null) {
			this.tail = new Node<T>(data);
			this.setHead(this.tail);
		}
		else {
			this.tail.next = new Node<T>(data);
			this.tail.next.prev = this.tail;
			this.tail = this.tail.next;
		}
	}

}

