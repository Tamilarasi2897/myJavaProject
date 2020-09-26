package sx3Configuration.webView;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.events.EventTarget;
import org.w3c.dom.html.HTMLAnchorElement;

import javafx.application.HostServices;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.scene.web.WebEngine;

public class RegisterHyperlinkHandler
          implements ChangeListener<Worker.State> {
	
    final WebEngine engine;
    final HostServices services;

    public RegisterHyperlinkHandler( WebEngine engine, HostServices services ) {
      this.engine = engine;
      this.services = services;
    }

    @Override
    public void changed(
            ObservableValue<? extends Worker.State> observable,
            Worker.State oldValue,
            Worker.State newValue
    ) {
      if (Worker.State.SUCCEEDED == newValue) {
        Document document = engine.getDocument();
        NodeList anchors = document.getElementsByTagName("a");
        HyperlinkHandler handler = new HyperlinkHandler(services);
        for (int i = 0, n = anchors.getLength(); i < n; ++i) {
          ((EventTarget) anchors.item(i)).addEventListener("click", handler, false);
        }
      }
    }
  }

  final class HyperlinkHandler implements EventListener {
    final HostServices services;

    HyperlinkHandler( HostServices services ) {
      this.services = services;
    }

	@Override
	public void handleEvent(Event evt) {
		String href = ((HTMLAnchorElement) evt.getCurrentTarget()).getHref();
	      services.showDocument(href);
	      evt.preventDefault();
	}
  }