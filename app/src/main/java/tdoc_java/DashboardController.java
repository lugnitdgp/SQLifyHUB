package tdoc_java;

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

public class DashboardController implements Initializable  {

    @FXML
    private TextField tfID;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfRegd;
    @FXML
    private TextField tfRoll;
    @FXML
    private TableView<Students> tvStudent;
    @FXML
    private TableColumn<Students,Integer> colID;
    @FXML
    private TableColumn<Students,String> colName;
    @FXML
    private TableColumn<Students,String> colRegd;
    @FXML
    private TableColumn<Students,String> colRoll;
    @FXML
    private Button btnInsert;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private void handleButton(ActionEvent e){
        if(e.getSource()==btnInsert){
            insertStudent();
        }
        if(e.getSource()==btnUpdate){
            updateStudent();
        }
        if(e.getSource()==btnDelete){
            deleteStudent();
        }

    }
    @FXML
    private void handleMouseAction(MouseEvent e){
        Students student = tvStudent.getSelectionModel().getSelectedItem();
        tfID.setText(""+student.getID());
        tfName.setText(student.getName());
        tfRegd.setText(student.getRegd());
        tfRoll.setText(student.getRoll());

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showStudents();
    }

    public Connection getConnection(){
        Connection con;
        try{
            con= DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/postgres","postgres","8187");
            return con;
        } catch (SQLException e) {
            System.out.println("Connection Error");
            return null;
        }
    }

    public ObservableList<Students> getStudents(){
        ObservableList<Students> studentsList = FXCollections.observableArrayList();
        Connection con = getConnection();
        String query = "select * from students;";
        Statement st;
        ResultSet rs;
        try{
            st=con.createStatement();
            rs = st.executeQuery(query);
            Students students ;
            while (rs.next()){
                students = new Students(rs.getInt("id"),rs.getString("name"),rs.getString("regd"),rs.getString("roll"));
                studentsList.add(students);
            }

        }catch (Exception e){
            return null;

        }
        return studentsList;



    }
    public void showStudents(){
        ObservableList<Students> list = getStudents();
        colID.setCellValueFactory(new PropertyValueFactory<Students,Integer>("ID"));
        colName.setCellValueFactory(new PropertyValueFactory<Students,String>("Name"));
        colRegd.setCellValueFactory(new PropertyValueFactory<Students,String>("Regd"));
        colRoll.setCellValueFactory(new PropertyValueFactory<Students,String>("Roll"));
        tvStudent.setItems(list);
    }

    private void insertStudent(){
        String query = "INSERT INTO students VALUES("+tfID.getText()+",'"+tfName.getText()+"','"+tfRegd.getText()+"','"+tfRoll.getText()+"');";
        executeQuery(query);
        showStudents();

    }

    private void updateStudent(){
        String query = "UPDATE students SET name='"+tfName.getText()+"',regd='"+tfRegd.getText()+"',roll='"+tfRoll.getText()+"' WHERE id ="+tfID.getText()+";";
        executeQuery(query);
        showStudents();
    }

    private void deleteStudent(){
        String query = "DELETE FROM students WHERE id="+tfID.getText()+";";
        executeQuery(query);
        showStudents();
    }

    private void executeQuery(String query){
        Connection conn = getConnection();
        Statement st;
        try{
            st = conn.createStatement();
            st.executeUpdate(query);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public ObservableList<String> getTables(){
        ObservableList<String> tableList = FXCollections.observableArrayList();
        Connection con = getConnection();
        String query = "SELECT * FROM information_schema.tables \n" +
                "WHERE table_schema = 'public';";
        Statement st;
        ResultSet rs;
        try{
            st=con.createStatement();
            rs = st.executeQuery(query);
            String tableName;
            while (rs.next()){

                tableName = rs.getString("tablename");
                tableList.add(tableName);
            }

        }catch (Exception e){
            return null;

        }
        return tableList;

    }






}
