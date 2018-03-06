package parsing;

/**
 * Stores all types of events that should be processed by the Parser and related information.
 */
public enum EventType {
  ORDER("ORDER", 3, false),
  SEEN("SEEN", 1, true),
  READY("READY", 1, true),
  ACCEPT("ACCEPT", 1, true),
  REJECT("REJECT", 2, false),
  REDO("REDO", 2, false),
  BILL("BILL", 1, true),
  PAID("PAID", 1, true),
  RECEIVE("RECEIVE", 2, true);


  // Each event has two basic arguments, the event itself and the employee number
  private static final int BASIC_ARGUMENT_NUMBER = 2;

  private String keyword;
  private int minimumArguments;
  private boolean numberArgumentsFixed;


  /**
   * Constructs an EventType.
   *
   * @param keyword              the word that triggers this event in the file.
   * @param minimumArguments     the minimum number of arguments that should be passed to this event.
   * @param numberArgumentsFixed whether or not the minimum number of arguments is also the maximum.
   */
  EventType(String keyword, int minimumArguments, boolean numberArgumentsFixed) {
    this.keyword = keyword;
    this.minimumArguments = minimumArguments;
    this.numberArgumentsFixed = numberArgumentsFixed;
  }

  /**
   * Finds the event type that corresponds to a given keyword.
   *
   * @param keyword the corresponding keyword.
   * @return the event type corresponding to the given keyword or {@code null} if it does not exist.
   */
  public static EventType getEventType(String keyword) {
    for (EventType e : EventType.values()) {
      if (keyword.equals(e.keyword)) {
        return e;
      }
    }

    return null;
  }

  /**
   * Checks if the given size of call is correct for this event type.
   *
   * @param size the number of symbols in a call, including the event itself and employee number.
   * @return whether or not the size is compatible.
   */
  public boolean correctSize(int size) {
    if (numberArgumentsFixed) {
      return size == this.minimumArguments + BASIC_ARGUMENT_NUMBER;
    } else {
      return size >= this.minimumArguments + BASIC_ARGUMENT_NUMBER;
    }
  }

  /**
   * Returns a string representation of the number of arguments required for this event type.
   *
   * @return the string representation of the number of arguments required for this event type.
   */
  public String getRequiredNumberArguments() {
    if (numberArgumentsFixed) {
      return "exactly " + (this.minimumArguments + BASIC_ARGUMENT_NUMBER);
    } else {
      return "at least " + (this.minimumArguments + BASIC_ARGUMENT_NUMBER);
    }
  }
}
