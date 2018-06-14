package ru.itpark.service.repository;

import ru.itpark.service.models.Category;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category> {

    List<Category> findAllWithProducts();
}
