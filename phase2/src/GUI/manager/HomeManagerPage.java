package GUI.manager;

import GUI.elements.*;
import controller.Restaurant;
import controller.TableController;

public class HomeManagerPage extends CustomPage {
  private final TableController tableController = Restaurant.getInstance().getTableController();

  private CustomLabel totalPayments = new CustomLabel();

  public HomeManagerPage() {
    update();
  }

  @Override
  public void populateTab(CustomTab tab) {
    CustomGridPane grid = new CustomGridPane(0);
    grid.setPercentageRows(10);
    grid.setPercentageColumns(25, 25, 25, 25);
    grid.setVgap(50);

    CustomLabel headerLabel = new CustomLabel("Management");
    headerLabel.setFontSize(30);
    headerLabel.center();
    headerLabel.setBold();
    grid.add(headerLabel, 0, 1, 4, 1);

    CustomButton viewInventoryButton = new CustomButton("View Inventory");
    viewInventoryButton.maximize();
    viewInventoryButton.setFontSize(20);
    viewInventoryButton.setOnAction(e -> new InventoryManagerPage().populateTab(tab));
    grid.add(viewInventoryButton, 1, 3, 2, 2);

    CustomButton requestInventoryButton = new CustomButton("Request Inventory");
    requestInventoryButton.maximize();
    requestInventoryButton.setFontSize(20);
    requestInventoryButton.setOnAction(e -> new RequestManagerPage().populateTab(tab));
    grid.add(requestInventoryButton, 1, 5, 2, 2);

    CustomButton seeOrdersButton = new CustomButton("View Pending Orders");
    seeOrdersButton.maximize();
    seeOrdersButton.setFontSize(20);
    seeOrdersButton.setOnAction(e -> new OrderManagerPage().populateTab(tab));
    grid.add(seeOrdersButton, 1, 7, 2, 2);

    CustomLabel paymentsLabel = new CustomLabel("Payments: ");
    grid.add(paymentsLabel, 1, 9, 1, 1);

    grid.add(totalPayments, 2, 9, 1, 1);

    tab.setCurrentPage(this, grid);
  }

  @Override
  public void update() {
    totalPayments.setText(String.format("$%.2f", tableController.getTotalPayments() / 100.0));
  }
}
