import java.util.Stack;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;
public class JavaFXTemplate extends Application {
	private TextArea gText;
	private VBox vb;
	private MenuBar menu;
	private Scene wScene, gScene, eScene, htpScene;
	private GameButton[][] arr;
	private Stage stage;
	private String bStyle = "-fx-background-color:gray; -fx-opacity:1", 
			bStyle1 = "-fx-background-color:black; -fx-opacity:1",
			bStyle2 = "-fx-background-color:white; -fx-opacity:1",
			bg = "-fx-background-color:sienna; -fx-opacity:1";
	private int player = 1;
	private EventHandler<ActionEvent> gButtonHandle;
	private Stack<Pair<Integer, Integer>> turnStack;
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		stage = primaryStage;
		primaryStage.setTitle("CONNECT FOUR WELCOME");
		sceneSetup();
		primaryStage.setScene(wScene);
		primaryStage.show();
	}
	public void gScene(){
		turnStack = new Stack<>();
		GridPane board = new GridPane();
		board.setHgap(10);
		board.setVgap(10);
		board.setAlignment(Pos.CENTER);
		GameButton b;
		arr = new GameButton[7][6];
		buttonHandler();
		for(int x = 0; x < 7; x++) {
			for(int y = 0; y < 6;y++) {
				b = new GameButton(x,y);
				b.setStyle(bStyle);
				b.setMinSize(100,100);
				b.setOnAction(gButtonHandle);
				arr[x][y] = b;
				board.add(b, x, 6-y);
			}
		}
		menu = new MenuBar(); //a menu bar takes menus as children
		Menu gameplay = new Menu("gameplay"), //a menu goes inside a menu bar
			 themes = new Menu("Themes"),
			 options = new Menu("Options");

		MenuItem reverse = new MenuItem("Reverse Move"), //menu items go inside a menu
				 originalTheme = new MenuItem("Original Theme"),
				 theme1 = new MenuItem("Theme one"),
				 theme2 = new MenuItem("Theme two"),
				 howToPlay = new MenuItem("How To Play"),
				 newGame = new MenuItem("New Game"),
				 exit = new MenuItem("Exit");
		
		
		gameplay.getItems().add(reverse); //add menu item to first menu
		themes.getItems().addAll(originalTheme, theme1, theme2);
		options.getItems().addAll(howToPlay, newGame, exit);
		
		menu.getMenus().addAll(gameplay, themes, options); //add menus to the menu bar
		
		gText = new TextArea();
		gText.setEditable(false);
		vb = new VBox(menu, board, gText);
		vb.setAlignment(Pos.TOP_CENTER);
		vb.setStyle(bg);

		gScene = new Scene(vb, 800,800);
		// event handlers
		reverse.setOnAction(e->{
			gText.setText("Reverse Move");
			if(!turnStack.isEmpty()) {
				Pair<Integer,Integer> p = turnStack.pop();
				arr[p.getKey()][p.getValue()].setStyle(bStyle);
				arr[p.getKey()][p.getValue()].setP(0);
				arr[p.getKey()][p.getValue()].setDisable(false);
				if(player == 1)
					player++;
				else
					player--;
			}
		});
		
		originalTheme.setOnAction(e->changeThemeOriginal()); 
		theme1.setOnAction(e->changeTheme1()); 
		theme2.setOnAction(e->changeTheme2()); 
		howToPlay.setOnAction(e->{
			gText.setText("howToPlay");
			Stage s = new Stage();
			s.setScene(htpScene);
			s.setTitle("HOW TO PLAY");
			s.show();
			}); 
		newGame.setOnAction(e->clearBoard()); 
		exit.setOnAction(e->stage.close());
	}
	public void clearBoard() {
		gText.setText("newGame");
		turnStack.clear();
		player = 1;
		for(int x = 0; x < 7;x++)
			for(int y = 0; y < 6; y++) {
				arr[x][y].setStyle(bStyle);
				arr[x][y].setP(0);
				arr[x][y].setDisable(false);
			}
		menu.setDisable(false);
	}
	
	public void buttonHandler() {
		gButtonHandle = new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				GameButton b = (GameButton)event.getSource();
				int x = b.getX(), y = b.getY();
				gText.setText("Player " + player +"\n");
				if(b.getP() == 0 && (y == 0 || arr[x][y-1].getP() != 0)) {
					b.setP(player);
					if(player == 1) {
						b.setStyle(bStyle1);
						player++;
					}
					else {
						b.setStyle(bStyle2);
						player--;
					}
					gText.appendText("valid move at (" + x + ", " + y + ")\n");
					b.setDisable(true);
					turnStack.push(new Pair<>(x,y));
					winCheck();
				}
				else {
					gText.appendText("invalid move at (" + x + ", " + y + ")\nTRY AGAIN");
				}
			}
		};
	}
	public void sceneSetup() {
		gScene();
		welcomeScene();
		htpScene();
	}
	public void endScene() {
		TextField t;
		Button exitButton = new Button("EXIT");
		exitButton.setOnAction(e->stage.close());
		exitButton.setStyle("-fx-background-color:pink");
		exitButton.setPrefSize(150, 75);

		Button replayButton = new Button("REPLAY");
		replayButton.setOnAction(e->{
			stage.setTitle("GamePlay");
			stage.setScene(gScene);
			clearBoard();
		});
		replayButton.setStyle("-fx-background-color:lightgreen");
		replayButton.setPrefSize(150, 75);
		if(player == 2)
			t = new TextField("PLAYER 1 WINS!!");
		else if (player == 1)
			t = new TextField("PLAYER 2 WINS!!");
		else
			t = new TextField("YOU BOTH LOSE!!");
		t.setFont(Font.font(50));
		t.setAlignment(Pos.CENTER);
		t.setStyle(bg);
		t.setEditable(false);
		VBox evb = new VBox(50, t,replayButton,exitButton);
		evb.setAlignment(Pos.TOP_CENTER);
		evb.setStyle(bg);
		eScene = new Scene(evb, 500, 500);
	}
	public void welcomeScene() {
		Button wButton = new Button("PLAY");
		wButton.setStyle("-fx-background-color:orange;");
		wButton.setMinSize(150,80);
		wButton.setFont(Font.font(30));
		wButton.setOnAction(e->{
			stage.setScene(gScene);
			stage.setTitle("GamePlay");
		});
		Image img = new Image("connect4.jpg");   // welcome screen image
		ImageView view = new ImageView(img);
		VBox wvb = new VBox(view, wButton);
		wvb.setStyle("-fx-background-color:transparent;");
		wvb.setAlignment(Pos.TOP_CENTER);
		wScene = new Scene(wvb);
	}
	public void htpScene() {
		TextArea htpT = new TextArea("How To Play:\n"
				+ "Connect Four is a two player game. Take turns clicking\n"
				+ "the lowest empty position to place your game piece.\n"
				+ "The first player to have 4 consecutive pieces, whether\n"
				+ "it be vertically, horizontally or diagonally, wins.");
		htpT.setEditable(false);
		htpT.setFont(Font.font(20));
		htpScene = new Scene(htpT, 500,500);
	}
	
	public void winCheck() {
		Pair<Integer,Integer> p;
		p = verCheck();
		PauseTransition pause = new PauseTransition(Duration.seconds(3));
		pause.setOnFinished(e->{
			endScene();
			stage.setTitle("Winner Winner Chicken Dinner");
			stage.setScene(eScene);
		});
		if(p != null) {
			for(int i = 0; i < 4; i++) 
				arr[p.getKey()][p.getValue()-i].setStyle("-fx-border-color:purple;" + 
						"-fx-border-width: 3 3 3 3;" + arr[p.getKey()][p.getValue()-i].getStyle());
			disableEverything();
			pause.play();
		}
		p = horCheck();
		if(p != null) {
			for(int i = 0; i < 4; i++) 
				arr[p.getKey()-i][p.getValue()].setStyle("-fx-border-color:orange;" + 
						"-fx-border-width: 3 3 3 3;" + arr[p.getKey()-i][p.getValue()].getStyle());
			disableEverything();
			pause.play();
		}
		p = fDiaCheck();
		if(p != null) {
			for(int i = 0; i < 4; i++) 
				arr[p.getKey()-i][p.getValue()-i].setStyle("-fx-border-color:green;" + 
						"-fx-border-width: 3 3 3 3;" + arr[p.getKey()-i][p.getValue()-i].getStyle());
			disableEverything();
			pause.play();
		}
		p = bDiaCheck();
		if(p != null) {
			for(int i = 0; i < 4; i++) 
				arr[p.getKey() + i][p.getValue() - i].setStyle("-fx-border-color:yellow;" + 
						"-fx-border-width: 3 3 3 3;" + arr[p.getKey()+i][p.getValue()-i].getStyle());
			disableEverything();
			pause.play();
		}
		if(turnStack.size() == 42) {
			player = 0;
			pause.play();
		}
	}
	public Pair<Integer, Integer> verCheck() {
		int piece;
		int count;
		for(int x = 0; x < 7; x++) {
			piece = arr[x][0].getP();
			count = 1;
			for(int y = 1; y < 6; y++) {
				if(piece == 0 || arr[x][3].getP() == 0)
					break;
				else if(arr[x][y].getP() == piece) {
					count++;
					if(count == 4)
						return new Pair<>(x,y);
				}
				else {
					count = 1;
					piece = arr[x][y].getP();
				}
			}
		}
		return null;
	}
	public Pair<Integer,Integer> horCheck() {
		int piece;
		int count;
		for(int y = 0; y < 6; y++) {
			if(arr[3][y].getP() == 0)
				return null;
			piece = arr[0][y].getP();
			count = 1;
			for(int x = 1; x < 7; x++) {
				if(piece != 0 && arr[x][y].getP() == piece) {
					count++;
					if(count == 4) 
						return new Pair<>(x,y);
				}
				else {
					count = 1;
					piece = arr[x][y].getP();
				}

			}
		}
		return null;
	}
	public Pair<Integer,Integer> fDiaCheck() {
		int piece;
		int count;
		for(int x = 0; x < 4; x++) {
			int y = 1;
			piece = arr[x][0].getP();
			count = 1;
			while(x + y < 7 && y < 6) {
				if(piece == 0 || piece != arr[x+y][y].getP()) {
					piece = arr[x+y][y].getP();
					count = 1;
				}
				else {
					count++;
					if(count == 4) 
						return new Pair<>(x+y,y);
				}
				y++;
			}
		}
		for(int y = 1; y < 3; y++) {
			int x = 1;
			piece = arr[0][y].getP();
			count = 1;
			while(x < 7 && y+x < 6 ) {
				if(piece == 0 || piece != arr[x][y+x].getP()) {
					piece = arr[x][y+x].getP();
					count = 1;
				}
				else {
					count++;
					if(count == 4)
						return new Pair<>(x,y+x);
				}
				x++;
			}
		}
		return null;
	}
	public Pair<Integer,Integer> bDiaCheck() {
		int piece;
		int count;
		for(int x = 3; x < 7; x++) {
			int y = 1;
			piece = arr[x][0].getP();
			count = 1;
			while(x-y >= 0 && y < 6) {
				if(piece == 0 || piece != arr[x-y][y].getP()) {
					piece = arr[x-y][y].getP();
					count = 1;
				}
				else {
					count++;
					if(count == 4)
						return new Pair<>(x-y,y);
				}
				y++;
			}
		}
		for(int y = 1; y < 3; y++) {
			int x = 1;
			piece = arr[6][y].getP();
			count = 1;
			while(6 - x >= 0 && y + x < 6) {
				if(piece == 0 || piece != arr[6 - x][y+x].getP()) {
					piece = arr[6 - x][y+x].getP();
					count = 1;
				}
				else {
					count++;
					if(count == 4)
						return new Pair<>(6 - x,y+x);
				}
				x++;
			}
		}
		return null;
	}
	public void disableEverything() {
		for(int x =0; x< 7; x++)
			for(int y = 0; y<6; y++)
				arr[x][y].setDisable(true);
		menu.setDisable(true);
	}
	public void changeTheme1() {
		bg = "-fx-background-color:blue; -fx-opacity:1";
		bStyle1 = "-fx-background-color:yellow; -fx-opacity:1";
		bStyle2 = "-fx-background-color:red; -fx-opacity:1";
		vb.setStyle(bg);
		for(int x =0; x< 7; x++)
			for(int y = 0; y<6; y++) {
				if(arr[x][y].getP() == 1)
					arr[x][y].setStyle(bStyle1);
				else if(arr[x][y].getP() == 2)
					arr[x][y].setStyle(bStyle2);
				else
					arr[x][y].setStyle(bStyle);
				arr[x][y].setShape(new Circle(50));
			}
	}
	public void changeTheme2() {
		bg = "-fx-background-color:wheat; -fx-opacity:1";
		bStyle1 = "-fx-background-color:saddlebrown; -fx-opacity:1";
		bStyle2 = "-fx-background-color:darkgreen; -fx-opacity:1";
		vb.setStyle(bg);
		for(int x =0; x< 7; x++)
			for(int y = 0; y<6; y++) {
				if(arr[x][y].getP() == 1)
					arr[x][y].setStyle(bStyle1);
				else if(arr[x][y].getP() == 2)
					arr[x][y].setStyle(bStyle2);
				else
					arr[x][y].setStyle(bStyle);
				arr[x][y].setShape(null);
					
			}
		
		
	}
	public void changeThemeOriginal() {
		bg = "-fx-background-color:lightblue; -fx-opacity:1";
		bStyle1 = "-fx-background-color:blue; -fx-opacity:1";
		bStyle2 = "-fx-background-color:red; -fx-opacity:1";
		vb.setStyle(bg);
		for(int x = 0; x < 7; x++)
			for(int y = 0; y<6; y++) {
				if(arr[x][y].getP() == 1)
					arr[x][y].setStyle(bStyle1);
				else if(arr[x][y].getP() == 2)
					arr[x][y].setStyle(bStyle2);
				else
					arr[x][y].setStyle(bStyle);
				arr[x][y].setShape(null);
			}
	}
	
}
