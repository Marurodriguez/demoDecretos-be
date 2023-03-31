-- MySQL dump 10.13  Distrib 5.7.37, for Win64 (x86_64)
--
-- Host: localhost    Database: vdig
-- ------------------------------------------------------
-- Server version	5.7.37-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `archivo`
--

DROP TABLE IF EXISTS `archivo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `archivo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `status` int(11) DEFAULT '0',
  `id_archivo_tipo` bigint(20) DEFAULT NULL COMMENT 'Enlace al tipo de Archivo (IMAGEN, PDF, OCR)',
  `id_archivo_data` bigint(20) DEFAULT NULL COMMENT 'Enlace al contenido del archivo. En la tabla archivo_data esta almacenado el archivo. Puede estar encriptado.',
  `id_documento` bigint(20) DEFAULT NULL COMMENT 'Enlace al Documento. El documento es la tabla maestra donde se contiene la informacion del documento. Por cada Documento hay varios archivos relacionados.',
  `id_enlace_imagen` bigint(20) DEFAULT NULL COMMENT 'Si es una Imagen Grande-> Enlace a la tabla archivo donde esta la preview. Si la imagen es preview enlaza a la tabla archivo donde la imagen es grande. (SI NO ES IMAGEN ES NULL)',
  `uuid` char(36) CHARACTER SET utf8mb4 NOT NULL,
  `extension` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT 'extension del archivo (.jpg,.pdf)',
  `hash` longtext CHARACTER SET utf8mb4 COMMENT 'Guarda el hash del archivo principal, para saber si no fue modificado.',
  `_version` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'En los casos que haya un cambio en el registro, se actualiza este campo.',
  `orden` int(11) NOT NULL DEFAULT '0' COMMENT 'Establece un orden, es importante cuando el tipo de archivo es imagen, asi se puede ordenar por pagina.',
  `uuid_alternativo` char(36) COLLATE utf8mb4_spanish_ci DEFAULT NULL COMMENT 'Es el uuid alternativo: Si es una imagen preview, el uuid_alternativo apunta al enlace de la imagen grande y si es grande apunta al uud de la imagen preview.',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_1gt8orv24hwuvc0uenj19n99r` (`uuid`),
  KEY `FK5uccfup40uil9b0ia7b6gmuwr` (`id_documento`),
  KEY `FKma297dbh4ky84j04dvxph0cqw` (`id_archivo_tipo`),
  KEY `fk_archivo_data` (`id_archivo_data`),
  KEY `fk_archivo_archivo` (`id_enlace_imagen`),
  CONSTRAINT `fk_archivo_archivo` FOREIGN KEY (`id_enlace_imagen`) REFERENCES `archivo` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_archivo_data` FOREIGN KEY (`id_archivo_data`) REFERENCES `archivo_data` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_archivo_data_tipo` FOREIGN KEY (`id_archivo_tipo`) REFERENCES `archivo_tipo` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=17869 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `archivo_data`
--

DROP TABLE IF EXISTS `archivo_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `archivo_data` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `data` longtext CHARACTER SET latin1 COMMENT 'Contenido del archivo, puede estar encriptado.',
  `_version` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` int(11) NOT NULL DEFAULT '0',
  `uuid` char(36) CHARACTER SET latin1 NOT NULL,
  `date_created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `encriptado` int(11) NOT NULL DEFAULT '0' COMMENT 'Indica si el archivo esta encriptado o no.',
  `filesize` bigint(20) NOT NULL COMMENT 'Guarda el tamanio del archivo principal en bytes.',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17869 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `archivo_tipo`
--

DROP TABLE IF EXISTS `archivo_tipo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `archivo_tipo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `status` int(11) DEFAULT '0',
  `uuid` char(36) CHARACTER SET utf8mb4 NOT NULL,
  `codigo` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
  `nombre` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
  `date_created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_dmn3bwpspixw2onkwynmo0w5t` (`uuid`),
  UNIQUE KEY `idx_archivo_tipo_codigo` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci COMMENT='Indica de que tipo es el archivo que esta relacionado al documento';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `campo`
--

DROP TABLE IF EXISTS `campo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `campo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `status` int(11) DEFAULT '0',
  `uuid` char(36) CHARACTER SET utf8mb4 NOT NULL,
  `buscable` bit(1) NOT NULL DEFAULT b'1',
  `codigo` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
  `id_campo_tipo` bigint(20) DEFAULT NULL,
  `id_categoria` bigint(20) DEFAULT NULL,
  `nombre` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
  `visible_tabla` bit(1) NOT NULL DEFAULT b'1',
  `date_created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_epbgenx7ibggdfpe3kwnsxtma` (`uuid`),
  KEY `FKgb016crydp90dr6flfctv0p8k` (`id_categoria`),
  CONSTRAINT `FKgb016crydp90dr6flfctv0p8k` FOREIGN KEY (`id_categoria`) REFERENCES `documento_tipo` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `campo_tipo`
--

DROP TABLE IF EXISTS `campo_tipo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `campo_tipo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `status` int(11) DEFAULT '0',
  `uuid` char(36) CHARACTER SET utf8mb4 NOT NULL,
  `codigo` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
  `nombre` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
  `date_created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_3uqnc294vwtspjfdvtr39en7l` (`uuid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `centro_digitalizacion`
--

DROP TABLE IF EXISTS `centro_digitalizacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `centro_digitalizacion` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'El ID del centro es único e indica el identificador único de centro.',
  `status` int(11) DEFAULT '0',
  `uuid` char(36) CHARACTER SET utf8mb4 NOT NULL,
  `codigo` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
  `nombre` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
  `date_created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_centro_digitalizacion_uuid` (`uuid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `dependencia`
--

DROP TABLE IF EXISTS `dependencia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dependencia` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `status` int(11) NOT NULL DEFAULT '0',
  `nombre` varchar(2500) CHARACTER SET latin1 NOT NULL,
  `codigo` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `date_created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `uuid` char(36) CHARACTER SET utf8mb4 NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_dependencia_nombre` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `digitalizador`
--

DROP TABLE IF EXISTS `digitalizador`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `digitalizador` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `_version` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` int(11) NOT NULL DEFAULT '0',
  `uuid` char(36) CHARACTER SET latin1 NOT NULL,
  `nombre` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `username` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `date_created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci COMMENT='Guarda el usuario que realizo la digitalizacion';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `documento`
--

DROP TABLE IF EXISTS `documento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `documento` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_documento_tipo` bigint(20) NOT NULL COMMENT 'Tipo de Tramite (INFO EXPEDIENTE)',
  `id_centro_digitalizacion` bigint(20) NOT NULL COMMENT 'Indica el Centro de Digitalizacion de donde digitalizo el documento',
  `id_dependencia_iniciadora` bigint(20) NOT NULL COMMENT 'Dependecia Iniciadora del expediente (INFO EXPEDIENTE)',
  `id_digitalizador` bigint(20) DEFAULT NULL COMMENT 'Indica el empleado que hizo la digitalizacion, corresponde a las tablas de usuario del sistema digitalizador. Junto con la pc_id y la fecha_digitalizacion sirve como auditoria.',
  `status` int(11) NOT NULL DEFAULT '0',
  `uuid` char(36) CHARACTER SET utf8mb4 NOT NULL,
  `anio` int(11) DEFAULT NULL COMMENT 'Anio del expediente (INFO EXPEDIENTE)',
  `caratula` varchar(500) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT 'Caratula del expediente (INFO EXPEDIENTE)',
  `numero_expediente` int(11) DEFAULT NULL COMMENT 'Numero del Expediente (INFO EXPEDIENTE)',
  `prefijo` int(11) DEFAULT NULL COMMENT 'Prefijo del expediente (INFO EXPEDIENTE) Para todos los expedientes ingresados desde malvinas es 4132',
  `matricula` int(11) DEFAULT NULL COMMENT 'Matricula del expediente (INFO EXPEDIENTE)',
  `alcance` int(11) NOT NULL DEFAULT '0' COMMENT 'Alcance del expediente (INFO EXPEDIENTE)',
  `fecha_elaborado` date DEFAULT NULL COMMENT 'Fecha de elaboracion del expediente (INFO EXPEDIENTE)',
  `cant_imagenes` int(11) NOT NULL DEFAULT '0' COMMENT 'Cantidad de Imagenes que tiene el documento (SIN CONTAR PDF O OCR)',
  `date_created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Fecha en que se creo el registro (que se hizo la migracion)',
  `pc_id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'Id de la maquina que digitalizo',
  `fecha_digitalizacion` datetime DEFAULT NULL COMMENT 'Fecha en que se hizo la digitalizacion',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_sltwnn0fb4nj5d7sujxrpqfnm` (`uuid`),
  KEY `FKh1n1qxrncf4mely18s60ewmuh` (`id_documento_tipo`),
  KEY `fk_documento_dependencia_iniciadora` (`id_dependencia_iniciadora`),
  KEY `fk_documento_centro` (`id_centro_digitalizacion`),
  KEY `fk_documento_digitalizador` (`id_digitalizador`),
  KEY `idx_documento_expediente` (`prefijo`,`numero_expediente`,`anio`,`alcance`),
  KEY `idx_documento_caratula` (`caratula`),
  CONSTRAINT `fk_documento_centro` FOREIGN KEY (`id_centro_digitalizacion`) REFERENCES `centro_digitalizacion` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_documento_dependencia_iniciadora` FOREIGN KEY (`id_dependencia_iniciadora`) REFERENCES `dependencia` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_documento_digitalizador` FOREIGN KEY (`id_digitalizador`) REFERENCES `digitalizador` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_documento_tipo_tramite` FOREIGN KEY (`id_documento_tipo`) REFERENCES `documento_tipo` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=175 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `documento_tipo`
--

DROP TABLE IF EXISTS `documento_tipo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `documento_tipo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `status` int(11) DEFAULT '0',
  `uuid` char(36) CHARACTER SET utf8mb4 NOT NULL,
  `codigo` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
  `nombre` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
  `date_created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ds053gu73hqsft72mqs8ul6ko` (`uuid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `informacion`
--

DROP TABLE IF EXISTS `informacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `informacion` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `status` int(11) DEFAULT '0',
  `uuid` char(36) CHARACTER SET utf8mb4 NOT NULL,
  `id_archivo` bigint(20) DEFAULT NULL,
  `id_campo` bigint(20) DEFAULT NULL,
  `id_documento` bigint(20) DEFAULT NULL,
  `valor` longtext CHARACTER SET utf8mb4,
  `date_created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_9x7wpec1dvjhqcf2xoefhkw6r` (`uuid`),
  KEY `FKp8ufms2rrk8sq1a7xqu97w8js` (`id_documento`),
  KEY `FKq0ee7hteoylr7ggejh4crqp6t` (`id_campo`),
  CONSTRAINT `FKp8ufms2rrk8sq1a7xqu97w8js` FOREIGN KEY (`id_documento`) REFERENCES `documento` (`id`),
  CONSTRAINT `FKq0ee7hteoylr7ggejh4crqp6t` FOREIGN KEY (`id_campo`) REFERENCES `campo` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuario` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uuid` char(36) CHARACTER SET latin1 NOT NULL,
  `status` int(11) NOT NULL DEFAULT '0',
  `codigo` varchar(50) CHARACTER SET latin1 DEFAULT NULL,
  `nombre` varchar(255) CHARACTER SET latin1 NOT NULL,
  `username` varchar(255) CHARACTER SET latin1 NOT NULL,
  `password` varchar(255) CHARACTER SET latin1 NOT NULL,
  `email` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `telefono` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `admin` int(11) NOT NULL DEFAULT '0',
  `codigo_interno` varchar(250) CHARACTER SET latin1 DEFAULT NULL,
  `documento` varchar(250) CHARACTER SET latin1 DEFAULT NULL,
  `genero` varchar(250) CHARACTER SET latin1 DEFAULT NULL,
  `date_created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Fecha en que se creo el registro (que se hizo la migracion)',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_usuario_uuid` (`uuid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping routines for database 'vdig'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-03-10 17:07:47
