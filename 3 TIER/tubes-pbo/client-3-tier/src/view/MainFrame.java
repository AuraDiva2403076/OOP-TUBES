package view;

import controller.ObatController;
import controller.PasienController;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("NAURA FARMA");
        setSize(1000, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();

        // ===== TAB PASIEN =====
        PasienFrame pasienFrame = new PasienFrame();
        new PasienController(pasienFrame);
        tabbedPane.addTab("Pasien", pasienFrame.getContentPane());

        // ===== TAB OBAT =====
        ObatFrame obatFrame = new ObatFrame();
        new ObatController(obatFrame);
        tabbedPane.addTab("Obat", obatFrame.getContentPane());

        add(tabbedPane, BorderLayout.CENTER);
    }
}
