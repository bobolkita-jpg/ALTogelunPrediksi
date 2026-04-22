package com.altogelun.prediksi;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.altogelun.prediksi.scraper.DataScraper;
import java.util.List;

public class KeluaranActivity extends AppCompatActivity {
    private TableLayout table;
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keluaran);
        setTitle("Keluaran Pasaran");

        table = findViewById(R.id.tableKeluaran);
        progress = findViewById(R.id.progress);

        loadData();
    }

    private void loadData() {
        progress.setVisibility(ProgressBar.VISIBLE);
        new Thread(() -> {
            List<DataScraper.KeluaranData> data = DataScraper.ambilData();
            new Handler(Looper.getMainLooper()).post(() -> {
                progress.setVisibility(ProgressBar.GONE);
                tampilkanTabel(data);
            });
        }).start();
    }

    private void tampilkanTabel(List<DataScraper.KeluaranData> data) {
        table.removeAllViews();
        TableRow header = new TableRow(this);
        header.addView(buatSel("Tanggal", true));
        header.addView(buatSel("Sydney", true));
        header.addView(buatSel("Singapura", true));
        header.addView(buatSel("Hongkong", true));
        table.addView(header);

        if (data.isEmpty()) {
            Toast.makeText(this, "Data tidak tersedia", Toast.LENGTH_SHORT).show();
            return;
        }

        for (DataScraper.KeluaranData d : data) {
            TableRow row = new TableRow(this);
            row.addView(buatSel(d.tanggal, false));
            row.addView(buatSel(d.sydney, false));
            row.addView(buatSel(d.singapura, false));
            row.addView(buatSel(d.hongkong, false));
            table.addView(row);
        }
    }

    private TextView buatSel(String txt, boolean header) {
        TextView tv = new TextView(this);
        tv.setText(txt);
        tv.setPadding(16,16,16,16);
        if (header) {
            tv.setTextColor(0xFFFFD700);
            tv.setTypeface(null, android.graphics.Typeface.BOLD);
        } else {
            tv.setTextColor(0xFFFFFFFF);
        }
        return tv;
    }
}
