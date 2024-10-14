package com.apiecommerce.apiecomerce.client.repositories;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.apiecommerce.apiecomerce.client.entities.ClienteProduto;
import com.apiecommerce.apiecomerce.client.entities.SacolaCliente;

public interface ClienteSacolaRepository extends JpaRepository<SacolaCliente, Long> {

    SacolaCliente findByUsuarioId(Long usuarioId);

    SacolaCliente findByPaymentId(Long paymentID);

}
