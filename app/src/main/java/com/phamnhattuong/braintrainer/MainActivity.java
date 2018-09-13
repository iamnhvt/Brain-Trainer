package com.phamnhattuong.braintrainer;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    public static final int NUM_ROW = 16;
    public static final int MIN_NUM = 1;
    public static final int MAX_NUM = 40;

    TextView clock, score, equation, result;
    Button a1, a2, a3, a4, playagain;
    int numCorrect, round, correctAnswer;
    boolean gameOver = true;
    CountDownTimer mCountDownTimer;

    public int getRandomNumber(int lb, int ub, int avoidNum) {
        Random rand = new Random();

        int randomNum = rand.nextInt((ub - lb) + 1) + lb;

        if (randomNum != avoidNum) {
            return randomNum;
        } else return getRandomNumber(lb, ub, avoidNum);
    }

    public void touchAnswer(View view) {
        if (gameOver) return;
        Button tmp = (Button) view;
        int x = Integer.valueOf(tmp.getText().toString());
        Log.d(TAG, "touchAnswer: " + x + " : " + correctAnswer);
        if (x == correctAnswer) {
            numCorrect++;
        }

        mCountDownTimer.onFinish();
    }

    public void restart(View view) {
        gameOver = false;
        result.setVisibility(View.INVISIBLE);
        view.setVisibility(View.INVISIBLE);

        round = 0;
        numCorrect = 0;
        gameOver = false;

        startNewRound();


    }

    public void startNewRound() {
        if (round == 16) {
            result = (TextView) findViewById(R.id.result);
            playagain = (Button) findViewById(R.id.playagain);
            result.setVisibility(View.VISIBLE);
            playagain.setVisibility(View.VISIBLE);
            result.setText(String.format("Your result: %d / %d", numCorrect, 16));
            gameOver = true;
            return;
        }
        round++;
        clock.setText("10s");
        score.setText(String.format("%d/%d", numCorrect, round - 1));
        int x1 = getRandomNumber(MIN_NUM, MAX_NUM, -1);
        int x2 = getRandomNumber(MIN_NUM, MAX_NUM, -1);
        int correctPosition = getRandomNumber(1, 4, -1);
        correctAnswer = x1 + x2;

        mCountDownTimer = new CountDownTimer(10 * 1000 + 100, 1000) {
            @Override
            public void onTick(long l) {
                clock.setText(String.format("%ds", (int)l/1000));
            }

            @Override
            public void onFinish() {
                this.cancel();
                MainActivity.this.startNewRound();
            }
        };
        mCountDownTimer.start();

        equation.setText(String.format("%d  + %d = ", x1, x2));

        for (int i = 1; i <= 4; i++) {
            int value = (i == correctPosition) ? x1 + x2 : getRandomNumber(1, 100, x1 + x2);
            switch (i) {
                case 1:
                    a1.setText(Integer.toString(value));
                    break;
                case 2:
                    a2.setText(Integer.toString(value));
                    break;
                case 3:
                    a3.setText(Integer.toString(value));
                    break;
                case 4:
                    a4.setText(Integer.toString(value));
                    break;
                default:
                    System.out.println("hihi");
            }
        }
    }

    public void gu(View view) {
        System.out.println("why!");
        setContentView(R.layout.activity_main);
        clock = (TextView) findViewById(R.id.clock);
        score = (TextView) findViewById(R.id.score);
        equation = (TextView) findViewById(R.id.equation);
        a1 = (Button) findViewById(R.id.answer1);
        a2 = (Button) findViewById(R.id.answer2);
        a3 = (Button) findViewById(R.id.answer3);
        a4 = (Button) findViewById(R.id.answer4);
        round = 0;
        numCorrect = 0;
        gameOver = false;

        startNewRound();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
    }
}
