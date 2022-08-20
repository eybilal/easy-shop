package io.coodle.easyshop.orderservice.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class OrderDto {
    private Long id;

    private String orderNumber;

    private String orderStatus;

    private Long customerId;
}
