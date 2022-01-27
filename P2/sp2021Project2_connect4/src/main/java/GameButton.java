import javafx.scene.control.Button;

public class GameButton extends Button {
	private int x, y, p;
	GameButton(int xCor, int yCor){
		x = xCor;
		y = yCor;
		p = 0;
	}
	int getX() {
		return x;
	}
	int getY() {
		return y;
	}
	int getP() {
		return p;
	}
	void setP(int v) {
		p = v;
	}
}
