-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 08, 2024 at 04:54 PM
-- Server version: 10.4.17-MariaDB
-- PHP Version: 8.0.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `laperah(2.0)`
--

-- --------------------------------------------------------

--
-- Table structure for table `branches`
--

CREATE TABLE `branches` (
  `BranchID` varchar(5) NOT NULL,
  `Lokasi` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `branches`
--

INSERT INTO `branches` (`BranchID`, `Lokasi`) VALUES
('BR001', 'Bandung'),
('BR002', 'Jakarta'),
('BR003', 'Bali'),
('BR004', 'Surabaya'),
('BR005', 'Samarinda'),
('BR006', 'Padang');

-- --------------------------------------------------------

--
-- Table structure for table `mejas`
--

CREATE TABLE `mejas` (
  `MejaID` varchar(5) NOT NULL,
  `TipeMeja` varchar(50) DEFAULT NULL,
  `Capacity` int(11) DEFAULT NULL,
  `BranchID` varchar(5) DEFAULT NULL,
  `isoccupied` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `mejas`
--

INSERT INTO `mejas` (`MejaID`, `TipeMeja`, `Capacity`, `BranchID`, `isoccupied`) VALUES
('MJ001', 'Romantic', 2, 'BR001', 1),
('MJ002', 'General', 4, 'BR002', 0),
('MJ003', 'Family', 10, 'BR003', 0),
('MJ004', 'Romantic', 2, 'BR001', 1),
('MJ005', 'General', 4, 'BR001', 0),
('MJ006', 'Family', 10, 'BR002', 0),
('MJ007', 'General', 4, 'BR001', 0),
('MJ008', 'Romantic', 2, 'BR001', 1);

-- --------------------------------------------------------

--
-- Table structure for table `menus`
--

CREATE TABLE `menus` (
  `MenuID` varchar(5) NOT NULL,
  `MenuName` varchar(255) DEFAULT NULL,
  `Tipe` varchar(50) DEFAULT NULL,
  `BranchID` varchar(5) DEFAULT NULL,
  `Story` varchar(255) DEFAULT NULL,
  `Harga` int(11) DEFAULT NULL,
  `terjual` int(11) DEFAULT 0,
  `lokasiAsal` varchar(40) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `menus`
--

INSERT INTO `menus` (`MenuID`, `MenuName`, `Tipe`, `BranchID`, `Story`, `Harga`, `terjual`, `lokasiAsal`) VALUES
('MN001', 'Nasi Goreng UwU', 'Special Menu', 'BR001', 'Delicious dish with a unique flavor profile', 50, NULL, NULL),
('MN002', 'Mie Skibidi', 'Special Menu', 'BR002', 'A savory delight with a perfect blend of ingredients', 75, NULL, NULL),
('MN003', 'Kwetrawr', 'Special Menu', 'BR003', 'Exquisite culinary creation that will tantalize your taste buds', 100, NULL, NULL),
('MN004', 'Ketoprak', 'Lokal Special', 'BR004', '-', 30, 0, 'Liyue'),
('MN005', 'Ayam Venti', 'Lokal Special', 'BR005', '-', 55, 0, 'Mondstat'),
('MN006', 'Es buah Fontaine', 'Lokal Special', 'BR006', '-', 50, NULL, 'Fontaine'),
('MN007', 'Nasi Goreng gila', 'Normal', 'BR001', '-', 75, NULL, NULL),
('MN008', 'Bolu kukus Laplus', 'Normal', 'BR002', '-', 100, NULL, NULL),
('MN009', 'Indomie', 'Normal', 'BR001', '-', 30, 0, '-'),
('MN010', 'Bubur', 'Normal', 'BR004', '-', 55, 0, '-');

-- --------------------------------------------------------

--
-- Table structure for table `staffs`
--

CREATE TABLE `staffs` (
  `StaffID` varchar(5) NOT NULL,
  `StaffName` varchar(255) DEFAULT NULL,
  `BranchID` varchar(5) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `staffs`
--

INSERT INTO `staffs` (`StaffID`, `StaffName`, `BranchID`) VALUES
('ST001', 'Arya Wiguna', 'BR001'),
('ST002', 'Bryan Ko', 'BR002'),
('ST003', 'Timothy Gendong', 'BR003'),
('ST004', 'Aditya Farcry', 'BR004'),
('ST005', 'Kobo Kanaeru', 'BR005'),
('ST006', 'Hochimachi Suisei', 'BR006');

-- --------------------------------------------------------

--
-- Table structure for table `transactiondetail`
--

CREATE TABLE `transactiondetail` (
  `TransactionID` varchar(5) NOT NULL,
  `MenuID` varchar(5) NOT NULL,
  `MejaID` varchar(5) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `transactiondetail`
--

INSERT INTO `transactiondetail` (`TransactionID`, `MenuID`, `MejaID`) VALUES
('TR001', 'MN001', 'MJ001'),
('TR002', 'MN002', 'MJ002'),
('TR003', 'MN003', 'MJ003'),
('TR004', 'MN004', 'MJ004');

-- --------------------------------------------------------

--
-- Table structure for table `transactionheader`
--

CREATE TABLE `transactionheader` (
  `TransactionID` varchar(5) NOT NULL,
  `StaffID` varchar(5) DEFAULT NULL,
  `CustomerName` varchar(255) DEFAULT NULL,
  `Status` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `transactionheader`
--

INSERT INTO `transactionheader` (`TransactionID`, `StaffID`, `CustomerName`, `Status`) VALUES
('TR001', 'ST001', 'Bogo Satoru', 'Completed'),
('TR002', 'ST002', 'Frieren', 'DineIn'),
('TR003', 'ST003', 'Joe dabozo', 'Reservasion'),
('TR004', 'ST001', 'Tono', 'In Reserve');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `branches`
--
ALTER TABLE `branches`
  ADD PRIMARY KEY (`BranchID`);

--
-- Indexes for table `mejas`
--
ALTER TABLE `mejas`
  ADD PRIMARY KEY (`MejaID`);

--
-- Indexes for table `menus`
--
ALTER TABLE `menus`
  ADD PRIMARY KEY (`MenuID`),
  ADD KEY `BranchID` (`BranchID`);

--
-- Indexes for table `staffs`
--
ALTER TABLE `staffs`
  ADD PRIMARY KEY (`StaffID`),
  ADD KEY `BranchID` (`BranchID`);

--
-- Indexes for table `transactiondetail`
--
ALTER TABLE `transactiondetail`
  ADD PRIMARY KEY (`TransactionID`,`MenuID`),
  ADD KEY `FK_transactiondetail_MenuID` (`MenuID`);

--
-- Indexes for table `transactionheader`
--
ALTER TABLE `transactionheader`
  ADD PRIMARY KEY (`TransactionID`),
  ADD KEY `StaffID` (`StaffID`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `menus`
--
ALTER TABLE `menus`
  ADD CONSTRAINT `menus_ibfk_1` FOREIGN KEY (`BranchID`) REFERENCES `branches` (`BranchID`);

--
-- Constraints for table `staffs`
--
ALTER TABLE `staffs`
  ADD CONSTRAINT `staffs_ibfk_1` FOREIGN KEY (`BranchID`) REFERENCES `branches` (`BranchID`);

--
-- Constraints for table `transactiondetail`
--
ALTER TABLE `transactiondetail`
  ADD CONSTRAINT `FK_transactiondetail_MenuID` FOREIGN KEY (`MenuID`) REFERENCES `menus` (`MenuID`);

--
-- Constraints for table `transactionheader`
--
ALTER TABLE `transactionheader`
  ADD CONSTRAINT `transactionheader_ibfk_1` FOREIGN KEY (`StaffID`) REFERENCES `staffs` (`StaffID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
