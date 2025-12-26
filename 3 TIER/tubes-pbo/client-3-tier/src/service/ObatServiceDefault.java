package service;

import model.Obat;
import java.net.*;
import java.io.*;
import java.util.*;
import org.json.JSONArray;
import org.json.JSONObject;

public class ObatServiceDefault implements ObatService {

    private static final String API_URL =
        "http://localhost/APPLICATION-TIER-PHP/public/index.php/obat";

    @SuppressWarnings("deprecation")
    @Override
    public List<Obat> getAll() {
        List<Obat> list = new ArrayList<>();

        try {
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream())
            );

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            JSONArray arr = new JSONArray(sb.toString());

            for (int i = 0; i < arr.length(); i++) {
                JSONObject o = arr.getJSONObject(i);

                list.add(new Obat(
                    o.getInt("id"),
                    o.getString("nama_obat"),
                    o.getString("kategori"),
                    o.getString("dosis"),
                    o.getInt("harga"),
                    o.getInt("stok")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // sementara belum dipakai
    @Override
    public void save(Obat o) {
        throw new UnsupportedOperationException("Use API POST");
    }

    @Override
    public void delete(int id) {
        throw new UnsupportedOperationException("Use API DELETE");
    }
}
