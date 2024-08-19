package com.soundsynth.Controller;

import ch.qos.logback.core.model.Model;
import com.soundsynth.Model.Carrinho;
import com.soundsynth.Model.ItemCarrinho;
import com.soundsynth.Repository.ItemCarrinhoRepository;
import com.soundsynth.Service.CarrinhoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/carrinho")
public class CarrinhoController {

    private final CarrinhoService carrinhoService;
    private final ItemCarrinhoRepository itemCarrinhoRepository;

    public CarrinhoController(CarrinhoService carrinhoService, ItemCarrinhoRepository itemCarrinhoRepository) {
        this.carrinhoService = carrinhoService;
        this.itemCarrinhoRepository = itemCarrinhoRepository;
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
    public ResponseEntity<Map<String, BigDecimal>> calcularSubtotal(@PathVariable Long carrinhoId) {
        // Obtém o carrinho com o ID fornecido
        Carrinho carrinho = carrinhoService.obterCarrinhoPorId(carrinhoId);

        // Calcula os subtotais
        Map<String, BigDecimal> subtotais = carrinhoService.calcularSubtotais(carrinho);

        if (subtotais != null) {
            return ResponseEntity.ok(subtotais);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{carrinhoId}/itens")
    public List<ItemCarrinho> listarItens(@PathVariable Long carrinhoId) {
        Carrinho carrinho = carrinhoService.obterCarrinhoPorId(carrinhoId);
        return carrinho.getItens();
    }
    @PutMapping("/{carrinhoId}/aumentar/{itemId}")
    public ResponseEntity<Void> aumentarQuantidade(@PathVariable Long carrinhoId, @PathVariable Long itemId) {
        if (carrinhoId == null || itemId == null) {
            return ResponseEntity.badRequest().build();
        }

        try {
            carrinhoService.aumentarQuantidade(carrinhoId, itemId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{carrinhoId}/diminuir/{itemId}")
    public ResponseEntity<Void> diminuirQuantidade(@PathVariable Long carrinhoId, @PathVariable Long itemId) {
        if (carrinhoId == null || itemId == null) {
            return ResponseEntity.badRequest().build();
        }

        try {
            carrinhoService.diminuirQuantidade(carrinhoId, itemId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}

