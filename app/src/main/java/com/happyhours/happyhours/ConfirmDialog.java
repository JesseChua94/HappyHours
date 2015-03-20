package com.happyhours.happyhours;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RatingBar;
import android.widget.Toast;


/**
 * Created by Jesse on 2015-03-07.
 */
public class ConfirmDialog extends DialogFragment {
    private float happiness;
    @Override
    public AlertDialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder;
        final HoursDatabaseAdapter db;

        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.confirm_entry, null);
        db = new HoursDatabaseAdapter(getActivity());
        builder = new AlertDialog.Builder(getActivity());

        final RatingBar ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
            }
        });

        builder.setView(view)
                .setTitle("Happiness rating")
                /* Enter the data into the database */
                .setPositiveButton("Enter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                    /* Get time */
                        Long minutes = getArguments().getLong("minutes");
                        Long seconds = getArguments().getLong("seconds");
                        String time = minutes + ":" + seconds;

                    /* Get date */
                        ActivityTimer aT = new ActivityTimer();
                        String date = aT.getDate();

                    /* Get activity name */
                        String activityName = getArguments().getString("activityName");

                        db.insertTables(time, date, activityName, ratingBar.getRating());
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ConfirmDialog.this.getDialog().cancel();
            }
        });
        return builder.create();
    }

}
