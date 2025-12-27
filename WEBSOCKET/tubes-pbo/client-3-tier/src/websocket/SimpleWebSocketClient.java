package websocket;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONObject;
import javax.swing.*;
import java.net.URI;

public class SimpleWebSocketClient {
    
    private MyWebSocketClient webSocketClient;
    private boolean connected = false;
    private WebSocketMessageListener listener;
    
    public void connect() {
        try {
            URI serverUri = new URI("ws://localhost:8080");
            
            webSocketClient = new MyWebSocketClient(serverUri);
            webSocketClient.connect();
            
        } catch (Exception e) {
            System.err.println("Failed to connect to WebSocket: " + e.getMessage());
            showConnectionError();
        }
    }
    
    private class MyWebSocketClient extends WebSocketClient {
        
        public MyWebSocketClient(URI serverUri) {
            super(serverUri);
        }
        
        @Override
        public void onOpen(ServerHandshake handshake) {
            connected = true;
            System.out.println("✅ Connected to WebSocket server");
            
            // Subscribe to updates
            JSONObject subscribeMsg = new JSONObject();
            subscribeMsg.put("action", "subscribe");
            send(subscribeMsg.toString());
            
            // Request initial data
            JSONObject getDataMsg = new JSONObject();
            getDataMsg.put("action", "get_pasien");
            send(getDataMsg.toString());
            
            // Update UI on EDT
            SwingUtilities.invokeLater(() -> {
                if (listener != null) {
                    listener.onConnected();
                }
            });
        }
        
        @Override
        public void onMessage(String message) {
            System.out.println("📥 Received: " + message);
            processMessage(message);
        }
        
        @Override
        public void onClose(int code, String reason, boolean remote) {
            connected = false;
            System.out.println("❌ Disconnected: " + reason + " (code: " + code + ")");
            
            SwingUtilities.invokeLater(() -> {
                if (listener != null) {
                    listener.onDisconnected(reason);
                }
            });
        }
        
        @Override
        public void onError(Exception ex) {
            System.err.println("💥 WebSocket error: " + ex.getMessage());
        }
    }
    
    private void processMessage(String message) {
        try {
            JSONObject json = new JSONObject(message);
            String type = json.optString("type", "unknown");
            
            SwingUtilities.invokeLater(() -> {
                if (listener != null) {
                    listener.onMessageReceived(json);
                }
                
                // Handle specific message types
                switch (type) {
                    case "pasien_data":
                        handlePasienData(json);
                        break;
                        
                    case "obat_data":
                        handleObatData(json);
                        break;
                        
                    case "realtime_update":
                        showRealTimeNotification(json);
                        break;
                        
                    case "notification":
                        showSystemNotification(json);
                        break;
                        
                    case "welcome":
                        System.out.println("Server says: " + json.optString("message"));
                        break;
                }
            });
            
        } catch (Exception e) {
            System.err.println("Error processing message: " + e.getMessage());
        }
    }
    
    private void handlePasienData(JSONObject data) {
        // Implement pasien data update
        System.out.println("📊 Received pasien data");
        // TODO: Update pasien table in UI
    }
    
    private void handleObatData(JSONObject data) {
        // Implement obat data update
        System.out.println("💊 Received obat data");
        // TODO: Update obat table in UI
    }
    
    private void showRealTimeNotification(JSONObject data) {
        // Show real-time update notification
        String time = new java.util.Date().toString();
        String message = "Real-time update at " + time;
        
        if (listener != null) {
            listener.onNotification("Update", message);
        }
    }
    
    private void showSystemNotification(JSONObject notification) {
        String title = notification.optString("title", "Notification");
        String message = notification.optString("message", "");
        
        JOptionPane.showMessageDialog(null, 
            message, 
            "WebSocket: " + title, 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void showConnectionError() {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(null,
                "⚠️ Cannot connect to WebSocket server!\n\n" +
                "Make sure Node.js server is running:\n" +
                "1. cd websocket-server\n" +
                "2. npm start\n\n" +
                "The application will continue without real-time updates.",
                "WebSocket Connection Error",
                JOptionPane.WARNING_MESSAGE);
        });
    }
    
    public void sendMessage(String action, JSONObject data) {
        if (connected && webSocketClient != null) {
            JSONObject message = new JSONObject();
            message.put("action", action);
            message.put("data", data);
            webSocketClient.send(message.toString());
        }
    }
    
    public void sendPing() {
        if (isConnected()) {
            JSONObject ping = new JSONObject();
            ping.put("action", "ping");
            webSocketClient.send(ping.toString());
        }
    }
    
    public void disconnect() {
        if (webSocketClient != null) {
            webSocketClient.close();
            connected = false;
            System.out.println("🔌 WebSocket disconnected");
        }
    }
    
    public boolean isConnected() {
        return connected && webSocketClient != null;
    }
    
    public void setMessageListener(WebSocketMessageListener listener) {
        this.listener = listener;
    }
    
    public interface WebSocketMessageListener {
        void onMessageReceived(JSONObject message);
        void onConnected();
        void onDisconnected(String reason);
        void onNotification(String title, String message);
    }
}