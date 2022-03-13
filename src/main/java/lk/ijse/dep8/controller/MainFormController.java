package lk.ijse.dep8.controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.dep8.Customer;

public class MainFormController {


    public TextField txtId;
    public TextField txtName;
    public TextField txtAddress;
    public Button btnSave;
    public TableView<Customer> tblCustomer;

    public void initialize(){
        tblCustomer.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblCustomer.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblCustomer.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));

        TableColumn<Customer,Button> lastCol =
                (TableColumn<Customer, Button>) tblCustomer.getColumns().get(3);
        lastCol.setCellValueFactory(param -> {
            Button btnDelete = new Button("Delete");
            btnDelete.setOnAction(event -> tblCustomer.getItems().remove(param.getValue()));
            return new ReadOnlyObjectWrapper<>(btnDelete);
        });

    }

    public void btnSave_ClickOnAction(ActionEvent event) {

        if ((!txtId.getText().matches("C\\d{3}"))||(tblCustomer.getItems().stream().
                anyMatch(customer -> customer.getId().equals(txtId.getText())))){
            new Alert(Alert.AlertType.ERROR,"Invalid Id", ButtonType.OK).show();
            txtId.requestFocus();
            return;
        }
        else if (txtName.getText().trim().isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Enter a Name", ButtonType.OK).show();
            txtName.requestFocus();
            return;
        }
        else if (txtAddress.getText().trim().isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Enter a Address", ButtonType.OK).show();
            txtAddress.requestFocus();
            return;
        }


            /*Streams API Usage*/

//        boolean anyMatch = tblCustomer.getItems().stream().
//                anyMatch(customerTM -> customerTM.getId().equals(txtId.getText()));
//
//
//        for (CustomerTM customer: tblCustomer.getItems()) {
//            if (customer.getId().matches(txtId.getText())){
//                txtId.requestFocus();
//                return;
//            }
//        }
        tblCustomer.getItems().
                    add(new Customer(txtId.getText(),txtName.getText(),txtAddress.getText()));
        txtId.clear();
        txtName.clear();
        txtAddress.clear();

    }
}
