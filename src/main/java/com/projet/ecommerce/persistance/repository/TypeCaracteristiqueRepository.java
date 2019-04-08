package com.projet.ecommerce.persistance.repository;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

import com.projet.ecommerce.persistance.entity.TypeCaracteristique;

public interface TypeCaracteristiqueRepository extends CrudRepository<TypeCaracteristique, Integer>{

	@Override
	Collection<TypeCaracteristique> findAll();
	
	TypeCaracteristique findById(int id);
}
