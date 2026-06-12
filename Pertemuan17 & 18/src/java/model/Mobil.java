package model;

/**
 * Model class untuk data Mobil
 */
public class Mobil {

    private int    idMobil;
    private String nopol;
    private String merk;
    private String tipe;
    private String warna;
    private int    tahun;
    private double hargaSewa;
    private String status;
    private String keterangan;

    public Mobil() {}

    public Mobil(int idMobil, String nopol, String merk, String tipe,
                 String warna, int tahun, double hargaSewa, String status, String keterangan) {
        this.idMobil    = idMobil;
        this.nopol      = nopol;
        this.merk       = merk;
        this.tipe       = tipe;
        this.warna      = warna;
        this.tahun      = tahun;
        this.hargaSewa  = hargaSewa;
        this.status     = status;
        this.keterangan = keterangan;
    }

    // Getters & Setters
    public int    getIdMobil()    { return idMobil; }
    public void   setIdMobil(int idMobil) { this.idMobil = idMobil; }

    public String getNopol()      { return nopol; }
    public void   setNopol(String nopol) { this.nopol = nopol; }

    public String getMerk()       { return merk; }
    public void   setMerk(String merk) { this.merk = merk; }

    public String getTipe()       { return tipe; }
    public void   setTipe(String tipe) { this.tipe = tipe; }

    public String getWarna()      { return warna; }
    public void   setWarna(String warna) { this.warna = warna; }

    public int    getTahun()      { return tahun; }
    public void   setTahun(int tahun) { this.tahun = tahun; }

    public double getHargaSewa()  { return hargaSewa; }
    public void   setHargaSewa(double hargaSewa) { this.hargaSewa = hargaSewa; }

    public String getStatus()     { return status; }
    public void   setStatus(String status) { this.status = status; }

    public String getKeterangan() { return keterangan; }
    public void   setKeterangan(String keterangan) { this.keterangan = keterangan; }
}
