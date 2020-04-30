package com.example.factorgame;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class second extends AppCompatActivity {
    private Button go;
    private RadioGroup radioGroup;
    private RadioButton option1, option2, option3;
    private EditText editText;

    //fields for essentials
    long plays = 0, score = 0;
    int coins = 0, level = 0, number = 0;
    boolean now = false, that = false, last = false;
    float accuracy = 0;
    //keys for shared preferences
    public static final String LEVEL = "level", SCORE = "score", PLAYS = "plays", ACCURACY = "accuracy", COINS = "coins", SP = "sp", NOW = "now", THAT = "that", LAST = "last";
    //declare an array for three options
    int[] option;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


        editText = findViewById(R.id.number);
        go = findViewById(R.id.go);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        radioGroup = findViewById(R.id.radio_group);

        if(savedInstanceState==null){
            Funfactor.setGo(false);
            Funfactor.setAnswer(false);
        }
        if(Funfactor.getGo()&&!Funfactor.getAnswer()) {
            go.setVisibility(View.GONE);
            radioGroup.setVisibility(View.VISIBLE);
            option1.setText(Integer.toString(Funfactor.getop1()));
            option2.setText(Integer.toString(Funfactor.getop2()));
            option3.setText(Integer.toString(Funfactor.getop3()));

            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    Funfactor.setAnswer(true);
                    RadioButton selected = findViewById(checkedId);
                    option1.setEnabled(false);
                    option2.setEnabled(false);
                    option3.setEnabled(false);
                    if (Funfactor.getNumber() % Integer.parseInt(selected.getText().toString()) == 0) {
                        Funfactor.setisCorrect(true);
                        Toast.makeText(getApplicationContext(), "You are correct", Toast.LENGTH_SHORT).show();
                    } else {
                        Funfactor.setisCorrect(false);
                        Toast.makeText(getApplicationContext(), "You are wrong", Toast.LENGTH_SHORT).show();
                    }
                    if (Funfactor.getNumber() % Funfactor.getop1() == 0)
                        option1.setTextColor(Color.parseColor("#2E7D32"));
                    else option1.setTextColor(Color.RED);
                    if (Funfactor.getNumber() % Funfactor.getop2() == 0)
                        option2.setTextColor(Color.parseColor("#2E7D32"));
                    else option2.setTextColor(Color.RED);
                    if (Funfactor.getNumber() % Funfactor.getop3() == 0)
                        option3.setTextColor(Color.parseColor("#2E7D32"));
                    else option3.setTextColor(Color.RED);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            replay();
                        }
                    }, 5000);
                }
            });
        }
        if(Funfactor.getAnswer()) {
            go.setVisibility(View.GONE);
            radioGroup.setVisibility(View.VISIBLE);
            option1.setText(Integer.toString(Funfactor.getop1()));
            option2.setText(Integer.toString(Funfactor.getop2()));
            option3.setText(Integer.toString(Funfactor.getop3()));
            option1.setEnabled(false);
            option2.setEnabled(false);
            option3.setEnabled(false);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                }
            }, 6000);
        }
    }

    public void checkLevel(View view) {
        Funfactor.setGo(true);
        hideKeyboard(this);
        if (editText.getText().toString().length() != 3 || (editText.getText().toString().charAt(0) == '0')) {
            Toast.makeText(this, "You are in level 0\nYou should enter a 3 digit number.", Toast.LENGTH_SHORT).show();
        } else {
            editText.setFocusable(false);
            openOptions();
        }
    }


    @SuppressLint("SetTextI18n")
    public void openOptions() {
        plays++;
        int correctIndex, factors = 0;
        number = Integer.parseInt(editText.getText().toString());
        Funfactor.setNumber(number);
        option = new int[3];
        go.setVisibility(View.INVISIBLE);
        //to initialize random number generator
        int p = getRandom(50);
        //randomly choose which option should be correct
        correctIndex = getRandom(3) - 1;
        //set option[correctIndex]=answer
        //add the factors of a number in an ArrayList
        ArrayList<Integer> factor = new ArrayList<>();
        for (int i = (int) Math.sqrt(number); i >= 1; i--) {
            if (number % i == 0) {
                if (number / i == i) {
                    factor.add(i);
                    factors++;
                } else {
                    factor.add(i);
                    factor.add(number / i);
                    factors += 2;
                }
            }
        }
        option[correctIndex] = factor.get(getRandom(factors) - 1);
        //set remaining options
        int k;
        for (int i = 0; i < 3; i++) {
            k = option[correctIndex];
            if (i != correctIndex) {
                while (true)
                    if (number % (k = getRandom(number)) != 0) break;
            }
            option[i] = k;
        }
        Funfactor.setop1(option[0]);
        Funfactor.setop2(option[1]);
        Funfactor.setop3(option[2]);
        String op1 = Integer.toString(option[0]);
        String op2 = Integer.toString(option[1]);
        String op3 = Integer.toString(option[2]);
        option1.setText(op1);
        option2.setText(op2);
        option3.setText(op3);
        TextView textView = findViewById(R.id.question);
        textView.setVisibility(View.INVISIBLE);
        radioGroup.setVisibility(View.VISIBLE);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Funfactor.setAnswer(true);
                RadioButton selected = findViewById(checkedId);
                option1.setEnabled(false);
                option2.setEnabled(false);
                option3.setEnabled(false);
                if (number % Integer.parseInt(selected.getText().toString()) == 0) {
                    Funfactor.setisCorrect(true);
                    Toast.makeText(getApplicationContext(), "You are correct", Toast.LENGTH_SHORT).show();
                } else {
                    Funfactor.setisCorrect(false);
                    Toast.makeText(getApplicationContext(), "You are wrong", Toast.LENGTH_SHORT).show();
                }

                if (number % option[0] == 0) option1.setTextColor(Color.parseColor("#2E7D32"));
                else option1.setTextColor(Color.RED);
                if (number % option[1] == 0) option2.setTextColor(Color.parseColor("#2E7D32"));
                else option2.setTextColor(Color.RED);
                if (number % option[2] == 0) option3.setTextColor(Color.parseColor("#2E7D32"));
                else option3.setTextColor(Color.RED);

                selected.setChecked(false);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        replay();
                    }
                }, 5000);
            }
        });
    }

    public int getRandom(int max) {
        return (1 + (int) (Math.random() * max));
    }

    public void replay() {
        Funfactor.setGo(false);
        Funfactor.setisCorrect(false);
        Funfactor.setAnswer(false);
        Intent i = new Intent(this, second.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        finish();
        startActivity(i);
    }

    public void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        assert imm != null;
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }



}
