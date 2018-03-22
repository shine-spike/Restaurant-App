package GUI;

import event.ReceiveEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import model.Ingredient;
import model.Restaurant;

public class ServerTab extends RestaurantTab{
    /**
     * Initializes this ServerTab's JavaFX tab
     */
    public void initializeTab(){
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Tab serverTab = new Tab("Server", grid);
        serverTab.setClosable(false);

        setTab(serverTab);
    }

    /**
     * Updates all the nodes of this tab with the appropriate new information
     */
    public void update(){

    }

    private class ServerTabHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {

        }
    }
}
