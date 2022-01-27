import javafx.scene.control.Button;

public class GameButton extends Button {
	private int x, y;
	GameButton(int xCor, int yCor){
		x = xCor;
		y = yCor;
	}
	GameButton(int xCor, int yCor, String text){
		x = xCor;
		y = yCor;
		this.setText(text);
	}
	int getX() {
		return x;
	}
	void setX(int x) {
		this.x = x;
	}
	int getY() {
		return y;
	}
	void setY(int y) {
		this.y = y;
	}
}
