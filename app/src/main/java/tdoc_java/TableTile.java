
package tdoc_java;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
public class TableTile {
    @FXML
    private Label tablename;
    
    public void setData(String name){
        tablename.setText(name);
    }
}
