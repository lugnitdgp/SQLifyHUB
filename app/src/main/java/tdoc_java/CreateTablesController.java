package tdoc_java;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ResourceBundle;

public class CreateTablesController implements Initializable {

    @FXML
    private TextField tableName;
    @FXML
    private TextField columnName;
    @FXML
    private ChoiceBox<String> myChoiceBox;
    @FXML
    private TableView<Columns> myTable;
    @FXML
    private TableColumn<Columns,String> colName;
    @FXML
    private TableColumn<Columns,String> colType;
    @FXML
    private void addColumn(ActionEvent e){
        list.add(new Columns(columnName.getText(),myChoiceBox.getValue()));
    }
    
    @FXML
    private void createTable(){
        String query = "CREATE TABLE IF NOT EXISTS "+tableName.getText()+"("+list.get(0).getName()+" "+list.get(0).getType()+" PRIMARY KEY,";
        for(int i = 1;i< list.size();i++){
            if(list.get(i).getType().equals("varchar")){
                query = query+list.get(i).getName()+" varchar(255)";
            }
            else{
                query = query+list.get(i).getName()+" "+list.get(i).getType();

            }
            if(i==(list.size()-1)){
                query = query + ");";
            }
            else{
                query = query +",";
            }
        }
        System.out.println(query);
        try{
            Connection connection = Login.connection; 
            Statement st = connection.createStatement();
            st.execute(query);
        }catch(Exception e){
            e.printStackTrace();
        }
        controller.startApp();
    }
    
    String[] types = {"integer","varchar"};
    ObservableList<Columns> list = FXCollections.observableArrayList(new Columns("id","integer"));

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        myChoiceBox.getItems().addAll(types);
        colName.setCellValueFactory(new PropertyValueFactory<Columns,String>("name"));
        colType.setCellValueFactory(new PropertyValueFactory<Columns,String>("type"));
        myTable.setItems(list);
    }
    
    private DashboardController controller;
    
     
    public void setController(DashboardController controller) {
        this.controller = controller;
    }
}
