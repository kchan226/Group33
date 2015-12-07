package com.group33.greenthumb;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class WatchHome extends Activity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_home);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);

                ImageView img = (ImageView) findViewById(R.id.imageView);
                Drawable myDrawable = getDrawable(R.drawable.home);
                img.setImageDrawable(myDrawable);

                Button clickButton = (Button) findViewById(R.id.view_tasks_button);
                clickButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent myIntent = new Intent(WatchHome.this, ViewTasksActivity.class);
                        Intent receivedIntent = getIntent();
                        if (receivedIntent.getBooleanExtra("syncToWear", false)) {
                            myIntent.putExtra("tasks", receivedIntent.getStringExtra("tasks"));
                            myIntent.putExtra("syncToWear", true);
                        }
                        WatchHome.this.startActivity(myIntent);
                    }
                });
            }
        });

    }


}
