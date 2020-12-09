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

public class MRUController implements Initializable {

    @FXML
    private TextField xoTxt;
    @FXML
    private TextField xTxt;
    @FXML
    private TextField vTxt;
    @FXML
    private TextField tTxt;
    @FXML
    private Button calcButton;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private Button exitButton;
    @FXML
    private Button backButton;
    @FXML
    private Label xoLabel;
    @FXML
    private Label xLabel;
    @FXML
    private Label vLabel;
    @FXML
    private Label tLabel;
    @FXML
    private LineChart<?, ?> lineChart;
    @FXML
    private Label xoLabel1;
    @FXML
    private Label xLabel1;
    @FXML
    private ImageView moto;
    
    public float xo, x, v, t;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {    
    }    

    @FXML
    private void goMRUSol(ActionEvent event) throws IOException {
        try{
            if(!xoTxt.getText().equals("")){
                this.xo=Float.parseFloat(xoTxt.getText());
            }else{
                this.xo=0;
            }
            if(!xTxt.getText().equals("")){
                this.x=Float.parseFloat(xTxt.getText());
            }else{
                this.x=0;
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
            makeCalculates(this.xo, this.x, this.v, this.t, event);
        }catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Imposible de realizar la operacion");
            alert.setContentText("Los campos son unicamente de numeros, para usar decimales se usa el punto (.)");
            alert.showAndWait(); 
        }  
    }
    
    private void makeCalculates(float xo, float x, float v, float t, ActionEvent event) throws IOException{
        if((xo==0 && x==0 && v==0 && t==0) || (xo!=0 && x==0 && v==0 && t==0) ||(xo==0 && x!=0 && v==0 && t==0) || 
           (xo==0 && x==0 && v!=0 && t==0) || (xo==0 && x==0 && v==0 && t!=0) ||(xo!=0 && x!=0 && v==0 && t==0) || 
           (xo!=0 && x==0 && v!=0 && t==0) || (xo!=0 && x==0 && v==0 && t!=0)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("No es posible calcular y graficar");
            alert.setContentText("No hay suficiente informacion para calcular");
            alert.showAndWait();
        }else if((xo!=0 && x!=0 && v!=0 && t!=0) && (x!=xo+v*t)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("No es posible calcular y graficar");
            alert.setContentText("Se han introducido todos los valores, sin embargo, los datos entregados son erroneos");
            alert.showAndWait();
        }else{
            while(x==0 || v==0 || t==0){
                if(x==0){
                    x=xo+v*t;
                    this.x=x;
                }
                if(v==0){
                    v=(x-xo)/t;
                    this.v=v;
                }
                if(t==0){
                    t=(x-xo)/v;
                    this.t=t;
                }
                if(xo==0 && x!=0 && v!=0 && t!=0){
                    xo=x-v*t;
                    this.xo=xo;
                }
            }
            this.sets(this.x,this.xo,this.v,this.t);  
        }
    }

    @FXML
    private void exit(ActionEvent event) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
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
    
    private void sets(float x, float xo, float v, float t){
        this.cleanGraph();
        this.x=x;
        this.xo=xo;
        this.v=v;
        this.t=t;
        xTxt.setText("");
        xoTxt.setText("");
        vTxt.setText("");
        tTxt.setText("");
        xoLabel.setText(Float.toString(xo)+" m");
        xLabel.setText(Float.toString(x)+" m");
        xoLabel1.setText(Integer.toString((int) xo)+" m");
        xLabel1.setText(Integer.toString((int) x)+" m");
        vLabel.setText(Float.toString(v)+" m/s");
        tLabel.setText(Float.toString(t)+" s");
        yAxis.setTickLabelsVisible(true);
        this.makeGraph();
    }
    private void makeGraph(){
        XYChart.Series series = new XYChart.Series();
        for(float i=0;i<=this.t;i++){
            series.getData().add(new XYChart.Data(Float.toString(i),xo+v*i));
        }
        this.lineChart.getData().addAll(series);
        this.makeAnimation();
    }
    private void cleanGraph(){
        this.lineChart.getData().clear();
    }
    private void makeAnimation(){
        javafx.scene.shape.Path path = new javafx.scene.shape.Path(); 
        MoveTo moveTo = new MoveTo(37, 36); 
        LineTo cubicCurveTo = new LineTo(500,36); 
        path.getElements().add(moveTo); 
        path.getElements().add(cubicCurveTo);        
        PathTransition pathTransition = new PathTransition(); 
        pathTransition.setDuration(Duration.seconds(t)); 
        pathTransition.setNode(moto); 
        pathTransition.setPath(path);  
        pathTransition.setCycleCount(1); 
        pathTransition.play();
    }
}
