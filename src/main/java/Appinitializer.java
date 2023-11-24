import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Appinitializer extends Application {

    public static void main(String[] args) {

        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("Start function");
        primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("view/dashboard.fxml"))));
        primaryStage.show();
    }

}
