package com.krenvpravo.buttonclick.ui;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.krenvpravo.buttonclick.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainActivityContract.View {

    @BindView(R.id.main_activity_btn)
    Button button;
    private MainActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        presenter = new MainActivityPresenter(this);
        initView();
    }

    @Override
    protected void onStart() {
        presenter.onViewStarted();
        super.onStart();
    }

    @Override
    protected void onStop() {
        presenter.onViewStopped();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        presenter.onViewDestroyed();
        super.onDestroy();
    }

    private void initView() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onButtonColorClicked();
            }
        });
        paintButtonRed();
    }

    @Override
    public void paintButtonRed() {
        button.setBackgroundColor(Color.RED);
    }

    @Override
    public void paintButtonBlue() {
        button.setBackgroundColor(Color.BLUE);
    }

    @Override
    public void showError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
    }
}
