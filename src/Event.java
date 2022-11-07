import java.util.List;

/**
 * An event is made up of an Entity that is taking an
 * Action a specified time.
 */
public final class Event {
    private final Action action;
    private final double time;
    private final Entity_I entity;

    public Event(Action action, double time, Entity_I entity) {
        this.action = action;
        this.time = time;
        this.entity = entity;
    }

    public Action getAction() {
        return action;
    }

    public double getTime() {
        return time;
    }

    public void removePendingEvent(EventScheduler eventScheduler) {
        List<Event> pending = eventScheduler.getPendingEvents().get(this.entity);

        if (pending != null) {
            pending.remove(this);
        }
    }



}
