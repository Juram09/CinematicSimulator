package controller;

import javafx.application.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application{

    public static void main(String[] args) {
       launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.initStyle(StageStyle.UNDECORATED);
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(Main.class.getResource("/view/Menu.fxml"));
        Pane window = (Pane) loader.load();
        Scene scene=new Scene(window,800,600);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }  
}
