package view.tablemodel;

import model.Pasien;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class PasienTableModel extends AbstractTableModel {

    private final String[] columnNames = {
            "ID", "Nama", "Email", "Umur", "Jenis Kelamin"
    };

    private List<Pasien> data = new ArrayList<>();

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col) {
        Pasien p = data.get(row);
        switch (col) {
            case 0: return p.getId();
            case 1: return p.getNama();
            case 2: return p.getEmail();
            case 3: return p.getUmur();
            case 4: return p.getJenisKelamin();
            default: return null;
        }
    }

    // ===== CRUD SUPPORT =====
    public void setData(List<Pasien> data) {
        this.data = data;
        fireTableDataChanged();
    }

    public void addData(Pasien p) {
        data.add(p);
        fireTableRowsInserted(data.size() - 1, data.size() - 1);
    }

    public Pasien getDataAt(int row) {
        return data.get(row);
    }

    public void removeAt(int row) {
        data.remove(row);
        fireTableRowsDeleted(row, row);
    }

    public List<Pasien> getData() {
        return data;
    }
}
