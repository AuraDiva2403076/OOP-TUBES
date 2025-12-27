<?php
error_reporting(E_ALL);
ini_set('display_errors', 1);

echo "<h2>Test Koneksi Database (APOTEK)</h2>";

// Load config & database
require_once __DIR__ . '/../app/config/Config.php';
require_once __DIR__ . '/../app/config/Database.php';

echo "<h3>1. Load Config & Database</h3>";
echo "✅ Config.php loaded<br>";
echo "✅ Database.php loaded<br>";

echo "<h3>2. Test Database Connection</h3>";

try {
    $db = new Database();
    $conn = $db->getConnection();

    if ($conn) {
        echo "✅ Database connected successfully!<br>";
        echo "Database: " . Config::$DB_NAME . "<br>";

        // Test query ke tabel OBAT
        $stmt = $conn->query("SELECT COUNT(*) AS total FROM obat");
        $result = $stmt->fetch(PDO::FETCH_ASSOC);

        echo "✅ Total data obat: " . $result['total'] . "<br>";
    } else {
        echo "❌ Database connection failed<br>";
    }

} catch (Exception $e) {
    echo "❌ Error: " . $e->getMessage();
}
