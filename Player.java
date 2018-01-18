package project;

import java.awt.Color;

import sedgewick.StdDraw;


/**
 * draw a player and let it move up,down,left and right. It can also shoot bullet to kill the aliens and get scores.
 * if it collides with the alien, it lose one life.
 */
public class Player implements Moveable {
	
	private double posX;
	private double posY; 
	private final double startPosX;
	private final double startPosY;
	private final double width = 0.1;
	private final double height = 0.1;
	private Color color;
	private int lives;
	private double speed;
	private final Color [] colors = {StdDraw.RED, StdDraw.YELLOW, StdDraw.WHITE};
	
	/**
	 * Creates a Player object to be implemented in the game
	 * @param x- x-coordinate of player (center)
	 * @param y- y-coordinate of player (center)
	 * @param speed- speed at which the player moves 
	 * @param lives- number of lives the player starts with
	 */
	public Player(double x, double y, double speed, int lives) {
		this.posX = x;
		this.posY = y;
		this.startPosX = x;
		this.startPosY = y;
		this.speed = speed;
		this.lives = lives;
		this.color = colors[(this.lives-1) % 3];
	}
	
	
	/**
	 * draw the graph of the player 
	 */
	public void draw() {
		StdDraw.setPenColor(this.color);
		StdDraw.filledRectangle(this.posX, this.posY, this.width/2, this.height/2);
		StdDraw.filledRectangle(this.posX, (this.posY + this.height/1.5), this.width/6, this.height/2);
	}
	
	

	/**
	 * drive the player to move
	 */
	public void move() {
		
		//If movements are possible:(press A move to the left; press B move to the right)
		if ((ArcadeKeys.isKeyPressed(0, 1)) && (this.posX - this.speed > -1)) {
			this.posX -= this.speed;
		}
		else if ((ArcadeKeys.isKeyPressed(0, 3)) && (this.posX + this.speed < 1)) {
			this.posX += this.speed;
		}
	}
	
	

	/**
	 * get the palyer's X position
	 * @return posX
	 */
	public double getPosX() {
		return this.posX;
	}
	
	
	/**
	 * set the palyer's X position
	 * @return posX
	 */
	public final void setPosX(double posX) {
		this.posX = posX;
	}


	/**
	 * set the palyer's Y position
	 * @return posY
	 */
	public final void setPosY(double posY) {
		this.posY = posY;
	}


	/**
	 * get the palyer's Y position
	 * @return posY
	 */
	public double getPosY() {
		return this.posY;
	}
	
	
	/**
	 * get the palyer's number of lives
	 * @return lives
	 */
	public int getLives() {
		return this.lives;
	}
	
	
	/**
	 * determine whether the key is pressed, if pressed, the player will shoot bullets
	 * @return true or false
	 */
	public boolean fire() {
		return (ArcadeKeys.isKeyPressed(0, 0)); //key w pushed
	}
	
	/**
	 * Kills player by reducing life and changing colors
	 */
	public void die() {
		this.lives--;
		if (lives != 0) {
			this.color = colors[lives - 1];
		}
		this.posX = this.startPosX;
		this.posY = this.startPosY; //doesn't do anything but good form
	}
	
	/**
	 * Determine if the player and alien collide based on comparing upper left and bottom right coordinates of each
	 * @param a- alien that player potentially collided with
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
