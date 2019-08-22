package sample.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.DatabaseHandler;
import sample.User;

public class SingUpController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField passwordF;

    @FXML
    private TextField loginF;

    @FXML
    private Button registrationButton;

    @FXML
    private TextField secondNameF;

    @FXML
    private TextField nameF;

    @FXML
    private TextField locationF;

    @FXML
    private CheckBox maleCheckBox;

    @FXML
    private CheckBox FemaleCheckBox;

    @FXML
    private Button backButton;

    @FXML
    private Label changeLogin;

    @FXML
    void initialize() {
        singUpNewUser();
    }

    private void singUpNewUser() {
        DatabaseHandler dbHandler = new DatabaseHandler();
        User user = new User();
        registrationButton.setOnAction(e->{
            int count =0;
            user.setFirstName(nameF.getText());
            user.setLastName(secondNameF.getText());
            user.setLocation(locationF.getText());
            user.setUserName(loginF.getText());
            ResultSet userNameResult= dbHandler.geyUserName(user);
            while(true){
                try {
                    if (!userNameResult.next()) break;
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                count++;
            }
            if(count==1){
                changeLogin.setText("Login already exist");
            }
            user.setPassword(passwordF.getText());
            user.setGender(checkMale());
            if(count==0) {
                dbHandler.signUpUser(user);
                backToMainWin(registrationButton);
            }
        });

        backButton.setOnAction(e->backToMainWin(backButton));
        maleCheckBox.setOnAction(e->FemaleCheckBox.setSelected(false));
        FemaleCheckBox.setOnAction(e-> maleCheckBox.setSelected(false));
    }

    private String checkMale() {
        if(maleCheckBox.isSelected()){
            return "Male";
        }
        if(FemaleCheckBox.isSelected()){
            return "Female";
        }
        return "";
    }

    private void backToMainWin(Node node){
        node.getScene().getWindow().hide();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/sample/view/sample.fxml"));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        Stage primaryStage = new Stage();
        primaryStage.setTitle("RegistrationForm");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }


}



