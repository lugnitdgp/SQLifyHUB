package tdoc_java;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class Table implements Initializable{
    
    String name;
    ObservableList<Columns> ColumnList;

    @FXML
    private Button addrow;

    @FXML
    private Button removerow;

    @FXML
    private VBox operations;

    @FXML
    private Button removecol;

    @FXML
    private TableView<Cells> base;
    
    @FXML
    private Button update;
    
    @FXML
    private Button deletetable;
    
    @FXML
    private HBox opspanel;
    
    
    
    private DashboardController controller;
    
    public void setController(DashboardController controller) {
        this.controller = controller;
    }
    
    
    @FXML
    void removetable(MouseEvent event) throws SQLException, IOException {
        String query = "DROP TABLE "+ name +";";
        Connection connection = Login.connection;
        Statement st = connection.createStatement();
        st.execute(query.toString());
        st.close();
        controller.startApp();
        controller.loadFXML("HOME");
    }
    
    @FXML
    void updateRow(MouseEvent event) {
        operations.getChildren().clear();
        ComboBox<String> comboBox = new ComboBox<>();
        ObservableList<String> items = FXCollections.observableArrayList();
        for(Columns col:ColumnList){
            items.add(col.getName());
        }
        comboBox.setItems(items);
        Label label = new Label();
        label.setText("Choose Field to Update");
        label.setStyle("-fx-padding: 10 0 5 0;-fx-text-fill: white;");
        operations.getChildren().add(label);
        operations.getChildren().add(comboBox);
        Label labelid = new Label();
        labelid.setText("Enter id of row");
        labelid.setStyle("-fx-padding: 10 0 5 0;-fx-text-fill: white;");
        operations.getChildren().add(labelid);
        TextField textField_id = new TextField();
        textField_id.setId("id");
        textField_id.setStyle("-fx-alignment: CENTER;-fx-background-color: white;-fx-border-radius: 30;-fx-background-radius: 30;-fx-text-fill: black;");
        operations.getChildren().add(textField_id);
        Label label2 = new Label();
        label2.setText("Enter new value");
        label2.setStyle("-fx-padding: 10 0 5 0;-fx-text-fill: white;");
        operations.getChildren().add(label2);
        TextField textField = new TextField();
        textField.setStyle("-fx-alignment: CENTER;-fx-background-color: white;-fx-border-radius: 30;-fx-background-radius: 30;-fx-text-fill: black;");
        operations.getChildren().add(textField);
        Label label3 = new Label();
        label3.setText("");
        label3.setStyle("-fx-padding: 10 0 5 0;-fx-text-fill: white;");
        operations.getChildren().add(label3);
        Button updateButton = new Button("Update Row");
        updateButton.setStyle("-fx-border-radius: 30;-fx-background-radius: 30;-fx-background-color: white;-fx-text-fill: #407BFF;-fx-font-weight: bold;");
        updateButton.setOnAction((ActionEvent newevent) -> {
            try {
                updateRowelements();
            } catch (SQLException | IOException ex) {
                Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        operations.getChildren().add(updateButton);
    }
    
    public void updateRowelements() throws SQLException, IOException{
        ObservableList<Node> fields = operations.getChildren();
        StringBuilder query = new StringBuilder("UPDATE "+ name +" SET ");
        
        for(int i =0;i<fields.size();i++){
            if(fields.get(i) instanceof ComboBox){
                ComboBox field = (ComboBox) fields.get(i);
                query.append(field.getValue() + " = ");
            }
        }
        
        for(int i =0;i<fields.size();i++){
            if(fields.get(i) instanceof TextField){
                TextField textField = (TextField) fields.get(i);
                if(!"id".equals(textField.getId()))
                query.append("'"+textField.getText()+"' ");
            }
        }
        query.append("WHERE id = ");
        for(int i =0;i<fields.size();i++){
            if(fields.get(i) instanceof TextField){
                TextField textField = (TextField) fields.get(i);
                if("id".equals(textField.getId()))
                query.append(textField.getText()+";");
            }
        }
        Connection connection = Login.connection;
        Statement st = connection.createStatement();
        st.execute(query.toString());
        st.close();
        controller.loadFXML(name);
    }

    @FXML
    void AddRow(MouseEvent event) throws SQLException{
        setRowelements();
    }

    @FXML
    void removeColumn(MouseEvent event) {
        operations.getChildren().removeIf(node -> !(node instanceof ScrollBar));
        ComboBox<String> comboBox = new ComboBox<>();
        ObservableList<String> items = FXCollections.observableArrayList();
        for(Columns col:ColumnList){
            items.add(col.getName());
        }
        comboBox.setItems(items);
        Label label = new Label();
        label.setText("Choose Column to Delete");
        label.setStyle("-fx-padding: 10 0 5 0;-fx-text-fill: white;");
        operations.getChildren().add(label);
        operations.getChildren().add(comboBox);
        Label label3 = new Label();
        label3.setText("");
        label3.setStyle("-fx-padding: 10 0 5 0;-fx-text-fill: white;");
        operations.getChildren().add(label3);
        Button deleteButton = new Button("delete Column");
        deleteButton.setStyle("-fx-border-radius: 30;-fx-background-radius: 30;-fx-background-color: white;-fx-text-fill: #407BFF;-fx-font-weight: bold;");
        deleteButton.setOnAction((ActionEvent newevent) -> {
            try {
                deletecolumn();
            } catch (SQLException | IOException ex) {
                Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        operations.getChildren().add(deleteButton);
    }
    
    public void deletecolumn() throws SQLException, IOException{
        ObservableList<Node> fields = operations.getChildren();
        StringBuilder query = new StringBuilder("ALTER TABLE "+ name);
        query.append(" DROP COLUMN ");
        for(int i =0;i<fields.size();i++){
            if(fields.get(i) instanceof ComboBox){
                ComboBox field = (ComboBox) fields.get(i);
                query.append(field.getValue() + ";");
            }
        }
        Connection connection = Login.connection;
        Statement st = connection.createStatement();
        st.execute(query.toString());
        st.close();
        controller.loadFXML(name);
    }

    @FXML
    void DeleteRow(MouseEvent event) {
        operations.getChildren().removeIf(node -> !(node instanceof ScrollBar));
        Label labelid = new Label();
        labelid.setText("Enter id of row");
        labelid.setStyle("-fx-padding: 10 0 5 0;-fx-text-fill: white;");
        operations.getChildren().add(labelid);
        TextField textField_id = new TextField();
        textField_id.setId("id");
        textField_id.setStyle("-fx-alignment: CENTER;-fx-background-color: white;-fx-border-radius: 30;-fx-background-radius: 30;-fx-text-fill: black;");
        operations.getChildren().add(textField_id);
        Label label3 = new Label();
        label3.setText("");
        label3.setStyle("-fx-padding: 10 0 5 0;-fx-text-fill: white;");
        operations.getChildren().add(label3);
        Button updateButton = new Button("Delete Row");
        updateButton.setStyle("-fx-border-radius: 30;-fx-background-radius: 30;-fx-background-color: white;-fx-text-fill: #407BFF;-fx-font-weight: bold;");
        updateButton.setOnAction((ActionEvent newevent) -> {
            try {
                deleteRow();
            } catch (SQLException | IOException ex) {
                Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        operations.getChildren().add(updateButton);
    }
    
    public void deleteRow() throws SQLException, IOException{
        ObservableList<Node> fields = operations.getChildren();
        StringBuilder query = new StringBuilder("DELETE FROM "+ name);
        query.append(" WHERE id = ");
        for(int i =0;i<fields.size();i++){
            if(fields.get(i) instanceof TextField){
                TextField textField = (TextField) fields.get(i);
                if("id".equals(textField.getId()))
                query.append(textField.getText()+";");
            }
        }
        Connection connection = Login.connection;
        Statement st = connection.createStatement();
        st.execute(query.toString());
        st.close();
        controller.loadFXML(name);
    }
    
    int Stringcounter = 0;
    public void addColumn(Columns col) throws IOException{ 
    TableColumn column = new TableColumn(col.getName());
      Stringcounter++;
      String set = (char) (96+Stringcounter) + "";
      column.setCellFactory(TextFieldTableCell.forTableColumn());
      column.setCellValueFactory(new PropertyValueFactory<Cells,String>(set));
      base.getColumns().add(column);
    }
    
    private int getColumnIndex(TableView<?> tableView, TablePosition<?, ?> tablePosition) {
        return tableView.getColumns().indexOf(tablePosition.getTableColumn());
    }
    
    public void addRow(Cells row){
        if(base.getItems() == null){
        base.getItems().add(row);
        base.sort();
        }
        else{
            base.getItems().add(row);
        }
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public void setColumnList(ObservableList<Columns> ColumnList){
        this.ColumnList = ColumnList;
    }
    
    public void getRowelements() throws SQLException, IOException{
        ObservableList<Node> fields = operations.getChildren();
        StringBuilder query = new StringBuilder("INSERT INTO "+ name +" (");
        for(Columns column:ColumnList){
            query.append(column.getName()+", ");
        }
        query.setLength(query.length() - 2);
        query.append(") VALUES (");
        for(int i =0;i<fields.size();i++){
            if(fields.get(i) instanceof TextField){
                TextField textField = (TextField) fields.get(i);
                query.append("'"+textField.getText()+"', ");
            }
        }
        query.setLength(query.length() - 2);
        query.append(");");
        Connection connection = Login.connection;
        Statement st = connection.createStatement();
        st.execute(query.toString());
        st.close();
        controller.loadFXML(name);
    }
    
    public void setRowelements(){
        operations.getChildren().clear();
        for(Columns col:ColumnList){
            Label label = new Label();
            label.setText(col.getName());
            label.setStyle("-fx-padding: 10 0 5 0;-fx-text-fill: white;");
            operations.getChildren().add(label);
            TextField textField = new TextField();
            textField.setStyle("-fx-alignment: CENTER;-fx-background-color: white;-fx-border-radius: 30;-fx-background-radius: 30;-fx-text-fill: black;");
            operations.getChildren().add(textField);
        }
        Button addButton = new Button("Add Row");
        addButton.setStyle("-fx-border-radius: 30;-fx-background-radius: 30;-fx-background-color: white;-fx-text-fill: #407BFF;-fx-font-weight: bold;");
        addButton.setOnAction((ActionEvent event) -> {
            try {
                getRowelements();
            } catch (SQLException | IOException ex) {
                Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        Label label = new Label();
        label.setText("");
        label.setStyle("-fx-padding: 10 0 5 0;-fx-text-fill: white;");
        operations.getChildren().add(label);
        operations.getChildren().add(addButton);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       base.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
       removerow.setDisable(true);
       update.setDisable(true);
       base.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            removerow.setDisable(newValue == null);
            update.setDisable(newValue == null);
        });
    }
    
    public void setRole(String role){
        if(role == "read"){
            opspanel.setVisible(false);
        }
    }
}
