package com.group33.greenthumb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.activeandroid.query.Delete;

public class DeletePlantActivity extends Activity implements View.OnTouchListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_plant);
        ImageButton m = (ImageButton) findViewById(R.id.imageButton);
        m.setOnTouchListener(this);

        // TODO: remove post-PROG03
        Plant p = Plant.getPlant("rose");
        if (p != null) {
            new Delete().from(Plant.class).where("Name = ?", "rose").execute();
            TextView t = (TextView)findViewById(R.id.plantTextView);
            t.setText("rose deleted");
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Intent i = new Intent();
        i.putExtra("exists", false);
        setResult(RESULT_OK, i);
        finish();
        return false;
    }
}
