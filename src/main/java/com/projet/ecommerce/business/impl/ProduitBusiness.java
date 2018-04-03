package com.projet.ecommerce.business.impl;

import com.projet.ecommerce.business.IProduitBusiness;
import com.projet.ecommerce.entity.Produit;
import com.projet.ecommerce.repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProduitBusiness implements IProduitBusiness {

    @Autowired
    private ProduitRepository produitRepository;

    /**
     * Ajoute un produit dans la base de données.
     * @param referenceProduit Référence du produit
     * @param description Description
     * @param prixHT Prix hors taxe
     * @return l'objet produit crée
     */
    @Override
    public Produit addProduit(String referenceProduit, String description, double prixHT) {
        Produit produit = new Produit();
        produit.setReferenceProduit(referenceProduit);
        produit.setDescription(description);
        produit.setPrixHT(prixHT);
        produitRepository.save(produit);
        return produit;
    }

    /**
     * Modifie le produit dans la base de données
     * @param referenceProduit Référence du produit à modifier
     * @param description Description
     * @param prixHT Prix hors taxe
     * @return l'objet produit modifié, null le produit à modifier n'est pas trouvée
     */
    @Override
    public Produit updateProduit(String referenceProduit, String description, double prixHT) {
        Optional<Produit> tempProduit = produitRepository.findById(referenceProduit);
        if(tempProduit.isPresent() == true){
            Produit produit = tempProduit.get();
            produit.setDescription(description);
            produit.setPrixHT(prixHT);
            produitRepository.save(produit);
            return produit;
        }
        return null;
    }

    /**
     * Supprime le produit dans la base de données.
     * @param referenceProduit Référence du produit à supprimer
     * @return true
     */
    @Override
    public boolean deleteProduit(String referenceProduit) {
        produitRepository.delete(produitRepository.findById(referenceProduit).get());
        return true;
    }

    /**
     * Retourne la liste complète des produits liés à la base de données.
     * @return une liste de produit
     */
    @Override
    public List<Produit> getProduit() {
        return produitRepository.findAll();
    }

    /**
     * Retourne le produit recherché.
     * @param referenceProduit Référence du produit à rechercher
     * @return l'objet produit recherché sinon null, s'il n'est pas trouvé
     */
    @Override
    public Produit getProduitByID(String referenceProduit) {
        Optional<Produit> tempProduit = produitRepository.findById(referenceProduit);
        if(tempProduit.isPresent() == true){
            return tempProduit.get();
        }
        return null;
    }
}
