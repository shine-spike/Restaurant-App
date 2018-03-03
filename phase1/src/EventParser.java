import java.util.ArrayList;

public class EventParser {

  private Restaurant restaurant;


  EventParser(Restaurant restaurant) {
    this.restaurant = restaurant;
  }


  public void parseEvent(String[] symbols) {
    EventType eventType = EventType.getEventType(symbols[0]);
    if (eventType == null) {
      return;
    }

    switch (eventType) {
      // An order is placed by a server
      case ORDER:
        parseOrderEvent(symbols);
        break;

      // A placed order is seen by a cook
      case SEEN:
        parseSeenEvent(symbols);
        break;

      // A seen order is ready to be served
      case READY:
        parseReadyEvent(symbols);
        break;

      // A ready order is successfully delivered
      case ACCEPT:
        parseAcceptEvent(symbols);
        break;

      // A ready order is rejected by the table
      case REJECT:
        parseRejectEvent(symbols);
        break;

      // A ready order is sent back to be recooked
      case REDO:
        parseRedoEvent(symbols);
        break;

      // A bill is requested by a server
      case BILL:
        parseBillEvent(symbols);
        break;

      // A table paid off the bill
      case PAID:
        parsePaidEvent(symbols);
        break;

      // An ingredient is received by an employee
      case RECEIVE:
        parseReceiveEvent(symbols);
        break;
    }
  }

  private void parseOrderEvent(String[] symbols) {
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

    boolean result = restaurant.orderPlace(Integer.parseInt(symbols[1]), Integer.parseInt(symbols[2]),
            symbols[3], symbols[4], subtractions, additions);
    if (!result) {
      System.out.println("Order could not be placed.");
    }
  }

  private void parseSeenEvent(String[] symbols) {
    boolean result = restaurant.orderSee(Integer.parseInt(symbols[1]), Integer.parseInt(symbols[2]));
    if (!result) {
      System.out.println("Order with the given number is not pending.");
    }
  }

  private void parseReadyEvent(String[] symbols) {
    boolean result = restaurant.orderReady(Integer.parseInt(symbols[1]), Integer.parseInt(symbols[2]));
    if (!result) {
      System.out.println("Order with the given number is not pending.");
    }
  }

  private void parseAcceptEvent(String[] symbols) {
    boolean result = restaurant.orderAccept(Integer.parseInt(symbols[1]), Integer.parseInt(symbols[2]));
    if (!result) {
      System.out.println("Order with the given number is not ready.");
    }
  }

  private void parseRejectEvent(String[] symbols) {
    boolean result = restaurant.orderReject(Integer.parseInt(symbols[1]), Integer.parseInt(symbols[2]), symbols[3]);
    if (!result) {
      System.out.println("Order with the given number is not ready.");
    }
  }

  private void parseRedoEvent(String[] symbols) {
    boolean result = restaurant.orderRedo(Integer.parseInt(symbols[1]), Integer.parseInt(symbols[2]), symbols[3]);
    if (!result) {
      System.out.println("Order with the given number is not ready or the order cannot be fulfilled.");
    }
  }

  private void parseBillEvent(String[] symbols) {
    boolean result = restaurant.printBill(Integer.parseInt(symbols[1]), Integer.parseInt(symbols[2]));
    if (!result) {
      System.out.println("Bill was not able to be printed.");
    }
  }

  private void parsePaidEvent(String[] symbols) {
    boolean result = restaurant.payBill(Integer.parseInt(symbols[1]), Integer.parseInt(symbols[2]));
    if (!result) {
      System.out.println("Bill was not able to be paid.");
    }
  }

  private void parseReceiveEvent(String[] symbols) {
    boolean result = restaurant.receiveIngredient(Integer.parseInt(symbols[1]), symbols[2], Integer.parseInt(symbols[3]));
    if (!result) {
      System.out.println("Ingredient was not able to be received.");
    }
  }
}
