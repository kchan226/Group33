package com.group33.greenthumb;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddPlantActivity extends AppCompatActivity {

    Plant plant;
    Boolean edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plant);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }

        myToolbar.setOnMenuItemClickListener(
                new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        // Handle menu item click event
                        if (item.getTitle().equals("delete")) {
                            new AlertDialog.Builder(AddPlantActivity.this)
                                    .setMessage("Are you sure you want to delete " + plant.name + "?")
                                    .setCancelable(false)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            plant.delete();
                                            Intent intent = new Intent();
                                            setResult(RESULT_OK, intent);
                                            finish();
                                        }
                                    })
                                    .setNegativeButton("No", null)
                                    .show();
                        }
                        return true;
                    }
                });


        final Spinner waterSpinner = (Spinner) findViewById(R.id.water_spinner);
        final Spinner compostSpinner = (Spinner) findViewById(R.id.compost_spinner);
        final Spinner harvestSpinner = (Spinner) findViewById(R.id.harvest_spinner);

        String toolbarTitle;
        //if we're editing a plant
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String plantName = extras.getString("name");
            plant = Plant.getPlant(plantName);
            edit = true;
            toolbarTitle = "Edit Plant";
        } else {
            edit = false;
            toolbarTitle = "New Plant";
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(toolbarTitle);
        }


        //if we're editing a plant
        if (plant != null) {
//            TextView title = (TextView) findViewById(R.id.addPlant);
//            title.setText("Edit Plant");
            TextView pName = (TextView) findViewById(R.id.plantName);
            pName.setText(plant.name);
            if (plant.waterFrequency > 0) {
                TextView waterField = (TextView) findViewById(R.id.water);
                waterField.setText(String.format("%d", plant.waterFrequency));
            }
            if (plant.compostFrequency > 0) {
                TextView compostField = (TextView) findViewById(R.id.compost);
                compostField.setText(String.format("%d", plant.compostFrequency));
            }
            if (plant.harvestFrequency > 0) {
                TextView harvestField = (TextView) findViewById(R.id.harvest);
                harvestField.setText(String.format("%d", plant.harvestFrequency));
            }
            if (plant.soilpH > 0.0) {
                TextView pHField = (TextView) findViewById(R.id.pH);
                pHField.setText(String.format("%.1f", plant.soilpH));
            }
            if (plant.sowDepth > 0.0) {
                TextView depthField = (TextView) findViewById(R.id.sowDepth);
                depthField.setText(String.format("%.1f", plant.sowDepth));
            }
        }

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
                EditText mHarvest = (EditText) findViewById(R.id.harvest);
                String inputHarvest = mHarvest.getText().toString();
                EditText mpH = (EditText) findViewById(R.id.pH);
                String inputpH = mpH.getText().toString();
                EditText mDepth = (EditText) findViewById(R.id.sowDepth);
                String inputDepth = mDepth.getText().toString();


                Integer water = 0;
                Integer compost = 0;
                Integer harvest = 0;
                double pH = 0.0;
                double sowDepth = 0.0;

                if (inputName.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please input a plant name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!inputWater.isEmpty()) {
                    water = numberDays(Integer.parseInt(inputWater), waterSpinner.getSelectedItem().toString());
                    if (water < 0) {
                        Toast.makeText(getApplicationContext(), "Watering frequency cannot be negative", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if (!inputCompost.isEmpty()) {
                    compost = numberDays(Integer.parseInt(inputCompost), compostSpinner.getSelectedItem().toString());
                    if (compost < 0) {
                        Toast.makeText(getApplicationContext(), "Composting frequency cannot be negative", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if (!inputHarvest.isEmpty()) {
                    harvest = numberDays(Integer.parseInt(inputHarvest), harvestSpinner.getSelectedItem().toString());
                    if (harvest < 0) {
                        Toast.makeText(getApplicationContext(), "Harvesting frequency cannot be negative", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if (!inputpH.isEmpty()) {
                    pH = Double.parseDouble(inputpH);
                    if (pH < 0.0 || pH > 14.0) {
                        Toast.makeText(getApplicationContext(), "Soil pH must be between 0 and 14", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if (!inputDepth.isEmpty()) {
                    sowDepth = Double.parseDouble(inputDepth);
                    if (sowDepth < 0.0) {
                        Toast.makeText(getApplicationContext(), "Sow depth cannot be negative", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                Plant tempPlant = Plant.getPlant(inputName);
                //don't add a new plant if already exists
                if (tempPlant == null && !edit) {
                    Plant newPlant = new Plant(inputName, water, compost, harvest, pH, sowDepth);
                    newPlant.save();
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                } else if (edit) {
                    plant.name = inputName;
                    plant.waterFrequency = water;
                    plant.compostFrequency = compost;
                    plant.harvestFrequency = harvest;
                    plant.soilpH = pH;
                    plant.sowDepth = sowDepth;
                    plant.save();
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Plant with this name already exists in your list", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        if (edit) {
            getMenuInflater().inflate(R.menu.edit_plant_menu, menu);
        }
        return super.onCreateOptionsMenu(menu);
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
