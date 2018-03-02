import java.io.*;

/**
 * Entry point for the program.
 *
 * Controls the starting up of the restaurant program. Parses available configuration files
 * or generates them if they have not been created.
 */
public class Driver {


  public static void main(String[] args) {
    Restaurant restaurant = new Restaurant();

    restaurant.parseConfiguration();
  }
}
