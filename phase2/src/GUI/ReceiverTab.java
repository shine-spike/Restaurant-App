package GUI;

import javafx.scene.control.Tab;

public class ReceiverTab {
    public Tab getTab(){

        Tab managerTab = new Tab("Receiver");
        managerTab.setClosable(false);

        return managerTab;
    }
}
