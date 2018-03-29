package GUI.manager;

import GUI.elements.*;
import javafx.scene.control.TextArea;
import util.Reorderer;

public class RequestManagerPage extends CustomPage {
  private TextArea emailArea = new TextArea();

  RequestManagerPage() {
    update();
  }

  @Override
  public void populateTab(CustomTab tab) {
    CustomGridPane grid = new CustomGridPane(50);
    grid.setHgap(10);
    grid.setPercentageColumns(50, 50);
    grid.setEvenRows(24);

    CustomLabel emailLabel = new CustomLabel("Email to Send");
    emailLabel.center();
    emailLabel.setBold();
    emailLabel.setFontSize(20);
    grid.add(emailLabel, 0, 0, 2, 1);

    grid.add(emailArea, 0, 1, 2, 19);

    CustomButton sendButton = new CustomButton("Send");
    sendButton.maximize();
    sendButton.setOnAction(
        e ->
            Reorderer.sendEmail(
                "localhost",
                "matthewverreault@yahoo.com",
                "csc207restaurantmarch2018@gmail.com",
                emailArea.getText()));
    grid.add(sendButton, 0, 22, 1, 2);

    if (tab.canGoBack()) {
      grid.add(getBackButton(tab), 1, 22, 1, 2);
    }

    tab.setCurrentPage(this, grid);
  }

  @Override
  public void update() {
    emailArea.setText(Reorderer.getReorders());
  }
}
