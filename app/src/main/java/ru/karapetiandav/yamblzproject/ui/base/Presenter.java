package ru.karapetiandav.yamblzproject.ui.base;


public interface Presenter<V> {
    void onAttach(V view);
    void onDetach();
    boolean isAttached();
}
