package fr.univamu.iut.produitsetutilisateurs;

import java.util.ArrayList;

/**
 * @author alexis MARIOTTI
 * Interfaace identifiant les methodes pour la classe d'accés à la BD
 */
public interface ProduitsEtUtilisateursRepositoryInterface {

    public void close();

    /**
     * Renvoie un produit au dans un objet Produit à partir de son identifiant
     * @param productId l'identifiant du produit
     * @return le produit dans un objet Produit
     */
    public Produit getProduct(int productId);

    /**
     * Permet d'ajouter un utilisateur à la base de donnée
     * @param name nom de l'utilisateur
     * @param passwd mot de passe de l'utilisateur
     * @param isAdmin booleen identifiant si l'utilisateur est gestionnaire ou non
     * @return true : utilisateur ajoutée, false : probleme
     */
    public boolean addUtilisateur(String name, String passwd, boolean isAdmin);

    /**
     * Renvoie une array liste de tous les produits
     * @return Une array liste de tous les produits
     */
    public ArrayList<Produit> allProduct();

    /**
     * Permet d'augmenter la quantité d'un produit dans la base de donnée
     * @param productId identifiant du produit
     * @param quantity quantité à augmenter
     * @return true : incrementée, false : probleme
     */
    public boolean increaseQuantity(int productId, int quantity);

    /**
     * Permet de diminuer la quantité d'un produit dans la base de donnée
     * @param productId identifiant du produit
     * @param quantity quantité à diminuer
     * @return true : decrementée, false : probleme
     */
    public boolean decreaseQuantity(int productId, int quantity);

    /**
     * Renvoie tous les clients (utilisateurs non gestionnaires) dans une ArrayList d'Utilisateurs
     * @return tous les clients (utilisateurs non gestionnaires) dans une ArrayList d'Utilisateurs
     */
    public ArrayList<Utilisateur> allRegistered();

    /**
     * Renvoie un booleen identifiant si l'utilisateur indiqué est gestionnaire ou non
     * @param userId identifiant de l'utilisateur souhaité
     * @return booleen identifiant si l'utilisateur indiqué est gestionnaire ou non
     */
    public boolean isAdmin(int userId);

    /**
     * Renvoie un booleen identifiant si l'identifiant et le mot de passe indiqués correspondent à un compte dans la base de donnée
     * @param userId identifiant de l'utilisateur souhaité
     * @param password mot de passe de l'utilisateur souhaité
     * @return un booleen identifiant si l'identifiant et le mot de passe indiqués correspondent à un compte dans la base de donnée
     */
    public boolean isLogged(int userId, String password);

    /**
     * Renvoie un booleen identifiant si l'identifiant indiqué correspond à un utilisateur dans la base de donnée
     * @param userId identifiant de l'utilisateur souhaité
     * @return booleen identifiant si l'id indiqué existe ou non
     */
    public boolean isExist(int userId);
}
