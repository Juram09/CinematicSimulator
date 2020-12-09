
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MenuController implements Initializable {

    @FXML
    private Button mruButton;
    @FXML
    private Button mruaButton;
    @FXML
    private Button MPButton;
    @FXML
    private Button CLButton;
    @FXML
    private Button exitButton;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void goMRU(ActionEvent event) throws IOException {
        ((Node)(event.getSource())).getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/MRU.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        Scene scene=new Scene(root,800,600);
        stage.setScene(scene);  
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    private void goMRUA(ActionEvent event) throws IOException {
        ((Node)(event.getSource())).getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/MRUA.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        Scene scene=new Scene(root,800,600);
        stage.setScene(scene);  
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    private void goMP(ActionEvent event) throws IOException {
        ((Node)(event.getSource())).getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/MParabolico.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        Scene scene=new Scene(root,800,600);
        stage.setScene(scene);  
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    private void GoCL(ActionEvent event) throws IOException {
        ((Node)(event.getSource())).getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/CLibre.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        Scene scene=new Scene(root,800,600);
        stage.setScene(scene);  
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    private void exit(ActionEvent event) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
}
