package view;

import model.Pasien;

import javax.swing.*;
import java.awt.*;

public class PasienDialog extends JDialog {

    private JTextField txtNama;
    private JTextField txtEmail;
    private JTextField txtUmur;
    private JComboBox<String> cbJenisKelamin;

    private JButton btnSave;
    private JButton btnCancel;

    private Pasien pasien; // null = tambah | ada = edit

    // ===================== TAMBAH =====================
    public PasienDialog(JFrame parent) {
        super(parent, "Tambah Pasien", true);
        initComponents();
    }

    // ===================== EDIT ======================
    public PasienDialog(JFrame parent, Pasien pasien) {
        super(parent, "Edit Pasien", true);
        this.pasien = pasien;
        initComponents();
        loadData();
    }

    // ===================== INIT UI ===================
    private void initComponents() {
        setSize(350, 300);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        formPanel.add(new JLabel("Nama"));
        txtNama = new JTextField();
        formPanel.add(txtNama);

        formPanel.add(new JLabel("Email"));
        txtEmail = new JTextField();
        formPanel.add(txtEmail);

        formPanel.add(new JLabel("Umur"));
        txtUmur = new JTextField();
        formPanel.add(txtUmur);

        formPanel.add(new JLabel("Jenis Kelamin"));
        cbJenisKelamin = new JComboBox<>(new String[]{"Laki-laki", "Perempuan"});
        formPanel.add(cbJenisKelamin);

        btnSave = new JButton("Simpan");
        btnCancel = new JButton("Batal");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        btnCancel.addActionListener(e -> dispose());
    }

    // ===================== LOAD DATA (EDIT) ==========
    private void loadData() {
        if (pasien != null) {
            txtNama.setText(pasien.getNama());
            txtEmail.setText(pasien.getEmail());
            txtUmur.setText(String.valueOf(pasien.getUmur()));
            cbJenisKelamin.setSelectedItem(pasien.getJenisKelamin());
        }
    }

    // ===================== GET DATA ==================
    public Pasien getPasien() {
        if (pasien == null) {
            pasien = new Pasien();
        }

        pasien.setNama(txtNama.getText());
        pasien.setEmail(txtEmail.getText());
        pasien.setUmur(Integer.parseInt(txtUmur.getText()));
        pasien.setJenisKelamin(cbJenisKelamin.getSelectedItem().toString());

        return pasien;
    }

    // ===================== BUTTON ====================
    public JButton getBtnSave() {
        return btnSave;
    }
}
