package fr.univamu.iut.produitsetutilisateurs;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

import java.util.ArrayList;

/**
 * @author alexis MARIOTTI
 * Objet permetant de traiter des informations provenant de la base de donnée et de les renvoyer au format voulu. Cette classe est utilisée par les ressources
 */
public class ProduitEtUtilisateurService {

    protected ProduitsEtUtilisateursRepositoryInterface repo;

    public ProduitEtUtilisateurService(ProduitsEtUtilisateursRepositoryInterface ressourceEtUtilisateurRepo) {
        this.repo = ressourceEtUtilisateurRepo;
    }

    /**
     * Renvoie un produit au format JSON à partir de son identifiant
     * @param productId l'identifiant du produit
     * @return le produit au format JSON
     */
    public String getProductJSON(int productId){
        String result = null;
        Produit myProduct = repo.getProduct(productId);

        if( myProduct != null ) {

            // création du json et conversion du produit
            try (Jsonb jsonb = JsonbBuilder.create()) {
                result = jsonb.toJson(myProduct);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return result;
    }

    /**
     * Permet d'ajouter un utilisateur à la base de donnée
     * @param name nom de l'utilisateur
     * @param passwd mot de passe de l'utilisateur
     * @param isAdmin booleen identifiant si l'utilisateur est gestionnaire ou non
     * @return true : utilisateur ajoutée, false : probleme
     */
    public boolean addUtilisateur(String name, String passwd, boolean isAdmin){
        return repo.addUtilisateur(name,passwd,isAdmin);
    }

    /**
     * Renvoie tous les produits au format JSON
     * @return tous les produits au format JSON
     */
    public String allProductJSON(){
        ArrayList<Produit> allProd = repo.allProduct();

        // création du json et conversion de la liste de produit
        String result = null;
        try( Jsonb jsonb = JsonbBuilder.create()){
            result = jsonb.toJson(allProd);
        }
        catch (Exception e){
            System.err.println( e.getMessage() );
        }

        return result;
    }

    /**
     * Permet d'augmenter la quantité d'un produit dans la base de donnée
     * @param productId identifiant du produit
     * @param quantity quantité à augmenter
     * @return true : incrementée, false : probleme
     */
    public boolean increaseQuantity(int productId, int quantity){
        return repo.increaseQuantity(productId,quantity);
    }

    /**
     * Permet de diminuer la quantité d'un produit dans la base de donnée
     * @param productId identifiant du produit
     * @param quantity quantité à diminuer
     * @return true : decrementée, false : probleme
     */
    public boolean decreaseQuantity(int productId, int quantity){
        return repo.decreaseQuantity(productId,quantity);
    }

    /**
     * Renvoie tous les clients (utilisateurs non gestionnaires) au format JSON
     * @return tous les clients (utilisateurs non gestionnaires) au format JSON
     */
    public String allRegisteredJSON(){
        ArrayList<Utilisateur> allUser = repo.allRegistered();

        // création du json et conversion de la liste de livres
        String result = null;
        try( Jsonb jsonb = JsonbBuilder.create()){
            result = jsonb.toJson(allUser);
        }
        catch (Exception e){
            System.err.println( e.getMessage() );
        }

        return result;
    }

    /**
     * Renvoie un booleen identifiant si l'utilisateur indiqué est gestionnaire ou non, au format JSON
     * @param userId identifiant de l'utilisateur souhaité
     * @return booleen identifiant si l'utilisateur indiqué est gestionnaire ou non, au format JSON
     */
    public String isAdminJSON(int userId){
        String result = null;
        boolean isAdmin = repo.isAdmin(userId);

        // création du json et conversion du booleen
        try (Jsonb jsonb = JsonbBuilder.create()) {
            result = jsonb.toJson(isAdmin);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return result;
    }

    /**
     * Renvoie un booleen identifiant si l'identifiant et le mot de passe indiqués correspondent à un compte dans la base de donnée
     * @param id identifiant de l'utilisateur souhaité
     * @param password mot de passe de l'utilisateur souhaité
     * @return un booleen identifiant si l'identifiant et le mot de passe indiqués correspondent à un compte dans la base de donnée
     */
    public boolean isLogged(int id, String password){
        return repo.isLogged(id,password);
    }

    /**
     * Renvoie une string contenant un booleen au format JSON identifiant si l'identifiant indiqué correspond à un utilisateur dans la base de donnée
     * @param userId identifiant de l'utilisateur souhaité
     * @return une string contenant un booleen au format JSON identifiant si l'id indiqué existe ou non
     */
    public String isExistJSON(int userId){
        String result = null;
        boolean isExist = repo.isExist(userId);

        // création du json et conversion du booleen
        try (Jsonb jsonb = JsonbBuilder.create()) {
            result = jsonb.toJson(isExist);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return result;
    }

}
