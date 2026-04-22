package com.altogelun.prediksi;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.altogelun.prediksi.ai.AIPredictor;
import com.altogelun.prediksi.ai.LearningEngine;
import com.altogelun.prediksi.methods.*;
import com.altogelun.prediksi.scraper.DataScraper;
import java.util.List;

public class PrediksiActivity extends AppCompatActivity {
    private TextView tvPasaran, tvMistikLama, tvMistikBaru, tvIndeks, tvAI, tvBobot;
    private ProgressBar progress;
    private String pasaran;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prediksi);
        
        pasaran = getIntent().getStringExtra("pasaran");
        setTitle("Prediksi " + pasaran);

        tvPasaran = findViewById(R.id.tvPasaran);
        tvMistikLama = findViewById(R.id.tvMistikLama);
        tvMistikBaru = findViewById(R.id.tvMistikBaru);
        tvIndeks = findViewById(R.id.tvIndeks);
        tvAI = findViewById(R.id.tvAI);
        tvBobot = findViewById(R.id.tvBobot);
        progress = findViewById(R.id.progress);

        tvPasaran.setText("Pasaran: " + pasaran);

        Button btnRefresh = findViewById(R.id.btnRefresh);
        btnRefresh.setOnClickListener(v -> prediksi());

        prediksi();
    }

    private void prediksi() {
        progress.setVisibility(ProgressBar.VISIBLE);
        new Thread(() -> {
            List<String> data = DataScraper.ambilDataPasaran(pasaran);

            LearningEngine le = new LearningEngine(this);
            AIPredictor ai = new AIPredictor(le.getBobot());

            List<Integer> ml = MistikLama.prediksi(data);
            List<Integer> mb = MistikBaru.prediksi(data);
            List<Integer> idx = IndeksMethod.prediksi(data);
            List<Integer> hasilAI = ai.prediksiGabungan(data);

            if (data.size() > 1) {
                le.evaluasi(hasilAI, data.get(0));
            }

            new Handler(Looper.getMainLooper()).post(() -> {
                progress.setVisibility(ProgressBar.GONE);
                tvMistikLama.setText("🔮 Mistik Lama: " + formatList(ml));
                tvMistikBaru.setText("✨ Mistik Baru: " + formatList(mb));
                tvIndeks.setText("📈 Indeks: " + formatList(idx));
                tvAI.setText("🤖 AI Prediksi (Top 10): " + formatList(hasilAI));
                tvBobot.setText("Bobot AI: " + le.getBobot().toString());
            });
        }).start();
    }

    private String formatList(List<Integer> list) {
        if (list.isEmpty()) return "-";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < Math.min(list.size(), 15); i++) {
            sb.append(String.format("%02d", list.get(i)));
            if (i < Math.min(list.size(), 15)-1) sb.append(", ");
        }
        return sb.toString();
    }
}
