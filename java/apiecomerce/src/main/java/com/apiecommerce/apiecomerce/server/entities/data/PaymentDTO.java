package com.apiecommerce.apiecomerce.server.entities.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {
    private Long id;
    private String status;
    private String status_detail;
    private Double transaction_amount;
    private String payment_method_id;
    private String payment_type_id;
    private String date_approved;

}
