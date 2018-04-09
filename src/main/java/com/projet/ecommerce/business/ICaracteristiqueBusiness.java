package com.projet.ecommerce.business;

import com.projet.ecommerce.persistance.entity.Caracteristique;
import com.projet.ecommerce.persistance.entity.TypeCaracteristique;

import java.util.List;

/**
 * Interface de CaracteristiqueBusiness
 */
public interface ICaracteristiqueBusiness {
    /**
     * Méthode définissant l'ajout de d'une caractéristique.
     * @param typeCaracteristique objet TypeCaracteristique liée à la caractéristique
     * @param valeur la valeur associée à la caractéristique
     * @return l'objet caractéristique crée
     */
    Caracteristique addCaracteristique(TypeCaracteristique typeCaracteristique, String valeur);

    /**
     * Méthode définissant la modification d'une caractéristique.
     * @param idCaracteristique Id de la caractéristiqe à modifier
     * @param typeCaracteristique le nouveau objet TypeCaracteristique liée à la caractéristique
     * @param valeur la nouvelle valeur associée à la caractéristique
     * @return l'objet caractéristique modifié.
     */
    Caracteristique updateCaracteristique(int idCaracteristique, TypeCaracteristique typeCaracteristique, String valeur);

    /**
     * Méthode définissant la supprésion d'un type de caractéristique.
     * @param idCaracteristique Id de la caractéristique à supprimer
     * @return true
     */
    boolean deleteCaracteristique(int idCaracteristique);

    /**
     * Méthode définissant la recherche de tous les caractéristiques.
     * @return une liste de caractéristique
     */
    List<Caracteristique> getCaracteristique();

    /**
     * Méthode définissant la recherche d'une caractéristique selon l'id de la caractéristique.
     * @param idCaracteristique Id de la caractéristique à rechercher
     * @return l'objet recherché
     */
    Caracteristique getCaracteristiqueByID(int idCaracteristique);
}
