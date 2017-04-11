package edu.virginia.lab1test;

import edu.virginia.engine.display.Game;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import edu.virginia.engine.display.Sprite;
import edu.virginia.engine.display.AnimatedSprite;
import edu.virginia.engine.display.Tween;
import edu.virginia.engine.display.TweenJuggler;
import edu.virginia.engine.util.SoundManager;


public class Alpha extends Game {

	Sprite trump = new Sprite("trump", "BoxTrump.jpg");
	boolean isWalking = false;
	
	ArrayList<Sprite> good = new ArrayList<Sprite>();
	int goodCounter = 0;
	int frameCounterGood = 0;
	int currentTimerGood = 0;

	ArrayList<Sprite> bad = new ArrayList<Sprite>();
	int badCounter = 0;
	int frameCounterBad = 0;
	int currentTimerBad = 0;
	
	ArrayList<Sprite> power = new ArrayList();
	int powerCounter = 0;
	int frameCounterPower = 0;
	int currentTimerPower = 0;

	
	Random randomNum = new Random();
	
	int level = 1;	
	//randomNum = rand.nextInt((max - min) + 1) + min;
	int[] goodGeneration = {0, 90, 120, 150};
	int[] goodGenerationMin = {0, 0, 30, 60};
	//0 to 90
	//30 to 120
	//60 to 150
	int[] badGeneration = {0, 180, 150, 120};
	int[] badGenerationMin = {0, 60, 30, 0};
	//60 to 180
	//30 to 150
	//0 to 120
	int[] powerGeneration = {0, 240, 180, 120};
	int[] powerGenerationMin = {0, 120, 60, 0};
	//120 to 240
	//60 to 180
	//0 to 120
	
	int currentVY = 1; 
	int oldVY = 1; //for the Kisses PU
	int[] vThresh = {0, 240, 210, 180}; //will get faster every x amount of frames;
	int[] vCap = {0, 10, 15, 20};
	int vCounter = 0; //to count which frame we're on
	
	boolean invulnerable = false;
	int invulnerableTimer = 300;
	int invulnerableTimerMax = 300; //this is really dumb but trust me it will make changing values easier
	boolean slowDown = false;
	int slowDownTimer = 300;
	int slowDownTimerMax = 300; //this is really dumb but trust me it will make changing values easier
	
	String invul = ""; //invulnerable string
	String sd = ""; //slow down string
	String levelMessage = "";
	boolean levelLimbo = false;
	int limboTimer = 0;
	
	int healthVal = 100;
	int pointVal = 0;
	int timeVal = 3600;
	int timeValMax = 3600;
	//1 min per level - 60 fps * 60 sec = 3600
	
	
	/**
	 * Constructor. See constructor in Game.java for details on the parameters given
	 * */
	public Alpha() {
		/******* CREATE GAME *******/
		super("Alpha", 600, 700);
		
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
		
		if (level == 5) {
			this.stop();
		}
		
		if (!levelLimbo) {
			if (level == 4) {
				/****************************************************************************************
				 ****************************************************************************************
				 *
				 * COMMENCING BOSS BATTLE
				 * 
				 *****************************************************************************************
				 ***************************************************************************************/
			} else if (level < 4){
				timeVal--;
				
				if (timeVal <= 0 && !levelLimbo){
					level++;
					
					//reset objects
					for (int i = 0; i < good.size(); i++) {
						//remove from disp tree
						this.removeChild(good.get(i));
					}
					for (int i = 0; i < bad.size(); i++) {
						//remove from disp tree
						this.removeChild(bad.get(i));
					}
					for (int i = 0; i < power.size(); i++) {
						//remove from disp tree
						this.removeChild(power.get(i));
					}
					//clear arraylists
					good.clear();
					bad.clear();
					power.clear();
					
					//reset PU status messages
					invul = "";
					sd = "";
					
					//begin limbo
					levelLimbo = true;
				}
				/****************************************************************************************
				 ****************************************************************************************
				 *
				 * COMMENCING NEXT LEVEL
				 * 
				 *****************************************************************************************
				 ***************************************************************************************/
				
				
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
					//generate an object with a velocity of currentVY
					good.get(good.size() - 1).setVY(currentVY);
					//add to display tree
					this.addChild(good.get(good.size() - 1));
					
					//increment goodCounter (kind of like an item ID)
					goodCounter++;
					//a good object will generate between minX to maxX frames
					//randomNum = rand.nextInt((max - min) + 1) + min;
					currentTimerGood = randomNum.nextInt(goodGeneration[level] - goodGenerationMin[level] + 1) + goodGenerationMin[level];
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
					//generate an object with a velocity of currentVY
					bad.get(bad.size() - 1).setVY(currentVY);
					//add to display tree
					this.addChild(bad.get(bad.size() - 1));
					
					//increment badCounter (kind of like an item ID)
					badCounter++;
					//a bad object will generate between minX to maxX frames
					//randomNum = rand.nextInt((max - min) + 1) + min;
					currentTimerBad = randomNum.nextInt(badGeneration[level] - badGenerationMin[level] + 1) + badGenerationMin[level];
					frameCounterBad = 0;
				} else {
					frameCounterBad++;
				}
				
				//POWER UP OBJECTS
				int powerUpNum = randomNum.nextInt(3);
				//0 = kiss, 1 = meatloaf, 2 = tacosalad
				
				if (frameCounterPower == currentTimerPower) {
					//generate new PU object
					if (powerUpNum == 0) {
						power.add(new Sprite("powerKiss" + powerCounter, "KissBox.png"));
					} else if (powerUpNum == 1) {
						power.add(new Sprite("powerMeatloaf" + powerCounter, "MeatloafBox.png"));
					} else if (powerUpNum == 2) {
						power.add(new Sprite("powerTacoSalad" + powerCounter, "TacoSaladBox.png"));
					}
					
					//generate a random x position between 200 and 600 (including bounds)
					power.get(power.size() - 1).setPosition(randomNum.nextInt(560 - 200 + 1) + 200, 0);
					//turn on physics for this object
					power.get(power.size() - 1).setPhysics(true);
					//make PU fall down at currentVY
					power.get(power.size() - 1).setVY(currentVY);
					//add to display tree
					this.addChild(power.get(power.size() - 1));
					
					//increment goodCounter (kind of like an item ID)
					powerCounter++;
					//a PU object will generate between minX to maxX frames
					//randomNum = rand.nextInt((max - min) + 1) + min;
					currentTimerPower = randomNum.nextInt(powerGeneration[level] - powerGenerationMin[level] + 1) + powerGenerationMin[level];
					frameCounterPower = 0;
				} else {
					frameCounterPower++;
				}
				
				
				/*************************** DROPPING PHYSICS OF OBJECTS ***************************/
				/*************************** AND COLLISION OF OBJECTS ***************************/
				//only need to check for collisions on objects past y = 550
				
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
				
				
				/*************************** INVULNERABLITY COUNTER ***************************/
				if(invulnerable && invulnerableTimer > 0){
					invulnerableTimer--;
				} else {
					invulnerable = false;
					invulnerableTimer = 300;
				}
				
				
				/*************************** MAKE OBJECTS FALL FASTER EVERY 60 FRAMES ***************************/
				//capped at... vCap???
				//precision for timing of first speed boost will be off but who cares????
				
				//KISS EFFECT ON VELOCITY
				if(slowDown && slowDownTimer == slowDownTimerMax){
					//first instance of colliding with kiss PU
					oldVY = currentVY;
					currentVY = 1;
					slowDownTimer--;
				} else if (slowDown && slowDownTimer != slowDownTimerMax && slowDownTimer > 0){
					//kiss PU has taken effect
					slowDownTimer--;
				} else if (slowDown) {
					//kiss PU timer has run out!
					currentVY = oldVY; //reset currentVY
					slowDown = false; //reset slowDown bool
					slowDownTimer = 300; //reset slowDown timer
				}
				
				vCounter++;
				if (vCounter >= vThresh[level] && currentVY < vCap[level]) {
					currentVY++;
					vCounter = 0;
				}
				
			}
			
		} else {
			limboTimer++;
			//0 to 180 and then 180 to 360
			if (limboTimer < 180 && limboTimer > 0) {
				levelMessage = "End of Level | Final Score: " + pointVal;
			} else if (limboTimer < 360 && limboTimer > 180) {
				if (level == 4) {
					levelMessage = "A wild Hillary appeared!";
				} else {
					levelMessage = "Starting Level " + level;
				}
				
			} else if (limboTimer > 360) {
				//get out of limbo
				levelLimbo = false;
				//reset limbo timer
				limboTimer = 0;
				//reset level timer
				timeVal = timeValMax;
				//reset player health
				healthVal = 100;
				//reset object velocity
				vCounter = 0;
				currentVY = 1;
				oldVY = 1;
				//reset power up status
				invulnerable = false;
				invulnerableTimer = invulnerableTimerMax;
				slowDown = false;
				slowDownTimer = slowDownTimerMax; 
			}
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
		
		if(invulnerable){
			invul = "Invulnerable for: " + Math.floor(invulnerableTimer/60);
		} else {
			invul = "";
		}
		
		if(slowDown){
			sd = "Slowing down for: " + Math.floor(invulnerableTimer/60);
		} else {
			sd = "";
		}


		g.drawString("Level: " + level, 15, 20);
		g.drawString(time, 15, 40);
		g.drawString(health, 15, 60);
		g.drawString(points, 15, 80);
		g.drawString(invul, 15, 100);
		g.drawString(sd, 15, 120);
		
		if (healthVal <= 0) {
			g.drawString("You Died", 350, 200);
			this.stop();
			
		}
		
		if (levelLimbo) {
			g.drawString(levelMessage, 275, 200);
		}
		
		
		/* Same, just check for null in case a frame gets thrown in before trump is initialized */
		if(trump != null) trump.draw(g);
	}

	/**
	 * Quick main class that simply creates an instance of our game and starts the timer
	 * that calls update() and draw() every frame
	 * */
	public static void main(String[] args) {
		Alpha game = new Alpha();
		game.start();
	}
}
