package ru.karapetiandav.yamblzproject.ui.base;


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

    @Override
    public boolean isAttached() {
        return view != null;
    }
}
