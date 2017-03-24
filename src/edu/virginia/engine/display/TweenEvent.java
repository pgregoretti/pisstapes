package edu.virginia.engine.display;

import edu.virginia.engine.events.Event;
import edu.virginia.engine.events.IEventDispatcher;

public class TweenEvent extends Event{
	//String eventType;
	Tween tween;
	private String eventType;
	private IEventDispatcher source; //the object that created this event with the new keyword
	
	//final static strings denoting the various events (e.g., TWEEN_COMPLETE_EVENT)
	
	public TweenEvent(String eventType, Tween tween, IEventDispatcher source){
		super(eventType, source);
		this.eventType = eventType;
		this.tween = tween;
	}
	
	public Tween getTween(){
		return this.tween;
	}

}
