package GUI;

import javafx.scene.control.Tab;

public class ManagerTab extends RestaurantTab{
    /**
     * Initializes this ManagerTab's JavaFX tab
     */
    public void initializeTab(){
        Tab managerTab = new Tab("Manager");
        managerTab.setClosable(false);

        setTab(managerTab);
    }

    /**
     * Updates all the nodes of this tab with the appropriate new information
     */
    public void update(){

    }
}
