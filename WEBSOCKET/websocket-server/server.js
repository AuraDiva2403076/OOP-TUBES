const WebSocket = require('ws');
const mysql = require('mysql2');
require('dotenv').config();

// Buat WebSocket Server di port 8080
const wss = new WebSocket.Server({ port: 8080 });

console.log('🚀 WebSocket Server running on ws://localhost:8080');
console.log('⏰ Started at:', new Date().toLocaleString());

// Koneksi database MySQL
const db = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: '',
    database: 'naura_farma'
});

db.connect((err) => {
    if (err) {
        console.error('❌ Database connection failed:', err.message);
    } else {
        console.log('✅ Connected to MySQL database');
    }
});

// Handle koneksi client
wss.on('connection', (ws) => {
    console.log('👤 New client connected');
    
    // Kirim welcome message
    ws.send(JSON.stringify({
        type: 'welcome',
        message: 'Connected to Naura Farma WebSocket Server',
        timestamp: new Date().toISOString()
    }));
    
    // Handle pesan dari client
    ws.on('message', (message) => {
        try {
            const data = JSON.parse(message);
            console.log('📩 Received:', data);
            
            switch (data.action) {
                case 'subscribe':
                    handleSubscribe(ws, data);
                    break;
                    
                case 'get_pasien':
                    getPasienData(ws);
                    break;
                    
                case 'get_obat':
                    getObatData(ws);
                    break;
                    
                case 'ping':
                    ws.send(JSON.stringify({
                        type: 'pong',
                        timestamp: new Date().toISOString()
                    }));
                    break;
                    
                default:
                    ws.send(JSON.stringify({
                        type: 'error',
                        message: 'Unknown action'
                    }));
            }
        } catch (error) {
            console.error('Error processing message:', error);
        }
    });
    
    // Handle disconnect
    ws.on('close', () => {
        console.log('👋 Client disconnected');
    });
    
    // Handle error
    ws.on('error', (error) => {
        console.error('WebSocket error:', error);
    });
});

// Fungsi untuk subscribe
function handleSubscribe(ws, data) {
    ws.subscribed = true;
    ws.send(JSON.stringify({
        type: 'subscribed',
        message: 'Subscribed to real-time updates',
        timestamp: new Date().toISOString()
    }));
    
    // Kirim update setiap 5 detik
    const interval = setInterval(() => {
        if (ws.readyState === WebSocket.OPEN && ws.subscribed) {
            sendRealTimeUpdate(ws);
        } else {
            clearInterval(interval);
        }
    }, 5000);
    
    ws.on('close', () => {
        clearInterval(interval);
    });
}

// Fungsi untuk mendapatkan data pasien
function getPasienData(ws) {
    db.query('SELECT * FROM pasien LIMIT 10', (error, results) => {
        if (error) {
            ws.send(JSON.stringify({
                type: 'error',
                message: 'Failed to fetch pasien data',
                error: error.message
            }));
            return;
        }
        
        ws.send(JSON.stringify({
            type: 'pasien_data',
            data: results,
            timestamp: new Date().toISOString()
        }));
    });
}

// Fungsi untuk mendapatkan data obat
function getObatData(ws) {
    db.query('SELECT * FROM obat LIMIT 10', (error, results) => {
        if (error) {
            ws.send(JSON.stringify({
                type: 'error',
                message: 'Failed to fetch obat data',
                error: error.message
            }));
            return;
        }
        
        ws.send(JSON.stringify({
            type: 'obat_data',
            data: results,
            timestamp: new Date().toISOString()
        }));
    });
}

// Fungsi untuk mengirim update real-time
function sendRealTimeUpdate(ws) {
    const update = {
        type: 'realtime_update',
        timestamp: new Date().toISOString(),
        message: 'Real-time update from server',
        data: {
            server_time: new Date().toLocaleString(),
            connected_clients: wss.clients.size,
            random_value: Math.floor(Math.random() * 100)
        }
    };
    
    ws.send(JSON.stringify(update));
}

// Broadcast ke semua client
function broadcastToAll(message) {
    wss.clients.forEach((client) => {
        if (client.readyState === WebSocket.OPEN) {
            client.send(JSON.stringify(message));
        }
    });
}

// Simulasi data berubah (misal: stok obat berkurang)
setInterval(() => {
    const notification = {
        type: 'notification',
        timestamp: new Date().toISOString(),
        title: 'System Update',
        message: 'Database synchronized successfully',
        priority: 'info'
    };
    
    broadcastToAll(notification);
}, 15000); // Setiap 15 detik

// Handle server shutdown
process.on('SIGINT', () => {
    console.log('\n🛑 Shutting down WebSocket server...');
    db.end();
    wss.close();
    process.exit(0);
});