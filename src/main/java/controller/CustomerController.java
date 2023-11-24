package controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;
import javafx.event.*;
import model.tm.CustomerTm;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.concurrent.Flow;
import db.*;

import static javafx.collections.FXCollections.observableArrayList;
import static javafx.collections.FXCollections.observableList;

public class CustomerController implements Initializable {
    Connection conn= DBConnection.getConnection();

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtCustId;

    @FXML
    private TextField txtSalary;

    @FXML
    private JFXButton addBtn;

    @FXML
    private TableView<CustomerTm> tblCustomer;

    @FXML
    private TableColumn colId;

    @FXML
    private TableColumn colName;

    @FXML
    private TableColumn colAddress;

    @FXML
    private TableColumn colSalary;

    @FXML
    private TableColumn colOption;

    @FXML
    private JFXButton reloadBtn;

    @FXML
    private JFXButton updateBtn;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colOption.setCellValueFactory(new PropertyValueFactory<Object, Button>("btn"));
        System.out.println("initialize method run");
        loadCustomerTable();

        tblCustomer.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            setData(newValue);
        });

    }

    private void setData(CustomerTm customer) {
        if(customer!=null) {
            txtCustId.setEditable(false);
            txtCustId.setText(customer.getId());
            txtName.setText(customer.getName());
            txtAddress.setText(customer.getAddress());
            txtSalary.setText(String.valueOf(customer.getSalary()));
        }
    }

    private void loadCustomerTable() {

        System.out.println("loadCustomertable() runline1");
        String sql = "SELECT * FROM customer";
        ObservableList<CustomerTm> tmList = FXCollections.observableArrayList();
        try {
           Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(sql);
            System.out.println("loadCustomertable() runline2");

            while (result.next()) {
                Button btn = new Button("Delete");
                CustomerTm c = new CustomerTm(
                        result.getString(1),
                        result.getString(2),
                        result.getString(3),
                        result.getDouble(4),
                        btn
                );

                btn.setOnAction(actionEvent -> {
                    deleteCustomer(c.getId());
                });
                tmList.add(c);
            }
            tblCustomer.setItems(tmList);

        } catch ( SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void deleteCustomer(String id) {
        String sql = "Delete from customer WHERE id='" + id + "'";

        try {
            Statement stmt = conn.createStatement();
            int result = stmt.executeUpdate(sql);
            if (result > 0) {
                new Alert(Alert.AlertType.INFORMATION, "CUSTOMER Deleted").show();
            }

        } catch ( SQLException e) {
            try {
                throw new SQLException(e);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        loadCustomerTable();


    }

    public void add(javafx.event.ActionEvent actionEvent) {
    }

    public void reload(javafx.event.ActionEvent actionEvent) {

        loadCustomerTable();
        clearFields();

    }

    private void clearFields() {
        txtSalary.clear();
        txtAddress.clear();
        txtName.clear();
        txtCustId.clear();
        txtCustId.setEditable(true);
    }

    public void update(javafx.event.ActionEvent actionEvent) {
        String sql="UPDATE customer SET name='" +txtName.getText()+ "',"+
                "address='"+txtAddress.getText()+"',"+
                "salary='" +txtSalary.getText()+"'"+
                "WHERE id='"+txtCustId.getText()+"'";
        try {
            Statement stmt = conn.createStatement();
            int result = stmt.executeUpdate(sql);
            if(result>0){
                new Alert(Alert.AlertType.INFORMATION,"Updated successfully");
                loadCustomerTable();
            }
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public void addButton(ActionEvent actionEvent) {

        Customer c = new Customer((txtCustId.getText()), txtName.getText(), txtAddress.getText(), Double.parseDouble(txtSalary.getText()));
        String sql = "INSERT INTO CUSTOMER (id,name,address,salary) values('" + c.getId() + "','" + c.getName() + "','" + c.getAddress() + "'," + c.getSalary() + ")";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade", "root", "root");
            Statement stmt = conn.createStatement();
            int result = stmt.executeUpdate(sql);
            if (result > 0) {
                new Alert(Alert.AlertType.INFORMATION, "CUSTOMER Added").show();
            }else{
                System.out.println("lines"+result);
            }
        } catch (SQLIntegrityConstraintViolationException ex) {
            new Alert(Alert.AlertType.ERROR, "Duplicate entry").show();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("exception at addButton");
            e.printStackTrace();
        }


        loadCustomerTable();

    }
}
