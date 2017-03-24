package edu.virginia.lab1test;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import edu.virginia.engine.display.DisplayObjectContainer;
import edu.virginia.engine.display.Game;

/**
 * Example game that utilizes our engine. We can create a simple prototype game with just a couple lines of code
 * although, for now, it won't be a very fun game :)
 * */
public class SolarSystem extends Game {
	
	/*** count for ovals ***/
	int count = 0;

	/* Create a sprite object for our game.*/
	DisplayObjectContainer sun = new DisplayObjectContainer("Sun", "Sun.png");
	DisplayObjectContainer mercury = new DisplayObjectContainer("mercury", "Mercury.png");
	DisplayObjectContainer venus = new DisplayObjectContainer("Venus", "Venus.png");
	DisplayObjectContainer earth = new DisplayObjectContainer("Earth", "Earth.png");
	DisplayObjectContainer moon = new DisplayObjectContainer("Moon", "Moon.png");
	DisplayObjectContainer mars = new DisplayObjectContainer("Mars", "Mars.png");
	DisplayObjectContainer jupiter = new DisplayObjectContainer("Jupiter", "Jupiter.png");
	DisplayObjectContainer jmoon1 = new DisplayObjectContainer("Jmoon1", "Moon.png");
	DisplayObjectContainer jmoon2 = new DisplayObjectContainer("Jmoon2", "Moon.png");
	DisplayObjectContainer jmoon3 = new DisplayObjectContainer("Jmoon3", "Moon.png");
	DisplayObjectContainer saturn = new DisplayObjectContainer("Saturn", "Saturn.png");
	DisplayObjectContainer uranus = new DisplayObjectContainer("Uranus", "Uranus.png");
	DisplayObjectContainer neptune = new DisplayObjectContainer("Neptune", "Neptune.png");
	

	
	/**
	 * Constructor. See constructor in Game.java for details on the parameters given
	 * */
	public SolarSystem() {
		super("Solar System", 800, 500);
		
		/*** add suns ***/
		this.addChild(sun);
		
		/*** add planets to their suns ***/
		sun.addChild(mercury);
		sun.addChild(venus);
		sun.addChild(earth);
		sun.addChild(mars);
		sun.addChild(jupiter);
		sun.addChild(saturn);
		sun.addChild(uranus);
		sun.addChild(neptune);
		
		earth.addChild(moon);
		jupiter.addChild(jmoon1);
		jupiter.addChild(jmoon2);
		jupiter.addChild(jmoon3);
		
		/*** set suns locations ***/
		sun.setPivotPoint(75, 75);
		sun.setPosition(400, 250);
		
		/*** set planets locations ***/
		mercury.setPivotPoint(250, 20);
		mercury.setPosition(75, 75);
		venus.setPivotPoint(350, 47);
		venus.setPosition(75, 75);
		earth.setPivotPoint(475, 50);
		earth.setPosition(75, 75);
		
		moon.setPivotPoint(125, 25);
		moon.setPosition(50, 50);
		
		mars.setPivotPoint(500, 27);
		mars.setPosition(75, 75);
		jupiter.setPivotPoint(825, 70);
		jupiter.setPosition(75, 75);
		
		jmoon1.setPivotPoint(150, 25);
		jmoon1.setPosition(70, 70);
		jmoon2.setPivotPoint(200, 25);
		jmoon2.setPosition(70, 70);
		jmoon3.setPivotPoint(250, 25);
		jmoon3.setPosition(70, 70);
		
		saturn.setPivotPoint(1250, 65);
		saturn.setPosition(75, 75);
		uranus.setPivotPoint(1450, 148);
		uranus.setPosition(75, 75);
		neptune.setPivotPoint(1775, 60);
		neptune.setPosition(75, 75);
		
		/*** scale down solar system bc this shit is big ***/
		sun.setScaleX(0.5);
		sun.setScaleY(0.5);
	}
	
	/**
	 * Engine will automatically call this update method once per frame and pass to us
	 * the set of keys (as strings) that are currently being pressed down
	 * */
	@Override
	public void update(ArrayList<String> pressedKeys){
		super.update(pressedKeys);
		System.out.println(pressedKeys);
		count++;
		
		/*** alter planet distance for ellipse ***/
		mercury.setPivotPoint(250*Math.cos(count/10.0), 125*Math.sin(count/10.0));
		venus.setPivotPoint(350*Math.cos(count/20.0), 250*Math.sin(count/20.0));
		earth.setPivotPoint(475*Math.cos(count/30.0), 350*Math.sin(count/30.0));
		moon.setPivotPoint(125*Math.cos(count/10.0), 100*Math.sin(count/10.0));
		
		mars.setPivotPoint(500*Math.cos(count/35.0), 475*Math.sin(count/35.0));
		jupiter.setPivotPoint(825*Math.cos(count/40.0), 700*Math.sin(count/40.0));
		jmoon1.setPivotPoint(150*Math.cos(count/10.0), 125*Math.sin(count/10.0));
		jmoon2.setPivotPoint(200*Math.cos(count/20.0), 175*Math.sin(count/20.0));
		jmoon3.setPivotPoint(250*Math.cos(count/30.0), 225*Math.sin(count/30.0));

		saturn.setPivotPoint(1250*Math.cos(count/45.0), 1125*Math.sin(count/45.0));
		uranus.setPivotPoint(1450*Math.cos(count/50.0), 1325*Math.sin(count/50.0));
		neptune.setPivotPoint(1775*Math.cos(count/60.0), 1650*Math.sin(count/60.0));
		
		double xPos = sun.getPositionX();
		double yPos = sun.getPositionY();

		if(pressedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_UP))) {
			yPos--;
		}
		if(pressedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_DOWN))) {
			yPos++;
		}
		if(pressedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_LEFT))) {
			xPos--;
		}
		if(pressedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_RIGHT))) {
			xPos++;
		}
		if(pressedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_Q))) {
			sun.setScaleX(sun.getScaleX() - 0.02);
			sun.setScaleY(sun.getScaleY() - 0.02);
			if (sun.getScaleX() <= 0.1) {
				sun.setScaleX(0.1);
				sun.setScaleY(0.1);
			}
		}
		if(pressedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_W))) {
			sun.setScaleX(sun.getScaleX() + 0.02);
			sun.setScaleY(sun.getScaleY() + 0.02);
			if (sun.getScaleX() >= 1.25) {
				sun.setScaleX(1.25);
				sun.setScaleY(1.25);
			}
		}
		if(pressedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_A))) {
			sun.setRotation(sun.getRotation() - 0.1);
		}
		if(pressedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_S))) {
			sun.setRotation(sun.getRotation() + 0.1);
		}
		
		sun.setPosition(xPos, yPos);
		
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
		SolarSystem game = new SolarSystem();
		game.start();

	}
}
