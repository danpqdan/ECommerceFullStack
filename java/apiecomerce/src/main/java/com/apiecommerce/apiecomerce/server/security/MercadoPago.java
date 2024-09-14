package com.apiecommerce.apiecomerce.server.security;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.apiecommerce.apiecomerce.server.entities.Produtos;
import com.apiecommerce.apiecomerce.server.entities.DTO.SacolaDTO;
import com.apiecommerce.apiecomerce.server.entities.DTO.SacolaProdutoDTO;
import com.apiecommerce.apiecomerce.server.interfaces.SacolaRepository;
import com.apiecommerce.apiecomerce.server.interfaces.UsuarioRepository;
import com.apiecommerce.apiecomerce.server.services.SacolaService;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.MercadoPagoClient;
import com.mercadopago.client.payment.PaymentPayerRequest;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;

@Service
public class MercadoPago {

        @Value("${token.mercadopago}")
        String token;

        @Autowired
        UsuarioRepository usuarioRepository;
        @Autowired
        SacolaRepository sacolaRepository;
        @Autowired
        SacolaService sacolaService;

        public List<Produtos> listProduto(SacolaProdutoDTO sacolaDTO) {
                var sac = sacolaRepository.findById(sacolaDTO.getProdutoID()).get().getProdutos();
                return sac.stream().toList();
        }

        public Double valorSacola(SacolaProdutoDTO sacolaProdutoDTO) {
                return sacolaService.somaValorProdutos(sacolaProdutoDTO);
        }

        public ModelAndView pageIndexPg() {
                ModelAndView mv = new ModelAndView();
                mv.setViewName("checkout.html");
                return mv;
        }

        public List<String> getPreferenceId(SacolaDTO sacola) {
                try {
                        var sac = sacolaRepository.findById(sacola.id()).get().getProdutos();
                        MercadoPagoConfig.setAccessToken(token);
                        
                        
                        List<PreferenceItemRequest> items = new ArrayList<>();
                        
                        for (int i = 0; i < sac.size(); i++) {
                                sac.get(i);
                                PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
                                                .id(sac.get(i).getId().toString())
                                                .title(sac.get(i).getNome())
                                                .description("Teste")
                                                .pictureUrl("http://picture.com/PS5")
                                                .categoryId("games")
                                                .quantity(sac.get(i).getQuantidade())
                                                .currencyId("BRL")
                                                .unitPrice(new BigDecimal(sac.get(i).getPreco()))
                                                .build();

                                List<PreferenceItemRequest> items1 = new ArrayList<>();
                                items1.add(itemRequest);
                                items.addAll(items1);
                        }
                        PreferenceBackUrlsRequest backUrlsRequest = PreferenceBackUrlsRequest
                                        .builder()
                                        .success("https://google.com.br")
                                        .pending("https://linkedin.com")
                                        .failure("https://youtube.com.br")
                                        .build();

                        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                                        .items(items)
                                        .backUrls(backUrlsRequest)
                                        .build();

                        PreferenceClient client = new PreferenceClient();
                        Preference preference = client.create(preferenceRequest);
                        List<String> list = new ArrayList<>();
                        list.add(preference.getId());
                        list.add(preference.getMarketplace());
                        list.add(preference.getInitPoint());
                        list.add(preference.getSandboxInitPoint());

                        return list;
                } catch (MPException | MPApiException e) {
                        return new ArrayList<>(e.getMessage().indexOf(token));
                }
        }

        public void infoCliente() {
                
        }

}