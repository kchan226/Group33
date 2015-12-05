package com.group33.greenthumb;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PlantListActivity extends ListActivity implements
        GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener, View.OnTouchListener {

    String DEBUG_TAG = "Plant List";
    private GestureDetectorCompat mDetector;

    ArrayList<String> plantItems = new ArrayList<>();


    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_list);
        // Instantiate the gesture detector with the
        // application context and an implementation of
        // GestureDetector.OnGestureListener
        mDetector = new GestureDetectorCompat(this,this);
        // Set the gesture detector as the double tap
        // listener.
        mDetector.setOnDoubleTapListener(this);

        adapter = new ArrayAdapter<String>(this, R.layout.plant_listview, plantItems);
        setListAdapter(adapter);
        updatePlantList();

        ListView listView = getListView();
        listView.setTextFilterEnabled(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent i = new Intent(PlantListActivity.this, EditPlantActivity.class);
                i.putExtra("name", ((TextView) view).getText().toString());
                startActivity(i);
            }
        });

    }

    //METHOD WHICH WILL HANDLE DYNAMIC INSERTION
    public void addItems(View v) {
        Intent i = new Intent(this, AddPlantActivity.class);
        startActivityForResult(i, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == 1) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // The user picked a contact.
                // The Intent's data Uri identifies which contact was selected.

                // Do something with the contact here (bigger example below)
                updatePlantList();
            }
        }
    }

    protected void updatePlantList() {
        plantItems.clear();
        List<Plant> allPlants = Plant.getAllPlants();
        int size = allPlants.size();
        for (int i = 0; i < size; i++) {
            plantItems.add(allPlants.get(i).name);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.mDetector.onTouchEvent(event);
        // Be sure to call the superclass implementation
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent event) {
        Log.d(DEBUG_TAG, "onDown: " + event.toString());
        return true;
    }

    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2,
                           float velocityX, float velocityY) {
        Log.d(DEBUG_TAG, "onFling: " + event1.toString()+event2.toString());
        Intent i = new Intent(this, DeletePlantActivity.class);
        startActivityForResult(i, 1);
        return true;
    }

    @Override
    public void onLongPress(MotionEvent event) {
        Log.d(DEBUG_TAG, "onLongPress: " + event.toString());
        finish(); // return to home screen
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                            float distanceY) {
        Log.d(DEBUG_TAG, "onScroll: " + e1.toString()+e2.toString());
        return true;
    }

    @Override
    public void onShowPress(MotionEvent event) {
        Log.d(DEBUG_TAG, "onShowPress: " + event.toString());
    }

    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        Log.d(DEBUG_TAG, "onSingleTapUp: " + event.toString());
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent event) {
        Log.d(DEBUG_TAG, "onDoubleTap: " + event.toString());
        Intent i = new Intent(this, EditPlantActivity.class);
        startActivity(i);
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent event) {
        Log.d(DEBUG_TAG, "onDoubleTapEvent: " + event.toString());
        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent event) {
        Log.d(DEBUG_TAG, "onSingleTapConfirmed: " + event.toString());
        Intent i = new Intent(this, AddPlantActivity.class);
        startActivityForResult(i, 1);
        return true;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mDetector.onTouchEvent(event);
        return false;
    }
}
