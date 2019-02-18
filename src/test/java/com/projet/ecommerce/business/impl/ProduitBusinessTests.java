package com.projet.ecommerce.business.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.projet.ecommerce.business.dto.CaracteristiqueDTO;
import com.projet.ecommerce.business.dto.ProduitDTO;
import com.projet.ecommerce.business.dto.TypeCaracteristiqueDTO;
import com.projet.ecommerce.business.dto.transformer.CaracteristiqueTransformer;
import com.projet.ecommerce.business.dto.transformer.ProduitTransformer;
import com.projet.ecommerce.entrypoint.graphql.GraphQLCustomException;
import com.projet.ecommerce.persistance.entity.Caracteristique;
import com.projet.ecommerce.persistance.entity.Categorie;
import com.projet.ecommerce.persistance.entity.Photo;
import com.projet.ecommerce.persistance.entity.Produit;
import com.projet.ecommerce.persistance.repository.CaracteristiqueRepository;
import com.projet.ecommerce.persistance.repository.CategorieRepository;
import com.projet.ecommerce.persistance.repository.PhotoRepository;
import com.projet.ecommerce.persistance.repository.ProduitRepository;
import com.projet.ecommerce.persistance.repository.ProduitRepositoryCustom;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ProduitBusinessTests {

    @Mock
    private ProduitRepository produitRepository;

    @Mock
    private CategorieRepository categorieRepository;

    @Mock
    private ProduitRepositoryCustom produitRepositoryCustom;

    @Mock
    private PhotoRepository photoRepository;

    @Mock
    private Page page;
    
    @Mock
    private CaracteristiqueRepository caracteristiqueRepository;

    @InjectMocks
    private ProduitBusiness produitBusiness;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void add() {
        Produit produit = new Produit();
        produit.setReferenceProduit("A05A01");
        produit.setPrixHT(2.1f);
        produit.setDescription("Un livre");
        produit.setNom("Livre1");
        produit.setCategories(new ArrayList<>());
        produit.setPhotos(new ArrayList<>());
        Mockito.when(produitRepository.save(Mockito.any())).thenReturn(produit);

        ProduitDTO retour1 = produitBusiness.add("A05A01", "Test", "Test", 4.7f, null);
        Assert.assertNotNull(retour1);
        Assert.assertEquals(produit.getNom(), retour1.getNom());
        Assert.assertEquals(produit.getDescription(), retour1.getDescription());
        Assert.assertEquals(produit.getPrixHT(), retour1.getPrixHT(), 0);
        Assert.assertEquals(produit.getReferenceProduit(), retour1.getRef());

        // Je teste si le produit business m'envoie bien une GraphQLCustomException, si le produit existe déjà
        thrown.expect(GraphQLCustomException.class);
        ProduitDTO retour2 = produitBusiness.add("", "", "dfdfdf", 0, null);
        Assert.assertNull(retour2);
    }

    @Test
    public void addProductAlreadyExist() {
        Produit produit = new Produit();
        produit.setReferenceProduit("A05A01");
        produit.setPrixHT(2.1f);
        produit.setDescription("Un livre");
        produit.setNom("Livre1");
        produit.setCategories(new ArrayList<>());
        produit.setPhotos(new ArrayList<>());

        // Je teste si le produit business m'envoie bien une GraphQLCustomException, si le produit existe déjà
        thrown.expect(GraphQLCustomException.class);
        Mockito.when(produitRepository.findById(Mockito.anyString())).thenReturn(Optional.of(produit));
        ProduitDTO retour = produitBusiness.add("A05A01", "Test", "Test", 4.7f, null);
        Assert.assertNull(retour);
    }

    @Test
    public void addProductWithCategories() {
        Produit produit = new Produit();
        produit.setReferenceProduit("A05A01");
        produit.setPrixHT(2.1f);
        produit.setDescription("Un livre");
        produit.setNom("Livre1");

        Categorie categorie = new Categorie();
        categorie.setNomCategorie("Transport");
        categorie.setIdCategorie(1);
        categorie.setBorneGauche(1);
        categorie.setBorneDroit(8);

        List<Categorie> categorieList = new ArrayList<>();
        categorieList.add(categorie);

        produit.setCategories(categorieList);
        produit.setPhotos(new ArrayList<>());

        List<Integer> categoriesProduit = new ArrayList<>();
        categoriesProduit.add(1);
        categoriesProduit.add(2);
        categoriesProduit.add(3);


        Mockito.when(categorieRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(categorie));
        Mockito.when(produitRepository.save(Mockito.any())).thenReturn(produit);

        ProduitDTO retour = produitBusiness.add("A05A01", "Test", "Test", 4.7f, categoriesProduit);


        Assert.assertNotNull(retour);
        Assert.assertEquals(retour.getClass(), ProduitDTO.class);
        Assert.assertEquals(retour.getCategories().get(0).getNom(), "Transport");
    }

    @Test
    public void updateFound() {
        Produit produit = new Produit();
        produit.setReferenceProduit("A05A01");
        produit.setPrixHT(2.1f);
        produit.setDescription("Un livre");
        produit.setNom("Livre");
        produit.setCategories(new ArrayList<>());
        produit.setPhotos(new ArrayList<>());

        Mockito.when(produitRepository.findById(Mockito.any())).thenReturn(Optional.of(produit));
        Mockito.when(produitRepository.save(Mockito.any())).thenReturn(produit);

        ProduitDTO retour = produitBusiness.update(produit);
        Assert.assertNotNull(retour);

        Assert.assertEquals(produit.getNom(), retour.getNom());
        Assert.assertEquals(produit.getDescription(), retour.getDescription());
        Assert.assertEquals(produit.getPrixHT(), retour.getPrixHT(), 0);
        Assert.assertEquals(produit.getReferenceProduit(), retour.getRef());
    }

    @Test
    public void updateNull() {
        produitBusiness.update(null);
    }

    @Test
    public void updateNotFound() {
        Mockito.when(produitRepository.findById(Mockito.any())).thenReturn(Optional.empty());
        Produit produit = new Produit();
        produit.setReferenceProduit("Test");
        thrown.expect(GraphQLCustomException.class);
        produitBusiness.update(produit);
    }

    @Test
    public void updateCatNotFound() {
        Produit produit = new Produit();
        produit.setReferenceProduit("A05A01");

        List<Categorie> categorieList = new ArrayList<>();
        categorieList.add(new Categorie());

        produit.setCategories(categorieList);
        produit.setPhotos(new ArrayList<>());

        Mockito.when(produitRepository.findById(Mockito.anyString())).thenReturn(Optional.of(produit));

        thrown.expect(GraphQLCustomException.class);
        produitBusiness.update(produit);
    }

    @Test
    public void updateCatFound() {
        Categorie categorie = new Categorie();

        Produit produit = new Produit();
        produit.setReferenceProduit("A05A01");

        List<Categorie> categorieList = new ArrayList<>();
        categorieList.add(categorie);

        produit.setCategories(categorieList);
        produit.setPhotos(new ArrayList<>());

        Mockito.when(produitRepository.findById(Mockito.anyString())).thenReturn(Optional.of(produit));
        Mockito.when(categorieRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(categorie));
        Mockito.when(produitRepository.save(Mockito.any())).thenReturn(produit);

        ProduitDTO retour = produitBusiness.update(produit);
        Assert.assertNotNull(retour);
        Assert.assertEquals(1, retour.getCategories().size());
    }

    @Test
    public void updatePictureNotFound() {
        Produit produit = new Produit();
        produit.setReferenceProduit("A05A01");
        produit.setCategories(new ArrayList<>());

        List<Photo> photoList = new ArrayList<>();
        photoList.add(new Photo());

        produit.setPhotos(photoList);

        Mockito.when(produitRepository.findById(Mockito.anyString())).thenReturn(Optional.of(produit));

        thrown.expect(GraphQLCustomException.class);
        produitBusiness.update(produit);
    }

    @Test
    public void updatePictureFound() {
        Photo photo = new Photo();

        Produit produit = new Produit();
        produit.setReferenceProduit("A05A01");
        produit.setCategories(new ArrayList<>());

        List<Photo> photoList = new ArrayList<>();
        photoList.add(photo);

        produit.setPhotos(photoList);

        Mockito.when(produitRepository.findById(Mockito.anyString())).thenReturn(Optional.of(produit));
        Mockito.when(photoRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(photo));
        Mockito.when(produitRepository.save(Mockito.any())).thenReturn(produit);

        ProduitDTO retour = produitBusiness.update(produit);
        Assert.assertNotNull(retour);
        Assert.assertEquals(1, retour.getPhotos().size());
    }

    @Test
    public void delete() {
        Assert.assertTrue(produitBusiness.delete("A05A01"));
    }

    @Test
    public void getAll() {
        List<Produit> produitList = new ArrayList<>();
        Mockito.when(produitRepository.findAll()).thenReturn(produitList);
        Assert.assertEquals(produitBusiness.getAll().size(), 0);

        Produit produit = new Produit();
        produit.setReferenceProduit("A05A01");
        produit.setPrixHT(2.1f);
        produit.setDescription("Un livre");
        produit.setNom("Livre1");
        produit.setPhotos(new ArrayList<>());
        produit.setCategories(new ArrayList<>());
        produitList.add(produit);

        Mockito.when(produitRepository.findAll()).thenReturn(produitList);
        Mockito.verify(produitRepository, Mockito.times(1)).findAll();
        List<ProduitDTO> produitDTOList = produitBusiness.getAll();
        Assert.assertEquals(produitDTOList.size(), 1);

        ProduitDTO retour = produitDTOList.get(0);
        Assert.assertEquals(produit.getNom(), retour.getNom());
        Assert.assertEquals(produit.getDescription(), retour.getDescription());
        Assert.assertEquals(produit.getPrixHT(), retour.getPrixHT(), 0);
        Assert.assertEquals(produit.getReferenceProduit(), retour.getRef());

        Mockito.verify(produitRepository, Mockito.times(2)).findAll();
    }

    @Test
    public void getAllByRefAndCatAndName() {
        // Initialisation
        List<Produit> produitList = new ArrayList<>();
        Mockito.when(produitRepositoryCustom.findAllWithCriteria(Mockito.anyString(), Mockito.anyString())).thenReturn(produitList);
        Assert.assertEquals(produitRepositoryCustom.findAllWithCriteria(Mockito.anyString(), Mockito.anyString()).size(), 0);

        Produit produit = new Produit();
        produit.setReferenceProduit("A05A01");
        produit.setPrixHT(2.1f);
        produit.setDescription("Un livre");
        produit.setNom("Livre1");
        produit.setPhotos(new ArrayList<>());
        produit.setCategories(new ArrayList<>());
        produitList.add(produit);

        // Permets de tester si la méthode retourne une List avec une référence
        Mockito.when(produitRepositoryCustom.findAllWithCriteria(Mockito.any(), Mockito.any())).thenReturn(produitList);
        Mockito.verify(produitRepositoryCustom, Mockito.times(1)).findAllWithCriteria(Mockito.any(), Mockito.any());
        List<ProduitDTO> produitDTOList = produitBusiness.getAll("ref", null, null);
        Assert.assertEquals(produitDTOList.size(), 1);

        // Permets de tester si la méthode retourne une Liste avec un nom de catégorie
        produitDTOList = produitBusiness.getAll(null, "cat", null);
        Assert.assertEquals(produitDTOList.size(), 1);

        // Permets de tester si la méthode retourne une Liste avec un nom de produit
        Mockito.when(produitRepository.findByNomContainingIgnoreCase(Mockito.any())).thenReturn(produitList);
        produitDTOList = produitBusiness.getAll(null, null, "Livre1");
        Mockito.verify(produitRepository, Mockito.times(1)).findByNomContainingIgnoreCase(Mockito.anyString());
        Assert.assertEquals(produitDTOList.size(), 1);

        // Teste si les valeurs sont cohérentes avec le produit retournées
        ProduitDTO retour = produitDTOList.get(0);
        Assert.assertEquals(produit.getNom(), retour.getNom());
        Assert.assertEquals(produit.getDescription(), retour.getDescription());
        Assert.assertEquals(produit.getPrixHT(), retour.getPrixHT(), 0);
        Assert.assertEquals(produit.getReferenceProduit(), retour.getRef());
    }

    @Test
    public void getAllByRefAndCatEmpty() {
        thrown.expect(GraphQLCustomException.class);
        produitBusiness.getAll(null, null, null);
    }

    @Test
    public void getPageWithoutName() {
        Mockito.when(produitRepository.findAll(Mockito.any(Pageable.class))).thenReturn(page);
        Assert.assertNotNull(produitBusiness.getPage(1, 5, null, 0));
    }

    @Test
    public void getPageWithName() {
        Mockito.when(produitRepository.findByNomContainingIgnoreCase(Mockito.any(Pageable.class), Mockito.anyString())).thenReturn(page);
        Assert.assertNotNull(produitBusiness.getPage(1, 5, "Toto", 0));
    }

    //TODO Faire le teste avec id catégorie
    
    
    @Test
    public void addCaracteristique() {
    	//Produit sans caracteristique
    	Produit p = getDummyProduit("123456");
    	    	
    	Mockito.when(produitRepository.findById(Mockito.any())).thenReturn(Optional.of(p));
    	
    	//Meme produit auquel on ajoute une caracteristique
    	Produit p2 = getDummyProduit("123456");
    	Caracteristique c = getDummyCaracteristique(TypeCaracteristiqueDTO.EDITEUR);
    	//on set l'id pour simuler une caracteristique inserée en base
    	c.setId(150);
    	p2.addCaracteristique(c);
    	
    	Mockito.when(produitRepository.save(Mockito.any())).thenReturn(p2);
    	
    	ProduitDTO pDto = produitBusiness.addCaracteristique(p.getReferenceProduit(), TypeCaracteristiqueDTO.EDITEUR.name(), c.getValeur());
    	
    	Assert.assertEquals(p.getReferenceProduit(), pDto.getRef());
    	Assert.assertNotNull(pDto.getCaracteristiques());
    	Assert.assertEquals(pDto.getCaracteristiques().size(), 1);
    	
    	CaracteristiqueDTO cDto = pDto.getCaracteristiques().get(0);
    	Assert.assertNotNull(cDto);
    	Assert.assertEquals(cDto, CaracteristiqueTransformer.entityToDto(c));
    	
    }
    
    @Test
    public void removeCaracteristique() {
    	
    	//produit auquel on ajoute une caracteristique
    	Produit p = getDummyProduit("123456");
    	Caracteristique c = getDummyCaracteristique(TypeCaracteristiqueDTO.EDITEUR);
    	//on set l'id pour simuler une caracteristique inserée en base
    	c.setId(150);
    	p.addCaracteristique(c);
    	
    	Mockito.when(produitRepository.findById(Mockito.any())).thenReturn(Optional.of(p));
    	//on va capturer la caracteristique qui sera supprimée de la base
    	ArgumentCaptor<Caracteristique> cap = ArgumentCaptor.forClass(Caracteristique.class);
    	Mockito.doNothing().when(caracteristiqueRepository).delete(cap.capture());
    	
    	//on verifie que le produit a bien sa caracteristique avant l'appel business  	
    	Assert.assertNotNull(p.getCaracteristiques());
    	Assert.assertFalse(p.getCaracteristiques().isEmpty());
    	
    	ProduitDTO pDto = produitBusiness.removeCaracteristique(CaracteristiqueTransformer.entityToDto(c));
    	
    	//on verifie que le produit n'a plus la caracteristique
    	Assert.assertEquals(p.getReferenceProduit(), pDto.getRef());
    	Assert.assertNotNull(pDto.getCaracteristiques());
    	Assert.assertTrue(pDto.getCaracteristiques().isEmpty());
    	
    	//on verifie que la caracteristique supprimée est bien celle d'origine
    	Assert.assertEquals(c.getId(), cap.getValue().getId());
    	
    }
    
    @Test
    public void getTypesCaracteristique() {
    	
    	List<String> types = produitBusiness.getTypesCaracteristiques();
    	    	
    	Produit p = getDummyProduit("123456");
    	//on mock les operations en bdd car elles ne sont pas importantes pour le test
    	Mockito.when(produitRepository.findById(Mockito.any())).thenReturn(Optional.of(p));
    	Mockito.when(produitRepository.save(Mockito.any())).thenReturn(p);
    	
    	//on valide que chacun des types de la liste est valide pour recuperer une instance de l'enum
    	types.stream().forEach(type -> produitBusiness.addCaracteristique(p.getReferenceProduit(), type, "dummy valeur"));
    }
    
    private Produit getDummyProduit(String refProduit) {
    	Produit p = new Produit();
    	p.setReferenceProduit(refProduit);
    	p.setPrixHT(10.875f);
    	p.setDescription("DUMMY");
    	p.setNom("produit dummy");
    	p.setCategories(new ArrayList<>());
    	p.setPhotos(new ArrayList<>());
    	p.setAvisClients(new ArrayList<>());
    	
    	return p;
    }
    
    private Caracteristique getDummyCaracteristique(TypeCaracteristiqueDTO type) {
    	Caracteristique c = new Caracteristique();
    	c.setTypeCaracteristique(type);
    	c.setValeur("valeur dummy");
    	   	
    	return c;
    }
}
