package util;

import java.io.*;

public class Serializer {
  private static final String BASE_DIRECTORY = "phase2/config/";

  /**
   * Serializes the given objects and saves them to the file with the given name.
   *
   * @param objects the objects to serialize.
   * @param name the name of the file.
   */
  public static void serialize(Serializable[] objects, String name) {
    try {
      File file = new File(BASE_DIRECTORY + name);
      if (new File(BASE_DIRECTORY).mkdirs()) {
        // TODO: remove print
        System.out.println("Created data directory.");
      }

      if (file.delete()) {
        // TODO: remove print
        System.out.println("Overwrote previous data.");
      } else {
        // TODO: remove print
        System.out.println("Writing new data.");
      }

      FileOutputStream fileOutputStream = new FileOutputStream(file);
      ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
      objectOutputStream.writeObject(objects);
      objectOutputStream.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Deserialize and returns an array of serializable objects of the given type from the file with the given name.
   *
   * @param tClass the type of the array of serializable objects.
   * @param name the name of the file.
   * @param <T> the type of the serializable object.
   * @return the array of deserialized objects.
   */
  public static <T extends Serializable> T[] deserialize(Class<T[]> tClass, String name) {
    T[] result = null;

    try {
      FileInputStream fileInputStream = new FileInputStream(BASE_DIRECTORY + name);
      ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
      result = tClass.cast(objectInputStream.readObject());
      objectInputStream.close();
    } catch (IOException e) {
      // TODO: remove print
      System.out.println("Could not find previous data.");
    } catch (ClassNotFoundException e) {
      // TODO: remove print
      System.out.println("Something went wrong.");
    }

    return result;
  }
}
