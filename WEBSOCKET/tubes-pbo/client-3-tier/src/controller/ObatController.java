package controller;

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

        // ❌ DELETE DINONAKTIFKAN (3-TIER)
        frame.getBtnDelete().addActionListener(e -> {
            JOptionPane.showMessageDialog(
                    frame,
                    "Fitur delete dilakukan melalui Application Tier (API)",
                    "Info",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });

        // ✅ REFRESH DATA DARI API
        frame.getBtnRefresh().addActionListener(e -> loadData());
    }

    private void loadData() {
        frame.getTableModel().setData(service.getAll());
    }
}
