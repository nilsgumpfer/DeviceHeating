package de.thm.smarthome.global.observer;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Nils on 28.01.2017.
 */
public interface IObserver extends Remote{
    public void update(Object o, java.lang.Object change) throws RemoteException;
}
