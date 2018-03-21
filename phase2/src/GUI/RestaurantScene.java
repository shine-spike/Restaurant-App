package GUI;

import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import model.Employee;

public class RestaurantScene {
    private RestaurantApplication application;
    private Employee employee;

    public RestaurantScene(RestaurantApplication application, Employee employee){
        this.application = application;
        this.employee = employee;
    }

    public Scene getScene() {
        TabPane restaurantTabPane = new TabPane();

        if (employee.isManager()) {
            restaurantTabPane.getTabs().add(new ManagerTab().getTab());
        }
        if (employee.isServer()) {
            restaurantTabPane.getTabs().add(new ServerTab().getTab());
        }
        if (employee.isCook()) {
            restaurantTabPane.getTabs().add(new CookTab().getTab());
        }
        if (employee.isReceiver()) {
            restaurantTabPane.getTabs().add(new ReceiverTab().getTab());
        }

        return new Scene(restaurantTabPane);
    }
}
