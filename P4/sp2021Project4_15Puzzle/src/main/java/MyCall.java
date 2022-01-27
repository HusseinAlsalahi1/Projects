import java.util.ArrayList;
import java.util.concurrent.Callable;

class MyCall implements Callable<ArrayList<Node>>{
	int[] puzzle;
	String heuristic;
	
	
	MyCall(int[] puzzle, String heuristic){
		this.puzzle = puzzle;
		this.heuristic = heuristic;
	}
	

	@Override
	public ArrayList<Node> call() throws Exception {
		// TODO Auto-generated method stub
		
		Node startState = new Node(puzzle);
		startState.setDepth(0);
//		System.out.println("db1");
		DB_Solver2 start_A_Star = new DB_Solver2(startState, heuristic);
//		System.out.println("db2");
		Node solution = start_A_Star.findSolutionPath();
//		System.out.println("db3");
		if(solution == null)							//no solution was found
			return null;
		else {										//found a solution
			return start_A_Star.getSolutionPath(solution);	//creates and returns ArrayList of solution path
		}
	}

}
