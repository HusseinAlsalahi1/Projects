import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import java.util.concurrent.*;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Puzzle15 extends Application {
	GameButton first, second;
	String background = "-fx-background-color:gray; -fx-opacity:1; -fx-font-size: 30pt; -fx-pref-height: 100px; -fx-pref-width: 100px;",
		   background2 = "-fx-background-color:lightblue; -fx-opacity:1;",
		   gameButtonStyle = "-fx-background-color:pink; -fx-opacity:1; -fx-font-size: 30pt; -fx-pref-height: 100px; -fx-pref-width: 100px;",
		   otherButtonStyle = "-fx-background-color:green; -fx-opacity:1; -fx-font-size: 10pt; -fx-pref-height: 50px; -fx-pref-width: 100px;",
		   otherButtonStyle2 = "-fx-background-color:lightgreen; -fx-opacity:1; -fx-font-size: 20pt; -fx-pref-height: 100px; -fx-pref-width: 200px;";
	Stage stage;
	GridPane board;
	Stack<int[]> puzzleStack;
	ExecutorService ex;
	Button aih1, aih2, solButton;
	ArrayList<Node> solutionArray;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
		ex = Executors.newFixedThreadPool(10);
		
		stage = primaryStage;
		primaryStage.setScene(welcomeScene());
		primaryStage.show();
	}
	
	
	public Scene welcomeScene() {
		Label message = new Label("Welcome\nCan you solve\n this 15 Puzzle?");
		message.setStyle("-fx-background-color:lightblue; -fx-opacity:1;-fx-font-size: 60pt;");
		message.setAlignment(Pos.CENTER);
		PauseTransition pause = new PauseTransition(Duration.seconds(3));
		stage.setTitle("P4 Gameplay");
		puzzleStack = createStack();
		GameButton arr[][] = new GameButton[4][4];
		EventHandler<ActionEvent> GameButtonHandler = new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event) {
				second = (GameButton)event.getSource();
				// System.out.println("second selected");
				switchGameButtons(arr);
			}
		};
		pause.setOnFinished(e-> stage.setScene(gameScene(arr, puzzleStack.pop(), GameButtonHandler)));
		pause.play();
		return new Scene(message);
	}
	
	
	public Scene gameScene(GameButton arr[][], int puzzle[], EventHandler<ActionEvent> handler) {
		board = new GridPane();
		board.setStyle(background);
		board.setHgap(10);
		board.setVgap(10);
		board.setAlignment(Pos.CENTER);
		GameButton b;
		for(int y = 0; y < 4; y++) {
			for(int x = 0; x < 4;x++) {
				b = new GameButton(x, y, puzzle[y*4 + x] + "");
				b.setStyle(gameButtonStyle);
				b.setOnAction(handler);
				if(puzzle[y*4 + x] == 0) {
					this.first = b;
					b.setStyle(background);
					b.setDisable(true);
				}
				arr[x][y] = b;
				board.add(b, x, y);
			}
		}
		
		aih1 = new Button("AI H1");
		aih2 = new Button("AI H2");
		EventHandler<ActionEvent> AIHandler = new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event) {
				// System.out.println("button clicked");
				aih1.setDisable(true);
				aih2.setDisable(true);
				// System.out.print("buttons disabled");
				AIHandle(arr, (Button)event.getSource());
			}
		};
		aih1.setOnAction(AIHandler);
		aih2.setOnAction(AIHandler);
		solButton = new Button("Get Solution");
		solButton.setDisable(true);
		solButton.setOnAction(e->{
			displaySolution(arr);
			solButton.setDisable(true);
		});
		
		HBox hb = new HBox(30,aih1,aih2, solButton);
		hb.setAlignment(Pos.BASELINE_CENTER);
		hb.setStyle(background);
		VBox vb = new VBox(50, board, hb);
		vb.setStyle(background);
		return new Scene(vb, 700, 700);
	}
	
	
	public void switchGameButtons(GameButton arr[][]) {
		if(isValid()){
			first.setText(second.getText());
			first.setStyle(gameButtonStyle);
			second.setText("0");
			second.setStyle(background);
			first.setDisable(false);
			second.setDisable(true);
			first = second;
			if(winCheck(arr)) {
				stage.setScene(winScene(arr));
				// System.out.println("scene change");
			}
		}
	}
	
	
	private boolean isValid() {
		int x = first.getX() - second.getX(),
			y = first.getY() - second.getY();
		// System.out.println( "(" + first.getX()+", "+first.getY() + ")   " + "(" + second.getX()+", "+second.getY() + ")   " + x+y +"    " + x*y);
		if((x+y == 1 || x+y == -1) && x*y == 0) {
			// System.out.println("valid");
			return true;
		}
		// System.out.println("invalid");
		return false;
	}
	
	
	public boolean winCheck(GameButton arr[][]) {
		int next, curr = Integer.parseInt(arr[0][0].getText());
		for(int y = 0; y < 4; y++) {
			for(int x = 0; x < 4; x++) {
				next = Integer.parseInt(arr[x][y].getText());
				if(curr > next)
					return false;
				curr = next;
			}
		}
		return true;
	}
	
	
	public Scene winScene(GameButton arr[][]) {
		Button replay = new Button("New Puzzle"),
			   quit = new Button("Quit");
		replay.setStyle(otherButtonStyle2);
		quit.setStyle(otherButtonStyle2);
		
		
		Label title = new Label("WINNER WINNER\nCHICKEN DINNER");
		title.setFont(Font.font(60));
		title.setAlignment(Pos.TOP_CENTER);
		replay.setOnAction(e-> newGame(arr));
		quit.setOnAction(e->stage.close());
		HBox hb = new HBox(30, replay, quit);
		hb.setStyle(background2);
		hb.setAlignment(Pos.CENTER);
		VBox vb = new VBox(title,hb);
		vb.setStyle(background2);
		vb.setAlignment(Pos.TOP_CENTER);
		return new Scene(vb, 600, 400);
	}
	
	
	public void newGame(GameButton arr[][]) {
		int puzzle[] = puzzleStack.pop();
		for(int y = 0; y < 4; y++) {
			for(int x = 0; x < 4; x++) {
				arr[x][y].setText(puzzle[y*4 + x] + "");
				arr[x][y].setStyle(gameButtonStyle);
				arr[x][y].setDisable(false);
				if(puzzle[y*4 + x] == 0) {
					this.first = arr[x][y];
					arr[x][y].setStyle(background);
					arr[x][y].setDisable(true);
				}
			}
		}
		stage.setScene(board.getScene());
	}
	
	
	public Stack<int[]> createStack() {
		Stack<int[]> stack = new Stack<int[]>();
		stack.push(new int[] {1,5,3,11,9,6,12,2,4,0,13,14,8,10,15,7});
		stack.push(new int[] {0,3,6,2,11,5,14,12,9,13,1,7,4,15,8,10});
		stack.push(new int[] {5,11,6,2,3,0,1,14,9,15,12,4,7,13,8,10});
		stack.push(new int[] {9,12,10,1,13,0,5,6,8,15,3,2,7,11,4,14});
		stack.push(new int[] {15,4,6,3,0,1,8,7,9,12,10,11,13,2,14,5});
		stack.push(new int[] {8,10,1,6,0,12,4,15,13,7,9,3,2,14,5,11});
		stack.push(new int[] {2,4,6,13,1,5,14,7,8,3,11,0,12,10,9,15});
		stack.push(new int[] {5,6,7,14,0,2,13,15,4,3,9,10,1,8,12,11});
		stack.push(new int[] {3,1,11,5,2,6,9,15,12,7,14,10,13,8,0,4});
		stack.push(new int[] {3,5,6,15,2,11,1,10,12,7,9,4,8,0,13,14});
		return stack;
	}
	
	
	public void AIHandle(GameButton arr[][], Button b) {
		String heuristic;
		int[] puzzle = new int[16];
		if(b == aih1)
			heuristic = "heuristicOne";
		else
			heuristic = "heuristicTwo";
		for(int i = 0; i < 16; i++) {
			puzzle[i] = Integer.parseInt(arr[i%4][i/4].getText());
			// System.out.print(puzzle[i]);
		}
		// System.out.print("check 1 " + "\n");
		Future<ArrayList<Node>> future = ex.submit(new MyCall(puzzle, heuristic));
		// System.out.print("check 2");
		try {
			// System.out.print("check 3");
			solutionArray = future.get();
			// System.out.print("check 4");
			if(solutionArray != null)
				solButton.setDisable(false);
//			else
//				// System.out.print("no solution found");
		}catch(Exception e){System.out.println(e.getMessage());}
	}
	
	
	public void displaySolution(GameButton arr[][]) {
		int max = 10;
		PauseTransition pause;
		if(solutionArray.size() < max)
			max = solutionArray.size();
		disableAllButtons(arr, true);
		for(double i = 0; i < max; i++) {
			int[] puzzle = solutionArray.get((int) i).getKey();
			pause = new PauseTransition(Duration.seconds(i/2));
			pause.setOnFinished(e->{
				displayTurn(arr, puzzle);
			});
			pause.play();
		}
		pause = new PauseTransition(Duration.seconds(5));
		pause.setOnFinished(e->{
			disableAllButtons(arr, false);
			aih1.setDisable(false);
			aih2.setDisable(false);
			if(winCheck(arr))
				stage.setScene(winScene(arr));
		});
		pause.play();
	}
	
	
	public void displayTurn(GameButton arr[][], int[]puzzle) {
		// System.out.println("printing");
		for(int y = 0; y < 4; y++) {
			for(int x = 0; x < 4; x++) {
				int val = puzzle[y*4 + x];
				arr[x][y].setText(val + "");
				if(val == 0) {
					arr[x][y].setStyle(background);
					first = arr[x][y];
				}
				else 
					arr[x][y].setStyle(gameButtonStyle);
				// System.out.print(val + " ");
			}
		}
	}
	
	
	public void disableAllButtons(GameButton arr[][], boolean val) {
		for(int y = 0; y < 4; y++)
			for(int x = 0; x < 4; x++)
				arr[x][y].setDisable(val);
		first.setDisable(true);
	}
	
	
}
