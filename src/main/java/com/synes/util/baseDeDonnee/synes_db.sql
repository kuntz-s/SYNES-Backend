-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le :  lun. 12 juin 2023 à 00:50
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
  `posteLe` datetime NOT NULL,
  `idMembre` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `avoirpermission`
--

CREATE TABLE `avoirpermission` (
  `idRole` int(50) NOT NULL,
  `idPermission` int(20) NOT NULL,
  `id` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `avoirpermission`
--

INSERT INTO `avoirpermission` (`idRole`, `idPermission`, `id`) VALUES
(1, 1, 1),
(1, 2, 2),
(1, 4, 3);

-- --------------------------------------------------------

--
-- Structure de la table `evenement`
--

CREATE TABLE `evenement` (
  `id` int(20) NOT NULL,
  `nom` varchar(100) NOT NULL,
  `dateDebut` datetime NOT NULL,
  `dateFin` datetime NOT NULL,
  `description` varchar(500) NOT NULL,
  `idMembre` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `membre`
--

CREATE TABLE `membre` (
  `matricule` varchar(20) NOT NULL,
  `nom` varchar(500) NOT NULL,
  `prenom` varchar(500) NOT NULL,
  `email` varchar(500) NOT NULL,
  `photo` varchar(500) DEFAULT NULL,
  `motDePasse` varchar(500) NOT NULL,
  `idRole` int(20) NOT NULL,
  `id` int(20) NOT NULL,
  `idUniversite` int(20) NOT NULL,
  `dateCreation` datetime NOT NULL,
  `dateInscription` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `membre`
--

INSERT INTO `membre` (`matricule`, `nom`, `prenom`, `email`, `photo`, `motDePasse`, `idRole`, `id`, `idUniversite`, `dateCreation`, `dateInscription`) VALUES
('12x034euy1', 'tchuente', 'micaelle', 'nzouetengmicaelle@gmail.com', NULL, '$2a$10$/xuv7CJ4/7rCVJUYXU0YGe7br0gAsKiqNSsy20/DULDAgStpEXTHa', 1, 1, 1, '2023-05-22 14:19:35', '2010-10-10 10:10:10');

-- --------------------------------------------------------

--
-- Structure de la table `membreconnected`
--

CREATE TABLE `membreconnected` (
  `idMembre` int(20) NOT NULL,
  `id` int(20) NOT NULL,
  `nomRole` varchar(100) NOT NULL,
  `nomUniversite` varchar(100) NOT NULL,
  `token` varchar(500) NOT NULL,
  `permissions` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `membreconnected`
--

INSERT INTO `membreconnected` (`idMembre`, `id`, `nomRole`, `nomUniversite`, `token`, `permissions`) VALUES
(1, 1, 'Secretaire Congres', 'universite de yaounde 1', 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJuem91ZXRlbmdtaWNhZWxsZUBnbWFpbC5jb20iLCJleHAiOjE2ODUzMzE2NDgsImlhdCI6MTY4NTMxMzY0OH0.oG4EsgYnwg8alQl39PvYphVpEfGlTMcUcrB_YRDywsb9oxaNGqzoTZv85QF2iKZU51AEIkorAB1b9h2JbyHWfQ', ';Gestion Syndicat;Création membre;Attributtion role systeme');

-- --------------------------------------------------------

--
-- Structure de la table `notification`
--

CREATE TABLE `notification` (
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
  `description` varchar(500) NOT NULL,
  `nom` varchar(50) NOT NULL,
  `idUniversite` int(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `organe`
--

INSERT INTO `organe` (`id`, `fondAlloue`, `description`, `nom`, `idUniversite`) VALUES
(1, 1000000, 'organe supreme du syndicat', 'congres', NULL);

-- --------------------------------------------------------

--
-- Structure de la table `permissions`
--

CREATE TABLE `permissions` (
  `nom` varchar(50) NOT NULL,
  `description` varchar(100) NOT NULL,
  `id` int(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `permissions`
--

INSERT INTO `permissions` (`nom`, `description`, `id`) VALUES
('Gestion Syndicat', 'gérer tout ce qui concerne la création, modification, suppresion des roles, organes, universités', 1),
('Création membre', 'pouvoir créer un membre simplement avec le role par defaut Membre université', 2),
('Attributtion role organe', 'pouvoir attribuer un role à tout menbre de son organe uniquement', 3),
('Attributtion role systeme', 'pouvoir attribuer un role à tout membre du systeme', 4),
('Gestion Evènement', 'Pouvoir manipuler les différents évènements du syndicat', 5);

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
  `circonscription` varchar(100) NOT NULL,
  `id` int(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `role`
--

CREATE TABLE `role` (
  `nom` varchar(50) NOT NULL,
  `description` varchar(500) NOT NULL,
  `id` int(50) NOT NULL,
  `idOrgane` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `role`
--

INSERT INTO `role` (`nom`, `description`, `id`, `idOrgane`) VALUES
('Secretaire Congres', 'porte parole de l\'organe du congres qui sera en charge la création des différents postes y relatifs et aussi des différentes universités participantes du SYNES', 1, 1);

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
  `montant` int(11) NOT NULL,
  `type` varchar(50) NOT NULL,
  `raison` varchar(100) NOT NULL,
  `id` int(20) NOT NULL,
  `idMembre` int(20) NOT NULL,
  `idEvenement` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `universite`
--

CREATE TABLE `universite` (
  `nom` varchar(100) NOT NULL,
  `localisation` varchar(100) NOT NULL,
  `logo` varchar(100) DEFAULT NULL,
  `id` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `universite`
--

INSERT INTO `universite` (`nom`, `localisation`, `logo`, `id`) VALUES
('universite de yaounde 1', 'yaounde', NULL, 1);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `annonce`
--
ALTER TABLE `annonce`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fkmembre` (`idMembre`);

--
-- Index pour la table `avoirpermission`
--
ALTER TABLE `avoirpermission`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_permission` (`idPermission`),
  ADD KEY `rrole` (`idRole`);

--
-- Index pour la table `evenement`
--
ALTER TABLE `evenement`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fkmm` (`idMembre`);

--
-- Index pour la table `membre`
--
ALTER TABLE `membre`
  ADD PRIMARY KEY (`id`),
  ADD KEY `univercitéId` (`idUniversite`),
  ADD KEY `idRole` (`idRole`);

--
-- Index pour la table `membreconnected`
--
ALTER TABLE `membreconnected`
  ADD PRIMARY KEY (`id`),
  ADD KEY `memb` (`idMembre`);

--
-- Index pour la table `notification`
--
ALTER TABLE `notification`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `organe`
--
ALTER TABLE `organe`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_univ` (`idUniversite`);

--
-- Index pour la table `permissions`
--
ALTER TABLE `permissions`
  ADD PRIMARY KEY (`id`);

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
  ADD UNIQUE KEY `unique` (`id`),
  ADD KEY `fk_idNot` (`idMembre`);

--
-- Index pour la table `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_organe` (`idOrgane`);

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
  ADD PRIMARY KEY (`id`),
  ADD KEY `idmem` (`idMembre`),
  ADD KEY `fkeven` (`idEvenement`);

--
-- Index pour la table `universite`
--
ALTER TABLE `universite`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `annonce`
--
ALTER TABLE `annonce`
  MODIFY `id` int(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `avoirpermission`
--
ALTER TABLE `avoirpermission`
  MODIFY `id` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT pour la table `evenement`
--
ALTER TABLE `evenement`
  MODIFY `id` int(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `membre`
--
ALTER TABLE `membre`
  MODIFY `id` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT pour la table `membreconnected`
--
ALTER TABLE `membreconnected`
  MODIFY `id` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `notification`
--
ALTER TABLE `notification`
  MODIFY `id` int(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `organe`
--
ALTER TABLE `organe`
  MODIFY `id` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT pour la table `permissions`
--
ALTER TABLE `permissions`
  MODIFY `id` int(50) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT pour la table `piecesjointes`
--
ALTER TABLE `piecesjointes`
  MODIFY `id` int(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `recevoirnotification`
--
ALTER TABLE `recevoirnotification`
  MODIFY `id` int(50) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `role`
--
ALTER TABLE `role`
  MODIFY `id` int(50) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

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
-- AUTO_INCREMENT pour la table `universite`
--
ALTER TABLE `universite`
  MODIFY `id` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `annonce`
--
ALTER TABLE `annonce`
  ADD CONSTRAINT `fkmembre` FOREIGN KEY (`idMembre`) REFERENCES `membre` (`id`);

--
-- Contraintes pour la table `avoirpermission`
--
ALTER TABLE `avoirpermission`
  ADD CONSTRAINT `fk_permission` FOREIGN KEY (`idPermission`) REFERENCES `permissions` (`id`),
  ADD CONSTRAINT `rrole` FOREIGN KEY (`idRole`) REFERENCES `role` (`id`);

--
-- Contraintes pour la table `evenement`
--
ALTER TABLE `evenement`
  ADD CONSTRAINT `fkmm` FOREIGN KEY (`idMembre`) REFERENCES `membre` (`id`);

--
-- Contraintes pour la table `membre`
--
ALTER TABLE `membre`
  ADD CONSTRAINT `c` FOREIGN KEY (`idUniversite`) REFERENCES `universite` (`id`),
  ADD CONSTRAINT `fk_role` FOREIGN KEY (`idRole`) REFERENCES `role` (`id`);

--
-- Contraintes pour la table `membreconnected`
--
ALTER TABLE `membreconnected`
  ADD CONSTRAINT `memb` FOREIGN KEY (`idMembre`) REFERENCES `membre` (`id`);

--
-- Contraintes pour la table `organe`
--
ALTER TABLE `organe`
  ADD CONSTRAINT `fk_univ` FOREIGN KEY (`idUniversite`) REFERENCES `universite` (`id`);

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
-- Contraintes pour la table `role`
--
ALTER TABLE `role`
  ADD CONSTRAINT `fk_organe` FOREIGN KEY (`idOrgane`) REFERENCES `organe` (`id`);

--
-- Contraintes pour la table `soldebancaire`
--
ALTER TABLE `soldebancaire`
  ADD CONSTRAINT `fk_transaction` FOREIGN KEY (`idTransaction`) REFERENCES `transaction` (`id`);

--
-- Contraintes pour la table `transaction`
--
ALTER TABLE `transaction`
  ADD CONSTRAINT `fkeven` FOREIGN KEY (`idEvenement`) REFERENCES `evenement` (`id`),
  ADD CONSTRAINT `idmem` FOREIGN KEY (`idMembre`) REFERENCES `membre` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
