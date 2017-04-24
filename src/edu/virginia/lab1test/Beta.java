package edu.virginia.lab1test;

import edu.virginia.engine.display.Game;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import edu.virginia.engine.display.DisplayObjectContainer;
import edu.virginia.engine.display.Sprite;
import edu.virginia.engine.display.AnimatedSprite;
import edu.virginia.engine.display.Tween;
import edu.virginia.engine.display.TweenJuggler;
import edu.virginia.engine.util.SoundManager;


public class Beta extends Game {
	
	boolean restartGame = false;

//	Sprite trump = new Sprite("trump", "BoxTrump.jpg");
	AnimatedSprite trump = new AnimatedSprite("Trump", "trump-spritesheet.png", 94, 150, 2, 8, 0);
	boolean isWalking = false;
	
	/*************************** BACKGROUND AND SCREEN CONTAINERS ***************************/
	DisplayObjectContainer background = new DisplayObjectContainer("background");
	DisplayObjectContainer allobjects = new DisplayObjectContainer("allobjects");
	DisplayObjectContainer screen = new DisplayObjectContainer("screen");
	
	/*************************** BASIC LEVEL VARIABLES ***************************/
	ArrayList<Sprite> good = new ArrayList<Sprite>();
	int goodCounter = 0;
	int frameCounterGood = 0;
	int currentTimerGood = 0;

	ArrayList<Sprite> bad = new ArrayList<Sprite>();
	int badCounter = 0;
	int frameCounterBad = 0;
	int currentTimerBad = 0;
	
	ArrayList<Sprite> power = new ArrayList<Sprite>();
	int powerCounter = 0;
	int frameCounterPower = 0;
	int currentTimerPower = 0;
	
	Random randomNum = new Random();
	
	
	/*************************** BOSS LEVEL VARIABLES ***************************/
	Random hillaryRandom = new Random();
	int tempX;
	int hilTimer = 60;
	
	Sprite hillary = new Sprite("hillary" , "BoxHillary.jpg");
	Tween hillaryX = new Tween(hillary);

	ArrayList<Sprite> bullet = new ArrayList<Sprite>();
	int bulletCounter = 0;
	
	ArrayList<Sprite> fireball = new ArrayList<Sprite>();
	int fireballCounter = 0;
	int frameCounterFB = 0;
	int currentTimerFB = 0;
	
	int hilHealthVal = 100;
	int numBullets = 0;
	int bulletVY = 10;
	int fireballVY = 15;
	
	int victory = 0;
	//0 = still playing, 1 = win!, -1 = lose!
	
	
	/*************************** LEVEL SWITCHING VARIABLES ***************************/
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
	public Beta() {
		/******* CREATE GAME *******/
		super("Beta", 600, 700);
		
		/****** NOTE ******/
		//0 TO 200 IS THE "SCOREBOARD"
		//200 TO 600 IS THE GAME SCREEN
		//BOUNDS TO CHECK FOR--ITEMS SHOULD ONLY GENERATE BETWEEN 200 < X < 600
		
		
		/******* ADD SPRITES *******/
		this.addChild(background);
		this.addChild(allobjects);
		this.addChild(screen);
		allobjects.addChild(trump);
		allobjects.addChild(hillary);
//		this.addChild(trump);
//		this.addChild(hillary);
		
		/******* ASSIGN ANIMATIONS *******/
		trump.setAnimation("right", 0, 7);
		trump.setAnimation("left", 8, 15);
		
		/******* POSITION SPRITES *******/
		trump.setPosition(350, 530);
		//set hillary off screen
		hillary.setPosition(610, 50);
		
		/******* SETUP TWEENS *******/
		//hillary goes from x = 250 to 490
		tempX = hillaryRandom.nextInt((490 - 250) + 1) + 250;
		System.out.println("Hillary is travelling to " + Integer.toString(tempX));
		hillaryX.animate("X", hillary.getPositionX(), (double)tempX, 125);
		
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
		/****************************************************************************************
		 * 
		 * GAME SCREEN HANDLING
		 * 
		 ***************************************************************************************/
		if (restartGame) {
			//reset objects
			for (int i = 0; i < good.size(); i++) {
				//remove from disp tree
				allobjects.removeChild(good.get(i));
			}
			for (int i = 0; i < bad.size(); i++) {
				//remove from disp tree
				allobjects.removeChild(bad.get(i));
			}
			for (int i = 0; i < power.size(); i++) {
				//remove from disp tree
				allobjects.removeChild(power.get(i));
			}
			//clear arraylists
			good.clear();
			bad.clear();
			power.clear();
			
			//reset PU status messages
			invul = "";
			sd = "";
			
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
		
		
		double xPos = trump.getPositionX();
		double yPos = trump.getPositionY();
		
		/****************************************************************************************
		 * 
		 * TRUMP HANDLING
		 * 
		 ***************************************************************************************/
		
		if(pressedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_LEFT))) {
			xPos -= 4;
			if (trump.getAnimation() != "left")
				trump.animate("left");
			isWalking = true;
		}
		if (pressedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_RIGHT))) {
			xPos += 4;
			if (trump.getAnimation() != "right")
				trump.animate("right");
			isWalking = true;
		}
		
		/*************************** BOUNDS CHECKING ***************************/
		//x goes from 200 to 506
		if (xPos < 200) {
			xPos = 200;
		} else if (xPos > 506) {
			xPos = 506;
		}

		trump.setPosition(xPos, yPos);
		
		if (level == 5) {
			this.stop();
		} else if (level < 4) {
			timeVal--;
			if (timeVal <= 0) {
				timeVal = 0;
			}
			
			//update Trump's bullet inventory
			numBullets = pointVal/10;
			
			if (timeVal <= 0 && !levelLimbo){
				level++;
				
				//reset objects
				for (int i = 0; i < good.size(); i++) {
					//remove from disp tree
					allobjects.removeChild(good.get(i));
				}
				for (int i = 0; i < bad.size(); i++) {
					//remove from disp tree
					allobjects.removeChild(bad.get(i));
				}
				for (int i = 0; i < power.size(); i++) {
					//remove from disp tree
					allobjects.removeChild(power.get(i));
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
		}		
		
		
		if (!levelLimbo) {
			if (level == 4) {
				/****************************************************************************************
				 ****************************************************************************************
				 *
				 * COMMENCING BOSS BATTLE
				 * 
				 ****************************************************************************************
				 ***************************************************************************************/
				
				//Make hil visible
				hillary.setVisible(true);
				
				/*************************** HILLARY'S RANDOM PATH ***************************/
				if(!TweenJuggler.getInstance().getTweens().contains(hillaryX)){
					/** X TWEEN **/
					int prevX = tempX;
					tempX = hillaryRandom.nextInt((490 - 250) + 1) + 250;
					hillaryX.animate("X", hillary.getPositionX(), (double)tempX, 125);
					TweenJuggler.getInstance().add(hillaryX);
					
					/** DEBUGGER MESSAGES **/
					System.out.println("Hillary is travelling from " + Integer.toString(prevX) + " to " 
							+ Integer.toString(tempX));
				}
				
				
				/*************************** GENERATE FIREBALLS RANDOMLY ***************************/
				//FIREBALL OBJECTS
				if (frameCounterFB == currentTimerFB) {
					//generate new fireball object
					fireball.add(new Sprite("fireball" + fireballCounter, "BoxBad.jpg"));
					//set fireball to hil's position
					fireball.get(fireball.size() - 1).setPosition(hillary.getPositionX(), hillary.getPositionY());
					//turn on physics for this object
					fireball.get(fireball.size() - 1).setPhysics(true);
					//generate an object with a velocity of currentVY
					fireball.get(fireball.size() - 1).setVY(fireballVY);
					//add to display tree
					allobjects.addChild(fireball.get(fireball.size() - 1));
					
					//increment fireballCounter (kind of like an item ID)
					fireballCounter++;
					//a fireball object will generate between 0 to 180 frames
					currentTimerFB = randomNum.nextInt(180);
					frameCounterFB = 0;
				} else {
					frameCounterFB++;
				}
				
				/*************************** SHOOTING PHYSICS OF BULLET ***************************/
				/*************************** AND COLLISION OF BULLET ***************************/
				//only need to check for collisions on bullet past y <= 50

				for (int i = 0; i < bullet.size(); i++) {
					bullet.get(i).setPositionY(bullet.get(i).getPositionY() - bullet.get(i).getVY());
					if (bullet.get(i).getPositionY() <= 50) {
						//CHECK FOR COLLISIONS
						if(bullet.get(i).collidesWith(hillary)){
							hilHealthVal -= 10;
							allobjects.removeChild(bullet.get(i));
							bullet.remove(i);
							i--;
						} else if (bullet.get(i).getPositionY() <= 0) { //else bullet went up off screen
							allobjects.removeChild(bullet.get(i));
							bullet.remove(i);
							i--;
						}	
					} 		
				}
				
				/*************************** DROPPING PHYSICS OF FIREBALL ***************************/
				/*************************** AND COLLISION OF FIREBALL ***************************/
				//only need to check for collisions on objects past y = 450
				for (int i = 0; i < fireball.size(); i++) {
					fireball.get(i).setPositionY(fireball.get(i).getPositionY() + fireball.get(i).getVY());
					if (fireball.get(i).getPositionY() >= 450) {
						//CHECK FOR COLLISIONS
						if(fireball.get(i).collidesWith(trump)){
							healthVal-=10;
							allobjects.removeChild(fireball.get(i));
							fireball.remove(i);
							i--;
						} else if (fireball.get(i).getPositionY() >= 640) {
							allobjects.removeChild(fireball.get(i));
							fireball.remove(i);
							i--;
						}				
					} 		
				}
				
				
				
			} else if (level < 4){
				
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
					allobjects.addChild(good.get(good.size() - 1));
					
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
					allobjects.addChild(bad.get(bad.size() - 1));
					
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
						power.add(new Sprite("powerKiss" + powerCounter, "BoxKiss.png"));
					} else if (powerUpNum == 1) {
						power.add(new Sprite("powerMeatloaf" + powerCounter, "BoxMeatloaf.png"));
					} else if (powerUpNum == 2) {
						power.add(new Sprite("powerTacoSalad" + powerCounter, "BoxTacoSalad.png"));
					}
					
					//generate a random x position between 200 and 600 (including bounds)
					power.get(power.size() - 1).setPosition(randomNum.nextInt(560 - 200 + 1) + 200, 0);
					//turn on physics for this object
					power.get(power.size() - 1).setPhysics(true);
					//make PU fall down at currentVY
					power.get(power.size() - 1).setVY(currentVY);
					//add to display tree
					allobjects.addChild(power.get(power.size() - 1));
					
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
				//only need to check for collisions on objects past y = 450
				
				//GOOD OBJECTS
				for (int i = 0; i < good.size(); i++) {
					good.get(i).setPositionY(good.get(i).getPositionY() + good.get(i).getVY());
					if (good.get(i).getPositionY() >= 450) {
						//CHECK FOR COLLISIONS
						if(good.get(i).collidesWith(trump)){
							pointVal+=5;
							allobjects.removeChild(good.get(i));
							good.remove(i);
							i--;
						} else if (good.get(i).getPositionY() >= 640) {
							allobjects.removeChild(good.get(i));
							good.remove(i);
							i--;
						}
					} 		
				}
				
				//BAD OBJECTS
				for (int i = 0; i < bad.size(); i++) {
					bad.get(i).setPositionY(bad.get(i).getPositionY() + bad.get(i).getVY());
					if (bad.get(i).getPositionY() >= 450) {
						//CHECK FOR COLLISIONS
						if(bad.get(i).collidesWith(trump) && !invulnerable){
							healthVal-=10;
							allobjects.removeChild(bad.get(i));
							bad.remove(i);
							i--;
						} else if (bad.get(i).getPositionY() >= 640) {
							allobjects.removeChild(bad.get(i));
							bad.remove(i);
							i--;
						}
					} 		
				}
				
				//POWER UP OBJECTS
				for (int i = 0; i < power.size(); i++) {
					power.get(i).setPositionY(power.get(i).getPositionY() + power.get(i).getVY());
					if (power.get(i).getPositionY() >= 450) {
						//CHECK FOR COLLISIONS
						if(power.get(i).collidesWith(trump)){
							if(power.get(i).getId().contains("Kiss")){
								System.out.println("Trump was kissed by Ivanka");
								if(!slowDown){
									slowDown = true;
								}
								allobjects.removeChild(power.get(i));
								power.remove(i);
								i--;
							} else if(power.get(i).getId().contains("Meatloaf")){
								System.out.println("Trump ate meatloaf with Chris Christie");
								if(healthVal<100){
									healthVal+=10;
								}
								allobjects.removeChild(power.get(i));
								power.remove(i);
								i--;
							} else if(power.get(i).getId().contains("TacoSalad")){
								System.out.println("Trump ate a Taco Salad and became less racist");
								if(!invulnerable){
									invulnerable = true;
								}
								allobjects.removeChild(power.get(i));
								power.remove(i);
								i--;
							}
						} else if (power.get(i).getPositionY() >= 640) {
							allobjects.removeChild(power.get(i));
							power.remove(i);
							i--;
						}	
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
					levelMessage = "A wild Hillary appeared! | Trump gathered " + pointVal/10 + " bullets";
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
		
		if(pressedKeys.size() == 0 || !isWalking) {
			trump.setPause(true);
		} else {
			trump.setPause(false);
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
		
		g.drawRect(0, 0, 200, 700);
		g.drawRect(200, 0, 400, 700);

		String health = "Health: " + healthVal;


		if (level < 4) {
			String points = "Points: " + pointVal;
			String time = "Time Remaining: " + Math.floor(timeVal/60);
			
			if(invulnerable){
				invul = "Invulnerable for: " + Math.floor(invulnerableTimer/60);
			} else {
				invul = "";
			}
			
			if(slowDown){
				sd = "Slowing down for: " + Math.floor(slowDownTimer/60);
			} else {
				sd = "";
			}
			
			g.drawString("Level: " + level, 15, 20);
			g.drawString(time, 15, 40);
			g.drawString(health, 15, 60);
			g.drawString(points, 15, 80);
			g.drawString(invul, 15, 100);
			g.drawString(sd, 15, 120);			
		} else if (level == 4) {
			String bullets = "Bullets left: " + numBullets;
			String hilHealth = "Hillary's Health: " + hilHealthVal;
			
			g.drawString(health, 15, 20);
			g.drawString(bullets, 15, 40);
			g.drawString(hilHealth, 15, 60);
		}
		
		if (healthVal <= 0) {
			g.drawString("You Died", 350, 200);
			this.stop();
		}
		
		if (level >= 4) {
			if (hilHealthVal <= 0) {
				g.drawString("You defeated democracy!", 350, 220);
				this.stop();
			} else if (numBullets <= 0 && bullet.isEmpty()) {
				g.drawString("You ran out of bullets.", 350, 220);
				this.stop();
			}			
		}
		
		if (levelLimbo) {
			g.drawString(levelMessage, 250, 200);
		}
		
		
		/* Same, just check for null in case a frame gets thrown in before trump is initialized */
		if(trump != null) trump.draw(g);
	}

	/**
	 * Quick main class that simply creates an instance of our game and starts the timer
	 * that calls update() and draw() every frame
	 * */
	public static void main(String[] args) {
		Beta game = new Beta();
		game.start();
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		super.keyReleased(e);
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			if (this.isRunning()) {
				this.pause();
			} else {
				this.start();
			}
			
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			
			//can only use space in boss fight
			if (level == 4 && !levelLimbo) {
				//have bullets left
				if (numBullets > 0) {
					numBullets--;
					/*************************** GENERATE A BULLET ***************************/
					//generate new bullet object
					bullet.add(new Sprite("bullet" + bulletCounter, "BoxBullet.jpg"));
					//set bullet position to trump's position
					bullet.get(bullet.size() - 1).setPosition(trump.getPositionX(), trump.getPositionY());
					//turn on physics for this object
					bullet.get(bullet.size() - 1).setPhysics(true);
					//set bullet's velocity to the bulletVY
					bullet.get(bullet.size() - 1).setVY(bulletVY);
					//add to display tree
					allobjects.addChild(bullet.get(bullet.size() - 1));
					
					//increment bulletCounter (kind of like an item ID)
					bulletCounter++;					
				}
			}
			
		}
	}
}
