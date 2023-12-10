package tdoc_java;

import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;


import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class DashboardController  implements Initializable{
    
    @FXML
    private VBox navigationbox;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> Tables = getTables();
        if(Tables != null){
            for(int i=0;i<Tables.size();i++){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/fxml/tabletile.fxml"));
                try {
                    HBox listtile = fxmlLoader.load();
                    TableTile Tile = fxmlLoader.getController();
                    Tile.setData(Tables.get(i));
                    navigationbox.getChildren().add(listtile);
                    
                } catch (IOException ex) {
                    Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
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
