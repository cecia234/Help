package it.units.ceschia.help.fragment;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import it.units.ceschia.help.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }
}