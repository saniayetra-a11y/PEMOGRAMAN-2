package model;

/**
 * Model class untuk data Customer
 */
public class Customer {

    private int    idCustomer;
    private String nik;
    private String nama;
    private String alamat;
    private String noTelp;
    private String email;
    private String tglDaftar;

    public Customer() {}

    public Customer(int idCustomer, String nik, String nama, String alamat,
                    String noTelp, String email, String tglDaftar) {
        this.idCustomer = idCustomer;
        this.nik        = nik;
        this.nama       = nama;
        this.alamat     = alamat;
        this.noTelp     = noTelp;
        this.email      = email;
        this.tglDaftar  = tglDaftar;
    }

    // Getters & Setters
    public int    getIdCustomer()  { return idCustomer; }
    public void   setIdCustomer(int idCustomer) { this.idCustomer = idCustomer; }

    public String getNik()         { return nik; }
    public void   setNik(String nik) { this.nik = nik; }

    public String getNama()        { return nama; }
    public void   setNama(String nama) { this.nama = nama; }

    public String getAlamat()      { return alamat; }
    public void   setAlamat(String alamat) { this.alamat = alamat; }

    public String getNoTelp()      { return noTelp; }
    public void   setNoTelp(String noTelp) { this.noTelp = noTelp; }

    public String getEmail()       { return email; }
    public void   setEmail(String email) { this.email = email; }

    public String getTglDaftar()   { return tglDaftar; }
    public void   setTglDaftar(String tglDaftar) { this.tglDaftar = tglDaftar; }
}
