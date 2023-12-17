package tdoc_java;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ResourceBundle;

public class CreateRolesController implements Initializable {

    @FXML
    private TextField roleName;

    @FXML
    private TextField password;

    @FXML
    private ChoiceBox<String> rolesList;

    @FXML
    private void createRole(){
        String name = roleName.getText();
        String pass = password.getText();
        String query1 = "CREATE ROLE "+name+" WITH LOGIN PASSWORD '"+pass+"';";
        String query2 = (rolesList.getValue().equals("Read"))?"GRANT pg_read_all_data TO "+roleName.getText()+";":"ALTER ROLE "+roleName.getText()+" WITH SUPERUSER;";
        try{
            Connection connection = Login.connection;
            Statement st = connection.createStatement();
            st.execute(query1);
            st.execute(query2);
        }catch(Exception e){
            e.printStackTrace();
        }
        controller.startApp();
    }

    String[] roles = {"Read","Read-Write"};

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        rolesList.getItems().addAll(roles);
        rolesList.setValue("Read");
    }

    private DashboardController controller;


    public void setController(DashboardController controller) {
        this.controller = controller;
    }
}
