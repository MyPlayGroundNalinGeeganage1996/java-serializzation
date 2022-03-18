package lk.ijse.dep8.controller;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import lk.ijse.dep8.Customer;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class MainFormController {


    public TextField txtId;
    public TextField txtName;
    public TextField txtAddress;
    public Button btnSave;
    public TableView<Customer> tblCustomer;
    private final Path dbPath = Paths.get("database/customers.dep8db");
    public TextField txtPicture;
    public Button btnBrowse;
    public ImageView img1;
    private File file;

    public void initialize(){
        tblCustomer.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblCustomer.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblCustomer.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));

        TableColumn<Customer, ImageView> colPicture = (TableColumn<Customer, ImageView>) tblCustomer.
                getColumns().get(3);
        colPicture.setCellValueFactory(param -> {
            byte[] picture = param.getValue().getPicture();
            ByteArrayInputStream bis = new ByteArrayInputStream(picture);

            ImageView im = new ImageView(new Image(bis));
            im.setFitHeight(75);
            im.setFitWidth(75);
            return new ReadOnlyObjectWrapper<>(im);
        });


        TableColumn<Customer,Button> lastCol =
                (TableColumn<Customer, Button>) tblCustomer.getColumns().get(4);
        lastCol.setCellValueFactory(param -> {
            Button btnDelete = new Button("Delete");
            btnDelete.setOnAction(event -> tblCustomer.getItems().remove(param.getValue()));
            return new ReadOnlyObjectWrapper<>(btnDelete);
        });

        initDatabase();

        tblCustomer.getSelectionModel().selectedItemProperty().addListener(observable -> {
            btnSave.setText("Update");
            txtId.setText(tblCustomer.getSelectionModel().getSelectedItem().getId());
            txtName.setText(tblCustomer.getSelectionModel().getSelectedItem().getName());
            txtAddress.setText(tblCustomer.getSelectionModel().getSelectedItem().getAddress());
            txtPicture.setText(tblCustomer.getSelectionModel().getSelectedItem().getPicPath());

        });


    }

    public void btnSave_ClickOnAction(ActionEvent event) throws IOException {
        if (btnSave.getText().equals("Save")) {

            if ((!txtId.getText().matches("C\\d{3}")) || (tblCustomer.getItems().stream().
                    anyMatch(customer -> customer.getId().equals(txtId.getText())))) {
                new Alert(Alert.AlertType.ERROR, "Invalid Id", ButtonType.OK).show();
                txtId.requestFocus();
                return;
            } else if (txtName.getText().trim().isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "Enter a Name", ButtonType.OK).show();
                txtName.requestFocus();
                return;
            } else if (txtAddress.getText().trim().isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "Enter a Address", ButtonType.OK).show();
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
            byte[] picture = Files.readAllBytes(Paths.get(txtPicture.getText()));

            Customer newCustomer = new Customer(
                    txtId.getText(),
                    txtName.getText(),
                    txtAddress.getText(),
                    picture,
                    txtPicture.getText());
            tblCustomer.getItems().add(newCustomer);
            boolean result = saveCustomers();

            if (!result) {
                new Alert(Alert.AlertType.ERROR, "Failed to save the customer, try again").show();
                tblCustomer.getItems().remove(newCustomer);
            } else {
                txtId.clear();
                txtName.clear();
                txtAddress.clear();
                txtPicture.clear();
                img1.setImage(null);
            }

            txtId.requestFocus();
        }
        else{
            /*TODO: Updating part*/

        }

    }
    private void initDatabase() {
        try {

            if (!Files.exists(dbPath)) {
                Files.createDirectories(dbPath.getParent());
                Files.createFile(dbPath);
            }

            loadAllCustomers();

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to initialize the database").showAndWait();
            Platform.exit();
        }
    }

    private boolean saveCustomers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(dbPath, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING))) {
            oos.writeObject(new ArrayList<Customer>(tblCustomer.getItems()));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void loadAllCustomers() {
        try (InputStream is = Files.newInputStream(dbPath, StandardOpenOption.READ);
             ObjectInputStream ois = new ObjectInputStream(is)) {
            tblCustomer.getItems().clear();
            tblCustomer.setItems(FXCollections.observableArrayList((ArrayList<Customer>) ois.readObject()));
        } catch (IOException | ClassNotFoundException e) {
            if (!(e instanceof EOFException)) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Failed to load customers").showAndWait();
            }
        }
    }

    public void btnBrowse_OnAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("select your profile picture");
        fileChooser.getExtensionFilters().add(new FileChooser.
                ExtensionFilter("Images","*.jpeg","*.jpg","*.gif","*.png","bmp"));
        file = fileChooser.showOpenDialog(btnSave.getScene().getWindow());

        if (file != null){
            txtPicture.setText(file.getAbsolutePath());
            try {
                img1.setImage(new Image(String.valueOf(file.toURI().toURL())));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

        }
    }
}