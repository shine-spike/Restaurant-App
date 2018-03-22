package event;

import controller.Restaurant;

public abstract class Event {
    public final Restaurant restaurant = Restaurant.getInstance();
    public final int employeeNumber;


    public Event(int employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public abstract EventStatus process();
}
