package view;

import view.tablemodel.PasienTableModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;
import java.awt.*;

public class PasienFrame extends JFrame {

    private JTable table;
    private PasienTableModel tableModel;
    private TableRowSorter<PasienTableModel> sorter;

    private JButton btnTambah;
    private JButton btnHapus;
    private JButton btnRefresh;
    private JTextField txtSearch;

    public PasienFrame() {
        setTitle("NAURA FARMA - Data Pasien");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== TABLE =====
        tableModel = new PasienTableModel();
        table = new JTable(tableModel);

        sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);

        add(new JScrollPane(table), BorderLayout.CENTER);

        // ===== PANEL ATAS (SEARCH) =====
        JPanel panelTop = new JPanel(new BorderLayout());
        panelTop.add(new JLabel(" Cari Pasien: "), BorderLayout.WEST);

        txtSearch = new JTextField();
        panelTop.add(txtSearch, BorderLayout.CENTER);

        add(panelTop, BorderLayout.NORTH);

        // ===== PANEL BUTTON =====
        JPanel panelButton = new JPanel();

        btnTambah = new JButton("Tambah");
        btnHapus = new JButton("Hapus");
        btnRefresh = new JButton("Refresh");

        panelButton.add(btnTambah);
        panelButton.add(btnHapus);
        panelButton.add(btnRefresh);

        add(panelButton, BorderLayout.SOUTH);

        // ===== SEARCH LISTENER =====
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

    // ===== GETTER (DIPAKAI CONTROLLER) =====
    public JButton getBtnAdd() {
        return btnTambah;
    }

    public JButton getBtnDelete() {
        return btnHapus;
    }

    public JButton getBtnRefresh() {
        return btnRefresh;
    }

    public JTable getTable() {
        return table;
    }

    public PasienTableModel getTableModel() {
        return tableModel;
    }
}
