package com.altogelun.prediksi.ai;

import com.altogelun.prediksi.methods.*;
import java.util.*;

public class AIPredictor {
    private Map<String, Double> bobotMetode;

    public AIPredictor(Map<String, Double> bobot) {
        this.bobotMetode = bobot != null ? bobot : defaultBobot();
    }

    private Map<String, Double> defaultBobot() {
        Map<String, Double> b = new HashMap<>();
        b.put("MISTIK_LAMA", 0.33);
        b.put("MISTIK_BARU", 0.33);
        b.put("INDEKS", 0.34);
        return b;
    }

    public List<Integer> prediksiGabungan(List<String> dataKeluaran) {
        Map<Integer, Double> skor = new HashMap<>();

        List<Integer> ml = MistikLama.prediksi(dataKeluaran);
        List<Integer> mb = MistikBaru.prediksi(dataKeluaran);
        List<Integer> idx = IndeksMethod.prediksi(dataKeluaran);

        for (int n : ml) skor.merge(n, bobotMetode.get("MISTIK_LAMA"), Double::sum);
        for (int n : mb) skor.merge(n, bobotMetode.get("MISTIK_BARU"), Double::sum);
        for (int n : idx) skor.merge(n, bobotMetode.get("INDEKS"), Double::sum);

        Map<Integer, Integer> freq = analisisFrekuensi(dataKeluaran);
        for (Map.Entry<Integer, Integer> e : freq.entrySet()) {
            skor.merge(e.getKey(), e.getValue() * 0.15, Double::sum);
        }

        List<Map.Entry<Integer, Double>> sorted = new ArrayList<>(skor.entrySet());
        sorted.sort((a,b) -> Double.compare(b.getValue(), a.getValue()));

        List<Integer> hasil = new ArrayList<>();
        for (int i = 0; i < Math.min(10, sorted.size()); i++) {
            hasil.add(sorted.get(i).getKey());
        }
        return hasil;
    }

    private Map<Integer, Integer> analisisFrekuensi(List<String> data) {
        Map<Integer, Integer> freq = new HashMap<>();
        for (String k : data) {
            if (k.length() < 2) continue;
            try {
                int dua = Integer.parseInt(k.substring(k.length()-2));
                freq.merge(dua, 1, Integer::sum);
            } catch (Exception ignored) {}
        }
        return freq;
    }
}
