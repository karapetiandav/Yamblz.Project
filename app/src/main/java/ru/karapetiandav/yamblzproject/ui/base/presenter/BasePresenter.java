package ru.karapetiandav.yamblzproject.ui.base.presenter;


public class BasePresenter<V> implements Presenter<V> {

    private V view;

    @Override
    public void onAttach(V view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        view = null;
    }

}
