package sample;

import TongDaOA.tongdaVuln;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import utils.HttpUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private TextArea text;
    @FXML
    private Button tongda;
    @FXML
    private TextField tongdaoutput;
    @FXML
    private Button versionBtn;
    @FXML
    private Button computeNameBtn;
    @FXML
    private TextField urlInput;
    @FXML
    private TextField proxyInput;





    @Override
    public void initialize(URL location, ResourceBundle resources) {
        urlInput.setText("http://10.211.55.7");

        System.out.println("controller Start");

        tongda.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    String url = urlInput.getText();
                    text.setText(tongdaVuln.tongdaGetVersionInfo(url));
                    String result = HttpUtils.tongdaGetSession(url);
                    tongdaoutput.setText(result);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });


        versionBtn.setOnAction(new EventHandler<ActionEvent>() {
            String url = urlInput.getText();
            @Override
            public void handle(ActionEvent event) {
                text.setText(tongdaVuln.tongdaGetVersionInfo(url));
            }
        });

        computeNameBtn.setOnAction(new EventHandler<ActionEvent>() {
            String url = urlInput.getText();
            @Override
            public void handle(ActionEvent event) {
                text.setText(tongdaVuln.tongdaComputerName(url));
            }
        });



    }

    public void postRequest(ActionEvent event){
        text.setText("hi1");
    }



    public void changeImg(ActionEvent event){ }

    public void getTitle(ActionEvent event) { }




}
