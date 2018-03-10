-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 10-03-2018 a las 21:19:28
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
-- Estructura de tabla para la tabla `operarios`
--

CREATE TABLE `operarios` (
  `id` int(10) NOT NULL,
  `id_tarea` bigint(15) NOT NULL,
  `id_usuario` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `operarios`
--

INSERT INTO `operarios` (`id`, `id_tarea`, `id_usuario`) VALUES
(1, 1517813777134, 84234508),
(2, 1517813734020, 84234508),
(3, 1517897223016, 84234508),
(4, 1517985320150, 84234508);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `orden`
--

CREATE TABLE `orden` (
  `id` bigint(15) NOT NULL,
  `idEmpleado` int(11) DEFAULT NULL,
  `fecha` text,
  `prioridad` text,
  `sintoma` text,
  `ubicacion` text,
  `descripcion` text,
  `estado` text,
  `contiene_imagen` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `orden`
--

INSERT INTO `orden` (`id`, `idEmpleado`, `fecha`, `prioridad`, `sintoma`, `ubicacion`, `descripcion`, `estado`, `contiene_imagen`) VALUES
(1517813734020, 84234508, '05-02-2018  06:55', 'Media', '5/2/18', 'Aula', 'Ejercicios practicos', 'Realizado', -1),
(1517813777134, 84234508, '05-02-2018  06:56', 'Baja', '2/2/18', 'Aula', 'Inicio. Explicar conceptos generales', 'Realizado', -1),
(1517897223016, 84234508, '06-02-2018  06:07', 'Media', 'Circuitos', 'Aula', 'Diseño de circuitos', 'Realizado', -1),
(1517985320150, 84234508, '07-02-2018  06:35', 'Media', 'Repaso', 'Aula', 'Repaso circuitos  puntos de luz', 'Realizado', -1),
(1518072149034, 84234508, '08-02-2018  06:42', 'Media', 'Repaso y ejemplo  telerruptor', 'Aula', 'Repasar circuitos alumbrado. Ejemplo  telerruptor', 'Realizado', -1),
(1518159221289, 84234508, '09-02-2018  06:53', 'Media', 'Cuadro de protección  de una v', 'Aula', 'Explicar cuadro de protección de una vivienda y sus elementos.', 'Realizado', -1),
(1518592806364, 84234508, '14-02-2018  07:20', 'Media', 'Fluorescentes ', 'Aula', 'Explicación  tipos de lámparas y funcionamienro de fluorescentes ', 'Realizado', -1),
(1518678543716, 84234508, '15-02-2018  07:09', 'Media', 'Ejercicio de control', 'Aula', 'Ejercicios  de control de conocimiento. Símbolos, circuitos de alumbrado.', 'Realizado', -1),
(1518764865648, 84234508, '16-02-2018  07:07', 'Media', 'Repaso', 'Aula ', 'Repaso fluorescentes y halogenos. Contactores', 'Realizado', -1),
(1519024265995, 84234508, '19-02-2018  07:11', 'Media', 'Riesgos laborales y canalizaci', 'Aula', 'Riesgos laborales y materiales se canalizacipm', 'Realizado', -1),
(1519111079546, 84234508, '20-02-2018  07:17', 'Media', 'Circuitos con contactores', 'Aula\n', 'Circuitos con con factores. Riesgos laborales', 'Realizado', -1),
(1519237349351, 84234508, '21-02-2018  18:22', 'Media', 'Montar paneles', 'Taller', 'Montar paneles  ver dispositivos ', 'Realizado', -1),
(1519279230143, 84234508, '22-02-2018  06:00', 'Media', 'Prácticas punto de luz', 'Taller', 'Realización  práctica de puntos de luz .', 'Realizado', -1),
(1519366756237, 84234508, '23-02-2018  06:19', 'Media', 'Prácticas punto de luz commuta', 'Taller', 'Realización práctica  de punto de luz commutado y de cruzamiento', 'Realizado', -1),
(1519680040710, 84234508, '26-02-2018  21:20', 'Media', 'Finalizar ejercicio practico  ', 'Taller', 'Finalizar práctica puntos de luz. Ejercicio teórico de repaso ', 'Realizado', -1),
(1519714109725, 84234508, '27-02-2018  06:48', 'Media', 'Riesgos laborales', 'Aula', 'Continuar tema 1', 'Realizado', -1),
(1519802392264, 84234508, '28-02-2018  07:19', 'Media', 'P R L', 'Aula', 'Tema 2 P R L', 'Realizado', -1),
(1519974390109, 84234508, '02-03-2018  07:06', 'Media', 'Examen  uf0538', 'Aula', 'Test final mf0538', 'Realizado', -1),
(1520233965452, 84234508, '05-03-2018  07:12', 'Media', 'Riesgos  laborales', 'Aula', 'Continuar  riesgos labores', 'Realizado', -1),
(1520322246657, 84234508, '06-03-2018  07:44', 'Media', 'Riesgos  laborales', 'Aula', 'Riesgos laborales ', 'Realizado', -1),
(1520442003020, 84234508, '07-03-2018  17:00', 'Media', 'Domotica', 'Taller', 'Comienzo domotica', 'Realizado', -1),
(1520495992498, 84234508, '08-03-2018  07:59', 'Media', 'Domotica', 'Taller', 'Domotica. Teoria', 'Realizado', -1),
(1520578320691, 84234508, '09-03-2018  06:52', 'Media', 'Test o el y E prl', 'Aula', 'Test de conocimiento y E final PRL', 'Pendiente', -1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tarea`
--

CREATE TABLE `tarea` (
  `id` bigint(15) NOT NULL,
  `id_orden` bigint(15) NOT NULL,
  `fecha_inicio` varchar(255) DEFAULT NULL,
  `fecha_fin` varchar(255) DEFAULT NULL,
  `descripcion` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `tarea`
--

INSERT INTO `tarea` (`id`, `id_orden`, `fecha_inicio`, `fecha_fin`, `descripcion`) VALUES
(25, 25, 'ayer', 'hoy', 'ewrwrww'),
(1517515084421, 1517515084421, '01-02-2018  19:58:20', '01-02-2018  19:59:58', 'Vsdgdhdh e'),
(1517595716969, 1517595716969, '02-02-2018  18:23:41', '02-02-2018  18:24:09', 'Cambiar '),
(1517813734020, 1517813734020, '05-02-2018  23:14:12', '05-02-2018  23:16:10', 'Ejercicios resistencias serie y paralelo. Mostrar cómo se mide con polimetro. Ejercicio circuito  commutado de cruzamiento.\n'),
(1517813777134, 1517813777134, '05-02-2018  06:56:42', '05-02-2018  06:57:05', 'Explicar conceptos generales'),
(1517897223016, 1517897223016, '06-02-2018  21:37:04', '06-02-2018  21:38:48', 'Ejercicios sobre circuito de punto de luz de cruzamiento. Explicar funcionamiento diferencial'),
(1517985320150, 1517985320150, '07-02-2018  15:33:33', '07-02-2018  15:35:33', 'Realizar esquemas punto luz cruzamiento. Introducción motores '),
(1518072149034, 1518072149034, '08-02-2018  15:14:10', '08-02-2018  15:16:51', 'Ejercicio de control.\nCircuito  mixto. Circuitos puntos de luz, telerruptor.'),
(1518159221289, 1518159221289, '09-02-2018  14:32:21', '09-02-2018  14:32:43', 'Cuadro de protección '),
(1518592806364, 1518592806364, '14-02-2018  14:16:08', '14-02-2018  14:17:30', 'Explicar  tipos de lámparas.  Funcionamiento halogenas con transformador y funcionamiento tubos fluorescentes '),
(1518678543716, 1518678543716, '15-02-2018  14:27:24', '15-02-2018  14:28:23', 'Ejercicio de control. Explicar diferencias entre diferencial y magnetotermico'),
(1518764865648, 1518764865648, '16-02-2018  23:15:48', '16-02-2018  23:16:41', 'Repaso  fluorescentes malignos con factores. Comienzo riesgos laborales.'),
(1519024265995, 1519024265995, '19-02-2018  22:37:37', '19-02-2018  22:38:32', 'Repaso general. Telerruptores, con factores, canalizaciones'),
(1519111079546, 1519111079546, '21-02-2018  05:54:23', '21-02-2018  05:55:15', 'Circuitos con contacto es. Riesgos laborales'),
(1519237349351, 1519237349351, '21-02-2018  18:22:43', '21-02-2018  18:23:23', 'Ver con factores, computadores, montar paneles'),
(1519279230143, 1519279230143, '23-02-2018  06:17:40', '23-02-2018  06:18:01', 'Montaje punto de luz simple'),
(1519366756237, 1519366756237, '26-02-2018  03:27:46', '26-02-2018  03:29:08', 'Cableado punto de luz\nPunto luz vomitado\nPunto luz cruzamiento '),
(1519680040710, 1519680040710, '26-02-2018  21:20:54', '26-02-2018  21:21:53', 'Finalizar ejercicio practico punto de luz.\nRealizar ejercicio escrito de repaso. '),
(1519714109725, 1519714109725, '27-02-2018  23:03:06', '27-02-2018  23:03:24', 'Tema 2 prl'),
(1519802392264, 1519802392264, '28-02-2018  21:27:14', '28-02-2018  21:27:42', 'Se suspende la clase por alerta.'),
(1519974390109, 1519974390109, '05-03-2018  05:55:38', '05-03-2018  05:56:17', 'Realización test de conocimiento y prueba práctica final'),
(1520233965452, 1520233965452, '05-03-2018  14:11:26', '05-03-2018  14:11:59', 'Tema2 . Realización  E1'),
(1520322246657, 1520322246657, '07-03-2018  16:58:11', '07-03-2018  16:59:10', 'Finalizar libro riesgos laborales'),
(1520442003020, 1520442003020, '07-03-2018  17:00:14', '07-03-2018  17:00:35', 'Nociones básicas domotica'),
(1520453014781, 1520453014781, '07-03-2018  20:04:10', '07-03-2018  20:04:42', 'Fhdbzvdv dvdvdvdhdhgqj'),
(1520495992498, 1520495992498, '08-03-2018  23:02:40', '08-03-2018  23:02:53', 'Domotica'),
(1520578320691, 1520578320691, '10-03-2018  18:37:29', '10-03-2018  18:38:25', 'Test P R L. E práctica  final');

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
-- Indices de la tabla `operarios`
--
ALTER TABLE `operarios`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `orden`
--
ALTER TABLE `orden`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `tarea`
--
ALTER TABLE `tarea`
  ADD PRIMARY KEY (`id`);

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
