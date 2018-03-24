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
        int id = employee.getEmployeeNumber();

        if (employee.isManager()) {
            restaurantTabPane.getTabs().add(new ManagerTab(id).getTab());
        }
        if (employee.isServer()) {
            restaurantTabPane.getTabs().add(new ServerTab(id).getTab());
        }
        if (employee.isCook()) {
            restaurantTabPane.getTabs().add(new CookTab(id).getTab());
        }
        if (employee.isReceiver()) {
            restaurantTabPane.getTabs().add(new ReceiverTab(id).getTab());
        }

        Scene scene = new Scene(restaurantTabPane);
        scene.getStylesheets().add(getClass().getResource("../application.css").toExternalForm());
        return scene;
    }
}
