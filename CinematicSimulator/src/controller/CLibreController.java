
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class CLibreController implements Initializable {

    @FXML
    private Button calcButton;
    @FXML
    private TextField hTxt;
    @FXML
    private TextField vTxt;
    @FXML
    private TextField tTxt;
    @FXML
    private TextField voTxt;
    @FXML
    private Label hLabel;
    @FXML
    private Label voLabel;
    @FXML
    private Label vLabel;
    @FXML
    private Label tLabel;
    @FXML
    private Label hLabel1;
    @FXML
    private LineChart<?, ?> lineChart;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private LineChart<?, ?> lineChart1;
    @FXML
    private NumberAxis yAxis1;
    @FXML
    private CategoryAxis xAxis1;
    @FXML
    private Button backButton;
    @FXML
    private Button exitButton;
    @FXML
    private ImageView ball;
    
    public float h, vo, v, t,g=(float) 9.8;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {        
    }    

    @FXML
    private void goCLSol(ActionEvent event) throws IOException {
        try{
            if(!hTxt.getText().equals("")){
                this.h=Float.parseFloat(hTxt.getText());
            }else{
                this.h=0;
            }
            if(!voTxt.getText().equals("")){
                this.vo=Float.parseFloat(voTxt.getText());
            }else{
                this.vo=0;
            }
            if(!vTxt.getText().equals("")){
                this.v=Float.parseFloat(vTxt.getText());
            }else{
                this.v=0;
            }
            if(!tTxt.getText().equals("")){
                this.t=Float.parseFloat(tTxt.getText());
            }else{
                this.t=0;
            }
            makeCalculates(this.h, this.vo, this.v, this.t, this.g, event);
        }catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Imposible de realizar la operacion");
            alert.setContentText("Los campos son unicamente de numeros, para usar decimales se usa el punto (.)");
            alert.showAndWait(); 
        }
    }
    
    private void makeCalculates(float h, float vo, float v, float t,float g, ActionEvent event) throws IOException{
        if((h==0 && vo==0 && v==0 && t==0) || (h==0 && vo!=0 && v==0 && t==0)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("No es posible calcular y graficar");
            alert.setContentText("No hay suficiente informacion para calcular");
            alert.showAndWait();
        }else if((h!=0 && vo!=0 && v!=0 && t!=0) && (v!=vo+g*t || h!=(vo*t)+(g*t*t)/2)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("No es posible calcular y graficar");
            alert.setContentText("Se han introducido todos los valores, sin embargo, los datos entregados son erroneos");
            alert.showAndWait();
        }else{
            while(h==0 || v==0 || t==0){
                if(h==0){
                    h=(vo*t)+(g*t*t)/2;
                }
                if(v==0){
                    v=(float) Math.sqrt(vo*vo+2*g*h);
                }
                if(t==0){
                    t=(v-vo)/g;
                }
                if(vo==0 && h!=0 && v!=0 && t!=0){
                    vo=v-g*t;
                }
            }
            this.sets(h, vo, v, t);
        }
    }

    @FXML
    private void goBack(ActionEvent event) throws IOException {
        ((Node)(event.getSource())).getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Menu.fxml"));
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
    
    private void sets(float h, float vo, float v, float t){
        this.cleanGraph();
        this.h=h;
        this.vo=vo;
        this.v=v;
        this.t=t;
        hTxt.setText("");
        vTxt.setText("");
        voTxt.setText("");
        tTxt.setText("");
        hLabel.setText(Float.toString(h)+" m");
        hLabel1.setText(Float.toString(h)+" m");
        voLabel.setText(Float.toString(vo)+" m/s");
        vLabel.setText(Float.toString(v)+" m/s");
        tLabel.setText(Float.toString(t)+" s");
        yAxis.setTickLabelsVisible(true);
        yAxis1.setTickLabelsVisible(true);
        this.makeGraph();
    } 
    private void makeGraph(){
        XYChart.Series series = new XYChart.Series();
        XYChart.Series series1 = new XYChart.Series();
        for(float i=0;i<=this.t;i+=0.5){
            series.getData().add(new XYChart.Data(Float.toString(i),h+(vo*i)-(g*i*i)/2));
            series1.getData().add(new XYChart.Data(Float.toString(i),(vo+g*i)));
        }
        lineChart.getData().addAll(series);
        lineChart1.getData().addAll(series1);
        this.makeAnimation();
    }
    private void cleanGraph(){
        this.lineChart.getData().clear();
        this.lineChart1.getData().clear();
    }
    private void makeAnimation(){
        javafx.scene.shape.Path path = new javafx.scene.shape.Path();
        MoveTo moveTo = new MoveTo(15,15); 
        LineTo cubicCurveTo = new LineTo(15,390); 
        path.getElements().add(moveTo); 
        path.getElements().add(cubicCurveTo);        
        PathTransition pathTransition = new PathTransition(); 
        pathTransition.setDuration(Duration.seconds(t)); 
        pathTransition.setNode(ball); 
        pathTransition.setPath(path);  
        pathTransition.setCycleCount(1); 
        pathTransition.play();
    }
}
