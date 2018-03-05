package pl.radoslawgorczyca.animalsheltersosnowiec.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import pl.radoslawgorczyca.animalsheltersosnowiec.R;

/**
 * Created by Radek on 05-Mar-18.
 */

public class LoggingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_logging);
    }
}
