package ru.itpark.service.models;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Product { //продукция
    private Integer id; // id при создании
    private Category idCategory; // id из таблицы категории
    private String name; // наименование продукта
    private Integer article; //артикль продукта
    private String material; // состав продукта
    private String color; // цвет
    private String size; // размер (10 см)
    private Integer availability; // количество в наличии
    private Integer price; // цена
}
