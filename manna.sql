-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 13-12-2017 a las 18:29:06
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
  `id_empleado` int(11) DEFAULT NULL,
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

INSERT INTO `orden` (`_id`, `id_empleado`, `Fecha`, `Prioridad`, `Sintoma`, `Ubicacion`, `Descripcion`, `Estado`) VALUES
(1512381063721, 27781115, '04-12-2017  09:51', 'Baja', 'Parado', 'Jardín', 'Riego automático no funciona en la zon B', 'Realizado'),
(1512399947226, 34705834, '04-12-2017  15:05', 'Muy alta', 'Parado', 'Oficinas centrales', 'No hay luz', 'Realizado'),
(1512426918277, 84234508, '04-12-2017  22:35', 'Muy alta', 'Parado', 'Entrda garaje', 'Puerta de acceso a garaje no abre', 'Realizado'),
(1512469256741, 36520913, '05-12-2017  10:20', 'Alta', 'Rotura', 'Sótano', 'Dilatador pierde agua', 'Realizado'),
(1512469400314, 73541207, '05-12-2017  10:23', 'Alta', 'Gotera', 'Archivo 4', 'Gotera', 'Pendiente'),
(1512590901229, 29244062, '06-12-2017  20:08', 'Baja', 'Rotura', 'Sótano 2', 'Colector tiene un manómetro roto', 'Proceso'),
(1512850365638, 67490810, '09-12-2017  20:12', 'Alta', 'Fuga de agua', 'Cocina ', 'Tuberia agua caliente fuga agua', 'Realizado'),
(1512851040124, 79315147, '09-12-2017  20:24', 'Alta', 'Rotura', 'Cocina', 'Calentador pierde agua', 'Proceso'),
(1512856062259, 11815701, '09-12-2017  21:47', 'Media', 'Rotura', 'Pasillo  ', 'Puerta rota', 'Proceso'),
(1512952446945, 84234508, '11-12-2017  00:34', 'Muy alta', ' Parado', 'Sala máquinas tubo neumático ', 'Tubo salido de caja de distribución ', 'Realizado'),
(1513010254147, 84234508, '11-12-2017  16:37', 'Alta', 'Rotura', 'Sótano 1', 'Tubo ropa sucia no funciona', 'Proceso'),
(1513024849483, 86929680, '11-12-2017  20:40', 'Alta', 'Rotura', 'Aula 34', 'Cristal roto', 'Realizado');

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
(2, 9999, 'ManNa', 'No'),
(3104986, 1111, 'Tiburcio', 'Si'),
(10497067, 1305, 'Adoracion', 'No'),
(10653825, 2709, 'Alejandro', 'No'),
(11815701, 1610, 'Carlos', 'No'),
(13158952, 3010, 'Julio', 'No'),
(24975972, 2606, 'Marta', 'No'),
(27781115, 103, 'Julia', 'No'),
(29244062, 1202, 'Alfonso', 'No'),
(34705834, 2805, 'Raquel', 'No'),
(36520913, 2505, 'Nuria', 'No'),
(41258574, 3112, 'Victor', 'No'),
(41662754, 1704, 'Ana Maria', 'No'),
(44555018, 2905, 'Luis', 'No'),
(58146322, 1211, 'Monica', 'No'),
(62080733, 811, 'Miguel', 'No'),
(67490810, 1207, 'Beatriz', 'No'),
(71430275, 1908, 'Yasmina', 'No'),
(73541207, 3101, 'Ana', 'Si'),
(76789255, 1510, 'Pablo', 'No'),
(79315147, 2812, 'Alejandra', 'No'),
(83284242, 2702, 'Jesus', 'No'),
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
