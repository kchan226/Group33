package com.group33.greenthumb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

/**
 * Created by Daniel on 12/1/2015.
 */
public class TaskListActivity extends Activity {

    ArrayList<String> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        Button deleteButton = (Button) findViewById(R.id.minus_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

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

        String[] tasks = new String[] {"[Example Task], [Example Task], [Example Task]"};
        taskList = new ArrayList<String>();
        taskList.addAll(Arrays.asList(tasks));
        ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, taskList);

        //listAdapter.add("task");
        taskListView.setAdapter(listAdapter);

        taskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        //TODO: remove post-PROG03
        Plant rose = Plant.getPlant("rose");
        if (rose != null) {
            Calendar past = rose.lastWatered;
            Calendar rightNow = Calendar.getInstance();
            long daysPassed = (past.getTimeInMillis() - rightNow.getTimeInMillis()) / (24 * 60 * 60 * 1000);

            //for the sake of the demo, comment out the if statement
//            if (daysPassed > rose.waterFrequency) {
            TextView v = (TextView) findViewById(R.id.textView2);
            v.setText("Water Roses");
//            }
        }

    }
}
