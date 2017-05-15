package pl.luxdev.mbot.events;

import pl.luxdev.mbot.instances.FirstInstance;

public interface EventHandler<T>{
    void handle(T event, FirstInstance bot);
    EventType getEventType();
}
