package GUI;

import javafx.scene.control.Tab;

public class CookTab extends RestaurantTab{
    /**
     * Initializes this CookTab's JavaFX tab
     */
    public void initializeTab(){
        Tab cookTab = new Tab("Cook");
        cookTab.setClosable(false);

        setTab(cookTab);
    }

    /**
     * Updates all the nodes of this tab with the appropriate new information
     */
    public void update(){

    }
}
