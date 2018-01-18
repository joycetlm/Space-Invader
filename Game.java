package project;

import java.awt.Color;
import java.util.Iterator;
import java.util.LinkedList;

import sedgewick.StdDraw;

/**
 * Plays game by using all created objects and Moveable interface
 * @author Zachary Polsky
 *
 */

public class Game {

	private LinkedList<Alien> aliens;
	private MultiAliens ma = new MultiAliens(1,3);//generate multialiens
	private LinkedList<Moveable> move;
	private LinkedList<Bullet> bullets;
	private Player player;
	private double alienSpeed;
	private int score;
	private int level;


	/**
	 * Creates a Game object to be implemented in the game
	 */
	public Game() {
		aliens = new LinkedList<Alien>();
		move = new LinkedList<Moveable>();
		bullets = new LinkedList<Bullet>();
		StdDraw.setScale(-1, 1);
		player = new Player(0, -.9, .04, 3);
		move.addAll(ma.multialiens);
		move.add(player);
		alienSpeed = 0.04;
		addAliens();
		score = 0;
		level = 1;
	}

	/**
	 * indicate the player's score and current level on the screen    
	 * @param score
	 * @param level
	 */
	public void drawBoard(int score, int level) {
		StdDraw.clear();
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.filledRectangle(0, 0, 1, 1);
		StdDraw.setPenColor(Color.WHITE);
		StdDraw.text(0.75, 0.9, "Score: " + score);
		StdDraw.text(0.73, 0.8, "Level: " + level);
	}
	/**
	 * determine if the game is over ( player loses all his 3 lives)
	 */
	public boolean isOver() {
		return (!(player.getLives() > 0));
	}
	/**
	 * add aliens to the list
	 */
	public void addAliens(){
		addAlien(0.5, 0.5, alienSpeed, true);
		addAlien(-0.5, 0.5, alienSpeed, true);
		addAlien(-0.9, 0.5, alienSpeed, false);
	}

	/**
	 * add new aliens
	 * @param xposition
	 * @param yposition
	 * @param speed
	 * @param upDown
	 */
	private void addAlien(double x, double y, double speed, boolean upDown)
	{
		Alien a = new Alien(x, y, speed, upDown);
		aliens.add(a);
		move.add(a);
	}

	/**
	 * add new multialiens group
	 * @param row of the group
	 * @param column of the group
	 */
	private void addMultialiens(int r, int c){

		ma.multialiens.addAll(new MultiAliens(r,c).multialiens);
		move.addAll(ma.multialiens);
	}

	/**
	 * play the game
	 */
	public void play(){
		// display the score and level
		drawBoard(score, level);
		for (Moveable m : move) {
			m.move();
			m.draw();
		}

		// if the fire key is pressed and the total number of bullets on screen is less than 3,
		// player can shoot new bullets
		if (player.fire() && bullets.size() < 3) {
			Bullet b = new Bullet(player.getPosX(), player.getPosY() + .15, .05);
			move.add(b);
			bullets.add(b);
		}

		//Check for collisions
		//if the player collide with an alien, he loses a life and minus 10 scores
		//if the bullet collide with an alien, the alien dies, the bullet disappear, the player wins 50 scores;
		//else the bullet disappear utill it is off the screen
		/*CODE A*/ // start
		for (Alien a : aliens) {
			if (player.collide(a)) {
				player.die();
				score -= 10;
			}
			for (Bullet b : bullets) {
				if (b.collide(a)) {
					a.die();
					b.setOffScreen();
					score += 50;
				}
				else if (b.getPosY() >= 1){
					b.setOffScreen();
				}
			}
		}
		/*CODE A*/ //end


		// Check for collisions for multialiens
		//if the player collide with an alien of the alien group, he loses a life and minus 5 scores
		//if the bullet collide with an alien of the alien group, the alien dies, the bullet disappear, the player wins 20 scores;
		//else the bullet disappear until it is off the screen
		for (Alien a : ma.multialiens) {
			if (player.collide(a)) {
				player.die();
				score -= 5;
			}
			for (Bullet b : bullets) {
				if (b.collide(a)) {
					a.die();
					b.setOffScreen();
					score += 20;
				}
				else if (b.getPosY() >= 1){
					b.setOffScreen();
				}
			}
		}

		// Used to prevent concurrent modification errors
		//remove the dead aliens
		Iterator<Alien> alienIter = aliens.iterator();
		while (alienIter.hasNext()) {
			Alien a = alienIter.next();
			if (!a.isAlive()) {
				alienIter.remove();
				move.remove(a);
			}
		}
		//remove the dead aliens
		Iterator<Bullet> bulletIter = bullets.iterator();
		while (bulletIter.hasNext()) {
			Bullet b = bulletIter.next();
			if (b.getIsOffScreen()) {
				bulletIter.remove();
				move.remove(b);
			}
		}

		//remove the dead aliens of multialiens
		Iterator<Alien> multialiensIter = ma.multialiens.iterator();
		while (multialiensIter.hasNext()) {
			Alien c = multialiensIter.next();
			if (!c.isAlive()) {
				multialiensIter.remove();
				move.remove(c);
			}
		}

		// Advance to next level
		//for random aliens, the speed*1.5 everytime the level increase;
		//for multialiens, the row increases
		levelUp();
		StdDraw.show(60);

		if (isOver()){
			drawGameEnd();
		}
		if (level>3){
			drawGameWin();
		}
	}


	/**
	 * generate new aliens if the player has passed the current level;
	 * game will be more difficult when the level increases
	 */
	public void levelUp() {
		if (aliens.isEmpty()&& ma.multialiens.isEmpty()) {
			level += 1;
			if (level<=3){
				this.levelIndicator();
			}
			
			//when come to a new level, reset bullets and player's position
			Iterator<Bullet> bulletIter = bullets.iterator();
			while (bulletIter.hasNext()) {
				Bullet b = bulletIter.next();
				bulletIter.remove();
				move.remove(b);
			}
			player.setPosX(0);
			player.setPosY(-0.9);
			
			//the speed of random aliens is 1.2 times faster than the previous level
			alienSpeed *= 1.2*(level-1);
			addAliens();
			
			//the row of alien group increases
			addMultialiens(level , 3);
		}
	}

	/**
	 * Display the graph if the game is over
	 */

	public void drawGameEnd()
	{
		StdDraw.clear();
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.filledRectangle(0, 0, 1, 1);
		StdDraw.setPenColor(Color.WHITE);
		StdDraw.text(0, 0, "GAME OVER");
		StdDraw.show(100);
	}

	/**
	 * display the graph when the player wins
	 */
	public void drawGameWin(){
		StdDraw.clear();
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.filledRectangle(0, 0, 1, 1);
		StdDraw.setPenColor(Color.RED);
		StdDraw.text(0, 0, "Congratulations! You win!");
		StdDraw.show(100);
	}

	public void levelIndicator(){
		StdDraw.clear();
		StdDraw.show(1000);
		StdDraw.setPenColor(Color.RED);
		StdDraw.text(0, 0, "Welcome to level "+level);
		StdDraw.show(1000);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Game game = new Game();
		//if the player passes all the 3 levels, he wins
		while (!game.isOver()&&game.level<=3){
			game.play();
		}
	}


}


