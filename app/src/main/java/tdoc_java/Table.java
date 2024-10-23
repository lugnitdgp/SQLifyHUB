package tdoc_java;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class Table implements Initializable {

    private String name;
    private ObservableList<Columns> columnList;

    @FXML
    private Button addrow, removerow, removecol, update, deletetable;
    @FXML
    private VBox operations;
    @FXML
    private TableView<Cells> base;
    @FXML
    private HBox opspanel;

    private DashboardController controller;

    public void setController(DashboardController controller) {
        this.controller = controller;
    }

    @FXML
    void removetable(MouseEvent event) throws SQLException, IOException {
        executeUpdate("DROP TABLE " + name + ";");
        controller.startApp();
        controller.loadFXML("HOME");
    }

    @FXML
    void updateRow(MouseEvent event) {
        operations.getChildren().clear();
        createUpdateRowUI();
    }

    private void createUpdateRowUI() {
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.setItems(getColumnNames());

        operations.getChildren().addAll(
                createLabel("Choose Field to Update"),
                comboBox,
                createLabel("Enter ID of Row"),
                createTextField("id"),
                createLabel("Enter New Value"),
                createTextField(),
                createButton("Update Row", e -> updateRowElements(comboBox))
        );
    }

    private void updateRowElements(ComboBox<String> comboBox) {
        try {
            String query = buildUpdateQuery(comboBox.getValue());
            executeUpdate(query);
            controller.loadFXML(name);
        } catch (SQLException | IOException e) {
            showError("Failed to update row: " + e.getMessage());
        }
    }

    @FXML
    void AddRow(MouseEvent event) throws SQLException {
        setRowElements();
    }

    @FXML
    void removeColumn(MouseEvent event) {
        operations.getChildren().clear();
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.setItems(getColumnNames());

        operations.getChildren().addAll(
                createLabel("Choose Column to Delete"),
                comboBox,
                createButton("Delete Column", e -> deleteColumn(comboBox))
        );
    }

    private void deleteColumn(ComboBox<String> comboBox) {
        try {
            String query = "ALTER TABLE " + name + " DROP COLUMN " + comboBox.getValue() + ";";
            executeUpdate(query);
            controller.loadFXML(name);
        } catch (SQLException | IOException e) {
            showError("Failed to delete column: " + e.getMessage());
        }
    }

    @FXML
    void DeleteRow(MouseEvent event) {
        operations.getChildren().clear();
        operations.getChildren().addAll(
                createLabel("Enter ID of Row"),
                createTextField("id"),
                createButton("Delete Row", e -> deleteRow())
        );
    }

    private void deleteRow() {
        try {
            String query = "DELETE FROM " + name + " WHERE id = " + getTextFieldValue("id") + ";";
            executeUpdate(query);
            controller.loadFXML(name);
        } catch (SQLException | IOException e) {
            showError("Failed to delete row: " + e.getMessage());
        }
    }

    public void addColumn(Columns col) throws IOException {
        TableColumn<Cells, String> column = new TableColumn<>(col.getName());
        column.setCellFactory(TextFieldTableCell.forTableColumn());
        column.setCellValueFactory(new PropertyValueFactory<>(getColumnLetter(columnList.size())));
        base.getColumns().add(column);
    }

    public void addRow(Cells row) {
        base.getItems().add(row);
        base.sort();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColumnList(ObservableList<Columns> columnList) {
        this.columnList = columnList;
    }

    public void setRowElements() {
        operations.getChildren().clear();
        for (Columns col : columnList) {
            operations.getChildren().addAll(createLabel(col.getName()), createTextField());
        }
        operations.getChildren().add(createButton("Add Row", e -> insertRow()));
    }

    private void insertRow() {
        try {
            String query = buildInsertQuery();
            executeUpdate(query);
            controller.loadFXML(name);
        } catch (SQLException | IOException e) {
            showError("Failed to insert row: " + e.getMessage());
        }
    }

    private void executeUpdate(String query) throws SQLException {
        try (Connection connection = Login.connection; Statement st = connection.createStatement()) {
            st.execute(query);
        }
    }

    private ObservableList<String> getColumnNames() {
        ObservableList<String> items = FXCollections.observableArrayList();
        for (Columns col : columnList) {
            items.add(col.getName());
        }
        return items;
    }

    private String buildUpdateQuery(String fieldName) {
        return "UPDATE " + name + " SET " + fieldName + " = '" + getTextFieldValue("") + "' WHERE id = " + getTextFieldValue("id") + ";";
    }

    private String buildInsertQuery() {
        StringBuilder query = new StringBuilder("INSERT INTO " + name + " (");
        for (Columns column : columnList) {
            query.append(column.getName()).append(", ");
        }
        query.setLength(query.length() - 2);
        query.append(") VALUES (");
        for (Node field : operations.getChildren()) {
            if (field instanceof TextField) {
                query.append("'").append(((TextField) field).getText()).append("', ");
            }
        }
        query.setLength(query.length() - 2);
        query.append(");");
        return query.toString();
    }

    private String getTextFieldValue(String id) {
        for (Node node : operations.getChildren()) {
            if (node instanceof TextField && id.equals(((TextField) node).getId())) {
                return ((TextField) node).getText();
            }
        }
        return null;
    }

    private TextField createTextField() {
        TextField textField = new TextField();
        textField.setStyle("-fx-alignment: CENTER; -fx-background-color: white; -fx-border-radius: 30; -fx-background-radius: 30; -fx-text-fill: black;");
        return textField;
    }

    private TextField createTextField(String id) {
        TextField textField = createTextField();
        textField.setId(id);
        return textField;
    }

    private Label createLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-padding: 10 0 5 0; -fx-text-fill: white;");
        return label;
    }

    private Button createButton(String text, javafx.event.EventHandler<ActionEvent> handler) {
        Button button = new Button(text);
        button.setStyle("-fx-border-radius: 30; -fx-background-radius: 30; -fx-background-color: white; -fx-text-fill: #407BFF; -fx-font-weight: bold;");
        button.setOnAction(handler);
        return button;
    }

    private String getColumnLetter(int index) {
        return String.valueOf((char) ('a' + index));
    }

    private void showError(String message) {
        Label errorLabel = new Label(message);
        errorLabel.setStyle("-fx-text-fill: red;");
        operations.getChildren().add(errorLabel);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), e -> operations.getChildren().remove(errorLabel)));
        timeline.play();
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

    public void setRole(String role) {
        opspanel.setVisible(!"read".equals(role));
    }
}
