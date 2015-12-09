package com.group33.greenthumb;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TaskStackBuilder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Daniel on 12/1/2015.
 */
public class TaskListActivity extends AppCompatActivity implements
        GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener, View.OnTouchListener{

    String DEBUG_TAG = "Task List";
    private GestureDetectorCompat mDetector;
    private Menu mMenu;
    private String curQuery = "";
    ArrayList<String> taskList = new ArrayList<String>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        mDetector = new GestureDetectorCompat(this,this);
        mDetector.setOnDoubleTapListener(this);
        adapter = new ArrayAdapter<String>(this, R.layout.plant_listview, taskList);
        //String[] tasks = new String[] {"Water Roses", "Water Daisies", "Harvest Strawberries"};
        //taskList.addAll(Arrays.asList(tasks));
        //listAdapter.add("task");

        Button addButton = (Button) findViewById(R.id.plus_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(TaskListActivity.this, AddTaskActivity.class);
                myIntent.putExtra("taskList", taskList);
                TaskListActivity.this.startActivity(myIntent);
            }
        });

        ListView taskListView = (ListView) findViewById(R.id.taskListView);
        taskListView.setAdapter(adapter);
        taskListView.setTextFilterEnabled(true);
        registerForContextMenu(taskListView);

        updateTaskList("");
        adapter.notifyDataSetChanged();
        List<Plant> allPlants = Plant.getAllPlants();
        int size = allPlants.size();
        Calendar now = Calendar.getInstance();
        long rightNow = now.getTimeInMillis();
        for (int i = 0; i < size; i++) {
            Plant temp = allPlants.get(i);
            Log.d(temp.name, ""+temp.waterFrequency);
            if (temp.lastWatered == null) {
                temp.lastWatered = now;
            } else if ((temp.lastWatered.getTimeInMillis() - rightNow) / (24 * 60 * 60 * 1000) >= temp.waterFrequency) {
                String newTaskName = "Water " + temp.name;
                Task tempTask = Task.getTask(newTaskName);
                if (tempTask == null) {
                    Task newTask = new Task(newTaskName);
                    newTask.type = Task.WATER;
                    newTask.save();
                }
            }
            if (temp.lastHarvested == null) {
                temp.lastHarvested = now;
            } else if ((temp.lastHarvested.getTimeInMillis() - rightNow) / (24 * 60 * 60 * 1000) >= temp.harvestFrequency) {
                String newTaskName = "Harvest " + temp.name;
                Task tempTask = Task.getTask(newTaskName);
                if (tempTask == null) {
                    Task newTask = new Task(newTaskName);
                    newTask.type = Task.HARVEST;
                    newTask.save();
                }
            }
            if (temp.lastComposted == null) {
                temp.lastComposted = now;
            } else if ((temp.lastComposted.getTimeInMillis() - rightNow) / (24 * 60 * 60 * 1000) >= temp.compostFrequency) {
                String newTaskName = "Compost " + temp.name;
                Task tempTask = Task.getTask(newTaskName);
                if (tempTask == null) {
                    Task newTask = new Task(newTaskName);
                    newTask.type = Task.COMPOST;
                    newTask.save();
                }
            }
        }
        updateTaskList("");
        adapter.notifyDataSetChanged();

        Toolbar myToolbar = (Toolbar) findViewById(R.id.task_toolbar);
        setSupportActionBar(myToolbar);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Your Tasks");
        }

        //TODO: remove post-PROG03
        //Plant rose = Plant.getPlant("rose");
        //if (rose != null) {
        //    Calendar past = rose.lastWatered;
        //    Calendar rightNow = Calendar.getInstance();
        //    long daysPassed = (past.getTimeInMillis() - rightNow.getTimeInMillis()) / (24 * 60 * 60 * 1000);

            //for the sake of the demo, comment out the if statement
        //    if (daysPassed > rose.waterFrequency) {
            //TextView v = (TextView) findViewById(R.id.textView2);
            //v.setText("Water Roses");
        //    }
        //}

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.task_actions, menu);
    }

    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final String taskName = taskList.get(info.position);
        final int index = info.position;
        Intent i;
        switch(item.getItemId()){
            case R.id.delete:
                new AlertDialog.Builder(this)
                        .setMessage("Are you sure you want to delete " + taskName + "?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Task task = Task.getTask(taskName);
                                task.delete();
                                //Plant plant = Plant.getPlant(plantName);
                                //plant.delete();
                                //  plantItems.remove(index);
                                //  adapter.notifyDataSetChanged();
                                updateTaskList(curQuery);
                                Toast.makeText(getApplicationContext(), "Deleted " + taskName, Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                break;
        }
        return true;
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
                updateTaskList("");
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateTaskList("");
    }

    protected void updateTaskList(String query) {
        taskList.clear();
        List<Task> allTasks;
        if (query.isEmpty()) {
            allTasks = Task.getAllTasks();
        } else {
            allTasks = Task.search(query);
        }
        int size = allTasks.size();
        for (int i = 0; i < size; i++) {
            taskList.add(allTasks.get(i).name);
        }
        adapter.notifyDataSetChanged();

        TextView empty = (TextView) findViewById(R.id.empty2);
        if (query.isEmpty()) {
            empty.setText(getResources().getString(R.string.emptyTaskList));
        }
        /*
        else {
            empty.setText("No plants match your search");
        }
        */
        if (taskList.size() == 0) {
            empty.setVisibility(View.VISIBLE);
        } else {
            empty.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
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
        Log.d(DEBUG_TAG, "onFling: " + event1.toString() + event2.toString());
        return true;
    }

    @Override
    public void onLongPress(MotionEvent event) {
        Log.d(DEBUG_TAG, "onLongPress: " + event.toString());
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
        return true;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mDetector.onTouchEvent(event);
        return false;
    }
}
