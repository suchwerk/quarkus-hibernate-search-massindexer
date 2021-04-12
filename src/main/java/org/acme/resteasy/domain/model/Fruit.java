package org.acme.resteasy.domain.model;

import java.util.Random;
import java.util.UUID;

public class Fruit {
    static Random ran = new Random();
    public String id;
    public String name;
    public String color;

    public Fruit(String id, String name, String color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }

    public static Fruit apple() {
        return new Fruit(UUID.randomUUID().toString(), "Apple", "Red");
    }

    public static Fruit banana() {
        return new Fruit(UUID.randomUUID().toString(), "Banana", "Yellow");
    }

    public static Fruit orange() {
        return new Fruit(UUID.randomUUID().toString(), "Orange", "Orange");
    }

    @Override
    public String toString() {
        return "Fruit{" + "id='" + id + '\'' + ", name='" + name + '\'' + ", color='" + color + '\'' + '}';
    }

    public static Fruit random() {
        switch (ran.nextInt(3)) {
        case 0:
            return banana();
        case 1:
            return apple();
        case 2:
            return orange();
        }
        return null;
    }
}
