package GUI.elements;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

/**
 * Custom label class for easier management of labels to improve consistency of the look and feel of
 * the program.
 */
public class CustomLabel extends Label {
  /** Constructs an empty, default label. */
  public CustomLabel() {}

  /**
   * Constructs a label with the given text.
   *
   * @param text the text to display in the label.
   */
  public CustomLabel(String text) {
    super(text);
  }

  /** Centers the label. */
  public void center() {
    setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    setAlignment(Pos.CENTER);
  }

  /**
   * Sets the size of the font in this label.
   *
   * @param size the new size in pixels.
   */
  public void setFontSize(int size) {
    addStyle(String.format("-fx-font-size: %dpx", size));
  }

  /** Sets the font in this label to be bold. */
  public void setBold() {
    addStyle("-fx-font-weight: bold");
  }

  /** Sets this label to be a warning label. */
  public void setWarning() {
    setTextFill(Color.FIREBRICK);
  }

  /** Sets this label to be a default information label. */
  public void setInfo() {
    setTextFill(Color.BLACK);
  }

  /**
   * Adds a style to the stylesheet of this label.
   *
   * @param style the style string to add.
   */
  private void addStyle(String style) {
    if (getStyle().length() == 0) {
      setStyle(style);
    } else {
      setStyle(getStyle() + "; " + style);
    }
  }
}
