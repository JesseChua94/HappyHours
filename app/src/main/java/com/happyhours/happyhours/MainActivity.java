package com.happyhours.happyhours;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Set buttons */
        Button newActivity = (Button) findViewById(R.id.new_activity);
        Button trends = (Button) findViewById(R.id.trends);
        newActivity.setOnClickListener(this);
        trends.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.new_activity:
                intent = new Intent(MainActivity.this, ActivityTimer.class);
                startActivity(intent);
                break;
            case R.id.trends:
                intent = new Intent(MainActivity.this, Trends.class);
                startActivity(intent);
        }
    }
}
