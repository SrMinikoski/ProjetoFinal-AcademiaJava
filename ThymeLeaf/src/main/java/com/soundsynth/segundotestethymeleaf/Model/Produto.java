package com.soundsynth.segundotestethymeleaf.Model;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class Produto {
        private Long id;
        private String nome;
        private String categoria;
        private String imagem;
        private BigDecimal precoAVista;
        private BigDecimal precoAPrazo;
        private boolean promocao;
        private String descricao;
        private String especificacoes;
        private String marca;
    }
