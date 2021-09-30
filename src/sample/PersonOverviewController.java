package sample;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class PersonOverviewController {
    @FXML
    private TableView<Person> personTable;
    @FXML
    private TableColumn<Person,String> fNameCol;
    @FXML
    private TableColumn<Person,String> lNameCol;

    @FXML
    private Label fNameLab;
    @FXML
    private Label lNameLab;
    @FXML
    private Label streetLab;
    @FXML
    private Label postalCodeLab;
    @FXML
    private Label cityLab;
    @FXML
    private Label birthdayLab;

    public PersonOverviewController(){}

    private Main main;


    @FXML
    private void initialize(){
        fNameCol.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lNameCol.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
    }


    public void setMainApp(Main main){
        this.main = main;


    }


}
