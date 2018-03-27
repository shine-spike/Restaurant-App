package GUI.server;

import GUI.elements.*;
import controller.MenuController;
import controller.OrderController;
import controller.Restaurant;
import javafx.collections.FXCollections;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import util.Localizer;

import java.util.ArrayList;
import java.util.HashMap;

public class OrderServerPage extends CustomPage {
  private MenuController menuController = Restaurant.getInstance().getMenuController();
  private OrderController orderController = Restaurant.getInstance().getOrderController();

  private int tableNumber;
  private int customerIndex;

  private ArrayList<String> menuList = new ArrayList<>();
  private ListView<String> menuListView = new ListView<>();
  private ArrayList<String> menuItemList = new ArrayList<>();
  private ListView<String> menuItemListView = new ListView<>();

  OrderServerPage(int tableNumber, int customerIndex) {
    this.tableNumber = tableNumber;
    this.customerIndex = customerIndex;

    update();
  }

  @Override
  public void populateTab(CustomTab tab) {
    CustomGridPane grid = new CustomGridPane(50);
    grid.setHgap(25);
    grid.setPercentageColumns(25, 25, 25, 25);
    grid.setEvenRows(24);

    CustomLabel menuListLabel = new CustomLabel("Menu List");
    menuListLabel.setFontSize(20);
    menuListLabel.setBold();
    menuListLabel.center();
    grid.add(menuListLabel, 0, 0, 2, 1);

    menuListView
        .getSelectionModel()
        .selectedIndexProperty()
        .addListener(
            (obs, oldSelection, newSelection) -> {
              int selectionIndex = newSelection.intValue();
              if (selectionIndex != -1) {
                String menu = menuList.get(selectionIndex);

                menuItemList = menuController.getMenuItemStrings(menu);
                menuItemListView.setItems(
                    FXCollections.observableArrayList(Localizer.localize(menuItemList)));
              }
            });
    grid.add(menuListView, 0, 1, 2, 5);

    CustomLabel menuItemListLabel = new CustomLabel("Menu Item List");
    menuItemListLabel.setFontSize(20);
    menuItemListLabel.setBold();
    menuItemListLabel.center();
    grid.add(menuItemListLabel, 2, 0, 2, 1);

    menuItemListView
        .getSelectionModel()
        .selectedIndexProperty()
        .addListener(
            (obs, oldSelection, newSelection) -> {
              int selectionIndex = newSelection.intValue();
              int selectedMenuIndex = menuListView.getSelectionModel().getSelectedIndex();

              if (selectionIndex != -1 && selectedMenuIndex != -1) {
                String menuItem = menuItemList.get(selectionIndex);
                String menu = menuList.get(selectedMenuIndex);
              }
            });
    grid.add(menuItemListView, 2, 1, 2, 5);

    HashMap<String, Integer> ingredientChangeAmounts = new HashMap<>();
    Spinner<Integer> ingredientAmountSpinner = new Spinner<>(0, 10, 0);

    CustomLabel placeOrderLabel = new CustomLabel();
    placeOrderLabel.setWarning();
    placeOrderLabel.setBold();
    placeOrderLabel.center();
    grid.add(placeOrderLabel, 0, 23, 4, 1);

    CustomButton discardOrderButton = new CustomButton("Discard");
    discardOrderButton.maximize();
    discardOrderButton.setOnAction(e -> tab.goBack());
    grid.add(discardOrderButton, 0, 20, 2, 2);

    CustomButton createOrderButton = new CustomButton("Create");
    createOrderButton.maximize();
    createOrderButton.setOnAction(
        e -> {
          int selectedMenuIndex = menuListView.getSelectionModel().getSelectedIndex();
          int selectedMenuItemIndex = menuItemListView.getSelectionModel().getSelectedIndex();

          if (selectedMenuIndex != -1 && selectedMenuItemIndex != -1) {
            String menu = menuList.get(selectedMenuIndex);
            String menuItem = menuItemList.get(selectedMenuItemIndex);

            if (!orderController.createOrder(
                tab.getEmployeeNumber(),
                tableNumber,
                customerIndex,
                menu,
                menuItem,
                ingredientChangeAmounts)) {
              placeOrderLabel.setText("Order cannot be satisfied.");
            } else {
              tab.goBack();
            }
          }
        });
    grid.add(createOrderButton, 2, 20, 2, 2);

    tab.setCurrentPage(this, grid);
  }

  @Override
  public void update() {
    menuList = menuController.getMenuStrings();
    menuListView.setItems(FXCollections.observableList(Localizer.localize(menuList)));

    menuListView.refresh();
  }
}
