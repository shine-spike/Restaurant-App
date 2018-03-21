package GUI;

import javafx.scene.control.Tab;

public class ReceiverTab extends RestaurantTab{
    /**
     * Initializes this ReceiverTab's JavaFX tab
     */
    public void initializeTab(){
        Tab receiverTab = new Tab("Receiver");
        receiverTab.setClosable(false);

        setTab(receiverTab);
    }

    /**
     * Updates all the nodes of this tab with the appropriate new information
     */
    public void update(){

    }
}
