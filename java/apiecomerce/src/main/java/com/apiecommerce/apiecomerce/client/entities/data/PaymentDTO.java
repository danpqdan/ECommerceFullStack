package com.apiecommerce.apiecomerce.client.entities.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mercadopago.resources.payment.Payment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO extends Payment {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("status")
    private String status;

    @JsonProperty("collector_id")
    private Long collectorId;

    @JsonProperty("status_detail")
    String status_detail;

    @JsonProperty("transaction_amount")
    Double transaction_amount;

    @JsonProperty("payment_method_id")
    String payment_method_id;

    @JsonProperty("payment_type_id")
    String payment_type_id;
    
    @JsonProperty("date_approved")
    String date_approved;

}
