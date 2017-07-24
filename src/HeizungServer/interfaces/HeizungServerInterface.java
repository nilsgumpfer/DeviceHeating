package HeizungServer.interfaces;



import de.thm.smarthome.global.beans.*;
import de.thm.smarthome.global.enumeration.ResponseCode;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Tim on 07.04.2017.
 */
public interface HeizungServerInterface extends Remote {

    void setGenericName(String new_genericName) throws RemoteException;
    void setDesiredTemperature(MeasureBean new_desiredTemperature) throws RemoteException;
    void setPowerState(PowerStateBean new_powerState) throws RemoteException;
    MeasureBean getCurrentTemperature() throws RemoteException;
    MeasureBean getDesiredTemperature() throws RemoteException;
    ManufacturerBean getManufacturer() throws RemoteException;
    ActionModeBean getActionMode() throws RemoteException;
    PowerStateBean getPowerState() throws RemoteException;
    ModelVariantBean getModelVariant() throws RemoteException;
    String getGenericName() throws RemoteException;
    String getSerialNumber() throws RemoteException;

    /*public void setTemperature(double temperature, HeizungClientInterface c) throws RemoteException;
    public double getTemperature(HeizungClientInterface c) throws RemoteException;
    public String getName(HeizungClientInterface c) throws RemoteException;
    public double getMaxTemperature (HeizungClientInterface c) throws RemoteException;
    public double getMinTemperature (HeizungClientInterface c) throws RemoteException;
    public double getMaxWaterlevel (HeizungClientInterface c) throws RemoteException;
    public double getMinWaterlevel (HeizungClientInterface c) throws RemoteException;
    public boolean setMaxWaterlevel (double max_wl, HeizungClientInterface c) throws RemoteException;
    public boolean setMinWaterlevel (double min_wl, HeizungClientInterface c) throws RemoteException;
    public boolean setMaxTemperature (double max_temp, HeizungClientInterface c) throws RemoteException;
    public boolean setMinTemperature (double min_temp, HeizungClientInterface c) throws RemoteException;
    public void standby(HeizungClientInterface c) throws RemoteException;
    public void wakeUp(HeizungClientInterface c) throws RemoteException;
    public ResponseCode switchOn(HeizungClientInterface c) throws RemoteException;
    public ResponseCode switchOff(HeizungClientInterface c) throws RemoteException;
    public String getStatus(HeizungClientInterface c) throws RemoteException;
    //public void update(AObservable o, Object change, HeizungClientInterface c) throws RemoteException;*/
}
