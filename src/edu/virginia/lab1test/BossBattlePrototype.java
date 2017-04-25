package edu.virginia.lab1test;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import edu.virginia.engine.display.AnimatedSprite;
import edu.virginia.engine.display.Game;
import edu.virginia.engine.display.Sprite;
import edu.virginia.engine.display.Tween;
import edu.virginia.engine.display.TweenEvent;
import edu.virginia.engine.display.TweenJuggler;
import edu.virginia.engine.events.Event;
import edu.virginia.engine.events.EventDispatcher;
import edu.virginia.engine.util.SoundManager;

/**
 * Example game that utilizes our engine. We can create a simple prototype game with just a couple lines of code
 * although, for now, it won't be a very fun game :)
 * */
public class BossBattlePrototype extends Game {
	Random hillaryRandom = new Random();
	int tempX;
	int tempY;
	int timer = 60;

	/* Create a sprite object for our game. We'll use link */
	AnimatedSprite link = new AnimatedSprite("Link", "link-spritesheet.png", 120, 127, 4, 10, 10, 13);
	Tween linkEnter1 = new Tween(link);
	Tween linkEnter2 = new Tween(link);
	
	Sprite hillary = new Sprite("hillary" , "hillary.png");
	Tween hillaryX = new Tween(hillary);
	Tween hillaryY = new Tween(hillary);
	
	Sprite bullet = new Sprite("bullet", "bullet.png");
	Tween bulletY = new Tween(bullet);
	
	Sprite fireball = new Sprite("fireball", "fireball.png");
	Tween fireballY = new Tween(fireball);
	
	static SoundManager sound = new SoundManager();
	
	String winner = "";
	boolean isWalking = false;
	
	
	/**
	 * Constructor. See constructor in Game.java for details on the parameters given
	 * */
	public BossBattlePrototype() {
		/******* CREATE GAME *******/
		super("BOSS BATTLE PROTOTYPE", 1000, 700);
		
		
		/******* ADD SPRITES *******/
		link.addChild(bullet);
		this.addChild(link);
		this.addChild(hillary);
		
		/******* ASSIGN ANIMATIONS *******/
		link.setAnimation("left", 10, 19);
		link.setAnimation("right", 30, 39);
		
		/******* POSITION SPRITES *******/
		link.setPosition(400, 550);
		hillary.setPosition(340, 0);
		
		/******* SETUP TWEENS *******/
		linkEnter1.animate("SCALE_X", 0.0, 1.0, 50);
		linkEnter2.animate("SCALE_Y", 0.0, 1.0, 50);
		tempX = hillaryRandom.nextInt(900);
		tempY = hillaryRandom.nextInt(400);
		//System.out.println("Hillary is travelling to " + "(" + Integer.toString(tempX) + ", " + Integer.toString(tempY) + ")");
		System.out.println("Hillary is travelling to " + Integer.toString(tempX));
		hillaryX.animate("X", hillary.getPositionX(), (double)tempX, 125);
		hillaryY.animate("Y", hillary.getPositionY(), (double)tempY, 125);
		

		
		/******* ADD TWEENS *******/
		TweenJuggler.getInstance().add(linkEnter1); // 0		
		TweenJuggler.getInstance().add(linkEnter2);	// 1
		TweenJuggler.getInstance().add(hillaryX); // 2
		TweenJuggler.getInstance().add(hillaryY); // 3
		
		/******* ADD SOUNDS *******/
		//sound.LoadMusic("zelda", "zelda.wav");
		sound.LoadSoundEffect("rupee", "OOT_Get_Rupee.wav");
		
		
	}
	
	
	/**
	 * Engine will automatically call this update method once per frame and pass to us
	 * the set of keys (as strings) that are currently being pressed down
	 * */
	@Override
	public void update(ArrayList<String> pressedKeys){
		super.update(pressedKeys);
		double xPos = link.getPositionX();
		double yPos = link.getPositionY();
		
		xPos = xPos + link.getVX();
		yPos = yPos + link.getVY();
		
		
		
		link.setPosition(xPos, yPos);
		
		/** PRESS SPACE TO SHOOT BULLET **/
		if(pressedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_SPACE))){
			if(!TweenJuggler.getInstance().getTweens().contains(bulletY)){
				bulletY.animate("Y", bullet.getPositionY(), -900, 150);
				link.removeChild(bullet);
				this.addChild(bullet);
				bullet.setPositionY(link.getPositionY());
				bullet.setPositionX(link.getPositionX());
				TweenJuggler.getInstance().add(bulletY);
			}
			
		}
		
		
		/**
		 *  There is currently a weird bug where if you keep pressing space
		 *  rapidly the bullet disconnects from link
		 */
		
		
		
		
		// This is a little wonky maybe
		/** RESPAWN BULLET **/
		if(!TweenJuggler.getInstance().getTweens().contains(bulletY) && bullet.getPositionY() <= -128){
//			int i = TweenJuggler.getInstance().getTweens().indexOf(bulletY);
//			TweenJuggler.getInstance().getTweens().remove(i);
			this.removeChild(bullet);
			link.addChild(bullet);
			bullet.setPositionY(link.getPositionY());
			bullet.setPositionX(link.getPositionX());
		}
		
		/** BULLET COLLISION DETECTION **/
		if(bullet.collidesWith(hillary) && this.contains(bullet)){
			link.removeChild(bullet);
			this.removeChild(bullet);
			int i = TweenJuggler.getInstance().getTweens().indexOf(bulletY);
			TweenJuggler.getInstance().getTweens().remove(i);
			System.out.println("HILLARY HAS BEEN HIT");
			link.addChild(bullet);
			bullet.setPositionY(link.getPositionY());
			bullet.setPositionX(link.getPositionX());
		}
		
		/** RANDOM HILLARY MOVE GENERATOR **/
		if(timer==0){
			int move = hillaryRandom.nextInt(3);
			if(move==0 && !TweenJuggler.getInstance().getTweens().contains(fireballY)){
				hillary.addChild(fireball);
				fireballY.animate("Y", fireball.getPositionY(), 900, 150);
				hillary.removeChild(fireball);
				this.addChild(fireball);
				fireball.setPositionY(hillary.getPositionY());
				fireball.setPositionX(hillary.getPositionX());
				TweenJuggler.getInstance().add(fireballY);	
			}
			if(move==1){
				System.out.println("Hillary uses attack 1");
			}
			if(move==2){
				System.out.println("Hillary uses attack 2");
			}
			timer = 60*(hillaryRandom.nextInt(5)+1);
			System.out.println("Set timer to: " + Integer.toString(timer/60));
		} else {
			timer--;
		}
		
		/** WEIRD EDGE CASE TEST **/
		// causes a flicker :(
		if(!TweenJuggler.getInstance().getTweens().contains(fireballY) && (this.contains(fireball) || hillary.contains(fireball))){
			this.removeChild(fireball);
		}
		
		/** FIREBALL COLLISION DETECTION **/
		if(fireball.collidesWith(link) && this.contains(fireball)){
			hillary.removeChild(fireball);
			this.removeChild(fireball);
			int i = TweenJuggler.getInstance().getTweens().indexOf(fireballY);
			TweenJuggler.getInstance().getTweens().remove(i);
			System.out.println("LINK HAS BEEN HIT");
		}
		
		/** HILLARY MOVEMENT **/
		if(!TweenJuggler.getInstance().getTweens().contains(hillaryX) && !TweenJuggler.getInstance().getTweens().contains(hillaryY)){
			/** X TWEEN **/
			int prevX = tempX;
			tempX = hillaryRandom.nextInt(900);
			hillaryX.animate("X", hillary.getPositionX(), (double)tempX, 125);
			TweenJuggler.getInstance().add(hillaryX);
			
			/** Y TWEEN **/
			int prevY = tempY;
			tempY = hillaryRandom.nextInt(400);
			hillaryY.animate("Y", hillary.getPositionY(), (double)tempY, 125);
			TweenJuggler.getInstance().add(hillaryY); 
			
			/** DEBUGGER MESSAGES **/
			//System.out.println("Hillary is travelling from " + Integer.toString(prevX) + " to " + Integer.toString(tempX));
			//System.out.println("Hillary is travelling from " + Integer.toString(prevY) + " to " + Integer.toString(tempXY));
			System.out.println("Hillary is travelling from (" + Integer.toString(prevX) 
					+ ", " + Integer.toString(prevY) + ") to (" 
					+ Integer.toString(tempX) + ", " + Integer.toString(tempY) + ")");
			
			
		}

		if(pressedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_UP)) && link.getVY() == 0) {
			//JUMPING ACTIONS
			link.setVY(-13);
			
			//LINK IS MOVING LEFT OR RIGHT AS WELL AS JUMPING
			//favoring left over right
			if(pressedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_LEFT))) {
				link.setVX(-5);
			} else if (pressedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_RIGHT))) {
				link.setVX(5);
			}
			
		} else {
			//OTHERWISE LINK IS JUST WALKING
			
			if(pressedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_LEFT))) {
				xPos--;
				if (link.getAnimation() != "left")
					link.animate("left");
				isWalking = true;
			}
			if (pressedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_RIGHT))) {
				xPos++;
				if (link.getAnimation() != "right")
					link.animate("right");
				isWalking = true;
			}
		}
		
		/**** PHYSICS ****/
		/**
		 *  Y stuff
		 */

		// air drag
		if (link.getVY() > 0) {
			link.setVY(link.getVY() - 0.1);
			if (link.getVY() <= 0) {
				link.setVY(0.0);
			}
		} else if (link.getVY() < 0) {
			link.setVY(link.getVY() + 0.1);
			if (link.getVY() >= 0) {
				link.setVY(0.0);
			}
		}
		// to make it drop down
//		if (yPos < 260 && link.getVY() == 0) {
//			link.setVY(9.8);
//		}
		// when it's in the air
		if (yPos > 0 && yPos < 250) {
			link.setVY(link.getVY() + 0.5);
		}
		
		/**
		 * X STUFF
		 */

		// air drag
		if (link.getVX() > 0) {
			link.setVX(link.getVX() - 0.1);
			if (link.getVX() <= 0) {
				link.setVX(0.0);
			}
		} else if (link.getVX() < 0) {
			link.setVX(link.getVX() + 0.1);
			if (link.getVX() >= 0) {
				link.setVX(0.0);
			}
		}
		
		/* could do bounds checking by having border sprites just outside the frame
		 * and when collision is detected, reset the position of the sprite
		 */
		
		/**** BOUNDS CHECKING ****/
		//x goes from 0 to 880
		if (xPos < 0) {
			xPos = 0;
			link.setVX(0);
		} else if (xPos > 880) {
			xPos = 880;
			link.setVX(0);
		}
		//y goes from 0 to 560
		if (yPos < 0) {
			yPos = 1;
			link.setVY(0);
		} else if (yPos > 550) {
			yPos = 550;
			link.setVY(0);
		}

		link.setPosition(xPos, yPos);

		if(pressedKeys.size() == 0 || !isWalking) {
			link.setPause(true);
		} else {
			link.setPause(false);
		}
		
		//reset iswalking
		isWalking = false;
		
//		if (/*TODO*/true) {
//			winner = "Player 1 wins!";
//		}
		
		
		/* Make sure link is not null. Sometimes Swing can auto cause an extra frame to go before everything is initialized */
		if(link != null) link.update(pressedKeys);
	}
	
	/**
	 * Engine automatically invokes draw() every frame as well. If we want to make sure link gets drawn to
	 * the screen, we need to make sure to override this method and call link's draw method.
	 * */
	@Override
	public void draw(Graphics g){
		super.draw(g);
		g.drawString(winner, 200, 20);
		g.drawRect(tempX, tempY, 3, 3);
		
		/* Same, just check for null in case a frame gets thrown in before link is initialized */
		if(link != null) link.draw(g);
	}

	/**
	 * Quick main class that simply creates an instance of our game and starts the timer
	 * that calls update() and draw() every frame
	 * */
	public static void main(String[] args) {
		BossBattlePrototype game = new BossBattlePrototype();
		game.start();
		sound.PlayMusic("zelda");


	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		super.keyReleased(e);
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			
			
		}
	}
}
