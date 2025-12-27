package dao;

import java.util.List;
import model.Pasien;

public interface PasienDao {
    void insert(Pasien pasien);
    void update(Pasien pasien);
    void delete(int id);
    List<Pasien> findAll();
}
