package tdoc_java;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {
   public static String getConnection() {
      Connection connection = null;
      try {
         Class.forName("org.postgresql.Driver");
         
         connection = DriverManager
            .getConnection("jdbc:postgresql://localhost:5432/tdoc_db","postgres", "8187");
         
      } catch (ClassNotFoundException | SQLException e) {
          
         System.err.println(e.getClass().getName()+": "+e.getMessage());
         System.exit(0);
         
      }
      
      return "Opened database successfully";
   }
   
    @Override
    public void start(Stage stage) {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        Label l = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        Scene scene = new Scene(new StackPane(l), 640, 480);
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        System.out.println(App.getConnection());
        launch();
    }
}
