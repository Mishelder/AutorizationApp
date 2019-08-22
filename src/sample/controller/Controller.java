package sample.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.DatabaseHandler;
import sample.User;
import sample.animation.Shake;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField password;

    @FXML
    private TextField login;

    @FXML
    private Button singButton;

    @FXML
    private Button loginSingUpButton;

    @FXML
    private Label fillField;

    @FXML
    void initialize(){
        loginSingUpButton.setOnAction(e-> {
            loginSingUpButton.getScene().getWindow().hide();
            Parent loader=null;
            try {
                 loader = FXMLLoader.load(getClass().getResource("/sample/view/singUp.fxml"));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            Stage stage =new Stage();
            stage.setTitle("Registration");
            stage.setScene(new Scene(loader,600,400));
            stage.showAndWait();

        });

        singButton.setOnAction(e-> {
           String loginText = login.getText().trim();
           String passText = password.getText().trim();

           if(!loginText.isEmpty()&&!passText.isEmpty())
               loginUser(loginText,passText);


        });
    }

    private void loginUser(String loginText, String passText) {
        DatabaseHandler dbHandler =new DatabaseHandler();
        User user =new User();
        user.setUserName(loginText);
        user.setPassword(passText);
        ResultSet passwordResult= dbHandler.geyUserPassword(user);
        ResultSet userNameResult= dbHandler.geyUserName(user);
        int passCount =getCountOfUsers(passwordResult);
        int userNameCount =getCountOfUsers(userNameResult);


            if(userNameCount==1) {
                if (passCount==1) {
                    singButton.getScene().getWindow().hide();
                    Parent root = null;
                    try {
                        root = FXMLLoader.load(getClass().getResource("/sample/view/app.fxml"));
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    Stage primaryStage = new Stage();
                    primaryStage.setTitle("You entrance!!!!");
                    primaryStage.setScene(new Scene(root, 600, 400));
                    primaryStage.show();

                } else {Shake passAnim = new Shake(password);
                    passAnim.playAnim();
                       }
            }else {Shake loginAnim = new Shake(login);
                loginAnim.playAnim();
                }





    }

    private int getCountOfUsers(ResultSet re ){
        int count =0;
        while(true){
            try {
                if (!re.next()) break;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            count++;
        }
        return count;
    }
}
