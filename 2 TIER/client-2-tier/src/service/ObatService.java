package service;

import model.Obat;
import java.util.List;

public interface ObatService {

    List<Obat> getAll();

    void save(Obat o);

    void delete(int id);
}
