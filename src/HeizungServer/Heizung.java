package HeizungServer;

/**
 * Created by Tim on 13.04.2017.
 */
import HeizungServer.interfaces.HeizungClientInterface;
import HeizungServer.interfaces.HeizungServerInterface;
import HeizungServer.observer.AObservable;
import HeizungServer.observer.IObserver;
import de.thm.smarthome.global.enumeration.ResponseCode;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.ByteArrayOutputStream;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RemoteServer;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by Tim on 07.04.2017.
 */
public class Heizung extends AObservable implements IObserver, HeizungServerInterface {

    public String heizungname = null;
    public String serverstatus = null;
    public int serverport = 1099;
    public ByteArrayOutputStream srvlog = null;
    public Registry rmiRegistry;
    public Double temperature = 0.00;
    public Double maxTemperature = 0.00;
    public Double minTemperature = 0.00;
    public Double maxWaterlevel = 0.00;
    public Double minWaterlevel = 0.00;
    public String status = "-";

    public StringProperty heizungstemperatur = new SimpleStringProperty("0.00 °C");
    public StringProperty maxheizungstemperatur = new SimpleStringProperty("0.00 °C");
    public StringProperty minheizungstemperatur = new SimpleStringProperty("0.00 °C");
    public StringProperty maxwaterlevel = new SimpleStringProperty("0 l");
    public StringProperty minwaterlevel = new SimpleStringProperty("0 l");

    public Heizung() {

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
    public double getTemperature(HeizungClientInterface c) throws RemoteException {

        return this.temperature;
    }

    public double getTemperatureSrv() throws RemoteException {

        return this.temperature;
    }

    @Override
    public String getName(HeizungClientInterface c) throws RemoteException {

        return this.heizungname;
    }

    @Override
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

    public String getStatusSrv(){
        return this.status;
    }


    public String startServer(String heizungname) throws RemoteException {
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
            Naming.rebind("//127.0.0.1/"+heizungname, this);
            this.heizungname = heizungname;
            this.serverstatus = "Gestartet";
            status = "On";
            return "Server ist gestartet!";


        } catch (MalformedURLException e) {
            e.printStackTrace();
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
            return null;
        }
    }

    public String stopServer(){
        try {

            //Registry rmiRegistry = LocateRegistry.getRegistry("127.0.0.1", serverport);
            //HeizungServerInterface myService = (HeizungServerInterface) rmiRegistry.lookup(heizungname);

            rmiRegistry.unbind(heizungname);

            //UnicastRemoteObject.unexportObject(myService, true);
            UnicastRemoteObject.unexportObject(rmiRegistry, true);
            this.serverstatus = "Gestoppt";
            status = "Off";
            return "Server ist gestoppt!";

        } catch (NoSuchObjectException e)
        {
            e.printStackTrace();
            return "Fehler beim Stoppen des Servers!";
        } catch (NotBoundException e)
        {
            e.printStackTrace();
            return "Fehler beim Stoppen des Servers!";
        } catch (RemoteException e) {
            e.printStackTrace();
            return "Fehler beim Stoppen des Servers!";
        }

    }



    public double getMaxTemperatureSrv(){
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
    }

    }



