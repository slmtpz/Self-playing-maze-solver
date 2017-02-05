package cmpe160Project3;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;


public class GUI {

	private JFrame frame;
	private GamePanel gamePanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws FileNotFoundException 
	 */
	public GUI() throws FileNotFoundException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws FileNotFoundException 
	 */
	private void initialize() throws FileNotFoundException {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 500, 520);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		gamePanel = new GamePanel(new MazeSolver("maze.txt"));
		
		
		gamePanel.setBackground(Color.WHITE);
		frame.getContentPane().add(gamePanel);
		frame.pack();
	}

}

class GamePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private int gamePanelWidth;
	private int gamePanelHeight;

	private BufferedImage gameImage;
	private Timer gameTimer;
	
	private int frameRate = 25;
	private int gridSquareSize = 20;
	
	private Game game;

	public GamePanel(Game game) throws FileNotFoundException {
		super();
		gamePanelWidth = 500;
		gamePanelHeight = 500;
		game.setGamePanel(this);
		this.game = game;
		gameImage = new BufferedImage(gamePanelWidth, gamePanelHeight, BufferedImage.TYPE_INT_ARGB);
		setGameTimer();
	}
	
	private void setGameTimer() {
		gameTimer = new Timer(1000/frameRate, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					animate();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				repaint();
			}
		});
		gameTimer.setInitialDelay(0);
		gameTimer.start();
	}
	
	private void animate() throws FileNotFoundException {
		clearCanvas();
		drawGrid();
		
		game.animateObjects();
	}

	public void setSquareSize(int newSize){
		gridSquareSize = newSize;
	}
	
	public void setGridSize(int gridWidth, int gridHeight) {
		this.gamePanelWidth = gridWidth * gridSquareSize;
		this.gamePanelHeight = gridHeight * gridSquareSize;
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(gamePanelWidth, gamePanelHeight);
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (gameImage != null) {
			g.drawImage(gameImage, 0, 0, null);
		}
	}
	
	private void clearCanvas() {
		Graphics g = gameImage.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, gameImage.getWidth(), getHeight());
		g.dispose();
	}
	
	public int getGridWidth() {
		return gamePanelWidth/gridSquareSize;
	}
	
	public int getGridHeight() {
		return gamePanelHeight/gridSquareSize;
	}
	
	private void drawGrid() {
		Graphics g = gameImage.getGraphics();
		g.setColor(Color.LIGHT_GRAY);

		// vertical grid
		for (int i=0; i<getGridWidth(); i++) {
			int lineX = i*gridSquareSize;
			g.drawLine(lineX, 0, lineX, gamePanelHeight);
		}
		// horizontal grid
		for (int i=0; i<getGridHeight(); i++) {
			int lineY = i*(gridSquareSize);
			g.drawLine(0, lineY, gamePanelWidth, lineY);
		}
		g.dispose();
	}

	public void drawRectangle(int gridX, int gridY, Color color) {
		if (gridX < 0 || gridY < 0 || 
				gridX >= getGridWidth() || gridY >= getGridHeight()) {
			return;
		}
		Graphics g = gameImage.getGraphics();
		g.setColor(color);
		int x = gridX*gridSquareSize+1;
		int y = gridY*gridSquareSize+1;
		g.fillRect(x, y, gridSquareSize-1, gridSquareSize-1);
		g.dispose();
	}
	
	public void drawSmallRectangle(int gridX, int gridY, Color color) {
		if (gridX < 0 || gridY < 0 || 
				gridX >= getGridWidth() || gridY >= getGridHeight()) {
			return;
		}
		Graphics g = gameImage.getGraphics();
		g.setColor(color);
		int x = gridX*gridSquareSize+3;
		int y = gridY*gridSquareSize+3;
		g.fillRect(x, y, gridSquareSize-5, gridSquareSize-5);
		g.dispose();
	}
}
