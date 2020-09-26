package sx3Configuration.util;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;

public class OnMouseExitedHandler implements EventHandler<Event>{
	
	private ContextMenu contextMenu;
	public OnMouseExitedHandler(ContextMenu contextMenu) {
		this.contextMenu = contextMenu;
	}


	@Override
	public void handle(Event event) {
		contextMenu.hide();
		
	}
	
}
