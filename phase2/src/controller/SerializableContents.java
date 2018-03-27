package controller;

import java.io.Serializable;

/**
 * Interface for easily allowing the serialization of the contents of our controllers.
 *
 * @param <T> the type of the content.
 */
public interface SerializableContents<T extends Serializable> {
  /**
   * Gets the content of the controller to be serialized.
   *
   * @return the array of serializable contents.
   */
  T[] getContents();

  /**
   * Sets the contents of the controller.
   *
   * @param contents the contents to set.
   */
  void setContents(T[] contents);

  /**
   * Gets the name of the serialization file to save to.
   *
   * @return the name of the file.
   */
  String getName();
}
