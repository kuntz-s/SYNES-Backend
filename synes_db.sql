-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le :  mer. 17 mai 2023 à 13:21
-- Version du serveur :  10.1.36-MariaDB
-- Version de PHP :  5.6.38

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `synes_db`
--

-- --------------------------------------------------------

--
-- Structure de la table `annonce`
--

CREATE TABLE `annonce` (
  `titre` varchar(250) NOT NULL,
  `contenu` varchar(500) NOT NULL,
  `typeAnnonce` varchar(50) NOT NULL,
  `id` int(20) NOT NULL,
  `posteLe` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `avoirpermission`
--

CREATE TABLE `avoirpermission` (
  `idRole` int(20) NOT NULL,
  `idPermission` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `membre`
--

CREATE TABLE `membre` (
  `matricule` varchar(20) NOT NULL,
  `nom` varchar(50) NOT NULL,
  `prenom` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `photo` varchar(100) DEFAULT NULL,
  `motDePasse` varchar(50) NOT NULL,
  `role` varchar(50) NOT NULL,
  `id` int(20) NOT NULL,
  `idUnivercite` int(20) NOT NULL,
  `dateCreation` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `notification`
--

CREATE TABLE `notification` (
  `envoyePar` varchar(20) NOT NULL,
  `typeMessage` varchar(50) NOT NULL,
  `envoyéLe` datetime NOT NULL,
  `id` int(20) NOT NULL,
  `contenu` varchar(500) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `organe`
--

CREATE TABLE `organe` (
  `id` int(20) NOT NULL,
  `fondAlloue` int(50) DEFAULT NULL,
  `description` varchar(100) NOT NULL,
  `nom` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `permissions`
--

CREATE TABLE `permissions` (
  `nom` varchar(50) NOT NULL,
  `description` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `piecesjointes`
--

CREATE TABLE `piecesjointes` (
  `nom` varchar(50) NOT NULL,
  `urlFichier` varchar(50) NOT NULL,
  `id` int(20) NOT NULL,
  `idAnnonce` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `recevoirnotification`
--

CREATE TABLE `recevoirnotification` (
  `idNotification` int(20) NOT NULL,
  `idMembre` int(20) NOT NULL,
  `circonscription` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `role`
--

CREATE TABLE `role` (
  `nom` varchar(50) NOT NULL,
  `description` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `soldebancaire`
--

CREATE TABLE `soldebancaire` (
  `solde` int(20) NOT NULL,
  `modifieLe` date NOT NULL,
  `id` int(20) NOT NULL,
  `idTransaction` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `transaction`
--

CREATE TABLE `transaction` (
  `effectuePar` int(20) NOT NULL,
  `montant` int(11) NOT NULL,
  `type` varchar(50) NOT NULL,
  `raison` varchar(100) NOT NULL,
  `id` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `université`
--

CREATE TABLE `université` (
  `nom` varchar(100) NOT NULL,
  `localisation` varchar(100) NOT NULL,
  `logo` varchar(100) DEFAULT NULL,
  `id` int(20) NOT NULL,
  `idSection` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `annonce`
--
ALTER TABLE `annonce`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `membre`
--
ALTER TABLE `membre`
  ADD PRIMARY KEY (`id`),
  ADD KEY `univercitéId` (`idUnivercite`),
  ADD KEY `Role` (`role`);

--
-- Index pour la table `notification`
--
ALTER TABLE `notification`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `organe`
--
ALTER TABLE `organe`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `permissions`
--
ALTER TABLE `permissions`
  ADD PRIMARY KEY (`nom`);

--
-- Index pour la table `piecesjointes`
--
ALTER TABLE `piecesjointes`
  ADD PRIMARY KEY (`id`),
  ADD KEY `annoneId` (`idAnnonce`);

--
-- Index pour la table `recevoirnotification`
--
ALTER TABLE `recevoirnotification`
  ADD PRIMARY KEY (`idNotification`,`idMembre`),
  ADD KEY `fk_idNot` (`idMembre`);

--
-- Index pour la table `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`nom`);

--
-- Index pour la table `soldebancaire`
--
ALTER TABLE `soldebancaire`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idTransaction` (`idTransaction`);

--
-- Index pour la table `transaction`
--
ALTER TABLE `transaction`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `université`
--
ALTER TABLE `université`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_idsection` (`idSection`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `annonce`
--
ALTER TABLE `annonce`
  MODIFY `id` int(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `membre`
--
ALTER TABLE `membre`
  MODIFY `id` int(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `notification`
--
ALTER TABLE `notification`
  MODIFY `id` int(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `organe`
--
ALTER TABLE `organe`
  MODIFY `id` int(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `piecesjointes`
--
ALTER TABLE `piecesjointes`
  MODIFY `id` int(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `soldebancaire`
--
ALTER TABLE `soldebancaire`
  MODIFY `id` int(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `transaction`
--
ALTER TABLE `transaction`
  MODIFY `id` int(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `université`
--
ALTER TABLE `université`
  MODIFY `id` int(20) NOT NULL AUTO_INCREMENT;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `membre`
--
ALTER TABLE `membre`
  ADD CONSTRAINT `c` FOREIGN KEY (`idUnivercite`) REFERENCES `université` (`id`),
  ADD CONSTRAINT `fk_role` FOREIGN KEY (`Role`) REFERENCES `role` (`nom`);

--
-- Contraintes pour la table `piecesjointes`
--
ALTER TABLE `piecesjointes`
  ADD CONSTRAINT `cléEtrangere` FOREIGN KEY (`idAnnonce`) REFERENCES `annonce` (`id`);

--
-- Contraintes pour la table `recevoirnotification`
--
ALTER TABLE `recevoirnotification`
  ADD CONSTRAINT `fk_idNot` FOREIGN KEY (`idMembre`) REFERENCES `membre` (`id`),
  ADD CONSTRAINT `fk_idnotification` FOREIGN KEY (`idNotification`) REFERENCES `notification` (`id`);

--
-- Contraintes pour la table `soldebancaire`
--
ALTER TABLE `soldebancaire`
  ADD CONSTRAINT `fk_transaction` FOREIGN KEY (`idTransaction`) REFERENCES `transaction` (`id`);

--
-- Contraintes pour la table `université`
--
ALTER TABLE `université`
  ADD CONSTRAINT `fk_idsection` FOREIGN KEY (`idSection`) REFERENCES `organe` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
