import java.util.ArrayList;

public class EventParser {
  public static void parseEvent(Restaurant restaurant, String[] symbols) {
    EventType eventType = EventType.getEventType(symbols[0]);
    if (eventType == null) {
      return;
    }

    switch (eventType) {
      // An order is placed by a server
      case ORDER:
        parseOrderEvent(restaurant, symbols);
        break;

      // A placed order is seen by a cook
      case SEEN:
        parseSeenEvent(restaurant, symbols);
        break;

      // A seen order is ready to be served
      case READY:
        // TODO: remove the order from pending orders and notify server
        break;

      // A ready order is successfully delivered
      case ACCEPT:
        // TODO: add the order to the bill
        break;

      // A ready order is rejected by the table
      case REJECT:
        // TODO: discard the order
        break;

      // A ready order is sent back to be recooked
      case REDO:
        // TODO: copy the order, requeue it, and discard the original
        break;

      // A bill is requested by a server
      case BILL:
        // TODO: display the bill
        break;

      // A table paid off the bill
      case PAID:
        // TODO: clear the table's bill
        break;

      // An ingredient is received by an employee
      case RECEIVE:
        // TODO: add the amount of ingredient to the inventory
        break;
    }
  }

  private static void parseOrderEvent(Restaurant restaurant, String[] symbols) {
    ArrayList<String> subtractions = new ArrayList<>();
    ArrayList<String> additions = new ArrayList<>();

    boolean isAddition = false;
    for (int i = 5; i < symbols.length; i++) {
      if (symbols[i].equals("NO")) {
        isAddition = false;
        continue;
      } else if (symbols[i].equals("ADD")) {
        isAddition = true;
        continue;
      }

      if (isAddition) {
        additions.add(symbols[i]);
      } else {
        subtractions.add(symbols[i]);
      }
    }

    boolean result = restaurant.placeOrder(Integer.parseInt(symbols[1]), Integer.parseInt(symbols[2]),
            symbols[3], symbols[4], subtractions, additions);
    if (!result) {
      System.out.println("Order could not be placed.");
    }
  }

  private static void parseSeenEvent(Restaurant restaurant, String[] symbols) {
    boolean result = restaurant.orderSeen(Integer.parseInt(symbols[1]), Integer.parseInt(symbols[2]));
    if (!result) {
      System.out.println("Order with the given number is not pending.");
    } else {
      System.out.println("Order has been seen.");
    }
  }
}
