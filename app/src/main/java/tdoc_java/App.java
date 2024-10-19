package tdoc_java;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
   
   private static Stage stage;
   
    @Override
    public void start(Stage primarystage) {
          try{
          stage = primarystage;
          stage.setTitle("SQLifyHUB");
          Parent root = FXMLLoader.load(getClass().getResource("/fxml/app.fxml"));
          Scene scene = new Scene(root);
          stage.setMinWidth(1400);
          stage.setMinHeight(800);
          stage.setResizable(true);
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
        stage.setScene(new Scene(pane, stage.getWidth(), stage.getHeight()));
    }


    public static void main(String[] args) {
        launch();
    }
}
