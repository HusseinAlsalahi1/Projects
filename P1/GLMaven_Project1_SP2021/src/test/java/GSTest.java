	import static org.junit.jupiter.api.Assertions.assertArrayEquals;
	import static org.junit.jupiter.api.Assertions.assertEquals;
	import static org.junit.jupiter.api.Assertions.assertThrows;
	
	import java.util.ArrayList;
	import java.util.Iterator;
	import java.util.ListIterator;
	
	import org.junit.jupiter.api.BeforeEach;
	import org.junit.jupiter.api.Test;
	
	
	
public class GSTest {

	private GenericStack<Integer> gSI;
	private GenericStack<String> gSS;
	@BeforeEach
	void innitEach() {
		gSS = new GenericStack<>("one");
		gSI = new GenericStack<>(5);
		gSI.push(4);
		gSI.push(3);
		gSI.push(2);
		gSI.push(1);
	}
	
	@Test
	void getLengthTest() {
		assertEquals(5, gSI.getLength());
		gSI.dumpList();
		assertEquals(0, gSI.getLength());
		gSI.pop();
		assertEquals(0, gSI.getLength());
		
		assertEquals(1, gSS.getLength());
	}
	@Test
	void setLengthTest() {
		assertEquals(5, gSI.getLength());
		gSI.setLength(100);
		assertEquals(100, gSI.getLength());
		gSI.pop();
		assertEquals(99, gSI.getLength());
		gSI.setLength(-4);
		assertEquals(-4, gSI.getLength());
	}
	@Test 
	void getHeadTest() {
		assertEquals(1,gSI.getHead().data);
		gSI.pop();
		assertEquals(2,gSI.getHead().data);
		gSI.dumpList();
		assertEquals(null,gSI.getHead());
		
		assertEquals("one",gSS.getHead().data);
	}
	@Test
	void pushTest() {
		Integer[] eList = {1,2,3,4,5};
		assertArrayEquals(eList, gSI.dumpList().toArray());
		
		gSS.push("two");
		assertEquals(2, gSS.getLength());
		assertEquals("two", gSS.pop());
		assertEquals("one", gSS.pop());
		assertEquals(0, gSS.getLength());
	}
	@Test
	void popTest() {
		assertEquals(1, gSI.pop());
		assertEquals(2, gSI.pop());
		assertEquals(3, gSI.getLength());
		assertEquals(3,gSI.getHead().data);
		assertEquals(3, gSI.pop());
		assertEquals(4, gSI.pop());
		assertEquals(5, gSI.pop());
		assertEquals(null, gSI.pop());
		assertEquals(0, gSI.getLength());
		
		assertEquals("one", gSS.pop());
		assertEquals(null, gSS.getHead());
		assertEquals(null, gSS.pop());
		assertEquals(0, gSS.getLength());
	}
	@Test
	void dumpListTest() {
		Integer[] aList = {1,2,3,4,5};
		ArrayList<Integer> bList = new ArrayList<>();
		Integer[] cList = {11,22,33};
		
		assertArrayEquals(aList, gSI.dumpList().toArray());
		assertEquals(0, gSI.getLength());
		assertEquals(null, gSI.getHead());
		assertArrayEquals(bList.toArray(), gSI.dumpList().toArray());
		assertEquals(0, gSI.getLength());
		assertEquals(null, gSI.getHead());
		gSI.push(33);
		gSI.push(22);
		gSI.push(11);
		assertEquals(3, gSI.getLength());
		assertEquals(11, gSI.getHead().data);
		assertArrayEquals(cList, gSI.dumpList().toArray());
		assertEquals(0, gSI.getLength());
		assertEquals(null, gSI.getHead());
	}
	@Test
	void getTest() {
		for(int i = 0; i < 5; i++)
			assertEquals(i+1, gSI.get(i));
		assertEquals(null, gSI.get(-1));
		assertEquals(null, gSI.get(5));
		
		gSS.pop();
		assertEquals(null, gSS.get(-1));
		assertEquals(null, gSS.get(0));
		assertEquals(null, gSS.get(1));
	}
	@Test
	void setTest() {
		Integer[] aList = {11,22,33,44,55};
		
		for(int i = 0; i < 5; i++)
			assertEquals(i+1, gSI.set(i, i*10 + i+11));
		assertEquals(null, gSI.set(-1, -100));
		assertEquals(null, gSI.set(5, 500));
		assertArrayEquals(aList, gSI.dumpList().toArray());
		
		gSS.dumpList();
		assertEquals(null, gSS.set(-1, "Unpredeted1"));
		assertEquals(null, gSS.set(0, "Unpredeted2"));
		assertEquals(null, gSS.set(1, "Unpredeted3"));
	}
	@Test
	void removeTailTest() {
		Integer[] aList = {1,2,3};
		assertEquals(5,gSI.removeTail());
		assertEquals(4,gSI.removeTail());
		assertEquals(3,gSI.getLength());
		assertEquals(1,gSI.getHead().data);
		assertArrayEquals(aList, gSI.dumpList().toArray());
		assertEquals(0, gSI.getLength());
		
		assertEquals("one", gSS.removeTail());
		assertEquals(null, gSS.getHead());
		assertEquals(0, gSS.getLength());
		gSS.push("two");
		assertEquals("two", gSS.getHead().data);
		assertEquals("two", gSS.removeTail());
		assertEquals(0, gSS.getLength());
		assertEquals(null, gSS.getHead());
	}
	@Test
	void iteratorTest() {
		int num = 0;
		int i = 1;
		for(int v : gSI) {
			assertEquals(i, v);
			i++;
			num++;
		}
		assertEquals(5, num);
		gSS.pop();
		for(String s : gSS) {
			assertEquals("sfcjkj", s);
		}
	}
	@Test
	void descendingIteratorTest() {
		Iterator<Integer> rIterI = gSI.descendingIterator();
		int i = 5;
		while(rIterI.hasNext()) {
			assertEquals(i, rIterI.next());
			i--;
		}
		assertThrows(Exception.class, () -> rIterI.next());
		Iterator<String> rIterS = gSS.descendingIterator();
		i = 5;
		while(rIterS.hasNext()) {
			assertEquals("one", rIterS.next());
			i--;
		}
		assertEquals(4, i);
		assertThrows(Exception.class, () -> rIterS.next());
		gSS.pop();
		Iterator<String> rIterS2 = gSS.descendingIterator();
		assertEquals(null, gSS.getHead());
		assertEquals(false, rIterS2.hasNext());
		assertThrows(Exception.class, () -> rIterS2.next());
	}
	@Test
	void listIteratorTest() {
		ListIterator<Integer> listIterI = gSI.listIterator(3);
		assertEquals(true, listIterI.hasNext());
		assertEquals(4, listIterI.nextIndex());
		assertEquals(4, listIterI.next());
		assertEquals(4, listIterI.previous());
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
		
		ListIterator<String> listIterS = gSS.listIterator(0);
		assertThrows(Exception.class, () -> gSS.listIterator(2));
		assertThrows(Exception.class, () -> gSS.listIterator(-1));
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
		
		ListIterator<String> listIterS2 = gSS.listIterator(1);
		assertEquals(false, listIterS2.hasNext());
		assertEquals(1, listIterS2.nextIndex());
		assertThrows(Exception.class, () -> listIterS2.next());
		assertEquals(true, listIterS2.hasPrevious());
		assertEquals(0, listIterS2.previousIndex());
		assertEquals("one", listIterS2.previous());
		assertEquals(-1, listIterS2.previousIndex());
		assertThrows(Exception.class, () -> listIterS2.previous());
		
		gSS.pop();
		assertEquals(null, gSS.getHead());
		assertThrows(Exception.class, () -> gSS.listIterator(1));
		assertThrows(Exception.class, () -> gSS.listIterator(-1));
		ListIterator<String> listIterS3 = gSS.listIterator(0);
		assertEquals(false, listIterS3.hasNext());
		assertEquals(false, listIterS3.hasPrevious());
		assertEquals(0, listIterS3.nextIndex());
		assertEquals(-1, listIterS3.previousIndex());
		assertThrows(Exception.class, () -> listIterS3.next());
		assertThrows(Exception.class, () -> listIterS3.previous());
	}


}
