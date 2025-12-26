package view;

import model.Obat;

import javax.swing.*;
import java.awt.*;

public class ObatDialog extends JDialog {

    private JTextField txtNama;
    private JTextField txtKategori;
    private JTextField txtDosis;
    private JTextField txtHarga;
    private JTextField txtStok;

    private JButton btnSave;
    private JButton btnCancel;

    public ObatDialog(Frame parent) {
        super(parent, "Tambah Obat", true);
        initComponents();
    }

    private void initComponents() {
        setSize(400, 300);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout());

        JPanel panelForm = new JPanel(new GridLayout(5, 2, 5, 5));

        panelForm.add(new JLabel("Nama Obat"));
        txtNama = new JTextField();
        panelForm.add(txtNama);

        panelForm.add(new JLabel("Kategori"));
        txtKategori = new JTextField();
        panelForm.add(txtKategori);

        panelForm.add(new JLabel("Dosis"));
        txtDosis = new JTextField();
        panelForm.add(txtDosis);

        panelForm.add(new JLabel("Harga"));
        txtHarga = new JTextField();
        panelForm.add(txtHarga);

        panelForm.add(new JLabel("Stok"));
        txtStok = new JTextField();
        panelForm.add(txtStok);

        add(panelForm, BorderLayout.CENTER);

        JPanel panelButton = new JPanel();

        btnSave = new JButton("Simpan");
        btnCancel = new JButton("Batal");

        panelButton.add(btnSave);
        panelButton.add(btnCancel);

        add(panelButton, BorderLayout.SOUTH);

        btnCancel.addActionListener(e -> dispose());
    }

    // 🔽 AMBIL DATA DARI FORM
    public Obat getObat() {
        Obat o = new Obat();
        o.setNamaObat(txtNama.getText());
        o.setKategori(txtKategori.getText());
        o.setDosis(txtDosis.getText());
        o.setHarga(Integer.parseInt(txtHarga.getText()));
        o.setStok(Integer.parseInt(txtStok.getText()));
        return o;
    }

    public JButton getBtnSave() {
        return btnSave;
    }
}
