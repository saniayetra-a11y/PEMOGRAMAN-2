package model;

/**
 * Model class untuk data Transaksi Sewa Mobil
 */
public class Transaksi {

    private int    idTransaksi;
    private String kodeTransaksi;
    private int    idCustomer;
    private int    idMobil;
    private String tglSewa;
    private String tglKembaliRencana;
    private String tglKembaliAktual;
    private int    lamaSewa;
    private double totalBiaya;
    private double denda;
    private String status;
    private String keterangan;

    // Join fields (dari tabel lain)
    private String namaCustomer;
    private String nopolMobil;
    private String merkMobil;
    private String tipeMobil;
    private double hargaSewa;

    public Transaksi() {}

    // Getters & Setters
    public int    getIdTransaksi()       { return idTransaksi; }
    public void   setIdTransaksi(int v)  { this.idTransaksi = v; }

    public String getKodeTransaksi()     { return kodeTransaksi; }
    public void   setKodeTransaksi(String v) { this.kodeTransaksi = v; }

    public int    getIdCustomer()        { return idCustomer; }
    public void   setIdCustomer(int v)   { this.idCustomer = v; }

    public int    getIdMobil()           { return idMobil; }
    public void   setIdMobil(int v)      { this.idMobil = v; }

    public String getTglSewa()           { return tglSewa; }
    public void   setTglSewa(String v)   { this.tglSewa = v; }

    public String getTglKembaliRencana() { return tglKembaliRencana; }
    public void   setTglKembaliRencana(String v) { this.tglKembaliRencana = v; }

    public String getTglKembaliAktual()  { return tglKembaliAktual; }
    public void   setTglKembaliAktual(String v) { this.tglKembaliAktual = v; }

    public int    getLamaSewa()          { return lamaSewa; }
    public void   setLamaSewa(int v)     { this.lamaSewa = v; }

    public double getTotalBiaya()        { return totalBiaya; }
    public void   setTotalBiaya(double v){ this.totalBiaya = v; }

    public double getDenda()             { return denda; }
    public void   setDenda(double v)     { this.denda = v; }

    public String getStatus()            { return status; }
    public void   setStatus(String v)    { this.status = v; }

    public String getKeterangan()        { return keterangan; }
    public void   setKeterangan(String v){ this.keterangan = v; }

    public String getNamaCustomer()      { return namaCustomer; }
    public void   setNamaCustomer(String v) { this.namaCustomer = v; }

    public String getNopolMobil()        { return nopolMobil; }
    public void   setNopolMobil(String v){ this.nopolMobil = v; }

    public String getMerkMobil()         { return merkMobil; }
    public void   setMerkMobil(String v) { this.merkMobil = v; }

    public String getTipeMobil()         { return tipeMobil; }
    public void   setTipeMobil(String v) { this.tipeMobil = v; }

    public double getHargaSewa()         { return hargaSewa; }
    public void   setHargaSewa(double v) { this.hargaSewa = v; }

    public double getGrandTotal()        { return totalBiaya + denda; }
}
