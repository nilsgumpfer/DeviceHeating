package HeizungServer;

/**
 * Created by Tim on 13.04.2017.
 */
import HeizungServer.interfaces.HeizungClientInterface;
import HeizungServer.interfaces.HeizungServerInterface;
import HeizungServer.observer.AObservable;
import HeizungServer.observer.IObserver;
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
    public StringProperty heizungstemperatur = new SimpleStringProperty("0.00 °C");

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
        String neueTemp = String.valueOf(this.temperature);
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

    }



