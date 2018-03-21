package GUI;

import javafx.scene.control.Tab;

public class ServerTab extends RestaurantTab{
    /**
     * Initializes this ServerTab's JavaFX tab
     */
    public void initializeTab(){
        Tab serverTab = new Tab("Server");
        serverTab.setClosable(false);

        setTab(serverTab);
    }

    /**
     * Updates all the nodes of this tab with the appropriate new information
     */
    public void update(){

    }
}
