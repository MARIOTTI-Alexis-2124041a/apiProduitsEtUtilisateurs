package fr.univamu.iut.produitsetutilisateurs;

/**
 * Objet representant un Utilisateur avec les attributs present dans la BD
 * @author alexis MARIOTTI
 */
public class Utilisateur {

    private int id;
    private String nom;
    private String mdp;
    private boolean isAdmin;

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getMdp() {
        return mdp;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public Utilisateur(int id, String nom, String mdp, boolean isAdmin) {
        this.id = id;
        this.nom = nom;
        this.mdp = mdp;
        this.isAdmin = isAdmin;
    }
}
