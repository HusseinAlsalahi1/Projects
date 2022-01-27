import java.util.ArrayList;
import java.util.HashMap;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import javafx.util.Pair;

public class ClientGUI extends Application{

	
	Button send;
	HashMap<String, Scene> sceneMap;
	Client clientConnection;
	ArrayList<Button> buttonList;
	Stage pStage;
	ListView<String> listItems;
	PauseTransition pause;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("Port Portal");
		pStage = primaryStage;
		pause = new PauseTransition(Duration.seconds(0.001));
		pause.setOnFinished(e->checkFlags());
		
		listItems = new ListView<String>();
		sceneMap = new HashMap<String, Scene>();
		sceneMap.put("port", createPortGui());
		sceneMap.put("client",  createClientGui());
		sceneMap.put("category", createCategoryGui());
		sceneMap.put("win", createEndGui("Impressive, you know\nyour words"));
		sceneMap.put("lose", createEndGui("Wow, this is supposed\n to be a simple game"));
		
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
		
		primaryStage.setScene(sceneMap.get("port"));
		primaryStage.show();
		
	}
	private void checkFlags() {
		{
			String scene = clientConnection.getScene();
			String disable = clientConnection.getDisable();
			if(!scene.equals("")) {
				clientConnection.setScene("");
				pStage.setScene(sceneMap.get(scene));
			}
			for(int i = 0; i < buttonList.size(); i++)
				if (disable.equals(buttonList.get(i).getText().toLowerCase())) {
					System.out.println( disable + " will be disabled");
					buttonList.get(i).setDisable(true);
					clientConnection.setDisable("");
					System.out.println(disable + " DISABLED");
				}
			send.setDisable(false);	
		}
	}
	public Scene createEndGui(String message) {
		Label label = new Label(message);
		Button replay = new Button("REPLAY");
		Button quit = new Button("QUIT");
		VBox vb = new VBox(20, label, replay, quit);
		vb.setStyle("-fx-background-color: lightblue");
		vb.setAlignment(Pos.TOP_CENTER);
		label.setFont(Font.font(50));
		label.setAlignment(Pos.CENTER);
		replay.setStyle("-fx-background-color: lightgreen");
		replay.setMinSize(100, 50);
		replay.setFont(Font.font(35));
		quit.setStyle("-fx-background-color: red");
		quit.setMinSize(100, 50);
		quit.setFont(Font.font(35));
		replay.setOnAction(e->{
			pStage.setScene(sceneMap.get("category"));
			listItems.getItems().clear();
			clientConnection.send(new Pair<String,String>("reset", "reset"));
			for(int i = 0; i < buttonList.size(); i++) {
				buttonList.get(i).setDisable(false);
			}
		});
		quit.setOnAction(e->pStage.close());
		return new Scene(vb, 600, 500);
	}

	public Scene createClientGui() {
		TextField t = new TextField();
		send = new Button("Send");
		send.setOnAction(e->{
			clientConnection.send(new Pair<String,String>("guess", t.getText()));
			send.setDisable(true);
			t.clear();
			pause.play();
		});
		t.setFont(Font.font(30));
		VBox vb = new VBox(10,listItems, t,send);
		vb.setStyle("-fx-background-color:lightblue;");
		send.setStyle("-fx-background-color:pink;");
		send.setMinSize(100, 50);
		send.setFont(Font.font(35));
		t.setFont(Font.font(30));
		return new Scene(vb, 700, 600);
		
	}
	private ArrayList<Button> createList(){
		ArrayList<Button> list = new ArrayList<Button>();
		list.add(new Button("Food"));
		list.add(new Button("Clothing"));
		list.add(new Button("Language"));
		EventHandler<ActionEvent> categoryHandle = new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				Button b = (Button)event.getSource();
				clientConnection.send(new Pair<String,String>("cat", b.getText().toLowerCase()));
				pStage.setScene(sceneMap.get("client"));
				pStage.setTitle("Can you guess the word?");
				listItems.getItems().clear();
			}
		};
		Button b;
		for(int i = 0; i < list.size(); i++) {
			b = list.get(i);
			b.setStyle("-fx-background-color:pink;");
			b.setMinSize(300, 150);
			b.setFont(Font.font(65));
			b.setOnAction(categoryHandle);
		}
		return list;
	}
	public Scene createCategoryGui() {
		buttonList = createList();
		VBox vb = new VBox(20);
		vb.setAlignment(Pos.CENTER);
		for (int i =0; i < buttonList.size(); i++)
			vb.getChildren().add(buttonList.get(i));
		vb .setStyle("-fx-background-color:lightblue;");
		return new Scene(vb, 500, 600);
	}
	public Scene createPortGui() {
		Button b = new Button("CONNECT");
		TextField t = new TextField();
		Label l = new Label("Enter port number:");
		Label description = new Label("Description:"
				+ "\nPick a category to recieve a word and guess the word 1 character"
				+ "\nat a time. You are allowed only 6 mistakes per word and 3 tries"
				+ "\nper category. To win you must uncover 1 word from each category."
				+ "\n Have Fun!");
		b.setOnAction(e->{ 
			pStage.setScene(sceneMap.get("category"));
			pStage.setTitle("Choose a Category");
			clientConnection = new Client(listItems, Integer.parseInt(t.getText()));
			clientConnection.start();
		});
		b.setMinSize(100, 50);
		t.setFont(Font.font(30));
		VBox vb = new VBox(l,t, b, description);
		vb.setAlignment(Pos.TOP_LEFT);
		vb .setStyle("-fx-background-color:lightblue;");
		b.setStyle("-fx-background-color:pink;");
		b.setFont(Font.font(30));
		l.setFont(Font.font(15));
		return new Scene(vb, 400, 400);
	}

}
