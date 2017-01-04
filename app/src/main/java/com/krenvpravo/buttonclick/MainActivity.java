package com.krenvpravo.buttonclick;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    public static final String IS_RED_KEY = "is red marker";

    @BindView(R.id.main_activity_btn)
    Button button;
    boolean isButtonRed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isButtonRed) {
                    paintButtonBlue();
                } else {
                    paintButtonRed();
                }
            }
        });
        paintButtonRed();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(IS_RED_KEY, isButtonRed);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        isButtonRed = savedInstanceState.getBoolean(IS_RED_KEY, true);
        if (isButtonRed) {
            paintButtonRed();
        } else {
            paintButtonBlue();
        }
    }

    private void paintButtonRed() {
        button.setBackgroundColor(Color.RED);
        isButtonRed = true;
    }

    private void paintButtonBlue() {
        button.setBackgroundColor(Color.BLUE);
        isButtonRed = false;
    }
}
