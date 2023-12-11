package tdoc_java;

import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;


import java.net.URL;
import java.sql.*;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class DashboardController  implements Initializable{
    
    
    
    @FXML
    private BorderPane dashboard;

    @FXML
    private VBox navigationbox;

    @FXML
    private Button home;

    @FXML
    private AnchorPane main;

    @FXML
    private Label username;

    @FXML
    private Button create;

    @FXML
    private Button logout;

    @FXML
    void Home(MouseEvent event) throws IOException {
        loadFXML("HOME");
    }

    @FXML
    void Logout(MouseEvent event) {
        
    }

    @FXML
    void createTable(MouseEvent event) throws IOException {
        loadFXML("CREATE");
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        startApp();
    }
    
    public void startApp(){
        ObservableList<String> Tables = getTables();
        if(Tables != null){
            navigationbox.getChildren().clear();
            for(int i=0;i<Tables.size();i++){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/fxml/tabletile.fxml"));
                try {
                    HBox listtile = fxmlLoader.load();
                    TableTile Tile = fxmlLoader.getController();
                    Tile.setData(Tables.get(i));
                    Tile.setController(this);
                    navigationbox.getChildren().add(listtile);             
                } catch (IOException ex) {
                    Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public void loadFXML(String name) throws IOException{
        Parent root = null;
        if("HOME".equals(name)){
            dashboard.setCenter(main);
        }
        else if("CREATE".equals(name)){
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/fxml/createTable.fxml"));
            root = fxmlLoader.load();
            CreateTablesController controller = fxmlLoader.getController();
            controller.setController(this);
            dashboard.setCenter(root);
        }
        else if(!"HOME".equals(name)){
            root = FXMLLoader.load(getClass().getResource("/fxml/table.fxml"));
            dashboard.setCenter(root);
        }
    }
     
    public ObservableList<String> getTables(){
        ObservableList<String> tableList = FXCollections.observableArrayList();
        Connection connection = Login.connection;
        String query = """
                       SELECT * FROM information_schema.tables 
                       WHERE table_schema = 'public';""";
        Statement st;
        ResultSet rs;
        try{
            st = connection.createStatement();
            rs = st.executeQuery(query);
            String tableName;
            while (rs.next()){
                tableName = rs.getString("table_name");
                tableList.add(tableName);
            }
        }catch (SQLException e){
            return null;
        }
        return tableList;
    }
}
