public class GenericStack<T> extends GenericList<T>{
	private Node<T> tail;
	
	GenericStack(T data){
		this.tail = new Node<T>(data);
		this.setHead(this.tail);
		this.setLength(1);
	}
	
	public T removeTail() {
		if(this.tail == null)
			return null;
		else if(this.tail == this.getHead())
			return pop();
		else {
			this.setLength(this.getLength() - 1);
			T tVal = this.tail.data;
			this.tail = this.tail.prev;
			this.tail.next = null;
			return tVal;
		}
	}
	public void push(T data) {
		this.add(data);
	}
	public T pop() {
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
			Node<T> top = new Node<T>(data);
			top.next = this.getHead();
			this.setHead(top);
			top.next.prev = top; // reverse link
		}
	}
}

