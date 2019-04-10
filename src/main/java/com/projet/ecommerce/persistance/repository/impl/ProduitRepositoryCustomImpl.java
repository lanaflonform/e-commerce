package com.projet.ecommerce.persistance.repository.impl;

import com.projet.ecommerce.persistance.entity.Categorie;
import com.projet.ecommerce.persistance.entity.Produit;
import com.projet.ecommerce.persistance.entity.ValeurCaracteristique;
import com.projet.ecommerce.persistance.repository.ProduitRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Repository
public class ProduitRepositoryCustomImpl implements ProduitRepositoryCustom {

	// Requêtes en Java Persistence Query Language
	private static final String SQL_ALL_PRODUCTS = "SELECT p FROM Produit AS p";
	private static final String SQL_PRODUCT_BY_REFERENCE = "SELECT p FROM Produit AS p WHERE p.referenceProduit = :ref";

	private static final String SQL_PRODUCTS_BY_CATEGORY = "SELECT c.produits FROM Categorie AS c " +
			"WHERE c.borneGauche >= (" +
			"SELECT cat_recherchee.borneGauche " +
			"FROM Categorie AS cat_recherchee " +
			"WHERE cat_recherchee.nomCategorie =:cat" +
			") " +
			"AND c.borneDroit <= (" +
			"SELECT cat_recherchee2.borneDroit " +
			"FROM Categorie AS cat_recherchee2 " +
			"WHERE cat_recherchee2.nomCategorie =:cat)";

	@Autowired
	private EntityManager entityManager;

	@Override
	public Collection<Produit> findAllWithCriteria(String ref, String cat) {

		Query query = null;

		if (ref == null) {
			if (cat == null) {
				query = entityManager.createQuery(SQL_ALL_PRODUCTS, Produit.class);
			} else {
				System.out.println("testsdfsdfsdfsdf");
				query = entityManager.createQuery(SQL_PRODUCTS_BY_CATEGORY, Collection.class);
				query.setParameter("cat", cat);
			}
		} else {
			// Dans tous les cas, on fait une recherche par référence
			query = entityManager.createQuery(SQL_PRODUCT_BY_REFERENCE, Produit.class);
			query.setParameter("ref", ref);
		}
		System.out.println(query.getResultList().size());
		return query.getResultList();
	}

	@Override
	public Collection<Produit> findProductWithCriteria(double noteMoyenneMin, double noteMoyenneMax, String nomProduit, String valeurNomProduit, String nomCategorie) {

		CriteriaBuilder criteriaBuilderObj = entityManager.getCriteriaBuilder();

		//Création de l'objet de requête à partir de l'instance 'CriteriaBuilder'.
		CriteriaQuery<Produit>  queryObj = criteriaBuilderObj.createQuery(Produit.class);
		Root<Produit> produit = queryObj.from(Produit.class);
		List<Predicate> predicateList = new ArrayList<>();

		//recuperation de tout les produits
		queryObj.select(produit);

		if(nomProduit != null) {

			predicateList.add(criteriaBuilderObj.equal(produit.get("nom"), nomProduit));
		}

		if( valeurNomProduit != null) {

			predicateList.add(criteriaBuilderObj.like(produit.get("nom"), "%"+valeurNomProduit+"%"));
		}
		
		    

		queryObj.where(criteriaBuilderObj.and(predicateList.toArray(new Predicate[predicateList.size()])));


		TypedQuery<Produit> typedQuery = entityManager.createQuery(queryObj);
		List<Produit> produitList = typedQuery.getResultList();

		return produitList;		
	}



}