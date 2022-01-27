import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GQTest {
	private GenericQueue<Integer> gQI;
	private GenericQueue<String> gQS;
	@BeforeEach
	void innitEach() {
		gQS = new GenericQueue<>("one");
		gQI = new GenericQueue<>(1);
		gQI.enqueue(2);
		gQI.enqueue(3);
		gQI.enqueue(4);
		gQI.enqueue(5);
	}
	
	@Test
	void getLengthTest() {
		assertEquals(5, gQI.getLength());
		gQI.dumpList();
		assertEquals(0, gQI.getLength());
		gQI.dequeue();
		assertEquals(0, gQI.getLength());
		
		assertEquals(1, gQS.getLength());
	}
	@Test
	void setLengthTest() {
		assertEquals(5, gQI.getLength());
		gQI.setLength(100);
		assertEquals(100, gQI.getLength());
		gQI.dequeue();
		assertEquals(99, gQI.getLength());
		gQI.setLength(-4);
		assertEquals(-4, gQI.getLength());
	}
	@Test 
	void getHeadTest() {
		assertEquals(1,gQI.getHead().data);
		gQI.dequeue();
		assertEquals(2,gQI.getHead().data);
		gQI.dumpList();
		assertEquals(null,gQI.getHead());
		
		assertEquals("one",gQS.getHead().data);
	}
	@Test
	void enqueueTest() {
		Integer[] eList = {1,2,3,4,5};
		assertArrayEquals(eList, gQI.dumpList().toArray());
		
		gQS.enqueue("two");
		assertEquals(2, gQS.getLength());
		assertEquals("one", gQS.dequeue());
		assertEquals("two", gQS.dequeue());
		assertEquals(0, gQS.getLength());
	}
	@Test
	void dequeueTest() {
		assertEquals(1, gQI.dequeue());
		assertEquals(2, gQI.dequeue());
		assertEquals(3, gQI.getLength());
		assertEquals(3,gQI.getHead().data);
		assertEquals(3, gQI.dequeue());
		assertEquals(4, gQI.dequeue());
		assertEquals(5, gQI.dequeue());
		assertEquals(null, gQI.dequeue());
		
		assertEquals("one", gQS.dequeue());
		assertEquals(null, gQS.getHead());
		assertEquals(null, gQS.dequeue());
		assertEquals(0, gQS.getLength());
	}
	@Test
	void dumpListTest() {
		Integer[] aList = {1,2,3,4,5};
		ArrayList<Integer> bList = new ArrayList<>();
		Integer[] cList = {11,22,33};
		
		assertArrayEquals(aList, gQI.dumpList().toArray());
		assertEquals(0, gQI.getLength());
		assertEquals(null, gQI.getHead());
		assertArrayEquals(bList.toArray(), gQI.dumpList().toArray());
		assertEquals(0, gQI.getLength());
		assertEquals(null, gQI.getHead());
		gQI.enqueue(11);
		gQI.enqueue(22);
		gQI.enqueue(33);
		assertEquals(3, gQI.getLength());
		assertEquals(11, gQI.getHead().data);
		assertArrayEquals(cList, gQI.dumpList().toArray());
		assertEquals(0, gQI.getLength());
		assertEquals(null, gQI.getHead());
	}
	@Test
	void getTest() {
		for(int i = 0; i<gQI.getLength(); i++)
			assertEquals(i+1, gQI.get(i));
		assertEquals(null, gQI.get(-1));
		assertEquals(null, gQI.get(5));
		
		gQS.dequeue();
		assertEquals(null, gQS.get(-1));
		assertEquals(null, gQS.get(0));
		assertEquals(null, gQS.get(1));
	}
	@Test
	void setTest() {
		Integer[] aList = {11,22,33,44,55};
		
		for(int i = 0; i<gQI.getLength(); i++)
			assertEquals(i+1, gQI.set(i, i*10 + i+11));
		assertEquals(null, gQI.set(-1, -100));
		assertEquals(null, gQI.set(5, 500));
		assertArrayEquals(aList, gQI.dumpList().toArray());
		
		gQS.dumpList();
		assertEquals(null, gQS.set(-1, "Unpredeted1"));
		assertEquals(null, gQS.set(0, "Unpredeted2"));
		assertEquals(null, gQS.set(1, "Unpredeted3"));
	}
	@Test
	void removeTailTest() {
		Integer[] aList = {1,2,3};
		assertEquals(5,gQI.removeTail());
		assertEquals(4,gQI.removeTail());
		assertEquals(3,gQI.getLength());
		assertEquals(1,gQI.getHead().data);
		assertArrayEquals(aList, gQI.dumpList().toArray());
		assertEquals(0, gQI.getLength());
		
		assertEquals("one", gQS.removeTail());
		assertEquals(null, gQS.getHead());
		assertEquals(0, gQS.getLength());
		gQS.enqueue("two");
		assertEquals("two", gQS.getHead().data);
		assertEquals("two", gQS.removeTail());
		assertEquals(0, gQS.getLength());
		assertEquals(null, gQS.getHead());
	}
	@Test
	void iteratorTest() {
		int num = 0;
		int i = 1;
		for(int v : gQI) {
			assertEquals(i, v);
			i++;
			num++;
		}
		assertEquals(5, num);
		gQS.dequeue();
		for(String s : gQS) {
			assertEquals("sfcjkj", s);
		}
	}
	@Test
	void descendingIteratorTest() {
		Iterator<Integer> rIterI = gQI.descendingIterator();
		int i = 5;
		while(rIterI.hasNext()) {
			assertEquals(i, rIterI.next());
			i--;
		}
		assertThrows(Exception.class, () -> rIterI.next());
		Iterator<String> rIterS = gQS.descendingIterator();
		i = 5;
		while(rIterS.hasNext()) {
			assertEquals("one", rIterS.next());
			i--;
		}
		assertEquals(4, i);
		assertThrows(Exception.class, () -> rIterS.next());
		gQS.dequeue();
		Iterator<String> rIterS2 = gQS.descendingIterator();
		assertEquals(null, gQS.getHead());
		assertEquals(false, rIterS2.hasNext());
		assertThrows(Exception.class, () -> rIterS2.next());
	}
	@Test
	void listIteratorTest() {
		ListIterator<Integer> listIterI = gQI.listIterator(3);
		assertEquals(true, listIterI.hasNext());
		assertEquals(4, listIterI.nextIndex());
		assertEquals(4, listIterI.next());
		assertEquals(true, listIterI.hasNext());
		assertEquals(5, listIterI.nextIndex());
		assertEquals(5, listIterI.next());
		assertEquals(false, listIterI.hasNext());
		assertThrows(Exception.class, () -> listIterI.next());
		assertEquals(false, listIterI.hasNext());
		assertEquals(5, listIterI.nextIndex());
		int i = 5;
		while(listIterI.hasPrevious()) {
			assertEquals(i-1, listIterI.previousIndex());
			assertEquals(i, listIterI.previous());
			i--;
		}
		assertEquals(0,i);
		assertEquals(-1, listIterI.previousIndex());
		assertThrows(Exception.class, () -> listIterI.previous());
		
		ListIterator<String> listIterS = gQS.listIterator(0);
		assertThrows(Exception.class, () -> gQS.listIterator(2));
		assertThrows(Exception.class, () -> gQS.listIterator(-1));
		assertEquals(true, listIterS.hasNext());
		assertEquals(false, listIterS.hasPrevious());
		assertEquals(1, listIterS.nextIndex());
		assertEquals("one", listIterS.next());
		
		assertEquals(false, listIterS.hasNext());
		assertThrows(Exception.class, () -> listIterS.next());
		assertEquals(true, listIterS.hasPrevious());
		assertEquals(0, listIterS.previousIndex());
		assertEquals("one", listIterS.previous());
		assertEquals(-1, listIterS.previousIndex());
		assertThrows(Exception.class, () -> listIterS.previous());
		
		ListIterator<String> listIterS2 = gQS.listIterator(1);
		assertEquals(false, listIterS2.hasNext());
		assertEquals(1, listIterS2.nextIndex());
		assertThrows(Exception.class, () -> listIterS2.next());
		assertEquals(true, listIterS2.hasPrevious());
		assertEquals(0, listIterS2.previousIndex());
		assertEquals("one", listIterS2.previous());
		assertEquals(-1, listIterS2.previousIndex());
		assertThrows(Exception.class, () -> listIterS2.previous());
		
		gQS.dequeue();
		assertEquals(null, gQS.getHead());
		assertThrows(Exception.class, () -> gQS.listIterator(1));
		assertThrows(Exception.class, () -> gQS.listIterator(-1));
		ListIterator<String> listIterS3 = gQS.listIterator(0);
		assertEquals(false, listIterS3.hasNext());
		assertEquals(false, listIterS3.hasPrevious());
		assertEquals(0, listIterS3.nextIndex());
		assertEquals(-1, listIterS3.previousIndex());
		assertThrows(Exception.class, () -> listIterS3.next());
		assertThrows(Exception.class, () -> listIterS3.previous());
	}
}
