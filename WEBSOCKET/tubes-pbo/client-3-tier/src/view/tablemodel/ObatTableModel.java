package view.tablemodel;

import model.Obat;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class ObatTableModel extends AbstractTableModel {

    private final String[] columnNames = {
            "ID", "Nama Obat", "Kategori", "Dosis", "Harga", "Stok"
    };

    private List<Obat> data = new ArrayList<>();

    public void setData(List<Obat> data) {
        this.data = data;
        fireTableDataChanged();
    }

    public Obat getDataAt(int row) {
        return data.get(row);
    }

    public void removeAt(int row) {
        data.remove(row);
        fireTableRowsDeleted(row, row);
    }

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
        Obat o = data.get(row);
        switch (col) {
            case 0: return o.getId();
            case 1: return o.getNamaObat();
            case 2: return o.getKategori();
            case 3: return o.getDosis();
            case 4: return o.getHarga();
            case 5: return o.getStok();
            default: return null;
        }
    }
}
