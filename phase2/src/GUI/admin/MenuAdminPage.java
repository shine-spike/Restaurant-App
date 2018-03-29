package GUI.admin;

import GUI.elements.*;
import controller.MenuController;
import controller.Restaurant;
import javafx.collections.FXCollections;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import util.Localizer;

import java.util.ArrayList;

public class MenuAdminPage extends CustomPage {
  private final MenuController menuController = Restaurant.getInstance().getMenuController();

  private ArrayList<String> menuList = new ArrayList<>();
  private ArrayList<String> menuItemList = new ArrayList<>();
  private final ListView<String> menuListView = new ListView<>();
  private final ListView<String> menuItemListView = new ListView<>();

  MenuAdminPage() {
    update();
  }

  @Override
  public void populateTab(CustomTab tab) {
    CustomGridPane grid = new CustomGridPane(50);
    grid.setHgap(25);
    grid.setPercentageColumns(10, 10, 10, 10, 10, 10, 10, 10, 10, 10);
    grid.setEvenRows(24);

    CustomLabel menuListLabel = new CustomLabel("Menu List");
    menuListLabel.setFontSize(20);
    menuListLabel.setBold();
    menuListLabel.center();
    grid.add(menuListLabel, 0, 0, 5, 1);

    CustomLabel menuInformationLabel = new CustomLabel("Menu Information");
    menuInformationLabel.setFontSize(20);
    menuInformationLabel.setBold();
    menuInformationLabel.center();
    grid.add(menuInformationLabel, 0, 8, 5, 1);

    CustomLabel menuLocalizedNameLabel = new CustomLabel("Localized Name");
    TextField menuLocalizedNameField = new TextField();
    grid.add(menuLocalizedNameLabel, 0, 9, 2, 1);
    grid.add(menuLocalizedNameField, 3, 9, 2, 1);

    CustomLabel menuUnlocalizedNameLabel = new CustomLabel("Unlocalized Name");
    CustomLabel menuUnlocalizedNameField = new CustomLabel();
    grid.add(menuUnlocalizedNameLabel, 0, 10, 2, 1);
    grid.add(menuUnlocalizedNameField, 3, 10, 2, 1);

    CustomLabel modificationLabel = new CustomLabel();
    modificationLabel.center();
    modificationLabel.setBold();
    grid.add(modificationLabel, 0, 13, 5, 1);

    CustomButton deleteMenuButton = new CustomButton("Delete Menu");
    deleteMenuButton.maximize();
    deleteMenuButton.setOnAction(
        e -> {
          int selectedIndex = menuListView.getSelectionModel().getSelectedIndex();
          String name = menuLocalizedNameField.getText();

          if (selectedIndex != -1 && name.length() > 0) {
            String menu = menuList.get(selectedIndex);

            menuController.removeMenu(menu);

            modificationLabel.setInfo();
            modificationLabel.setText("Menu has been deleted.");
            update();
          } else {
            modificationLabel.setWarning();
            modificationLabel.setText("Menu not selected.");
          }
        });
    grid.add(deleteMenuButton, 0, 12, 2, 1);

    CustomButton modifyMenuButton = new CustomButton("Modify Menu");
    modifyMenuButton.maximize();
    modifyMenuButton.setOnAction(
        e -> {
          int selectedIndex = menuListView.getSelectionModel().getSelectedIndex();
          String name = menuLocalizedNameField.getText();

          if (selectedIndex != -1 && name.length() > 0) {
            String menu = menuList.get(selectedIndex);

            menuController.removeMenu(menu);

            modificationLabel.setInfo();
            modificationLabel.setText("Menu has been modified.");
            update();
          } else {
            modificationLabel.setWarning();
            modificationLabel.setText(
                "Menu not selected or one or more of the above fields are empty.");
          }
        });
    grid.add(modifyMenuButton, 2, 12, 3, 1);

    menuListView
        .getSelectionModel()
        .selectedIndexProperty()
        .addListener(
            (obs, oldSelection, newSelection) -> {
              int selectionIndex = newSelection.intValue();
              if (selectionIndex != -1) {
                String menu = menuList.get(selectionIndex);

                menuUnlocalizedNameField.setText(menu);
                menuLocalizedNameField.setText(Localizer.localize(menu));

                menuItemList = menuController.getMenuItemStrings(menu);
                menuItemListView.setItems(
                    FXCollections.observableArrayList(Localizer.localize(menuItemList)));
              }
            });
    grid.add(menuListView, 0, 1, 5, 6);

    CustomLabel menuRegistrationLabel = new CustomLabel("Register Menu");
    menuRegistrationLabel.setFontSize(20);
    menuRegistrationLabel.setBold();
    menuRegistrationLabel.center();
    grid.add(menuRegistrationLabel, 0, 15, 5, 1);

    CustomLabel newMenuLocalizedNameLabel = new CustomLabel("Localized Name");
    TextField newMenuLocalizedNameField = new TextField();
    grid.add(newMenuLocalizedNameLabel, 0, 16, 2, 1);
    grid.add(newMenuLocalizedNameField, 3, 16, 2, 1);

    CustomLabel newMenuUnlocalizedNameLabel = new CustomLabel("Unlocalized Name");
    TextField newMenuUnlocalizedNameField = new TextField();
    grid.add(newMenuUnlocalizedNameLabel, 0, 17, 2, 1);
    grid.add(newMenuUnlocalizedNameField, 3, 17, 2, 1);

    CustomLabel newMenuLabel = new CustomLabel();
    newMenuLabel.center();
    newMenuLabel.setBold();
    grid.add(newMenuLabel, 0, 20, 5, 1);

    CustomButton newMenuButton = new CustomButton("Register Menu");
    newMenuButton.maximize();
    newMenuButton.setOnAction(
        e -> {
          String menu = newMenuUnlocalizedNameField.getText();
          String name = newMenuLocalizedNameField.getText();
          if (menu.length() > 0 && name.length() > 0) {
            Localizer.register(menu, name);
            menuController.addMenu(menu);

            newMenuLabel.setInfo();
            newMenuLabel.setText("Menu has been created.");
            update();
          } else {
            newMenuLabel.setWarning();
            newMenuLabel.setText("One or more of the above fields are empty.");
          }
        });
    grid.add(newMenuButton, 0, 19, 5, 1);

    CustomLabel menuItemListLabel = new CustomLabel("Menu Item List");
    menuItemListLabel.setFontSize(20);
    menuItemListLabel.setBold();
    menuItemListLabel.center();
    grid.add(menuItemListLabel, 5, 0, 5, 1);

    CustomLabel menuItemInformationLabel = new CustomLabel("Menu Item Information");
    menuItemInformationLabel.setFontSize(20);
    menuItemInformationLabel.setBold();
    menuItemInformationLabel.center();
    grid.add(menuItemInformationLabel, 5, 8, 5, 1);

    CustomLabel menuItemLocalizedNameLabel = new CustomLabel("Localized Name");
    TextField menuItemLocalizedNameField = new TextField();
    grid.add(menuItemLocalizedNameLabel, 5, 9, 2, 1);
    grid.add(menuItemLocalizedNameField, 8, 9, 2, 1);

    CustomLabel menuItemUnlocalizedNameLabel = new CustomLabel("Unlocalized Name");
    CustomLabel menuItemUnlocalizedNameField = new CustomLabel();
    grid.add(menuItemUnlocalizedNameLabel, 5, 10, 2, 1);
    grid.add(menuItemUnlocalizedNameField, 8, 10, 2, 1);

    CustomLabel menuItemPriceLabel = new CustomLabel("Price");
    TextField menuItemPriceField = new TextField();
    grid.add(menuItemPriceLabel, 5, 11, 2, 1);
    grid.add(menuItemPriceField, 8, 11, 2, 1);

    CustomLabel itemModificationLabel = new CustomLabel();
    itemModificationLabel.center();
    itemModificationLabel.setBold();
    grid.add(itemModificationLabel, 5, 13, 5, 1);

    CustomButton deleteMenuItemButton = new CustomButton("Delete Menu Item");
    deleteMenuItemButton.maximize();
    deleteMenuItemButton.setOnAction(
        e -> {
          int selectedMenuIndex = menuListView.getSelectionModel().getSelectedIndex();
          int selectedIndex = menuItemListView.getSelectionModel().getSelectedIndex();

          if (selectedIndex != -1 && selectedMenuIndex != -1) {
            String menu = menuList.get(selectedMenuIndex);
            String menuItem = menuItemList.get(selectedIndex);

            menuController.removeMenuItem(menu, menuItem);

            itemModificationLabel.setInfo();
            itemModificationLabel.setText("Menu item has been deleted.");
            update();
          } else {
            itemModificationLabel.setWarning();
            itemModificationLabel.setText("Menu or menu item not selected.");
          }
        });
    grid.add(deleteMenuItemButton, 5, 12, 2, 1);

    CustomButton modifyMenuItemButton = new CustomButton("Modify Menu Item");
    modifyMenuItemButton.maximize();
    modifyMenuItemButton.setOnAction(
        e -> {
          int selectedMenuIndex = menuListView.getSelectionModel().getSelectedIndex();
          int selectedIndex = menuItemListView.getSelectionModel().getSelectedIndex();
          String itemName = menuItemLocalizedNameField.getText();
          String price = menuItemPriceField.getText();

          if (selectedIndex != -1
              && selectedMenuIndex != -1
              && itemName.length() > 0
              && price.matches("\\d+")) {
            String menu = menuList.get(selectedMenuIndex);
            String menuItem = menuItemList.get(selectedIndex);

            new MenuItemAdminPage(menu, menuItem, Integer.parseInt(price)).populateTab(tab);
            Localizer.register(menuItem, itemName);

            itemModificationLabel.setInfo();
            itemModificationLabel.setText("Menu item has been modified.");
            update();
          } else {
            itemModificationLabel.setWarning();
            itemModificationLabel.setText(
                "Menu or menu item not selected or one or more of the above fields are invalid.");
          }
        });
    grid.add(modifyMenuItemButton, 7, 12, 3, 1);

    CustomLabel menuItemRegistrationLabel = new CustomLabel("Register Menu Item");
    menuItemRegistrationLabel.setFontSize(20);
    menuItemRegistrationLabel.setBold();
    menuItemRegistrationLabel.center();
    grid.add(menuItemRegistrationLabel, 5, 15, 5, 1);

    CustomLabel newMenuItemLocalizedNameLabel = new CustomLabel("Localized Name");
    TextField newMenuItemLocalizedNameField = new TextField();
    grid.add(newMenuItemLocalizedNameLabel, 5, 16, 2, 1);
    grid.add(newMenuItemLocalizedNameField, 8, 16, 2, 1);

    CustomLabel newMenuItemUnlocalizedNameLabel = new CustomLabel("Unlocalized Name");
    TextField newMenuItemUnlocalizedNameField = new TextField();
    grid.add(newMenuItemUnlocalizedNameLabel, 5, 17, 2, 1);
    grid.add(newMenuItemUnlocalizedNameField, 8, 17, 2, 1);

    CustomLabel newMenuItemPriceLabel = new CustomLabel("Price");
    TextField newMenuItemPriceField = new TextField();
    grid.add(newMenuItemPriceLabel, 5, 18, 2, 1);
    grid.add(newMenuItemPriceField, 8, 18, 2, 1);

    CustomLabel newMenuItemLabel = new CustomLabel();
    newMenuItemLabel.center();
    newMenuItemLabel.setBold();
    grid.add(newMenuItemLabel, 5, 20, 5, 1);

    CustomButton newMenuItemButton = new CustomButton("Register Menu Item");
    newMenuItemButton.maximize();
    newMenuItemButton.setOnAction(
        e -> {
          int selectedMenuIndex = menuListView.getSelectionModel().getSelectedIndex();
          String menuItem = newMenuItemUnlocalizedNameField.getText();
          String itemName = newMenuItemLocalizedNameField.getText();
          String price = newMenuItemPriceField.getText();

          if (selectedMenuIndex != -1
              && menuItem.length() > 0
              && itemName.length() > 0
              && price.matches("\\d+")) {
            String menu = menuList.get(selectedMenuIndex);
            Localizer.register(menuItem, itemName);

            new MenuItemAdminPage(menu, menuItem, Integer.parseInt(price)).populateTab(tab);

            newMenuItemLabel.setInfo();
            newMenuItemLabel.setText("Menu item has been created.");
            update();
          } else {
            newMenuItemLabel.setWarning();
            newMenuItemLabel.setText(
                "Menu not selected or one or more of the above fields are invalid.");
          }
        });
    grid.add(newMenuItemButton, 5, 19, 5, 1);

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
                int price = menuController.getMenuItemPrice(menu, menuItem);

                menuItemUnlocalizedNameField.setText(menuItem);
                menuItemLocalizedNameField.setText(Localizer.localize(menuItem));
                menuItemPriceField.setText(Integer.toString(price));
              }
            });

    grid.add(menuItemListView, 5, 1, 5, 6);

    if (tab.canGoBack()) {
      grid.add(getBackButton(tab), 0, 22, 10, 2);
    }

    tab.setCurrentPage(this, grid);
  }

  @Override
  public void update() {
    menuList = menuController.getMenuStrings();
    menuListView.setItems(FXCollections.observableList(Localizer.localize(menuList)));

    menuListView.refresh();
  }
}
