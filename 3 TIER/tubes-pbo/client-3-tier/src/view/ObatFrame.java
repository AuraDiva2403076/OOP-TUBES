package view;

import view.tablemodel.ObatTableModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;
import java.awt.*;

public class ObatFrame extends JFrame {

    private JTable table;
    private ObatTableModel tableModel;
    private TableRowSorter<ObatTableModel> sorter;

    private JButton btnTambah;
    private JButton btnHapus;
    private JButton btnRefresh;
    private JTextField txtSearch;

    public ObatFrame() {
        setTitle("NAURA FARMA - Data Obat");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== TABLE =====
        tableModel = new ObatTableModel();
        table = new JTable(tableModel);

        sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);

        add(new JScrollPane(table), BorderLayout.CENTER);

        // ===== SEARCH =====
        JPanel panelTop = new JPanel(new BorderLayout());
        panelTop.add(new JLabel(" Cari Obat: "), BorderLayout.WEST);

        txtSearch = new JTextField();
        panelTop.add(txtSearch, BorderLayout.CENTER);
        add(panelTop, BorderLayout.NORTH);

        // ===== BUTTON =====
        JPanel panelButton = new JPanel();
        btnTambah = new JButton("Tambah");
        btnHapus = new JButton("Hapus");
        btnRefresh = new JButton("Refresh");

        panelButton.add(btnTambah);
        panelButton.add(btnHapus);
        panelButton.add(btnRefresh);
        add(panelButton, BorderLayout.SOUTH);

        // ===== SEARCH LOGIC =====
        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { filter(); }
            public void removeUpdate(DocumentEvent e) { filter(); }
            public void changedUpdate(DocumentEvent e) { filter(); }

            private void filter() {
                String text = txtSearch.getText();
                if (text.trim().isEmpty()) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });
    }

    // ===== GETTER =====
    public JButton getBtnAdd() { return btnTambah; }
    public JButton getBtnDelete() { return btnHapus; }
    public JButton getBtnRefresh() { return btnRefresh; }
    public JTable getTable() { return table; }
    public ObatTableModel getTableModel() { return tableModel; }
}
