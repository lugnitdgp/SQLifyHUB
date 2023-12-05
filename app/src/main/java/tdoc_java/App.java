package tdoc_java;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {
   
   private static Stage stage;
   
    @Override
    public void start(Stage primarystage) {
          try{
          stage = primarystage;
          Parent root = FXMLLoader.load(getClass().getResource("/fxml/app.fxml"));
          Scene scene = new Scene(root);
          String css = this.getClass().getResource("/stylesheets/app.css").toExternalForm();
          scene.getStylesheets().add(css);
          primarystage.setScene(scene);
          primarystage.show();
          }
          catch(Exception e){
              e.printStackTrace();
          }
    }
    
    public void changeScene(String fxml) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource(fxml));
        stage.setScene(new Scene(pane));
    }


    public static void main(String[] args) {
        launch();
    }
}
