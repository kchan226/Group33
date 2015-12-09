package com.group33.greenthumb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Daniel on 12/1/2015.
 */
public class AddTaskActivity extends AppCompatActivity {

    Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.task_toolbar);
        setSupportActionBar(myToolbar);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("New Task");
        }

        Button doneButton = (Button) findViewById(R.id.done_button);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText mName = (EditText) findViewById(R.id.editText);
                String inputName = mName.getText().toString().trim();
                if (inputName.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please input a task name", Toast.LENGTH_SHORT).show();
                    return;
                }
                Task tempTask = Task.getTask(inputName);
                if (tempTask == null) {
                    Task newTask = new Task(inputName);
                    newTask.save();
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Task with this name already exists in your list", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
