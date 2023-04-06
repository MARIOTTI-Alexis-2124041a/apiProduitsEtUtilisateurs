package fr.univamu.iut.produitsetutilisateurs;

import java.io.Closeable;
import java.sql.*;
import java.util.ArrayList;

/**
 * Classe d'accès à la base de donnée
 * @author alexis MARIOTTI
 */
public class ProduitsEtUtilisateursRepositoryMariadb implements ProduitsEtUtilisateursRepositoryInterface, Closeable {

    /**
     * Accès à la base de données (session)
     */
    protected Connection dbConnection ;

    public ProduitsEtUtilisateursRepositoryMariadb(String infoConnection, String user, String pwd ) throws java.sql.SQLException, java.lang.ClassNotFoundException {
        Class.forName("org.mariadb.jdbc.Driver");
        dbConnection = DriverManager.getConnection( infoConnection, user, pwd ) ;
    }

    @Override
    public void close() {
        try{
            dbConnection.close();
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
        }
    }

    /**
     * Renvoie un produit au dans un objet Produit à partir de son identifiant
     * @param productId l'identifiant du produit
     * @return le produit dans un objet Produit
     */
    @Override
    public Produit getProduct(int productId) {
        Produit selectedProduct = null;

        String query = "SELECT * FROM Produit WHERE idProduit=?";

        // construction et exécution d'une requête préparée
        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            ps.setInt(1, productId);

            //éxecution de la requette
            ResultSet result = ps.executeQuery();

            if( result.next() )
            {
                int id = result.getInt("idProduit");
                float price = result.getFloat("prix");
                String name = result.getString("nom");
                int quantity = result.getInt("quantite");

                selectedProduct = new Produit(id, price, quantity, name);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return selectedProduct;
    }

    /**
     * Permet d'ajouter un utilisateur à la base de donnée
     * @param name nom de l'utilisateur
     * @param passwd mot de passe de l'utilisateur
     * @param isAdmin booleen identifiant si l'utilisateur est gestionnaire ou non
     * @return true : utilisateur ajoutée, false : probleme
     */
    @Override
    public boolean addUtilisateur(String name, String passwd, boolean isAdmin) {
        String query = "INSERT INTO `Utilisateur` ( `nom`, `mdp`, `estGestionnaire`) VALUES ( ?, ?, ?);";
        int nbRowModified = 0;

        // construction et exécution d'une requête préparée
        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            ps.setString(1, name);
            ps.setString(2, passwd);
            ps.setBoolean(3, isAdmin);

            // exécution de la requête
            nbRowModified = ps.executeUpdate();
        } catch (SQLException e) {
            // on attrape l'erreur si la réservation existe déjà, mais on ne fait rien car le return s'en charge
        }
        return ( nbRowModified != 0 );
    }

    /**
     * Renvoie une array liste de tous les produits
     * @return Une array liste de tous les produits
     */
    @Override
    public ArrayList<Produit> allProduct() {
        ArrayList<Produit> allProducts = null;

        String query = "SELECT * FROM Produit";

        // construction et exécution d'une requête préparée
        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            // exécution de la requête
            ResultSet result = ps.executeQuery();

            allProducts = new ArrayList<Produit>();

            // récupération du premier (et seul) tuple résultat
            while ( result.next() )
            {
                int id = result.getInt("idProduit");
                float price = result.getFloat("prix");
                String name = result.getString("nom");
                int quantity = result.getInt("quantite");

                // création du produit courant
                Produit currentProduct = new Produit(id, price, quantity, name);

                allProducts.add(currentProduct);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allProducts;
    }

    /**
     * Permet d'augmenter la quantité d'un produit dans la base de donnée
     * @param productId identifiant du produit
     * @param quantity quantité à augmenter
     * @return true : incrementée, false : probleme
     */
    @Override
    public boolean increaseQuantity(int productId, int quantity) {
        String query = "UPDATE Produit SET quantite = quantite + ? WHERE Produit.idProduit = ?;";
        int nbRowModified = 0;

        // construction et exécution d'une requête préparée
        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            ps.setInt(1, quantity);
            ps.setInt(2, productId);

            // exécution de la requête
            nbRowModified = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ( nbRowModified != 0 );
    }

    /**
     * Permet de diminuer la quantité d'un produit dans la base de donnée
     * @param productId identifiant du produit
     * @param quantity quantité à diminuer
     * @return true : decrementée, false : probleme
     */
    @Override
    public boolean decreaseQuantity(int productId, int quantity) {
        //on utilise la fonction greatest pour eviter les valeurs negatives
        String query = "UPDATE Produit SET quantite = greatest(quantite - ?,0) WHERE Produit.idProduit = ?;";
        int nbRowModified = 0;

        // construction et exécution d'une requête préparée
        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            ps.setInt(1, quantity);
            ps.setInt(2, productId);

            // exécution de la requête
            nbRowModified = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ( nbRowModified != 0 );
    }

    /**
     * Renvoie tous les clients (utilisateurs non gestionnaires) dans une ArrayList d'Utilisateurs
     * @return tous les clients (utilisateurs non gestionnaires) dans une ArrayList d'Utilisateurs
     */
    @Override
    public ArrayList<Utilisateur> allRegistered() {
        ArrayList<Utilisateur> allRegistered = null;

        String query = "SELECT * FROM Utilisateur WHERE estGestionnaire = 0";

        // construction et exécution d'une requête préparée
        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            // exécution de la requête
            ResultSet result = ps.executeQuery();

            allRegistered = new ArrayList<Utilisateur>();

            // récupération du premier (et seul) tuple résultat
            while ( result.next() )
            {
                int id = result.getInt("idUtilisateur");
                String passwd = result.getString("mdp");
                String name = result.getString("nom");
                boolean isAdmin = result.getBoolean("estGestionnaire");

                // création de l'utilisateur courant
                Utilisateur currentUser = new Utilisateur(id,name,passwd,isAdmin);

                allRegistered.add(currentUser);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allRegistered;
    }

    /**
     * Renvoie un booleen identifiant si l'utilisateur indiqué est gestionnaire ou non
     * @param userId identifiant de l'utilisateur souhaité
     * @return booleen identifiant si l'utilisateur indiqué est gestionnaire ou non
     */
    public boolean isAdmin(int userId){
        boolean isAdmin = false;

        String query = "SELECT * FROM Utilisateur WHERE idUtilisateur = ?";

        // construction et exécution d'une requête préparée
        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            ps.setInt(1, userId);

            // exécution de la requête
            ResultSet result = ps.executeQuery();

            if (result.next()){
                isAdmin = result.getBoolean("estGestionnaire");

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return isAdmin;
    }

    /**
     * Renvoie un booleen identifiant si l'identifiant et le mot de passe indiqués correspondent à un compte dans la base de donnée
     * @param userId identifiant de l'utilisateur souhaité
     * @param password mot de passe de l'utilisateur souhaité
     * @return un booleen identifiant si l'identifiant et le mot de passe indiqués correspondent à un compte dans la base de donnée
     */
    public boolean isLogged(int userId, String password){
        boolean isLogged = false;

        String query = "SELECT * FROM `Utilisateur` WHERE idUtilisateur = ? AND mdp = ?";
        // construction et exécution d'une requête préparée
        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            ps.setInt(1, userId);
            ps.setString(2,password);

            // exécution de la requête
            ResultSet result = ps.executeQuery();

            if (result.next()){
                isLogged = true;
            } else {
                isLogged = false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return isLogged;
    }
}
