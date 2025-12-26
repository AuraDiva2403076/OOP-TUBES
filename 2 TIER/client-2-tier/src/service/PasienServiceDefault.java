package service;

import dao.PasienDao;
import dao.mysql.PasienDaoMysql;
import model.Pasien;

import java.util.List;

public class PasienServiceDefault implements PasienService {

    private final PasienDao dao = new PasienDaoMysql();

    @Override
    public void save(Pasien pasien) {
        dao.insert(pasien);
    }

    @Override
    public void update(Pasien pasien) {
        dao.update(pasien);
    }

    @Override
    public void delete(int id) {      
        dao.delete(id);
    }

    @Override
    public List<Pasien> getAll() {
        return dao.findAll();
    }
}
