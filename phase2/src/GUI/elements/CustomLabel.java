package GUI.elements;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class CustomLabel extends Label {
  public CustomLabel() {}

  public CustomLabel(String label) {
    super(label);
  }

  public void center() {
    setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    setAlignment(Pos.CENTER);
  }

  public void setFontSize(int size) {
    addStyle(String.format("-fx-font-size: %dpx", size));
  }

  public void setBold() {
    addStyle("-fx-font-weight: bold");
  }

  public void setWarning() {
    setTextFill(Color.FIREBRICK);
  }

  public void setInfo() {
    setTextFill(Color.BLACK);
  }

  private void addStyle(String style) {
    if (getStyle().length() == 0) {
      setStyle(style);
    } else {
      setStyle(getStyle() + "; " + style);
    }
  }
}
