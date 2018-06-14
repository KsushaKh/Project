package ru.itpark.service.models;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Delivery { // доставка
    private Integer id; // id при создании
    private String nameDelivery; // наименование доставки
    private Integer priceDelivery; //стоимость доставки
}
