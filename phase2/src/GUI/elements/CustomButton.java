package GUI.elements;

import javafx.scene.control.Button;

public class CustomButton extends Button {
  public CustomButton(String text, boolean maximize, int fontSize) {
    this(text, maximize);
    setStyle(String.format("-fx-font-size: %dpx", fontSize));
  }

  public CustomButton(String text, boolean maximize) {
    super(text);
    if (maximize) {
      setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    }
  }
}
