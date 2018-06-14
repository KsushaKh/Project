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
public class Order {
    private Integer id; // id при создании
    private List<Product> idProduct; // id из таблицы продукция
    private User idUser; // id из таблицы пользователи
    private List<Integer> amount; // количество
    private List<Product> priceProduct; // цена продукта
    private Integer priceOrder; // цена заказа
    private Delivery idDelivery; // id из таблицы доставка
    private Payment idPayment; // id из таблицы оплата
    private String statusOrder; // статус заказа
}
