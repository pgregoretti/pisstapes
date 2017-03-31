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
public class Prototype extends Game {

	Sprite trump = new Sprite("trump", "BoxTrump.jpg");
	boolean isWalking = false;
	ArrayList<Sprite> good = new ArrayList();
	ArrayList<Sprite> bad = new ArrayList();
	int goodCounter = 0;
	int badCounter = 0;
	int frameCounterGood = 0;
	int frameCounterBad = 0;
	int currentTimerGood = 0;
	int currentTimerBad = 0;
	int futureTimerGood = 0;
	int futureTimerBad = 0;
	int currentVY = 1; //will get faster every 60 frames;
	int vCounter = 0;
	//HOW MANY COUNTERS DO WE NEED??? IS THERE A BETTER WAY TO DO THIS
	Random randomNum = new Random();
	
	int healthVal = 100;
	int pointVal = 0;
	
	
	/**
	 * Constructor. See constructor in Game.java for details on the parameters given
	 * */
	public Prototype() {
		/******* CREATE GAME *******/
		super("Prototype", 600, 700);
		
		/****** NOTE ******/
		//0 TO 200 IS THE "SCOREBOARD"
		//200 TO 600 IS THE GAME SCREEN
		//BOUNDS TO CHECK FOR--ITEMS SHOULD ONLY GENERATE BETWEEN 200 < X < 600

		
		/******* ADD SPRITES *******/
		this.addChild(trump);
		
		/******* ASSIGN ANIMATIONS *******/
		
		/******* POSITION SPRITES *******/
		trump.setPosition(200, 590);
		
		/******* SETUP TWEENS *******/
		
		/******* ADD TWEENS *******/
		
		/******* ADD SOUNDS *******/
		
		
	}
	
	
	/**
	 * Engine will automatically call this update method once per frame and pass to us
	 * the set of keys (as strings) that are currently being pressed down
	 * */
	@Override
	public void update(ArrayList<String> pressedKeys) {
		super.update(pressedKeys);
		
		double xPos = trump.getPositionX();
		double yPos = trump.getPositionY();

		trump.setPosition(xPos, yPos);
		
		/****** DROPPING PHYSICS OF OBJECTS *****/
		//GOOD OBJECTS
		for (int i = 0; i < good.size(); i++) {
			good.get(i).setPositionY(good.get(i).getPositionY() + good.get(i).getVY());
			if (good.get(i).getPositionY() >= 550) {
				//CHECK FOR COLLISIONS
				if(good.get(i).collidesWith(trump)){
					//IF COLLIDE, ADD POINTS
					pointVal++;
					
				}
				//REMOVE FROM DISPLAY TREE
				this.removeChild(good.get(i));
				//REMOVE FROM ARRAYLIST
				good.remove(good.get(i));
			}
			if (good.get(i).getPositionY() >= 640) {
				this.removeChild(good.get(i));
				good.remove(i);
				i--;
			}			
		}
		
		//BAD OBJECTS
		for (int i = 0; i < bad.size(); i++) {
			bad.get(i).setPositionY(bad.get(i).getPositionY() + bad.get(i).getVY());
			if (bad.get(i).getPositionY() >= 550) {
				//CHECK FOR COLLISIONS
				//IF COLLIDE, REMOVE HEALTH
				if(bad.get(i).collidesWith(trump)){
					healthVal--;
					
				}
				//REMOVE FROM DISPLAY TREE
				this.removeChild(bad.get(i));
				
				//REMOVE FROM ARRAYLIST
				bad.remove(bad.get(i));
			}
			if (bad.get(i).getPositionY() >= 640) {
				this.removeChild(bad.get(i));
				bad.remove(i);
				i--;
			}			
		}
		
		//POWER UP OBJECTS
		//TODO
			
		if(pressedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_LEFT))) {
			xPos -= 5;
			isWalking = true;
		}
		if (pressedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_RIGHT))) {
			xPos += 5;
			isWalking = true;
		}
		
		/******* MAKE OBJECTS FALL FASTER EVERY 60 FRAMES ******/
		//precision for timing of first speed boost will be off but who cares????
		vCounter++;
		if (vCounter >= 60) {
			currentVY++;
			vCounter = 0;
		}
		
		/******* GENERATE OBJECTS RANDOMLY ******/
		//GOOD OBJECTS
		if (frameCounterGood == currentTimerGood) {
			//generate new good object
			good.add(new Sprite("good" + goodCounter, "BoxGood.jpg"));
			//generate a random x position between 200 and 600 (including bounds)
			good.get(good.size() - 1).setPosition(randomNum.nextInt(560 - 200 + 1) + 200, 0);
			//turn on physics for this object
			good.get(good.size() - 1).setPhysics(true);
			good.get(good.size() - 1).setVY(currentVY);
			//add to display tree
			this.addChild(good.get(good.size() - 1));
			
			//increment goodCounter (kind of like an item ID)
			goodCounter++;
			currentTimerGood = futureTimerGood;
			//a good object will generate between 0 to 90 frames
			futureTimerGood = randomNum.nextInt(90);
			frameCounterGood = 0;
		} else {
			frameCounterGood++;
		}
		
		//BAD OBJECTS
		if (frameCounterBad == currentTimerBad) {
			//generate new bad object
			bad.add(new Sprite("bad" + badCounter, "BoxBad.jpg"));
			//generate a random x position between 200 and 600 (including bounds)
			bad.get(bad.size() - 1).setPosition(randomNum.nextInt(560 - 200 + 1) + 200, 0);
			//turn on physics for this object
			bad.get(bad.size() - 1).setPhysics(true);
			bad.get(bad.size() - 1).setVY(currentVY);
			//add to display tree
			this.addChild(bad.get(bad.size() - 1));
			
			//increment badCounter (kind of like an item ID)
			badCounter++;
			currentTimerBad = futureTimerBad;
			//a bad object will generate between 0 to 90 frames
			futureTimerBad = randomNum.nextInt(90);
			frameCounterBad = 0;
		} else {
			frameCounterBad++;
		}
		
		//POWER UP OBJECTS
		//TODO
		
		
		/**** BOUNDS CHECKING ****/
		//x goes from 200 to 600
		if (xPos < 200) {
			xPos = 200;
		} else if (xPos > 540) {
			xPos = 540;
		}
		//y goes from 0 to 700
		if (yPos > 590) {
			yPos = 590;
		}

		trump.setPosition(xPos, yPos);
		
		//reset iswalking
		isWalking = false;
		
		
		/* Make sure trump is not null. Sometimes Swing can auto cause an extra frame to go before everything is initialized */
		if(trump != null) trump.update(pressedKeys);
	}
	
	/**
	 * Engine automatically invokes draw() every frame as well. If we want to make sure trump gets drawn to
	 * the screen, we need to make sure to override this method and call trump's draw method.
	 * */
	@Override
	public void draw(Graphics g){
		super.draw(g);
		
		//draw out hitboxes for testing purposes
		for(int i =0; i<this.getChildren().size(); i++){
			int currentX = (int) this.getChild(i).getPositionX();
			int currentY = (int) this.getChild(i).getPositionY();
			int currentWidth = (int) (this.getChild(i).getUnscaledWidth() * this.getChild(i).getScaleX());
			int currentHeight = (int) (this.getChild(i).getUnscaledHeight() * this.getChild(i).getScaleY());
			g.drawRect(currentX, currentY, currentWidth, currentHeight);
		}
		
		g.drawRect(0, 0, 200, 700);
		g.drawRect(200, 0, 400, 700);

		String health = "Health: " + healthVal;
		String points = "Points: " + pointVal;


		g.drawString(health, 15, 20);
		g.drawString(points, 15, 40);
		
		/* Same, just check for null in case a frame gets thrown in before trump is initialized */
		if(trump != null) trump.draw(g);
	}

	/**
	 * Quick main class that simply creates an instance of our game and starts the timer
	 * that calls update() and draw() every frame
	 * */
	public static void main(String[] args) {
		Prototype game = new Prototype();
		game.start();
	}
}
