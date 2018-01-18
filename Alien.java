package project;

import sedgewick.StdDraw;

/**
 * draw a single alien and make it move
 */
public class Alien implements Moveable {

	private double posX;
	private double posY;
	private final double startX;
	private final double startY;
	private final double width = 0.15;
	private final double height = 0.15;
	private double speed;	
	private boolean upDown;
	private boolean isAlive = true;

	/**
	 * Creates an Alien object to be implemented in the game
	 * @param x- x-coordinate of alien (center)
	 * @param y- y-coordinate of alien (center)
	 * @param speed- speed at which the alien moves 
	 * @param upDown- true if alien moves up/down pattern; false if alien moves side-to-side pattern
	 */
	public Alien(double x, double y, double speed, boolean upDown) {
		this.posX = x;
		this.posY = y;
		this.startX = x;
		this.startY = y;
		this.speed = -speed;
		this.upDown = upDown;
	}


	/**
	 * get the alien's X position
	 * @return posX
	 */
	public double getPosX() {
		return this.posX;
	}

	/**
	 * get the alien's Y position
	 * @return posY
	 */
	public double getPosY() {
		return this.posY;
	}


	/**
	 * get the width of each alien
	 * @return width
	 */
	public double getWidth() {
		return this.width;
	}


	/**
	 * get the height of each alien
	 * @return height
	 */
	public double getHeight() {
		return this.height;
	}


	/**
	 * draw the graph of alien
	 */
	public void draw() {
		StdDraw.setPenColor(StdDraw.WHITE);
		StdDraw.filledRectangle(this.posX, this.posY, this.width/2, this.height/2);
	}


	/**
	 * drive the alien to move
	 */
	public void move() {
		if (isOffScreen()) {
			speed *= -1;
		}
		if (upDown) {
			this.posY += speed;
		} else {
			this.posX += speed;
		}

	}


	/**
	 * judge whether the alien is out of the screen
	 *  @return true or false
	 */
	public boolean isOffScreen() {
		return (this.posX > 1 || this.posX < -1 || this.posY > 1 || this.posY < -1);
	}


	/**
	 * set the alien to the original start position
	 */
	public void moveToStart() {
		this.posX = startX;
		this.posY = startY;
	}



	/**
	 * judge whether the alien is alive
	 * @return true or false
	 */
	public boolean isAlive() {
		return this.isAlive;
	}


	/**
	 * remove the alien
	 */
	public void die() {
		this.isAlive = false;
	}


}
