package edu.virginia.lab1test;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import edu.virginia.engine.display.DisplayObjectContainer;
import edu.virginia.engine.display.Game;
import edu.virginia.engine.events.Event;
import edu.virginia.engine.events.EventDispatcher;
import edu.virginia.engine.util.SoundManager;

/**
 * Example game that utilizes our engine. We can create a simple prototype game with just a couple lines of code
 * although, for now, it won't be a very fun game :)
 * */
public class CoinPickUp extends Game {
	
	DisplayObjectContainer mario = new DisplayObjectContainer("MyMario", "Mario.png");
	DisplayObjectContainer coin = new DisplayObjectContainer("Coin", "coin.png");
	EventDispatcher coinDispatcher = new EventDispatcher();
	QuestManager myQuestManager = new QuestManager();
	static SoundManager sound = new SoundManager();
	
	/**
	 * Constructor. See constructor in Game.java for details on the parameters given
	 * */
	public CoinPickUp() {
		super("CoinPickUp", 800, 500);
		this.addChild(mario);
		this.addChild(coin);
		mario.setPosition(600, 300);
		coinDispatcher.addEventListener(myQuestManager, "coin");
		sound.LoadMusic("zelda", "zelda.wav");
		sound.LoadSoundEffect("rupee", "OOT_Get_Rupee.wav");
	}
	
	/**
	 * Engine will automatically call this update method once per frame and pass to us
	 * the set of keys (as strings) that are currently being pressed down
	 * */
	@Override
	public void update(ArrayList<String> pressedKeys){
		super.update(pressedKeys);
//		System.out.println(pressedKeys);


		System.out.println(mario.getVX());
		
		double xPos = mario.getPositionX();
		double yPos = mario.getPositionY();

		if (pressedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_UP))) {
			mario.setPhysics(true);
			yPos--;
		} else {
			mario.setPhysics(false);
		}
		if (pressedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_DOWN))) {
			yPos++;
		}
		if (pressedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_LEFT))) {
			mario.setVX(mario.getVX()-0.5);

			System.out.println(mario.getVX());
			xPos=xPos+mario.getVX();
			System.out.println(xPos);
		}
		if (pressedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_RIGHT))) {
			mario.setVX(mario.getVX()+0.5);
			xPos=xPos+mario.getVX();
		}
		
		if(!mario.hasPhysics()){
			yPos++;
		}
		
		if(yPos>480-mario.getUnscaledHeight()){
			yPos--;
		}
		mario.setPosition(xPos, yPos);
		
		if (xPos < coin.getUnscaledWidth() && xPos >= 0 && yPos < coin.getUnscaledHeight() && yPos>= 0 && this.contains(coin)) {
			//coin.setVisible(false);
			this.removeChild(coin);
			sound.PlaySoundEffect("rupee");
			coinDispatcher.dispatchEvent(new Event("coin", coinDispatcher));
		}
		
		/* Make sure mario is not null. Sometimes Swing can auto cause an extra frame to go before everything is initialized */
//		if(mario != null) mario.update(pressedKeys);
	}
	
	/**
	 * Engine automatically invokes draw() every frame as well. If we want to make sure mario gets drawn to
	 * the screen, we need to make sure to override this method and call mario's draw method.
	 * */
	@Override
	public void draw(Graphics g){
		super.draw(g);
		
		/* Same, just check for null in case a frame gets thrown in before Mario is initialized */
//		if(mario != null) mario.draw(g);
	}

	/**
	 * Quick main class that simply creates an instance of our game and starts the timer
	 * that calls update() and draw() every frame
	 * */
	public static void main(String[] args) {
		CoinPickUp game = new CoinPickUp();
		game.start();
		sound.PlayMusic("zelda");

	}
}
