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
import java.util.ArrayList;
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
    private Button role;

    @FXML
    void Home(MouseEvent event) throws IOException, SQLException {
        loadFXML("HOME");
    }

    @FXML
    void Logout(MouseEvent event) throws IOException {
         
    }

    @FXML
    void createTable(MouseEvent event) throws IOException, SQLException {
        loadFXML("CREATE");
    }

    @FXML
    void createRole(MouseEvent e)throws IOException,SQLException{
        loadFXML("ROLE");
    }

    @FXML
    void createDb(MouseEvent e)throws IOException,SQLException{
        loadFXML("DB");
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
    
    public void loadFXML(String name) throws IOException, SQLException{
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
        } else if ("ROLE".equals(name)) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/fxml/createRole.fxml"));
            root = fxmlLoader.load();
            CreateRolesController controller = fxmlLoader.getController();
            controller.setController(this);
            dashboard.setCenter(root);

        }else if("DB".equals(name)){
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/fxml/createDB.fxml"));
            root = fxmlLoader.load();
            CreateDBController controller = fxmlLoader.getController();
            controller.setController(this);
            dashboard.setCenter(root);

        }
        else if(!"HOME".equals(name)){
            String query = "SELECT column_name, data_type FROM information_schema.columns WHERE table_name = '"+name+"';";
            ObservableList<Columns> ColumnList = FXCollections.observableArrayList();
            Connection connection = Login.connection;
            Statement st;
            ResultSet rs;
            st = connection.createStatement();
            try{
            rs = st.executeQuery(query);
            String ColumnName;
            String ColumnType;
            while (rs.next()){
                ColumnName = rs.getString("column_name");
                ColumnType = rs.getString("data_type");
                ColumnList.add(new Columns(ColumnName,ColumnType));
            }
            }catch (SQLException e){
            
            }
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/fxml/dynamictable.fxml"));
            root = fxmlLoader.load();
            Table tablecontroller = fxmlLoader.getController();
            tablecontroller.setName(name);
            tablecontroller.setColumnList(ColumnList);
            tablecontroller.setController(this);
            
            for(int i=0;i<ColumnList.size();i++){
                tablecontroller.addColumn(ColumnList.get(i));
                if("character varying".equals(ColumnList.get(i).getType())){
                }
                else if("integer".equals(ColumnList.get(i).getType())){
                }
            }
            
            StringBuilder rowquery= new StringBuilder("SELECT ");
            for (Columns column : ColumnList) {
                rowquery.append(column.getName()).append(", ");
            }
            rowquery.setLength(rowquery.length() - 2);
            rowquery.append(" FROM " + name);
            
            ResultSet resultSet = st.executeQuery(rowquery.toString());
            List<Cells> rows = new ArrayList<>();
            while (resultSet.next()) {
                Cells row = new Cells(null,null,null,null,null,null,null,null,null,null,null,null);
                int set = 0;
                for (Columns column : ColumnList) {
                    Object data = resultSet.getObject(column.getName());
                    set++;
                    switch((char)(96+set)){
                        case 'a':
                            row.a = data.toString();break;
                        case 'b':
                            row.b = data.toString();break;
                        case 'c':
                            row.c = data.toString();break;
                        case 'd':
                            row.d = data.toString();break;
                        case 'e':
                            row.e = data.toString();break;
                        case 'f':
                            row.f = data.toString();break;
                        case 'g':
                            row.g = data.toString();break;
                        case 'h':
                            row.h = data.toString();break;
                        case 'i':
                            row.i = data.toString();break;
                        case 'j':
                            row.j = data.toString();break;
                        case 'k':
                            row.k = data.toString();break;
                        case 'l':
                            row.l = data.toString();break;
                    }
                }
                rows.add(row);
            }
            for(int i=0;i<rows.size();i++){
                tablecontroller.addRow(rows.get(i));
            }
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
            st.close();
        }catch (SQLException e){
            return null;
        }
        return tableList;
    }
}
