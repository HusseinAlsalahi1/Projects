
import java.util.ArrayList;
import java.util.HashMap;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import javafx.util.Pair;

public class ClientGUI extends Application{

	
	TextField s1,s2,s3,s4;
	Button serverChoice,b1;
	HashMap<String, Scene> sceneMap;
	GridPane grid;
	HBox buttonBox;
	
	Scene startScene;
	BorderPane startPane;
	Server serverConnection;
	// client stuff
	Button clientChoice;
	Button but;
	TextField textF, c1;
	VBox clientBox;
	Client clientConnection;
	HashMap<String, Scene> clientSceneMap;
	ArrayList<Button> catList;
	Stage pStage;
	//
	
	ListView<String> listItems, listItems2;
	TextField t;
	Button b;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("The Networked Client/Server GUI Example");
		pStage = primaryStage;
		PauseTransition pause = new PauseTransition(Duration.seconds(0.001));
		pause.setOnFinished(e->{
			String scene = clientConnection.getScene();
			String disable = clientConnection.getDisable();
			clientConnection.send(new Pair<String,String>("scene", scene));
			clientConnection.send(new Pair<String,String>("disable", disable));
			if(!scene.equals("")) {
				clientConnection.setScene("");
				System.out.println( scene + " will change");
				listItems.getItems().add("Scene change to (" + scene + ")");
				primaryStage.setScene(clientSceneMap.get(scene));
				System.out.println( scene + " CHANGED");
			}
			for(int i = 0; i < catList.size(); i++)
				if (disable.equals(catList.get(i).getText().toLowerCase())) {
					System.out.println( disable + " will be disabled");
					catList.get(i).setDisable(true);
					clientConnection.setDisable("");
					System.out.println( disable + " DISABLED");
				}
			b1.setDisable(false);
			
		});
		
		
		this.serverChoice = new Button("Server");
		this.serverChoice.setStyle("-fx-pref-width: 300px");
		this.serverChoice.setStyle("-fx-pref-height: 300px");
		//
		b = new Button("CONNECT");
		t = new TextField("enter port #");
		b.setOnAction(e->{ 
			primaryStage.setScene(sceneMap.get("server"));
			primaryStage.setTitle("This is the Server");
			serverConnection = new Server(data -> Platform.runLater(() -> listItems.getItems().add(data.toString())), Integer.parseInt(t.getText()));						
		});
		//
		but = new Button("CONNECT");
		textF = new TextField("enter port #");
		but.setOnAction(e->{ 
			primaryStage.setScene(clientSceneMap.get("category"));
			primaryStage.setTitle("category");
			clientConnection = new Client(listItems2, Integer.parseInt(textF.getText()));
			clientConnection.start();
		});
		//
		catList = new ArrayList<Button>();
		catList.add(new Button("Food"));
		catList.add(new Button("Clothing"));
		catList.add(new Button("Language"));
		EventHandler<ActionEvent> categoryHandle = new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				Button b = (Button)event.getSource();
				clientConnection.send(new Pair<String,String>("cat", b.getText().toLowerCase()));
				primaryStage.setScene(clientSceneMap.get("client"));
				primaryStage.setTitle("GUESS");
			}
		};
		for(int i = 0; i < catList.size(); i++)
			catList.get(i).setOnAction(categoryHandle);
		//
		this.serverChoice.setOnAction(e->{
			primaryStage.setScene(sceneMap.get("port"));	
			primaryStage.setTitle("Port Portal");
		});
		
		
		this.clientChoice = new Button("Client");
		this.clientChoice.setStyle("-fx-pref-width: 300px");
		this.clientChoice.setStyle("-fx-pref-height: 300px");
		
		this.clientChoice.setOnAction(e-> {primaryStage.setScene(clientSceneMap.get("port"));
											primaryStage.setTitle("Port Portal");
		});
		
		this.buttonBox = new HBox(400, serverChoice, clientChoice);
		startPane = new BorderPane();
		startPane.setPadding(new Insets(70));
		startPane.setCenter(buttonBox);
		
		startScene = new Scene(startPane, 800,800);
		
		listItems = new ListView<String>();
		listItems2 = new ListView<String>();
		
		c1 = new TextField();
		b1 = new Button("Send");
		b1.setOnAction(e->{
			clientConnection.send(new Pair<String,String>("guess", c1.getText()));
			b1.setDisable(true);
			c1.clear();
			pause.play();
		});
		
		sceneMap = new HashMap<String, Scene>();
		sceneMap.put("server",  createServerGui());
		sceneMap.put("port", createPortGui());
		

		clientSceneMap = new HashMap<String, Scene>();
		clientSceneMap.put("port", createClientPortGui());
		clientSceneMap.put("client",  createClientGui());
		clientSceneMap.put("category", createCategoryGui());
		clientSceneMap.put("win", createEndGui("Impressive, you know your words"));
		clientSceneMap.put("lose", createEndGui("Wow, this is supposed to be a simple game"));
		
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
		
		 
		
		primaryStage.setScene(startScene);
		primaryStage.show();
		
	}
	
	public Scene createServerGui() {
		
		BorderPane pane = new BorderPane();
		pane.setPadding(new Insets(70));
		pane.setStyle("-fx-background-color: coral");
		
		pane.setCenter(listItems);
	
		return new Scene(pane, 500, 400);
		
		
	}

	public Scene createPortGui() {
		return new Scene(new VBox(10, t,b), 400, 300);
	}
	//
	public Scene createEndGui(String message) {
		Label label = new Label(message);
		Button replay = new Button("REPLAY");
		Button quit = new Button("QUIT");
		VBox vb = new VBox(10, label, replay, quit);
		vb.setStyle("-fx-background-color: green");
		replay.setOnAction(e->{
			pStage.setScene(clientSceneMap.get("category"));
			listItems2.getItems().clear();
			clientConnection.send(new Pair<String,String>("reset", "reset"));
		});
		quit.setOnAction(e->pStage.close());
		return new Scene(vb, 400, 300);
	}

	public Scene createClientGui() {
		
		clientBox = new VBox(10,listItems2, c1,b1);
		clientBox.setStyle("-fx-background-color: blue");
		return new Scene(clientBox, 400, 300);
		
	}
	public Scene createCategoryGui() {
		VBox vb = new VBox(20);
		for (int i =0; i < catList.size(); i++)
			vb.getChildren().add(catList.get(i));
		return new Scene(vb, 300,600);
	}
	public Scene createClientPortGui() {
		return new Scene(new VBox(textF, but), 300, 600);
	}

}
