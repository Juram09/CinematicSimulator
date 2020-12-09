
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
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Juan
 */
public class MParabolicoController implements Initializable {

    @FXML
    private TextField tTxt;
    @FXML
    private TextField bTxt;
    @FXML
    private TextField vTxt;
    @FXML
    private Button calcButton;
    @FXML
    private Label tLabel;
    @FXML
    private Label bLabel;
    @FXML
    private Label vLabel;
    @FXML
    private Label hLabel;
    @FXML
    private Label xLabel;
    @FXML
    private Label xLabel1;
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
    private Button exitButton;
    @FXML
    private Button backButton;
    
    public float t, b, v, g=(float) 9.8, ymax, d;
    @FXML
    private ImageView ball;
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
    private void goMPSol(ActionEvent event) throws IOException {
        try{
            if(!bTxt.getText().equals("")){
                this.b=Float.parseFloat(bTxt.getText());
            }else{
                this.b=0;
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
            makeCalculates(this.b, this.v, this.t, this.g, event);
        }catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Imposible de realizar la operacion");
            alert.setContentText("Los campos son unicamente de numeros, para usar decimales se usa el punto (.)");
            alert.showAndWait(); 
        }  
    }
    
    private void makeCalculates(float b, float v, float t, float g, ActionEvent event) throws IOException{
        if((t==0 && b==0 && v==0) || (t!=0 && b==0 && v==0) || (t==0 && b!=0 && v==0) || (t==0 && b==0 && v!=0)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("No es posible calcular y graficar");
            alert.setContentText("No hay suficiente informacion para calcular o la informacion suministrada no es correcta");
            alert.showAndWait();
        }else if((b!=0 && v!=0 && t!=0) && (v!=g*t/(2*Math.sin(Math.toRadians(b))))){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("No es posible calcular y graficar");
            alert.setContentText("Se han introducido todos los valores, sin embargo, los datos entregados son erroneos");
            alert.showAndWait();
        }else{
            for(int i=0;i<2;i++){
                if(b==0){
                   if((g*t)/(2*v)>1 || (g*t)/(2*v)<-1){
                       Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText(null);
                        alert.setTitle("No es posible calcular y graficar");
                        alert.setContentText("Se han introducido todos los valores, sin embargo, los datos entregados son erroneos");
                        alert.showAndWait();
                   }else{
                       b=(float) Math.toDegrees(Math.asin((g*t)/(2*v)));
                       this.sets(t, b, v);
                   }
                }
                if(v==0){
                    v=(float) ((g*t)/(2*Math.sin(Math.toRadians(b))));
                    this.sets(t, b, v);
                }
                if(t==0){
                    t=(float) ((2*v*Math.sin(Math.toRadians(b)))/g);
                    this.sets(t, b, v);
                }
            }
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
    
    private void sets(float t, float b, float v){
        float ymax = (float) ((v*v*Math.sin(Math.toRadians(b))*Math.sin(Math.toRadians(b)))/(2*this.g));
        float d = (float) ((v*v*Math.sin(Math.toRadians(2*b)))/this.g);
        this.cleanGraph();
        this.t=t;
        this.b=b;
        this.v=v;
        this.ymax=ymax;
        this.d=d;
        bTxt.setText("");
        vTxt.setText("");
        tTxt.setText("");
        xLabel.setText(Float.toString(d)+" m");
        bLabel.setText(Float.toString(b)+"°");
        xLabel1.setText(Integer.toString((int) d)+" m");
        vLabel.setText(Float.toString(v)+" m/s");
        tLabel.setText(Float.toString(t)+" s");
        hLabel.setText(Float.toString(ymax)+" m");
        yAxis.setTickLabelsVisible(true);
        yAxis1.setTickLabelsVisible(true);
        this.makeGraph();
    }
    private void makeGraph(){
        XYChart.Series series = new XYChart.Series();
        XYChart.Series series1 = new XYChart.Series();
        for(float i=0;i<=this.t;i+=this.t/10){
            series.getData().add(new XYChart.Data(Float.toString(i), v*i*Math.cos(Math.toRadians(b))));
            series1.getData().add(new XYChart.Data(Float.toString(i),(v*Math.sin(Math.toRadians(b))*i-(g*i*i)/2)));
        }
        lineChart1.getData().addAll(series);
        lineChart.getData().addAll(series1);
        this.makeAnimation();
    }
    private void cleanGraph(){
        this.lineChart.getData().clear();
        this.lineChart1.getData().clear();
    }
    private void makeAnimation(){
        ball.setVisible(true);
        javafx.scene.shape.Path path = new javafx.scene.shape.Path(); 
        MoveTo moveTo = new MoveTo(10, 10); 
        CubicCurveTo cubicCurveTo = new CubicCurveTo(30, -83, 413, -83, 500, 40); 
        path.getElements().add(moveTo); 
        path.getElements().add(cubicCurveTo);        
        PathTransition pathTransition = new PathTransition(); 
        pathTransition.setDuration(Duration.seconds(t/3)); 
        pathTransition.setNode(ball); 
        pathTransition.setPath(path);  
        pathTransition.setCycleCount(1); 
        pathTransition.play(); 
    }
}
