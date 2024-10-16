package com.apiecommerce.apiecomerce.client.services;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.apiecommerce.apiecomerce.client.entities.ClienteProduto;
import com.apiecommerce.apiecomerce.client.entities.SacolaCliente;
import com.apiecommerce.apiecomerce.client.entities.data.ClienteSacolaDTO;
import com.apiecommerce.apiecomerce.client.entities.data.PaymentDTO;
import com.apiecommerce.apiecomerce.client.repositories.ClienteProdutoRepository;
import com.apiecommerce.apiecomerce.client.repositories.ClienteSacolaRepository;
import com.apiecommerce.apiecomerce.server.interfaces.SacolaRepository;
import com.apiecommerce.apiecomerce.server.services.CustomUserDetailsService;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.preference.Preference;

@Service
public class MercadoPagoService {

        @Value("${token.mercadopago}")
        String token;

        @Autowired
        CustomUserDetailsService userDetailsService;
        @Autowired
        SacolaRepository sacolaRepository;
        @Autowired
        ClienteSacolaRepository clienteSacolaRepository;
        @Autowired
        ClienteSacolaService clienteSacolaService;
        @Autowired
        ClienteProdutoRepository clienteProdutoRepository;

        public List<ClienteProduto> listProduto(ClienteSacolaDTO sacolaDTO) {
                var sacola = clienteSacolaService.transformaSacolaEmSacolaCliente(sacolaDTO.getLogin());
                var produtos = clienteProdutoRepository
                                .encontreTodosOsClienteProdutoPorId(sacola.getId());
                return produtos;
        }

        public SacolaCliente sacolaCliente(ClienteSacolaDTO sacolaDTO) {
                var usuario = userDetailsService.validarUsuario(sacolaDTO.getLogin());
                var sacola = clienteSacolaRepository.findByUsuarioId(usuario.getId());
                return sacola;
        }

        public List<String> getPreferenceId(ClienteSacolaDTO sacola) {
                try {
                        var produto = listProduto(sacola);
                        MercadoPagoConfig.setAccessToken(token);

                        List<PreferenceItemRequest> items = new ArrayList<>();

                        for (int i = 0; i < produto.size(); i++) {
                                produto.get(i);
                                PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
                                                .id(produto.get(i).getId().toString())
                                                .title(produto.get(i).getProduto().getNome())
                                                .description(produto.get(i).getProduto().getDescricao())
                                                .pictureUrl("http://picture.com/PS5")
                                                .categoryId("games")
                                                .quantity(produto.get(i).getQuantidadeProduto())
                                                .currencyId("BRL")
                                                .unitPrice(new BigDecimal(produto.get(i).getProduto().getPreco()))
                                                .build();

                                items.add(itemRequest);
                        }
                        PreferenceBackUrlsRequest backUrlsRequest = PreferenceBackUrlsRequest
                                        .builder()
                                        .success("https://55e1-2804-6828-fea6-5e00-edfe-8349-5218-ec5f.ngrok-free.app/webhook/mercadopago/")
                                        .pending("https://linkedin.com")
                                        .failure("https://youtube.com.br")
                                        .build();

                        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                                        .items(items)
                                        .backUrls(backUrlsRequest)
                                        .autoReturn("all")
                                        .build();

                        PreferenceClient client = new PreferenceClient();
                        Preference preference = client.create(preferenceRequest);
                        List<String> list = new ArrayList<>();
                        list.add(preference.getId());
                        list.add(preference.getCollectorId().toString());
                        list.add(preference.getClientId());
                        list.add(preference.getMarketplace());
                        list.add(preference.getInitPoint());
                        list.add(preference.getSandboxInitPoint());

                        var sacolaDTO = sacolaCliente(sacola);
                        sacolaDTO.setPreferenceId(preference.getId());
                        sacolaDTO.setPaymentId(preference.getCollectorId());
                        clienteSacolaRepository.saveAndFlush(sacolaDTO);

                        return list;
                } catch (MPException | MPApiException e) {
                        return new ArrayList<>(e.getMessage().indexOf(token));
                }
        }

        public PaymentDTO consultarPagamento(Long paymentId) throws Exception {
                // Construir a URL para a API de Pagamentos do Mercado Pago
                String url = "https://api.mercadopago.com/v1/payments/" + paymentId;

                // Fazer a chamada HTTP GET para consultar o pagamento
                RestTemplate restTemplate = new RestTemplate();
                HttpHeaders headers = new HttpHeaders();
                headers.setBearerAuth(token); // Adicionar o token de autenticação

                HttpEntity<Void> entity = new HttpEntity<>(headers);

                ResponseEntity<PaymentDTO> response = restTemplate.exchange(url, HttpMethod.GET, entity,
                                PaymentDTO.class);

                System.out.println(response.getBody());

                // Retornar o objeto Payment da resposta
                return response.getBody();
        }

}