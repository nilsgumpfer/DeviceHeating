package HeizungServer.observer;

/**
 * Created by Nils on 28.01.2017.
 */
public interface IObserver {
    public void update(AObservable o, Object change);
}
