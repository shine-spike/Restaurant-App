package GUI.manager;

import GUI.elements.CustomGridPane;
import GUI.elements.CustomPage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;

public class InventoryPage extends CustomPage{
    private Button backButton, sendButton;

    @Override
    public void populateTab(Tab tab) {
        CustomGridPane grid = new CustomGridPane(0);
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // Buttons
        backButton = new Button("Back");
        backButton.setOnAction(e -> new MangerMenuPage().populateTab(tab));

        sendButton = new Button("Send");

        grid.add(sendButton, 0, 1);
        grid.add(backButton, 1, 1);

        tab.setContent(grid);
    }

    @Override
    public void update() {}
}
