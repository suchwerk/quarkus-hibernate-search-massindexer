package org.acme.resteasy.infrastructure.persistence.mssql;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

@Indexed
@Entity
@Table(name = "Fruit")
public class FruitEntity {

    @Id
    @FullTextField
    public String id;
    @FullTextField
    public String name;
    @FullTextField
    public String color;

    public FruitEntity() {

    }

    public FruitEntity(String id, String name, String color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }
}
