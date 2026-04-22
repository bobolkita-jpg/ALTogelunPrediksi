package com.altogelun.prediksi.ai;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.*;

public class LearningEngine {
    private static final String PREF = "ai_learning";
    private SharedPreferences sp;

    public LearningEngine(Context ctx) {
        sp = ctx.getSharedPreferences(PREF, Context.MODE_PRIVATE);
    }

    public void update(String metode, boolean berhasil) {
        String key = "bobot_" + metode;
        float current = sp.getFloat(key, 0.33f);
        float delta = berhasil ? 0.02f : -0.01f;
        float newVal = Math.max(0.1f, Math.min(0.8f, current + delta));
        sp.edit().putFloat(key, newVal).apply();
    }

    public Map<String, Double> getBobot() {
        Map<String, Double> b = new HashMap<>();
        b.put("MISTIK_LAMA", (double) sp.getFloat("bobot_MISTIK_LAMA", 0.33f));
        b.put("MISTIK_BARU", (double) sp.getFloat("bobot_MISTIK_BARU", 0.33f));
        b.put("INDEKS", (double) sp.getFloat("bobot_INDEKS", 0.34f));
        return b;
    }

    public void evaluasi(List<Integer> prediksi, String hasilAktual) {
        if (hasilAktual == null || hasilAktual.length() < 2) return;
        int aktual = Integer.parseInt(hasilAktual.substring(hasilAktual.length()-2));
        boolean match = prediksi.contains(aktual);
        update("MISTIK_LAMA", match);
        update("MISTIK_BARU", match);
        update("INDEKS", match);
    }
}
