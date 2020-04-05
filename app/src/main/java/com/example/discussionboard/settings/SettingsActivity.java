package com.example.discussionboard.settings;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.SwitchPreference;

import com.example.discussionboard.R;


public class SettingsActivity extends PreferenceActivity {

    public boolean isOn;
    ListPreference list;
    ListPreference listPreference = (ListPreference) findPreference("");
    Preference preference;
    SwitchPreference notif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        list = (ListPreference) findPreference("languageType");

        preference = (Preference) findPreference("about");

        notif = (SwitchPreference) findPreference("switch");

        notif.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                if (notif.isChecked()) {
                    System.out.println("True");
                    isOn = true;
                    return true;
                }
                System.out.println("False");
                isOn = false;
                return false;
            }
        });

    }
}
