package ru.itpark.service.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

// RowMapper удобен тем, что в каждом Class UsersRepositoryImpl можем использовать
public interface RowMapper<T> {
    //принимает на вход строку ResultSet - это конкретная строка row
    //а возвращает уже объект T
    T mapRow(ResultSet row) throws SQLException;
}
