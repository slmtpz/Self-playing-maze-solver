package cmpe160Project3;

import java.io.FileNotFoundException;

public interface Game {

	public void setGamePanel(GamePanel panel);
	public void animateObjects() throws FileNotFoundException;
	
}