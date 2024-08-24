package com.soundsynth.segundotestethymeleaf.Model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class Carrinho {
    private Long id;
    private List<ItemCarrinho> itens = new ArrayList<>();
}
