package fr.univamu.iut.produitsetutilisateurs;

/**
 * @author alexis MARIOTTI
 * Objet permetant de representer un produit
 */
public class Produit {

    private int id;
    private float prix;
    private int quantite;

    /**
     * Objet Produit pouvant être créé avec les attributs presents dans la BD
     * @param id l'identifiant du produit
     * @param prix le prix du produit pour 1 de quantité
     * @param quantite la quantité du produit disponible en stock
     */
    public Produit(int id, float prix, int quantite) {
        this.id = id;
        this.prix = prix;
        this.quantite = quantite;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public int getId() {
        return id;
    }

    public float getPrix() {
        return prix;
    }

    public int getQuantite() {
        return quantite;
    }
}
