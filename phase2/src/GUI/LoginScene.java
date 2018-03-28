package GUI;

import GUI.elements.CustomButton;
import GUI.elements.CustomGridPane;
import GUI.elements.CustomLabel;
import controller.Restaurant;
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
    grid.setHgap(100);
    grid.setVgap(10);
    grid.setPercentageColumns(25, 50, 25);

    CustomLabel headerLabel = new CustomLabel("Employee Login");
    headerLabel.center();
    headerLabel.setFontSize(30);
    grid.add(headerLabel, 0, 0, 2, 2);

    CustomLabel userName = new CustomLabel("Employee Name");
    TextField userTextField = new TextField();
    userTextField.setText("Admin account"); // TODO: remove
    grid.add(userName, 0, 10);
    grid.add(userTextField, 1, 10);

    CustomLabel password = new CustomLabel("Password");
    PasswordField passwordField = new PasswordField();
    passwordField.setText("adminaccount"); // TODO: remove
    grid.add(password, 0, 11);
    grid.add(passwordField, 1, 11);

    CustomLabel incorrectPassword = new CustomLabel();
    incorrectPassword.setWarning();
    incorrectPassword.center();
    incorrectPassword.setBold();
    grid.add(incorrectPassword, 0, 24, 2, 1);

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
    grid.add(loginButton, 0, 18, 2, 5);

    return grid;
  }
}
