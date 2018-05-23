package com.projet.ecommerce.business;

import com.projet.ecommerce.business.dto.CategorieDTO;
import com.projet.ecommerce.business.impl.CategorieBusiness;
import com.projet.ecommerce.entrypoint.graphQL.GraphQLCustomException;
import com.projet.ecommerce.persistance.entity.Categorie;
import com.projet.ecommerce.persistance.repository.CategorieRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

/*@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class CategorieBusinessTests {

    @Mock
    private CategorieRepository categorieRepository;

    @InjectMocks
    private CategorieBusiness categorieBusiness;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void insertParent() {
        Categorie categorie1 = new Categorie();
        categorie1.setNomCategorie("Transport");
        categorie1.setBorneGauche(1);
        categorie1.setBorneDroit(4);
        categorie1.setLevel(1);

        Collection<Categorie> categorieCollection = new ArrayList<>();
        categorieCollection.add(categorie1);

        Mockito.when(categorieRepository.findAll()).thenReturn(categorieCollection);
        Mockito.when(categorieRepository.save(Mockito.any())).thenReturn(categorie1);
        CategorieDTO retour1 = categorieBusiness.addParent("Test");

        Assert.assertNotNull(retour1);
        Assert.assertEquals(categorie1.getNomCategorie(), retour1.getNom());
    }

    @Test
    public void insertEnfant() {
        Categorie categorie1 = new Categorie();
        categorie1.setNomCategorie("Transport");
        categorie1.setBorneGauche(1);
        categorie1.setBorneDroit(4);
        categorie1.setLevel(1);

        Collection<Categorie> categorieCollection = new ArrayList<>();
        categorieCollection.add(categorie1);

        Mockito.when(categorieRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(categorie1));
        Mockito.when(categorieRepository.findAll()).thenReturn(categorieCollection);
        Mockito.when(categorieRepository.save(Mockito.any())).thenReturn(categorie1);
        CategorieDTO retour1 = categorieBusiness.addEnfant("Test", categorie1.getIdCategorie());

        Assert.assertNotNull(retour1);
        Assert.assertEquals(categorie1.getNomCategorie(), retour1.getNom());
    }

    @Test
    public void delete() {
        Mockito.when(categorieRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(new Categorie()));
        Assert.assertTrue(categorieBusiness.delete(1));
    }

    @Test
    public void deleteNull() {
        Assert.assertFalse(categorieBusiness.delete(1));
    }

    @Test
    public void getCategorieThrownException(){
        List<Categorie> categories = new ArrayList<>();
        Mockito.when(categorieRepository.findAllWithCriteria(Mockito.anyInt(), Mockito.anyString())).thenReturn(categories);
        thrown.expect(GraphQLCustomException.class);
        categorieBusiness.getCategorie(0, "nom", false);
    }

		Categorie parent1 = new Categorie();
		parent1.setNomCategorie("parent1");
		parent1.setBorneDroit(6);
		parent1.setBorneGauche(1);
		categorieRepository.save(parent1);

		Categorie enfant1 = new Categorie();
		enfant1.setNomCategorie("Enfant1");
		enfant1.setBorneGauche(2);
		enfant1.setBorneDroit(3);
		categorieRepository.save(enfant1);

        Categorie enfant2 = new Categorie();
        enfant2.setNomCategorie("Enfant2");
        enfant2.setBorneGauche(4);
        enfant2.setBorneDroit(5);
        categorieRepository.save(enfant2);

        Categorie parent2 = new Categorie();
        parent2.setNomCategorie("parent2");
        parent2.setBorneDroit(6);
        parent2.setBorneGauche(1);
        categorieRepository.save(parent2);

		Mockito.when(categorieRepository.findCategorieByNomCategorie(Mockito.anyString())).thenReturn(Optional.of(enfant2));

        // TODO: MARCHE PAS - A CONTINUER
        // categorieBusiness.delete("enfant2");

    }

	@Test
	public void deleteNull() {
		Assert.assertFalse(categorieBusiness.delete("Fofo"));
	}

//	@Test
//	public void getAll() {
//		List<Categorie> categories = new ArrayList<>();
//		Mockito.when(categorieRepository.findAll()).thenReturn(categories);
//		Assert.assertEquals(categorieBusiness.getAll().size(), 0);
//
//		// Création des catégories et ajout dans la liste.
//		Categorie categorie1 = new Categorie();
//        categorie1.setNomCategorie("Transport1");
//        categorie1.setBorneGauche(1);
//        categorie1.setBorneDroit(4);
//        categorie1.setLevel(1);
//        categorie1.setProduits(new ArrayList<>());
//
//        Categorie categorie2 = new Categorie();
//        categorie2.setNomCategorie("Transport1");
//        categorie2.setBorneGauche(2);
//        categorie2.setBorneDroit(3);
//        categorie2.setLevel(2);
//        categorie2.setProduits(new ArrayList<>());
//
//        categories.add(categorie1);
//        categories.add(categorie2);
//
//        // Tests
//		Mockito.when(categorieRepository.findAll()).thenReturn(categories);
//        List<CategorieDTO> categorieDTOList = categorieBusiness.getAll();
//
//        Assert.assertEquals(2, categories.size());
//
//		CategorieDTO retour = categorieDTOList.get(0);
//		Assert.assertEquals(categorie1.getNomCategorie(), retour.getNom());
//        Assert.assertEquals(categorie2.getNomCategorie(), retour.getSousCategories().get(0).getNom());
//	}

	@Test
	public void getCategorie() {
		List<Categorie> categories = new ArrayList<>();
		Mockito.when(categorieRepository.findAllWithCriteria(Mockito.anyString(), false)).thenReturn(categories);
		thrown.expect(GraphQLCustomException.class);
		categorieBusiness.getCategorie("nom", false, false);

		// Création des catégories et ajout dans la liste.
		Categorie categorie1 = new Categorie();
		categorie1.setNomCategorie("Transport1");
		categorie1.setBorneGauche(1);
		categorie1.setBorneDroit(4);
		categorie1.setLevel(1);
		categorie1.setProduits(new ArrayList<>());

		Categorie categorie2 = new Categorie();
		categorie2.setNomCategorie("Transport1");
		categorie2.setBorneGauche(2);
		categorie2.setBorneDroit(3);
		categorie2.setLevel(2);
		categorie2.setProduits(new ArrayList<>());

		categories.add(categorie1);
		categories.add(categorie2);

		// Tests
		Mockito.when(categorieRepository.findAllWithCriteria(Mockito.anyString(), false)).thenReturn(categories);
		List<CategorieDTO> categorieDTOList = categorieBusiness.getCategorie("tests", true, false);

		Assert.assertEquals(1, categorieDTOList.size());

		CategorieDTO retour = categorieDTOList.get(0);
		Assert.assertEquals(categorie1.getNomCategorie(), retour.getNom());
		Assert.assertEquals(categorie2.getNomCategorie(), retour.getSousCategories().get(0).getNom());
	}


	@Test
	public void construireAssociationEnfantsChemins() {

		// Création des catégories
		Collection<Categorie> categories = new ArrayList<>();

		Categorie cat1 = new Categorie();
		cat1.setLevel(1);
		cat1.setBorneDroit(10);
		cat1.setBorneGauche(1);
		cat1.setNomCategorie("Transport");

		Categorie cat2 = new Categorie();
		cat2.setLevel(2);
		cat2.setBorneDroit(7);
		cat2.setBorneGauche(2);
		cat2.setNomCategorie("Aérien");

		Categorie cat3 = new Categorie();
		cat3.setLevel(3);
		cat3.setBorneDroit(6);
		cat3.setBorneGauche(3);
		cat3.setNomCategorie("Avion");

		categories.add(cat1);
		categories.add(cat2);
		categories.add(cat3);

		Mockito.when(this.categorieRepository.findParents(Mockito.any())).thenReturn(categories);

		HashMap<Categorie,String> resultat;
		resultat = this.categorieBusiness.construireAssociationEnfantsChemins(categories);

		// Tests
		Assert.assertEquals(resultat.get(cat1), "");
		Assert.assertEquals(resultat.get(cat2), "Transport");
		Assert.assertEquals(resultat.get(cat3), "Transport > Aérien");

	}

    @Test
    public void getCategorieById() {
        List<Categorie> categories = new ArrayList<>();

        // Création des catégories et ajout dans la liste.
        Categorie categorie1 = new Categorie();
        categorie1.setIdCategorie(1);
        categorie1.setNomCategorie("Transport");
        categorie1.setBorneGauche(1);
        categorie1.setBorneDroit(4);
        categorie1.setLevel(1);
        categorie1.setProduits(new ArrayList<>());

        Categorie categorie2 = new Categorie();
        categorie1.setIdCategorie(2);
        categorie2.setNomCategorie("Transport1");
        categorie2.setBorneGauche(2);
        categorie2.setBorneDroit(3);
        categorie2.setLevel(2);
        categorie2.setProduits(new ArrayList<>());

        categories.add(categorie1);
        categories.add(categorie2);

        // Ici je test la recherche d'un nom avec l'affichage des sous-catégories, on doit avoir une liste égale à 1 car ses enfants sont dans l'arraylist sous-catégorie
        Mockito.when(categorieRepository.findAllWithCriteria(Mockito.anyInt(), Mockito.any())).thenReturn(categories);
        List<CategorieDTO> categorieDTOList = categorieBusiness.getCategorie(1, null, true, false);
        Assert.assertEquals(1, categorieDTOList.size());

        CategorieDTO retour = categorieDTOList.get(0);
        Assert.assertEquals(categorie1.getNomCategorie(), retour.getNom());
        Assert.assertEquals(categorie2.getNomCategorie(), retour.getSousCategories().get(0).getNom());

        // Ici je test la recherche d'un nom sans l'affichage des sous-catégories, on doit avoir une liste supérieur à 1
        List<CategorieDTO> categorieDTOList2 = categorieBusiness.getCategorie(1, null, false, false);
        Assert.assertEquals(2, categorieDTOList2.size());
    }

    @Test
    public void construireAssociationEnfantsChemins() {

        // Création des catégories
        Collection<Categorie> categories = new ArrayList<>();

        Categorie cat1 = new Categorie();
        cat1.setLevel(1);
        cat1.setBorneDroit(10);
        cat1.setBorneGauche(1);
        cat1.setNomCategorie("Transport");

        Categorie cat2 = new Categorie();
        cat2.setLevel(2);
        cat2.setBorneDroit(7);
        cat2.setBorneGauche(2);
        cat2.setNomCategorie("Aérien");

        Categorie cat3 = new Categorie();
        cat3.setLevel(3);
        cat3.setBorneDroit(6);
        cat3.setBorneGauche(3);
        cat3.setNomCategorie("Avion");

        categories.add(cat1);
        categories.add(cat2);
        categories.add(cat3);

        Mockito.when(this.categorieRepository.findParents(Mockito.any())).thenReturn(categories);

        HashMap<Categorie, String> resultat;
        resultat = this.categorieBusiness.construireAssociationEnfantsChemins(categories);

        // Tests
        Assert.assertEquals(resultat.get(cat1), "");
        Assert.assertEquals(resultat.get(cat2), "Transport");
        Assert.assertEquals(resultat.get(cat3), "Transport > Aérien");

    }
}*/