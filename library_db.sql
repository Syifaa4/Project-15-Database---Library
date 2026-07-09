-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 09, 2026 at 06:03 AM
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
-- Database: `library_db`
--

DELIMITER $$
--
-- Procedures
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `tambah_buku` (IN `p_judul` VARCHAR(100), IN `p_penulis` VARCHAR(100), IN `p_stok` INT)   BEGIN

INSERT INTO books(judul,penulis,stok)
VALUES(p_judul,p_penulis,p_stok);

END$$

--
-- Functions
--
CREATE DEFINER=`root`@`localhost` FUNCTION `total_buku` () RETURNS INT(11) DETERMINISTIC BEGIN

DECLARE jumlah INT;

SELECT COUNT(*)
INTO jumlah
FROM books;

RETURN jumlah;

END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `books`
--

CREATE TABLE `books` (
  `id_buku` int(11) NOT NULL,
  `judul` varchar(100) NOT NULL,
  `penulis` varchar(100) NOT NULL,
  `stok` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `books`
--

INSERT INTO `books` (`id_buku`, `judul`, `penulis`, `stok`) VALUES
(1, 'Pemrograman Java', 'Abdul Kadir', 6),
(2, 'Loversation', 'Valerie Patkar.', 4),
(3, 'Laut Bercerita', 'Leila S. Chudori', 3),
(4, 'Pengenalan Machine Learning dengan Python', 'Dios Kurniawan', 6),
(5, 'Seporsi Mie Ayam Sebelum Mati', 'Brian Khrisna', 8);

-- --------------------------------------------------------

--
-- Table structure for table `borrow`
--

CREATE TABLE `borrow` (
  `id_pinjam` int(11) NOT NULL,
  `id_user` int(11) NOT NULL,
  `id_buku` int(11) NOT NULL,
  `tanggal_pinjam` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `borrow`
--

INSERT INTO `borrow` (`id_pinjam`, `id_user`, `id_buku`, `tanggal_pinjam`) VALUES
(1, 1, 1, '2026-07-09'),
(2, 4, 2, '2026-07-09');

--
-- Triggers `borrow`
--
DELIMITER $$
CREATE TRIGGER `kurangi_stok` AFTER INSERT ON `borrow` FOR EACH ROW BEGIN

UPDATE books

SET stok = stok - 1

WHERE id_buku = NEW.id_buku;

END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id_user` int(11) NOT NULL,
  `nama` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id_user`, `nama`) VALUES
(1, 'Zayyan'),
(2, 'Rosella'),
(3, 'Afifah'),
(4, 'Syifa Nurul');

-- --------------------------------------------------------

--
-- Stand-in structure for view `view_peminjaman`
-- (See below for the actual view)
--
CREATE TABLE `view_peminjaman` (
`id_pinjam` int(11)
,`nama` varchar(100)
,`judul` varchar(100)
,`tanggal_pinjam` date
);

-- --------------------------------------------------------

--
-- Structure for view `view_peminjaman`
--
DROP TABLE IF EXISTS `view_peminjaman`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_peminjaman`  AS SELECT `borrow`.`id_pinjam` AS `id_pinjam`, `users`.`nama` AS `nama`, `books`.`judul` AS `judul`, `borrow`.`tanggal_pinjam` AS `tanggal_pinjam` FROM ((`borrow` join `users` on(`borrow`.`id_user` = `users`.`id_user`)) join `books` on(`borrow`.`id_buku` = `books`.`id_buku`)) ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `books`
--
ALTER TABLE `books`
  ADD PRIMARY KEY (`id_buku`);

--
-- Indexes for table `borrow`
--
ALTER TABLE `borrow`
  ADD PRIMARY KEY (`id_pinjam`),
  ADD KEY `id_user` (`id_user`),
  ADD KEY `id_buku` (`id_buku`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id_user`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `books`
--
ALTER TABLE `books`
  MODIFY `id_buku` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `borrow`
--
ALTER TABLE `borrow`
  MODIFY `id_pinjam` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `borrow`
--
ALTER TABLE `borrow`
  ADD CONSTRAINT `borrow_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `users` (`id_user`),
  ADD CONSTRAINT `borrow_ibfk_2` FOREIGN KEY (`id_buku`) REFERENCES `books` (`id_buku`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
