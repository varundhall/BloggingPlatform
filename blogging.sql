-- phpMyAdmin SQL Dump
-- version 4.5.4.1deb2ubuntu2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Mar 17, 2017 at 07:00 PM
-- Server version: 5.7.17-0ubuntu0.16.04.1
-- PHP Version: 7.0.15-0ubuntu0.16.04.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `blogging`
--

-- --------------------------------------------------------

--
-- Table structure for table `blog`
--

CREATE TABLE `blog` (
  `id` varchar(10) NOT NULL,
  `title` text,
  `plainText` text NOT NULL,
  `time_last_updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `blogInfo`
--

CREATE TABLE `blogInfo` (
  `id` varchar(10) NOT NULL,
  `paragraphId` varchar(10) NOT NULL DEFAULT '',
  `comment_id` varchar(10) NOT NULL DEFAULT '',
  `comment` text,
  `time_last_updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `blogPara`
--

CREATE TABLE `blogPara` (
  `blog_id` varchar(10) NOT NULL,
  `paragraphId` varchar(10) NOT NULL,
  `paragraph` text,
  `time_last_updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `blog`
--
ALTER TABLE `blog`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `blogInfo`
--
ALTER TABLE `blogInfo`
  ADD PRIMARY KEY (`id`,`paragraphId`,`comment_id`);

--
-- Indexes for table `blogPara`
--
ALTER TABLE `blogPara`
  ADD PRIMARY KEY (`blog_id`,`paragraphId`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `blogInfo`
--
ALTER TABLE `blogInfo`
  ADD CONSTRAINT `blogInfo_ibfk_1` FOREIGN KEY (`id`,`paragraphId`) REFERENCES `blogPara` (`blog_id`, `paragraphId`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
