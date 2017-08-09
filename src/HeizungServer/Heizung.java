package HeizungServer;

/**
 * Created on 13.04.2017.
 */
import HeizungServer.interfaces.HeizungClientInterface;
import HeizungServer.interfaces.HeizungServerInterface;
import de.thm.smarthome.global.beans.*;
import de.thm.smarthome.global.enumeration.EUnitOfMeasurement;
import de.thm.smarthome.global.observer.AObservable;
import de.thm.smarthome.global.observer.IObserver;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.ByteArrayOutputStream;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RemoteServer;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created on 07.04.2017.
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
    private HeizungServerInterface stub = null;

    public String serverstatus = null;
    public int serverport = 1099;
    public ByteArrayOutputStream srvlog = null;
    public Registry rmiRegistry;
    public String status = "-";

    /*Bindung an Attribut*/
    public StringProperty heizungstemperatur = new SimpleStringProperty(String.valueOf(currentTemperature.getMeasure_Double())+" "+currentTemperature.getUnitOfMeasurement_String());
    public StringProperty desiredHeatngTemperature = new SimpleStringProperty(String.valueOf(desiredTemperature.getMeasure_Double())+" "+desiredTemperature.getUnitOfMeasurement_String());

    public Heizung() {

    }

    @Override
    public MeasureBean getCurrentTemperature() throws RemoteException{

        return currentTemperature;
    }

    @Override
    public MeasureBean getDesiredTemperature() throws RemoteException{

        return desiredTemperature;
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
                    notifyObservers(currentTemperature);

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        System.out.print(e.toString());
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
                    notifyObservers(desiredTemperature);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                    System.out.print(e.toString());
                    }
                }
            }

        });
        abkuehlen.start();


        }


    @Override
    public void setGenericName(String genericName)throws RemoteException{
        this.genericName = genericName;
        notifyObservers(genericName);
    }

    @Override
    public void setPowerState(PowerStateBean new_powerState) throws RemoteException{

        powerState = new_powerState;
        notifyObservers(powerState);

    }


    public String startServer() throws RemoteException {
        serverIP = getServerIP();
        System.setProperty("java.rmi.server.hostname", serverIP);
        if(stub == null) {
            /*exportObject erzeugt stub für Objekt und exprtet dies über TCP Port
            * Port 0 bedeutet, dass das System selbst einen Port generiert der benutzt wird*/
            stub = (HeizungServerInterface) UnicastRemoteObject.exportObject(this, 0);

        }
        /*erstellt Namensdienst (Registry) auf Port (hier Standardport 1099 -> ist oben definiert)*/
        rmiRegistry = LocateRegistry.createRegistry(serverport);
        try {

            /*Aktiviert und definiert das Logging des Servers*/
            RemoteServer.setLog(System.out);
            /*Bindet den Server an die folgende Adresse und registriert Objekt am Nameserver*/
            Naming.rebind("//"+serverIP+"/"+servername, this);
            this.serverstatus = "Gestartet";
            status = "On";
            return "Server ist gestartet!";


        } catch (MalformedURLException e) {
           System.out.print(e.toString());
            return "Fehler beim Starten des Servers!";
        }
        catch (RemoteException rex) {
            System.out.print(rex.toString());
            return "Fehler beim Starten des Servers!";
        }
    }


    @Override
    public void update(Object o, java.lang.Object change) {

    }


    public String getServerIP() {
        InetAddress ip;
        try {

            ip = InetAddress.getLocalHost();
            return ip.getHostAddress();

        } catch (UnknownHostException e) {

            System.out.print(e.toString());
            return "0.0.0.0";
        }
    }

    public String stopServer(){
        try {

            rmiRegistry.unbind(servername);

            UnicastRemoteObject.unexportObject(rmiRegistry, true);
            this.serverstatus = "Gestoppt";
            status = "Off";
            return "Server ist gestoppt!";

        } catch (NoSuchObjectException e)
        {
           System.out.print(e.toString());
            return "Fehler beim Stoppen des Servers!";
        } catch (NotBoundException nbe)
        {
           System.out.print(nbe.toString());
            return "Fehler beim Stoppen des Servers!";
        } catch (RemoteException rex) {
           System.out.print(rex.toString());
            return "Fehler beim Stoppen des Servers!";
        }

    }

    }



