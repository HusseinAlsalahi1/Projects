//import static org.junit.jupiter.api.Assertions.*;
//
//import java.util.ArrayList;
//import java.util.Stack;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.DisplayName;
//
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.ValueSource;
//
//class MyTest {
//	static ArrayList<int[]> puzzleList;
//	static Puzzle15 p;
//	@BeforeAll
//	static void innit() {
//		puzzleList = new ArrayList<int[]>();
//		puzzleList.add(new int[] {1,5,3,11,9,6,12,2,4,0,13,14,8,10,15,7});
//		puzzleList.add(new int[] {0,3,6,2,11,5,14,12,9,13,1,7,4,15,8,10});
//		puzzleList.add(new int[] {5,11,6,2,3,0,1,14,9,15,12,4,7,13,8,10});
//		puzzleList.add(new int[] {9,12,10,1,13,0,5,6,8,15,3,2,7,11,4,14});
//		puzzleList.add(new int[] {15,4,6,3,0,1,8,7,9,12,10,11,13,2,14,5});
//		puzzleList.add(new int[] {8,10,1,6,0,12,4,15,13,7,9,3,2,14,5,11});
//		puzzleList.add(new int[] {2,4,6,13,1,5,14,7,8,3,11,0,12,10,9,15});
//		puzzleList.add(new int[] {5,6,7,14,0,2,13,15,4,3,9,10,1,8,12,11});
//		puzzleList.add(new int[] {3,1,11,5,2,6,9,15,12,7,14,10,13,8,0,4});
//		puzzleList.add(new int[] {3,5,6,15,2,11,1,10,12,7,9,4,8,0,13,14});
//		p = new Puzzle15();
//	}
//	
//	@ParameterizedTest
//	@ValueSource(ints = {0,1,2,3,4,5,6,7,8,9})
//	void testPuzzles(int num) {
//		System.out.println("heuristic1");
//		Node start = new Node(puzzleList.get(num));
//		start.setDepth(0);
//		DB_Solver2 AIH1 = new DB_Solver2(start, "heuristicOne");
//		Node end = AIH1.findSolutionPath();
//		assertArrayEquals(new int[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15}, end.getKey());
//	}
//	
//	@Test
//	void testPuzzles2() {
//		System.out.println("heuristic2");
//		for(int i = 0; i< puzzleList.size(); i++) {
//			Node start = new Node(puzzleList.get(i));
//			start.setDepth(0);
//			DB_Solver2 AIH1 = new DB_Solver2(start, "two");
//			Node end = AIH1.findSolutionPath();
//			assertArrayEquals(new int[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15}, end.getKey());
//		}
//	}
//	
//	@Test
//	void testAIH1() {
//		Node start = new Node(puzzleList.get(0));
//		start.setDepth(0);
//		DB_Solver2 AIH1 = new DB_Solver2(start, "heuristicOne");
//		Node end = AIH1.findSolutionPath();
//		ArrayList<Node> solution = AIH1.getSolutionPath(end);
//		assertArrayEquals(puzzleList.get(0), solution.get(0).getKey());
//		assertArrayEquals(new int[] {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15}, solution.get(solution.size()-1).getKey());
//	}
//	
//	@Test
//	void testAIH2() {
//		Node start = new Node(puzzleList.get(0));
//		start.setDepth(0);
//		DB_Solver2 AIH1 = new DB_Solver2(start, "two");
//		Node end = AIH1.findSolutionPath();
//		ArrayList<Node> solution = AIH1.getSolutionPath(end);
//		assertArrayEquals(puzzleList.get(0), solution.get(0).getKey());
//		assertArrayEquals(new int[] {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15}, solution.get(solution.size()-1).getKey());
//	}
//	
//	
//	// testing main class
//	@Test
//	void testCreateStack() {
//		Stack<int[]> puzzleStack = p.createStack();
//		assertEquals(10,puzzleStack.size());
//		assertEquals(16, p.createStack().pop().length);
//	}
//	// testing Node class
//	@Test 
//	void testKey() {
//		Node node = new Node(puzzleList.get(0));
//		assertEquals(puzzleList.get(0), node.getKey());
//	}
//	
//	
//}
