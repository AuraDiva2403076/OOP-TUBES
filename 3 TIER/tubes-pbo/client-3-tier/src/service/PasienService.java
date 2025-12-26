package service;

import java.util.List;
import model.Pasien;

public interface PasienService {
    void save(Pasien pasien);
    void update(Pasien pasien);
    void delete(int id);          
    List<Pasien> getAll();
}
