package com.varejodigital.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceFragment;


import com.varejodigital.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends PreferenceFragment {

    public static SettingsFragment newInstance() {
        return (new SettingsFragment());
    }


    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.prefs);
    }

}
