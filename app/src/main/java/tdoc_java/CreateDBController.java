package tdoc_java;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class CreateDBController implements Initializable {

    ObservableList<String> list = FXCollections.observableArrayList();

    @FXML
    private TextField dbName;

    @FXML
    private ListView<String> DBList;

    @FXML
    void createDB(MouseEvent e){
        try{
            Connection connection = Login.connection;
            Statement st = connection.createStatement();
            st.execute("CREATE DATABASE "+dbName.getText()+";");

        } catch (Exception exception){
            System.out.println(exception.getStackTrace());
        }



        controller.startApp();
    }



    private DashboardController controller;


    public void setController(DashboardController controller) {
        this.controller = controller;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            Connection connection = Login.connection;
            Statement st = connection.createStatement();
            ResultSet rs =st.executeQuery("SELECT datname FROM pg_database;");
            while (rs.next()){
                list.add(rs.getString("datname"));
            }
            st.close();
            connection.close();
            System.out.println(list);
            DBList.setItems(list);


        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
