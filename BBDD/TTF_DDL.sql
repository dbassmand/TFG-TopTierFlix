-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: toptierflix
-- ------------------------------------------------------
-- Server version	8.0.37

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `comentario`
--

DROP TABLE IF EXISTS `comentario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comentario` (
  `id_comentario` int NOT NULL AUTO_INCREMENT,
  `comentario` varchar(2000) NOT NULL,
  `fecha_creacion` datetime(6) DEFAULT NULL,
  `pelicula_id` int NOT NULL,
  `usuario_email` varchar(255) NOT NULL,
  `serie_id` int DEFAULT NULL,
  PRIMARY KEY (`id_comentario`),
  KEY `FKhrf4rufkti4p7wm8tcfv4wd1b` (`pelicula_id`),
  KEY `FKeh7emoj8dx8srhftbjvnlw0lh` (`usuario_email`),
  KEY `FKihpxkhrvatoo2a11umnwvhmed` (`serie_id`),
  CONSTRAINT `FKeh7emoj8dx8srhftbjvnlw0lh` FOREIGN KEY (`usuario_email`) REFERENCES `usuarios` (`email`),
  CONSTRAINT `FKhrf4rufkti4p7wm8tcfv4wd1b` FOREIGN KEY (`pelicula_id`) REFERENCES `pelicula` (`id_pelicula`),
  CONSTRAINT `FKihpxkhrvatoo2a11umnwvhmed` FOREIGN KEY (`serie_id`) REFERENCES `serie` (`id_serie`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `comentario_musica`
--

DROP TABLE IF EXISTS `comentario_musica`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comentario_musica` (
  `id_comentario_musica` int NOT NULL AUTO_INCREMENT,
  `comentario` varchar(2000) NOT NULL,
  `fecha_creacion` datetime(6) DEFAULT NULL,
  `musica_id` int NOT NULL,
  `usuario_email` varchar(255) NOT NULL,
  PRIMARY KEY (`id_comentario_musica`),
  KEY `FK2vy6u7cyli4nx0lp953lr1u5r` (`musica_id`),
  KEY `FKfu93yg56gtcef4q4yoe00yusa` (`usuario_email`),
  CONSTRAINT `FK2vy6u7cyli4nx0lp953lr1u5r` FOREIGN KEY (`musica_id`) REFERENCES `musica` (`id_musica`),
  CONSTRAINT `FKfu93yg56gtcef4q4yoe00yusa` FOREIGN KEY (`usuario_email`) REFERENCES `usuarios` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `comentario_serie`
--

DROP TABLE IF EXISTS `comentario_serie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comentario_serie` (
  `id_comentario_serie` int NOT NULL AUTO_INCREMENT,
  `comentario` varchar(2000) NOT NULL,
  `fecha_creacion` datetime(6) DEFAULT NULL,
  `serie_id` int NOT NULL,
  `usuario_email` varchar(255) NOT NULL,
  PRIMARY KEY (`id_comentario_serie`),
  KEY `FKhpnp4fimxstw50iqhkcmnqcoi` (`serie_id`),
  KEY `FKbnkc53bf5wxiybjf8hsi2sx81` (`usuario_email`),
  CONSTRAINT `FKbnkc53bf5wxiybjf8hsi2sx81` FOREIGN KEY (`usuario_email`) REFERENCES `usuarios` (`email`),
  CONSTRAINT `FKhpnp4fimxstw50iqhkcmnqcoi` FOREIGN KEY (`serie_id`) REFERENCES `serie` (`id_serie`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `comentario_videojuego`
--

DROP TABLE IF EXISTS `comentario_videojuego`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comentario_videojuego` (
  `id_comentario_videojuego` int NOT NULL AUTO_INCREMENT,
  `comentario` varchar(2000) NOT NULL,
  `fecha_creacion` datetime(6) NOT NULL,
  `usuario_email` varchar(255) NOT NULL,
  `videojuego_id` int NOT NULL,
  PRIMARY KEY (`id_comentario_videojuego`),
  KEY `FKnapgu1cs5abjwb1iu5um4x23m` (`usuario_email`),
  KEY `FKtpteyapnojb85sqqub6un8mhb` (`videojuego_id`),
  CONSTRAINT `FKnapgu1cs5abjwb1iu5um4x23m` FOREIGN KEY (`usuario_email`) REFERENCES `usuarios` (`email`),
  CONSTRAINT `FKtpteyapnojb85sqqub6un8mhb` FOREIGN KEY (`videojuego_id`) REFERENCES `videojuego` (`id_videojuego`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `genero`
--

DROP TABLE IF EXISTS `genero`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `genero` (
  `id_genero` int NOT NULL,
  `titulo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_genero`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `genero_musica`
--

DROP TABLE IF EXISTS `genero_musica`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `genero_musica` (
  `id_genero_musica` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) NOT NULL,
  PRIMARY KEY (`id_genero_musica`),
  UNIQUE KEY `UKhn3kck3eybth439sipsb6hhjx` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `genero_pelicula`
--

DROP TABLE IF EXISTS `genero_pelicula`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `genero_pelicula` (
  `id_pelicula` int NOT NULL,
  `id_genero` int NOT NULL,
  KEY `FKnpb8vdeybu4p41dibnbawra2q` (`id_genero`),
  KEY `FK7rxwafj1id7f40i0mq446ivw6` (`id_pelicula`),
  CONSTRAINT `FK7rxwafj1id7f40i0mq446ivw6` FOREIGN KEY (`id_pelicula`) REFERENCES `pelicula` (`id_pelicula`),
  CONSTRAINT `FKnpb8vdeybu4p41dibnbawra2q` FOREIGN KEY (`id_genero`) REFERENCES `genero` (`id_genero`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `genero_serie`
--

DROP TABLE IF EXISTS `genero_serie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `genero_serie` (
  `id_serie` int NOT NULL,
  `id_genero` int NOT NULL,
  KEY `FKednvyq8eefyero75r39n8ynxp` (`id_genero`),
  KEY `FKlpjorg7k24ngubl6di11q36ld` (`id_serie`),
  CONSTRAINT `FKednvyq8eefyero75r39n8ynxp` FOREIGN KEY (`id_genero`) REFERENCES `genero` (`id_genero`),
  CONSTRAINT `FKlpjorg7k24ngubl6di11q36ld` FOREIGN KEY (`id_serie`) REFERENCES `serie` (`id_serie`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `genero_videojuego`
--

DROP TABLE IF EXISTS `genero_videojuego`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `genero_videojuego` (
  `id_genero_videojuego` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) NOT NULL,
  PRIMARY KEY (`id_genero_videojuego`),
  UNIQUE KEY `UK93hbif6ig2gst1d73h7mn5qoi` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `musica`
--

DROP TABLE IF EXISTS `musica`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `musica` (
  `id_musica` int NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(2000) NOT NULL,
  `fecha_estreno` date NOT NULL,
  `ruta_portada` varchar(500) DEFAULT NULL,
  `titulo` varchar(255) NOT NULL,
  `youtube_trailer_id` varchar(255) NOT NULL,
  PRIMARY KEY (`id_musica`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `musica_genero_musica`
--

DROP TABLE IF EXISTS `musica_genero_musica`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `musica_genero_musica` (
  `musica_id` int NOT NULL,
  `genero_musica_id` int NOT NULL,
  KEY `FK2300gy6rytarg7iuepnsa7j3u` (`genero_musica_id`),
  KEY `FKnvc4jcl154j2cj3icd8ijgs79` (`musica_id`),
  CONSTRAINT `FK2300gy6rytarg7iuepnsa7j3u` FOREIGN KEY (`genero_musica_id`) REFERENCES `genero_musica` (`id_genero_musica`),
  CONSTRAINT `FKnvc4jcl154j2cj3icd8ijgs79` FOREIGN KEY (`musica_id`) REFERENCES `musica` (`id_musica`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pelicula`
--

DROP TABLE IF EXISTS `pelicula`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pelicula` (
  `id_pelicula` int NOT NULL AUTO_INCREMENT,
  `fecha_estreno` date NOT NULL,
  `ruta_portada` varchar(255) DEFAULT NULL,
  `sinopsis` varchar(2000) NOT NULL,
  `titulo` varchar(255) NOT NULL,
  `youtube_trailer_id` varchar(255) NOT NULL,
  PRIMARY KEY (`id_pelicula`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rol`
--

DROP TABLE IF EXISTS `rol`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rol` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK43kr6s7bts1wqfv43f7jd87kp` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `serie`
--

DROP TABLE IF EXISTS `serie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `serie` (
  `id_serie` int NOT NULL AUTO_INCREMENT,
  `fecha_estreno` date NOT NULL,
  `ruta_portada` varchar(255) DEFAULT NULL,
  `sinopsis` varchar(2000) NOT NULL,
  `titulo` varchar(255) NOT NULL,
  `youtube_trailer_id` varchar(255) NOT NULL,
  PRIMARY KEY (`id_serie`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `usuario_musica_favorita`
--

DROP TABLE IF EXISTS `usuario_musica_favorita`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario_musica_favorita` (
  `usuario_id` int NOT NULL,
  `musica_id` int NOT NULL,
  KEY `FK173ayibyd02o0ff8rlx6tl2k4` (`musica_id`),
  KEY `FKkku8uxh4gcrkcofnvv1ifdun2` (`usuario_id`),
  CONSTRAINT `FK173ayibyd02o0ff8rlx6tl2k4` FOREIGN KEY (`musica_id`) REFERENCES `musica` (`id_musica`),
  CONSTRAINT `FKkku8uxh4gcrkcofnvv1ifdun2` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `usuario_pelicula_favorita`
--

DROP TABLE IF EXISTS `usuario_pelicula_favorita`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario_pelicula_favorita` (
  `usuario_id` int NOT NULL,
  `pelicula_id` int NOT NULL,
  KEY `FKiwu57jw3kkw2tckdvfsmojkra` (`pelicula_id`),
  KEY `FK2hou8b02s9ym0y2petddp1e2` (`usuario_id`),
  CONSTRAINT `FK2hou8b02s9ym0y2petddp1e2` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`),
  CONSTRAINT `FKiwu57jw3kkw2tckdvfsmojkra` FOREIGN KEY (`pelicula_id`) REFERENCES `pelicula` (`id_pelicula`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `usuario_serie_favorita`
--

DROP TABLE IF EXISTS `usuario_serie_favorita`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario_serie_favorita` (
  `usuario_id` int NOT NULL,
  `serie_id` int NOT NULL,
  KEY `FK5tecc8qm3k3f66vw0qtglh07` (`serie_id`),
  KEY `FK8orvvuv51efxcqha3x0amcfq9` (`usuario_id`),
  CONSTRAINT `FK5tecc8qm3k3f66vw0qtglh07` FOREIGN KEY (`serie_id`) REFERENCES `serie` (`id_serie`),
  CONSTRAINT `FK8orvvuv51efxcqha3x0amcfq9` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `usuario_videojuego_favorita`
--

DROP TABLE IF EXISTS `usuario_videojuego_favorita`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario_videojuego_favorita` (
  `usuario_id` int NOT NULL,
  `videojuego_id` int NOT NULL,
  KEY `FKq435ht747wvr197ywofp6xeqk` (`videojuego_id`),
  KEY `FKmj7pg1u9gbpi5cc8y7dst1o43` (`usuario_id`),
  CONSTRAINT `FKmj7pg1u9gbpi5cc8y7dst1o43` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`),
  CONSTRAINT `FKq435ht747wvr197ywofp6xeqk` FOREIGN KEY (`videojuego_id`) REFERENCES `videojuego` (`id_videojuego`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `id` int NOT NULL AUTO_INCREMENT,
  `apellido` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKkfsp0s1tflm1cwlj8idhqsad0` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `usuarios_roles`
--

DROP TABLE IF EXISTS `usuarios_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios_roles` (
  `usuario_id` int NOT NULL,
  `rol_id` int NOT NULL,
  KEY `FK6yxg1lhuv5nievqea7rvn6afm` (`rol_id`),
  KEY `FKqcxu02bqipxpr7cjyj9dmhwec` (`usuario_id`),
  CONSTRAINT `FK6yxg1lhuv5nievqea7rvn6afm` FOREIGN KEY (`rol_id`) REFERENCES `rol` (`id`),
  CONSTRAINT `FKqcxu02bqipxpr7cjyj9dmhwec` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `videojuego`
--

DROP TABLE IF EXISTS `videojuego`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `videojuego` (
  `id_videojuego` int NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(2000) NOT NULL,
  `fecha_estreno` date NOT NULL,
  `ruta_portada` varchar(500) DEFAULT NULL,
  `titulo` varchar(255) NOT NULL,
  `youtube_trailer_id` varchar(255) NOT NULL,
  PRIMARY KEY (`id_videojuego`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `videojuego_genero_videojuego`
--

DROP TABLE IF EXISTS `videojuego_genero_videojuego`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `videojuego_genero_videojuego` (
  `videojuego_id` int NOT NULL,
  `genero_videojuego_id` int NOT NULL,
  KEY `FKrtxkdnttfv2hx6bordfkp77ui` (`genero_videojuego_id`),
  KEY `FKg0y5uc55nlhyb57dmw7nfl4da` (`videojuego_id`),
  CONSTRAINT `FKg0y5uc55nlhyb57dmw7nfl4da` FOREIGN KEY (`videojuego_id`) REFERENCES `videojuego` (`id_videojuego`),
  CONSTRAINT `FKrtxkdnttfv2hx6bordfkp77ui` FOREIGN KEY (`genero_videojuego_id`) REFERENCES `genero_videojuego` (`id_genero_videojuego`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-25 13:08:44
