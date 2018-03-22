package controller;

import model.Ingredient;
import model.MenuItem;
import model.Order;
import model.OrderStatus;

import java.util.ArrayList;
import java.util.HashMap;


public class OrderController {
    // Holder for all orders are in the restaurant
    private final ArrayList<Order> orders = new ArrayList<>();

    /**
     * Gets the order with a given order number from all the orders.
     *
     * @param orderNumber the order number to search for.
     * @return the {@link Order} that has the given order number.
     */
    public Order getOrderFromNumber(int orderNumber) {
        for (Order order : orders) {
            if (order.getOrderNumber() == orderNumber) {
                return order;
            }
        }
        return null;
    }

    public Order createOrder(int employeeNumber, int tableNumber, int customerIndex,
                             MenuItem menuItem, HashMap<Ingredient, Integer> ingredients) {
        Order order = new Order(employeeNumber, tableNumber, customerIndex, menuItem, ingredients);
        orders.add(order);
        return order;
    }

    public void cancelOrder(int employeeNumber, Order order) {
        orders.remove(order);
    }

    public void registerOrder(Order order) {
        orders.add(order);
    }

    public ArrayList<Order> ordersFromStatus(OrderStatus orderStatus){
        ArrayList<Order> out = new ArrayList<>();

        for(Order i: orders){
            if(i.getStatus().equals(orderStatus)){
                out.add(i);
            }
        }

        return out;
    }
}
