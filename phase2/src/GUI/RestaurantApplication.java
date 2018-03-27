package GUI;

import controller.Restaurant;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import util.Localizer;
import util.Logger;

public class RestaurantApplication extends Application {
  private Stage stage;
  private int employeeNumber;

  @Override
  public void start(Stage stage) {
    Logger.startLogger();

    Localizer.parseLocale();
    Restaurant.getInstance().retrieveModel();

    System.setProperty("prism.lcdtext", "false");

    this.stage = stage;
    setupStage();

    startLoginScene();
    stage.show();
  }

  @Override
  public void stop() throws Exception {
    Restaurant.getInstance().storeModel();
    Localizer.storeLocale();
    super.stop();
  }

  public Stage getStage() {
    return stage;
  }

  public void setEmployeeNumber(int employeeNumber) {
    this.employeeNumber = employeeNumber;
  }

  private void swapScenes(Parent newContent) {
    stage.getScene().setRoot(newContent);
  }

  private void setupStage() {
    Scene scene = new Scene(new GridPane());
    stage.setScene(scene);

    stage.setFullScreen(true);
    stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
    stage.setTitle("Restaurant");
  }

  public void startLoginScene() {
    swapScenes(new LoginScene(this).getRoot());
  }

  public void startRestaurantScene() {
    swapScenes(new RestaurantScene(this, employeeNumber).getRoot());
  }
}
