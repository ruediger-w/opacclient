package de.geeksfactory.opacclient.frontend;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceFragmentCompat;
import de.geeksfactory.opacclient.R;

public class MainPreferenceActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportFragmentManager().beginTransaction()
                                   .replace(R.id.content_frame, getNewMainPreferenceFragment())
                                   .commit();
    }

    protected PreferenceFragmentCompat getNewMainPreferenceFragment() {
        return new MainPreferenceFragment();
    }
}
