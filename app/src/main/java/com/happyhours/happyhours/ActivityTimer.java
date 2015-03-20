package com.happyhours.happyhours;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Jesse on 2015-03-01.
 */
public class ActivityTimer extends FragmentActivity implements View.OnClickListener {
    long ms;
    long minutes;
    long seconds;
    long reduce;
    Chronometer chronometer;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        Button start = (Button) findViewById(R.id.start);
        Button stop = (Button) findViewById(R.id.stop);
        start.setOnClickListener(this);
        stop.setOnClickListener(this);

        chronometer = (Chronometer) findViewById(R.id.timer);
    }

    public String getDate(){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(cal.getTime());
    }
    /* Take away minutes to get total seconds */
    public void convertTime(long ms){
        if (ms > 60000) {
            minutes = ms / 60000;
            reduce = (ms / 60000) * 60000;
            seconds = (ms - reduce) / 1000;
        } else {
            seconds = ms / 1000;
            minutes = seconds /60;
        }
    }
    /* Send time and activity name to the dialog fragment */
    public void sendData(long minutes, long seconds, ConfirmDialog dialog){
        Bundle args = new Bundle();
        args.putLong("minutes", minutes);
        args.putLong("seconds", seconds);

        EditText aN = (EditText) findViewById(R.id.activityName);
        String activityName = aN.getText().toString();
        args.putString("activityName", activityName);

        dialog.setArguments(args);

    }
   @Override
   public void onClick(View v) {
        switch (v.getId()){
            case R.id.start:
                chronometer.setBase(SystemClock.elapsedRealtime());
                chronometer.start();
                break;

            case R.id.stop:
                chronometer.stop();
                ms = SystemClock.elapsedRealtime() - chronometer.getBase();
                convertTime(ms);

                /* toast for testing */
                Toast.makeText(ActivityTimer.this, getDate(), Toast.LENGTH_SHORT).show();

                /* Sets up pop up dialog */
                ConfirmDialog confirm = new ConfirmDialog();
                sendData(minutes, seconds, confirm);

                /* NEED TO IMPLEMENT RESET FOR TIMER maybe? */
                confirm.show(getSupportFragmentManager(), "confirm");
                break;
        }
    }
}
