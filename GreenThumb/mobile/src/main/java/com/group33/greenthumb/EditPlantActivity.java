package com.group33.greenthumb;

import android.os.Bundle;
import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class EditPlantActivity extends Activity implements View.OnTouchListener{

    String plantName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_plant);
        ImageButton m = (ImageButton) findViewById(R.id.imageButton);
        m.setOnTouchListener(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            plantName = extras.getString("name");
        }

        // TODO: remove post-PROG03
        Plant p = Plant.getPlant("rose");
        if (p != null) {
            p.waterFrequency = 3;
            p.save();

            TextView t = (TextView)findViewById(R.id.plantTextView);
            t.setText(plantName + ": watering changed to every 4 days");
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        finish();
        return false;
    }

}
