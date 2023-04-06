package fr.univamu.iut.produitsetutilisateurs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ProduitEtUtilisateurServiceTest {

    private ProduitEtUtilisateurService service;

    @BeforeEach
    void setUp() throws SQLException, ClassNotFoundException {
        ProduitsEtUtilisateursRepositoryInterface db = new ProduitsEtUtilisateursRepositoryMariadb("jdbc:mariadb://mysql-annonces.alwaysdata.net/annonces_projet_bd", "annonces_projet", "%Admin0!");
        this.service = new ProduitEtUtilisateurService(db);
    }

    @Test
    void getProductJSON() {

        String product = this.service.getProductJSON(1);

        Assertions.assertEquals("{\"id\":1,\"prix\":10.5,\"quantite\":60}", product);
    }

    @Test
    void addUtilisateur() {
    }

    @Test
    void allProductJSON() {
    }

    @Test
    void increaseQuantity() {
    }

    @Test
    void decreaseQuantity() {
    }

    @Test
    void allRegisteredJSON() {
    }

    @Test
    void isAdminJSON() {
    }

    @Test
    void isLogged() {
    }
}