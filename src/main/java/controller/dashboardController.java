package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

public class dashboardController {


    public AnchorPane pane;
    public HBox headerPane;

    public void goToCustomer(ActionEvent actionEvent) {
        Stage stage= (Stage) pane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/Customer.fxml"))));
            stage.show();
        } catch (IOException e) {
            System.out.println("this is not working");
            throw new RuntimeException(e);
        }
    }

    public void gotoOrders(ActionEvent actionEvent) {

    }

    public void goToItems(ActionEvent actionEvent) {
        Stage stage=(Stage) pane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/item.fxml"))));
            stage.show();
        } catch (IOException e) {
            System.out.println("dashboardController goto items Error");
            throw new RuntimeException(e);
        }

    }
}
