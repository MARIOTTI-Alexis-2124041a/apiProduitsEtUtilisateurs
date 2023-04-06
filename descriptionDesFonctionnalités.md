# Description des fonctionnalités implémentées

## Tous les produits

Chemin : /api/produit

Renvoie : renvoie tous les produits et leurs informations present dans la base de données au format JSON

## Produit en particulier

Chemin: /api/produit/id

Parametres : id --> int, identifiant du produit voulu

Renvoie : renvoie le produit et ses informations au format JSON ou un JSON vide si le produit n'existe pas

## Modifier le stock d'un produit

Chemin: /api/produit/methode/id/quantité

Parametres : 
- methode --> String, increase pour augmenter le stock et decrease pour decrementer le stock
- id --> int, identifiant du produit voulu
- quantité --> int, la quantité de produit à ajouter ou à decrementer

Renvoie : 
- "increased" quand la quantité a bien été incrementée
- "decreased" quand la quantité a bien été décrementée
- "conflict" quand ils y a un problème avec la requêtte

## Liste des clients

Les clients sont des utilisateurs non gestionnaires, cette liste n'est visible que par les gestionnaire, il faut donc donner les identifiants d'un gestionnaire.

Chemin: /api/utilisateur/id/mdp

Parametres :
- id --> int, l'identifiant du gestionnaire
- mdp --> String, le mot de passe du gestionnaire

Renvoie :
- La liste des clients au format JSON si l'utilisateur donnée est gestionnaire avec un mot de passe correspondant.
- "L'utilisateur n'est pas un gestionnaire ou n'est pas connecté" au format JSON si l'utilisateur fourni ne correspond pas.

## Ajout utilisateur

Chemin : /api/utilisateur/idAdmin/mdpAdmin/id/mdp/isAdmin