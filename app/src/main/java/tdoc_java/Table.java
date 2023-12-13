package tdoc_java;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.TableColumn;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.util.converter.BooleanStringConverter;
import javafx.util.converter.NumberStringConverter;
import javafx.scene.control.TableColumn.CellEditEvent;


public class Table implements Initializable{

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
      Stringcounter++;
      String set = (char) (96+Stringcounter) + "";
      column.setCellFactory(TextFieldTableCell.forTableColumn());
      column.setCellValueFactory(new PropertyValueFactory<Cells,String>(set));
      column.setOnEditCommit(event->{
        Cells cell = base.getSelectionModel().getSelectedItem();
        int columnIndex = getColumnIndex(base, base.getSelectionModel().getSelectedCells().get(0));
        switch(columnIndex)
        {
            case 0: cell.a = (String) event.getSource() ;break;
            case 1: cell.b = (String) event.getSource();break;
            case 2: cell.c = (String) event.getSource();break;
            case 3: cell.d = (String) event.getSource();break;
            case 4: cell.e = (String) event.getSource();break;
            case 5: cell.f =(String) event.getSource();break;
            case 6: cell.g = (String) event.getSource();break;
            case 7: cell.h = (String) event.getSource();break;
            case 8: cell.i = (String) event.getSource();break;
            case 9: cell.j = (String) event.getSource();break;
            case 10: cell.k = (String) event.getSource();break;
            case 11: cell.l = (String) event.getSource();break;
        }
        base.refresh();
      });
      column.setEditable(true);
      base.getColumns().add(column);
    }
    
    private int getColumnIndex(TableView<?> tableView, TablePosition<?, ?> tablePosition) {
        // Get the column index of the selected cell
        return tableView.getColumns().indexOf(tablePosition.getTableColumn());
    }
    
    public void addRow(Cells row){
        base.getItems().add(row);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        base.setEditable(true);
    }
}

