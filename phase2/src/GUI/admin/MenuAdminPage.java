package GUI.admin;

import GUI.elements.CustomButton;
import GUI.elements.CustomGridPane;
import GUI.elements.CustomLabel;
import GUI.elements.CustomPage;
import controller.MenuController;
import controller.Restaurant;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import util.Localizer;

import java.util.ArrayList;

public class MenuAdminPage extends CustomPage {
  private MenuController menuController = Restaurant.getInstance().getMenuController();

  private ArrayList<String> menuList = new ArrayList<>();
  private ListView<String> menuListView = new ListView<>();
  private ArrayList<String> menuItemList = new ArrayList<>();
  private ListView<String> menuItemListView = new ListView<>();

  MenuAdminPage() {
    update();
  }

  @Override
  public void populateTab(Tab tab, Node previous) {
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

    CustomButton modifyMenuButton = new CustomButton("Modify Menu");
    modifyMenuButton.maximize();
    modifyMenuButton.setOnAction(
        e -> {
          int selectedIndex = menuListView.getSelectionModel().getSelectedIndex();
          if (selectedIndex != -1) {
            String menu = menuList.get(selectedIndex);
            String name = menuLocalizedNameField.getText();

            Localizer.register(menu, name);

            modificationLabel.setText("Menu has been modified.");
            update();
          }
        });
    grid.add(modifyMenuButton, 0, 12, 5, 1);

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
                newMenuLabel.setText("Menu has been created.");
                update();
              }
            });
    grid.add(newMenuButton, 0, 19, 5, 1);

    CustomLabel menuItemListLabel = new CustomLabel("Menu Item List");
    menuItemListLabel.setFontSize(20);
    menuItemListLabel.setBold();
    menuItemListLabel.center();
    grid.add(menuItemListLabel, 5, 0, 5, 1);

    menuItemListView
        .getSelectionModel()
        .selectedItemProperty()
        .addListener((obs, oldSelection, newSelection) -> {});

    grid.add(menuItemListView, 5, 1, 5, 6);

    if (previous != null) {
      grid.add(getBackButton(tab, previous), 0, 22, 10, 2);
    }

    tab.setContent(grid);
  }

  @Override
  public void update() {
    menuList = menuController.getMenuStrings();
    menuListView.setItems(FXCollections.observableList(Localizer.localize(menuList)));
  }
}
