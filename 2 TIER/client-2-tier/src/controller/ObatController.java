package controller;

import model.Obat;
import service.ObatService;
import service.ObatServiceDefault;
import view.ObatFrame;

import javax.swing.*;

public class ObatController {

    private final ObatFrame frame;
    private final ObatService service = new ObatServiceDefault();

    public ObatController(ObatFrame frame) {
        this.frame = frame;
        initListener();
        loadData();
    }

    private void initListener() {

        // ===== HAPUS =====
        frame.getBtnDelete().addActionListener(e -> {
            int viewRow = frame.getTable().getSelectedRow();
            if (viewRow < 0) {
                JOptionPane.showMessageDialog(frame, "Pilih obat dulu!");
                return;
            }

            int modelRow = frame.getTable()
                    .convertRowIndexToModel(viewRow);

            Obat o = frame.getTableModel().getDataAt(modelRow);

            int confirm = JOptionPane.showConfirmDialog(
                    frame,
                    "Hapus obat " + o.getNamaObat() + "?",
                    "Konfirmasi",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                service.delete(o.getId());
                frame.getTableModel().removeAt(modelRow); // 🔥 TANPA REFRESH
            }
        });

        // ===== REFRESH =====
        frame.getBtnRefresh().addActionListener(e -> loadData());
    }

    private void loadData() {
        frame.getTableModel().setData(service.getAll());
    }
}
