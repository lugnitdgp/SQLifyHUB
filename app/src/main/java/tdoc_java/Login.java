/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tdoc_java;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Login {

    boolean createTable = false;

    public static Connection connection;
    
    @FXML
    private Button connect;

    @FXML
    private Button connectDbButton;

    @FXML
    private Button createDbButton;

    @FXML
    private TextField db;
    
    @FXML
    private TextField username;
    
    @FXML
    private TextField password;
    
    public void Connect(ActionEvent event) throws IOException{
        performConnect();
    }
    
    public void performConnect() throws IOException {
      App main = new App();
      var db_name = db.getText().toString();
      String user = username.getText().toString();
      String passkey = password.getText().toString();
      try {
          Class.forName("org.postgresql.Driver");
          if (createTable) {
              connection = DriverManager
                      .getConnection("jdbc:postgresql://localhost:5432/", user, passkey);
              try {
                  Statement statement = connection.createStatement();
                  statement.execute("CREATE DATABASE " + db_name + ";");
              } finally {
                  connection.close();
              }
          }

          connection = DriverManager
                  .getConnection("jdbc:postgresql://localhost:5432/" + db_name, user, passkey);

          System.out.println(connection);
          main.changeScene("/fxml/dashboard.fxml");
      } catch (SQLException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Connection Failed");
        alert.setContentText(e.getMessage());
        alert.show();
      } catch (ClassNotFoundException e) {
        System.err.println(e.getClass().getName()+": "+e.getMessage());
        System.exit(0);
      }
    }

    public void connectDb() {
        if (createTable) {
            String styleClass1 = connectDbButton.getStyleClass().removeLast();
            String styleClass2 = createDbButton.getStyleClass().removeLast();
            connectDbButton.getStyleClass().addLast(styleClass2);
            createDbButton.getStyleClass().addLast(styleClass1);

            createTable = false;
        }
    }

    public void createDb() {
        if (!createTable) {
            String styleClass1 = connectDbButton.getStyleClass().removeLast();
            String styleClass2 = createDbButton.getStyleClass().removeLast();
            connectDbButton.getStyleClass().addLast(styleClass2);
            createDbButton.getStyleClass().addLast(styleClass1);

            createTable = true;
        }
    }
}
