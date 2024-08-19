package com.soundsynth.segundotestethymeleaf.Model;


import lombok.Data;

@Data
public class ItemCarrinho {
    private Long id;
    private Produto produto;
    private int quantidade;
    private Carrinho carrinho;
}