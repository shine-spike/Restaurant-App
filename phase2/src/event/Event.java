package event;

import controller.Restaurant;

public abstract class Event {
    Restaurant restaurant = Restaurant.getInstance();
    int employeeNumber;


    Event(int employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    abstract EventStatus process();
}
