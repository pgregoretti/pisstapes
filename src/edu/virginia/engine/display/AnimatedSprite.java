package edu.virginia.engine.display;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class AnimatedSprite extends Sprite {
	
	private ArrayList<BufferedImage> frames = new ArrayList<BufferedImage>();
	private BufferedImage bigImg;
	private int currentFrame = 0;
	private int startIndex = 0;
	private int endIndex = 9;
	private Map<String, Integer> startDict = new HashMap<String, Integer>();
	private Map<String, Integer> endDict = new HashMap<String, Integer>();
	private String animation = "";
	private int count = 0;
	private int speed = 3;
	private boolean pause = false;
	
	
	public AnimatedSprite(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}
	
	public AnimatedSprite(String id, String spriteSheet, int spriteWidth, int spriteHeight, int rows, int cols, int startFrame) {
		super(id);
		
		//read and parse out sprite sheet
		try {
			String file = ("resources" + File.separator + spriteSheet);
			this.bigImg = ImageIO.read(new File(file));
		} catch (IOException e) {
			System.out.println("[Error in DisplayObject.java:readImage] Could not read image " + spriteSheet);
			e.printStackTrace();
		}

		for (int i = 0; i < rows; i++) {
		    for (int j = 0; j < cols; j++) {
		    	this.frames.add(bigImg.getSubimage(j * spriteWidth, i * spriteHeight, spriteWidth, spriteHeight));
		    }
		}	
		
		this.setImage(this.frames.get(startFrame));
	}
	
	public String getAnimation() {
		return this.animation;
	}
	
	public void setAnimation (String animation, int start, int end) {
		this.startDict.put(animation, start);
		this.endDict.put(animation, end);
	}
	
	public void setSpeed (int speed) {
		this.speed = speed;
	}
	
	public void setPause (boolean pause) {
		this.pause = pause;
	}
	
	public void animate (String animation) {
		this.startIndex = this.startDict.get(animation);
		this.endIndex = this.endDict.get(animation);
		this.currentFrame = this.startIndex;
		this.animation = animation;
	}
	
	@Override
	public void update(ArrayList<String> pressedKeys) {
		if (!this.pause) {
			if (this.count >= this.speed) {
				this.setImage(this.frames.get(this.currentFrame));
				if (this.currentFrame == this.endIndex)
					this.currentFrame = this.startIndex;
				else
					this.currentFrame = this.currentFrame + 1;
				this.count = 0;
			} else
				this.count = this.count + 1;
		}
		
		super.update(pressedKeys);
	}
	
	

}
