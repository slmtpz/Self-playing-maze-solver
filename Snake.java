package cmpe160Project3;

import java.awt.Color;
import java.awt.Point;
import java.io.FileNotFoundException;
import java.util.Stack;

public class Snake implements Animatable {		

	private MazeSolver mazeSolver;
	private Stack<Point> s;
	
	/**
	 * Constructs a snake to find end point from start.
	 * @param mazeSolver
	 */
	public Snake(MazeSolver mazeSolver){
		this.mazeSolver = mazeSolver;
		s = new Stack<Point>();
		if(mazeSolver.getStart() != null)
		s.push(mazeSolver.getStart());
	}
	
	/**
	 * Finds the way to go.
	 * Also inform the user if there is no way to reach end point.
	 */
	public void move() throws FileNotFoundException {
		if(!s.isEmpty() && !(s.get(s.size()-1).x==mazeSolver.getEnd().x && s.get(s.size()-1).y==mazeSolver.getEnd().y)){
			Point c = s.lastElement();
			mazeSolver.mark(c.x, c.y);
			if(mazeSolver.checkNeighbor(c.x+1, c.y)) s.push(new Point(c.x+1,c.y));
			else if(mazeSolver.checkNeighbor(c.x, c.y-1)) s.push(new Point(c.x,c.y-1)); 
			else if(mazeSolver.checkNeighbor(c.x, c.y+1)) s.push(new Point(c.x,c.y+1)); 
			else if(mazeSolver.checkNeighbor(c.x-1, c.y)) s.push(new Point(c.x-1,c.y)); 
			else s.pop();
		}
		if(s.isEmpty()) System.out.println("The road is blocked.");
	}

	/**
	 * Draws blue rectangles for the way from start to end.
	 */
	public void draw() {
		for(int i=0; i<s.size(); i++)
		mazeSolver.drawRectangle(s.get(i).x,s.get(i).y,Color.BLUE);
	}
}
