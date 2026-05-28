-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 28, 2026 at 05:42 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `dbaplikasipenilaianmahasiswa`
--

-- --------------------------------------------------------

--
-- Table structure for table `tbmahasiswa`
--

CREATE TABLE `tbmahasiswa` (
  `nim` varchar(15) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `semester` int(11) NOT NULL DEFAULT 1,
  `kelas` varchar(10) NOT NULL,
  `password` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbmahasiswa`
--

INSERT INTO `tbmahasiswa` (`nim`, `nama`, `semester`, `kelas`, `password`) VALUES
('231011401305', 'cess', 6, '05TPLM05', '2d2ca7eedf739ef4c3800713ec482e1a'),
('231011401511', 'sania yetra', 6, '05TPLM05', '81dc9bdb52d04dc20036dbd8313ed055');

-- --------------------------------------------------------

--
-- Table structure for table `tbmatakuliah`
--

CREATE TABLE `tbmatakuliah` (
  `kode_matakuliah` varchar(10) NOT NULL,
  `nama_matakuliah` varchar(100) NOT NULL,
  `jumlah_sks` int(11) NOT NULL DEFAULT 2
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbmatakuliah`
--

INSERT INTO `tbmatakuliah` (`kode_matakuliah`, `nama_matakuliah`, `jumlah_sks`) VALUES
('MK001', 'Pemrograman 1', 3),
('MK002', 'Pemrograman 2', 3),
('MK003', 'Basis Data', 3),
('MK004', 'Jaringan Komputer', 3);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tbmahasiswa`
--
ALTER TABLE `tbmahasiswa`
  ADD PRIMARY KEY (`nim`);

--
-- Indexes for table `tbmatakuliah`
--
ALTER TABLE `tbmatakuliah`
  ADD PRIMARY KEY (`kode_matakuliah`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
