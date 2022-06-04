package com.example.levv.view.funcional;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import com.example.levv.R;
import com.example.levv.controller.tela.TelaSplashController;


public class TelaSplashActivity extends AppCompatActivity {

    private TelaSplashController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_splash);
        this.controller = new TelaSplashController(getBaseContext());
        this.trocarTelaSplash();
    }

    private void trocarTelaSplash() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(
                        new Intent(
                                getBaseContext(),
                                controller.selecionarTela()
                        )
                );
            }
        }, 300);
    }
}