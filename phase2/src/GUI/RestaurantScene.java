package GUI;

import javafx.scene.Scene;
import javafx.scene.control.TabPane;

public class RestaurantScene {
    private RestaurantApplication application;

    public RestaurantScene(RestaurantApplication application){
        this.application = application;
    }

    public Scene getScene(){
        TabPane restaurantTabPane = new TabPane();

        restaurantTabPane.getTabs().add(new ManagerTab().getTab());
        restaurantTabPane.getTabs().add(new ServerTab().getTab());
        restaurantTabPane.getTabs().add(new CookTab().getTab());
        restaurantTabPane.getTabs().add(new ReceiverTab().getTab());

        return new Scene(restaurantTabPane);
    }
}
