package com.group33.greenthumb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class AddPlantActivity extends Activity implements View.OnTouchListener{

    public Plant demoPlant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plant);
        ImageButton m = (ImageButton) findViewById(R.id.imageButton);
        m.setOnTouchListener(this);

        // TODO: remove post-PROG03
        TextView v = (TextView)findViewById(R.id.plantTextView);
        TextView w = (TextView)findViewById(R.id.waterTextView);
        String plant = "rose";
        demoPlant = Plant.getPlant(plant);
        if (demoPlant == null) {
            demoPlant = new Plant(plant, 2);
            demoPlant.save();
            v.setText("Plant Added: " + plant);
            w.setText("Watering set to every 2 days");
        } else {
            v.setText("rose already added");
        }

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Intent i = new Intent();
        i.putExtra("exists", true);
        setResult(RESULT_OK, i);
        finish();
        return false;
    }
}
