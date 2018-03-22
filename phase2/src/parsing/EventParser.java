package parsing;

import event.*;
import model.Restaurant;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Controls parsing of all event symbols and passing on the respective information to the Restaurant.
 */
class EventParser {
  private final Restaurant restaurant;


  /**
   * Construct an EventParser for a given restaurant.
   *
   * @param restaurant the restaurant to pass in the information to.
   */
  EventParser(Restaurant restaurant) {
    this.restaurant = restaurant;
  }


  /**
   * Parses a given array of strings representing an event call.
   * Distributes the parsing of events depending on which type of event it is.
   *
   * @param symbols the array of strings representing an event.
   */
  public void parseEvent(String[] symbols) {
    // Get the type of event
    EventType eventType = EventType.getEventType(symbols[0]);
    // Return if there is no event of that type
    if (eventType == null) {
      return;
    }

    // Make sure the given symbols are of the correct size for the event they represent
    if (!eventType.correctSize(symbols.length)) {
      System.out.println(eventType.name() + " event should have " + eventType.getRequiredNumberArguments()
              + " number of arguments, got " + symbols.length + ". Skipping.");
    }

    // Distribute the event parsing depending on the type of event
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

  /**
   * Parses a given array of strings that represent an order event.
   * Places an order in the restaurant if parsing was successful.
   *
   * @param symbols the array of strings representing an order event.
   */
  private void parseOrderEvent(String[] symbols) {
    HashMap<String, Integer> ingredientStrings = new HashMap<>();

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
        ingredientStrings.put(symbols[i], ingredientStrings.getOrDefault(symbols[i], 1) + 1);
      } else {
        ingredientStrings.put(symbols[i], ingredientStrings.getOrDefault(symbols[i], 0) - 1);
      }
    }

//    boolean result = restaurant.placeOrder(Integer.parseInt(symbols[1]), Integer.parseInt(symbols[2]),
//            symbols[3], symbols[4], subtractions, additions);
//    if (!result) {
//      System.out.println("Order could not be placed.");
//    }
    new PlaceOrderEvent(Integer.parseInt(symbols[1]), Integer.parseInt(symbols[2]),
                        symbols[3], symbols[4], ingredientStrings).process();
  }

  /**
   * Parses a given array of strings that represent a seen event.
   * Flags the order as seen in the restaurant if parsing was successful.
   *
   * @param symbols the array of strings representing a seen event.
   */
  private void parseSeenEvent(String[] symbols) {
//    boolean result = restaurant.orderSeen(Integer.parseInt(symbols[1]), Integer.parseInt(symbols[2]));
//    if (!result) {
//      System.out.println("Order with the given number is not pending.");
//    }
    new SeenOrderEvent(Integer.parseInt(symbols[1]), Integer.parseInt(symbols[2])).process();
  }

  /**
   * Parses a given array of strings that represent a ready event.
   * Notifies the restaurant that the order is ready if parsing was successful.
   *
   * @param symbols the array of strings representing a ready event.
   */
  private void parseReadyEvent(String[] symbols) {
//    boolean result = restaurant.orderReady(Integer.parseInt(symbols[1]), Integer.parseInt(symbols[2]));
//    if (!result) {
//      System.out.println("Order with the given number is not pending.");
//    }
    new ReadyOrderEvent(Integer.parseInt(symbols[1]), Integer.parseInt(symbols[2])).process();
  }

  /**
   * Parses a given array of strings that represent an accept event.
   * Notifies the restaurant that the order has been accepted if parsing was successful.
   *
   * @param symbols the array of strings representing an accept event.
   */
  private void parseAcceptEvent(String[] symbols) {
//    boolean result = restaurant.orderAccepted(Integer.parseInt(symbols[1]), Integer.parseInt(symbols[2]));
//    if (!result) {
//      System.out.println("Order with the given number is not ready.");
//    }
    new AcceptedOrderEvent(Integer.parseInt(symbols[1]), Integer.parseInt(symbols[2])).process();
  }

  /**
   * Parses a given array of strings that represent a reject event.
   * Notifies the restaurant that the order has been rejected if parsing was successful.
   *
   * @param symbols the array of strings representing a reject event.
   */
  private void parseRejectEvent(String[] symbols) {
    StringBuilder reason = new StringBuilder();
    for (int i = 3; i < symbols.length; i++) {
      reason.append(symbols[i]);
      reason.append(" ");
    }

//    boolean result = restaurant.orderRejected(Integer.parseInt(symbols[1]), Integer.parseInt(symbols[2]), reason.toString());
//    if (!result) {
//      System.out.println("Order with the given number is not ready.");
//    }
    new RejectedOrderEvent(Integer.parseInt(symbols[1]), Integer.parseInt(symbols[2]), reason.toString()).process();
  }

  /**
   * Parses a given array of strings that represent a redo event.
   * Notifies the restaurant that the order is to be redone if parsing was successful.
   *
   * @param symbols the array of strings representing a redo event.
   */
  private void parseRedoEvent(String[] symbols) {
    StringBuilder reason = new StringBuilder();
    for (int i = 3; i < symbols.length; i++) {
      reason.append(symbols[i]);
      reason.append(" ");
    }

//    boolean result = restaurant.redoOrder(Integer.parseInt(symbols[1]), Integer.parseInt(symbols[2]), reason.toString());
//    if (!result) {
//      System.out.println("Order with the given number is not ready or the order cannot be fulfilled.");
//    }
    new RedoOrderEvent(Integer.parseInt(symbols[1]), Integer.parseInt(symbols[2]), reason.toString()).process();
  }

  /**
   * Parses a given array of strings that represent a bill event.
   * Prints out the bill in the restaurant if parsing was successful.
   *
   * @param symbols the array of strings representing a bill event.
   */
  private void parseBillEvent(String[] symbols) {
//    boolean result = restaurant.printBill(Integer.parseInt(symbols[1]), Integer.parseInt(symbols[2]));
//    if (!result) {
//      System.out.println("Bill was not able to be printed.");
//    }
    new PrintBillEvent(Integer.parseInt(symbols[1]), Integer.parseInt(symbols[2])).process();
  }

  /**
   * Parses a given array of strings that represent a paid event.
   * Pays the bill in the restaurant if parsing was successful.
   *
   * @param symbols the array of strings representing a paid event.
   */
  private void parsePaidEvent(String[] symbols) {
//    boolean result = restaurant.billPaid(Integer.parseInt(symbols[1]), Integer.parseInt(symbols[2]));
//    if (!result) {
//      System.out.println("Bill was not able to be paid.");
//    }
    new PaidBillEvent(Integer.parseInt(symbols[1]), Integer.parseInt(symbols[2])).process();
  }

  /**
   * Parses a given array of strings that represent a receive event.
   * Receives ingredients in the restaurant if parsing was successful.
   *
   * @param symbols the array of strings representing a receive event.
   */
  private void parseReceiveEvent(String[] symbols) {
//    boolean result = restaurant.ingredientsReceived(Integer.parseInt(symbols[1]), symbols[2], Integer.parseInt(symbols[3]));
//    if (!result) {
//      System.out.println("Ingredient was not able to be received.");
//    }
    new ReceiveEvent(Integer.parseInt(symbols[1]), symbols[2], Integer.parseInt(symbols[3])).process();
  }
}
