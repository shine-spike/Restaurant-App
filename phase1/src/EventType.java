public enum EventType {
  ORDER("ORDER"),
  SEEN("SEEN"),
  READY("READY"),
  ACCEPT("ACCEPT"),
  REJECT("REJECT"),
  REDO("REDO"),
  BILL("BILL"),
  PAID("PAID"),
  RECEIVE("RECEIVE");

  private String keyword;


  EventType(String keyword) {
    this.keyword = keyword;
  }

  public static EventType getEventType(String keyword) {
    for(EventType e : EventType.values()){
      if(keyword.equals(e.keyword)) {
        return e;
      }
    }

    return null;
  }
}
