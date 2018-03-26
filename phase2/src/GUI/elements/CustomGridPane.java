package GUI.elements;

import javafx.geometry.Insets;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

/**
 * Custom grid pane to allow for more consistent segmentation of the screen. Contains methods
 * to easily segment the screen into grids of the desired size.
 */
public class CustomGridPane extends GridPane {
  /** Constructs a default grid pane with no padding. */
  public CustomGridPane() {
    this(0);
  }

  /**
   * Constructs a default grid pane with the given padding on all sides.
   *
   * @param padding the number of pixels of padding.
   */
  public CustomGridPane(int padding) {
    setPadding(new Insets(padding));
  }

  /**
   * Sets the first columns of the grid to have the given percentages of the entire screen.
   *
   * @param percentages iterable of percentages out of 100.
   */
  public void setPercentageColumns(int... percentages) {
    for (int percentage : percentages) {
      ColumnConstraints column = new ColumnConstraints();
      column.setPercentWidth(percentage);
      getColumnConstraints().add(column);
    }
  }

  /**
   * Sets the first rows of the grid to have the given percentages of the entire screen.
   *
   * @param percentages iterable of percentages out of 100.
   */
  public void setPercentageRows(int... percentages) {
    for (int percentage : percentages) {
      RowConstraints row = new RowConstraints();
      row.setPercentHeight(percentage);
      getRowConstraints().add(row);
    }
  }

  /**
   * Sets all of the rows of the grid to have the same percentage of the screen.
   *
   * @param numRows number of rows.
   */
  public void setEvenRows(int numRows) {
    int eachRow = 100 / numRows;
    int[] rowPercentages = new int[numRows];
    for (int i = 0; i < numRows; i++) {
      rowPercentages[i] = eachRow;
    }
    setPercentageRows(rowPercentages);
  }
}
