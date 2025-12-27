package websocket;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.json.JSONObject;

import javafx.scene.paint.Color;

public class WebSocketManager {
    
    private static SimpleWebSocketClient client;
    private static WebSocketManager instance;
    
    private WebSocketManager() {
        // Private constructor for singleton
    }
    
    public static WebSocketManager getInstance() {
        if (instance == null) {
            instance = new WebSocketManager();
        }
        return instance;
    }
    
    public void connect() {
        if (client == null || !client.isConnected()) {
            client = new SimpleWebSocketClient();
            
            // Set up listener
            client.setMessageListener(new SimpleWebSocketClient.WebSocketMessageListener() {
                @Override
                public void onMessageReceived(JSONObject message) {
                    handleWebSocketMessage(message);
                }
                
                @Override
                public void onConnected() {
                    System.out.println("✅ WebSocket connected successfully");
                    showStatus("Connected to WebSocket server", Color.GREEN);
                }
                
                @Override
                public void onDisconnected(String reason) {
                    System.out.println("❌ WebSocket disconnected: " + reason);
                    showStatus("Disconnected: " + reason, Color.RED);
                }
                
                @Override
                public void onNotification(String title, String message) {
                    showNotification(title, message);
                }
            });
            
            client.connect();
        }
    }
    
    private void handleWebSocketMessage(JSONObject message) {
        String type = message.optString("type", "");
        
        switch (type) {
            case "pasien_data":
                updatePasienTable(message);
                break;
                
            case "obat_data":
                updateObatTable(message);
                break;
                
            case "realtime_update":
                // Refresh data
                refreshAllData();
                break;
        }
    }
    
    private void updatePasienTable(JSONObject data) {
        // Implementation to update pasien table
        System.out.println("Updating pasien table with WebSocket data");
        // Call your PasienController to refresh data
    }
    
    private void updateObatTable(JSONObject data) {
        // Implementation to update obat table
        System.out.println("Updating obat table with WebSocket data");
        // Call your ObatController to refresh data
    }
    
    private void refreshAllData() {
        SwingUtilities.invokeLater(() -> {
            // Refresh both tables
            System.out.println("Refreshing all data from WebSocket");
            // Implement refresh logic here
        });
    }
    
    private void showStatus(String message, Color color) {
        SwingUtilities.invokeLater(() -> {
            // Update status bar in MainFrame
            // You'll need to get reference to your status label
            System.out.println("Status: " + message);
        });
    }
    
    private void showNotification(String title, String message) {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(null,
                message,
                "WebSocket: " + title,
                JOptionPane.INFORMATION_MESSAGE);
        });
    }
    
    public void disconnect() {
        if (client != null) {
            client.disconnect();
            client = null;
        }
    }
    
    public boolean isConnected() {
        return client != null && client.isConnected();
    }
    
    public void sendDataRequest(String dataType) {
        if (isConnected()) {
            JSONObject request = new JSONObject();
            request.put("action", "get_" + dataType);
            client.sendMessage("get_" + dataType, new JSONObject());
        }
    }
}