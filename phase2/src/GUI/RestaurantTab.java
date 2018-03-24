package GUI;

import javafx.scene.control.Tab;

public abstract class RestaurantTab {
    // The JavaFX tab that this class represents
    private Tab tab;
    // The number of the employee using this tab
    private int employeeNumber;

    /**
     * Constructs a restaurant tab for the employee with the id employeeNumber
     */
    RestaurantTab(String tabName, int employeeNumber){
        this.employeeNumber = employeeNumber;
        initializeTab(tabName);
    }

    private void initializeTab(String tabName) {
        Tab tab = new Tab(tabName);
        tab.setClosable(false);
        this.tab = tab;
        populateTab();
    }

    /**
     * Gets this RestaurantTab's JavaFX tab
     * @return The JavaFX tab that this class represents
     */
    public Tab getTab() {
        return tab;
    }

    public int getEmployeeNumber() {
        return employeeNumber;
    }

    /**
     * Populates the tab with the nodes it should display.
     */
    public abstract void populateTab();

    /**
     * Updates all the nodes of this tab with the appropriate new information
     */
    public abstract void updateTab();
}
