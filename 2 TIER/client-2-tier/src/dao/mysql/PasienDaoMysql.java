package dao.mysql;

import config.DatabaseConnection;
import dao.PasienDao;
import model.Pasien;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PasienDaoMysql implements PasienDao {

    private final Connection connection = DatabaseConnection.getConnection();

    @Override
    public void insert(Pasien pasien) {
        String sql = "INSERT INTO pasien (nama, email, umur, jenis_kelamin) VALUES (?,?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, pasien.getNama());
            ps.setString(2, pasien.getEmail());
            ps.setInt(3, pasien.getUmur());
            ps.setString(4, pasien.getJenisKelamin());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Pasien pasien) {
        String sql = "UPDATE pasien SET nama=?, email=?, umur=?, jenis_kelamin=? WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, pasien.getNama());
            ps.setString(2, pasien.getEmail());
            ps.setInt(3, pasien.getUmur());
            ps.setString(4, pasien.getJenisKelamin());
            ps.setInt(5, pasien.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM pasien WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Pasien> findAll() {
        List<Pasien> list = new ArrayList<>();
        String sql = "SELECT * FROM pasien";

        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Pasien p = new Pasien();
                p.setId(rs.getInt("id"));
                p.setNama(rs.getString("nama"));
                p.setEmail(rs.getString("email"));
                p.setUmur(rs.getInt("umur"));
                p.setJenisKelamin(rs.getString("jenis_kelamin"));
                list.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
