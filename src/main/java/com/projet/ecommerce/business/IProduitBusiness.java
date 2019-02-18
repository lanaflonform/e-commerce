package com.projet.ecommerce.business;

import java.util.List;

import org.springframework.data.domain.Page;

import com.projet.ecommerce.business.dto.CaracteristiqueDTO;
import com.projet.ecommerce.business.dto.ProduitDTO;
import com.projet.ecommerce.business.dto.TypeCaracteristiqueDTO;
import com.projet.ecommerce.persistance.entity.Produit;

/**
 * Interface du service ProduitBusiness.
 */

public interface IProduitBusiness {

    /**
     * Méthode définissant la modification d'un produit.
     *
     * @param referenceProduit La référence du produit
     * @param nom              Le nom du produit
     * @param description      Sa description
     * @param prixHT           Son prix hors taxe
     * @return le produit ajouté
     */
    ProduitDTO add(String referenceProduit, String nom, String description, float prixHT, List<Integer> nouvelleCat);

    /**
     * Méthode définissant la modification d'un produit.
     *
     * @param produit L'objet produit modifié à sauvegarder
     * @return l'objet produit modifié
     */
    ProduitDTO update(Produit produit);

    /**
     * Méthode définissant la suppression d'un produit.
     *
     * @param referenceProduit Référence du produit à supprimer
     * @return true
     */
    boolean delete(String referenceProduit);

    /**
     * Méthode définissant la recherche de tous les produits.
     *
     * @return une liste de produit
     */
    List<ProduitDTO> getAll();

    /**
     * Méthode définissant la recherche des produits selon les paramètres ci-dessous
     *
     * @param ref la référence du produit recherché
     * @param nom le nom du produit recherché
     * @param cat la catégorie du /des produit(s) recherché(s)
     * @return la liste des produits trouvés
     */
    List<ProduitDTO> getAll(String ref, String nom, String cat);

    /**
     * Méthode définissant la pagination
     *
     * @param pageNumber  la page souhaitée
     * @param nb          le nombre de produit à afficher dans la page
     * @param nom         le nom du produit recherché
     * @param IDcategorie l'id catégorie recherchée
     * @return une page de produit
     */
    Page<Produit> getPage(int pageNumber, int nb, String nom, int IDcategorie);
    
    
    /**
     * Méthode ajoutant une caractéristique au produit.
     * La caractéristique sera inserée en base
     * 
     * @param refProduit  la référence du produit auquel on ajoute la caractéristique
     * @param type  le type de la caractéristique ajoutée
     * @param valeur  la valeur de la caractéristique ajoutée
     * @return  un nouvel objet ProduitDTO refletant la modification
     */
    ProduitDTO addCaracteristique(String refProduit, String type, String valeur);
    
    /**
     * Méthode supprimant une caractéristique de son produit.
     * La caractéristique sera supprimée de la base de donnée.
     * 
     * @param carac la caractéristique supprimée
     * @return un nouvel objet ProduitDTO refletant la modification
     */
    ProduitDTO removeCaracteristique(CaracteristiqueDTO carac);
    
    /**
     * Méthode pour récuperer la liste des types de caracteristique possibles.
     * @return la liste des constantes possibles utilisées pour typer les caracteristiques sous forme de List<String>
     */
    List<String> getTypesCaracteristiques(); 
    
   


}
