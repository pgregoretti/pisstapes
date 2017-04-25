package edu.virginia.engine.display;

import java.util.ArrayList;

public class Tween {
	private DisplayObject object;
	private String transition;
	private String param;
	private double startVal;
	private double endVal;
	private double time;
	private boolean isAnimating = false;
	private double incr = 0;
	ArrayList<String> pressedKeys = new ArrayList<String>();
	
	public Tween(DisplayObject object) {
		this.object = object;
		this.transition = "LINEAR";
	}
	
	public Tween(DisplayObject object, String transition) {
		this.object = object;
		this.transition = transition;
	}
	
	public String getParam() {
		return this.param;
	}
	
	public void animate(String fieldToAnimate, double startVal, double endVal, double time) {
		this.param = fieldToAnimate;
		this.startVal = startVal;
		this.endVal = endVal;
		this.time = time;
		this.isAnimating = true;
		
		//System.out.println("Incr Y by " + Double.toString(incr));
		if (this.transition.equals("LINEAR")) {
			this.incr = (this.endVal - this.startVal)/this.time;
			System.out.println("Incr " + this.param + " of " + this.object.getId() + " by " + Double.toString(incr));
			if(Math.abs(incr) < 0.5 && incr < 0){
				System.out.print("Fixing value from " + Double.toString(incr) + " to ");
				this.incr = -1;
				System.out.println(Double.toString(incr));
			}
			if(Math.abs(incr) < 0.5 && incr > 0){
				System.out.print("Fixing value from " + Double.toString(incr) + " to ");
				this.incr = 1;
				System.out.println(Double.toString(incr));
			}
		}
		setStart();
	}
	
	public void setStart() {
		if (this.param.equals("X")) {
			this.object.setPositionX(this.startVal);
		}
		if (this.param.equals("Y")) {
			this.object.setPositionY(this.startVal);
		}
		if (this.param.equals("SCALE_X")) {
			this.object.setScaleX(this.startVal);
		}
		if (this.param.equals("SCALE_Y")) {
			this.object.setScaleY(this.startVal);
		}
		if (this.param.equals("ALPHA")) {
			this.object.setAlpha((float)this.startVal);
		}
	}
	
	public boolean isComplete() {
		if (this.param.equals("X")) {
			if(this.startVal >= this.endVal){
				return this.object.getPositionX() <= this.endVal;
			}
			if(this.startVal < this.endVal){
				return this.object.getPositionX() >= this.endVal;
			}
			//return Math.abs(this.object.getPositionX() - this.endVal) == 0;
		}
		if (this.param.equals("Y")) {
			if(this.startVal >= this.endVal){
				return this.object.getPositionY() <= this.endVal;
			}
			if(this.startVal < this.endVal){
				return this.object.getPositionY() >= this.endVal;
			}
			//return Math.abs(this.object.getPositionY() - this.endVal) == 0;
		}
		if (this.param.equals("SCALE_X")) {
			return this.object.getScaleX() >= this.endVal;
		}
		if (this.param.equals("SCALE_Y")) {
			return this.object.getScaleY() >= this.endVal;
		}
		if (this.param.equals("ALPHA")) {
			return this.object.getAlpha() <= this.endVal;
		}
		System.out.println("No params matched");
		return false;
	}
	
//	public void setValue(TweenableParam param, double value) {
//		
//	}
	
	public void update() {
		this.object.update(pressedKeys);
		//invoked once per frame by the TweenJuggler. Updates this tween / DisplayObject
		//change object actual fields here
		if (isAnimating) {
			
			/** allows LINEAR and QUADRATIC transitions **/
			if (this.transition.equals("QUADRATIC")) 
				this.incr = this.incr++;
			
			if (this.param.equals("X")) {
				if(this.transition.equals("LINEAR")){
					this.object.setPositionX(this.object.getPositionX() + this.incr);
				}
				if(this.transition.equals("QUADRATIC")){
					double temp = this.object.getPositionX()+incr*incr;
					if (temp > this.endVal)
						temp = this.endVal;
					this.object.setPositionX(temp);
				}
				
			}
			if (this.param.equals("Y")) {
				
				if(this.transition.equals("LINEAR")){
					this.object.setPositionY(this.object.getPositionY() + this.incr);
				}
				if(this.transition.equals("QUADRATIC")){
					double temp = this.object.getPositionY()+incr*incr;
					if (temp > this.endVal)
						temp = this.endVal;
					this.object.setPositionY(temp);
				}
				
			}
			if (this.param.equals("SCALE_X")) {
				
				if(this.transition.equals("LINEAR")){
					this.object.setScaleX(this.object.getScaleX() + this.incr);
				}
				if(this.transition.equals("QUADRATIC")){
					double temp = this.object.getScaleX()+(incr*incr)/10;
					if (temp > this.endVal)
						temp = this.endVal;
					this.object.setScaleX(temp);
				}

			}
			if (this.param.equals("SCALE_Y")) {
				
				if(this.transition.equals("LINEAR")){
					this.object.setScaleY(this.object.getScaleY() + this.incr);
				}
				if(this.transition.equals("QUADRATIC")){
					double temp = this.object.getScaleY()+(incr*incr)/10;
					if (temp > this.endVal)
						temp = this.endVal;
					this.object.setScaleY(temp);
				}
				
			}
			if (this.param.equals("ALPHA")) {
				
				if(this.transition.equals("LINEAR")){
					this.object.setAlpha(this.object.getAlpha() + (float)this.incr);
				}
				if(this.transition.equals("QUADRATIC")){
					double temp = this.object.getAlpha()+(float)((incr*incr)/10);
					if (temp > this.endVal)
						temp = this.endVal;
					this.object.setAlpha((float)temp);
				}
				
			}
		}
		if (isComplete()) {
			this.isAnimating = false;
		}
	}
}
