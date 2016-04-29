# Projet-TAGL
projet tagl - cle valeur

// Partie Serveur : 
Notre systeme offre la possibilite de fonctionner sous forme client/serveur.
Pour cela, un serveur doit etre lance avec un numéro de port.
Ensuite, un client est lance avec l'adresse ip du serveur,ainsi que son port


// Manipulation de structures de donnÃ©es complexes :
Notre systeme offre la possibilte d'utiliser des cle-valeur sur des String, des listes de String.
De plus il offre de plusieurs fonctionnalites pour chacunes de ces structures.
Ces fonctionnalites sont expliquees dans la javadoc fournie.

// Cache LRU :
Notre systeme fonctionne avec un cache LRU, de taille 10.
on a choisit une taille petite pour simplifier les tests, mais il est tres simple d'en augmenter la valeur.

// Parallelisation :
La parallelisation est operationel. quand un client se connecte, un numero de port lui est atribue. A ce moment la, il recontacte le serveur sur le nouveau port,ou un thread dedier s'occupera de repondre a ses requetes. Le thread est detruit lorsque la connection se termine.


//Tests : 
Les tests sont dans la classe Projet-TAGL/src/test/java/tagl/Gestion_cle_valeurTest.java. 
Realises avec JUnit, ils testent toutes les fonctionnalites de Gestion_cle_valeur.java pour les couples cle-valeur simples
et pour ceux qui sont des listes. Nous avons aussi teste le syst�me de gestion de taille maximale de la memoire par effacement des plus anciens couples cle-valeurs lorsque l'on atteint la limite du nombre de couples autorises.


---------------------------------------------------
//Test Client/Serveur:
-> correspond a une commande taper dans le terminal
**(Client) est le retour afficher sur le terminal

lancer via Eclipse Serveur.java avec comme argument un numero de port, ex :
-> java Serveur 8888

lancer via Eclipse Client.java avec comme argument l'ip sur lequel le Serveur est lancé ainsi que le numero de port correspondant, ex :
-> java Client 152.77.82.115 8888

Chaque client/serveur est lancé dans un terminal différent.




/\/\/\/\/\ Test Serveur
-> java Serveur 8888
**(Serveur) Server Ready




/\/\/\/\/\ Test double Serveur sur le meme port
Les serveurs sont lancés sur le meme ordinateur


-> java Serveur 8888
**(Serveur) Server Ready

-> java Serveur 8888
**(Serveur2) Server Ready
**(Serveur2) Exception caught when trying to listen on port 8888 or listening for a connection
**(Serveur2) Adresse déjà utilisée






/\/\/\/\/\ Test client sans serveur
-> java Client 152.77.82.115 8888
**(Client) Couldn't get I/O for the connection to 152.77.82.115




/\/\/\/\/\ Test nom a ralonge 1

	Creation serveur
-> java Serveur 8888
**(Serveur) Server Ready

	Ajout client
-> java Client 152.77.82.115 8888
**(Client1) got a port : 8889
**(Serveur) nouveau thread, port : 8889


	client commande set
-> set "le livre"
**(Client) resultat: set reussi
**(Serveur) message recu sur 8889 : set "le livre"

	client commande get
-> get "le livre" 
**(Client) resultat: 
**(Serveur) message recu sur 8889 : get "le livre"



/\/\/\/\/\ Test nom a ralonge 2

	Creation serveur
-> java Serveur 8888
**(Serveur) Server Ready

	Ajout client
-> java Client 152.77.82.115 8888
**(Client1) got a port : 8889
**(Serveur) nouveau thread, port : 8889


	client commande set
-> set "le livre" "Le livre de la jungle"
**(Client) resultat: set reussi
**(Serveur) message recu sur 8889 : set "le livre" "Le livre de la jungle"

	client commande get
-> get "le livre" 
**(Client) resultat: "Le livre de la jungle"
**(Serveur) message recu sur 8889 : "le livre"


/\/\/\/\/\ Test clients / serveur

	Creation serveur
-> java Serveur 8888
**(Serveur) Server Ready

	Ajout client
-> java Client 152.77.82.115 8888
**(Client1) got a port : 8889
**(Serveur) nouveau thread, port : 8889


Test d'acces de la base de donnée depuis un client:

	client commande set
-> set name george 
**(Client) resultat: set reussi
**(Serveur) message recu sur 8889 : set name george

	client commande get
-> get name 
**(Client) resultat: george
**(Serveur) message recu sur 8889 : get name

Test de la base de donnée modifié depuis un nouveau client:

	Ajout client numero 2
-> java Client 152.77.82.115 8888
**(Client2) got a port : 8890
**(Serveur) nouveau thread, port : 8890

	client2 commande get
	
-> get name
**(Client2) resultat: george
**(Serveur) message recu sur 8890 : get name

Modification de la base de donnée du client2 et recuperation des donnée depuis le client1

	client2 commande set
	
-> set name 10
**(Client2) resultat: set reussi
**(Serveur) message recu sur 8890 : set name 10

	client1 commande incr
	
-> incr name
**(Client2) resultat: 11
**(Serveur) message recu sur 8890 : incr name



	ajout d'un 3eme client distant ( autre machine ) qui se trouve sur le reseau
machine d'adresse 152.77.82.114

-> java Client 152.77.82.115 8888
**(Client3) got a port : 8891
**(Serveur) nouveau thread, port : 8891

	modif depuis le client 3
name etait a 11
-> incr name
**(Client3) resultat: 12
**(Serveur) message recu sur 8891 : incr name



Affichage complet pour le serveur:
Server Ready
nouveau thread, port : 8889
message recu sur 8889 : set name george
message recu sur 8889 : get name
nouveau thread, port : 8890
message recu sur 8890 : get name
message recu sur 8890 : set name 10
message recu sur 8889 : incr name
nouveau thread, port : 8891
message recu sur 8891 : incr name


---------------------------------------------------

