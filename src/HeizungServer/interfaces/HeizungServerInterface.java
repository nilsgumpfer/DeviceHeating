package HeizungServer.interfaces;



import de.thm.smarthome.global.beans.*;
import de.thm.smarthome.global.observer.IObserver;
// import de.thm.smarthome.global.enumeration.ResponseCode;

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
    void attach(Object observer) throws RemoteException;

}
