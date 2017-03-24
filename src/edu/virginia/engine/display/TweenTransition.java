package edu.virginia.engine.display;

public class TweenTransition {
	//double percentDone;
	String transition = "";
	TweenTransition(String s){
		this.transition = s;
	}
	
	public String getTransition() {
		return transition;
	}

	public void setTransition(String transition) {
		this.transition = transition;
	}

	public void applyTransition(double percentDone){
		//this.percentDone = percentDone;
		
	}

	public void easeInOut(double percentDone){
		// applies a specific transition function, can have more of these
		// for each transition your engine supports. I will only list one here. 
	}

}
