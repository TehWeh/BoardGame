package data;

public interface DataObserver<T extends DataObject> {
    void onUpdate();
}
