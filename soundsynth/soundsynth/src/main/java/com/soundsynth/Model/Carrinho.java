package com.soundsynth.Model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Carrinho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "carrinho", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemCarrinho> itens = new ArrayList<>();

    public Carrinho() {}

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ItemCarrinho> getItens() {
        return itens;
    }

    public void setItens(List<ItemCarrinho> itens) {
        this.itens = itens;
    }

    public void adicionarItem(ItemCarrinho item) {
        this.itens.add(item);
        item.setCarrinho(this);
    }

    public void removerItem(ItemCarrinho item) {
        this.itens.remove(item);
        item.setCarrinho(null);
    }

    public BigDecimal calcularSubtotalAVista() {
        return itens.stream()
                .map(item -> item.getProduto().getPrecoAVista().multiply(BigDecimal.valueOf(item.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal calcularSubtotalAPrazo() {
        return itens.stream()
                .map(item -> item.getProduto().getPrecoAPrazo().multiply(BigDecimal.valueOf(item.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}

