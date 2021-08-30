package it.units.ceschia.help.fragment;

import android.os.Bundle;
import android.util.Log;

import androidx.preference.ListPreference;
import androidx.preference.PreferenceFragmentCompat;

import it.units.ceschia.help.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);

        ListPreference listAppName = findPreference("reply");

        listAppName.setOnPreferenceClickListener(preference -> {
            // do something
            Log.i("echo","Preference Clicked!");
            return true;
        });
    }


}