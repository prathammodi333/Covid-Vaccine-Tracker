/*
    Developed by : Pratham Modi(20CE056)
    Home page
*/

package com.example.covidvaccinetracker;

import static com.example.covidvaccinetracker.AppNotification.CHANNEL_1_ID;

import android.app.Notification;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import com.example.covidvaccinetracker.view.MainActivity1;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private NotificationManagerCompat notificationManager;

    Button btn_part1;
    Button btn_part2;
    TextView marqueeEffect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Random random = new Random();   // generate random number
        int randomNumber = random.nextInt(3);

        btn_part1 = findViewById(R.id.btn_TrackCovidVaccine);
        btn_part2 = findViewById(R.id.btn_TrackCovidCases);
        marqueeEffect = findViewById(R.id.marqueeEffect);
        notificationManager = NotificationManagerCompat.from(this);

        marqueeEffect.setEllipsize(TextUtils.TruncateAt.MARQUEE);  //for marqueeEffect
        marqueeEffect.setSelected(true);

        // give text to marqueeLine
        if(randomNumber==0)
        {
            marqueeEffect.setText("This is our CVT app to fight against corona let's fight against corona.");
        }
        else if(randomNumber==1)
        {
            marqueeEffect.setText("Corona starts with your hands. Please wash your hands frequently with hand-wash or an alcohol based solution.");
        }
        else if(randomNumber==2)
        {
            marqueeEffect.setText("Social distancing only works if we all participate, and showing down or preventing the spread of the virus will save lives");
        }

    } // for showing the design of the page

    public void fnTrackCovidVaccine(View view) {
        String title = "CVT";
        String message = "Let's get vaccinated to fight against covid";
        Intent intent = new Intent(this, MainActivity1.class);
        startActivity(intent);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        notificationManager.notify(1, notification);
    } // redirect user to vaccine info page


    public void fnTrackCovidCases(View view){
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }  // redirect user to statistics page

    @Override
    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        MainActivity.super.onBackPressed();

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();

                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    } // asks user to exit or not

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    } // exit button

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == R.id.exit){
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("Do you want to exit?");
            builder.setCancelable(true);
            builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        }
        return true;
    }
} // asks user to exit or not while pressing exit button