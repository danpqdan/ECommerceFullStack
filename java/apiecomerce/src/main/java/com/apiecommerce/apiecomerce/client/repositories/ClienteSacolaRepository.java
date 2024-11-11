package com.apiecommerce.apiecomerce.client.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apiecommerce.apiecomerce.client.entities.SacolaCliente;

public interface ClienteSacolaRepository extends JpaRepository<SacolaCliente, Long> {

    SacolaCliente findByUsuarioId(Long usuarioId);

    SacolaCliente findByPaymentId(Long paymentID);

}
