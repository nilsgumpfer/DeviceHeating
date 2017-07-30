package HeizungServer;

import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.io.PrintStream;
import java.io.OutputStream;
import java.rmi.RemoteException;
import java.util.Optional;


public class Controller {


    @FXML
    private Label lbl_Serverip;
    @FXML
    private Label lbl_Servername;
    @FXML
    private Label lbl_Serverport;
    @FXML
    private Label lbl_Serverstatus;
    @FXML
    private Label lbl_srvmsg;
    @FXML
    private Button btn_starteServer;
    @FXML
    private Button btn_stoppeServer;
    @FXML
    private TextArea ta_srvlog;


    @FXML
    private Label lbl_temp;
    @FXML
    private Label lbl_desiredtemp;

    public static PrintStream ps;

    public Heizung heiz1 = null;


    public void BTNServerStarten(ActionEvent event) throws IOException {
        /*TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Name der Heizung definieren");
        dialog.setHeaderText("Heizung anlegen");
        dialog.setContentText("Bitte dieser Heizung einen Namen geben:");

        if (!(lbl_Servername.getText().equals("-"))) {

        } else {
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent() == true && !result.get().equals("")) {
                lbl_Servername.setText(result.get());
            } else {
                return;
            }
        }*/

        if (heiz1 == null) {
            heiz1 = new Heizung();
        } else {
            heiz1 = new Heizung();
        }

        ps = new PrintStream(new OutputStream() {

            @Override
            public void write(int i) throws IOException {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        ta_srvlog.appendText(String.valueOf((char) i));
                    }
                });


            }
        });
        System.setOut(ps);

        /*Server wird gestartet*/

        lbl_srvmsg.setText(heiz1.startServer());
        lbl_Serverip.setText(heiz1.getServerIP());
        lbl_Servername.setText(heiz1.servername);
        lbl_Serverstatus.setText(heiz1.serverstatus);

        lbl_temp.textProperty().bind(heiz1.heizungstemperatur);
        lbl_desiredtemp.textProperty().bind(heiz1.desiredHeatngTemperature);


        StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(heiz1.serverport);
        String srvport = sb.toString();

        lbl_Serverport.setText(srvport);

        if (lbl_Serverstatus.getText() == "Gestartet") {
            btn_starteServer.setDisable(true);
            btn_stoppeServer.setDisable(false);
        }
    }

    public void BTNServerStoppen(ActionEvent event) {
        lbl_srvmsg.setText(heiz1.stopServer());
        lbl_Serverstatus.setText(heiz1.serverstatus);

        if (lbl_Serverstatus.getText() == "Gestoppt") {
            btn_stoppeServer.setDisable(true);
            btn_starteServer.setDisable(false);
        }
    }

    public void BTNSetTemp(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Temperatur einstellen");
        dialog.setHeaderText("Temperatur der Heizung einstellen");
        dialog.setContentText("Bitte Temperatur der Heizung einstellen:");
        Optional<String> result = dialog.showAndWait();

        if (result.isPresent() == true && !result.get().equals("")) {
            Double newTemp = Double.parseDouble(result.get());
            System.out.println(newTemp);
            heiz1.setTemperatureSrv(newTemp);
        } else {
            return;
        }
    }
}

    /*public void BTNSetMaxTemp(ActionEvent event){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Maximale Temperatur einstellen");
        dialog.setHeaderText("Maximale Temperatur der Heizung einstellen");
        dialog.setContentText("Bitte maximale Temperatur der Heizung einstellen:");
        Optional<String> result = dialog.showAndWait();

        if(result.isPresent() == true && !result.get().equals("")) {
            Double newTemp = Double.parseDouble(result.get());
            System.out.println(newTemp);
            heiz1.setMaxTemperatureSrv(newTemp);
        }
        else{
            return;
        }
    }*/

    /*public void BTNSetMinTemp(ActionEvent event){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Minimalste Temperatur einstellen");
        dialog.setHeaderText("Minimalste Temperatur der Heizung einstellen");
        dialog.setContentText("Bitte minimalste Temperatur der Heizung einstellen:");
        Optional<String> result = dialog.showAndWait();

        if(result.isPresent() == true && !result.get().equals("")) {
            Double newTemp = Double.parseDouble(result.get());
            System.out.println(newTemp);
            heiz1.setMinTemperatureSrv(newTemp);
        }
        else{
            return;
        }
    }

    /*public void BTNSetMaxWL(ActionEvent event){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Maximales Wasserlevel einstellen");
        dialog.setHeaderText("Maximalstes Wasserlevel der Heizung einstellen");
        dialog.setContentText("Bitte maximalstes Wasserlevel der Heizung einstellen:");
        Optional<String> result = dialog.showAndWait();

        if(result.isPresent() == true && !result.get().equals("")) {
            Double newMaxWL = Double.parseDouble(result.get());
            System.out.println(newMaxWL);
            heiz1.setMaxWaterlevelSrv(newMaxWL);
        }
        else{
            return;
        }
    }*/

    /*public void BTNSetMinWL(ActionEvent event){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Minimales Wasserlevel einstellen");
        dialog.setHeaderText("Minimalstes Wasserlevel der Heizung einstellen");
        dialog.setContentText("Bitte minimalstes Wasserlevel der Heizung einstellen:");
        Optional<String> result = dialog.showAndWait();

        if(result.isPresent() == true && !result.get().equals("")) {
            Double newMinWL = Double.parseDouble(result.get());
            System.out.println(newMinWL);
            heiz1.setMinWaterlevelSrv(newMinWL);
        }
        else{
            return;
        }
    }
}*/
