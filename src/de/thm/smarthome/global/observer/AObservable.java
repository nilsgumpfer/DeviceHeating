package de.thm.smarthome.global.observer;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public abstract class AObservable {
    private List<Object> attachedObservers = new ArrayList<>();
    public boolean changed = true;

    public void attach(Object observer){
        attachedObservers.add((IObserver)observer);
    }

    public void detach(Object observer){
        attachedObservers.remove(observer);
    }

    public void notifyObservers(Object change)  { //TODO: Ab hier nachverfolgen, ob Updates überhaupt beim SHManager ankommen




        for (Object element : attachedObservers) {
            try {
                IObserver myO = (IObserver) element;
                myO.update(this, change);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

    }

}


