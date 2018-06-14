package ru.itpark.service.models;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Category { // категории
    private Integer id; // id при создании категории
    private String nameCategory; // наименование категории
    private List<Product> products;
}
