package edu.virginia.engine.events;

import java.util.ArrayList;
import java.util.HashMap;

public class EventDispatcher implements IEventDispatcher {
	
	HashMap<String, ArrayList<IEventListener>> observables = new HashMap<String, ArrayList<IEventListener>>();

	@Override
	public void addEventListener(IEventListener listener, String eventType) {
		
		//this event type already exists
		if (observables.containsKey(eventType))
			observables.get(eventType).add(listener);
		//otherwise, create a new event type and add subscribe this listener to the event
		else {
			ArrayList<IEventListener> subscribers = new ArrayList<IEventListener>();
			subscribers.add(listener);
			observables.put(eventType, subscribers);
		}
		
	}

	@Override
	public void removeEventListener(IEventListener listener, String eventType) {
		observables.get(eventType).remove(listener);
		
	}

	@Override
	public void dispatchEvent(Event event) {
		ArrayList<IEventListener> subscribers = observables.get(event.getEventType());
		for (int i = 0; i < subscribers.size(); i++)
			subscribers.get(i).handleEvent(event);		
	}

	@Override
	public boolean hasEventListener(IEventListener listener, String eventType) {
		return observables.get(eventType).contains(listener);
	}

}
