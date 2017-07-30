package HeizungServer;

/**
 * Created by Tim on 13.04.2017.
 */
import HeizungServer.interfaces.HeizungClientInterface;
import HeizungServer.interfaces.HeizungServerInterface;
import HeizungServer.observer.AObservable;
import HeizungServer.observer.IObserver;
import de.thm.smarthome.global.beans.*;
import de.thm.smarthome.global.enumeration.*;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.ByteArrayOutputStream;
import java.io.InterruptedIOException;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;
import java.rmi.server.RemoteServer;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by Tim on 07.04.2017.
 */
public class Heizung extends AObservable implements IObserver, HeizungServerInterface {

    /*Attribute/Beans*/

    private MeasureBean currentTemperature = new MeasureBean(0.00, EUnitOfMeasurement.TEMPERATURE_DEGREESCELSIUS);
    private MeasureBean desiredTemperature = new MeasureBean(0.00, EUnitOfMeasurement.TEMPERATURE_DEGREESCELSIUS);
    private ModelVariantBean modelVariant;
    private ManufacturerBean manufacturer;
    private PowerStateBean powerState;
    private ActionModeBean actionModeBean;


    /*Variable*/
    private String genericName = null;
    private String serialNumber = null;
    public String servername = "SmartHomeAPI";
    private String serverIP;

    public String serverstatus = null;
    public int serverport = 1099;
    public ByteArrayOutputStream srvlog = null;
    public Registry rmiRegistry;
    public Double temperature = 0.00;
    public String status = "-";

    public StringProperty heizungstemperatur = new SimpleStringProperty(String.valueOf(currentTemperature.getMeasure_Double())+" "+currentTemperature.getUnitOfMeasurement_String());
    public StringProperty desiredHeatngTemperature = new SimpleStringProperty(String.valueOf(desiredTemperature.getMeasure_Double())+" "+desiredTemperature.getUnitOfMeasurement_String());

    public Heizung() {

    }

    private String getServerIPbyHostName(){
        InetAddress ip;
        try {

            ip = InetAddress.getByName(genericName);
            System.out.println(ip.getHostAddress());
            return ip.getHostAddress();

        } catch (UnknownHostException e) {

            e.printStackTrace();
            return "0.0.0.0";
        }
    }

    public void setTemperature(double temperature, HeizungClientInterface c) {
        this.temperature = temperature;
        String neueTemp = String.valueOf(this.temperature);
        Platform.runLater(new Runnable() {
                              @Override
                              public void run() {
                                  heizungstemperatur.set(neueTemp + " °C");
                              }
            });

        notifyObservers(this.temperature);
    }

    public void setTemperatureSrv(double temperature) {
        this.temperature = temperature;
        String neueTemp = String.valueOf(this.temperature + " °C");
        heizungstemperatur.set(neueTemp);
        notifyObservers(this.temperature);
    }

    @Override
    public MeasureBean getCurrentTemperature() throws RemoteException{

        return currentTemperature;
    }

    @Override
    public MeasureBean getDesiredTemperature() throws RemoteException{

        return desiredTemperature;
    }

    public double getTemperatureSrv() throws RemoteException {

        return this.temperature;
    }

    @Override
    public ModelVariantBean getModelVariant() throws RemoteException{

        return modelVariant;
    }

    @Override
    public ManufacturerBean getManufacturer() throws RemoteException{

        return manufacturer;
    }

    @Override
    public ActionModeBean getActionMode() throws RemoteException{

        return actionModeBean;
    }

    @Override
    public String getGenericName()  throws RemoteException{

        return this.genericName;
    }

    @Override
    public String getSerialNumber()  throws RemoteException{

        return this.serialNumber;
    }

    @Override
    public PowerStateBean getPowerState() throws RemoteException{

        return powerState;
    }

    @Override
    public void setDesiredTemperature(MeasureBean new_desiredTemperature) throws RemoteException{

        desiredTemperature = new_desiredTemperature;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                desiredHeatngTemperature.set(String.valueOf(desiredTemperature.getMeasure_Double()) + " " + desiredTemperature.getUnitOfMeasurement_String());
            }
        });

        if(desiredTemperature.getMeasure_Double() < currentTemperature.getMeasure_Double()){
            abkuehlen();
        }
        else if (desiredTemperature.getMeasure_Double() > currentTemperature.getMeasure_Double()){
        aufheizen();

    }}

    private void setCurrentTemperature(MeasureBean new_currentTemperature) {
        currentTemperature = new_currentTemperature;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                heizungstemperatur.set(String.valueOf(currentTemperature.getMeasure_Double()) + " " + currentTemperature.getUnitOfMeasurement_String());
            }
        });

    }


    private void aufheizen(){

        Thread aufheizen = new Thread(new Runnable() {
            @Override
            public void run() {
                for (double d = currentTemperature.getMeasure_Double(); currentTemperature.getMeasure_Double() < desiredTemperature.getMeasure_Double(); d++) {

                    MeasureBean new_currentTemperature = new MeasureBean(d, currentTemperature.getUnitOfMeasurement_Enum());
                    setCurrentTemperature(new_currentTemperature);

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
            }
        }

       });
    aufheizen.start();
    }

    private void abkuehlen(){

        Thread abkuehlen = new Thread(new Runnable() {
            @Override
            public void run() {
                for(double d = currentTemperature.getMeasure_Double(); currentTemperature.getMeasure_Double() > desiredTemperature.getMeasure_Double(); d--){

                    MeasureBean new_currentTemperature = new MeasureBean(d, currentTemperature.getUnitOfMeasurement_Enum());
                    setCurrentTemperature(new_currentTemperature);

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                    }
                }
            }

        });
        abkuehlen.start();


        }


    @Override
    public void setGenericName(String genericName)throws RemoteException{
        this.genericName = genericName;
    }

    @Override
    public void setPowerState(PowerStateBean new_powerState) throws RemoteException{

        powerState = new_powerState;

    }

    /* @Override
    public double getMaxTemperature(HeizungClientInterface c) throws RemoteException {
        return this.maxTemperature;
    }

    @Override
    public double getMinTemperature(HeizungClientInterface c) throws RemoteException {
        return this.minTemperature;
    }

    @Override
    public double getMaxWaterlevel(HeizungClientInterface c) throws RemoteException {
        return this.maxWaterlevel;
    }

    @Override
    public double getMinWaterlevel(HeizungClientInterface c) throws RemoteException {
        return minWaterlevel;
    }

    @Override
    public boolean setMaxWaterlevel(double max_wl, HeizungClientInterface c) throws RemoteException {
        maxWaterlevel = max_wl;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                maxwaterlevel.set(String.valueOf(maxWaterlevel) + " l");}});
        notifyObservers(this.maxWaterlevel);
        return true;
    }

    public void setMaxWaterlevelSrv(double max_wl){
        maxWaterlevel = max_wl;
        maxwaterlevel.set(String.valueOf(maxWaterlevel) + " l");
    }

    @Override
    public boolean setMinWaterlevel(double min_wl, HeizungClientInterface c) throws RemoteException {
        minWaterlevel = min_wl;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                minwaterlevel.set(String.valueOf(minWaterlevel) + " l");}});
        notifyObservers(this.minWaterlevel);
        return true;
    }

    public void setMinWaterlevelSrv(double min_wl){
        minWaterlevel = min_wl;
        minwaterlevel.set(String.valueOf(minWaterlevel) + " l");
    }

    @Override
    public boolean setMaxTemperature(double max_temp, HeizungClientInterface c) throws RemoteException {
        maxTemperature = max_temp;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                maxheizungstemperatur.set(maxTemperature + " °C");
            }
        });

        notifyObservers(maxTemperature);
        return true;
    }

    public void setMaxTemperatureSrv(double max_temp) {
        maxTemperature = max_temp;
        maxheizungstemperatur.set(String.valueOf(maxTemperature) + " °C");
    }

    @Override
    public boolean setMinTemperature(double min_temp, HeizungClientInterface c) throws RemoteException {
        minTemperature = min_temp;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                minheizungstemperatur.set(minTemperature + " °C");
            }
        });

        notifyObservers(minTemperature);
        return true;
    }

    public void setMinTemperatureSrv(double min_temp) {
        minTemperature = min_temp;
        minheizungstemperatur.set(String.valueOf(minTemperature) + " °C");
    }

    @Override
    public void standby(HeizungClientInterface c) throws RemoteException {
    status = "Standby";
    }

    @Override
    public void wakeUp(HeizungClientInterface c) throws RemoteException {
    status = "On";
    }

    @Override
    public ResponseCode switchOn(HeizungClientInterface c) throws RemoteException {
        if(!status.equals("On")){
            status = "On";
            return ResponseCode.SwitchedOn;
        }
        else{
            return ResponseCode.AlreadySwitchedOn;
        }

    }

    @Override
    public ResponseCode switchOff(HeizungClientInterface c) throws RemoteException {
        if(!status.equals("Off")){
            status = "Off";
            return ResponseCode.SwitchedOff;
        }
        else{
            return ResponseCode.AlreadySwitchedOff;
        }

    }

    @Override
    public String getStatus(HeizungClientInterface c) throws RemoteException {
        return this.status;
    }
*/
    public String getStatusSrv(){
        return this.status;
    }


    public String startServer() throws RemoteException {
        serverIP = getServerIP();
        System.setProperty("java.rmi.server.hostname", serverIP);
        HeizungServerInterface stub = (HeizungServerInterface) UnicastRemoteObject.exportObject(this, 0);
        rmiRegistry = LocateRegistry.createRegistry(serverport);

        try {
            /*if (System.getSecurityManager() == null) {
                System.setProperty("java.security.policy", "file:C:\\Users\\Tim\\IdeaProjects\\HeizungServer\\out\\production\\HeizungServer\\HeizungServer\\server.policy");
                System.setSecurityManager(new SecurityManager());

            }*/
            /*Aktiviert und definiert das Logging des Servers*/
            RemoteServer.setLog(System.out);
            //System.out.println(srvlog.toString());
            /*Bindet den Server an die folgende Adresse*/
            Naming.rebind("//"+serverIP+"/"+servername, this);
            this.serverstatus = "Gestartet";
            status = "On";
            return "Server ist gestartet!";


        } catch (MalformedURLException e) {
            e.printStackTrace();
            return "Fehler beim Starten des Servers!";
        }
        catch (RemoteException rex) {
            rex.printStackTrace();
            return "Fehler beim Starten des Servers!";
        }
    }


    /*@Override
    public String getName() {
        return null;
    }*/

    @Override
    public void update(AObservable o, Object change) {

    }


    /*@Override
    public double getTemperature() {
        return 0;
    }*/

    public String getServerIP() {
        InetAddress ip;
        try {

            ip = InetAddress.getLocalHost();
            return ip.getHostAddress();

        } catch (UnknownHostException e) {

            e.printStackTrace();
            return "0.0.0.0";
        }
    }

    public String stopServer(){
        try {

            //Registry rmiRegistry = LocateRegistry.getRegistry("127.0.0.1", serverport);
            //HeizungServerInterface myService = (HeizungServerInterface) rmiRegistry.lookup(heizungname);

            rmiRegistry.unbind(servername);

            //UnicastRemoteObject.unexportObject(myService, true);
            UnicastRemoteObject.unexportObject(rmiRegistry, true);
            this.serverstatus = "Gestoppt";
            status = "Off";
            return "Server ist gestoppt!";

        } catch (NoSuchObjectException e)
        {
            e.printStackTrace();
            return "Fehler beim Stoppen des Servers!";
        } catch (NotBoundException nbe)
        {
            nbe.printStackTrace();
            return "Fehler beim Stoppen des Servers!";
        } catch (RemoteException rex) {
            rex.printStackTrace();
            return "Fehler beim Stoppen des Servers!";
        }

    }



    /*public double getMaxTemperatureSrv(){
        return this.maxTemperature;
    }


    public double getMinTemperatureSrv() throws RemoteException {
        return this.minTemperature;
    }

    public double getMaxWaterlevelSrv() throws RemoteException {
        return this.maxWaterlevel;
    }

    public double getMinWaterlevelSrv() throws RemoteException {
        return minWaterlevel;
    }*/

    }



