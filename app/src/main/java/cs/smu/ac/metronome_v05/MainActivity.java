package cs.smu.ac.metronome_v05;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {

    MetronomeThread metronomeThread; //= new MetronomeThread();
    Spinner spinner;
    ArrayList<Integer> arr = new ArrayList<>();
    ImageView imageView;
    ToggleButton playNstop;

    int spinnerBPM = 60;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);

        playNstop = (ToggleButton) findViewById(R.id.toggleButton);


        spinner = findViewById(R.id.bpmSpinner);
        arr.add(60);
        arr.add(80);
        arr.add(100);
        arr.add(110);
        arr.add(120);
        arr.add(130);
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, arr);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                spinnerBPM = ((Integer) parent.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        playNstop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //metronomeThread = new MetronomeThread();

                if (playNstop.isChecked()) {
                    metronomeThread = new MetronomeThread();

                    metronomeThread.setBpm(spinnerBPM);
                    metronomeThread.setImageView(imageView);
                    metronomeThread.start();

                    playNstop.setChecked(true);

                } else {
                    if (metronomeThread.isPlaying()) {
                        metronomeThread.setPlaying(false);
                        metronomeThread.interrupt();
                        metronomeThread = null;
                        playNstop.setChecked(false);
                    }

                    imageView.setImageResource(R.drawable.a1);
                }
            }
        });


    }
}
