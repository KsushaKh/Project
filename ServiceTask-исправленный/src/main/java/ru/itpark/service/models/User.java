package ru.itpark.service.models;

import lombok.*;

//библиотека lombok подключена в файле pom.xml
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class User { // пользователи
    //когда описываем модели стараемся использовать обертки
    // (дает возможность пустых полей)
    private Integer id; // id при создании
    private String name; // имя пользователя
    private String surname; // фамилия
    private String loginBuyer; // логин
    private String passwordBuyer; // пароль
    private String addressBuyer; //адрес
    private String mail; // почта
    private Integer mobile; // телефон
}