package com.unpam.view;

/**
 * PesanDialog – View Helper (Web Version)
 * Pertemuan 14 – Aplikasi Web MVC
 *
 * Di aplikasi web tidak menggunakan dialog konfirmasi seperti desktop.
 * Class ini dibuat agar tidak banyak perubahan pada source code model.
 * Pesan konfirmasi langsung diproses tanpa dialog.
 */
public class PesanDialog {

    /**
     * Di aplikasi web, konfirmasi selalu dianggap "Ya"
     * karena validasi dilakukan di sisi client (HTML form).
     */
    public boolean konfirmasi(String pesan) {
        return true;
    }

    /**
     * Tampilkan pesan informasi (tidak ada popup di web)
     */
    public void tampilPesan(String pesan) {
        // Di web, pesan ditampilkan lewat response HTML
        System.out.println("[INFO] " + pesan);
    }
}
