package edu.virginia.engine.display;

import java.util.ArrayList;

// this is a singleton class
// access by doing TweenJuggler.getInstance().someMethod();
public class TweenJuggler {
	ArrayList<Tween> tweens = new ArrayList<Tween>();
	
	public ArrayList<Tween> getTweens() {
		return tweens;
	}

	private static TweenJuggler instance;
	
	public TweenJuggler(){
		if(instance != null){
			System.out.println("ERROR: Cannot re-initialize TweenJuggler!");
		}
		instance = this;
	}

	public static TweenJuggler getInstance(){
		return instance;
	}
	
	public void clear(){
		tweens.clear();
	}
	

	public void add(Tween tween){
		tweens.add(tween);
	}
	
	//invoked every frame by Game, calls update() on every Tween and
	//cleans up old / complete Tweens
	public void nextFrame(){
		for(int i = 0; i < tweens.size(); i++){
			if (tweens.get(i).isComplete()) {
				tweens.remove(i);
			} else {
				tweens.get(i).update();
			}
			// I think we can just leave complete tweens alone?
		}
		
	}

}
