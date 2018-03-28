package GUI.manager;

import GUI.elements.*;

public class HomeManagerPage extends CustomPage {
  @Override
  public void populateTab(CustomTab tab) {
    CustomGridPane grid = new CustomGridPane(0);
    grid.setPercentageRows(10);
    grid.setPercentageColumns(25, 50, 25);
    grid.setVgap(50);

    CustomLabel headerLabel = new CustomLabel("Management");
    headerLabel.setFontSize(30);
    headerLabel.center();
    headerLabel.setBold();
    grid.add(headerLabel, 1, 1);

    CustomButton viewInventoryButton = new CustomButton("View Inventory");
    viewInventoryButton.maximize();
    viewInventoryButton.setFontSize(20);
    viewInventoryButton.setOnAction(e -> new InventoryManagerPage().populateTab(tab));
    grid.add(viewInventoryButton, 1, 3, 1, 2);

    CustomButton requestInventoryButton = new CustomButton("Request Inventory");
    requestInventoryButton.maximize();
    requestInventoryButton.setFontSize(20);
    requestInventoryButton.setOnAction(e -> new RequestManagerPage().populateTab(tab));
    grid.add(requestInventoryButton, 1, 5, 1, 2);

    CustomButton seeOrdersButton = new CustomButton("View Pending Orders");
    seeOrdersButton.maximize();
    seeOrdersButton.setFontSize(20);
    seeOrdersButton.setOnAction(e -> new OrderManagerPage().populateTab(tab));
    grid.add(seeOrdersButton, 1, 7, 1, 2);

    tab.setCurrentPage(this, grid);
  }

  @Override
  public void update() {}
}
