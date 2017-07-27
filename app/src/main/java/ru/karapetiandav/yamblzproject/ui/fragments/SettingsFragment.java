package ru.karapetiandav.yamblzproject.ui.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import ru.karapetiandav.yamblzproject.R;

public class SettingsFragment extends PreferenceFragment{

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public void onStart() {
        super.onStart();
        updateSummary();
    }

    private void updateSummary() {
        Preference cityPreference = findPreference(getString(R.string.pref_city_key));
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        cityPreference.setSummary(prefs.getString(getString(R.string.city_name_key),
                getString(R.string.default_city_name)));
    }
}
