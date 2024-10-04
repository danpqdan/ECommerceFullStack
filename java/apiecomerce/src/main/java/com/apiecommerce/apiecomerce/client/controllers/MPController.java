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
import com.apiecommerce.apiecomerce.client.entities.data.SacolaDTO;
import com.apiecommerce.apiecomerce.client.services.ClienteProdutoService;
import com.apiecommerce.apiecomerce.client.services.ClienteSacolaService;
import com.apiecommerce.apiecomerce.client.services.MercadoPagoService;
import com.apiecommerce.apiecomerce.server.entities.SacolaServer;
import com.mercadopago.net.HttpStatus;
import com.mercadopago.resources.payment.Payment;

@RequestMapping("/api")
@RestController
public class MPController {

    @Autowired
    MercadoPagoService mercadoPago;
    @Autowired
    ClienteController clienteController;
    @Autowired
    ClienteSacolaService clienteSacolaService;
    @Autowired
    ClienteProdutoService clienteProdutoService;

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
    public ResponseEntity postProduto(@RequestBody SacolaDTO dto) {
        SacolaServer sacola = clienteSacolaService.transformaSacolaEmSacolaCliente(dto.getLogin());
        List<ClienteProduto> produto = clienteProdutoService.retornarProdutos(sacola.getId());
        ClienteSacolaDTO s1 = new ClienteSacolaDTO();
        s1.setLogin(dto.getLogin());
        s1.setClienteProdutoDTO(produto);
        return ResponseEntity.ok().body(mercadoPago.getPreferenceId(s1));
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
            // Aqui você pode atualizar o status da sacola ou executar outras lógicas
            // necessárias
            // Exemplo: atualizar a sacola para 'paga' usando os dados recebidos
            // updateSacolaToPaid(merchant_order_id);

            System.out.println("Pagamento aprovado: " + payment_id);
        } else {
            System.out.println("Pagamento não aprovado: " + status);
        }

        return ResponseEntity.ok().build();

    }

    @PostMapping("/mercadopago")
    public ResponseEntity<Void> receiveWebhook(@RequestBody Map<String, Object> payload) {
        try {
            // Extrair dados do payload
            Map<String, Object> data = (Map<String, Object>) payload.get("data");
            String paymentId = String.valueOf(data.get("id"));

            // Consultar o pagamento
            Payment payment = mercadoPago.consultarPagamento(paymentId);

            // Verificar o status do pagamento
            if (payment.getStatus().equalsIgnoreCase("approved")) {
                // Lógica para atualizar a sacola/pedido como "pago"
                System.out.println("Pagamento aprovado: " + paymentId);
            } else {
                System.out.println("Pagamento com status: " + payment.getStatus());
            }

            // Responder sucesso para o Mercado Pago
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            // Log de erro
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Retorna 500
        }

    }

}
