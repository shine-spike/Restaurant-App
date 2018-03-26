package GUI;

import controller.Restaurant;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

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
    userTextField.setText("Admin account"); // TODO: remove
    grid.add(userName, 0, 10);
    grid.add(userTextField, 1, 10);

    // Password area
    Label password = new Label("Password");
    passwordField = new PasswordField();
    passwordField.setText("adminaccount"); // TODO: remove
    grid.add(password, 0, 11);
    grid.add(passwordField, 1, 11);

    // Login button
    Button loginButton = new Button("Login");
    loginButton.setMinWidth(grid.getMinWidth());
    loginButton.setOnAction(e -> loginButtonPressed());
    grid.add(loginButton, 0, 18, 2, 1);

    // Incorrect password warning
    incorrectPassword = new Label();
    incorrectPassword.setMinWidth(grid.getMinWidth());
    incorrectPassword.setTextFill(Color.FIREBRICK);
    incorrectPassword.setAlignment(Pos.CENTER);
    incorrectPassword.setStyle("-fx-font-weight: bold");
    grid.add(incorrectPassword, 0, 24, 2, 1);

    return grid;
  }

  private void loginButtonPressed() {
    int employeeNumber =
        Restaurant.getInstance()
            .getEmployeeController()
            .login(userTextField.getText(), passwordField.getText());

    if (employeeNumber != -1) {
      application.setEmployeeNumber(employeeNumber);
      application.startRestaurantScene();
    } else {
      incorrectPassword.setText("Incorrect Username or Password");
    }
  }
}
