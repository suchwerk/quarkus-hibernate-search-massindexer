package org.acme.resteasy.api;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.acme.resteasy.domain.model.Fruit;
import org.acme.resteasy.infrastructure.persistence.mssql.FruitEntity;
import org.hibernate.CacheMode;
import org.hibernate.search.mapper.orm.Search;

@Path("/fruits")
public class FruitsResource {
    @Inject
    EntityManagerFactory entityManagerFactory;

    @GET
    @Path("/reindex")
    @Transactional
    @Produces(MediaType.TEXT_PLAIN)
    public String reindex() throws InterruptedException {
        System.out.println("reindexing...");
        Search.mapping(entityManagerFactory).scope(FruitEntity.class).massIndexer().typesToIndexInParallel(1)
                .threadsToLoadObjects(12).batchSizeToLoadObjects(100).cacheMode(CacheMode.NORMAL).idFetchSize(100)
                .transactionTimeout(3600).startAndWait();
        System.out.println("finished.");
        return "ok";
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public List<Fruit> fruits() {
        return Arrays.asList(Fruit.apple(), Fruit.banana(), Fruit.orange());
    }
}