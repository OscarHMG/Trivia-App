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
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SensorEventListener {
    private static final double ROTATION_THRESHOLD = 2.0f;
    private ImageView bottle;
    private SensorManager sensorManager;
    private Sensor gyroscopeSensor;
    RotateAnimation rotateAnimation;
    int lastDegree = 0;
    static int COUNT_NUMBER_PLAYERS = 0;

    boolean namesAreDone = false;

    private long mRotationTime = 0;
    private static final int ROTATION_WAIT_TIME_MS = 100;

    private LinearLayout container_logo;
    private ArrayList<String> players;

    private TextView playerOne, playerTwo;

    CharSequence[] values = {" Homero "," Aristoteles "," El Barto ", "None"};

    private QuestionLibrary questionLibrary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottle = findViewById(R.id.bottle);
        container_logo = findViewById(R.id.container_logo);
        playerOne = findViewById(R.id.playerOne);
        playerTwo = findViewById(R.id.playerTwo);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        players = new ArrayList<>();


        questionLibrary = new QuestionLibrary();

        bottle.setOnClickListener(this);

        showAlertDialogNames();
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
               /* AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Time to trivia!");
                builder.setMessage("Responde una pregunta de cultura general");
                builder.setPositiveButton("CERRAR", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

                builder.show();*/

                AlertDialog alertDialog1 = null;
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Time to trivia!");

                Random rnd = new Random();
                int numQuestions = rnd.nextInt(5);
                builder.setMessage(questionLibrary.getQuestion(numQuestions));

                CharSequence [] answers = questionLibrary.getChoices(numQuestions);

                builder.setSingleChoiceItems(answers, -1, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {

                        switch(item){
                            case 0:
                                Toast.makeText(MainActivity.this, "CORREEECT!", Toast.LENGTH_LONG).show();
                                break;
                            case 1:
                                Toast.makeText(MainActivity.this, "WRONG answer!", Toast.LENGTH_LONG).show();
                                break;
                            case 2:
                                Toast.makeText(MainActivity.this, "WRONG answer!", Toast.LENGTH_LONG).show();
                                break;
                        }
                        dialog.dismiss();
                    }
                });
                alertDialog1 = builder.create();
                alertDialog1.show();
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


    public void showAlertDialogNames () {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText edittext = new EditText(this);
        builder.setMessage("Enter name player:");
        builder.setTitle("Lets play!");
        builder.setCancelable(false);
        builder.setView(edittext);

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //What ever you want to do with the value

                String namePlayer = edittext.getText().toString();
                COUNT_NUMBER_PLAYERS ++;

                players.add(namePlayer);



                showAlertDialogNames();
            }
        });



        builder.setNegativeButton("Lets, go and play!", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
                namesAreDone = true;
                container_logo.setVisibility(View.GONE);
                bottle.setVisibility(View.VISIBLE);

                int [] randomNamesNumbers = getTwoRandomNumbers();


                String name1 = players.get(randomNamesNumbers [0]);
                String name2 = players.get(randomNamesNumbers [1]);


                playerOne.setText(name1);
                playerTwo.setText(name2);

                showMultiChoiceQuestion();

            }
        });

        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);



        dialog.show();

        //dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setEnabled(false);

        if (COUNT_NUMBER_PLAYERS >= 2) {
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setEnabled(true);
        }else{
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setEnabled(false);
        }



    }


    public int [] getTwoRandomNumbers(){
        int[] numbers = new int[2];

        Random rand = new Random();
        int number1 = 0 ,number2 = 0;
        boolean equals = false;


        while(!equals){
            number1 = rand.nextInt(players.size());
            number2 = rand.nextInt(players.size());

            equals = number1 != number2;
        }

        Log.i("OSCAR",""+number1);
        Log.i("OSCAR",""+number2);

        numbers[0] = number1;
        numbers[1] = number2;

        return numbers;
    }


    public void showMultiChoiceQuestion (){
        AlertDialog alertDialog1 = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Time to trivia!");

        Random rnd = new Random();
        int numQuestions = rnd.nextInt(5);
        builder.setMessage(questionLibrary.getQuestion(numQuestions));


        CharSequence [] answers = questionLibrary.getChoices(numQuestions);

        builder.setSingleChoiceItems(answers, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                switch(item){
                    case 0:
                        Toast.makeText(MainActivity.this, "CORREEECT!", Toast.LENGTH_LONG).show();
                        break;
                    case 1:
                        Toast.makeText(MainActivity.this, "WRONG answer!", Toast.LENGTH_LONG).show();
                        break;
                    case 2:
                        Toast.makeText(MainActivity.this, "WRONG answer!", Toast.LENGTH_LONG).show();
                        break;
                }
                dialog.dismiss();
            }
        });
        alertDialog1 = builder.create();
        alertDialog1.show();
    }
}

