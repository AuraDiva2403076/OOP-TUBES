package service;

import dao.ObatDao;
import dao.mysql.ObatDaoMysql;
import model.Obat;

import java.util.List;

public class ObatServiceDefault implements ObatService {

    private final ObatDao dao = new ObatDaoMysql();

    @Override
    public List<Obat> getAll() {
        return dao.findAll();
    }

    @Override
    public void save(Obat o) {
        dao.insert(o);
    }

    @Override
    public void delete(int id) {
        dao.delete(id);
    }
}
