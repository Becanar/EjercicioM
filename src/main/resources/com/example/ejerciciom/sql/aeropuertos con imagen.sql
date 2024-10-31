DROP SCHEMA IF EXISTS `aeropuertos` ;

CREATE SCHEMA IF NOT EXISTS `aeropuertos` DEFAULT CHARACTER SET latin1 COLLATE latin1_spanish_ci;

USE `aeropouetos` ;

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";

SET AUTOCOMMIT = 0;

START TRANSACTION;

--

-- Tabla `aeropuertos`

--

DROP TABLE IF EXISTS `aeropuertos`;

CREATE TABLE
    IF NOT EXISTS `aeropuertos` (
        `id` int(11) NOT NULL AUTO_INCREMENT,
        `nombre` varchar(50) NOT NULL,
        `anio_inauguracion` int(11) NOT NULL,
        `capacidad` int(11) NOT NULL,
        `id_direccion` int(11) NOT NULL,
        `imagen` blob NULL,
        PRIMARY KEY (`id`),
        KEY `aeropuertos_ibfk_1` (`id_direccion`)
    ) ENGINE = InnoDB AUTO_INCREMENT = 6 DEFAULT CHARSET = latin1;

INSERT INTO
    `aeropuertos` (
        `id`,
        `nombre`,
        `anio_inauguracion`,
        `capacidad`,
        `id_direccion`
    )
VALUES (1, 'Loiu', 1950, 59058047, 1), (
        2,
        'Adolfo Suárez Madrid-Barajas',
        1920,
        6173403,
        2
    ), (4, 'El Prat', 1925, 52686314, 3), (
        3,
        'Aeropuerto de Andorra',
        1980,
        300000,
        4
    ), (5, 'Foronda', 1977, 174022, 5), (
        6,
        'Base Aérea de Torrejón',
        1990,
        500,
        6
    );

--

-- Tabla `aeropuertos_privados`

--

DROP TABLE IF EXISTS `aeropuertos_privados`;

CREATE TABLE
    IF NOT EXISTS `aeropuertos_privados` (
        `id_aeropuerto` int(11) NOT NULL,
        `numero_socios` int(11) NOT NULL,
        PRIMARY KEY (`id_aeropuerto`)
    ) ENGINE = InnoDB DEFAULT CHARSET = latin1;

INSERT INTO
    `aeropuertos_privados` (
        `id_aeropuerto`,
        `numero_socios`
    )
VALUES (3, 15), (6, 2);

--

-- Tabla `aeropuertos_publicos`

--

DROP TABLE IF EXISTS `aeropuertos_publicos`;

CREATE TABLE
    IF NOT EXISTS `aeropuertos_publicos` (
        `id_aeropuerto` int(11) NOT NULL,
        `financiacion` decimal(10, 2) NOT NULL,
        `num_trabajadores` int(11) NOT NULL,
        PRIMARY KEY (`id_aeropuerto`)
    ) ENGINE = InnoDB DEFAULT CHARSET = latin1;

INSERT INTO
    `aeropuertos_publicos` (
        `id_aeropuerto`,
        `financiacion`,
        `num_trabajadores`
    )
VALUES (1, '100000000.50', 200), (2, '2000000000.00', 2500), (4, '1500000000.50', 2400), (5, '50000000.00', 100);

--

-- Tabla `aviones`

--

DROP TABLE IF EXISTS `aviones`;

CREATE TABLE
    IF NOT EXISTS `aviones` (
        `id` int(11) NOT NULL AUTO_INCREMENT,
        `modelo` varchar(60) NOT NULL,
        `numero_asientos` int(11) NOT NULL,
        `velocidad_maxima` int(11) NOT NULL,
        `activado` int(1) NOT NULL,
        `id_aeropuerto` int(11) NOT NULL,
        PRIMARY KEY (`id`),
        KEY `id_aeropuerto` (`id_aeropuerto`)
    ) ENGINE = InnoDB AUTO_INCREMENT = 5 DEFAULT CHARSET = latin1;

INSERT INTO
    `aviones` (
        `id`,
        `modelo`,
        `numero_asientos`,
        `velocidad_maxima`,
        `activado`,
        `id_aeropuerto`
    )
VALUES (1, 'Boeing1', 300, 500, 1, 2), (2, 'Falcon1', 250, 400, 1, 2), (3, 'Air force1', 200, 300, 1, 2), (4, 'Airbus1', 60, 450, 0, 2), (5, 'Boeing2', 300, 500, 1, 4), (6, 'Falcon2', 250, 400, 1, 4), (7, 'Air force2', 200, 300, 1, 4), (8, 'Airbus2', 60, 450, 0, 4), (9, 'Boeing3', 300, 500, 1, 3), (10, 'Falcon3', 250, 400, 1, 3), (11, 'Air force4', 200, 300, 1, 1), (12, 'Airbus4', 60, 450, 0, 1), (13, 'Boeing5', 300, 500, 1, 5), (14, 'Falcon5', 250, 400, 1, 5), (15, 'Air force6', 200, 300, 1, 6);

--

-- Tabla `direcciones`

--

DROP TABLE IF EXISTS `direcciones`;

CREATE TABLE
    IF NOT EXISTS `direcciones` (
        `id` int(11) NOT NULL AUTO_INCREMENT,
        `pais` varchar(50) NOT NULL,
        `ciudad` varchar(50) NOT NULL,
        `calle` varchar(50) NOT NULL,
        `numero` int(11) NOT NULL,
        PRIMARY KEY (`id`)
    ) ENGINE = InnoDB AUTO_INCREMENT = 10 DEFAULT CHARSET = latin1;

INSERT INTO
    `direcciones` (
        `id`,
        `pais`,
        `ciudad`,
        `calle`,
        `numero`
    )
VALUES (1, 'España', 'Bilbao', 'Loiu', 1), (
        2,
        'España',
        'Madrid',
        'Avenida Libertad',
        1
    ), (
        3,
        'España',
        'El Prat de Llobregat',
        'Calle maravillosa',
        2
    ), (
        4,
        'Andorra',
        'Andorra',
        'Calle aeropuerto',
        3
    ), (
        5,
        'España',
        'Vitoria',
        'Avenida Foronda',
        5
    ), (
        6,
        'España',
        'Madrid',
        'Prueba',
        5
    );

--

-- Tabla `usuarios`

--

DROP TABLE IF EXISTS `usuarios`;

CREATE TABLE
    IF NOT EXISTS `usuarios` (
        `usuario` varchar(50) NOT NULL,
        `password` varchar(50) NOT NULL,
        PRIMARY KEY (`usuario`)
    ) ENGINE = InnoDB DEFAULT CHARSET = latin1;

INSERT INTO
    `usuarios` (`usuario`, `password`)
VALUES ('admin', 'admin');

--

-- Foreign Keys

--

ALTER TABLE `aeropuertos`
ADD
    CONSTRAINT `aeropuertos_direcciones` FOREIGN KEY (`id_direccion`) REFERENCES `direcciones` (`id`) ON DELETE CASCADE;

ALTER TABLE
    `aeropuertos_privados`
ADD
    CONSTRAINT `aeropuertos_privados_aeropuertos` FOREIGN KEY (`id_aeropuerto`) REFERENCES `aeropuertos` (`id`) ON DELETE CASCADE;

ALTER TABLE
    `aeropuertos_publicos`
ADD
    CONSTRAINT `aeropuertos_publicos_aeropuertos` FOREIGN KEY (`id_aeropuerto`) REFERENCES `aeropuertos` (`id`) ON DELETE CASCADE;

ALTER TABLE `aviones`
ADD
    CONSTRAINT `aviones_aeropuerto` FOREIGN KEY (`id_aeropuerto`) REFERENCES `aeropuertos` (`id`);

COMMIT;