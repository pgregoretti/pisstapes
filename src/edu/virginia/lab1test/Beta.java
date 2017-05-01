package edu.virginia.lab1test;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import edu.virginia.engine.display.AnimatedSprite;
import edu.virginia.engine.display.DisplayObjectContainer;
import edu.virginia.engine.display.Game;
import edu.virginia.engine.display.Sprite;
import edu.virginia.engine.display.Tween;
import edu.virginia.engine.display.TweenEvent;
import edu.virginia.engine.display.TweenJuggler;
import edu.virginia.engine.util.SoundManager;


public class Beta extends Game {
	
	//create the font to use. Specify the size!
	Font myFontSidebar;
	Font myFontLevel;
	Font myFontTimer;
	Font myFontPU;
	Font myFontCounter;
	
	boolean start = true;
	boolean insidestart = false;
	boolean start2 = true;
	boolean pause = true;
	boolean gameRestart = false;
	boolean gameWin = false;
	boolean gameLose = false;

//	Sprite trump = new Sprite("trump", "BoxTrump.jpg");
	AnimatedSprite trump = new AnimatedSprite("Trump", "trump-spritesheet.png", 94, 150, 2, 8, 0, 7);

	boolean isWalking = false;
	static SoundManager sound = new SoundManager();
	
	
	/*************************** BACKGROUND AND SCREEN CONTAINERS/SCREEN SPRITES ***************************/
	DisplayObjectContainer background = new DisplayObjectContainer("background");
	DisplayObjectContainer healthContainer = new DisplayObjectContainer("healthContainer");
	DisplayObjectContainer allobjects = new DisplayObjectContainer("allobjects");
	DisplayObjectContainer person = new DisplayObjectContainer("person");
	DisplayObjectContainer screen = new DisplayObjectContainer("screen");
	
	Sprite sidebar = new Sprite("Sidebar", "sidebar.png");
	Sprite screenstart = new Sprite("ScreenStart", "screenstart.png");
	Sprite screeninstructions = new Sprite("ScreenInstructions", "screeninstructions.png");
	Sprite screenpause = new Sprite("ScreenPause", "screenpause.png");
	Sprite screennext = new Sprite("ScreenNext", "screennext.png");
	Sprite screenboss = new Sprite("ScreenBoss", "screenboss.png");
	Sprite screenwin = new Sprite("ScreenWin", "screenwin.png");
	Sprite screenlose = new Sprite("ScreenLose", "screenlose.png");
	
//	Sprite slow = new Sprite("kiss", "kiss.png");
	Sprite inv = new Sprite("tacosalad", "tacosalad.png");
	
	Sprite heartTween = new Sprite("heartTween", "heart.png");
	Tween heartTweenScaleX = new Tween(heartTween);
	Tween heartTweenScaleY = new Tween(heartTween);
	Tween heartTweenX = new Tween(heartTween);
	Tween heartTweenY = new Tween(heartTween);
	
	Sprite kissTween = new Sprite("kissTween", "kiss.png");
	Tween kissTweenScaleX = new Tween(kissTween);
	Tween kissTweenScaleY = new Tween(kissTween);
	Tween kissTweenY = new Tween(kissTween);
	
	
	String[] bgImg = {"", "backgroundlevel1.png", "backgroundlevel2.png", "backgroundlevel3.png", "backgroundlevel4.png"};
	Sprite levelBackground = new Sprite("LevelBackground");
	Sprite filter = new Sprite("filter", "filter.png");
	
	/*************************** BASIC LEVEL VARIABLES ***************************/

	
	String[] goodImg = {"maga.png", "piss.png", "babybottle.png"};
	String[] badImg = {"imwithher.png", "constitution.png", "holywater.png"};
	
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
	
//	Sprite hillary = new Sprite("hillary" , "BoxHillary.jpg");
	AnimatedSprite hillary = new AnimatedSprite("Hillary", "hillary-spritesheet.png", 88, 150, 2, 8, 0, 7);
	Tween hillaryX = new Tween(hillary);

	ArrayList<Sprite> bullet = new ArrayList<Sprite>();
	int bulletCounter = 0;
	
	ArrayList<Sprite> fireball = new ArrayList<Sprite>();
	int fireballCounter = 0;
	int frameCounterFB = 0;
	int currentTimerFB = 0;
	
	int hilHealthVal = 10;
	int numBullets = 0;
	int bulletVY = 5;
	int fireballVY = 15;
	
	int bulletCount = 0;
	
	
	/*************************** LEVEL SWITCHING VARIABLES ***************************/
	int level = 1;	
	
	//first entry in the array is a buffer because level goes from 1-4 (not 0-4)
	//randomNum = rand.nextInt((max - min) + 1) + min;
	int[] goodGeneration = {0, 150, 200, 250};
	int[] goodGenerationMin = {0, 0, 60, 75};
	//0 to 90
	//30 to 120
	//60 to 150
	
	int[] badGeneration = {0, 300, 200, 150};
	int[] badGenerationMin = {0, 120, 40, 20};
	//60 to 180
	//30 to 150
	//0 to 120
	
	int[] powerGeneration = {0, 300, 400, 500};
	int[] powerGenerationMin = {0, 200, 350, 350};
	//120 to 240
	//60 to 180
	//0 to 120
	
	int currentVY = 1; 
	int oldVY = 1; //for the Kisses PU
	int[] vThresh = {0, 240, 210, 180}; //will get faster every x amount of frames;
	int[] vCap = {0, 10, 15, 20};
	int vCounter = 0; //to count which frame we're on
	
	boolean invulnerable = false;
	boolean invulnerableTween = false;
	int invulnerableTimer = 300;
	int invulnerableTimerMax = 300; //this is really dumb but trust me it will make changing values easier
	boolean slowDown = false;
	boolean slowDownTween = false;
	int slowDownTimer = 300;
	int slowDownTimerMax = 300; //this is really dumb but trust me it will make changing values easier
	boolean meatLoaf = false;
	

	String points = "";
	String time = "";
	String levelString = "";
	String bullets = "";
	String hilHealth = "";
	int invulCount = 60;
	int sdCount = 60;
	int meatCount = 60;
	int maxmeatCount = 60;
	int maxinvulCount = 60;
	int maxsdCount = 60;
	String invul = ""; //invulnerable string
	String sd = ""; //slow down string
	String meat = ""; //meat string
	String levelMessage = "";
	String levelMessage2 = "";
	
	String countdown = "";
	String countUp = "";
	
	boolean levelLimbo = false;
	int limboTimer = 0;
	
	//the least efficient way to do this but whatever
	Sprite health1 = new Sprite("Health1", "heart.png");
	Sprite health2 = new Sprite("Health2", "heart.png");
	Sprite health3 = new Sprite("Health3", "heart.png");
	Sprite health4 = new Sprite("Health4", "heart.png");
	Sprite health5 = new Sprite("Health5", "heart.png");
	Sprite health6 = new Sprite("Health6", "heart.png");
	Sprite health7 = new Sprite("Health7", "heart.png");
	Sprite health8 = new Sprite("Health8", "heart.png");
	Sprite health9 = new Sprite("Health9", "heart.png");
	Sprite health10 = new Sprite("Health10", "heart.png");
	Sprite[] health = {health1, health1, health2, health3, health4, health5, health6, health7, health8, health9, health10};
	int healthVal = 10;
	int pointVal = 0;
//	int timeVal = 3600;
//	int timeValMax = 3600;
	int timeVal = 1800;
	int timeValMax = 1800;
	//1 min per level - 60 fps * 60 sec = 3600
	
	
	//Make Hillary's health!
	Sprite hilhealth1 = new Sprite("hilHealth1", "hilhealth.png");
	Sprite hilhealth2 = new Sprite("hilHealth2", "hilhealth.png");
	Sprite hilhealth3 = new Sprite("hilHealth3", "hilhealth.png");
	Sprite hilhealth4 = new Sprite("hilHealth4", "hilhealth.png");
	Sprite hilhealth5 = new Sprite("hilHealth5", "hilhealth.png");
	Sprite hilhealth6 = new Sprite("hilHealth6", "hilhealth.png");
	Sprite hilhealth7 = new Sprite("hilHealth7", "hilhealth.png");
	Sprite hilhealth8 = new Sprite("hilHealth8", "hilhealth.png");
	Sprite hilhealth9 = new Sprite("hilHealth9", "hilhealth.png");
	Sprite hilhealth10 = new Sprite("hilHealth10", "hilhealth.png" );
	Sprite[] hilHealthArray = {hilhealth1, hilhealth1, hilhealth2, hilhealth3, hilhealth4, hilhealth5, hilhealth6, hilhealth7, hilhealth8, hilhealth9, hilhealth10};
	
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
		
		/****** GAME FONTS ******/
		try {
		    //create the font to use. Specify the size!
		    myFontSidebar = Font.createFont(Font.TRUETYPE_FONT, new File("resources/joystix_monospace.ttf")).deriveFont(12f);
		    myFontLevel = Font.createFont(Font.TRUETYPE_FONT, new File("resources/joystix_monospace.ttf")).deriveFont(30f);
		    myFontTimer = Font.createFont(Font.TRUETYPE_FONT, new File("resources/joystix_monospace.ttf")).deriveFont(110f);
		    myFontCounter = Font.createFont(Font.TRUETYPE_FONT, new File("resources/joystix_monospace.ttf")).deriveFont(125f);

		    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		    //register the font
		    ge.registerFont(myFontSidebar);
		    ge.registerFont(myFontLevel);
		    ge.registerFont(myFontTimer);
		    ge.registerFont(myFontCounter);
		} catch (IOException e) {
		    e.printStackTrace();
		} catch(FontFormatException e) {
		    e.printStackTrace();
		}
//		yourSwingComponent.setFont(customFont);

		
		
		/******* ADD SPRITES *******/
		this.addChild(background);
		this.addChild(healthContainer);
		this.addChild(allobjects);
		this.addChild(person);
		this.addChild(screen);
		background.addChild(sidebar);
		background.addChild(levelBackground);
		for (int i = 1; i < health.length; i++) {
			healthContainer.addChild(health[i]);
		}
		
		for(int i = 1; i< hilHealthArray.length; i++){
			sidebar.addChild(hilHealthArray[i]);
			hilHealthArray[i].setVisible(false);
		}
		person.addChild(trump);
		person.addChild(hillary);
		person.addChild(heartTween);
		heartTween.setVisible(false);
		person.addChild(kissTween);
		kissTween.setVisible(false);
		
		
		background.addChild(inv);
//		background.addChild(slow);
		inv.setPosition(55, 275);
		inv.setScaleX(2);
		inv.setScaleY(2);
//		slow.setScaleX(2);
//		slow.setScaleY(2);
//		slow.setPosition(30, 330);
		inv.setAlpha(0.2f);
//		slow.setAlpha(0.2f);
		
		/******* ASSIGN ANIMATIONS *******/
		trump.setAnimation("right", 0, 7);
		trump.setAnimation("left", 8, 15);
		hillary.setAnimation("right", 0, 7);
		hillary.setAnimation("left", 8, 15);
		
		/******* POSITION SPRITES *******/
		trump.setPosition(350, 530);
		levelBackground.setPosition(200, 0);
		filter.setPosition(200, 0);

		//set hillary off screen
		hillary.setPosition(610, 50);
		
		health1.setPosition(10, 45);
		health2.setPosition(47, 45);
		health3.setPosition(84, 45);
		health4.setPosition(121, 45);
		health5.setPosition(158, 45);
		
		health6.setPosition(10, 75);
		health7.setPosition(47, 75);
		health8.setPosition(84, 75);
		health9.setPosition(121, 75);
		health10.setPosition(158, 75);
		
		
		hilhealth1.setPosition(10, 300);
		hilhealth2.setPosition(47, 300);
		hilhealth3.setPosition(84, 300);
		hilhealth4.setPosition(121, 300);
		hilhealth5.setPosition(158, 300);
		
		hilhealth6.setPosition(10, 330);
		hilhealth7.setPosition(47, 330);
		hilhealth8.setPosition(84, 330);
		hilhealth9.setPosition(121, 330);
		hilhealth10.setPosition(158, 330);
		
		
		/******* SETUP TWEENS *******/
		//hillary goes from x = 250 to 490
		tempX = hillaryRandom.nextInt((490 - 250) + 1) + 250;
		System.out.println("Hillary is travelling to " + Integer.toString(tempX));
		hillaryX.animate("X", hillary.getPositionX(), (double)tempX, 90);
		
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
		if (start) {
			if (!sound.contains("march")) {	
				sound.LoadMusic("march", "march.wav");
			}
			insidestart = true;
			screen.removeAll();
			screen.addChild(screenstart);
			levelBackground.setImage(levelBackground.readImage(bgImg[level]));
			this.pause();
		} else if (start2) {
			screen.removeAll();
			screen.addChild(screeninstructions);
			this.pause();
		} else if (pause) {
			if (gameWin) {
				screen.addChild(screenwin);
			} else if (gameLose) {
				sound.LoadSoundEffect("gameover", "gameover.wav");
				sound.LoadSoundEffect("losers", "losers.wav");
				sound.PlaySoundEffect("gameover");
				sound.PlaySoundEffect("losers");
				screen.addChild(screenlose);
			} else {
				screen.addChild(screenpause);
			}
			if (gameRestart) {
				if (sound.contains("battlesong")) {
					sound.StopMusic("battlesong");
				}
				if (sound.contains("march")) {
					sound.StopMusic("march");
				}
				sound.LoadMusic("march", "march.wav");
				sound.PlayMusic("march");
				
				//reset game
				start = true;
				insidestart = false;
				start2 = true;
				pause = true;
				gameRestart = false;
				gameWin = false;
				gameLose = false;

				//reset level
				level = 1;
				
				//reset background
				levelBackground.setImage(levelBackground.readImage(bgImg[level]));

				inv.setVisible(true);
//				slow.setVisible(true);
				inv.setAlpha(0.2f);
//				slow.setAlpha(0.2f);
				
				//reset hillary

				//set hillary off screen
				TweenJuggler.getInstance().clear();
				hillary.setPosition(610, 50);
				hillary.setVisible(false);
				
				
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
				for (int i = 0; i < bullet.size(); i++) {
					//remove from disp tree
					allobjects.removeChild(bullet.get(i));
				}
				for (int i = 0; i < fireball.size(); i++) {
					//remove from disp tree
					allobjects.removeChild(fireball.get(i));
				}
				
				//clear arraylists
				good.clear();
				bad.clear();
				power.clear();
				bullet.clear();
				fireball.clear();
				
				//get out of limbo
				levelLimbo = false;
				//reset limbo timer
				limboTimer = 0;
				//reset level timer
				timeVal = timeValMax;
				

				trump.setAlpha((float) 1.0);
				
				//reset player health
				healthContainer.removeAll();
				for (int i = 1; i < health.length; i++) {
					healthContainer.addChild(health[i]);
				}
				healthVal = 10;
				//reset player points
				pointVal = 0;
				//reset object velocity
				vCounter = 0;
				currentVY = 1;
				oldVY = 1;
				//reset power up status
				invulnerable = false;
				invulnerableTween = false;
				invulnerableTimer = invulnerableTimerMax;
				slowDown = false;
				slowDownTween = false;
				slowDownTimer = slowDownTimerMax; 
				meatLoaf = false;
				
				invulCount = maxinvulCount;
				sdCount = maxsdCount;
				meatCount = maxmeatCount;
				
				invul = "";
				sd = "";
				meat = "";

				for(int i = 1; i< hilHealthArray.length; i++){
					sidebar.addChild(hilHealthArray[i]);
					hilHealthArray[i].setVisible(false);
				}
				hilHealthVal = 10;
				numBullets = 0;
				bulletCount = 0;

				countdown = "";
				countUp = "";
			}
			this.pause();
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

				TweenJuggler.getInstance().clear();
				
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
				
				if (numBullets > 0) {
					if (level != 4) {
						sound.LoadSoundEffect("levelend", "levelend.wav");
						sound.PlaySoundEffect("levelend");	
					} else {
						sound.StopMusic("march");
						sound.LoadSoundEffect("hillaryintro", "hillaryintro.wav");
						sound.PlaySoundEffect("hillaryintro");	
						if (!sound.contains("battlesong")) {	
							sound.LoadMusic("battlesong", "battlesong.wav");
						}
					}
				}
				
				if (level == 4) {
					screen.addChild(screenboss);
				} else {
					screen.addChild(screennext);
				}
				
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
				for(int i = 0; i < hilHealthArray.length; i++){
					hilHealthArray[i].setVisible(true);
				}
				
				
				/*************************** HILLARY'S RANDOM PATH ***************************/
				if(!TweenJuggler.getInstance().getTweens().contains(hillaryX)){
					/** X TWEEN **/
					int prevX = tempX;
					tempX = hillaryRandom.nextInt((490 - 250) + 1) + 250;
					hillaryX.animate("X", hillary.getPositionX(), (double)tempX, 90);
					if (tempX - hillary.getPositionX() <= 0 && hillary.getAnimation() != "left") {
						System.out.println("hillary moving left");
						hillary.animate("left");
					} else if (tempX - hillary.getPositionX() > 0 && hillary.getAnimation() != "right") {
						System.out.println("hillary moving right");
						hillary.animate("right");
					}
					TweenJuggler.getInstance().add(hillaryX);
					
					/** DEBUGGER MESSAGES **/
					System.out.println("Hillary is travelling from " + Integer.toString(prevX) + " to " 
							+ Integer.toString(tempX));
				}
				
				
				
				/*************************** GENERATE FIREBALLS RANDOMLY ***************************/
				//FIREBALL OBJECTS
				if (frameCounterFB == currentTimerFB) {
					//generate new fireball object
					fireball.add(
							new Sprite("fireball" + fireballCounter, "fireball.png"));
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
					currentTimerFB = randomNum.nextInt(120);
					frameCounterFB = 0;
				} else {
					frameCounterFB++;
				}
				
				/*************************** SHOOTING PHYSICS OF BULLET ***************************/
				/*************************** AND COLLISION OF BULLET ***************************/
				//only need to check for collisions on bullet past y <= 220

				for (int i = 0; i < bullet.size(); i++) {
					bullet.get(i).setPositionY(bullet.get(i).getPositionY() - bullet.get(i).getVY());
					if (bullet.get(i).getPositionY() <= 220) {
						//CHECK FOR COLLISIONS
						if(bullet.get(i).collidesWith(hillary)){
							sidebar.removeChild(hilHealthArray[hilHealthVal]);
							hilHealthVal--;
							//REMOVE HILLARY'S HEALTH HERE
							allobjects.removeChild(bullet.get(i));
							bullet.remove(i);
							i--;
							sound.LoadSoundEffect("wrong", "wrong.wav");
							sound.PlaySoundEffect("wrong");
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
							healthContainer.removeChild(health[healthVal]);
							healthVal--;
							allobjects.removeChild(fireball.get(i));
							fireball.remove(i);
							i--;
							sound.LoadSoundEffect("bullshit", "bullshit.wav");
							sound.PlaySoundEffect("bullshit");
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
					int goodNum = randomNum.nextInt(3);

					//generate new good object
					good.add(new Sprite("good" + goodCounter, goodImg[goodNum]));
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
					int badNum = randomNum.nextInt(3);
					//generate new bad object
					bad.add(new Sprite("bad" + badCounter, badImg[badNum]));
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
					boolean hasTaco = false;
					boolean hasKiss = false;
					boolean hasMeat = false;
					
					for (int i = 0; i < power.size(); i++) {
						if (power.get(i).getId().contains("powerKiss")) {
							hasKiss = true;
						} else if (power.get(i).getId().contains("powerMeatloaf")) {
							hasMeat = true;
						} else if (power.get(i).getId().contains("powerTacoSalad")) {
							hasTaco = true;
						}
					}
					
					boolean puGenerated = false;
					//generate new PU object
					if (powerUpNum == 0) {
						int cvy = 8;
						//kiss slows everything down
						if (level == 2) {
							cvy = 12;
						} else if (level == 3) {
							cvy = 16;
						}
						if(currentVY > cvy && !hasKiss){
							power.add(new Sprite("powerKiss" + powerCounter, "kiss.png"));
							puGenerated = true;
						}
					} else if (powerUpNum == 1 && !hasMeat) {
						//meatloaf is health so don't generate if health is full
						if(healthVal < 10){
							power.add(new Sprite("powerMeatloaf" + powerCounter, "meatloaf.png"));
							puGenerated = true;
						}
						//taco salad is invulnerability
					} else if (powerUpNum == 2 && !hasTaco) {
						power.add(new Sprite("powerTacoSalad" + powerCounter, "tacosalad.png"));
						puGenerated = true;
					}
					
					if (puGenerated) {
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
					}
					
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
							sound.LoadSoundEffect("bingbong", "bingbong.wav");
							sound.PlaySoundEffect("bingbong");
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
							healthContainer.removeChild(health[healthVal]);
							healthVal--;
							allobjects.removeChild(bad.get(i));
							bad.remove(i);
							i--;
							sound.LoadSoundEffect("oyaye", "oyaye.wav");
							sound.PlaySoundEffect("oyaye");
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
								slowDown = true;
								slowDownTween = true;
								sdCount = 0;
								person.addChild(heartTween);
//								slow.setAlpha(1);
								allobjects.removeChild(power.get(i));
								power.remove(i);
								i--;
								sound.LoadSoundEffect("ivanka", "heardofivanka.wav");
								sound.PlaySoundEffect("ivanka");
							} else if(power.get(i).getId().contains("Meatloaf")){
								System.out.println("Trump ate meatloaf with Chris Christie");
								meatLoaf = true;
								meatCount = 0;
								if(healthVal < 10){
									healthVal++;
									healthContainer.addChild(health[healthVal]);
								}
								
								//TWEENING EFFECT
								heartTween.setPivotPoint(13, 11);
								heartTween.setPosition(trump.getPositionX() + heartTween.getPivotPointX() + 30, trump.getPositionY() + heartTween.getPivotPointY());
								heartTween.setScaleX(0);
								heartTween.setScaleY(0);
								person.addChild(heartTween);

								heartTweenScaleX.animate("SCALE_X", 0.0, 2.3, 20);
								heartTweenScaleY.animate("SCALE_Y", 0.0, 2.3, 20);
								heartTweenX.animate("X", heartTween.getPositionX(), 95 + heartTween.getPivotPointX(), 15);
								heartTweenY.animate("Y", heartTween.getPositionY(), 78 + heartTween.getPivotPointY(), 15);
								
								allobjects.removeChild(power.get(i));
								power.remove(i);
								i--;
								sound.LoadSoundEffect("lovemylife", "lovemylife.wav");
								sound.PlaySoundEffect("lovemylife");
							} else if(power.get(i).getId().contains("TacoSalad")){
								System.out.println("Trump ate a Taco Salad and became less racist");
								invulnerable = true;
								invulnerableTween = true;
								invulCount = 0;
								inv.setAlpha(1);
								invulnerableTimer = 300;
								allobjects.removeChild(power.get(i));
								power.remove(i);
								i--;
								sound.LoadSoundEffect("mexicanpeople", "mexicanpeople.wav");
								sound.PlaySoundEffect("mexicanpeople");
							}
						} else if (power.get(i).getPositionY() >= 640) {
							allobjects.removeChild(power.get(i));
							power.remove(i);
							i--;
						}	
					} 		
				}
				
				/*************************** TWEENING ***************************/
				if (meatLoaf) {
					if (!heartTween.getVisible()) {
						heartTween.setVisible(true);
						TweenJuggler.getInstance().add(heartTweenScaleX);
						TweenJuggler.getInstance().add(heartTweenScaleY);
					} else {
						if(!TweenJuggler.getInstance().getTweens().contains(heartTweenScaleX) && !TweenJuggler.getInstance().getTweens().contains(heartTweenScaleX)){
							TweenJuggler.getInstance().add(heartTweenX);
							TweenJuggler.getInstance().add(heartTweenY);
							meatLoaf = false;
						}
					}
				} else {
					if(!TweenJuggler.getInstance().getTweens().contains(heartTweenX) && !TweenJuggler.getInstance().getTweens().contains(heartTweenY)){
						heartTween.setVisible(false);
					}
				}
				
				
				/*************************** INVULNERABLITY COUNTER ***************************/
				if(invulnerable && invulnerableTimer > 0){
					trump.setAlpha((float) 0.6);
					invulnerableTimer--;
				} else {
					trump.setAlpha(1);
					inv.setAlpha(0.2f);
					invulnerable = false;
					invulnerableTimer = 300;
				}
				
				
				/*************************** MAKE OBJECTS FALL FASTER EVERY 60 FRAMES ***************************/
				//capped at... vCap???
				//precision for timing of first speed boost will be off but who cares????
				/*
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
					slow.setAlpha(0.2f);
					currentVY = oldVY; //reset currentVY
					slowDown = false; //reset slowDown bool
					slowDownTimer = 300; //reset slowDown timer
				}
				*/
				
				if (slowDown) {
					currentVY = 3 * currentVY / 4;
//					slow.setAlpha(0.2f);
					slowDown = false;
				}
				
				vCounter++;
				if (vCounter >= vThresh[level] && currentVY < vCap[level]) {
					currentVY++;
					vCounter = 0;
				}
				
				
				/**********************
				 * 
				 * DEALING WITH STRING DISPLAYS
				 * 
				 **********************/
				
				//invulnerable
				if(invulCount != maxinvulCount){
					invulCount++;
					invul = "Invulnerable!";
				} else {
					invul = "";
				}
				//slowDown
				if(sdCount != maxsdCount){
					sdCount++;
					sd = "Slowing Down!";
				} else {
					sd = "";
				}
				//meat
				if(meatCount != maxmeatCount){
					meatCount++;
					meat = "+1!";
				} else {
					meat = "";
				}
		
				
			}
			
		} else {
			//END OF LEVEL
			
			limboTimer++;
			levelMessage = "" + level;
			//0 to 180 and then 180 to 360
//			if (limboTimer < 180 && limboTimer > 0) {
//				levelMessage = "End of Level | Final Score: " + pointVal;
//				levelMessage2 = "";
//			} else if (limboTimer < 360 && limboTimer > 180) {
//				if (level == 4) {
//					levelMessage = "A wild Hillary appeared!";
//					levelMessage2 = "ATrump gathered " + pointVal/10 + " bullets";
//				} else {
//					levelMessage = "Starting Level " + level;
//					levelMessage2 = "";
//				}
//				
//			} else
			if (limboTimer > 360) {
				if (level == 4) {
					screen.removeChild(screenboss);
//					screen.addChild(filter);
					inv.setVisible(false);
//					slow.setVisible(false);
				} else {
					screen.removeChild(screennext);
				}
				
				//add new bg screen
				levelBackground.setImage(levelBackground.readImage(bgImg[level]));
				
				//get out of limbo
				levelLimbo = false;
				//reset limbo timer
				limboTimer = 0;
				//reset level timer
				timeVal = timeValMax;
				

				trump.setAlpha((float) 1.0);
				
				//reset player health
				healthContainer.removeAll();
				for (int i = 1; i < health.length; i++) {
					healthContainer.addChild(health[i]);
				}
				healthVal = 10;
				//reset object velocity
				vCounter = 0;
				currentVY = 1;
				oldVY = 1;
				//reset power up status
				invulnerable = false;
				invulnerableTween = false;
				invulnerableTimer = invulnerableTimerMax;
				slowDown = false;
				slowDownTween = false;
				slowDownTimer = slowDownTimerMax; 
				meatLoaf = false;
				
				invulCount = maxinvulCount;
				sdCount = maxsdCount;
				meatCount = maxmeatCount;
				
				invul = "";
				sd = "";
				meat = "";
			}
		}
		
//		if(pressedKeys.size() == 0 || !isWalking) {
//			trump.setPause(true);
//		} else {
//			trump.setPause(false);
//		}
		
		//reset iswalking
		isWalking = false;	
		
		/************ VICTORY STATES/SCREEN STATES ************/
		if (healthVal <= 0) {
			gameLose = true;
			gameRestart = true;
			pause = true;
			sound.LoadSoundEffect("reality", "reality.wav");
			sound.PlaySoundEffect("reality");
		}
		
		if (level >= 4) {
			if (hilHealthVal <= 0) {
				gameWin = true;
				gameRestart = true;
				pause = true;
				sound.LoadSoundEffect("maga", "maga.wav");
				sound.PlaySoundEffect("maga");
			} else if (numBullets <= 0 && bullet.isEmpty()) {
				gameLose = true;
				gameRestart = true;
				pause = true;
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
		g.setColor(new Color(250,226,147));
		g.fillRect(0, 0, 200, 700);
		
		g.setFont(myFontSidebar);
		g.setColor(Color.BLACK);		


		if (level < 4) {
			//LEVEL
			levelString = "Level " + level;
			//FORMATTING TIMER INT
			if ((int)Math.floor(timeVal/60) < 10) {
				time = "0" + (int)Math.floor(timeVal/60);
			} else {
				time = "" + (int)Math.floor(timeVal/60);
			}
			//POINTS
			points = "Points: " + pointVal;
			//POWERUPS
//			g.drawString("Current PowerUps", 20, 290);
			g.setFont(myFontLevel);
			g.drawString(levelString, 10, 34);
			
			g.setFont(myFontTimer);
			g.setColor(Color.RED);
			g.drawString(time, 5, 195);
			
			g.setFont(myFontSidebar);
			g.setColor(Color.BLACK);
			g.drawString(points, 15, 235);			
		} else if (level == 4) {
			bullets = "Bullets left: " + numBullets;
			hilHealth = "";
			g.drawString("Hillary's Health", 15, 290);

			g.setFont(myFontLevel);
			g.drawString("FIGHT!!", 10, 34);
			
			g.setFont(myFontSidebar);
			g.setColor(Color.BLACK);
			g.drawString(bullets, 15, 200);
			g.drawString(hilHealth, 15, 220);
		}
		
		
		
		
		/* Same, just check for null in case a frame gets thrown in before trump is initialized */
//		if(trump != null) trump.draw(g);

		super.draw(g);
		
		if (level < 4 && !pause) {
			g.setFont(myFontLevel);
			g.setColor(Color.RED);
			g.drawString(invul, 240, 325);
			g.drawString(sd, 240, 325);
			g.drawString(meat, 365, 325);
		}
	
		
		if (levelLimbo) {
			if (level == 4) {
				if (pointVal < 10) {
					countdown = "00" + pointVal;
				} else if (pointVal < 100) {
					countdown = "0" + pointVal;				
				} else {
					countdown = "" + pointVal;				
				}

				if (bulletCount < 10) {
					countUp = "00" + bulletCount;
				} else if (bulletCount < 100) {
					countUp = "0" + bulletCount;				
				} else {
					countUp = "" + bulletCount;				
				}
				
				g.setFont(myFontCounter);
				g.setColor(new Color(224, 145, 5));
				if (pointVal > 0) {
					pointVal -= 10;
					if (pointVal < 0) {
						pointVal = 0;
					}
				}
				g.drawString(countdown, 143, 219);
				
				g.setColor(new Color(250, 226, 147));
				if (bulletCount < numBullets) {
					bulletCount++;
				}
				g.drawString(countUp, 143, 569);
			} else {
				g.setFont(myFontCounter);
				g.setColor(new Color(224, 145, 5));
				g.drawString(levelMessage, 250, 430);
			}
			
		}
		
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
			if (!levelLimbo) {
				if (this.isRunning()) {
					pause = true;
				} else {
					screen.removeAll();
					this.start();
					pause = false;
					if (insidestart && start) {
						start = false;
					} else if (insidestart && start2) {
						start2 = false;
					}
				}
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
					bullet.add(new Sprite("bullet" + bulletCounter, "bullet.png"));
					//set bullet position to trump's position (from middle of trump sprite)
					bullet.get(bullet.size() - 1).setPosition(trump.getPositionX() + 36, trump.getPositionY());
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
