package com.rayzem.trivia_app;

import android.content.Context;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SensorEventListener {
    private static final double ROTATION_THRESHOLD = 2.0f;
    private ImageView bottle;
    private SensorManager sensorManager;
    private Sensor gyroscopeSensor;
    RotateAnimation rotateAnimation;
    int lastDegree = 0;

    private long mRotationTime = 0;
    private static final int ROTATION_WAIT_TIME_MS = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottle = findViewById(R.id.bottle);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        bottle.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, gyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bottle:
                //rotateBottle(bottle, null);
                break;
        }
    }

   /* public void rotateBottle(View view, SensorEvent sensorEvent){
        Random random = new Random();
        int numRotations = random.nextInt(3600)+360;
        *//*rotateAnimation = new RotateAnimation(lastDegree, numRotations, RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);*//*

        rotateAnimation = new RotateAnimation(0, 360, RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);


        rotateAnimation.setFillAfter(true);
        rotateAnimation.setDuration(800);
        //rotateAnimation.setRepeatCount(3500);
        lastDegree = numRotations;
        rotateAnimation.setRepeatMode(Animation.RESTART);
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        rotateAnimation.setInterpolator(this, android.R.anim.linear_interpolator);

        rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Hora de la trivia!");
                builder.setMessage("Responde una pregunta de cultura general");
                builder.setPositiveButton("CERRAR", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

                builder.show();
            }



            @Override
            public void onAnimationRepeat(Animation animation) {

                //rotateAnimation.start();
            }
        });

        bottle.startAnimation(rotateAnimation);



        *//*if(Math.abs(sensorEvent.values[2]) > ROTATION_THRESHOLD){
            view.startAnimation(rotateAnimation);
        }else{
            view.clearAnimation();
            rotateAnimation.reset();
            rotateAnimation.cancel();
        }*//*
    }*/


    public void rotateBottle(){

        Random random = new Random();
        int numRotations = random.nextInt(3600)+360;

        Random RANDOM_NUMBER = new Random();
        //degree = RANDOM_NUMBER.nextInt(360) + 720;


        //Now start the rotating animation.
        rotateAnimation = new RotateAnimation(0, numRotations, RotateAnimation.RELATIVE_TO_SELF,
                0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);

        rotateAnimation.setDuration(3000);

        rotateAnimation.setFillAfter(true);

        rotateAnimation.setInterpolator(new DecelerateInterpolator());

        rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Hora de la trivia!");
                builder.setMessage("Responde una pregunta de cultura general");
                builder.setPositiveButton("CERRAR", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

                builder.show();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        bottle.startAnimation(rotateAnimation);


    }



    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            Log.i("Bottle", "" + Math.abs(sensorEvent.values[2]));

            long now = System.currentTimeMillis();

            if ((now - mRotationTime) > ROTATION_WAIT_TIME_MS) {
                if (Math.abs(sensorEvent.values[2]) > 2)
                    rotateBottle();
                else if (sensorEvent.values[2] <= 0) {
                    if (rotateAnimation != null && rotateAnimation.hasEnded()) {
                        rotateAnimation.cancel();
                    }

                }

            }


        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
