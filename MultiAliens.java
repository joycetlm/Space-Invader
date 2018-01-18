package project;

import java.util.LinkedList;


public class MultiAliens  implements Moveable {

	private int row,column; // rows an columns

	private final double X = -0.4;  //initial position of the 1st alien for the alien group
	private final double Y = 0.5;
	private double x; //current position of the 1st alien for the alien group
	private double y;
	private final double interval = 0.2;  // distance between each alien
	LinkedList<Alien> multialiens = new LinkedList<Alien>();

	/**
	 * generate a group of aliens and move together
	 * @param row of the group
	 * @param column of the group
	 */

	public MultiAliens(int row, int column) {
		this.row = row;
		this.column = column;
		this. x = X;
		this. y = Y;	
		for(int r = 0; r < row; r++){
			for(int c = 0; c < column; c++){
				multialiens.add(new Alien(x+interval*c , y+interval*r, 0.01, true));
			}
		}
	}



	/**
	 * @return the row of the group
	 */
	public final int getRow() {
		return row;
	}


	/**
	 * set the row of the group
	 * @param row
	 */
	public final void setRow(int row) {
		this.row = row;
	}


	/**
	 * @return the column of the group
	 */
	public final int getColumn() {
		return column;
	}


	/**
	 * set the column of the group
	 * @param column
	 */
	public final void setColumn(int column) {
		this.column = column;
	}


	/**
	 * drive the group of aliens to move
	 */
	@Override
	public void move() {

	}

	/**
	 * draw the group of aliens 
	 */
	@Override
	public void draw() {

	}	

}
