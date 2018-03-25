package GUI.manager;

import GUI.elements.CustomGridPane;
import GUI.elements.CustomPage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.layout.GridPane;

public class MangerMenuPage extends CustomPage {
    private Button inventoryButton, itemRequestsButton;

    @Override
    public void populateTab(Tab tab) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));


        // Panel Selection Button
        inventoryButton = new Button("Inventory");
        inventoryButton.setMinSize(200, 100);
        inventoryButton.setOnAction(e -> new InventoryPage().populateTab(tab));

        itemRequestsButton = new Button("Request Ingredients");
        itemRequestsButton.setMinSize(200, 100);
        itemRequestsButton.setOnAction(e -> new ItemRequestsPage().populateTab(tab));


        grid.add(inventoryButton, 0, 0);
        grid.add(itemRequestsButton, 0, 1);

        tab.setContent(grid);
    }

    @Override
    public void update() {}
}
