package com.apiecommerce.apiecomerce.client.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apiecommerce.apiecomerce.client.entities.ClienteProduto;
import com.apiecommerce.apiecomerce.client.entities.data.ClienteSacolaDTO;
import com.apiecommerce.apiecomerce.client.entities.data.PaymentDTO;
import com.apiecommerce.apiecomerce.client.entities.data.SacolaDTO;
import com.apiecommerce.apiecomerce.client.repositories.ClienteSacolaRepository;
import com.apiecommerce.apiecomerce.client.services.ClienteProdutoService;
import com.apiecommerce.apiecomerce.client.services.ClienteSacolaService;
import com.apiecommerce.apiecomerce.client.services.MercadoPagoService;
import com.apiecommerce.apiecomerce.server.services.CustomUserDetailsService;
import com.mercadopago.net.HttpStatus;

@RequestMapping("/api")
@RestController
public class MPController {

    @Autowired
    ClienteSacolaRepository clienteSacolaRepository;
    @Autowired
    MercadoPagoService mercadoPago;
    @Autowired
    ClienteController clienteController;
    @Autowired
    ClienteSacolaService clienteSacolaService;
    @Autowired
    ClienteProdutoService clienteProdutoService;
    @Autowired
    CustomUserDetailsService userDetailsService;

    @Value("${token.mercadopago}")
    String token;

    @GetMapping("/mp/finalizar")
    public ResponseEntity<Map<String, Object>> listarProdutos(@RequestBody ClienteSacolaDTO dto) {

        Map<String, Object> response = new HashMap<>();
        var lista = mercadoPago.listProduto(dto);

        response.put("Produtos", lista);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/mp/sacola")
    public ResponseEntity postProduto(@RequestHeader("Authorization") String token, @RequestBody SacolaDTO dto) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        if (userDetailsService.validarUsuarioPorToken(token, dto.getLogin()) != null) {

            List<ClienteProduto> produto = clienteProdutoService.retornarProdutos(dto.getLogin());
            ClienteSacolaDTO s1 = new ClienteSacolaDTO();
            s1.setLogin(dto.getLogin());
            s1.setClienteProdutoDTO(produto);
            return ResponseEntity.ok().body(mercadoPago.getPreferenceId(s1));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/mercadopago")
    public ResponseEntity<Void> receiveWebhook(
            @RequestParam String collection_id,
            @RequestParam String collection_status,
            @RequestParam String payment_id,
            @RequestParam String status,
            @RequestParam(required = false) String external_reference,
            @RequestParam String payment_type,
            @RequestParam String merchant_order_id,
            @RequestParam String preference_id,
            @RequestParam String site_id,
            @RequestParam String processing_mode,
            @RequestParam(required = false) String merchant_account_id) {

        if ("approved".equalsIgnoreCase(status)) {
            System.out.println("Pagamento aprovado: " + payment_id);
        } else {
            System.out.println("Pagamento não aprovado: " + status);
        }

        return ResponseEntity.ok().build();

    }

    @PostMapping("/mercadopago")
    public ResponseEntity<Void> receiveWebhook(@RequestBody Map<String, Object> payload) {
        try {

            Map<String, Object> data = (Map<String, Object>) payload.get("data");
            Long paymentId = Long.parseLong(String.valueOf(data.get("id")));
            PaymentDTO payment = mercadoPago.consultarPagamento(paymentId);

            if (payment.getStatus().equalsIgnoreCase("approved")) {

                var sacola = clienteSacolaRepository.findByPaymentId(payment.getCollectorId());
                sacola.setPaymentId(paymentId);
                sacola.setDate_approved(payment.getDate_approved());
                sacola.setStatus_detail(payment.getStatus_detail());
                sacola.setPayment_method_id(payment.getPayment_method_id());
                sacola.setPayment_type_id(payment.getPayment_type_id());
                sacola.setTransaction_amount(payment.getTransaction_amount());
                sacola.setStatus(payment.getStatus());
                clienteSacolaRepository.saveAndFlush(sacola);
                System.out.println("Pagamento aprovado: " + paymentId);
            } else {
                var sacola = clienteSacolaRepository.findByPaymentId(payment.getCollectorId());
                sacola.setPaymentId(paymentId);
                sacola.setDate_approved(payment.getDate_approved());
                sacola.setStatus_detail(payment.getStatus_detail());
                sacola.setPayment_method_id(payment.getPayment_method_id());
                sacola.setPayment_type_id(payment.getPayment_type_id());
                clienteSacolaRepository.saveAndFlush(sacola);
                System.out.println("Pagamento com status: " + payment.getStatus());
            }

            return ResponseEntity.ok().build();

        } catch (Exception e) {
            // Log de erro
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Retorna 500
        }

    }

}
