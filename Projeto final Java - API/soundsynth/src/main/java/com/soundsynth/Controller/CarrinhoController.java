package com.soundsynth.Controller;

import com.soundsynth.Model.Carrinho;
import com.soundsynth.Model.ItemCarrinho;
import com.soundsynth.Service.CarrinhoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/carrinho")
public class CarrinhoController {

    private final CarrinhoService carrinhoService;

    public CarrinhoController(CarrinhoService carrinhoService) {
        this.carrinhoService = carrinhoService;
    }

    @PostMapping("/novo")
    public ResponseEntity<Carrinho> criarCarrinho() {
        Carrinho novoCarrinho = carrinhoService.criarNovoCarrinho();
        return new ResponseEntity<>(novoCarrinho, HttpStatus.CREATED);
    }


    @PostMapping("/{carrinhoId}/{produtoId}")
    public Carrinho adicionarProduto(
            @PathVariable Long carrinhoId,
            @PathVariable Long produtoId,
            @RequestParam int quantidade) {
        return carrinhoService.adicionarProduto(carrinhoId, produtoId, quantidade);
    }

    @DeleteMapping("/{carrinhoId}/remover/{itemId}")
    public Carrinho removerItem(@PathVariable Long carrinhoId, @PathVariable Long itemId) {
        return carrinhoService.removerItem(carrinhoId, itemId);
    }

    @PutMapping("/{carrinhoId}/itens/{itemId}")
    public Carrinho atualizarQuantidade(@PathVariable Long carrinhoId, @PathVariable Long itemId, @RequestParam int quantidade) {
        return carrinhoService.atualizarQuantidade(carrinhoId, itemId, quantidade);
    }

    @GetMapping("/{carrinhoId}/subtotal")
    public BigDecimal calcularSubtotal(@PathVariable Long carrinhoId) {
        return carrinhoService.calcularSubtotal(carrinhoId);
    }

    @GetMapping("/{carrinhoId}/itens")
    public List<ItemCarrinho> listarItens(@PathVariable Long carrinhoId) {
        Carrinho carrinho = carrinhoService.obterCarrinhoPorId(carrinhoId);
        return carrinho.getItens();
    }

}

