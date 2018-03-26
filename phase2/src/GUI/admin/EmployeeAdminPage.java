package GUI.admin;

import GUI.elements.CustomButton;
import GUI.elements.CustomGridPane;
import GUI.elements.CustomLabel;
import GUI.elements.CustomPage;
import controller.EmployeeController;
import controller.Restaurant;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.*;

import java.util.Arrays;

public class EmployeeAdminPage extends CustomPage {
  private EmployeeController employeeController = Restaurant.getInstance().getEmployeeController();

  private ChoiceBox<String> employeeTypeField = new ChoiceBox<>();
  private ChoiceBox<String> newEmployeeTypeField = new ChoiceBox<>();
  private ListView<String> employeeListView = new ListView<>();

  EmployeeAdminPage() {
    update();
  }

  @Override
  public void populateTab(Tab tab, Node previous) {
    CustomGridPane grid = new CustomGridPane(50);
    grid.setHgap(25);
    grid.setVgap(25);
    grid.setPercentageColumns(50, 10, 10, 10, 10, 10);

    CustomLabel employeeInformationLabel = new CustomLabel("Employee Information");
    employeeInformationLabel.setFontSize(20);
    employeeInformationLabel.setBold();
    employeeInformationLabel.center();
    grid.add(employeeInformationLabel, 1, 0, 5, 1);

    CustomLabel employeeNumberLabel = new CustomLabel("Employee Number");
    CustomLabel employeeNumberField = new CustomLabel();
    grid.add(employeeNumberLabel, 1, 1, 2, 1);
    grid.add(employeeNumberField, 4, 1, 2, 1);

    CustomLabel employeeNameLabel = new CustomLabel("Employee Name");
    CustomLabel employeeNameField = new CustomLabel();
    grid.add(employeeNameLabel, 1, 2, 2, 1);
    grid.add(employeeNameField, 4, 2, 2, 1);

    CustomLabel firstNameLabel = new CustomLabel("First Name");
    TextField firstNameField = new TextField();
    grid.add(firstNameLabel, 1, 3, 2, 1);
    grid.add(firstNameField, 4, 3, 2, 1);

    CustomLabel lastNameLabel = new CustomLabel("Last Name");
    TextField lastNameField = new TextField();
    grid.add(lastNameLabel, 1, 4, 2, 1);
    grid.add(lastNameField, 4, 4, 2, 1);

    CustomLabel passwordLabel = new CustomLabel("New Password");
    PasswordField passwordField = new PasswordField();
    grid.add(passwordLabel, 1, 5, 2, 1);
    grid.add(passwordField, 4, 5, 2, 1);

    CustomLabel employeeTypeLabel = new CustomLabel("Employee Type");
    grid.add(employeeTypeLabel, 1, 6, 2, 1);
    grid.add(employeeTypeField, 4, 6, 2, 1);

    CustomLabel modificationLabel = new CustomLabel();
    modificationLabel.center();
    modificationLabel.setBold();
    grid.add(modificationLabel, 1, 10, 5, 1);

    CustomButton modifyButton = new CustomButton("Modify");
    modifyButton.maximize();
    modifyButton.setOnAction(
        e -> {
          int employeeNumber = employeeListView.getSelectionModel().getSelectedIndex();
          if (employeeNumber != -1) {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String password = passwordField.getText();
            String employeeType = employeeTypeField.getSelectionModel().getSelectedItem();

            employeeController.updateEmployeeName(employeeNumber, firstName, lastName);
            employeeController.updateEmployeeType(employeeNumber, employeeType);
            if (password.length() > 0) {
              employeeController.updateEmployeePassword(employeeNumber, password);
            }

            modificationLabel.setText("Employee has been modified.");
            update();
          }
        });
    grid.add(modifyButton, 1, 7, 5, 3);

    CustomLabel newEmployeeLabel = new CustomLabel("New Employee Registration");
    newEmployeeLabel.setFontSize(20);
    newEmployeeLabel.setBold();
    newEmployeeLabel.center();
    grid.add(newEmployeeLabel, 1, 12, 5, 1);

    CustomLabel newFirstNameLabel = new CustomLabel("First Name");
    TextField newFirstNameField = new TextField();
    grid.add(newFirstNameLabel, 1, 13, 2, 1);
    grid.add(newFirstNameField, 4, 13, 2, 1);

    CustomLabel newLastNameLabel = new CustomLabel("Last Name");
    TextField newLastNameField = new TextField();
    grid.add(newLastNameLabel, 1, 14, 2, 1);
    grid.add(newLastNameField, 4, 14, 2, 1);

    CustomLabel newPasswordLabel = new CustomLabel("Password");
    PasswordField newPasswordField = new PasswordField();
    grid.add(newPasswordLabel, 1, 15, 2, 1);
    grid.add(newPasswordField, 4, 15, 2, 1);

    CustomLabel newEmployeeTypeLabel = new CustomLabel("Employee Type");
    grid.add(newEmployeeTypeLabel, 1, 16, 2, 1);
    grid.add(newEmployeeTypeField, 4, 16, 2, 1);

    CustomLabel additionLabel = new CustomLabel();
    additionLabel.center();
    additionLabel.setBold();
    grid.add(additionLabel, 1, 20, 5, 1);

    CustomButton addButton = new CustomButton("Register");
    addButton.maximize();
    addButton.setOnAction(
        e -> {
          String newFirstName = newFirstNameField.getText();
          String newLastName = newLastNameField.getText();
          String newPassword = newPasswordField.getText();
          String newEmployeeType = newEmployeeTypeField.getSelectionModel().getSelectedItem();

          if (newFirstName.length() > 0
              && newLastName.length() > 0
              && newPassword.length() > 0
              && newEmployeeType != null) {
            employeeController.registerEmployee(
                newFirstName, newLastName, newPassword, newEmployeeType);
            additionLabel.setInfo();
            additionLabel.setText("New employee has been registered.");
          } else {
            additionLabel.setWarning();
            additionLabel.setText("One or more of the above fields are empty.");
          }

          update();
        });
    grid.add(addButton, 1, 17, 5, 3);

    CustomLabel employeeListLabel = new CustomLabel("Employee List");
    employeeListLabel.setFontSize(20);
    employeeListLabel.setBold();
    employeeListLabel.center();
    grid.add(employeeListLabel, 0, 0, 1, 1);

    employeeListView
        .getSelectionModel()
        .selectedIndexProperty()
        .addListener(
            (obs, oldSelection, newSelection) -> {
              if (newSelection.intValue() != -1) {
                int employeeNumber = newSelection.intValue();
                String[] employee = employeeController.getEmployeeInformation(employeeNumber);

                employeeNameField.setText(employee[0] + " " + employee[1]);
                employeeNumberField.setText(Integer.toString(employeeNumber));
                firstNameField.setText(employee[0]);
                lastNameField.setText(employee[1]);
                newPasswordField.setText("");
                employeeTypeField.setValue(employee[2]);

                if (newSelection.intValue() != oldSelection.intValue()) {
                  modificationLabel.setText("");
                }
              }
            });
    grid.add(employeeListView, 0, 1, 1, 20);

    if (previous != null) {
      grid.add(getBackButton(tab, previous), 0, 21, 6, 3);
    }

    tab.setContent(grid);
  }

  @Override
  public void update() {
    String[] employeeTypeStrings = employeeController.getEmployeeTypeStrings();

    employeeTypeField.setItems(FXCollections.observableList(Arrays.asList(employeeTypeStrings)));
    newEmployeeTypeField.setItems(FXCollections.observableList(Arrays.asList(employeeTypeStrings)));
    employeeListView.setItems(
        FXCollections.observableArrayList(employeeController.getEmployeeStrings()));

    employeeListView.refresh();
  }
}
