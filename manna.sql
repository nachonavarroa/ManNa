-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 03-12-2017 a las 12:26:01
-- Versión del servidor: 10.1.28-MariaDB
-- Versión de PHP: 7.1.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `manna`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `orden`
--

CREATE TABLE `orden` (
  `_id` bigint(15) NOT NULL,
  `Codigo_empleado` int(11) DEFAULT NULL,
  `Fecha` text,
  `Prioridad` text,
  `Sintoma` text,
  `Ubicacion` text,
  `Descripcion` text,
  `Estado` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `orden`
--

INSERT INTO `orden` (`_id`, `Codigo_empleado`, `Fecha`, `Prioridad`, `Sintoma`, `Ubicacion`, `Descripcion`, `Estado`) VALUES
(1512300322892, 1112, '03-12-2017  11:25', 'Muy alta', 'Parado', 'Localhost', 'Prueba 1', 'Pendiente');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `_id` int(11) NOT NULL,
  `Codigo_Usuario` int(11) DEFAULT NULL,
  `Nombre_Usuario` text,
  `Administrador` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`_id`, `Codigo_Usuario`, `Nombre_Usuario`, `Administrador`) VALUES
(3104986, 1111, 'Tiburcio', 'Si'),
(10497067, 1305, 'Adoracion', 'No'),
(10653825, 2709, 'Alejandro', 'No'),
(11815701, 1610, 'Carlos', 'No'),
(24975972, 2606, 'Marta', 'No'),
(27781115, 103, 'Julia', 'No'),
(29244062, 1202, 'Alfonso', 'No'),
(34705834, 2805, 'Raquel', 'No'),
(41258574, 3112, 'Victor', 'No'),
(41662754, 1704, 'Ana Maria', 'No'),
(44555018, 2905, 'Luis', 'No'),
(58146322, 1211, 'Monica', 'No'),
(62080733, 811, 'Miguel', 'No'),
(67490810, 1207, 'Beatriz', 'No'),
(71430275, 1908, 'Yasmina', 'No'),
(73541207, 3101, 'Ana', 'Si'),
(79315147, 2812, 'Alejandra', 'No'),
(84234508, 1112, 'Nacho', 'Si'),
(85136936, 901, 'Ignacio', 'No'),
(86929680, 1905, 'Eli', 'No');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `orden`
--
ALTER TABLE `orden`
  ADD PRIMARY KEY (`_id`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`_id`),
  ADD UNIQUE KEY `Codigo_Usuario` (`Codigo_Usuario`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
