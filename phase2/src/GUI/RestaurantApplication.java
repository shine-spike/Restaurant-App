package GUI;
import javafx.application.Application;
import javafx.stage.Stage;


public class RestaurantApplication extends Application{
    private Stage stage;

    @Override
    public void start(Stage stage){
        this.stage = stage;
        stage.setTitle("Restaurant");
        stage.setScene(new SigninScene(this).getScene());
        stage.show();
    }

    public void startRestaurantScene(){
        stage.setScene(new RestaurantScene(this).getScene());
    }
}
