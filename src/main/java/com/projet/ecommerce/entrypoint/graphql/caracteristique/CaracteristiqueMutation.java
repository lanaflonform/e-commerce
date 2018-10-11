package com.projet.ecommerce.entrypoint.graphql.caracteristique;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.projet.ecommerce.business.dto.CaracteristiqueDTO;
import com.projet.ecommerce.business.impl.CaracteristiqueBusiness;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.idl.TypeRuntimeWiring;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class CaracteristiqueMutation {

    @Autowired
    private CaracteristiqueBusiness caracteristiqueBusiness;

    public TypeRuntimeWiring produitWiring() {

        TypeRuntimeWiring.Builder builder = new TypeRuntimeWiring.Builder();
        builder.typeName("Mutation");

        builder.dataFetcher("addCaracteristique", (DataFetchingEnvironment environment) -> {
            ObjectMapper mapper = new ObjectMapper();
            Object rawInput = environment.getArgument("caracteristique");
            CaracteristiqueDTO caracteristiqueDTO = mapper.convertValue(rawInput, CaracteristiqueDTO.class);
            return caracteristiqueBusiness.add(caracteristiqueDTO);
        });

        builder.dataFetcher("deleteCaracteristique", (DataFetchingEnvironment environment) ->
                caracteristiqueBusiness.delete(environment.getArgument("id"))
        );


        return builder.build();
    }
}