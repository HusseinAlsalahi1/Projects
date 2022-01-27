import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.function.Consumer;

import javafx.application.Platform;
import javafx.scene.control.ListView;
import javafx.util.Pair;

public class Client extends Thread{
	
	Socket socketClient;
	int port;
	ObjectOutputStream out;
	ObjectInputStream in;
	private ListView<String> listItems;
	private Consumer<Serializable> callback;
	private String scene, disable;
	
	Client(ListView<String> list, int portNum){
		port = portNum;
		listItems = list;
		callback = data->{
			Platform.runLater(()->{
				readCommand((Pair<String, String>) data);
			});
		};
		scene = "";
		disable = "";
	}
	private void readCommand(Pair<String, String> p) {
		if(p.getKey().equals("show"))
			listItems.getItems().add(p.getValue());
		else if(p.getKey().equals("scene"))
			scene = p.getValue();
		else if(p.getKey().equals("disable"))
			disable = p.getValue();
	}
	public void run() {
		
		try {
		socketClient= new Socket("127.0.0.1",port);
	    out = new ObjectOutputStream(socketClient.getOutputStream());
	    in = new ObjectInputStream(socketClient.getInputStream());
	    socketClient.setTcpNoDelay(true);
		}
		catch(Exception e) {}
		
		while(true) {
			try {
				Pair<String, String> message = (Pair<String, String>) in.readObject();
				callback.accept(message);
			}
			catch(Exception e) {}
		}
	
    }
	
	public void send(Pair<String, String> data) {
		
		try {
			out.writeObject(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String getScene() {
		return scene;
	}
	public void setScene(String s) {
		scene = s;
	}
	public String getDisable() {
		return disable;
	}
	public void setDisable(String s) {
		disable = s;
	}
}
