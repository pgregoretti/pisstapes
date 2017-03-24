package edu.virginia.lab1test;

import edu.virginia.engine.display.TweenEvent;
import edu.virginia.engine.display.TweenJuggler;
import edu.virginia.engine.events.Event;
import edu.virginia.engine.events.IEventListener;

public class QuestManager implements IEventListener{

	@Override
	public void handleEvent(Event event) {
		String type = event.getEventType();
		if (type.equals("coin")) {
			System.out.println("Quest complete!");
		}
		if (type.equals("rupee")) {
			System.out.println(TweenJuggler.getInstance().getTweens().size());
			TweenJuggler.getInstance().add(((TweenEvent) event).getTween());
			System.out.println(TweenJuggler.getInstance().getTweens().size());
		}
	}

}
