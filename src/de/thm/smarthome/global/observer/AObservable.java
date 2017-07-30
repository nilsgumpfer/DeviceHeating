package de.thm.smarthome.global.observer;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nils on 28.01.2017.
 */
public abstract class AObservable {
    private List<IObserver> attachedObservers = new ArrayList<>();

    public void attach(IObserver observer){
        attachedObservers.add(observer);
    }

    public void detach(IObserver observer){
        attachedObservers.remove(observer);
    }

    public void notifyObservers(Object change)  { //TODO: Ab hier nachverfolgen, ob Updates überhaupt beim SHManager ankommen
        for (IObserver element:attachedObservers) {
            try {
                element.update(this,change);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
