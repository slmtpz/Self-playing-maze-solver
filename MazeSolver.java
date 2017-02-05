package cmpe160Project3;

import java.awt.Color;
import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MazeSolver implements Game {
	
	private File maze;
	private GamePanel gamePanel;
	private int width;
	private int height;
	private int squareSize;
	private List<Snake> snake;
	private Point start = null;
	private Point end = null;
	private List<Point> markL;

/**
 * Constructs a mazesolver game. Calls changeSize and determineStartEnd methods inside
 * in order to change maps size as to given maze and to determine start and end points.
 * @param mazePath Guides to file.
 * @throws FileNotFoundException
 */
	public MazeSolver(String mazePath) throws FileNotFoundException {
		this.maze = new File(mazePath);
		changeSize();
		determineStartEnd();
		snake = new ArrayList<Snake>();
		markL = new ArrayList<Point>();
	}
	
	/**
	 * Finds start and end points inside the given maze file.
	 * After finding, stores theirs value inside start and end fields.
	 * @throws FileNotFoundException
	 */
	public void determineStartEnd() throws FileNotFoundException{
		Scanner in = new Scanner(maze);
		int y = 0;
		while(in.hasNextLine()){
			char[] lineArray = in.nextLine().toCharArray();
			for(int x=0; x<lineArray.length; x++){
				if(lineArray[x]=='S') start = new Point(x,y);
				else if(lineArray[x]=='E') end = new Point(x,y);
				}
			y++;
		}
	}
	
	/**
	 * Fixes the squareSize field as to given maze. If the maze is big enough
	 * it lowers squareSize fields value to be able to make map.
	 * @throws FileNotFoundException
	 */
	public void changeSize() throws FileNotFoundException{
		Scanner in = new Scanner(maze);
		String line = in.nextLine();
		width = line.length();
		height = 1;
		while(in.hasNextLine()){
			height++;
			line = in.nextLine();
		}
		squareSize = 500 / Math.max(width, height);
	}
	
	/**
	 * Draws the map with rectangles according to given file.
	 * Draws black boxes for walls.
	 * Draws red box for start.
	 * Draws green boxes for end.
	 * @throws FileNotFoundException
	 */
	public void fillTheMap() throws FileNotFoundException{
		Scanner in = new Scanner(maze);
		int y = 0;
		while(in.hasNextLine()){
			char[] lineArray = in.nextLine().toCharArray();
			for(int x=0; x<lineArray.length; x++){
				if(lineArray[x]=='x') gamePanel.drawRectangle(x,y,Color.BLACK);
				else if(lineArray[x]=='S') gamePanel.drawRectangle(x,y,Color.RED);
				else if(lineArray[x]=='E') gamePanel.drawRectangle(x,y,Color.GREEN);
			}
			y++;
		}
	}
	
	/**
	 * Makes us to be able to check whether the given coordinates is available to go or not.
	 * @param x Horizontal (x) axis of the grid.
	 * @param y Vertical (y) axis of the grid.
	 * @return Returns true if given (x,y) coordinates available to go.
	 * @throws FileNotFoundException
	 */
	public boolean checkNeighbor(int x, int y) throws FileNotFoundException{
		if(!(x<gamePanel.getGridWidth() && x>-1 && y<gamePanel.getGridHeight() && y>-1)) return false;

		for(int i=0; i<markL.size(); i++){
			if(x==markL.get(i).x && y==markL.get(i).y) return false;
		}
		
		
		Scanner in = new Scanner(maze);
		String line = "";
		for(int i=0; i<=y ; i++)
			line = in.nextLine();
		char[] a = line.toCharArray();
		if(a[x]=='x') return false;
	
		return true;
	}
	
	/**
	 * Marks the coordinate (x,y) so it cannot be gone over.
	 * @param x Horizontal (x) axis of the grid.
	 * @param y Vertical (y) axis of the grid.
	 */
	public void mark(int x, int y){
		markL.add(new Point(x,y));
	}
	
	/**
	 * Makes us to be able to get start point from another class.
	 * @return Returns private start point.
	 */
	public Point getStart(){
		return start;
	}
	
	/**
	 * Makes us to be able to get end point from another class.
	 * @return Returns private end point.
	 */
	public Point getEnd(){
		return end;
	}
	
	/**
	 * Draws a rectangle at the coordinates (x,y) with given color.
	 * @param x Horizontal (x) axis of the grid.
	 * @param y Vertical (y) axis of the grid.
	 * @param color Draw a rectangle with this color.
	 */
	public void drawRectangle(int x, int y, Color color){
		gamePanel.drawRectangle(x,y,color);
	}
	
	/**
	 * Fixes the map size as to given file.
	 */
	public void setGamePanel(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		gamePanel.setSquareSize(squareSize);
		gamePanel.setGridSize(width,height);
		snake.add(new Snake(this));
	}

	/**
	 * Animate snake object to find end point.
	 * @throws FileNotFoundException
	 */
	public void animateObjects() throws FileNotFoundException {
		if (gamePanel != null) 
			for (Animatable s:snake) {
				s.move();
				s.draw();
			}
		fillTheMap();
	}

}
