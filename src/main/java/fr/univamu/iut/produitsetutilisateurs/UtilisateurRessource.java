package fr.univamu.iut.produitsetutilisateurs;

import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

/**
 * @author alexis MARIOTTI
 */
@Path("/utilisateur")
public class UtilisateurRessource {

    private ProduitEtUtilisateurService service;

    public UtilisateurRessource(){}

    public @Inject UtilisateurRessource(ProduitsEtUtilisateursRepositoryInterface repo){
        this.service = new ProduitEtUtilisateurService(repo);
    }
    /**
     * Enpoint permettant de publier de tous les clients à un gestionnaire ( n'affiche pas les questionnaires)
     *
     * @param id identifiant du gestionnaire qui veut afficher les clients
     * @param password mot de passe du gestionnaire qui veut afficher les clients
     * @return la liste des clients (avec leurs informations) au format JSON
     */
    @GET
    @Produces("application/json")
    @Path("{id}/{password}")
    public String getAllProduct(@PathParam("id") int id, @PathParam("password") String password) {
        Jsonb jsonb = JsonbBuilder.create();
        if (service.isLogged(id,password) && jsonb.fromJson(service.isAdminJSON(id), Boolean.class)){
            return service.allRegisteredJSON();
        } return jsonb.toJson("L'utilisateur n'est pas un gestionnaire ou n'est pas connecté");
    }

    /**
     * Enpoint permettant de publier si l'utilisateur associé à l'id est un gestionnaire ou non
     * @param userId identifiant de l'utilisateur desiré
     * @return la liste des clients (avec leurs informations) au format JSON
     */
    @GET
    @Produces("application/json")
    @Path("{userId}")
    public String isAdmin(@PathParam("userId") int userId) {
        return service.isAdminJSON(userId);
    }

    /**
     * Permet d'ajouter un utilisateur, seulement si l'on est connecté en tant que gestionnaire
     * @param id l'identifiant du gestionnaire qui veut ajouter cet utilisateur
     * @param adminPassword le mot de passe du gestionnaire qui veut ajouter cet utilisateur
     * @param name le nom de l'utilisateur à ajouter
     * @param password le mot de passe (déja aché de l'utiliateur à ajouter)
     * @param isAdmin booléen permetant de savoir si l'utilisateur à ajouter est un gestionnaire ou un simple inscrit
     * @return Response
     */
    @GET
    @Produces("application/json")
    @Path("{id}/{adminPassword}/{name}/{password}/{isAdmin}")
    public Response addUser(@PathParam("id") int id, @PathParam("adminPassword") String adminPassword, @PathParam("name") String name, @PathParam("password") String password, @PathParam("isAdmin") boolean isAdmin) {
        Jsonb jsonb = JsonbBuilder.create();
        if (service.isLogged(id,adminPassword) && jsonb.fromJson(service.isAdminJSON(id), Boolean.class)){
            if (service.addUtilisateur(name,password,isAdmin)){
                return Response.ok("user created").build();
            }
            return Response.status(Response.Status.CONFLICT).build();
        }
        return Response.status(Response.Status.CONFLICT).build();
    }
}
