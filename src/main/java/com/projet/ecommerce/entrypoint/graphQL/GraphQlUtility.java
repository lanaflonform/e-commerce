package com.projet.ecommerce.entrypoint.graphQL;

import com.projet.ecommerce.entrypoint.graphQL.produit.ProduitMutation;
import com.projet.ecommerce.entrypoint.graphQL.produit.ProduitQuery;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.File;

/**
 * Configuration de graphQL.
 */

@Configuration
public class GraphQlUtility {

    @Autowired
    private ProduitQuery produitQuery;

    @Autowired
    private ProduitMutation produitMutation;

    @PostConstruct
    public GraphQL createGraphQlObject() {
        return GraphQL.newGraphQL(graphQLSchema())
                .build();
    }

    public RuntimeWiring buildRuntimeWiring(){
        return  RuntimeWiring.newRuntimeWiring()
                .type(produitQuery.produitWiring())
                .type(produitMutation.produitWiring())
                .build();
    }

    private GraphQLSchema graphQLSchema() {

        File produitSchemaFile = new File("src/main/resources/graphql/produit.graphqls");
        File categorieSchemaFile = new File("src/main/resources/graphql/categorie.graphqls");

        SchemaParser schemaParser = new SchemaParser();
        TypeDefinitionRegistry typeRegistry = new TypeDefinitionRegistry();
        // chaque registre est fusionné dans le registre principal
        typeRegistry.merge(schemaParser.parse(produitSchemaFile));
        //typeRegistry.merge(schemaParser.parse(categorieSchemaFile));

        RuntimeWiring wiring = buildRuntimeWiring();
        return new SchemaGenerator().makeExecutableSchema(typeRegistry, wiring);
    }
}