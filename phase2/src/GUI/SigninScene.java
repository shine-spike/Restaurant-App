package GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import controller.Restaurant;

public class SigninScene {
    private RestaurantApplication application;
    private TextField userTextField;
    private PasswordField passwordField;
    private Text incorrectPassword;

    public SigninScene(RestaurantApplication application){
        this.application = application;
    }

    public Scene getScene(){
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // Username
        Label userName = new Label("Employee Name");
        userTextField = new TextField();

        // Password
        Label password = new Label("Password");
        passwordField = new PasswordField();

        // Incorrect Password Text
        incorrectPassword = new Text();


        // Button
        Button button = new Button("Sign in");

        button.setOnAction(new SignInButtonHandler());

        grid.add(userName,0, 0);
        grid.add(userTextField, 1, 0);
        grid.add(password, 0, 1);
        grid.add(passwordField, 1, 1);
        grid.add(button, 0, 4, 2, 1);
        grid.add(incorrectPassword, 0, 5, 2, 1);

        return new Scene(grid, 300, 275);
    }

    private class SignInButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            model.Employee employee = Restaurant.getInstance().getEmployeeController().getEmployee(userTextField.getText());
            if(employee != null && employee.checkPassword(passwordField.getText())) {
                application.startRestaurantScene(employee);
            } else {
                incorrectPassword.setFill(Color.FIREBRICK);
                incorrectPassword.setText("Incorrect Username or Password");
            }
        }
    }
}
