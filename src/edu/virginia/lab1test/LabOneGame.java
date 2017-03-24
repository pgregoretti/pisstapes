package edu.virginia.lab1test;

import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import edu.virginia.engine.display.AnimatedSprite;
import edu.virginia.engine.display.Game;

/**
 * Example game that utilizes our engine. We can create a simple prototype game with just a couple lines of code
 * although, for now, it won't be a very fun game :)
 * */
public class LabOneGame extends Game implements MouseListener {

	/* Create a sprite object for our game. We'll use mario */
	AnimatedSprite mario = new AnimatedSprite("Mario", "link-spritesheet.png", 120, 127, 4, 10, 0);
	
	int[] hitX = new int[]{0, mario.getUnscaledWidth(), mario.getUnscaledWidth(), 0};
	int[] hitY = new int[]{0, 0, mario.getUnscaledHeight(), mario.getUnscaledHeight()};
	Polygon marioHitbox2 = new Polygon(hitX, hitY, 4);
	Rectangle marioHitbox = new Rectangle((int)mario.getPositionX(), (int)mario.getPositionY(), mario.getUnscaledWidth(), mario.getUnscaledHeight());
	
	int healthVal = 5;
	String health = "Health: " + healthVal;
	int clickX = 0;
	int clickY = 0;
	double time = 60;
	String timer = "Time Left: " + (int) time;
	String winner = "";
	int count = 0;
	boolean isWalking = false;
	
	
	/**
	 * Constructor. See constructor in Game.java for details on the parameters given
	 * */
	public LabOneGame() {
		super("Lab One Test Game", 500, 300);
		this.getMainFrame().addMouseListener(this);
		mario.setAnimation("forward", 0, 9);
		mario.setAnimation("backward", 20, 29);
	}
	
	/**
	 * Engine will automatically call this update method once per frame and pass to us
	 * the set of keys (as strings) that are currently being pressed down
	 * */
	@Override
	public void update(ArrayList<String> pressedKeys){
		super.update(pressedKeys);
		System.out.println(pressedKeys);
		double xPos = mario.getPositionX();
		double yPos = mario.getPositionY();
		double pivotX = mario.getPivotPointX();
		double pivotY  = mario.getPivotPointY();
		
		if (mario.getAlpha() == 0.0) {
			count++;
			if (count >= 180) {
				mario.setAlpha(1.0f);
				if (!mario.getVisible()) {
					mario.setVisible(true);
				}
				count = 0;
			}
		} else {
			count = 0;
		}
		

		if(pressedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_UP))) {
			yPos--;
			if (mario.getAnimation() != "backward")
				mario.animate("backward");
			isWalking = true;
			marioHitbox.translate(0, -1);
			marioHitbox2.reset();
			hitY[0] = hitY[1] = hitY[0] - 1;
			hitY[2] = hitY[3] = hitY[2] - 1;
		}
		if(pressedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_DOWN))) {
			yPos++;
			if (mario.getAnimation() != "forward")
				mario.animate("forward");
			isWalking = true;
			marioHitbox.translate(0, 1);
			marioHitbox2.reset();
			hitY[0] = hitY[1] = hitY[0] + 1;
			hitY[2] = hitY[3] = hitY[2] + 1;
		}
		if(pressedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_LEFT))) {
			xPos--;
			isWalking = true;
			marioHitbox.translate(-1, 0);
			marioHitbox2.reset();
			hitX[0] = hitX[3] = hitX[0] - 1;
			hitX[1] = hitX[2] = hitX[1] - 1;
		}
		if(pressedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_RIGHT))) {
			xPos++;
			isWalking = true;
			marioHitbox.translate(1, 0);
			marioHitbox2.reset();
			hitX[0] = hitX[3] = hitX[0] + 1;
			hitX[1] = hitX[2] = hitX[1] + 1;
		}
		/**** COUNTERCLOCKWISE ****/
		if(pressedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_Q))) {
			mario.setRotation(mario.getRotation() - 0.1);
			
			double[] src = new double[]{
			        hitX[0], hitY[0],
			        hitX[1], hitY[1],
			        hitX[2], hitY[2],
			        hitX[3], hitY[3]};
			double[] dst = new double[8];
			AffineTransform t = AffineTransform.getRotateInstance(mario.getRotation(), mario.getPositionX(), mario.getPositionY());
			t.transform(src, 0, dst, 0, 4);
			int[] xTrans = new int[]{(int)dst[0], (int)dst[2],(int)dst[4],(int)dst[6]};
			int[] yTrans = new int[]{(int)dst[1], (int)dst[3],(int)dst[5],(int)dst[7]};
			marioHitbox2.reset();
			for (int i = 0; i < 4; i++) {
				marioHitbox2.addPoint(xTrans[i], yTrans[i]);
			}
		}
		/**** CLOCKWISE ****/
		if(pressedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_W))) {
			mario.setRotation(mario.getRotation() + 0.1);
			
			
			double[] src = new double[]{
			        hitX[0], hitY[0],
			        hitX[1], hitY[1],
			        hitX[2], hitY[2],
			        hitX[3], hitY[3]};
			double[] dst = new double[8];
			AffineTransform t = AffineTransform.getRotateInstance(mario.getRotation(), mario.getPositionX(), mario.getPositionY());
			t.transform(src, 0, dst, 0, 4);
			int[] xTrans = new int[]{(int)dst[0], (int)dst[2],(int)dst[4],(int)dst[6]};
			int[] yTrans = new int[]{(int)dst[1], (int)dst[3],(int)dst[5],(int)dst[7]};
			marioHitbox2.reset();
			for (int i = 0; i < 4; i++) {
				marioHitbox2.addPoint(xTrans[i], yTrans[i]);
			}
		}
		
		/**** SCALE DOWN ****/
		if(pressedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_A))) {
			mario.setScaleX(mario.getScaleX() - 0.1);
			mario.setScaleY(mario.getScaleY() - 0.1);
			marioHitbox.setSize((int)(mario.getUnscaledWidth() * mario.getScaleX()), (int)(mario.getUnscaledHeight() * mario.getScaleY()));
			marioHitbox2.reset();
		}
		/**** SCALE UP ****/
		if(pressedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_S))) {
			mario.setScaleX(mario.getScaleX() + 0.1);
			mario.setScaleY(mario.getScaleY() + 0.1);
			marioHitbox.setSize((int)(mario.getUnscaledWidth() * mario.getScaleX()), (int)(mario.getUnscaledHeight() * mario.getScaleY()));			
			marioHitbox2.reset();
		}
		/**** TRANSPARENT ****/
		if(pressedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_Z))) {
			mario.setAlpha(mario.getAlpha() - (float)0.1);
			marioHitbox2.reset();
		}
		/**** OPAQUE ****/
		if(pressedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_X))) {
			mario.setAlpha(mario.getAlpha() + (float)0.1);
			marioHitbox2.reset();
		}
		
		if(pressedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_I))) {
			pivotY--;
			marioHitbox.translate(0, 1);
			marioHitbox2.reset();
			hitY[0] = hitY[1] = hitY[0] + 1;
			hitY[2] = hitY[3] = hitY[2] + 1;
		}
		if(pressedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_J))) {
			pivotX--;
			marioHitbox.translate(1, 0);
			marioHitbox2.reset();
			hitX[0] = hitX[3] = hitX[0] + 1;
			hitX[1] = hitX[2] = hitX[1] + 1;
		}
		if(pressedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_K))) {
			pivotY++;
			marioHitbox.translate(0, -1);
			marioHitbox2.reset();
			hitY[0] = hitY[1] = hitY[0] - 1;
			hitY[2] = hitY[3] = hitY[2] - 1;
		}
		if(pressedKeys.contains(KeyEvent.getKeyText(KeyEvent.VK_L))) {
			pivotX++;
			marioHitbox.translate(-1, 0);
			marioHitbox2.reset();
			hitX[0] = hitX[3] = hitX[0] - 1;
			hitX[1] = hitX[2] = hitX[1] - 1;
		}

		mario.setPosition(xPos, yPos);
		mario.setPivotPoint(pivotX, pivotY);

		if(pressedKeys.size() == 0 || !isWalking) {
			mario.setPause(true);
		} else {
			mario.setPause(false);
		}
		
		//reset iswalking
		isWalking = false;
		
		if (time > 0 && healthVal > 0) {
			time -= 0.0166666667;
			timer = "Time Left: " + (int) time;
		} else if (healthVal > 0) {
			winner = "Player 1 wins!";
		} else {
			winner = "Player 2 wins!";
		}
		
		
		/* Make sure mario is not null. Sometimes Swing can auto cause an extra frame to go before everything is initialized */
		if(mario != null) mario.update(pressedKeys);
	}
	
	/**
	 * Engine automatically invokes draw() every frame as well. If we want to make sure mario gets drawn to
	 * the screen, we need to make sure to override this method and call mario's draw method.
	 * */
	@Override
	public void draw(Graphics g){
		super.draw(g);
		g.drawString(health, 15, 20);
		g.drawString(timer, 400, 20);
		g.drawString(winner, 200, 20);
		
		/* Same, just check for null in case a frame gets thrown in before Mario is initialized */
		if(mario != null) mario.draw(g);
	}

	/**
	 * Quick main class that simply creates an instance of our game and starts the timer
	 * that calls update() and draw() every frame
	 * */
	public static void main(String[] args) {
		LabOneGame game = new LabOneGame();
		game.start();

	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		super.keyReleased(e);
		if (e.getKeyChar() == 'v') {
			mario.setVisible(!mario.getVisible());
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		clickX = e.getX();
		clickY = e.getY() - 25;
		
		if (marioHitbox.contains(clickX, clickY) || marioHitbox2.contains(clickX, clickY)) {
			healthVal--;
//			if (healthVal < 0) {
//				healthVal = 0;
//			}
			health = "Health: " + healthVal;
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
