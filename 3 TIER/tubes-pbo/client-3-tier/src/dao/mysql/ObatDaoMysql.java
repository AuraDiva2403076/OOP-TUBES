package dao.mysql;

import config.DatabaseConnection;
import dao.ObatDao;
import model.Obat;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ObatDaoMysql implements ObatDao {

    private final Connection conn = DatabaseConnection.getConnection();

    @Override
    public List<Obat> findAll() {
        List<Obat> list = new ArrayList<>();
        String sql = "SELECT * FROM obat";

        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Obat o = new Obat();
                o.setId(rs.getInt("id"));
                o.setNamaObat(rs.getString("nama_obat"));
                o.setKategori(rs.getString("kategori"));
                o.setDosis(rs.getString("dosis"));
                o.setHarga(rs.getInt("harga"));
                o.setStok(rs.getInt("stok"));
                list.add(o);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void insert(Obat o) {
        String sql = "INSERT INTO obat (nama_obat, kategori, dosis, harga, stok) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, o.getNamaObat());
            ps.setString(2, o.getKategori());
            ps.setString(3, o.getDosis());
            ps.setInt(4, o.getHarga());
            ps.setInt(5, o.getStok());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM obat WHERE id=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
