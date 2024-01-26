package org.example;

import java.util.*;

public class App 
{
    public static void main( String[] args )
    {
        Product p1 = new Product("тесто");
        Product p2 = new Product("Мука");
        Product p3 = new Product("Яйца");
        Product p4 = new Product("Вода");
        Product p5 = new Product("Пшеница");

        System.out.println(p1.addProduct(p2));
        System.out.println(p1.addProduct(p3));
        System.out.println(p1.addProduct(p4));
        System.out.println(p2.addProduct(p1));
        System.out.println(p2.addProduct(p5));
        System.out.println(p5.addProduct(p1));

    }
}

class Product {

    // Название продукта
    private String name;
    // Дочерние продукты. Их хранение необязательно - этого не требует задание. Но если мы захотим узнать состав торта, нам придется их вывести)
    private Set<Product> childrenProducts = new HashSet<>();
    // Родительские продукты
    private Set<Product> parentProducts = new HashSet<>();

    // Добавление нового дочернего продукта в состав текущего
    public Product (String name) {
        this.name = name;
    }

    // Добавление нового дочернего продукта в состав текущего, если нет циклической зависимости
    public boolean addProduct(Product p) {
        // Поиск цикла в родительских продуктах
        Set<Product> visitedProducts = new HashSet<>();
        if (p == null || isCyclicAddition(p, visitedProducts)) {
            return false; // Если найден - возвращаем false
        }

        // Если цикл не найден, добавляем зависимости и возвращаем true
        this.childrenProducts.add(p);
        p.parentProducts.add(this);
        return true;
    }

    // Рекурсивная проверка всех родительских продуктов на наличие цикла
    private boolean isCyclicAddition(Product p, Set<Product> visitedProducts) {
        // Если текущий продукт был проверен ранее, его обход не обязателен
        if (visitedProducts.contains(p)) {
            return false;
        }

        // Если текущий продукт равен добавляемому - это цикл
        if (p.equals(this)) {
            return true;
        }

        // Проверяем родительские продукты на наличие цикла
        for (Product parentProduct : parentProducts) {
            // Если хотя бы один из родительских продуктов повторяет добавляемый - это цикл
            if (parentProduct.isCyclicAddition(p, visitedProducts)) {
                return true;
            }
            // Добавить продукт в список проверенных
            visitedProducts.add(parentProduct);
        }
        return false;

    }

    // Получить имя продукта
    public String getName() {
        return this.name;
    }

    // Получить дочерние продукты. Их хранение необязательно - этого не требует задание. Но если мы захотим узнать состав торта, нам придется его вызвать)
    public Set<Product> getChildren() {
        return childrenProducts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        return name.equals(product.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}