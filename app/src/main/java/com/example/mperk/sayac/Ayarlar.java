package com.example.mperk.sayac;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by MPERK on 24.11.2015.
 */
public class Ayarlar extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.ayarlar);
    }
}
