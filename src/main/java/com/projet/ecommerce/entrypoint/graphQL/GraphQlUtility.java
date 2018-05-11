package com.projet.ecommerce.entrypoint.graphQL;

import com.projet.ecommerce.entrypoint.graphQL.categorie.CategorieMutation;
import com.projet.ecommerce.entrypoint.graphQL.categorie.CategorieQuery;
import com.projet.ecommerce.entrypoint.graphQL.pagination.PaginationQuery;
import com.projet.ecommerce.entrypoint.graphQL.produit.ProduitMutation;
import com.projet.ecommerce.entrypoint.graphQL.produit.ProduitQuery;
import graphql.ExceptionWhileDataFetching;
import graphql.GraphQL;
import graphql.GraphQLError;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Configuration de graphQL.
 */

@Configuration
public class GraphQlUtility {

    @Autowired
    private ProduitQuery produitQuery;

    @Autowired
    private ProduitMutation produitMutation;

    @Autowired
    private CategorieQuery categorieQuery;

    @Autowired
    private CategorieMutation categorieMutation;

    @Autowired
    private PaginationQuery paginationQuery;

    @PostConstruct
    public GraphQL createGraphQlObject() {
        return GraphQL.newGraphQL(graphQLSchema())
                .build();
    }

    public List<GraphQLError> graphQLErrorHandler(List<GraphQLError> graphQLErrors){
        List<GraphQLError> clientErrors = graphQLErrors.stream()
                .filter(this::isClientError)
                .collect(Collectors.toList());

        List<GraphQLError> serverErrors = graphQLErrors.stream()
                .filter(e -> !isClientError(e))
                .map(GraphQLErrorAdapter::new)
                .collect(Collectors.toList());

        List<GraphQLError> returnGraphQLErrors = new ArrayList<>();
        returnGraphQLErrors.addAll(clientErrors);
        returnGraphQLErrors.addAll(serverErrors);
        return returnGraphQLErrors;
    }

    private boolean isClientError(GraphQLError error) {
        return !(error instanceof ExceptionWhileDataFetching || error instanceof Throwable);
    }

    public RuntimeWiring buildRuntimeWiring(){
        return  RuntimeWiring.newRuntimeWiring()
                .type(produitQuery.produitWiring())
                .type(produitMutation.produitWiring())
                .type(categorieQuery.produitWiring())
                .type(categorieMutation.produitWiring())
                .type(paginationQuery.produitWiring())
                .build();
    }

    private GraphQLSchema graphQLSchema() {

        File produitSchemaFile = new File("src/main/resources/graphql/produit.graphqls");
        File categorieSchemaFile = new File("src/main/resources/graphql/categorie.graphqls");
        File paginationSchemaFile = new File("src/main/resources/graphql/pagination.graphqls");

        SchemaParser schemaParser = new SchemaParser();
        TypeDefinitionRegistry typeRegistry = new TypeDefinitionRegistry();
        // chaque registre est fusionné dans le registre principal
        typeRegistry.merge(schemaParser.parse(produitSchemaFile));
        typeRegistry.merge(schemaParser.parse(categorieSchemaFile));
        typeRegistry.merge(schemaParser.parse(paginationSchemaFile));

        RuntimeWiring wiring = buildRuntimeWiring();


        return new SchemaGenerator().makeExecutableSchema(typeRegistry, wiring);
    }
}