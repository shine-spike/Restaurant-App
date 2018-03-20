package GUI;

import javafx.scene.control.Tab;

public class ManagerTab {
    public Tab getTab(){

        Tab managerTab = new Tab("Manager");
        managerTab.setClosable(false);

        return managerTab;
    }
}
