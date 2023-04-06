package fr.univamu.iut.produitsetutilisateurs;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

/**
 * @author alexis MARIOTTI
 */
@Path("/produit")
public class ProduitRessource {

    private ProduitEtUtilisateurService service;

    public ProduitRessource(){}

    public @Inject ProduitRessource(ProduitsEtUtilisateursRepositoryInterface repo){
        this.service = new ProduitEtUtilisateurService(repo);
    }

    /**
     * Enpoint permettant de publier de tous les produits
     * @return la liste des produits (avec leurs informations) au format JSON
     */
    @GET
    @Produces("application/json")
    public String getAllProduct() {
        return service.allProductJSON();
    }


    /**
     * Endpoint permettant de publier les informations d'un produit
     * dont la référence est passée paramètre dans le chemin
     * @param productId référence du produit cherché
     * @return les informations du produit au format JSON
     */
    @GET
    @Path("{productId}")
    @Produces("application/json")
    public String getProduct( @PathParam("productId") int productId){

        String result = service.getProductJSON(productId);

        // si aucun produit n'a été trouvée
        // on retourne simplement un JSON vide
        if( result == null )
            return "{}";

        return result;
    }

    /**
     * Endpoint permettant d'augmenter ou de diminuer la quantité d'un produit
     * @param method methoide que l'on veut uttiliser : "increase" ou "decrease"
     * @param productId référence du produit cherché
     * @param quantity quantité à ajouter
     * @return une Reponse indiquant si la requête s'est bien exécutée ou non
     */
    @GET
    @Path("{method}/{productId}/{quantity}")
    @Produces("application/json")
    public Response adjustValueProduct(@PathParam("method") String method, @PathParam("productId") int productId, @PathParam("quantity") int quantity){
        if (method.equals("increase")){
            if (service.increaseQuantity(productId,quantity)){
                return Response.ok("increased").build();
            }else{
                return Response.status(Response.Status.CONFLICT).build();
            }
        } else if (method.equals("decrease")) {
            if (service.decreaseQuantity(productId,quantity)){
                return Response.ok("decreased").build();
            }else{
                return Response.status(Response.Status.CONFLICT).build();
            }
        }
        return Response.status(Response.Status.CONFLICT).build();
    }

}