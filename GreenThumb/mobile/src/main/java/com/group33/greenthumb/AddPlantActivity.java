package com.group33.greenthumb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddPlantActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plant);

        final Spinner waterSpinner = (Spinner) findViewById(R.id.water_spinner);
        final Spinner compostSpinner = (Spinner) findViewById(R.id.compost_spinner);

        Button saveButton = (Button)findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText mName = (EditText) findViewById(R.id.plantName);
                String inputName = mName.getText().toString().trim();
                EditText mWater = (EditText) findViewById(R.id.water);
                String inputWater = mWater.getText().toString();
                EditText mCompost = (EditText) findViewById(R.id.compost);
                String inputCompost = mCompost.getText().toString();

                Integer water = 0;
                Integer compost = 0;

                if (inputName.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please input a plant name", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!inputWater.isEmpty()) {
                    water = numberDays(Integer.parseInt(inputWater), waterSpinner.getSelectedItem().toString());
                } else if (!inputCompost.isEmpty()) {
                    compost = numberDays(Integer.parseInt(inputCompost), compostSpinner.getSelectedItem().toString());
                }

                Plant tempPlant = Plant.getPlant(inputName);
                //don't add a new plant if already exists
                if (tempPlant == null) {
                    Plant newPlant = new Plant(inputName, water, compost);
                    newPlant.save();
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Plant with this name already exists in your list", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public Integer numberDays(Integer value, String unit) {
        switch (unit) {
            case "Days":
                return value;
            case "Months":
                return value * 30;
            case "Years":
                return value * 365;
            default:
                return -1;
        }
    }
}
