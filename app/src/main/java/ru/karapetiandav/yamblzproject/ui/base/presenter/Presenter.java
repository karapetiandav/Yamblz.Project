package ru.karapetiandav.yamblzproject.ui.base.presenter;


public interface Presenter<V> {
    void onAttach(V view);
    void onDetach();
}
