package com.group33.greenthumb;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.util.Calendar;
import java.util.List;

public class HomeScreen extends Activity {

    private GoogleApiClient mApiClient;
    private static final String START_ACTIVITY = "/start_activity";

    private void sendMessage( final String path, final String text ) {
        new Thread( new Runnable() {
            @Override
            public void run() {
                NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes( mApiClient ).await();
                for(Node node : nodes.getNodes()) {
                    MessageApi.SendMessageResult result = Wearable.MessageApi.sendMessage(
                            mApiClient, node.getId(), path, text.getBytes() ).await();
                }

            }
        }).start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home_screen);

        ImageView img = (ImageView) findViewById(R.id.imageView);
        Drawable myDrawable = getDrawable(R.drawable.img1);
        img.setImageDrawable(myDrawable);

        mApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .addApi(Wearable.API)
                .build();

        Button taskButton = (Button) findViewById(R.id.task_button);
        taskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(HomeScreen.this, TaskListActivity.class);
                HomeScreen.this.startActivity(myIntent);
            }
        });

        Button clickButton = (Button) findViewById(R.id.plant_button);
        clickButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(HomeScreen.this, PlantListActivity.class);
                HomeScreen.this.startActivity(myIntent);
            }
        });

        Button wearButton = (Button) findViewById(R.id.wear);
        wearButton
                .setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mApiClient.connect();
                Calendar now = Calendar.getInstance();
                String tasks = "";
                List<Task> allTasks = Task.getAllTasks();
                int size = allTasks.size();
                for (int i = 0; i < size; i++) {
                    Task tempTask = allTasks.get(i);
                    tasks += tempTask.name+"%";
                    if (tempTask.type == Task.WATER) {
                        Plant searchPlant = Plant.getPlant(tempTask.name.substring(6, tempTask.name.length()));
                        if (searchPlant != null) {
                            searchPlant.lastWatered = now;
                        }
                    } else if (tempTask.type == Task.HARVEST) {
                        Plant searchPlant = Plant.getPlant(tempTask.name.substring(8, tempTask.name.length()));
                        if (searchPlant != null) {
                            searchPlant.lastHarvested= now;
                        }
                    } else if (tempTask.type == Task.COMPOST) {
                        Plant searchPlant = Plant.getPlant(tempTask.name.substring(8, tempTask.name.length()));
                        if (searchPlant != null) {
                            searchPlant.lastComposted= now;
                        }
                    }
                    tempTask.delete();
                }
                sendMessage(START_ACTIVITY, tasks);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
