package ru.itpark.service.models;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Payment { //оплата
    private Integer id; // id при создании
    private String namePayment; // наименование оплаты
    private String statusPayment; // статус оплаты
}
