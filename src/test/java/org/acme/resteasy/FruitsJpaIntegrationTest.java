package org.acme.resteasy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.IntStream;

import javax.inject.Inject;

import org.acme.resteasy.infrastructure.persistence.mssql.JpaFruitRepository;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class FruitsJpaIntegrationTest {

    @Inject
    JpaFruitRepository jpaFruitRepository;

    @Test
    public void test_jpaRepository() {
        int numberOfEntities = 8000;
        int batchSize = 1000;
        assertEquals(numberOfEntities % batchSize, 0);

        int numberOfBatches = numberOfEntities / batchSize;

        IntStream.range(0, numberOfBatches).forEach(i -> {
            jpaFruitRepository.createTestBunch(batchSize);
        });
        System.out.println("\ndone.");

        int counted = jpaFruitRepository.count();
        System.out.println(String.format("created %d fruits", counted));
        assertEquals(numberOfEntities, counted);
        given().when().get("/fruits/reindex").then().statusCode(200).body(is("ok"));
    }
}
