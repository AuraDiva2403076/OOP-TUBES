package model;

public class Obat {

    private int id;
    private String namaObat;
    private String kategori;
    private String dosis;
    private int harga;
    private int stok;

    // ✅ CONSTRUCTOR LENGKAP (WAJIB ADA)
    public Obat(int id, String namaObat, String kategori, String dosis, int harga, int stok) {
        this.id = id;
        this.namaObat = namaObat;
        this.kategori = kategori;
        this.dosis = dosis;
        this.harga = harga;
        this.stok = stok;
    }

    // ✅ CONSTRUCTOR KOSONG (AMAN & OPSIONAL)
    public Obat() {
    }

    // ===== GETTER & SETTER =====
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamaObat() {
        return namaObat;
    }

    public void setNamaObat(String namaObat) {
        this.namaObat = namaObat;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getDosis() {
        return dosis;
    }

    public void setDosis(String dosis) {
        this.dosis = dosis;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public int getStok() {
        return stok;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }
}
