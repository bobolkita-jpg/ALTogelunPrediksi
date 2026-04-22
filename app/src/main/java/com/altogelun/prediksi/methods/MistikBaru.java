package com.altogelun.prediksi.methods;

import java.util.*;

public class MistikBaru {
    public static List<Integer> prediksi(List<String> dataKeluaran) {
        Set<Integer> result = new LinkedHashSet<>();
        if (dataKeluaran == null || dataKeluaran.isEmpty()) return new ArrayList<>();

        for (String keluaran : dataKeluaran) {
            if (keluaran.length() < 2) continue;
            try {
                int dua = Integer.parseInt(keluaran.substring(keluaran.length()-2));
                int a = (dua + 31) % 100;
                int b = (dua + 69) % 100;
                int balik = Integer.parseInt(
                    new StringBuilder(String.format("%02d", dua)).reverse().toString());
                result.add(a);
                result.add(b);
                result.add(balik);
            } catch (Exception ignored) {}
        }
        return new ArrayList<>(result);
    }
}
