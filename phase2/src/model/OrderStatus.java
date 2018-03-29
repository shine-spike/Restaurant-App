package model;

/** Simple enum representing all different statuses our orders can be in. */
public enum OrderStatus {
  CREATED,
  PLACED,
  UNSATISFIABLE,
  SEEN,
  READY,
  ACCEPTED,
  REJECTED,
  REDO,
  CANCELLED
}
