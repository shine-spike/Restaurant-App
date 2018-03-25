package GUI;

import GUI.elements.CustomButton;
import GUI.elements.CustomGridPane;
import controller.Restaurant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Employee;
import model.EmployeeType;

import java.util.Arrays;

public class AdminTab extends RestaurantTab {
  AdminTab(int employeeNumber) {
    super("Admin", employeeNumber);
  }

  public void populateTab() {
    CustomGridPane grid = new CustomGridPane(25);
    grid.setPercentageColumns(25, 50, 25);
    grid.setVgap(50);

    CustomButton manageEmployeesButton = new CustomButton("Manage Employees", true, 20);
    manageEmployeesButton.setOnAction(e -> manageEmployees());
    grid.add(manageEmployeesButton, 1, 0);

    CustomButton manageMenusButton = new CustomButton("Manage Menus", true, 20);
    manageMenusButton.setOnAction(e -> manageMenus());
    grid.add(manageMenusButton, 1, 1);

    getTab().setContent(grid);
  }

  private void manageEmployees() {
    CustomGridPane grid = new CustomGridPane(50);
    grid.setHgap(25);
    grid.setVgap(25);
    grid.setPercentageColumns(50, 10, 10, 10, 10, 10);

    Label employeeNumberLabel = new Label("Employee Number");
    Label employeeNumberField = new Label();
    grid.add(employeeNumberLabel, 1, 0);
    grid.add(employeeNumberField, 2, 0);
    Label employeeNameLabel = new Label("Employee Name");
    Label employeeNameField = new Label();
    grid.add(employeeNameLabel, 4, 0);
    grid.add(employeeNameField, 5, 0);

    Label firstNameLabel = new Label("First Name");
    TextField firstNameField = new TextField();
    grid.add(firstNameLabel, 1, 1, 2, 1);
    grid.add(firstNameField, 4, 1, 2, 1);

    Label lastNameLabel = new Label("Last Name");
    TextField lastNameField = new TextField();
    grid.add(lastNameLabel, 1, 2, 2, 1);
    grid.add(lastNameField, 4, 2, 2, 1);

    Label employeeTypeLabel = new Label("Employee Type");
    ObservableList<EmployeeType> employeeTypeObservableList = FXCollections.observableList(Arrays.asList(EmployeeType.values()));
    ChoiceBox<EmployeeType> employeeTypeField = new ChoiceBox<>(employeeTypeObservableList);
    grid.add(employeeTypeLabel, 1, 3, 2, 1);
    grid.add(employeeTypeField, 4, 3, 2, 1);

    ObservableList<Employee> employeeObservableList = FXCollections.observableList(Restaurant.getInstance().getEmployeeController().getEmployees());
    ListView<Employee> employeeListView = new ListView<>(employeeObservableList);
    employeeListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
      if (newSelection != null) {
        employeeNameField.setText(newSelection.getFullName());
        employeeNumberField.setText(Integer.toString(newSelection.getEmployeeNumber()));
        firstNameField.setText(newSelection.getName()[0]);
        lastNameField.setText(newSelection.getName()[1]);
        employeeTypeField.setValue(newSelection.getEmployeeType());
      }
    });
    grid.add(employeeListView, 0, 0, 1, 10);

    CustomButton modifyButton = new CustomButton("Modify", true);
    modifyButton.setOnAction(e -> {
      Employee employee = employeeListView.getSelectionModel().getSelectedItem();
      if (employee != null) {
        employee.setName(firstNameField.getText(), lastNameField.getText());
        employee.setEmployeeType(employeeTypeField.getSelectionModel().getSelectedItem());
        employeeListView.refresh();
      }
    });
    grid.add(modifyButton, 1, 4, 5, 1);

    getTab().setContent(grid);
  }

  private void manageMenus() {

  }

  /** Updates all the nodes of this tab with the appropriate new information */
  public void updateTab() {}
}
