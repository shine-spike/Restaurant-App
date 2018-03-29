package GUI;

import GUI.elements.CustomButton;
import GUI.elements.CustomGridPane;
import GUI.elements.CustomLabel;
import controller.Restaurant;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

class LoginScene {
  private final RestaurantApplication application;

  LoginScene(RestaurantApplication application) {
    this.application = application;
  }

  public Parent getRoot() {
    CustomGridPane grid = new CustomGridPane();
    grid.setAlignment(Pos.CENTER);
    grid.setHgap(100);
    grid.setVgap(10);
    grid.setPercentageColumns(15, 35, 35, 15);

    CustomLabel headerLabel = new CustomLabel("Employee Login");
    headerLabel.center();
    headerLabel.setFontSize(30);
    grid.add(headerLabel, 1, 0, 2, 2);

    CustomLabel userName = new CustomLabel("Employee Name");
    TextField userTextField = new TextField();
    userTextField.setText("Admin account"); // TODO: remove
    grid.add(userName, 1, 10);
    grid.add(userTextField, 2, 10);

    CustomLabel password = new CustomLabel("Password");
    PasswordField passwordField = new PasswordField();
    passwordField.setText("adminaccount"); // TODO: remove
    grid.add(password, 1, 11);
    grid.add(passwordField, 2, 11);

    CustomLabel incorrectPassword = new CustomLabel();
    incorrectPassword.setWarning();
    incorrectPassword.center();
    incorrectPassword.setBold();
    grid.add(incorrectPassword, 1, 24, 2, 1);

    CustomButton loginButton = new CustomButton("Login");
    loginButton.maximize();
    loginButton.setOnAction(
        e -> {
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
        });
    grid.add(loginButton, 1, 18, 2, 5);

    return grid;
  }
}
