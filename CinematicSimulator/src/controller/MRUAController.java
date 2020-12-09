
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

public class MRUAController implements Initializable {

    @FXML
    private TextField xoTxt;
    @FXML
    private TextField xTxt;
    @FXML
    private TextField voTxt;
    @FXML
    private TextField vTxt;
    @FXML
    private TextField aTxt;
    @FXML
    private TextField tTxt;
    @FXML
    private Button calcButton;
    @FXML
    private Label xoLabel;
    @FXML
    private Label xLabel;
    @FXML
    private Label voLabel;
    @FXML
    private Label vLabel;
    @FXML
    private Label aLabel;
    @FXML
    private Label tLabel;
    @FXML
    private Label xoLabel1;
    @FXML
    private Label xLabel1;
    @FXML
    private Button exitButton;
    @FXML
    private Button backButton;
    
    public float xo, x, v, vo, a, t;
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
    private ImageView carro;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }       

    @FXML
    private void goMRUASol(ActionEvent event) throws IOException {
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
            if(!voTxt.getText().equals("")){
                this.vo=Float.parseFloat(voTxt.getText());
            }else{
                this.vo=0;
            }
            if(!aTxt.getText().equals("")){
                this.a=Float.parseFloat(aTxt.getText());
            }else{
                this.a=0;
            }
            if(!tTxt.getText().equals("")){
                this.t=Float.parseFloat(tTxt.getText());
            }else{
                this.t=0;
            }
            makeCalculates(this.xo, this.x, this.vo, this.v, this.a, this.t, event);
        }catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Imposible de realizar la operacion");
            alert.setContentText("Los campos son unicamente de numeros, para usar decimales se usa el punto (.)");
            alert.showAndWait(); 
        }
    }
    
    private void makeCalculates(float xo, float x, float vo, float v, float a, float t, ActionEvent event) throws IOException{
        if((x!=0 && v!=0) || (x!=0 && a!=0) || (x!=0 && t!=0) || (v!=0 && a!=0) || (v!=0 && t!=0) || (a!=0 && t!=0)){
            if((xo!=0 && x!=0 && vo!=0 && v!=0 && a!=0 && t!=0) && ((x!=xo+vo*t+(a*t*t)/2) || (v!=vo+a*t) || (v!=Math.sqrt((vo*vo)+2*a*(x-xo))))){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("No es posible calcular y graficar");
                alert.setContentText("Se han introducido todos los valores, sin embargo, los datos entregados son erroneos");
                alert.showAndWait();
            }else{
                if(x!=0 && v!=0){
                    if(a==0){
                        a=(v*v-vo*vo)/(2*(x-xo));
                    }
                    if(t==0){
                        t=(v-vo)/a;
                    }
                }else if(x!=0 && a!=0){
                    if(v==0){
                        v=(float) Math.sqrt((vo*vo)+2*a*(x-xo));
                    }
                    if(t==0){
                        t=(v-vo)/a;
                    }
                }else if(x!=0 && t!=0){
                    if(a==0){
                        a=(2*(x-xo-(vo*t)))/(t*t);
                    }
                    if(v==0){
                        v=vo+a*t;
                    }
                }else if(v!=0 && a!=0){
                    if(t==0){
                        t=(v-vo)/a;
                    }
                    if(x==0){
                        x=xo+(vo*t)+(a*t*t)/2;
                    }
                }else if(v!=0 && t!=0){
                    if(a==0){
                        a=(v-vo)/t;
                    }
                    if(x==0){
                        x=xo+(vo*t)+(a*t*t)/2;
                    }
                }else if(a!=0 && t!=0){
                    if(v==0){
                        v=vo+a*t;
                    }
                    if(x==0){
                        x=xo+(vo*t)+(a*t*t)/2;
                    }
                }
                this.xo=xo;
                this.x=x;
                this.vo=vo;
                this.v=v;
                this.a=a;
                this.t=t;
                this.sets(this.x, this.xo, this.v, this.vo, this.a, this.t);
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("No es posible calcular y graficar");
            alert.setContentText("No hay suficiente informacion para calcular");
            alert.showAndWait();
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
    
    private void sets(float x, float xo, float v, float vo, float a, float t){
        this.cleanGraph();
        this.x=x;
        this.xo=xo;
        this.v=v;
        this.vo=vo;
        this.a=a;
        this.t=t;
        xTxt.setText("");
        xoTxt.setText("");
        vTxt.setText("");
        voTxt.setText("");
        aTxt.setText("");
        tTxt.setText("");
        xoLabel.setText(Float.toString(xo)+" m");
        xLabel.setText(Float.toString(x)+" m");
        xoLabel1.setText(Integer.toString((int) xo)+" m");
        xLabel1.setText(Integer.toString((int) x)+" m");
        voLabel.setText(Float.toString(vo)+" m/s");
        aLabel.setText(Float.toString(a)+" m/s²");
        vLabel.setText(Float.toString(v)+" m/s");
        tLabel.setText(Float.toString(t)+" s");
        yAxis.setTickLabelsVisible(true);
        yAxis1.setTickLabelsVisible(true);
        this.makeGraph();
    } 
    private void makeGraph(){
        XYChart.Series series = new XYChart.Series();
        XYChart.Series series1 = new XYChart.Series();
        for(float i=0;i<=this.t;i++){
            series.getData().add(new XYChart.Data(Float.toString(i),(xo+vo*i+(a*i*i)/2)));
            series1.getData().add(new XYChart.Data(Float.toString(i),(vo+a*i)));
        }
        lineChart.getData().addAll(series);
        lineChart1.getData().addAll(series1);
        this.makeAnimation();
    }
    private void cleanGraph(){
        this.lineChart.getData().clear();
        this.lineChart1.getData().clear();
    }
    private void makeAnimation(){//475
        javafx.scene.shape.Path path = new javafx.scene.shape.Path();
        MoveTo moveTo = new MoveTo(42, 23); 
        LineTo cubicCurveTo = new LineTo(520,20); 
        path.getElements().add(moveTo); 
        path.getElements().add(cubicCurveTo);        
        PathTransition pathTransition = new PathTransition(); 
        pathTransition.setDuration(Duration.seconds(t)); 
        pathTransition.setNode(carro); 
        pathTransition.setPath(path);  
        pathTransition.setCycleCount(1); 
        pathTransition.play();
    }
}
