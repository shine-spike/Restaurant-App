package GUI.server;

import GUI.elements.*;
import controller.MenuController;
import controller.OrderController;
import controller.Restaurant;
import javafx.collections.FXCollections;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import util.Localizer;

import java.util.ArrayList;
import java.util.HashMap;

public class OrderServerPage extends CustomPage {
  private final MenuController menuController = Restaurant.getInstance().getMenuController();
  private final OrderController orderController = Restaurant.getInstance().getOrderController();

  private final int tableNumber;
  private final int customerIndex;

  private ArrayList<String> menuList = new ArrayList<>();
  private ArrayList<String> menuItemList = new ArrayList<>();
  private final ListView<String> menuListView = new ListView<>();
  private final ListView<String> menuItemListView = new ListView<>();

  OrderServerPage(int tableNumber, int customerIndex) {
    this.tableNumber = tableNumber;
    this.customerIndex = customerIndex;

    update();
  }

  @Override
  public void populateTab(CustomTab tab) {
    CustomGridPane grid = new CustomGridPane(50);
    grid.setHgap(25);
    grid.setPercentageColumns(50, 50);
    grid.setEvenRows(24);

    CustomLabel menuListLabel = new CustomLabel("Menu List");
    menuListLabel.setFontSize(20);
    menuListLabel.setBold();
    menuListLabel.center();
    grid.add(menuListLabel, 0, 0);

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
    grid.add(menuListView, 0, 1, 1, 5);

    CustomLabel menuItemListLabel = new CustomLabel("Menu Item List");
    menuItemListLabel.setFontSize(20);
    menuItemListLabel.setBold();
    menuItemListLabel.center();
    grid.add(menuItemListLabel, 1, 0, 1, 1);

    ScrollPane ingredientsPane = new ScrollPane();
    ingredientsPane.setFitToWidth(true);
    grid.add(ingredientsPane, 0, 7, 2, 13);

    HashMap<String, Integer> ingredientAmountChanges = new HashMap<>();
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

                CustomGridPane ingredientsGrid = new CustomGridPane(10);
                ingredientsGrid.setHgap(10);
                ingredientsGrid.setVgap(10);
                ingredientsGrid.setPercentageColumns(5, 20, 5, 20, 5, 20, 5, 20);
                int numColumns = 8;
                int counter = 0;

                HashMap<String, Integer> ingredientAmounts =
                    menuController.getIngredientStrings(menu, menuItem);
                for (String ingredient : ingredientAmounts.keySet()) {
                  int initialAmount = ingredientAmounts.get(ingredient);

                  Spinner<Integer> ingredientAmountSpinner = new Spinner<>(0, 10, initialAmount);
                  ingredientAmountSpinner
                      .valueProperty()
                      .addListener(
                          (observable, oldValue, newValue) ->
                              ingredientAmountChanges.put(ingredient, newValue - initialAmount));
                  CustomLabel ingredientName = new CustomLabel(Localizer.localize(ingredient));

                  int column = counter % numColumns;
                  int row = counter / numColumns;
                  ingredientsGrid.add(ingredientAmountSpinner, column, row);
                  ingredientsGrid.add(ingredientName, column + 1, row);

                  counter += 2;
                }

                ingredientsPane.setContent(ingredientsGrid);
              }
            });
    grid.add(menuItemListView, 1, 1, 1, 5);

    CustomLabel placeOrderLabel = new CustomLabel();
    placeOrderLabel.setWarning();
    placeOrderLabel.setBold();
    placeOrderLabel.center();
    grid.add(placeOrderLabel, 0, 24, 2, 1);

    CustomButton discardOrderButton = new CustomButton("Discard");
    discardOrderButton.maximize();
    discardOrderButton.setOnAction(e -> tab.goBack());
    grid.add(discardOrderButton, 0, 21, 1, 2);

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
                tableNumber,
                customerIndex,
                menu,
                menuItem,
                ingredientAmountChanges)) {
              placeOrderLabel.setText("Order cannot be satisfied.");
            } else {
              tab.goBack();
            }
          }
        });
    grid.add(createOrderButton, 1, 21, 1, 2);

    tab.setCurrentPage(this, grid);
  }

  @Override
  public void update() {
    menuList = menuController.getMenuStrings();
    menuListView.setItems(FXCollections.observableList(Localizer.localize(menuList)));

    menuListView.refresh();
  }
}
