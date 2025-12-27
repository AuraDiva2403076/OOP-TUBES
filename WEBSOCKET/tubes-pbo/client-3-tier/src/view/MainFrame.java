package view;

import controller.ObatController;
import controller.PasienController;
import websocket.WebSocketManager;

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
    // Di class MainFrame, tambahkan:
private JButton wsConnectBtn;
private JButton wsDisconnectBtn;
private JLabel wsStatusLabel;

private void initWebSocketControls() {
    JPanel wsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    wsPanel.setBorder(BorderFactory.createTitledBorder("WebSocket Controls"));
    
    wsConnectBtn = new JButton("🔗 Connect");
    wsDisconnectBtn = new JButton("🔌 Disconnect");
    JButton wsRefreshBtn = new JButton("🔄 Refresh Data");
    wsStatusLabel = new JLabel("Status: Not Connected");
    wsStatusLabel.setForeground(Color.RED);
    
    wsConnectBtn.addActionListener(e -> connectWebSocket());
    wsDisconnectBtn.addActionListener(e -> disconnectWebSocket());
    wsRefreshBtn.addActionListener(e -> refreshViaWebSocket());
    
    wsDisconnectBtn.setEnabled(false);
    
    wsPanel.add(wsConnectBtn);
    wsPanel.add(wsDisconnectBtn);
    wsPanel.add(wsRefreshBtn);
    wsPanel.add(Box.createHorizontalStrut(20));
    wsPanel.add(wsStatusLabel);
    
    // Add to frame (misalnya di NORTH atau SOUTH)
    add(wsPanel, BorderLayout.NORTH);
}

private void connectWebSocket() {
    new Thread(() -> {
        WebSocketManager.getInstance().connect();
        
        SwingUtilities.invokeLater(() -> {
            wsConnectBtn.setEnabled(false);
            wsDisconnectBtn.setEnabled(true);
            wsStatusLabel.setText("Status: Connecting...");
            wsStatusLabel.setForeground(Color.ORANGE);
        });
        
        // Check connection status after 3 seconds
        new Timer(3000, e -> {
            if (WebSocketManager.getInstance().isConnected()) {
                wsStatusLabel.setText("Status: Connected ✅");
                wsStatusLabel.setForeground(Color.GREEN);
            } else {
                wsStatusLabel.setText("Status: Failed to connect ❌");
                wsStatusLabel.setForeground(Color.RED);
                wsConnectBtn.setEnabled(true);
                wsDisconnectBtn.setEnabled(false);
            }
        }).start();
    }).start();
}

private void disconnectWebSocket() {
    WebSocketManager.getInstance().disconnect();
    
    wsConnectBtn.setEnabled(true);
    wsDisconnectBtn.setEnabled(false);
    wsStatusLabel.setText("Status: Disconnected");
    wsStatusLabel.setForeground(Color.RED);
}

private void refreshViaWebSocket() {
    if (WebSocketManager.getInstance().isConnected()) {
        // Request fresh data from server
        WebSocketManager.getInstance().sendDataRequest("pasien");
        WebSocketManager.getInstance().sendDataRequest("obat");
        
        JOptionPane.showMessageDialog(this,
            "Requesting fresh data from server via WebSocket...",
            "Refresh Data",
            JOptionPane.INFORMATION_MESSAGE);
    } else {
        JOptionPane.showMessageDialog(this,
            "WebSocket not connected. Please connect first.",
            "Connection Error",
            JOptionPane.ERROR_MESSAGE);
    }
}
}
