package project;

import sedgewick.StdDraw;


/**
 * draw the bullet and make it move
 */
public class Bullet implements Moveable{
	
	private double posX;
	private double posY; 
	private double width = 0.05;
	private double height = 0.05;
	private double speed;
	private boolean isOffScreen = false;
	
	/**
	 * Creates a Bullet object to be implemented in the game
	 * @param x- x-coordinate of bullet (center)
	 * @param y- y-coordinate of bullet (center)
	 * @param speed- speed at which the bullet moves 
	 */
	public Bullet(double x, double y, double speed) {
		this.posX = x;
		this.posY = y;
		this.speed = speed;
	}

	
	/**
	 * draw the graph of bullet
	 */
	public void draw() {
		StdDraw.setPenColor(StdDraw.BLUE);
		StdDraw.filledRectangle(this.posX, this.posY, this.width/2, this.height/2);
	}
	
	
	/**
	 * drive the bullet to move
	 */
	public void move() {
		this.posY += speed;
	}
	
	
	/**
	 * get the bullet's X position
	 * @return posX
	 */
	public double getPosX() {
		return this.posX;
	}
	
	
	/**
	 * get the bullet's Y position
	 * @return posY
	 */
	public double getPosY() {
		return this.posY;
	}
	
	/**
	 * Determine if bullet is out of bounds
	 */
	public boolean getIsOffScreen() {
		return this.isOffScreen;	
	}
	
	/**
	 * Remove bullet if off screen or collides with alien
	 */
	public void setOffScreen() {
		this.isOffScreen = true;
	}
	
	/**
	 * Determine if a bullet and alien collide based on comparing upper left and bottom right coordinates of each
	 * @param a- alien that bullet potentially collided with
	 * @return true if collision occurred
	 */
	public boolean collide(Alien a) {	
		double myTopLeftX = posX - width/2;
		double myTopLeftY = posY + height/2;
		double myBottomRightX = posX + width/2;
		double myBottomRightY = posY - height/2;
		
		double otherTopLeftX = a.getPosX() - a.getWidth()/2;
		double otherTopLeftY = a.getPosY() + a.getHeight()/2;
		double otherBottomRightX = a.getPosX() + a.getWidth()/2;
		double otherBottomRightY = a.getPosY() - a.getHeight()/2;
		
		return (myTopLeftY >= otherBottomRightY && myBottomRightY <= otherTopLeftY && myBottomRightX >= otherTopLeftX && myTopLeftX <= otherBottomRightX);
	}

}
