package GUI;

import javafx.scene.control.Tab;

public class ServerTab {
    public Tab getTab(){
        Tab managerTab = new Tab("Server");
        managerTab.setClosable(false);

        return managerTab;
    }
}
