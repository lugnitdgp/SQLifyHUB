package tdoc_java;
import java.util.List;
import javafx.scene.control.TableColumn;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.util.converter.BooleanStringConverter;
import javafx.util.converter.NumberStringConverter;

public class Table {

    @FXML
    private Button addrow;

    @FXML
    private Button removerow;

    @FXML
    private Button addcol;

    @FXML
    private Button removecol;

    @FXML
    private TableView<Cells> base;

    @FXML
    void AddColumn(MouseEvent event) {
        addColumn(new Columns("",""));
    }

    @FXML
    void AddRow(MouseEvent event) {
        Cells row = null;
        addRow(row);
    }

    @FXML
    void DeleteColumn(MouseEvent event) {
    }

    @FXML
    void DeleteRow(MouseEvent event) {
    }
    
    int Stringcounter = 0;
    
    
    public void addColumn(Columns col){ 
    TableColumn column = new TableColumn(col.getName());
//    if(col.getType() == "character varying")
//    column.setCellFactory(TextFieldTableCell.forTableColumn());
//    else if(col.getType() == "integer")
//    column.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
      Stringcounter++;
      String set = (char) (96+Stringcounter) + "";
      column.setCellFactory(TextFieldTableCell.forTableColumn());
      column.setCellValueFactory(new PropertyValueFactory<Cells,String>(set));
      
    base.getColumns().add(column);
    }
    
    public void addRow(Cells row){
        base.getItems().add(row);
    }
}
