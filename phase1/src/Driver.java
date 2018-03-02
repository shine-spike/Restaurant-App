public class Driver {
  private static final boolean READ_FILES = true;


  public static void main(String[] args) {
    Restaurant restaurant = new Restaurant();

    if (READ_FILES) {
      restaurant.readFiles();
    }
  }
}
