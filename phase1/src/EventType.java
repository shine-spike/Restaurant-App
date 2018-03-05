public enum EventType {
  ORDER("ORDER", 3, false),
  SEEN("SEEN", 1, true),
  READY("READY", 1, true),
  ACCEPT("ACCEPT", 1, true),
  REJECT("REJECT", 2, true),
  REDO("REDO", 2, true),
  BILL("BILL", 1, true),
  PAID("PAID", 1, true),
  RECEIVE("RECEIVE", 2, true);


  // Each event has two basic arguments, the event itself and the employee number
  private static final int BASIC_ARGUMENT_NUMBER = 2;

  private String keyword;
  private int minimumArguments;
  private boolean numberArgumentsFixed;


  EventType(String keyword, int minimumArguments, boolean numberArgumentsFixed) {
    this.keyword = keyword;
    this.minimumArguments = minimumArguments;
    this.numberArgumentsFixed = numberArgumentsFixed;
  }

  public static EventType getEventType(String keyword) {
    for(EventType e : EventType.values()){
      if(keyword.equals(e.keyword)) {
        return e;
      }
    }

    return null;
  }

  public boolean correctSize(int size) {
    if (numberArgumentsFixed) {
      return size == this.minimumArguments + BASIC_ARGUMENT_NUMBER;
    } else {
      return size >= this.minimumArguments + BASIC_ARGUMENT_NUMBER;
    }
  }

  public String getRequiredNumberArguments() {
    if (numberArgumentsFixed) {
      return "at least " + this.minimumArguments;
    } else {
      return "exactly " + this.minimumArguments;
    }
  }
}
