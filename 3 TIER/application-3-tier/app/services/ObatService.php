<?php
require_once __DIR__ . '/../models/Obat.php';

class ObatService {
    private $model;

    public function __construct($db) {
        $this->model = new Obat($db);
    }

    public function getAllObat() {
        return $this->model->getAll();
    }
}
