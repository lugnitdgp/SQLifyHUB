
package tdoc_java;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class TableTile {
    @FXML
    private Label tablename;
    
    @FXML
    private Button view;
    
    private DashboardController controller;
    
     public void setController(DashboardController controller) {
        this.controller = controller;
    }

    @FXML
    public void View(MouseEvent event) throws IOException {
        String name = tablename.getText();
        controller.loadFXML(name);
    }
    
    public void setData(String name){
        tablename.setText(name);
    }
}
