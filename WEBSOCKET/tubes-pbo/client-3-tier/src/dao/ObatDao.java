package dao;

import model.Obat;
import java.util.List;

public interface ObatDao {

    List<Obat> findAll();

    void insert(Obat o);

    void delete(int id);
}
