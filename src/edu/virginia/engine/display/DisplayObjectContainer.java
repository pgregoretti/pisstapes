package edu.virginia.engine.display;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 * A very basic display object for a java based gaming engine
 * 
 * */
public class DisplayObjectContainer extends DisplayObject {

	/* The image that is displayed by this object */	
	private ArrayList<DisplayObject> children = new ArrayList<DisplayObject>();

	/**
	 * Constructors: can pass in the id OR the id and image's file path and
	 * position OR the id and a buffered image and position
	 */
	public DisplayObjectContainer(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}
	
	public DisplayObjectContainer(String id, String fileName) {
		super(id, fileName);
	}
	
	/*** Getters and Setters ***/
	
	public ArrayList<DisplayObject> getAllChildren() {
		return children;
	}
	
	public void addChild(DisplayObject child) {
		this.children.add(child);
	}
	
	public void addChild(DisplayObject child, int i) {
		this.children.add(i, child);
	}
	
	public void removeChild(DisplayObject child) {
		this.children.remove(child);
	}
	
	public void removeChild(int i) {
		this.children.remove(i);
	}
	
	public void removeAll() {
		this.children.clear();
	}
	
	public boolean contains(DisplayObject child) {
		return this.children.contains(child);
	}
	
	public DisplayObject getChild(String id) {
		for (int i = 0; i < this.children.size(); i++) {
			if (this.children.get(i).getId() == id) {
				return this.children.get(i);
			}
		}
		return null;
	}
	
	public DisplayObject getChild(int i) {
		return this.children.get(i);
	}
	
	public ArrayList<DisplayObject> getChildren() {
		return this.children;
	}
	

	/**
	 * Invoked on every frame before drawing. Used to update this display
	 * objects state before the draw occurs. Should be overridden if necessary
	 * to update objects appropriately.
	 * */
	@Override
	protected void update(ArrayList<String> pressedKeys) {
		super.update(pressedKeys);
	}

	/**
	 * Draws this image. This should be overloaded if a display object should
	 * draw to the screen differently. This method is automatically invoked on
	 * every frame.
	 * */
	@Override
	public void draw(Graphics g) {
		super.draw(g);
		if (!this.children.isEmpty()) {
			Graphics2D g2d = (Graphics2D) g;
			applyTransformations(g2d);
	
			/* Actually draw the image, perform the pivot point translation here */
			g2d.translate(-this.getPivotPointX(), -this.getPivotPointY());
			for (int i = 0; i < this.children.size(); i++) {
				this.children.get(i).draw(g2d);
			}
	
			
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
		g2d.rotate(-this.getRotation());
		g2d.translate(-this.getPositionX(), -this.getPositionY());
	}

}
