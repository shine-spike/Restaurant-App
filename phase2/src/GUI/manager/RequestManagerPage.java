package GUI.manager;

import GUI.elements.CustomButton;
import GUI.elements.CustomGridPane;
import GUI.elements.CustomPage;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import util.Reorderer;

public class RequestManagerPage extends CustomPage {
  @Override
  public void populateTab(Tab tab, Node previous) {
    CustomGridPane grid = new CustomGridPane(50);
    grid.setAlignment(Pos.CENTER);
    grid.setHgap(10);
    grid.setVgap(10);

    // Text Area
    TextArea email = new TextArea(Reorderer.getReorders());
    grid.add(email, 0, 0, 2, 1);

    // Buttons
    CustomButton backButton = getBackButton(tab, previous);
    grid.add(backButton, 1, 1);

    CustomButton sendButton = new CustomButton("Send");
    sendButton.setOnAction(
        e ->
            Reorderer.sendEmail(
                "localhost",
                "matthewverreault@yahoo.com",
                "csc207restaurantmarch2018@gmail.com",
                email.getText()));
    grid.add(sendButton, 0, 1);

    tab.setContent(grid);
  }

  @Override
  public void update() {}
}
