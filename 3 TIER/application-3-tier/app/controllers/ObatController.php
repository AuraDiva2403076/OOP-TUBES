<?php
require_once __DIR__ . '/../services/ObatService.php';
require_once __DIR__ . '/../config/Database.php';

class ObatController {

    public function index() {
        $database = new Database();
        $db = $database->getConnection();

        $service = new ObatService($db);
        $stmt = $service->getAllObat();

        $data = [];
        while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
            $data[] = $row;
        }

        header("Content-Type: application/json");
        echo json_encode($data);
    }
}









