package service;

import model.Obat;
import java.net.*;
import java.io.*;
import java.util.*;
import org.json.JSONObject;
import org.json.JSONArray;

public class ObatServiceDefault implements ObatService {

    private static final String API_URL = 
        "http://localhost/application-3-tier/public/index.php/obat";

    @SuppressWarnings("deprecation")
    @Override
    public List<Obat> getAll() {
        List<Obat> list = new ArrayList<>();

        try {
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            // Tambahkan timeout untuk menghindari blocking
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            int responseCode = conn.getResponseCode();
            
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "UTF-8")
                );

                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                br.close();

                String jsonString = sb.toString();
                
                // Debug: print response untuk melihat format JSON
                System.out.println("JSON Response: " + jsonString);
                
                JSONArray arr = new JSONArray(jsonString);

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
            } else {
                System.err.println("HTTP Error: " + responseCode);
            }

        } catch (MalformedURLException e) {
            System.err.println("URL tidak valid: " + API_URL);
            e.printStackTrace();
        } catch (SocketTimeoutException e) {
            System.err.println("Timeout saat menghubungi server");
        } catch (IOException e) {
            System.err.println("IO Error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public void save(Obat o) {
        throw new UnsupportedOperationException("Use API POST");
    }

    @Override
    public void delete(int id) {
        throw new UnsupportedOperationException("Use API DELETE");
    }
}