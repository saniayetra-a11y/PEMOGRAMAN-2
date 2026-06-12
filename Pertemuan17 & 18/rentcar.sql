-- ============================================
-- DATABASE: rentcar
-- Aplikasi Web Penyewaan Mobil (Rent Car)
-- Pemrograman 2 - Pertemuan 17
-- ============================================

CREATE DATABASE IF NOT EXISTS rentcar;
USE rentcar;

-- Tabel Mobil
CREATE TABLE IF NOT EXISTS mobil (
    id_mobil     INT AUTO_INCREMENT PRIMARY KEY,
    nopol        VARCHAR(15)  NOT NULL UNIQUE,
    merk         VARCHAR(50)  NOT NULL,
    tipe         VARCHAR(50)  NOT NULL,
    warna        VARCHAR(30)  NOT NULL,
    tahun        YEAR         NOT NULL,
    harga_sewa   DECIMAL(10,0) NOT NULL,
    status       ENUM('tersedia','disewa','perawatan') DEFAULT 'tersedia',
    keterangan   TEXT
);

-- Tabel Customer
CREATE TABLE IF NOT EXISTS customer (
    id_customer  INT AUTO_INCREMENT PRIMARY KEY,
    nik          VARCHAR(20)  NOT NULL UNIQUE,
    nama         VARCHAR(100) NOT NULL,
    alamat       TEXT         NOT NULL,
    no_telp      VARCHAR(20)  NOT NULL,
    email        VARCHAR(100),
    tgl_daftar   DATE         DEFAULT (CURRENT_DATE)
);

-- Tabel Transaksi Sewa
CREATE TABLE IF NOT EXISTS transaksi (
    id_transaksi  INT AUTO_INCREMENT PRIMARY KEY,
    kode_transaksi VARCHAR(20) NOT NULL UNIQUE,
    id_customer   INT          NOT NULL,
    id_mobil      INT          NOT NULL,
    tgl_sewa      DATE         NOT NULL,
    tgl_kembali_rencana DATE   NOT NULL,
    tgl_kembali_aktual  DATE,
    lama_sewa     INT          NOT NULL,
    total_biaya   DECIMAL(12,0) NOT NULL,
    denda         DECIMAL(12,0) DEFAULT 0,
    status        ENUM('aktif','selesai','batal') DEFAULT 'aktif',
    keterangan    TEXT,
    FOREIGN KEY (id_customer) REFERENCES customer(id_customer),
    FOREIGN KEY (id_mobil)    REFERENCES mobil(id_mobil)
);

-- ============================================
-- DATA CONTOH
-- ============================================

INSERT INTO mobil (nopol, merk, tipe, warna, tahun, harga_sewa, status) VALUES
('B 1234 ABC', 'Toyota',  'Avanza',  'Hitam',  2021, 300000, 'tersedia'),
('B 5678 DEF', 'Honda',   'Brio',    'Putih',  2022, 250000, 'tersedia'),
('B 9999 GHI', 'Daihatsu','Xenia',   'Silver', 2020, 280000, 'tersedia'),
('B 1111 JKL', 'Suzuki',  'Ertiga',  'Merah',  2023, 350000, 'tersedia'),
('B 2222 MNO', 'Mitsubishi','Pajero', 'Hitam',  2022, 700000, 'tersedia');

INSERT INTO customer (nik, nama, alamat, no_telp, email) VALUES
('3201010101010001', 'Budi Santoso',   'Jl. Merdeka No.1, Jakarta', '081234567890', 'budi@email.com'),
('3201010101010002', 'Siti Rahayu',    'Jl. Sudirman No.5, Jakarta','081298765432', 'siti@email.com'),
('3201010101010003', 'Ahmad Fauzi',    'Jl. Gatot Subroto No.10',   '085612345678', 'ahmad@email.com');
