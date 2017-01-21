package com.example.laptop.icraudi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    WebView web;
    ImageButton btnSplash;


    private String link = "http://www.audi.rs/";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        btnSplash = (ImageButton)findViewById(R.id.button1);
        btnSplash.setOnClickListener(this);

        String x = "<!DOCTYPE html><html><body><center><img src=\"http://bestanimations.com/Transport/Cars/luxury-car-animated-gif-1.gif\" alt=\"AUDI\" width=\"115%\" height=\"100%\"></center></body></html>";
         web = (WebView)findViewById(R.id.web1);
        web.loadData(x,"text/html","utf-8");
        web.setHorizontalScrollBarEnabled(false);

        SharedPreferences preferences =getSharedPreferences("show",0);
        SharedPreferences.Editor editor = preferences.edit();
        if(preferences.contains("firsttime"))
        {
           // Toast.makeText(this,"not first time",Toast.LENGTH_LONG).show();
        }else{

            editor.putBoolean("firsttime", true);
            editor.commit();
            insertDummyData();
        }


        web.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                web.loadUrl(link);
                btnSplash.setVisibility(View.INVISIBLE);
                return true;
            }
        });
    }

    public void insertDummyData()
    {
        try
        {
            DatabaseHelper db = new DatabaseHelper(getApplicationContext());
            db.insertCars("AUDI A4");
            db.insertCars("AUDI A6");
            db.insertCars("AUDI A8");
            db.insertCars("AUDI TT");

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }


    }
    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
        builder1.setMessage("Exiting already?");
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.button1:

                Intent i = new Intent(getApplicationContext(),carChoose.class);
                startActivity(i);
                break;


        }
    }
}
