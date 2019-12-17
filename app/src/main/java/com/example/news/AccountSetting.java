package com.example.news;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;

public class AccountSetting extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();

        //checkValues();
        //showUserSettings();
    }

    public static class MyPreferenceFragment extends PreferenceFragment
    {
        @Override
        public void onCreate(final Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.account_settings);
        }
    }

    /*private void showUserSettings() {
        SharedPreferences sharedPrefs= PreferenceManager.getDefaultSharedPreferences(this);
        String username = sharedPrefs.getString("prefUsername", "NULL");
        //boolean sendReport = sharedPrefs.getBoolean("prefSendReport", false);
        String tab1 = sharedPrefs.getString("tab1", "none");
        String tab2 = sharedPrefs.getString("tab2", "none");
        String tab3 = sharedPrefs.getString("tab3", "none");

        String msg = tab1 + " " + tab2 + " " + tab3;

        toast(msg);
    }*/

}
