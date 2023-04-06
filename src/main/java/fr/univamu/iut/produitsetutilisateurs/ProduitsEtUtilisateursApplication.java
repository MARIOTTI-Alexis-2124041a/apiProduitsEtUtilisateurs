package fr.univamu.iut.produitsetutilisateurs;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

/**
 * @author alexis MARIOTTI
 */
@ApplicationPath("/api")
@ApplicationScoped
public class ProduitsEtUtilisateursApplication extends Application {

    @Produces
    private ProduitsEtUtilisateursRepositoryInterface openDbConnection(){
        ProduitsEtUtilisateursRepositoryMariadb db = null;

        try{
            db = new ProduitsEtUtilisateursRepositoryMariadb("jdbc:mariadb://mysql-annonces.alwaysdata.net/annonces_projet_bd", "annonces_projet", "%Admin0!");
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }
        return db;
    }

    private void closeDbConnection(@Disposes ProduitsEtUtilisateursRepositoryInterface resaRepo ) {
        resaRepo.close();
    }

}