package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.util.HashMap;

public class EventManage {
    private HashMap<String, EventHandler<ActionEvent>> eventMap;
    public EventManage () {
        eventMap = new HashMap<String, EventHandler<ActionEvent>> ();
    }

    public void subscribe (String eventName, EventHandler<ActionEvent> event) {
        eventMap.put(eventName, event);
    }

    public void unsubscribe (String eventName) {
        eventMap.remove(eventName);
    }

    public EventHandler<ActionEvent> getEvent (String eventName) {
        return eventMap.get(eventName);
    }
}
