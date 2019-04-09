package com.gdunn.owner.todoornottodo;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;


public class prefActivity extends PreferenceActivity
{
    SharedPreferences settings;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();
    }
    public static class MyPreferenceFragment extends PreferenceFragment
    {
        //call XML into the fragment which sits on top of the main "prefsActivity"
        @Override
        public void onCreate(final Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            this.addPreferencesFromResource(R.xml.prefs);
        }

    }
}
