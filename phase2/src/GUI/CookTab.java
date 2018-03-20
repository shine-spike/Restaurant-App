package GUI;

import javafx.scene.control.Tab;

public class CookTab {
    public Tab getTab(){

        Tab managerTab = new Tab("Cook");
        managerTab.setClosable(false);

        return managerTab;
    }
}
