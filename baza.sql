-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Czas generowania: 25 Sty 2020, 13:26
-- Wersja serwera: 5.7.29-cll-lve
-- Wersja PHP: 7.3.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Baza danych: `mateuszs_projekt`
--
CREATE DATABASE IF NOT EXISTS `szkolajazdy` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
USE `szkolajazdy`;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `Instruktorzy`
--

CREATE TABLE `Instruktorzy` (
  `id` int(10) NOT NULL,
  `imię` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `nazwisko` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `telefon` int(9) NOT NULL,
  `pesel` varchar(12) COLLATE utf8_unicode_ci NOT NULL,
  `hasło` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `godzStartuPracy` int(2) NOT NULL DEFAULT '8',
  `godzKoncaPracy` int(2) NOT NULL DEFAULT '16'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Zrzut danych tabeli `Instruktorzy`
--

INSERT INTO `Instruktorzy` (`id`, `imię`, `nazwisko`, `telefon`, `pesel`, `hasło`, `email`, `godzStartuPracy`, `godzKoncaPracy`) VALUES
(1, 'Grzegorz', 'Hałan', 555666777, '721212222', 'grzechu123', 'grzechu@vip.pl', 10, 16),
(2, 'Dagmara', 'Stańczyk', 88855566, '800104555', 'kurs123', 'daga@wp.pl', 9, 16),
(4, 'Testowy', 'Instruktor', 3232, '212112', 'tester', 'instruktor@testowy.pl', 10, 12),
(5, 'sdasd', 'sda', 1212, '1221', 'asddas', 'asdas', 11, 11),
(6, 'Nowak', 'Adam', 1234, '1234', '1234', 'a.n@wp.pl', 9, 15),
(7, 'asfasf', 'asasf', 12341254, '123124', 'gasfgas', 'fasfaf', 11, 13),
(8, 'gfasfa', 'afasf', 123, '123', 'asags', 'gasgf', 11, 13),
(9, 'asd', 'fd', 1212, '123', 'gasg', 'fasf', 11, 14),
(16, 'fas', 'asd', 123, 'asf', 'asg', 'gas', 12, 15),
(17, 'fa', 'af', 123, 'af', 'asg', 'asd', 12, 13);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `Instruktorzy_Kategoria`
--

CREATE TABLE `Instruktorzy_Kategoria` (
  `Instruktorzyid` int(10) NOT NULL,
  `Kategoriaid` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Zrzut danych tabeli `Instruktorzy_Kategoria`
--

INSERT INTO `Instruktorzy_Kategoria` (`Instruktorzyid`, `Kategoriaid`) VALUES
(1, 1),
(2, 1),
(7, 1),
(17, 1),
(1, 2),
(2, 2);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `Kategoria`
--

CREATE TABLE `Kategoria` (
  `id` int(10) NOT NULL,
  `oznaczenie` varchar(2) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Zrzut danych tabeli `Kategoria`
--

INSERT INTO `Kategoria` (`id`, `oznaczenie`) VALUES
(1, 'A'),
(2, 'B');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `Rezerwacje`
--

CREATE TABLE `Rezerwacje` (
  `id` int(10) NOT NULL,
  `usługa` int(10) NOT NULL,
  `data` datetime DEFAULT NULL,
  `klient` int(10) NOT NULL,
  `instruktor` int(10) NOT NULL,
  `obecnosc` varchar(10) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'Oczekujące' COMMENT 'nieobecny, oczekujacy lub obecny'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Zrzut danych tabeli `Rezerwacje`
--

INSERT INTO `Rezerwacje` (`id`, `usługa`, `data`, `klient`, `instruktor`, `obecnosc`) VALUES
(29, 1, '2020-01-01 09:00:00', 1, 2, 'Oczekujący'),
(30, 1, '2020-01-01 10:00:00', 1, 2, 'Oczekujący'),
(31, 1, '2020-01-01 15:00:00', 1, 2, 'Oczekujący'),
(33, 1, '2020-01-02 11:00:00', 1, 2, 'Oczekujący'),
(34, 1, '2020-01-03 10:00:00', 1, 2, 'Oczekujący'),
(35, 1, '2020-01-04 10:00:00', 1, 2, 'Nieobecny'),
(36, 1, '2020-01-09 12:00:00', 1, 2, 'Oczekujący'),
(38, 1, '2020-01-17 13:00:00', 4, 2, 'Oczekujący'),
(40, 1, '2020-01-09 09:00:00', 1, 2, 'Oczekujący'),
(41, 1, '2020-01-02 12:00:00', 4, 2, 'Oczekujący'),
(42, 1, '2020-01-02 09:00:00', 1, 2, 'Oczekujący'),
(43, 1, '2020-01-02 14:00:00', 1, 2, 'Oczekujący'),
(44, 1, '2020-01-09 13:00:00', 1, 2, 'Oczekujący'),
(45, 1, '2020-01-09 11:00:00', 1, 2, 'Oczekujący'),
(46, 1, '2020-01-08 11:00:00', 1, 2, 'Oczekujący'),
(47, 1, '2020-01-09 15:00:00', 1, 2, 'Oczekujący'),
(48, 1, '2020-01-10 10:00:00', 1, 2, 'Oczekujący'),
(50, 1, '2020-01-01 12:00:00', 1, 2, 'Oczekujący'),
(56, 1, '2020-01-02 11:00:00', 3, 1, 'Oczekujący'),
(57, 1, '2020-01-10 12:00:00', 1, 1, 'Oczekujący'),
(58, 5, '2020-01-16 13:00:00', 1, 1, 'Oczekujący'),
(59, 1, '2020-01-09 14:00:00', 1, 1, 'Oczekujący'),
(60, 1, '2020-01-03 12:00:00', 1, 1, 'Obecny'),
(72, 5, '2020-01-18 10:00:00', 1, 1, 'Oczekujący'),
(73, 5, '2020-01-18 11:00:00', 1, 1, 'Oczekujący'),
(74, 5, '2020-01-18 12:00:00', 1, 1, 'Oczekujący'),
(75, 5, '2020-01-18 13:00:00', 1, 1, 'Oczekujący'),
(76, 5, '2020-01-19 10:00:00', 1, 1, 'Oczekujący'),
(77, 5, '2020-01-19 11:00:00', 1, 1, 'Oczekujący'),
(78, 5, '2020-01-20 12:00:00', 1, 1, 'Oczekujący'),
(79, 5, '2020-01-21 10:00:00', 1, 1, 'Oczekujący'),
(80, 5, '2020-01-28 10:00:00', 1, 1, 'Oczekujący'),
(81, 5, '2020-01-29 09:00:00', 1, 2, 'Oczekujący'),
(82, 5, '2020-01-31 09:00:00', 1, 2, 'Oczekujący'),
(83, 1, '2020-01-23 11:00:00', 1, 2, 'Oczekujący'),
(84, 1, '2020-01-29 10:00:00', 3, 1, 'Oczekujące'),
(85, 1, '2020-01-28 12:00:00', 1, 1, 'Oczekujące'),
(86, 5, '2020-01-28 13:00:00', 1, 1, 'Oczekujące'),
(87, 1, '2020-01-28 14:00:00', 1, 1, 'Oczekujące'),
(88, 1, '2020-01-28 15:00:00', 1, 1, 'Oczekujące');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `Transakcje`
--

CREATE TABLE `Transakcje` (
  `id` int(10) NOT NULL,
  `uzytkownik` int(10) NOT NULL,
  `data` date DEFAULT NULL,
  `kwota` int(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Zrzut danych tabeli `Transakcje`
--

INSERT INTO `Transakcje` (`id`, `uzytkownik`, `data`, `kwota`) VALUES
(1, 1, '2020-01-07', 2000),
(2, 1, '2020-01-13', -80),
(3, 1, '2020-01-13', -120),
(4, 1, '2020-01-13', -120),
(5, 1, '2020-01-13', -120),
(6, 1, '2020-01-13', -120),
(8, 1, '2020-01-13', -80),
(9, 1, '2020-01-13', -80),
(10, 1, '2020-01-13', -80),
(11, 1, '2020-01-13', -120),
(12, 1, '2020-01-13', -80),
(13, 1, '2020-01-13', -120),
(14, 1, '2020-01-13', -80),
(15, 1, '2020-01-13', -120),
(16, 1, '2020-01-13', -80),
(17, 1, '2020-01-13', -80),
(18, 1, '2020-01-13', -80),
(19, 1, '2020-01-13', -80),
(20, 1, '2020-01-13', -80),
(21, 1, '2020-01-13', -80),
(22, 1, '2020-01-13', -80),
(23, 1, '2020-01-13', -120),
(24, 4, '2020-01-13', -80),
(25, 1, '2020-01-13', -120),
(26, 1, '2020-01-13', -80),
(27, 1, '2020-01-13', 10000),
(28, 4, '2020-01-13', -80),
(29, 1, '2020-01-13', -80),
(30, 1, '2020-01-13', -80),
(31, 1, '2020-01-13', -80),
(32, 1, '2020-01-13', -80),
(33, 1, '2020-01-13', -80),
(34, 1, '2020-01-13', -80),
(35, 1, '2020-01-13', -80),
(36, 1, '2020-01-13', -80),
(37, 1, '2020-01-13', -80),
(38, 1, '2020-01-13', -80),
(39, 1, '2020-01-13', -120),
(42, 1, '2020-01-13', -1000),
(43, 1, '2020-01-13', 1000),
(44, 1, '2020-01-13', 80),
(45, 4, '2020-01-13', 180),
(46, 1, '2020-01-13', 80),
(47, 1, '2020-01-13', 80),
(48, 1, '2020-01-13', 0),
(49, 1, '2020-01-13', 0),
(50, 1, '2020-01-13', 0),
(51, 1, '2020-01-13', 130),
(52, 1, '2020-01-13', 130),
(53, 1, '2020-01-13', -130),
(54, 1, '2020-01-13', 70),
(55, 3, '2020-01-13', 70),
(56, 3, '2020-01-13', 70),
(57, 3, '2020-01-13', 70),
(58, 4, '2020-01-13', 70),
(59, 1, '2020-01-13', 130),
(60, 1, '2020-01-13', 70),
(61, 1, '2020-01-13', -130),
(62, 1, '2020-01-13', 130),
(63, 1, '2020-01-13', 200),
(64, 1, '2020-01-13', -130),
(65, 1, '2020-01-13', -130),
(66, 1, '2020-01-13', 130),
(67, 1, '2020-01-13', -130),
(68, 3, '2020-01-14', -130),
(69, 1, '2020-01-14', 130),
(70, 1, '2020-01-15', 130),
(71, 1, '2020-01-15', -130),
(72, 1, '2020-01-15', 130),
(73, 1, '2020-01-15', 70),
(74, 1, '2020-01-15', -70),
(75, 1, '2020-01-15', -130),
(76, 1, '2020-01-15', -70),
(77, 1, '2020-01-17', -200),
(78, 1, '2020-01-17', -200),
(79, 1, '2020-01-17', -70),
(80, 1, '2020-01-17', -70),
(81, 1, '2020-01-17', -200),
(82, 1, '2020-01-17', -70),
(83, 1, '2020-01-17', -200),
(84, 1, '2020-01-17', 200),
(85, 1, '2020-01-17', 200),
(86, 1, '2020-01-17', 200),
(87, 1, '2020-01-17', 200),
(88, 1, '2020-01-17', 200),
(89, 1, '2020-01-17', 200),
(90, 1, '2020-01-17', 200),
(91, 1, '2020-01-17', 70),
(92, 1, '2020-01-17', 70),
(93, 1, '2020-01-17', 70),
(94, 1, '2020-01-17', 70),
(95, 1, '2020-01-17', 200),
(96, 1, '2020-01-17', 200),
(97, 1, '2020-01-18', -200),
(98, 1, '2020-01-18', -200),
(99, 1, '2020-01-18', -200),
(100, 1, '2020-01-18', -200),
(101, 1, '2020-01-18', -200),
(102, 1, '2020-01-18', -200),
(103, 1, '2020-01-18', -200),
(104, 1, '2020-01-18', -200),
(105, 1, '2020-01-18', -200),
(106, 1, '2020-01-18', -200),
(107, 1, '2020-01-18', -200),
(108, 1, '2020-01-18', -200),
(109, 1, '2020-01-18', -200),
(110, 1, '2020-01-22', 130),
(111, 4, '2020-01-22', 1000),
(112, 11, '2020-01-22', 1000),
(113, 3, '2020-01-24', -70),
(114, 1, '2020-01-24', -70),
(115, 1, '2020-01-24', -200),
(116, 1, '2020-01-24', -70),
(117, 1, '2020-01-24', -70);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `Uslugi`
--

CREATE TABLE `Uslugi` (
  `id` int(10) NOT NULL,
  `nazwa` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `cena` int(10) NOT NULL,
  `Kategoriaid` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Zrzut danych tabeli `Uslugi`
--

INSERT INTO `Uslugi` (`id`, `nazwa`, `cena`, `Kategoriaid`) VALUES
(1, 'Szkolenie praktyczne samochodowe', 70, 2),
(5, 'Szkolenie praktyczne motocyklowe', 200, 1);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `Ustawienia`
--

CREATE TABLE `Ustawienia` (
  `id` int(11) NOT NULL,
  `Nazwa` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `Wartość` int(100) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Zrzut danych tabeli `Ustawienia`
--

INSERT INTO `Ustawienia` (`id`, `Nazwa`, `Wartość`) VALUES
(1, 'limit_rezerwacji_oczekujacych', 30),
(2, 'limit_rezerwacji_dziennych', 2);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `Uzytkownicy`
--

CREATE TABLE `Uzytkownicy` (
  `id` int(10) NOT NULL,
  `imię` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `nazwisko` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `telefon` int(9) NOT NULL,
  `pesel` varchar(12) COLLATE utf8_unicode_ci NOT NULL,
  `pkk` varchar(12) COLLATE utf8_unicode_ci DEFAULT NULL,
  `hasło` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `eportfel` int(10) DEFAULT NULL,
  `email` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `data_utworzenia` date DEFAULT NULL,
  `typ_uzytkownika` int(1) NOT NULL COMMENT '0 - kursant, 1 - biurowy, 2 - ksiegowy, 3 - admin'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Zrzut danych tabeli `Uzytkownicy`
--

INSERT INTO `Uzytkownicy` (`id`, `imię`, `nazwisko`, `telefon`, `pesel`, `pkk`, `hasło`, `eportfel`, `email`, `data_utworzenia`, `typ_uzytkownika`) VALUES
(1, 'Mateusz', 'Śliwka', 555666777, '9807011245', '8874545', 'test123', 7710, 'mateosl@wp.pl', '2019-12-16', 0),
(2, 'Miłosz', 'Stolarczyk', 888965216, '9801012225', '7765512', 'milosz123', 0, 'admin@admin.pl', '2019-12-22', 3),
(3, 'Marek', 'Kowalski', 999888777, '8852369964', '7732632', 'marek322', 10, 'marek@kondrat.pl', '2019-12-11', 0),
(4, 'Alina', 'Nowak', 609555888, '821212122', '2121323', 'alcia1', 1090, 'ala@buziaczek.pl', '2019-12-01', 0),
(5, 'Radosław', 'Kaczyński', 874555666, '550104558', '8511441', 'eshait', 0, 'radek@mail.pl', '2019-12-01', 0),
(8, 'Paulina', 'Pierzyna', 515666777, '9601098545', '12312312', 'pelkabelka123', 0, 'paulisia@bierutow.pl', '2019-12-13', 1),
(9, 'Matesz', 'Śliwka', 555666777, '123123123', '1312312', 'matiksl123', 0, 'mateosll@wp.pl', '2020-01-11', 0),
(11, 'Adam', 'Nowak', 123456789, '123456789', '123456789', '1234', 0, '123@123', '2020-01-12', 0),
(15, 'Anna', 'Banna', 665776876, '9976113456', '123123123', 'Anna123', 0, 'anna@wanna.pl', NULL, 0),
(16, 'Andrzej', 'Lubicz', 55566677, '999991221', '123213', 'maciek123', 0, 'maciejlubicz@o2.pl', NULL, 0),
(17, 'Grazyna', 'Zarko', 57757658, '981812812', '12123123', 'grazyna123', 0, 'grazynazarko@tlen.pl', NULL, 2);

--
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `Instruktorzy`
--
ALTER TABLE `Instruktorzy`
  ADD PRIMARY KEY (`id`);

--
-- Indeksy dla tabeli `Instruktorzy_Kategoria`
--
ALTER TABLE `Instruktorzy_Kategoria`
  ADD PRIMARY KEY (`Instruktorzyid`,`Kategoriaid`),
  ADD KEY `FK7l24nbi11uvv52bed84fgqan1` (`Kategoriaid`);

--
-- Indeksy dla tabeli `Kategoria`
--
ALTER TABLE `Kategoria`
  ADD PRIMARY KEY (`id`),
  ADD KEY `oznaczenie` (`oznaczenie`);

--
-- Indeksy dla tabeli `Rezerwacje`
--
ALTER TABLE `Rezerwacje`
  ADD PRIMARY KEY (`id`),
  ADD KEY `data` (`data`),
  ADD KEY `FKRezerwacje105117` (`usługa`) USING BTREE,
  ADD KEY `FK4161cigs40fjcic946ti98sd0` (`instruktor`),
  ADD KEY `FKdvnxt25hila8yk79dfp2467ww` (`klient`);

--
-- Indeksy dla tabeli `Transakcje`
--
ALTER TABLE `Transakcje`
  ADD PRIMARY KEY (`id`),
  ADD KEY `data` (`data`),
  ADD KEY `FK6nexlh5uofke6xaiwvylh4s50` (`uzytkownik`);

--
-- Indeksy dla tabeli `Uslugi`
--
ALTER TABLE `Uslugi`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKcjq4rxbj6w6j3xuw83fendt2f` (`Kategoriaid`);

--
-- Indeksy dla tabeli `Ustawienia`
--
ALTER TABLE `Ustawienia`
  ADD PRIMARY KEY (`id`);

--
-- Indeksy dla tabeli `Uzytkownicy`
--
ALTER TABLE `Uzytkownicy`
  ADD PRIMARY KEY (`id`),
  ADD KEY `typ_uzytkownika` (`typ_uzytkownika`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT dla tabeli `Instruktorzy`
--
ALTER TABLE `Instruktorzy`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT dla tabeli `Kategoria`
--
ALTER TABLE `Kategoria`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT dla tabeli `Rezerwacje`
--
ALTER TABLE `Rezerwacje`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=89;

--
-- AUTO_INCREMENT dla tabeli `Transakcje`
--
ALTER TABLE `Transakcje`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=118;

--
-- AUTO_INCREMENT dla tabeli `Uslugi`
--
ALTER TABLE `Uslugi`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT dla tabeli `Ustawienia`
--
ALTER TABLE `Ustawienia`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT dla tabeli `Uzytkownicy`
--
ALTER TABLE `Uzytkownicy`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- Ograniczenia dla zrzutów tabel
--

--
-- Ograniczenia dla tabeli `Instruktorzy_Kategoria`
--
ALTER TABLE `Instruktorzy_Kategoria`
  ADD CONSTRAINT `FK7l24nbi11uvv52bed84fgqan1` FOREIGN KEY (`Kategoriaid`) REFERENCES `Kategoria` (`id`),
  ADD CONSTRAINT `FKInstruktor296786` FOREIGN KEY (`Instruktorzyid`) REFERENCES `Instruktorzy` (`id`),
  ADD CONSTRAINT `FKInstruktor432049` FOREIGN KEY (`Kategoriaid`) REFERENCES `Kategoria` (`id`),
  ADD CONSTRAINT `FKmfk2hhtmj9vkx0ad01m4wgyf4` FOREIGN KEY (`Instruktorzyid`) REFERENCES `Instruktorzy` (`id`);

--
-- Ograniczenia dla tabeli `Rezerwacje`
--
ALTER TABLE `Rezerwacje`
  ADD CONSTRAINT `FK4161cigs40fjcic946ti98sd0` FOREIGN KEY (`instruktor`) REFERENCES `Instruktorzy` (`id`),
  ADD CONSTRAINT `FKRezerwacje105117` FOREIGN KEY (`usługa`) REFERENCES `Uslugi` (`id`),
  ADD CONSTRAINT `FKRezerwacje508450` FOREIGN KEY (`instruktor`) REFERENCES `Instruktorzy` (`id`),
  ADD CONSTRAINT `FKRezerwacje615313` FOREIGN KEY (`klient`) REFERENCES `Uzytkownicy` (`id`),
  ADD CONSTRAINT `FKdvnxt25hila8yk79dfp2467ww` FOREIGN KEY (`klient`) REFERENCES `Uzytkownicy` (`id`),
  ADD CONSTRAINT `FKfvcv9n5eib6o0eoyxrgnqq4bv` FOREIGN KEY (`usługa`) REFERENCES `Uslugi` (`id`);

--
-- Ograniczenia dla tabeli `Transakcje`
--
ALTER TABLE `Transakcje`
  ADD CONSTRAINT `FK6nexlh5uofke6xaiwvylh4s50` FOREIGN KEY (`uzytkownik`) REFERENCES `Uzytkownicy` (`id`),
  ADD CONSTRAINT `FKTransakcje550778` FOREIGN KEY (`uzytkownik`) REFERENCES `Uzytkownicy` (`id`);

--
-- Ograniczenia dla tabeli `Uslugi`
--
ALTER TABLE `Uslugi`
  ADD CONSTRAINT `FKUsługi972778` FOREIGN KEY (`Kategoriaid`) REFERENCES `Kategoria` (`id`),
  ADD CONSTRAINT `FKcjq4rxbj6w6j3xuw83fendt2f` FOREIGN KEY (`Kategoriaid`) REFERENCES `Kategoria` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
