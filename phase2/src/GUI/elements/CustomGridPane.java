package GUI.elements;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class CustomGridPane extends GridPane {
  public void setPercentageColumns(int... percentages) {
    for (int percentage : percentages) {
      ColumnConstraints column = new ColumnConstraints();
      column.setPercentWidth(percentage);
      getColumnConstraints().add(column);
    }
  }

  public void setPercentageRows(int... percentages) {
    for (int percentage : percentages) {
      RowConstraints row = new RowConstraints();
      row.setPercentHeight(percentage);
      getRowConstraints().add(row);
    }
  }
}