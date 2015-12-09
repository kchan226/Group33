package com.group33.greenthumb;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.wearable.view.WatchViewStub;
import android.util.FloatMath;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewTasksActivity extends Activity implements   GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener, View.OnTouchListener, SensorEventListener {

    private static final double SHAKE_THRESHOLD = 1.8f;
    private static final int SHAKE_WAIT_TIME_MS = 250;
    private static final double ROTATION_THRESHOLD = 2.0f;
    private static final int ROTATION_WAIT_TIME_MS = 100;

    private View mView;
    private TextView mTextTitle;
    private TextView mTextValues;
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private int mSensorType;
    private long mShakeTime = 0;
    private long mRotationTime = 0;

    private TextView mTextView;
    String DEBUG_TAG = "View Task";
    private GestureDetectorCompat mDetector;
    private String[] tasksList;
    private int taskIdx = 0;
    private boolean taskFinished = false;
    private Intent receivedIntent;
    private ViewTasksActivity x  = this;
    private Button backButton;
    private Button exitButton;
    private static final int SWIPE_THRESHOLD = 100;
    private static final int SWIPE_VELOCITY_THRESHOLD = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = savedInstanceState;
        if(args != null) {
            mSensorType = args.getInt("sensorType");
        }
        mSensorType = 1;
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(mSensorType);

        setContentView(R.layout.activity_view_tasks);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                backButton = (Button) findViewById(R.id.back_button);
                exitButton = (Button) findViewById(R.id.exit_button);

                mTextView = (TextView)findViewById(R.id.textView);
                receivedIntent = getIntent();
                String tasksStr = receivedIntent.getStringExtra("tasks");
                if (receivedIntent.getBooleanExtra("syncToWear", false) && !tasksStr.equals("")) {
                    tasksList = tasksStr.split("%");

                    mTextView.setText(tasksList[taskIdx]);


                    ImageButton m = (ImageButton) findViewById(R.id.imageButton);
                    m.setOnTouchListener(x);
                    mDetector = new GestureDetectorCompat(x, x);
                    // Set the gesture detector as the double tap
                    // listener.
                    mDetector.setOnDoubleTapListener(x);
                } else {
                    mTextView.setText("You have no tasks");
                }
            }
        });
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

        boolean result = false;
        float diffY = event2.getY() - event1.getY();
        float diffX = event2.getX() - event1.getX();
        if (Math.abs(diffX) > Math.abs(diffY)) {
            if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                if (diffX > 0) {
                    onSwipeRight();
                } else {
                    onSwipeLeft();
                }
                result = true;
            }
        }
        else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
            if (diffY > 0) {
                onSwipeDown();
            } else {
                onSwipeUp();
            }
            result = true;
        }
        return result;

    }

    public void onSwipeUp() {
        if (taskIdx == tasksList.length-1) {
            mTextView.setText("You have finished your tasks!");
            taskFinished = true;
            backButton.setVisibility(View.VISIBLE);
            exitButton.setVisibility(View.VISIBLE);
        } else {
            taskIdx++;
            mTextView.setText(tasksList[taskIdx]);
        }
    }

    public void onSwipeDown() {
        if (taskIdx != 0) {
            if (!taskFinished) {
                taskIdx--;
            }
            mTextView.setText(tasksList[taskIdx]);
            backButton.setVisibility(View.INVISIBLE);
            exitButton.setVisibility(View.INVISIBLE);
        }
    }

    public void onSwipeRight() {
    }

    public void onSwipeLeft() {
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

    @Override
    public void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // If sensor is unreliable, then just return
        Log.e("Reached3", "HERE");


        Log.e("x-value", Float.toString(event.values[0]));
        Log.e("y-value", Float.toString(event.values[1]));
        Log.e("z-value", Float.toString(event.values[2]));

        Log.e("Reached4", "HERE");
        if (receivedIntent == null || !receivedIntent.getBooleanExtra("syncToWear", false)) {
            return;
        }
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            detectShake(event);
        }
        else if(event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            detectRotation(event);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    // References:
    //  - http://jasonmcreynolds.com/?p=388
    //  - http://code.tutsplus.com/tutorials/using-the-accelerometer-on-android--mobile-22125
    private void detectShake(SensorEvent event) {
        Log.e("Reached5", "HERE");
        long now = System.currentTimeMillis();

        if((now - mShakeTime) > SHAKE_WAIT_TIME_MS) {
            mShakeTime = now;

            float gX = event.values[0] / SensorManager.GRAVITY_EARTH;
            float gY = event.values[1] / SensorManager.GRAVITY_EARTH;
            float gZ = event.values[2] / SensorManager.GRAVITY_EARTH;

            // gForce will be close to 1 when there is no movement
            double gForce = Math.sqrt(gX * gX + gY * gY + gZ * gZ);

            // Change background color if gForce exceeds threshold;
            // otherwise, reset the color
            Log.e("Reached5", gForce+"");
            if(gForce > SHAKE_THRESHOLD) {
                Log.e("Reached1", "HERE");
                if (taskIdx == tasksList.length-1) {
                    mTextView.setText("You have finished your tasks!");
                    taskFinished = true;
                } else {
                    taskIdx++;
                    mTextView.setText(tasksList[taskIdx]);
                }
            }
            else {
            }
        }
    }

    private void detectRotation(SensorEvent event) {
        Log.e("Reached6", "HERE");
        long now = System.currentTimeMillis();

        if((now - mRotationTime) > ROTATION_WAIT_TIME_MS) {
            mRotationTime = now;

            // Change background color if rate of rotation around any
            // axis and in any direction exceeds threshold;
            // otherwise, reset the color
            if(Math.abs(event.values[0]) > ROTATION_THRESHOLD ||
                    Math.abs(event.values[1]) > ROTATION_THRESHOLD ||
                    Math.abs(event.values[2]) > ROTATION_THRESHOLD) {
                Log.e("Reached2", "HERE");
            }
            else {
            }
        }
    }
}
