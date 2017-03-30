package edu.virginia.engine.display;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import edu.virginia.engine.events.EventDispatcher;

/**
 * A very basic display object for a java based gaming engine
 * 
 * */
public class DisplayObject extends EventDispatcher {

	/* All DisplayObject have a unique id */
	private String id;

	/* The image that is displayed by this object */
	private BufferedImage displayImage;
	
	private DisplayObject parent;
	

	/* additional fields */
	private boolean visible = true;
	private Point position = new Point(0,0);
	private Point pivotPoint = new Point(0,0);
	private double scaleX = 1.0;
	private double scaleY = 1.0;
	private double rotation = 0.0;
	private float alpha = (float)1.0;
	private double vx = 0.0;
	private double vy = 0.0;
	private boolean hasPhysics = false;
	private boolean isCollidable = true;
	

	/**
	 * Constructors: can pass in the id OR the id and image's file path and
	 * position OR the id and a buffered image and position
	 */
	public DisplayObject(String id) {
		this.setId(id);
	}

	public DisplayObject(String id, String fileName) {
		this.setId(id);
		this.setImage(fileName);
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}


	/**
	 * Returns the unscaled width and height of this display object
	 * */
	public int getUnscaledWidth() {
		if(displayImage == null) return 0;
		return displayImage.getWidth();
	}

	public int getUnscaledHeight() {
		if(displayImage == null) return 0;
		return displayImage.getHeight();
	}

	public BufferedImage getDisplayImage() {
		return this.displayImage;
	}

	protected void setImage(String imageName) {
		if (imageName == null) {
			return;
		}
		displayImage = readImage(imageName);
		if (displayImage == null) {
			System.err.println("[DisplayObject.setImage] ERROR: " + imageName + " does not exist!");
		}
	}
	
	/* Additional getters and setters */
	public DisplayObject getParent() {
		return parent;
	}

	public void setParent(DisplayObject parent) {
		this.parent = parent;
	}
	
	public boolean getVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
		if (visible) {
			this.setAlpha(1.0f);
		} else {
			this.setAlpha(0.0f);
		}
	}

	public double getPositionX() {
		return position.getX();
	}
	
	public double getPositionY() {
		return position.getY();
	}

	public void setPosition(double x, double y) {
		this.position.setLocation(x, y);
	}
	
	public void setPositionX(double x) {
		this.position.setLocation(x, this.position.getY());
	}
	
	public void setPositionY(double y) {
		this.position.setLocation(this.position.getX(), y);
	}
	
	public double getPivotPointX() {
		return pivotPoint.getX();
	}
	
	public double getPivotPointY() {
		return pivotPoint.getY();
	}
	
	public void setPivotPoint(double x, double y) {
		this.pivotPoint.setLocation(x, y);
	}

	public void setPivotPointX(double x) {
		this.pivotPoint.setLocation(x, this.pivotPoint.getY());
	}
	
	public void setPivotPointY(double y) {
		this.pivotPoint.setLocation(this.pivotPoint.getX(), y);
	}

	public double getScaleX() {
		return scaleX;
	}

	public void setScaleX(double scaleX) {
		this.scaleX = scaleX;
	}

	public double getScaleY() {
		return scaleY;
	}

	public void setScaleY(double scaleY) {
		this.scaleY = scaleY;
	}

	public double getRotation() {
		return rotation;
	}

	public void setRotation(double rotation) {
		this.rotation = rotation;
	}

	public float getAlpha() {
		return alpha;
	}

	public void setAlpha(float alpha) {
		//include bounds checking
		if((double) alpha < 0.0)
			alpha = (float) 0.0;
		if((double) alpha > 1.0)
			alpha = (float) 1.0;
		this.alpha = alpha;
	}
	
	public double getVX() {
		return vx;
	}

	public void setVX(double vx) {
		this.vx = vx;
	}

	public double getVY() {
		return vy;
	}

	public void setVY(double vy) {
		this.vy = vy;
	}

	public boolean hasPhysics() {
		return hasPhysics;
	}

	public void setPhysics(boolean hasPhysics) {
		this.hasPhysics = hasPhysics;
	}
	
	public boolean getCollidable() {
		return this.isCollidable;
	}

	public void setCollidable(boolean collidable) {
		this.isCollidable = collidable;
	}
	
	/**
	 * COLLISION DETECTION
	 */
	public Rectangle getHitbox() {
		//kind of overboard but...
		int currentX = (int) this.getPositionX();
		int currentY = (int) this.getPositionY();
		int currentWidth = (int) (this.getUnscaledWidth() * this.getScaleX());
		int currentHeight = (int) (this.getUnscaledHeight() * this.getScaleY());
		
		return new Rectangle(currentX, currentY, currentWidth, currentHeight); 
	}
	
	public boolean collidesWith(DisplayObject other) {
		//returns true if their respective Rectangle hitboxes intersect.
		return this.getHitbox().intersects(other.getHitbox());
	}
	
	public Rectangle getCollisionWith(DisplayObject other) {
		Rectangle hit = this.getHitbox().intersection(other.getHitbox());
		
		return hit; 
	}


	/**
	 * Helper function that simply reads an image from the given image name
	 * (looks in resources\\) and returns the bufferedimage for that filename
	 * */
	public BufferedImage readImage(String imageName) {
		BufferedImage image = null;
		try {
			String file = ("resources" + File.separator + imageName);
			image = ImageIO.read(new File(file));
		} catch (IOException e) {
			System.out.println("[Error in DisplayObject.java:readImage] Could not read image " + imageName);
			e.printStackTrace();
		}
		return image;
	}

	public void setImage(BufferedImage image) {
		if(image == null) return;
		displayImage = image;
	}
	

	/**
	 * Invoked on every frame before drawing. Used to update this display
	 * objects state before the draw occurs. Should be overridden if necessary
	 * to update objects appropriately.
	 * */
	protected void update(ArrayList<String> pressedKeys) {
		
	}

	/**
	 * Draws this image. This should be overloaded if a display object should
	 * draw to the screen differently. This method is automatically invoked on
	 * every frame.
	 * */
	public void draw(Graphics g) {
		
		if (displayImage != null) {
			
			/*
			 * Get the graphics and apply this objects transformations
			 * (rotation, etc.)
			 */
			Graphics2D g2d = (Graphics2D) g;
			applyTransformations(g2d);

			/* Actually draw the image, perform the pivot point translation here */
			g2d.translate(-this.getPivotPointX(), -this.getPivotPointY());
			g2d.drawImage(displayImage, 0, 0,
					(int) (getUnscaledWidth()),
					(int) (getUnscaledHeight()), null);
			g2d.drawOval((int)this.getPivotPointX(), (int)this.getPivotPointY(), 5, 5);
			g2d.translate(this.getPivotPointX(), this.getPivotPointY());
			
			/*
			 * undo the transformations so this doesn't affect other display
			 * objects
			 */
			reverseTransformations(g2d);
		}
	}

	/**
	 * Applies transformations for this display object to the given graphics
	 * object
	 * */
	protected void applyTransformations(Graphics2D g2d) {
		g2d.translate(this.getPositionX(), this.getPositionY());
//		g2d.rotate(this.getRotation(), this.getPivotPointX(), this.getPivotPointY());
		g2d.rotate(this.getRotation());
		g2d.scale(this.getScaleX(), this.getScaleY());
        g2d.setComposite(AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, this.getAlpha()));
	}

	/**
	 * Reverses transformations for this display object to the given graphics
	 * object
	 * */
	protected void reverseTransformations(Graphics2D g2d) {
        g2d.setComposite(AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, 1.0f));
		g2d.scale(1/this.getScaleX(), 1/this.getScaleY());
//		g2d.rotate(-this.getRotation(), this.getPivotPointX(), this.getPivotPointY());
		g2d.rotate(-this.getRotation());
		g2d.translate(-this.getPositionX(), -this.getPositionY());
	}

}