package ru.itpark.service.repository;

import java.util.List;

public interface CrudRepository<T> {
    //поиск по id
    T find(int id);
    //вытащить всех пользователей
    List<T> findAll();
    void save(T model);

}
