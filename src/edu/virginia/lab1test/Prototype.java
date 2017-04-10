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
	int goodCounter = 0;
	int frameCounterGood = 0;
	int currentTimerGood = 0;

	ArrayList<Sprite> bad = new ArrayList();
	int badCounter = 0;
	int frameCounterBad = 0;
	int currentTimerBad = 0;
	//HOW MANY COUNTERS DO WE NEED??? IS THERE A BETTER WAY TO DO THIS
	
	Random randomNum = new Random();
	
	int level = 1;	
	int[] goodGeneration = {0, 90, 120, 120};
	//goodGeneration[level]
	int[] badGeneration = {0, 180, 120, 120};
	
	int currentVY = 1; 
	int[] vThresh = {0, 120, 100, 90}; //will get faster every x amount of frames;
	int[] vCap = {0, 10, 15, 20};
	int vCounter = 0; //to count which frame we're on
	
	
	String levelMessage = "";
	boolean levelLimbo = false;
	int limboTimer = 0;
	
	int healthVal = 100;
	int pointVal = 0;
	int timeVal = 3600;
	//1 min per level - 60 fps * 60 sec = 3600
	
	
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
		if (!levelLimbo) {
			super.update(pressedKeys);
			timeVal--;
			
			if (timeVal <= 0 && !levelLimbo){
				level++;
				levelLimbo = true;
			}
			
			double xPos = trump.getPositionX();
			double yPos = trump.getPositionY();

			trump.setPosition(xPos, yPos);
			
			/****************************************************************************************
			 * 
			 * TRUMP HANDLING
			 * 
			 ***************************************************************************************/
			
			if(pressedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_LEFT))) {
				xPos -= 5;
				isWalking = true;
			}
			if (pressedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_RIGHT))) {
				xPos += 5;
				isWalking = true;
			}
			
			/*************************** BOUNDS CHECKING ***************************/
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
			
			
			/****************************************************************************************
			 * 
			 * OBJECT HANDLING
			 * 
			 ***************************************************************************************/
			
			
			/*************************** GENERATE OBJECTS RANDOMLY ***************************/
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
				//a good object will generate between 0 to 90 frames
				currentTimerGood = randomNum.nextInt(goodGeneration[level]);
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
				//a bad object will generate between 0 to 90 frames
				currentTimerBad = randomNum.nextInt(badGeneration[level]);
				frameCounterBad = 0;
			} else {
				frameCounterBad++;
			}
			
			//POWER UP OBJECTS
			//TODO
			
			
			/*************************** DROPPING PHYSICS OF OBJECTS ***************************/
			//GOOD OBJECTS
			for (int i = 0; i < good.size(); i++) {
				good.get(i).setPositionY(good.get(i).getPositionY() + good.get(i).getVY());
				if (good.get(i).getPositionY() >= 550) {
					//CHECK FOR COLLISIONS
					if(good.get(i).collidesWith(trump)){
						pointVal+=5;
						this.removeChild(good.get(i));
						good.remove(i);
						i--;
					}
					
				} else if (good.get(i).getPositionY() >= 640) {
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
					if(bad.get(i).collidesWith(trump)){
						healthVal-=10;
						this.removeChild(bad.get(i));
						bad.remove(i);
						i--;
					}
					
				} else if (bad.get(i).getPositionY() >= 640) {
					this.removeChild(bad.get(i));
					bad.remove(i);
					i--;
				}			
			}
			
			//POWER UP OBJECTS
			//TODO
			
			
			/*************************** MAKE OBJECTS FALL FASTER EVERY 60 FRAMES ***************************/
			//capped at... vCap???
			//precision for timing of first speed boost will be off but who cares????
			vCounter++;
			if (vCounter >= vThresh[level] && currentVY < vCap[level]) {
				currentVY++;
				vCounter = 0;
			}

			
			//reset iswalking
			isWalking = false;
		} else {
			limboTimer++;
			//0 to 180 and then 180 to 360
			if (limboTimer < 180 && limboTimer > 0) {
				levelMessage = "End of Level | Final Score: " + pointVal;
			} else if (limboTimer < 360 && limboTimer > 180) {
				levelMessage = "Starting Level " + level;
			} else if (limboTimer > 360) {
				//get out of limbo
				levelLimbo = false;
				//reset limbo timer
				limboTimer = 0;
				//reset level timer
				timeVal = 3600;
				//reset player health
				healthVal = 100;
				//reset object velocity
				currentVY = 1;
			}
		}
		
		
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
		String time = "Time Remaining: " + Math.floor(timeVal/60);


		g.drawString("Level: " + level, 15, 20);
		g.drawString(time, 15, 40);
		g.drawString(health, 15, 60);
		g.drawString(points, 15, 80);
		
		if (healthVal <= 0) {
			g.drawString("You Died", 400, 350);
			this.stop();
			
		}
		
		if (levelLimbo) {
			g.drawString(levelMessage, 400, 350);
		}
		
		
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
