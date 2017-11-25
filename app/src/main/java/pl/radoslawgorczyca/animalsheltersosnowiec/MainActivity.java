package pl.radoslawgorczyca.animalsheltersosnowiec;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_view);


        ArrayList<Animal> animals = new ArrayList<>();

        animals.add(new Animal("Reksio", "Rezerwacja", R.mipmap.dog1));

        AnimalAdapter adapter =
                new AnimalAdapter(this, animals);

        GridView gridView = findViewById(R.id.grid_view);

        gridView.setAdapter(adapter);



    }
}
