package com.altogelun.prediksi.methods;

import java.util.*;

public class MistikLama {
    private static final int[][] TABEL = {
        {0,50},{1,51},{2,52},{3,53},{4,54},{5,55},{6,56},{7,57},{8,58},{9,59},
        {10,60},{11,61},{12,62},{13,63},{14,64},{15,65},{16,66},{17,67},{18,68},{19,69},
        {20,70},{21,71},{22,72},{23,73},{24,74},{25,75},{26,76},{27,77},{28,78},{29,79},
        {30,80},{31,81},{32,82},{33,83},{34,84},{35,85},{36,86},{37,87},{38,88},{39,89},
        {40,90},{41,91},{42,92},{43,93},{44,94},{45,95},{46,96},{47,97},{48,98},{49,99}
    };

    public static List<Integer> prediksi(List<String> dataKeluaran) {
        Set<Integer> result = new LinkedHashSet<>();
        if (dataKeluaran == null || dataKeluaran.isEmpty()) return new ArrayList<>();

        for (String keluaran : dataKeluaran) {
            if (keluaran.length() < 2) continue;
            try {
                int dua = Integer.parseInt(keluaran.substring(keluaran.length()-2));
                for (int[] pair : TABEL) {
                    if (pair[0] == dua) result.add(pair[1]);
                    if (pair[1] == dua) result.add(pair[0]);
                }
            } catch (Exception ignored) {}
        }
        return new ArrayList<>(result);
    }
}
