# Projet-TAGL
projet tagl - cle valeur

// Partie Serveur : 
Notre systeme offre la possibilite de fonctionner sous forme client/serveur.


// Manipulation de structures de donnÃ©es complexes :
Notre systeme offre la possibilte d'utiliser des cle-valeur sur des String, des listes de String.
De plus il offre de plusieurs fonctionnalites pour chacunes de ces structures.
Ces fonctionnalites sont expliquees dans la javadoc fournie.

// Cache LRU :
Notre systeme fonctionne avec un cache LRU, de taille 10.
on a choisit une taille petite pour simplifier les tests, mais il est tres simple d'en augmenter la valeur.

// Parallelisation ::


//Tests : 
Les tests sont dans la classe Projet-TAGL/src/test/java/tagl/Gestion_cle_valeurTest.java. 
Realises avec JUnit, ils testent toutes les fonctionnalites de Gestion_cle_valeur.java pour les couples cle-valeur simples
et pour ceux qui sont des listes. Nous avons aussi teste le système de gestion de taille maximale de la memoire par effacement des plus anciens couples cle-valeurs lorsque l'on atteint la limite du nombre de couples autorises.