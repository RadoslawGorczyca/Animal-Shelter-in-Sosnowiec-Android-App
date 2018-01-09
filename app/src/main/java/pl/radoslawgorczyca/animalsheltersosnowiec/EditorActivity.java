package pl.radoslawgorczyca.animalsheltersosnowiec;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;

/**
 * Created by Radek on 09-Jan-18.
 */

public class EditorActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        setTitle("Add Pet");

    }
}
