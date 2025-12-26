package controller;

import model.Pasien;
import service.PasienService;
import service.PasienServiceDefault;
import view.PasienDialog;
import view.PasienFrame;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class PasienController {

    private final PasienFrame frame;
    private final PasienService service = new PasienServiceDefault();

    public PasienController(PasienFrame frame) {
        this.frame = frame;
        initListener();
        loadData();
    }

    // ================== LISTENER ==================
    private void initListener() {

        // ===== TAMBAH =====
        frame.getBtnAdd().addActionListener(e -> {
            PasienDialog dialog = new PasienDialog(frame);
            dialog.getBtnSave().addActionListener(ev -> {
                try {
                    Pasien p = dialog.getPasien();
                    service.save(p);
                    dialog.dispose();
                    loadData(); // aman & konsisten
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Input tidak valid!");
                }
            });
            dialog.setVisible(true);
        });

        // ===== HAPUS (RESPONSIF + SEARCH AMAN) =====
        frame.getBtnDelete().addActionListener(e -> {

            int viewRow = frame.getTable().getSelectedRow();
            if (viewRow < 0) {
                JOptionPane.showMessageDialog(frame, "Pilih data pasien dulu!");
                return;
            }

            int modelRow = frame.getTable()
                    .convertRowIndexToModel(viewRow);

            Pasien p = frame.getTableModel().getDataAt(modelRow);

            int confirm = JOptionPane.showConfirmDialog(
                    frame,
                    "Hapus pasien " + p.getNama() + "?",
                    "Konfirmasi",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                service.delete(p.getId());              // ✅ FIX
                frame.getTableModel().removeAt(modelRow); // langsung hilang
            }
        });

        // ===== DOUBLE CLICK = EDIT =====
        frame.getTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {

                    int viewRow = frame.getTable().getSelectedRow();
                    if (viewRow < 0) return;

                    int modelRow = frame.getTable()
                            .convertRowIndexToModel(viewRow);

                    Pasien p = frame.getTableModel().getDataAt(modelRow);

                    PasienDialog dialog = new PasienDialog(frame, p);
                    dialog.getBtnSave().addActionListener(ev -> {
                        service.update(dialog.getPasien());
                        dialog.dispose();
                        loadData();
                    });
                    dialog.setVisible(true);
                }
            }
        });

        // ===== REFRESH =====
        frame.getBtnRefresh().addActionListener(e -> loadData());
    }

    // ================== LOAD DATA ==================
    private void loadData() {
        List<Pasien> list = service.getAll();
        frame.getTableModel().setData(list);
    }
}
