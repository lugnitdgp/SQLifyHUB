/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tdoc_java;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Login {
    
    @FXML
    private Button connect;
    
    @FXML
    private TextField db;
    
    @FXML
    private TextField username;
    
    @FXML
    private TextField password;
    
    
    public void Connect(ActionEvent event) throws IOException{
        getConnection();
    }
    
    
   public void getConnection() throws IOException {
      App main = new App();
      String db_name = db.getText().toString();
      String user = username.getText().toString();
      String passkey = password.getText().toString();
      
      Connection connection = null;
      try {
         Class.forName("org.postgresql.Driver");
         
         connection = DriverManager
            .getConnection("jdbc:postgresql://localhost:5432/"+db_name,user,passkey);
         
      } catch (ClassNotFoundException | SQLException e) {
          
         System.err.println(e.getClass().getName()+": "+e.getMessage());
         System.exit(0);
         
      }
      
      System.out.println("Opened database successfully");
      main.changeScene("/fxml/dashboard.fxml");
   }
    
}
