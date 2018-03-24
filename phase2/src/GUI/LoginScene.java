package GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import controller.Restaurant;
import model.Employee;

public class LoginScene {
  private RestaurantApplication application;
  private TextField userTextField;
  private PasswordField passwordField;
  private Label incorrectPassword;

  LoginScene(RestaurantApplication application) {
    this.application = application;
  }

  public Parent getRoot() {
    GridPane grid = new GridPane();
    grid.setAlignment(Pos.CENTER);
    grid.setHgap(100);
    grid.setVgap(10);
    grid.setMinWidth(500);

    // Top header
    Label headerLabel = new Label("Employee Login");
    headerLabel.setMinWidth(grid.getMinWidth());
    headerLabel.setAlignment(Pos.CENTER);
    headerLabel.setStyle("-fx-font-size: 30px");
    grid.add(headerLabel, 0, 0, 2, 2);

    // Username area
    Label userName = new Label("Employee Name");
    userTextField = new TextField();
    grid.add(userName, 0, 10);
    grid.add(userTextField, 1, 10);

    // Password area
    Label password = new Label("Password");
    passwordField = new PasswordField();
    grid.add(password, 0, 11);
    grid.add(passwordField, 1, 11);

    // Login button
    Button button = new Button("Login");
    button.setMinWidth(grid.getMinWidth());
    button.setOnAction(new LoginButtonHandler());
    grid.add(button, 0, 18, 2, 1);

    // Incorrect password warning
    incorrectPassword = new Label();
    incorrectPassword.setMinWidth(grid.getMinWidth());
    incorrectPassword.setTextFill(Color.FIREBRICK);
    incorrectPassword.setAlignment(Pos.CENTER);
    incorrectPassword.setStyle("-fx-font-weight: bold");
    grid.add(incorrectPassword, 0, 24, 2, 1);


    return grid;
  }

  private class LoginButtonHandler implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent e) {
      Employee employee =
          Restaurant.getInstance()
              .getEmployeeController()
              .login(userTextField.getText(), passwordField.getText());
      if (employee != null) {
        application.setEmployee(employee);
        application.startRestaurantScene();
      } else {
        incorrectPassword.setText("Incorrect Username or Password");
      }
    }
  }
}
