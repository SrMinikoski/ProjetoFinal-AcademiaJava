package com.soundsynth.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "produtos")
@Data
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String categoria;
    private String imagem;
    private BigDecimal precoAVista;
    private BigDecimal precoAPrazo;
    private String promocao;
    private String descricao;
    private String especificacoes;
    private String marca;

}

