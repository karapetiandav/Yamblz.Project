package ru.karapetiandav.yamblzproject.mvp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

public class HolderFragment<T extends IPresenter> extends Fragment {

    public T presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public
    @Nullable
    T getPresenter() {
        return this.presenter;
    }

    public void setPresenter(T presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (this.presenter != null) {
            this.presenter.onDestroy();
        }
    }
}
