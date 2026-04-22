package com.altogelun.prediksi.scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.*;

public class DataScraper {
    private static final String URL = "https://hongkong-pools-1001.dishubkaranganyar.com/";

    public static class KeluaranData {
        public String tanggal;
        public String sydney;
        public String singapura;
        public String hongkong;

        public KeluaranData(String t, String s, String sg, String h) {
            tanggal = t; sydney = s; singapura = sg; hongkong = h;
        }
    }

    public static List<KeluaranData> ambilData() {
        List<KeluaranData> list = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(URL)
                .userAgent("Mozilla/5.0")
                .timeout(15000)
                .get();

            Elements tables = doc.select("table");
            for (Element table : tables) {
                Elements rows = table.select("tr");
                for (Element row : rows) {
                    Elements cols = row.select("td");
                    if (cols.size() >= 4) {
                        list.add(new KeluaranData(
                            cols.get(0).text(),
                            cols.get(1).text(),
                            cols.get(2).text(),
                            cols.get(3).text()
                        ));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<String> ambilDataPasaran(String pasaran) {
        List<String> result = new ArrayList<>();
        List<KeluaranData> data = ambilData();
        for (KeluaranData d : data) {
            switch (pasaran) {
                case "SYDNEY": if (!d.sydney.isEmpty()) result.add(d.sydney); break;
                case "SINGAPURA": if (!d.singapura.isEmpty()) result.add(d.singapura); break;
                case "HONGKONG": if (!d.hongkong.isEmpty()) result.add(d.hongkong); break;
            }
        }
        return result;
    }
}
