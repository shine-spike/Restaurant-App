package GUI;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import model.Employee;
import util.Logger;

public class RestaurantApplication extends Application {
  private Stage stage;
  private Employee employee;

  @Override
  public void start(Stage stage) {
    System.setProperty("prism.lcdtext", "false");

    Driver driver = new Driver();
    driver.readConfiguration();
    Logger.startLogger();

    this.stage = stage;
    setupStage();
    startLoginScene();
    stage.show();
  }

  public void setEmployee(Employee employee) {
    this.employee = employee;
  }

  private void swapScenes(Parent newContent) {
    stage.getScene().setRoot(newContent);
  }

  private void setupStage() {
    Scene scene = new Scene(new GridPane());
    scene.getStylesheets().add(getClass().getResource("../application.css").toExternalForm());
    stage.setScene(scene);

    stage.setMaximized(true);
    stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
    stage.setTitle("Restaurant");
  }

  public void startLoginScene() {
    swapScenes(new LoginScene(this).getRoot());
  }

  public void startRestaurantScene() {
    swapScenes(new RestaurantScene(this, employee).getRoot());
  }
}
