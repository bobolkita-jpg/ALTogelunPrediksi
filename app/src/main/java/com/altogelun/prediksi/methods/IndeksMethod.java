package com.altogelun.prediksi.methods;

import java.util.*;

public class IndeksMethod {
    public static List<Integer> prediksi(List<String> dataKeluaran) {
        Set<Integer> result = new LinkedHashSet<>();
        if (dataKeluaran == null || dataKeluaran.size() < 2) return new ArrayList<>();

        for (int i = 0; i < dataKeluaran.size()-1; i++) {
            try {
                String a = dataKeluaran.get(i);
                String b = dataKeluaran.get(i+1);
                int ai = Integer.parseInt(a.substring(a.length()-2));
                int bi = Integer.parseInt(b.substring(b.length()-2));
                int selisih = Math.abs(ai - bi);
                int jumlah = (ai + bi) % 100;
                result.add(selisih);
                result.add(jumlah);
                result.add((selisih + jumlah) % 100);
            } catch (Exception ignored) {}
        }
        return new ArrayList<>(result);
    }
}
