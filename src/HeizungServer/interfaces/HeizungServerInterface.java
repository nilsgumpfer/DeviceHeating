package HeizungServer.interfaces;



import HeizungServer.observer.AObservable;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Tim on 07.04.2017.
 */
public interface HeizungServerInterface extends Remote {
    public void setTemperature(double temperature, HeizungClientInterface c) throws RemoteException;
    public double getTemperature(HeizungClientInterface c) throws RemoteException;
    public String getName(HeizungClientInterface c) throws RemoteException;
    //public void update(AObservable o, Object change, HeizungClientInterface c) throws RemoteException;
}
