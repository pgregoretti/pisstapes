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
 * Example game that utilizes our engine. We can create a simple PowerUpPrototype game with just a couple lines of code
 * although, for now, it won't be a very fun game :)
 * */
public class PowerUpPrototype extends Game {

	Sprite trump = new Sprite("trump", "BoxTrump.jpg");
	boolean isWalking = false;
	
	ArrayList<Sprite> good = new ArrayList();
	int goodCounter = 0;
	int frameCounterGood = 0;
	int currentTimerGood = 0;
	int futureTimerGood = 0;

	ArrayList<Sprite> bad = new ArrayList();
	int badCounter = 0;
	int frameCounterBad = 0;
	int currentTimerBad = 0;
	int futureTimerBad = 0;
	
	ArrayList<Sprite> power = new ArrayList();
	int powerCounter = 0;
	int frameCounterPower = 0;
	int currentTimerPower = 0;
	int futureTimerPower = 0;
	
	int currentVY = 1; //will get faster every 60 frames;
	int oldVY = 1;
	int newVY = 1;
	int vCounter = 0;
	//HOW MANY COUNTERS DO WE NEED??? IS THERE A BETTER WAY TO DO THIS
	
	Random randomNum = new Random();
	
	int level = 1;	
	int healthVal = 100;
	int pointVal = 0;
	int timeVal = 3600;
	//1 min per level - 60 fps * 60 sec = 3600
	
	boolean invulnerable = false;
	double invulnerableTimer = 300;
	boolean slowDown = false;
	double slowDownTimer = 300;
	
	
	/**
	 * Constructor. See constructor in Game.java for details on the parameters given
	 * */
	public PowerUpPrototype() {
		/******* CREATE GAME *******/
		super("PowerUpPrototype", 600, 700);
		
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
		timeVal--;
		super.update(pressedKeys);
		
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
//		if (frameCounterGood == currentTimerGood) {
//			//generate new good object
//			good.add(new Sprite("good" + goodCounter, "BoxGood.jpg"));
//			//generate a random x position between 200 and 600 (including bounds)
//			good.get(good.size() - 1).setPosition(randomNum.nextInt(560 - 200 + 1) + 200, 0);
//			//turn on physics for this object
//			good.get(good.size() - 1).setPhysics(true);
//			good.get(good.size() - 1).setVY(currentVY);
//			//add to display tree
//			this.addChild(good.get(good.size() - 1));
//			
//			//increment goodCounter (kind of like an item ID)
//			goodCounter++;
//			currentTimerGood = futureTimerGood;
//			//a good object will generate between 0 to 90 frames
//			futureTimerGood = randomNum.nextInt(90);
//			frameCounterGood = 0;
//		} else {
//			frameCounterGood++;
//		}
		
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
		int powerUpNum = randomNum.nextInt(3);
		if (frameCounterPower == currentTimerPower) {
			//generate new good object
			switch(powerUpNum){
			case(0):
				power.add(new Sprite("powerKiss" + powerCounter, "KissBox.png"));
				break;
			case(1):
				power.add(new Sprite("powerMeatloaf" + powerCounter, "MeatloafBox.png"));
				break;
			case(2):
				power.add(new Sprite("powerTacoSalad" + powerCounter, "TacoSaladBox.png"));
				break;
			default:
				System.out.println("Power sprite generating error!");
			}
			
			//generate a random x position between 200 and 600 (including bounds)
			power.get(power.size() - 1).setPosition(randomNum.nextInt(560 - 200 + 1) + 200, 0);
			//turn on physics for this object
			power.get(power.size() - 1).setPhysics(true);
			power.get(power.size() - 1).setVY(currentVY);
			//add to display tree
			this.addChild(power.get(power.size() - 1));
			
			//increment goodCounter (kind of like an item ID)
			powerCounter++;
			currentTimerPower = futureTimerPower;
			//a good object will generate between 0 to 90 frames
			futureTimerPower = randomNum.nextInt(90);
			frameCounterPower = 0;
		} else {
			frameCounterPower++;
		}
		
		
		/*************************** DROPPING PHYSICS OF OBJECTS ***************************/
		//GOOD OBJECTS
//		for (int i = 0; i < good.size(); i++) {
//			good.get(i).setPositionY(good.get(i).getPositionY() + good.get(i).getVY());
//			if (good.get(i).getPositionY() >= 550) {
//				//CHECK FOR COLLISIONS
//				if(good.get(i).collidesWith(trump)){
//					pointVal+=5;
//					this.removeChild(good.get(i));
//					good.remove(i);
//					i--;
//				}
//				
//			} else if (good.get(i).getPositionY() >= 640) {
//				this.removeChild(good.get(i));
//				good.remove(i);
//				i--;
//			}			
//		}
		
		//BAD OBJECTS
		for (int i = 0; i < bad.size(); i++) {
			bad.get(i).setPositionY(bad.get(i).getPositionY() + bad.get(i).getVY());
			if (bad.get(i).getPositionY() >= 550) {
				//CHECK FOR COLLISIONS
				if(bad.get(i).collidesWith(trump) && !invulnerable){
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
		for (int i = 0; i < power.size(); i++) {
			power.get(i).setPositionY(power.get(i).getPositionY() + power.get(i).getVY());
			if (power.get(i).getPositionY() >= 550) {
				//CHECK FOR COLLISIONS
				if(power.get(i).collidesWith(trump)){
					if(power.get(i).getId().contains("Kiss")){
						System.out.println("Trump was kissed by Ivanka");
						if(!slowDown){
							slowDown = true;
						}
						this.removeChild(power.get(i));
						power.remove(i);
						i--;
					} else if(power.get(i).getId().contains("Meatloaf")){
						System.out.println("Trump ate meatloaf with Chris Christie");
						if(healthVal<100){
							healthVal+=10;
						}
						this.removeChild(power.get(i));
						power.remove(i);
						i--;
					} else if(power.get(i).getId().contains("TacoSalad")){
						System.out.println("Trump ate a Taco Salad and became less racist");
						if(!invulnerable){
							invulnerable = true;
						}
						this.removeChild(power.get(i));
						power.remove(i);
						i--;
					}
				}
				
			} else if (power.get(i).getPositionY() >= 640) {
				this.removeChild(power.get(i));
				power.remove(i);
				i--;
			}			
		}
		
		
		/*************************** MAKE OBJECTS FALL FASTER EVERY 60 FRAMES ***************************/
		//capped at... 20???
		//precision for timing of first speed boost will be off but who cares????
		
		oldVY = currentVY;
		if(slowDown){
			//oldVY = currentVY;
			currentVY = 1;
		} else {
			currentVY = oldVY;
		}
		
		vCounter++;
		if (vCounter >= 60 && currentVY < 20) {
			currentVY++;
			vCounter = 0;
		}
		
		/*************************** INVULNERABLITY & SLOW DOWN COUNTER ***************************/
		if(invulnerable && invulnerableTimer > 0){
			invulnerableTimer--;
			//System.out.println("Timer: " + Double.toString(invulnerableTimer));
		} else {
			invulnerable = false;
			invulnerableTimer = 300;
		}
		
		if(slowDown && slowDownTimer > 0){
			slowDownTimer--;
			//System.out.println("Timer: " + Double.toString(invulnerableTimer));
		} else {
			slowDown = false;
			slowDownTimer = 300;
		}

		
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
		String time = "Time Remaining: " + Math.floor(timeVal/60);
		
		String invul = "dummystring";
		if(invulnerable){
			invul = "Invulnerable for: " + Math.floor(invulnerableTimer/60);
		} else {
			invul = "You are vulnerable";
		}
		

		String sd = "dummystring";
		if(slowDown){
			sd = "Slowing down for: " + Math.floor(invulnerableTimer/60);
		} else {
			sd = "Items falling at normal speed";
		}
		

		g.drawString("Level: " + level, 15, 20);
		g.drawString(time, 15, 40);
		g.drawString(health, 15, 60);
		g.drawString(points, 15, 80);
		g.drawString(invul, 15, 100);
		g.drawString(sd, 15, 120);
		
		if (healthVal <= 0) {
			g.drawString("You Died", 400, 350);
			this.stop();
			
		}
	
		if(timeVal <= 0){
			g.drawString("Level Ended", 400, 350);
			g.drawString("Final Score: " + pointVal, 400, 370);
			this.stop();

			
		}
		
		
		/* Same, just check for null in case a frame gets thrown in before trump is initialized */
		if(trump != null) trump.draw(g);
	}

	/**
	 * Quick main class that simply creates an instance of our game and starts the timer
	 * that calls update() and draw() every frame
	 * */
	public static void main(String[] args) {
		PowerUpPrototype game = new PowerUpPrototype();
		game.start();
	}
}
