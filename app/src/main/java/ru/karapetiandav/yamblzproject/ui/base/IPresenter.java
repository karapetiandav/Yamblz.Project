package ru.karapetiandav.yamblzproject.ui.base;

import android.support.annotation.Nullable;

public abstract class IPresenter<T extends IView> {

    private T view;

    public void onAttach(T view) {
        this.view = view;
    }

    public void onDetach() {
        this.view = null;
    }

    public void onDestroy() {

    }

    protected
    @Nullable
    T getView() {
        return view;
    }
}
