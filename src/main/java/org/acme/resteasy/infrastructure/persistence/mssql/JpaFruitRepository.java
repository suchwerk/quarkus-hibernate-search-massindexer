package org.acme.resteasy.infrastructure.persistence.mssql;

import java.util.Optional;
import java.util.stream.IntStream;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.acme.resteasy.domain.model.Fruit;
import org.acme.resteasy.domain.repository.FruitRepository;

@Dependent
public class JpaFruitRepository implements FruitRepository {

    private EntityManager em;

    public JpaFruitRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    @Transactional
    public Optional<Fruit> fruitById(String id) {
        return Optional.ofNullable(em.find(FruitEntity.class, id)).map(this::mapToFruit);
    }

    @Override
    @Transactional
    public void saveNew(Fruit fruit) {
        FruitEntity entity = new FruitEntity(fruit.id, fruit.name, fruit.color);
        em.persist(entity);
    }

    @Transactional
    public void createTestBunch(int n) {
        IntStream.range(1, n + 1).forEach(i -> {
            Fruit fruit = Fruit.random();
            FruitEntity entity = new FruitEntity(fruit.id, fruit.name, fruit.color);
            em.persist(entity);
            if (i % 100 == 0)
                System.out.print(".");
            if (i % 10000 == 0)
                System.out.print(i + "\n");
        });
        System.out.println("\n");

    }

    private Fruit mapToFruit(FruitEntity fruitEntity) {
        return new Fruit(fruitEntity.id, fruitEntity.name, fruitEntity.color);
    }

    public int count() {
        return ((Number) em.createQuery("SELECT COUNT(o) FROM FruitEntity o").getSingleResult()).intValue();
    }
}
