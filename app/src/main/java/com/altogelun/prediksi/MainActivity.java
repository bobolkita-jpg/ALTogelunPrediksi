package com.altogelun.prediksi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("ALTogelun Prediksi");

        Button btnKeluaran = findViewById(R.id.btnKeluaran);
        Button btnPrediksiSdy = findViewById(R.id.btnPrediksiSdy);
        Button btnPrediksiSgp = findViewById(R.id.btnPrediksiSgp);
        Button btnPrediksiHk = findViewById(R.id.btnPrediksiHk);

        btnKeluaran.setOnClickListener(v -> 
            startActivity(new Intent(this, KeluaranActivity.class)));

        btnPrediksiSdy.setOnClickListener(v -> openPrediksi("SYDNEY"));
        btnPrediksiSgp.setOnClickListener(v -> openPrediksi("SINGAPURA"));
        btnPrediksiHk.setOnClickListener(v -> openPrediksi("HONGKONG"));
    }

    private void openPrediksi(String pasaran) {
        Intent i = new Intent(this, PrediksiActivity.class);
        i.putExtra("pasaran", pasaran);
        startActivity(i);
    }
}
