package GUI.elements;

import javafx.scene.control.Button;

/** Custom button to allow for easier and more consistent detailing of buttons in the program. */
public class CustomButton extends Button {
  /** Constructs a default button with no text. */
  public CustomButton() {}

  /**
   * Constructs a default button with the given text.
   *
   * @param text the text in the button.
   */
  public CustomButton(String text) {
    super(text);
  }

  /** Maximizes the button to fill up the entire cell. */
  public void maximize() {
    setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
  }

  /**
   * Sets the font size of the text inside the button.
   *
   * @param fontSize the font size of the text.
   */
  public void setFontSize(int fontSize) {
    setStyle(String.format("-fx-font-size: %dpx", fontSize));
  }
}
